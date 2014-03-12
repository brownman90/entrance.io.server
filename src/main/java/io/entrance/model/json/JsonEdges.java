package io.entrance.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of edges.
 * 
 * @author jan.prill
 *
 */
public class JsonEdges {

    private List<JsonEdge> edges = new ArrayList<JsonEdge>();
    
    public JsonEdges() {
        
    }

    public List<JsonEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<JsonEdge> edges) {
        this.edges = edges;
    }
    
    public void addEdge(JsonEdge edge) {
        edges.add(edge);
    }

}
