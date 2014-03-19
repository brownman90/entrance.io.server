
package io.entrance.service.graph.dsl;

import java.util.HashMap;
import java.util.Map;

public class TestDsl {

    public static void main(String[] args) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("Name", "Jan");
        properties.put("Heimat", "Hamburg");

        Node anotherNode = NodeBuilder.Node().create(properties).getNode();
        NodeBuilder.Node().create(properties).relate().as("comment").to(anotherNode);

    }

}
