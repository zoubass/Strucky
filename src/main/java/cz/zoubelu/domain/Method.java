package cz.zoubelu.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Created by zoubas on 3.6.16.
 */
@NodeEntity
public class Method {
    @GraphId
    private Long id;

    public Method() {

    }

    public Method(String name, Integer version) {
        this.name = name;
        this.version = version;
    }

    @Property(name="name")
    private String name;

    @Property(name = "version")
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
