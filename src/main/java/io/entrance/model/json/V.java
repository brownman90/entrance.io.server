
package io.entrance.model.json;

import com.tinkerpop.blueprints.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class V {

    private transient Vertex vertex;
    private Map<String, String> properties;
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
    
    public void addProperty(String key, String value) {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        
        properties.put(key, value);
    }

}
