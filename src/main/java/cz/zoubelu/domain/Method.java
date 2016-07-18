package cz.zoubelu.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Created by zoubas on 3.6.16.
 */
@JsonIdentityInfo(generator=JSOGGenerator.class)
@NodeEntity
public class Method {
    @GraphId
    private Long id;

    @Property(name="name")
    private String name;

    @Property(name = "version")
    private Integer version;

    public Method() {

    }

    public Method(String name, Integer version) {
        this.name = name;
        this.version = version;
    }

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
