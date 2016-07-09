package cz.zoubelu.repository;

import cz.zoubelu.domain.Application;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by zoubas on 25.6.16.
 */
public interface ApplicationRepository extends GraphRepository<Application> {
}
