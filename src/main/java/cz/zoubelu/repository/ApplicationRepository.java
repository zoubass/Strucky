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
 * Created by zoubas on 25.6.16.
 */
public interface ApplicationRepository extends GraphRepository<Application> {
    @Query("MATCH (a: Application) WHERE a.name={0} RETURN a")
    Application findByName(String name);

    @Query("MATCH (a: Application) WHERE a.systemId={0} RETURN a")
    Application findBySystemId(Integer systemId);
}
