
package io.entrance.service.graph.dsl;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;

import java.util.HashMap;
import java.util.Map;

public class Relation {

    private transient Edge edge;
    private Map<String, Object> properties = new HashMap<String, Object>();
    private Node inNode;

    @SuppressWarnings("unused")
    private Relation() {

    }

    public Relation(Edge edge) {
        this.edge = edge;
        properties.put("label", edge.getLabel());
        for (String key : edge.getPropertyKeys()) {
            Object value = edge.getProperty(key);
            properties.put(key, value);
        }
        
        // this edge directs to the following inNode
        inNode = new Node(edge.getVertex(Direction.IN));
    }

    public Edge getEdge() {
        return edge;
    }

}
