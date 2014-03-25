
package io.entrance.service.graph.dsl;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import io.entrance.service.json.gson.GSON;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<Relation> out = new ArrayList<Relation>();

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
    
    public Node(Vertex vertex, Integer inDepth, Integer outDepth) {
        this(vertex);
        
        if (inDepth != null) {
            // TODO
        }
        if (outDepth != null) {
            for (Edge edge : vertex.getEdges(Direction.OUT)) {
                out.add(new Relation(edge));
            }
        }
    }

    @Transient
    public Vertex getVertex() {
        return vertex;
    }
    
    public String json() {
    	return GSON.INSTANCE.gson().toJson(this);
    }

}
