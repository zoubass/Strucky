package cz.zoubelu.repository;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * Created by zoubas on 25.6.16.
 */
public interface ApplicationRepository extends GraphRepository<Application> {
    //FIXME: opravit cypher scripty
    @Query("MATCH (application:Application {name={0}}) RETURN application")
    Application findByName(String name);

    @Query("MATCH (m: Method {name:{1}, version:{2}})-[r:PROVIDES]<-(a: {0}) RETURN m")
    Method findMethodOfApplication(Application providingApp, String methodName, Integer methodVersion);

    @Query("start n=node({0}) MATCH r-[:CONSUMES]->(m: {1}) RETURN r")
    ConsumeRelationship findRelationship(Application consumingApp, Method consumedMethod);
}
