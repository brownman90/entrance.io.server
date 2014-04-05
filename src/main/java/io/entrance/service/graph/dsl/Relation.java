package io.entrance.service.graph.dsl;

import io.entrance.service.json.gson.GSON;

import java.util.HashMap;
import java.util.Map;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;

public class Relation {

   private transient Edge edge;
   private Map<String, Object> properties = new HashMap<String, Object>();
   private Object parentNode;
   private Object childNode;

   @SuppressWarnings("unused")
   private Relation() {

   }

   public Relation(Edge edge, Object id) {
      this.edge = edge;
      properties.put("label", edge.getLabel());
      for (String key : edge.getPropertyKeys()) {
         Object value = edge.getProperty(key);
         properties.put(key, value);
      }

      
      Node parent = new Node(edge.getVertex(Direction.IN));
      if (parent.getVertex().getId().equals(id)) {
         parentNode = parent.getVertex().getId();
      } else {
         parentNode = parent;
      }
      Node child = new Node(edge.getVertex(Direction.OUT));
      if (child.getVertex().getId().equals(id)) {
         childNode = child.getVertex().getId();
      } else {
         childNode = child;
      }
   }

   public Edge getEdge() {
      return edge;
   }

   public String json() {
      return GSON.INSTANCE.gson().toJson(this);
   }

}
