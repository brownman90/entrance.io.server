
package io.entrance.service.graph.dsl;

import java.util.HashMap;
import java.util.Map;

public class TestDsl {

    public static void main(String[] args) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("Name", "Jan");
        properties.put("Heimat", "Hamburg");

        Node anotherNode = Graph.Node().create(properties).getNode();
        Graph.Node().create(properties).rel("comment").to(anotherNode);
        // With relationship-properties. With an alias of "on" (instead of "to")
        Graph.Node().create(properties).rel("comment").with(properties).on(anotherNode);
        
        // create a relationship between two not yet existing nodes
        Graph.Node().create(properties).rel("link").to(properties);
        // do the same with relationship-properties.
        Graph.Node().create(properties).rel("link").with(properties).to(properties);
        

    }

}
