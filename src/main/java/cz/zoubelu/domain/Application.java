package cz.zoubelu.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;
import java.util.Set;

/**
 * Created by zoubas on 3.6.16.
 */
@JsonIdentityInfo(generator=JSOGGenerator.class)
@NodeEntity
public class Application {

    @GraphId
    private Long id;

    @Property(name = "name")
    private String appName;

    @Relationship(type = "PROVIDES", direction = Relationship.OUTGOING)
    private Set<Method> providedMethods;

    @Relationship(type = "CONSUMES", direction = Relationship.OUTGOING)
    private List<ConsumeRelationship> consumeRelationship;

    public Application(){

    }
    public Application(String appName, Set<Method> providedMethods) {
        this.appName = appName;
        this.providedMethods = providedMethods;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Set<Method> getProvidedMethods() {
        return providedMethods;
    }

    public void setProvidedMethods(Set<Method> providedMethods) {
        this.providedMethods = providedMethods;
    }

    public List<ConsumeRelationship> getConsumeRelationship() {
        return consumeRelationship;
    }

    public void setConsumeRelationship(List<ConsumeRelationship> consumeRelationship) {
        this.consumeRelationship = consumeRelationship;
    }
}
