
package io.entrance.service.graph;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

import org.vertx.java.core.json.JsonArray;

import io.entrance.model.json.V;
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

    public V createVertex(Map<String, String> properties) {
        Vertex vertex = graph.addVertex(null);
        for (Entry<String, String> entry : properties.entrySet()) {
            vertex.setProperty(entry.getKey(), entry.getValue());
        }

        return new V(vertex);
    }

    public String createVertexJson(Map<String, String> properties) {
        V wrapper = createVertex(properties);
        return GSON.INSTANCE.gson().toJson(wrapper);
    }
    
    public List<V> allVertices() {
        List<V> vs = new ArrayList<V>();
        for (Vertex vertex : graph.getVertices()) {
            vs.add(new V(vertex));
        }
        
        return vs;
    }
    
    public String allVerticesJson() {
        List<V> vs = allVertices();
        return GSON.INSTANCE.gson().toJson(vs);
    }

}
