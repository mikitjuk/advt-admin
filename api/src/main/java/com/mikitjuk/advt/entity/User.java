package com.mikitjuk.advt.entity;

import com.mikitjuk.advt.entity.types.PsqlEnum;
import com.mikitjuk.advt.entity.types.UserRole;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
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
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "role")
    @Type(type = "pgsql_enum")
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<App> apps;
}
