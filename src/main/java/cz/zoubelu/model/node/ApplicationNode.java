package cz.zoubelu.model.node;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by zoubas on 3.6.16.
 */
//@NodeEntity
public class ApplicationNode {

//    @GraphId
    private Long id;

//    @Property(name = "name")
    private String appName;

//    @Relationship(type = "PROVIDES", direction = Relationship.OUTGOING)
    private List<MethodNode> methods;


}
