package cz.zoubelu.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.*;

/**
 * Created by zoubas on 9.7.16.
 */
@JsonIdentityInfo(generator=JSOGGenerator.class)
@JsonIgnoreProperties({"application"})
@RelationshipEntity(type = "CONSUMES")
public class ConsumeRelationship {
    @GraphId
    private Long id;
    @Property
    private Long totalUsage;
    @StartNode
    private Application application;
    @EndNode
    private Method method;

    public ConsumeRelationship() {}

    public ConsumeRelationship(Application application, Method method) {
        this.application = application;
        this.method = method;
        this.totalUsage = 1L;
    }

    public Long getTotalUsage() {
        return totalUsage;
    }

    public void setTotalUsage(Long totalUsage) {
        this.totalUsage = totalUsage;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
