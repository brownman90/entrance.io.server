
package io.entrance.service.graph;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

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

    public Vertex readVertex(Object id) throws Exception {
        Vertex vertex = graph.getVertex(id);
        if (vertex == null) {
            throw new IllegalArgumentException(String.format("Vertex with id: %s can't be found", id));
        }

        return vertex;
    }

    public JsonVertex readVertexWrapper(Object id) throws Exception {
        JsonVertex jsonVertex = new JsonVertex(readVertex(id), 1, 1);
        return jsonVertex;
    }

    public String readVertexWrapperJson(Object id) {
        JsonVertex jsonVertex = null;
        try {
            jsonVertex = readVertexWrapper(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GSON.INSTANCE.gson().toJson(jsonVertex);
    }

    public List<JsonVertex> readAllVertices() {
        List<JsonVertex> jsonVertices = new ArrayList<JsonVertex>();
        for (Vertex vertex : graph.getVertices()) {
            jsonVertices.add(new JsonVertex(vertex, 1, 1));
        }

        return jsonVertices;
    }

    public String readAllVerticesJson() {
        List<JsonVertex> jsonVertices = readAllVertices();
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
        Vertex vertex = readVertex(id);
        setAllProperties(vertex, properties);
        graph.commit();

        return new JsonVertex(vertex, 1, 1);
    }

    public String updateVertexJson(Object id, Map<String, String> properties) throws Exception {
        JsonVertex wrapper = updateVertex(id, properties);
        return GSON.INSTANCE.gson().toJson(wrapper);
    }

}
