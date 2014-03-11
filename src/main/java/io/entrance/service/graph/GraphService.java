
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

    public JsonVertex createVertex(Map<String, String> properties) {
        Vertex vertex = graph.addVertex(null);
        for (Entry<String, String> entry : properties.entrySet()) {
            vertex.setProperty(entry.getKey(), entry.getValue());
        }
        
        graph.commit();

        return new JsonVertex(vertex);
    }

    public String createVertexJson(Map<String, String> properties) {
        JsonVertex wrapper = createVertex(properties);
        return GSON.INSTANCE.gson().toJson(wrapper);
    }
    
    public List<JsonVertex> allVertices() {
        List<JsonVertex> jsonVertexs = new ArrayList<JsonVertex>();
        for (Vertex vertex : graph.getVertices()) {
            jsonVertexs.add(new JsonVertex(vertex));
        }
        
        return jsonVertexs;
    }
    
    public String allVerticesJson() {
        List<JsonVertex> jsonVertexs = allVertices();
        return GSON.INSTANCE.gson().toJson(jsonVertexs);
    }

}
