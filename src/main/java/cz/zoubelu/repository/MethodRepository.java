package cz.zoubelu.repository;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.Method;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by zoubas on 12.7.16.
 */

public interface MethodRepository extends GraphRepository<Method> {
//    <-[r:PROVIDES]-(a:Application)
    @Query("MATCH (m:Method)<-[:PROVIDES]-(a:Application) WHERE m.name={mName} AND m.version={mVersion} AND id(a)={id} RETURN m")
    Method findProvidedMethodOfApplication(@Param("id") Application providingApp, @Param("mName") String methodName, @Param("mVersion") Integer methodVersion);
}
