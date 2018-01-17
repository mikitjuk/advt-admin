package com.mikitjuk.advt.domain;

import com.mikitjuk.advt.domain.types.PsqlEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
