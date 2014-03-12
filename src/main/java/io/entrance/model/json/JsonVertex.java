
package io.entrance.model.json;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import io.entrance.model.Node;
import io.entrance.service.json.gson.GSON;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for a @see {@link Vertex}. A 'JsonVertex' is a vertex or, as other
 * graph databases call them, a node. A vertex consists of an id and a set of
 * properties. The 'JsonVertex' is mostly a data transfer object for @see
 * {@link Vertex} that only transports the data necessary for the frontend in
 * JSON format.
 * 
 * @author jan.prill
 */
public class JsonVertex {

    private Object id;
    private Map<Object, String> inVs = new HashMap<Object, String>();
    private Map<Object, String> outVs = new HashMap<Object, String>();
    private Map<String, Object> properties = new HashMap<String, Object>();
    private transient Vertex vertex;
    private Map<String, JsonEdges> outgoing = new HashMap<String, JsonEdges>();

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private JsonVertex() {

    }

    /**
     * Constructor taking a node, but just transferring to @see
     * {@link #JsonVertex()} afterwards with the wrapped vertex of the node.
     * 
     * @param node
     */
    public JsonVertex(Node node, int inDepth, int outDepth) {
        this(node.getVertex(), inDepth, outDepth);
    }

    /**
     * Default constructor.
     * 
     * @param vertex
     */
    public JsonVertex(Vertex vertex, int inDepth, int outDepth) {
        this.vertex = vertex;
        this.id = vertex.getId();
        for (String key : vertex.getPropertyKeys()) {
            setProperty(key, vertex.getProperty(key).toString());
        }

        if (outDepth > 0) {
            for (Edge edge : vertex.getEdges(Direction.OUT)) {
                String label = edge.getLabel();
                Vertex inVertex = edge.getVertex(Direction.IN);
                outVs.put(inVertex.getId(), label);
                /** populate @see {@link outgoing} */
                JsonEdges jsonEdges = null;
                if ((jsonEdges = outgoing.get(label)) != null) {
                    jsonEdges.addEdge(new JsonEdge(edge, inDepth, outDepth));
                } else {
                    jsonEdges = new JsonEdges();
                    jsonEdges.addEdge(new JsonEdge(edge, inDepth, outDepth));
                    outgoing.put(label, jsonEdges);
                }
            }
        }
        if (inDepth > 0) {
            for (Edge edge : vertex.getEdges(Direction.IN)) {
                String label = edge.getLabel();
                Vertex inVertex = edge.getVertex(Direction.OUT);
                inVs.put(inVertex.getId(), label);
            }
        }
    }

    public Object getId() {
        return id;
    }

    public Map<Object, String> getInVs() {
        return inVs;
    }

    public Map<Object, String> getOutVs() {
        return outVs;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Transient
    public Vertex getVertex() {
        return vertex;
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    public String toJson() {
        return GSON.INSTANCE.gson().toJson(this);
    }

}
