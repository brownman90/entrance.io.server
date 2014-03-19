
package io.entrance.service.graph.dsl;

import com.tinkerpop.blueprints.Vertex;

import java.util.Map;

import io.entrance.service.graph.db.GraphDB;

/**
 * Find or create nodes.
 * 
 * @author jan.prill
 *
 */
public class NodeBuilder {

    private Node node;
    
    /**
     * Hidden default constructor.
     */
    private NodeBuilder() {
        
    }

    private NodeBuilder(Vertex vertex) {
        node = new Node(vertex);
    }

    // ok. you like to create a graph?
    // let's start with a new node...
    public static NodeBuilder Node() {
        return new NodeBuilder();
    }
    
    // ok. you've already got a node?
    // than let's add on to it...
    public static NodeBuilder Node(Object id) {
        Vertex vertex = GraphDB.INSTANCE.getGraph().getVertex(id);
        return new NodeBuilder(vertex);
    }
    
    public NodeBuilder update(Map<String, Object> properties) {
        setProperties(properties);
        return this;
    }
    
    public NodeBuilder create(Map<String, Object> properties) {
        node = new Node(GraphDB.INSTANCE.getGraph().addVertex(null));
        setProperties(properties);
        return this;
    }

    private void setProperties(Map<String, Object> properties) {
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            node.getVertex().setProperty(entry.getKey(), entry.getValue());
        }
    }
    
    public RelationBuilder relate() {
        RelationBuilder relationBuilder = new RelationBuilder(this);
        return relationBuilder;
    }

    public Node getNode() {
        return node;
    }
    
    

}
