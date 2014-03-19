
package io.entrance.service.graph.dsl;

import com.tinkerpop.blueprints.Edge;

public class Relation {

    private transient Edge edge;

    @SuppressWarnings("unused")
    private Relation() {

    }

    public Relation(Edge edge) {
        this.edge = edge;
    }

    public Edge getEdge() {
        return edge;
    }

}
