
package io.entrance.service.graph.dsl;

import com.tinkerpop.blueprints.Edge;

import java.util.Map;

public class RelationBuilder {

    private Relation relation;
    private String label;
    private Map<String, Object> properties;

    // keeping a back reference to the node builder.
    private NodeBuilder nodeBuilder;

    public RelationBuilder(NodeBuilder nodeBuilder) {
        this.nodeBuilder = nodeBuilder;
    }

    public RelationBuilder as(String label) {
        this.label = label;
        return this;
    }

    public RelationBuilder with(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public RelationBuilder to(Node node) {
        Edge edge = nodeBuilder.getNode().getVertex().addEdge(label, node.getVertex());
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            edge.setProperty(entry.getKey(), entry.getValue());
        }
        relation = new Relation(edge);

        return this;
    }

    public Relation getRelation() {
        return relation;
    }

}
