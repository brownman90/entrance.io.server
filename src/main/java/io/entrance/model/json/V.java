
package io.entrance.model.json;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper for a @see {@link Vertex}. A 'V' is a vertex or,
 * as other graph databases call them, a node. A vertex consists
 * of an id and a set of properties. The 'V' is mostly a data
 * transfer object for @see {@link Vertex} that only transports
 * the data necessary for the frontend in JSON format.
 * 
 * @author jan.prill
 *
 */
public class V {

    private transient Vertex vertex;
    private Map<String, Object> properties;
    private Object id;
    private Map<Object, String> outVs = new HashMap<Object, String>();
    private Map<Object, String> inVs = new HashMap<Object, String>();

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private V() {

    }

    /**
     * Default constructor.
     * 
     * @param vertex
     */
    public V(Vertex vertex) {
        this.vertex = vertex;
        this.id = vertex.getId();
        for (String key : vertex.getPropertyKeys()) {
            addProperty(key, vertex.getProperty(key).toString());
        }
        
        for (Edge edge : vertex.getEdges(Direction.OUT)) {
            String label = edge.getLabel();
            Vertex inVertex = edge.getVertex(Direction.IN);
            outVs.put(inVertex.getId(), label);
        }
        for (Edge edge : vertex.getEdges(Direction.IN)) {
            String label = edge.getLabel();
            Vertex inVertex = edge.getVertex(Direction.OUT);
            inVs.put(inVertex.getId(), label);
        }
    }
    
    public void addProperty(String key, Object value) {
        if (properties == null) {
            properties = new HashMap<String, Object>();
        }
        
        properties.put(key, value);
    }

}
