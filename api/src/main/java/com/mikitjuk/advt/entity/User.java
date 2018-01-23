package com.mikitjuk.advt.entity;

import com.mikitjuk.advt.entity.types.PsqlEnum;
import com.mikitjuk.advt.entity.types.UserRole;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "apps")
@EqualsAndHashCode
@Table(name = "users")
@TypeDef(name = "pgsql_enum",
        typeClass = PsqlEnum.class)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Integer id;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @Email
    @Size(min = 3, max = 255)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "role")
    @Type(type = "pgsql_enum")
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<App> apps;
}
