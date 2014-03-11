
package io.entrance.model;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import io.entrance.service.graph.db.GraphDB;

import java.util.Map;
import java.util.Map.Entry;

/**
 * A wrapper around an edge. Describes the relationship between two
 * nodes/vertices.
 * 
 * @author jan.prill
 */
public class Relationship {
    
    public static final String LABEL = "label";

    private Edge edge;

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private Relationship() {

    }

    /**
     * Constructs a relationship from an existing edge.
     * 
     * @param edge
     */
    public Relationship(Edge edge) {
        this.edge = edge;
    }

    /**
     * Constructs a relationship between the outVertex and the inVertex with the specified
     * map of properties.
     * 
     * @param outVertex
     * @param inVertex
     * @param properties
     * @throws Exception
     */
    public Relationship(Vertex outVertex, Vertex inVertex, Map<String, Object> properties) throws Exception {
       if (!properties.containsKey("label")) {
           throw new IllegalStateException("There's no label set in the relationship properties.");
       }
       
       Edge edge = GraphDB.INSTANCE.getGraph().addEdge(null, outVertex, inVertex, properties.get(LABEL).toString());
       for (Entry<String, Object> entry : properties.entrySet()) {
           edge.setProperty(entry.getKey(), entry.getValue());
       }
       this.edge = edge;
    }

    public Edge getEdge() {
        return edge;
    }

}
