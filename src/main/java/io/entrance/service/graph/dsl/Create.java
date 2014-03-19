package io.entrance.service.graph.dsl;

public class Create {
    
    /**
     * DSL: Create.node() => constructs a node builder.
     * 
     * @return the constructed @see {@link Graph}
     */
    public static Graph node() {
        return new Graph();
    }

}
