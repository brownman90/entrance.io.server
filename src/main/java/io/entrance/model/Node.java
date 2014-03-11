
package io.entrance.model;

import com.tinkerpop.blueprints.Vertex;

import io.entrance.service.graph.db.GraphDB;

import org.javatuples.Pair;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class Node {

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
     * Validates the relationship. Especially detects if a label is provided and
     * throws an error otherwise.
     * 
     * @param relProps
     */
    private void validateRelationshipProperties(Map<String, Object> relProps) throws Exception {
        if (!relProps.keySet().contains(Relationship.LABEL)) {
            throw new IllegalStateException("Label of the relationship is undefined");
        }
    }

    public Pair<Relationship, Node> add(Map<String, Object> relProps, Map<String, Object> nodeProps) throws Exception {
        validateRelationshipProperties(relProps);

        Node inNode = Node.create(nodeProps);
        Relationship relationship = this.add(relProps, inNode);

        return Pair.with(relationship, inNode);
    }

    /**
     * Adds an existing node to this one and defines the relationship between
     * the two. As @see {@link #add(Map, Vertex)} but accepting an inNode
     * instead of an inVertex.
     * 
     * @param relProps
     * @param inNode
     * @return the relationship between this node and the ingoing one.
     * @throws Exception
     */
    public Relationship add(Map<String, Object> relProps, Node inNode) throws Exception {
        Relationship relationship = add(relProps, inNode.getVertex());
        return relationship;
    }

    /**
     * Adds an existing vertex to this node and defines the relationship between
     * the two.
     * 
     * @param relProps - properties describing the relationship between both
     *            vertices. Must contain the label.
     * @param inVertex - the vertex that is added to this node.
     * @return the edge that has been created for the relation.
     */
    public Relationship add(Map<String, Object> relProps, Vertex inVertex) throws Exception {
        validateRelationshipProperties(relProps);

        Relationship relationship = new Relationship(this.vertex, inVertex, relProps);
        return relationship;
    }

    public Vertex getVertex() {
        return vertex;
    }

}
