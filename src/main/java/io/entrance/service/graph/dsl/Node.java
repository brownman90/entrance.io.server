
package io.entrance.service.graph.dsl;

import com.tinkerpop.blueprints.Vertex;

public class Node {

    private transient Vertex vertex;

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private Node() {

    }

    public Node(Vertex vertex) {
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

}
