
package io.entrance.model.json;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.HashMap;
import java.util.Map;

public class JsonEdge {

    private transient Edge edge;
    private String label;
    private Map<String, Object> properties = new HashMap<String, Object>();
    /**
     * The edge/relation comes from another vertex 'IN'to this vertex.
     */
    private JsonVertex inVertex;

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private JsonEdge() {

    }

    public JsonEdge(Edge edge, int inDepth, int outDepth) {
        this.edge = edge;
        Vertex inV = edge.getVertex(Direction.IN);
        inDepth = inDepth - 1;
        outDepth = outDepth - 1;
        inVertex = new JsonVertex(inV, inDepth, outDepth);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * Add or update the property.
     * 
     * @param key
     * @param value
     */
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    public JsonVertex getInVertex() {
        return inVertex;
    }

    public void setInVertex(JsonVertex inVertex) {
        this.inVertex = inVertex;
    }

}
