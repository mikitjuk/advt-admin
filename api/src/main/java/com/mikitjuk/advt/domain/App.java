package com.mikitjuk.advt.domain;

import com.mikitjuk.advt.domain.types.PsqlEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apps")
@TypeDef(name = "pgsql_enum", typeClass = PsqlEnum.class)
@Entity
public class App {

    public static final String USER = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apps_id_seq")
    @SequenceGenerator(name = "apps_id_seq", sequenceName = "apps_id_seq", allocationSize = 1)
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "type")
    @Type(type = "pgsql_enum")
    private AppType type;

    @ElementCollection(targetClass = ContentType.class)
    @CollectionTable(name = "app_content", joinColumns = @JoinColumn(name = "app_id"))
    @Column(name = "content")
    @Type(type = "pgsql_enum")
    private Set<ContentType> contentTypes;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


//    @Column(name = "user_id", insertable = false, updatable = false)
//    private Integer userId;

}
