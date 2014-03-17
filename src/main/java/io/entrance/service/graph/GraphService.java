
package io.entrance.service.graph;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

import org.vertx.java.core.json.JsonArray;

import io.entrance.model.json.JsonVertex;
import io.entrance.service.graph.db.GraphDB;
import io.entrance.service.json.gson.GSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GraphService {

    private TitanGraph graph;

    public GraphService() {
        graph = GraphDB.INSTANCE.getGraph();
    }
    
    private void setAllProperties(Vertex vertex, Map<String, String> properties) {
        for (Entry<String, String> entry : properties.entrySet()) {
            vertex.setProperty(entry.getKey(), entry.getValue());
        }
    }
    
    public List<JsonVertex> allVertices() {
        List<JsonVertex> jsonVertices = new ArrayList<JsonVertex>();
        for (Vertex vertex : graph.getVertices()) {
            jsonVertices.add(new JsonVertex(vertex, 1, 1));
        }
        
        return jsonVertices;
    }
 
    public String allVerticesJson() {
        List<JsonVertex> jsonVertices = allVertices();
        return GSON.INSTANCE.gson().toJson(jsonVertices);
    }

    public JsonVertex createVertex(Map<String, String> properties) {
        Vertex vertex = graph.addVertex(null);
        setAllProperties(vertex, properties);
        graph.commit();

        return new JsonVertex(vertex, 1, 1);
    }

    public String createVertexJson(Map<String, String> properties) {
        JsonVertex wrapper = createVertex(properties);
        return GSON.INSTANCE.gson().toJson(wrapper);
    }
    
    public JsonVertex updateVertex(Object id, Map<String, String> properties) throws Exception {
        Vertex vertex = graph.getVertex(id);
        if (vertex == null) {
            throw new IllegalArgumentException(String.format("Vertex with id: %s can't be found", id));
        }      
        setAllProperties(vertex, properties);
        graph.commit();
        
        return new JsonVertex(vertex, 1, 1);
    }
    
    public String updateVertexJson(Object id, Map<String, String> properties) throws Exception {
        JsonVertex wrapper = updateVertex(id, properties);
        return GSON.INSTANCE.gson().toJson(wrapper);
    }

}
