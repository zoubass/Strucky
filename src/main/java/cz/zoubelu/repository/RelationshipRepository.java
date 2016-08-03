package cz.zoubelu.repository;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zoubas on 12.7.16.
 */
public interface RelationshipRepository extends GraphRepository<ConsumeRelationship> {

    @Query("MATCH (m:Method)<-[c:CONSUMES]-(a:Application) WHERE id(m)={mId} AND id(a)={appId} RETURN c")
    ConsumeRelationship findRelationship(@Param("appId") Application consumingApp, @Param("mId") Method consumedMethod);

    //TODO: opravit query
    @Query("START n=node(*) MATCH (n)-[r]->(m) RETURN n as application,r as rel,m as method")
    List<Map<String, Object>> getGraph();
}
