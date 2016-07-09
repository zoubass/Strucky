package cz.zoubelu.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by zoubas on 3.6.16.
 */
@NodeEntity
public class Application {

    @GraphId
    private Long id;

    @Property(name = "name")
    private String appName;

    @Relationship(type = "PROVIDES", direction = Relationship.OUTGOING)
    private Set<Method> methods;

    public Application(){

    }
    public Application(String appName, Set<Method> methods) {
        this.appName = appName;
        this.methods = methods;
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

    public Set<Method> getMethods() {
        return methods;
    }

    public void setMethods(Set<Method> methods) {
        this.methods = methods;
    }
}
