package cz.zoubelu.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by zoubas on 3.6.16.
 */
@JsonIdentityInfo(generator=JSOGGenerator.class)
@NodeEntity
public class Application {

    @GraphId
    private Long id;

    @Property(name = "systemId")
    private Integer systemId;

    @Property(name = "name")
    private String name;

    @Relationship(type = "PROVIDES", direction = Relationship.OUTGOING)
    private List<Method> providedMethods;

    @Relationship(type = "CONSUMES", direction = Relationship.OUTGOING)
    private List<ConsumeRelationship> consumeRelationship;

    public Application(){

    }
    public Application(String name, List<Method> providedMethods) {
        this.name = name;
        this.providedMethods = providedMethods;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConsumeRelationship> getConsumeRelationship() {
        return consumeRelationship;
    }

    public void setConsumeRelationship(List<ConsumeRelationship> consumeRelationship) {
        this.consumeRelationship = consumeRelationship;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public List<Method> getProvidedMethods() {
        return providedMethods;
    }

    public void setProvidedMethods(List<Method> providedMethods) {
        this.providedMethods = providedMethods;
    }
}
