
package io.entrance.service.graph.dsl;

import com.tinkerpop.blueprints.Vertex;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for a blueprint vertex. Acts as a DTO. Might be
 * serialized to JSON.
 * 
 * @author jan.prill
 *
 */
public class Node {

    private transient Vertex vertex;
    private Map<String, Object> properties = new HashMap<String, Object>();

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private Node() {

    }

    public Node(Vertex vertex) {
        this.vertex = vertex;
        // transfer the properties for DTO purposes:
        for (String key : vertex.getPropertyKeys()) {
            Object value = vertex.getProperty(key);
            properties.put(key, value);
        }
    }

    @Transient
    public Vertex getVertex() {
        return vertex;
    }

}
