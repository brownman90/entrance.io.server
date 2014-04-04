package io.entrance.model.json;

import java.util.Map;

public class JsonDTO {

   /**
    * The type of data transported. Needs to be set.
    */
   private String type;
   /**
    * Id of the out vertex.
    */
   private String out;
   /**
    * Relationship properties.
    */
   private Map<String, Object> relProperties;
   /**
    * Properties of an outgoing vertex.
    */
   private Map<String, Object> outProperties;
   /**
    * Id of an in vertex.
    */
   private String in;
   /**
    * Properties of an incoming vertex.
    */
   private Map<String, Object> inProperties;

   public JsonDTO() {

   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("JsonDTO [type=");
      builder.append(type);
      builder.append(", out=");
      builder.append(out);
      builder.append(", relProperties=");
      builder.append(relProperties);
      builder.append(", outProperties=");
      builder.append(outProperties);
      builder.append(", in=");
      builder.append(in);
      builder.append(", inProperties=");
      builder.append(inProperties);
      builder.append("]");
      return builder.toString();
   }

   public String getType() {
      return type;
   }

   public String getOut() {
      return out;
   }

   public Map<String, Object> getRelProperties() {
      return relProperties;
   }

   public Map<String, Object> getOutProperties() {
      return outProperties;
   }

   public String getIn() {
      return in;
   }

   public Map<String, Object> getInProperties() {
      return inProperties;
   }

}
