
package io.entrance.service.graph.dsl;

import com.tinkerpop.blueprints.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.entrance.service.graph.db.GraphDB;

/**
 * Find, create, update or delete nodes.
 * 
 * @author jan.prill
 *
 */
public class Graph {

    /**
     * The current root scope.
     */
    private Node node;
    
    /**
     * Hidden default constructor.
     */
    public Graph() {
        
    }

    public Graph(Vertex vertex) {
        node = new Node(vertex);
    }

    // ok. you like to create a graph?
    // let's start with a new node...
    public static Graph Node() {
        return new Graph();
    }
    
    // ok. you've already got a node?
    // than let's add on to it...
    public static Graph Node(Object id) {
        Vertex vertex = GraphDB.INSTANCE.getGraph().getVertex(id);
        return new Graph(vertex);
    }
    
    public Graph update(Map<String, Object> properties) {
        setProperties(properties);
        return this;
    }
    
    public Graph create(Map<String, Object> properties) {
        node = new Node(GraphDB.INSTANCE.getGraph().addVertex(null));
        setProperties(properties);
        return this;
    }
    
    public Graph find() {
        return this;
    }
    
    /**
     * find().all() => finds all vertices.
     * 
     * @return
     */
    public GenericList<Node> all() {
        GenericList<Node> nodes = new GenericList<Node>();
        for (Vertex vertex : GraphDB.INSTANCE.getGraph().getVertices()) {
            nodes.addElement(new Node(vertex));
        }
        
        return nodes;
    }
    
    public Graph where(String condition) {
        // TODO: where()-parser
        return this;
    }

    private void setProperties(Map<String, Object> properties) {
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            node.getVertex().setProperty(entry.getKey(), entry.getValue());
        }
    }
    
    public RelationBuilder rel(String label) {
        RelationBuilder relationBuilder = new RelationBuilder(this, label);
        return relationBuilder;
    }

    public Node getNode() {
        return node;
    }
    
    

}
