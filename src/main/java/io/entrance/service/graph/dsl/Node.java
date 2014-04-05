package io.entrance.service.graph.dsl;

import io.entrance.service.json.gson.GSON;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * Wrapper for a blueprint vertex. Acts as a DTO. Might be serialized to JSON.
 * 
 * @author jan.prill
 *
 */
public class Node {

   private transient Vertex vertex;
   private Object id;
   private Map<String, Object> properties = new HashMap<String, Object>();
   private List<Relation> childRelations = new ArrayList<Relation>();
   private List<Relation> parentRelations = new ArrayList<Relation>();

   /**
    * Hidden default constructor.
    */
   @SuppressWarnings("unused")
   private Node() {

   }

   public Node(Vertex vertex) {
      this.vertex = vertex;
      this.id = vertex.getId();
      // transfer the properties for DTO purposes:
      for (String key : vertex.getPropertyKeys()) {
         Object value = vertex.getProperty(key);
         properties.put(key, value);
      }
   }

   public Node(Vertex vertex, Integer parentDepth, Integer childDepth) {
      this(vertex);

      if (parentDepth > 0) {
         for (Edge edge : vertex.getEdges(Direction.OUT)) {
            parentRelations.add(new Relation(edge, vertex.getId()));
         }
      }
      if (childDepth > 0) {
         for (Edge edge : vertex.getEdges(Direction.IN)) {
            childRelations.add(new Relation(edge, vertex.getId()));
         }
      }
   }

   @Transient
   public Vertex getVertex() {
      return vertex;
   }

   public String json() {
      return GSON.INSTANCE.gson().toJson(this);
   }

}
