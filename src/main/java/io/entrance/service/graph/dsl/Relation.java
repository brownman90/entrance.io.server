package io.entrance.service.graph.dsl;

import io.entrance.service.json.gson.GSON;

import java.util.HashMap;
import java.util.Map;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;

public class Relation {

   private transient Edge edge;
   private Map<String, Object> properties = new HashMap<String, Object>();
   private Node parentNode;
   private Node childNode;

   @SuppressWarnings("unused")
   private Relation() {

   }

   public Relation(Edge edge) {
      this.edge = edge;
      properties.put("label", edge.getLabel());
      for (String key : edge.getPropertyKeys()) {
         Object value = edge.getProperty(key);
         properties.put(key, value);
      }

      parentNode = new Node(edge.getVertex(Direction.IN));
      childNode = new Node(edge.getVertex(Direction.OUT));
   }

   public Edge getEdge() {
      return edge;
   }

   public String json() {
      return GSON.INSTANCE.gson().toJson(this);
   }

}
