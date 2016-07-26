package cz.zoubelu.repository;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by zoubas on 12.7.16.
 */
public interface RelationshipRepository extends GraphRepository<ConsumeRelationship> {

    @Query("MATCH (m:Method)<-[c:CONSUMES]-(a:Application) WHERE id(m)={mId} AND id(a)={appId} RETURN c")
    ConsumeRelationship findRelationship(@Param("appId") Application consumingApp, @Param("mId") Method consumedMethod);
}
