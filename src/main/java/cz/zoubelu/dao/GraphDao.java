package cz.zoubelu.dao;

import cz.zoubelu.model.node.ApplicationNode;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by zoubas on 25.6.16.
 */
public interface GraphDao extends GraphRepository<ApplicationNode> {
}
