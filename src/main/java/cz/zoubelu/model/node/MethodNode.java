package cz.zoubelu.model.node;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Created by zoubas on 3.6.16.
 */
@NodeEntity
public class MethodNode {
    @GraphId
    private Long id;

    @Property(name = "version")
    private Integer version;


}
