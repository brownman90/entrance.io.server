package io.entrance.service.graph.dsl;

import io.entrance.service.graph.db.GraphDB;

import java.util.Map;

import com.tinkerpop.blueprints.Edge;

public class RelationBuilder {

   private Relation relation;
   private String label;
   private Map<String, Object> properties;

   // keeping a back reference to the graph.
   private Graph graph;

   public RelationBuilder(Graph graph, String label) {
      this.graph = graph;
      this.label = label;
   }

   public RelationBuilder with(Map<String, Object> properties) {
      this.properties = properties;
      return this;
   }

   public RelationBuilder to(Map<String, Object> properties) {
      Node node = Graph.Node().create(properties).getNode();
      return to(node);
   }

   public RelationBuilder to(Node node) {
      Edge edge = graph.getNode().getVertex().addEdge(label, node.getVertex());
      if (properties != null && !properties.isEmpty()) {
         for (Map.Entry<String, Object> entry : properties.entrySet()) {
            edge.setProperty(entry.getKey(), entry.getValue());
         }
      }
      relation = new Relation(edge, node.getVertex().getId());

      GraphDB.INSTANCE.graph().commit();
      return this;
   }

   /**
    * Alias for @see {@link RelationBuilder#to(Node)}.
    * 
    * @param node
    * @return
    */
   public RelationBuilder on(Node node) {
      return to(node);
   }

   /**
    * Alias for @see {@link RelationBuilder#to(Map)}
    * 
    * @param properties
    * @return
    */
   public RelationBuilder on(Map<String, Object> properties) {
      return to(properties);
   }

   public Relation getRelation() {
      return relation;
   }

}
