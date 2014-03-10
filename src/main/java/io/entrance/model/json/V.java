
package io.entrance.model.json;

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

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private V() {

    }

    public V(Vertex vertex) {
        this.vertex = vertex;
        this.id = vertex.getId();
        for (String key : vertex.getPropertyKeys()) {
            addProperty(key, vertex.getProperty(key).toString());
        }
    }
    
    public void addProperty(String key, Object value) {
        if (properties == null) {
            properties = new HashMap<String, Object>();
        }
        
        properties.put(key, value);
    }

}
