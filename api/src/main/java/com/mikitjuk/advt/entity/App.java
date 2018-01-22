package com.mikitjuk.advt.entity;

import com.mikitjuk.advt.entity.types.AppType;
import com.mikitjuk.advt.entity.types.ContentType;
import com.mikitjuk.advt.entity.types.PsqlEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ContentType.class)
    @CollectionTable(name = "app_content", joinColumns = @JoinColumn(name = "app_id"))
    @Column(name = "content")
    @Type(type = "pgsql_enum")
    private List<ContentType> contentTypes;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
