package io.entrance.model;

import com.tinkerpop.blueprints.Edge;

/**
 * A wrapper around an edge. Describes the relationship between two nodes/vertices.
 * 
 * @author jan.prill
 *
 */
public class Relationship {

    private Edge edge;
    
    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private Relationship() {

    }
    
    public Relationship(Edge edge) {
        this.edge = edge;
    }

}
