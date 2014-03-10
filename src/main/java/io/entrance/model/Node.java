
package io.entrance.model;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import org.javatuples.Pair;

import io.entrance.service.graph.db.GraphDB;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class Node {

    private Vertex vertex;

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private Node() {

    }

    public Node(Vertex vertex) {
        this.vertex = vertex;
    }

    /**
     * Adds an existing vertex to this node and defines the relationship between
     * the two.
     * 
     * @param relationship - properties describing the relationship between both
     *            vertices. Must contain the label.
     * @param inVertex - the vertex that is added to this node.
     * @return the edge that has been created for the relation.
     */
    public Edge add(Map<String, Object> relationship, Vertex inVertex) throws Exception {
        validateRelationshipProperties(relationship);

        Edge edge = vertex.addEdge(relationship.remove("label").toString(), inVertex);
        for (Entry<String, Object> entry : relationship.entrySet()) {
            edge.setProperty(entry.getKey(), entry.getValue());
        }

        return edge;
    }

    private void validateRelationshipProperties(Map<String, Object> relationship) {
        if (!relationship.keySet().contains("label")) {
            throw new IllegalStateException("Label of the relationship is undefined");
        }
    }

    public Pair<Edge, Node> add(Map<String, Object> relationship, Map<String, Object> properties) throws Exception {
        validateRelationshipProperties(relationship);
        
        Node inNode = Node.create(properties);
        Edge edge = this.add(relationship, inNode.vertex);
        
        // TODO: return the relationship as well
        return Pair.with(edge, inNode);
    }

    /**
     * Creates a vertex and returns an instance of this class as a wrapper
     * around the vertex.
     * 
     * @param properties
     * @return  
     * @throws Exception
     */
    public static Node create(Map<String, Object> properties) throws Exception {
        Vertex vertex = GraphDB.INSTANCE.getGraph().addVertex(null);
        for (Entry<String, Object> entry : properties.entrySet()) {
            vertex.setProperty(entry.getKey(), entry.getValue());
        }

        // set timestamps
        long epoch = new Date().getTime();
        vertex.setProperty("created_at", epoch);
        vertex.setProperty("updated_at", epoch);

        return new Node(vertex);
    }
}
