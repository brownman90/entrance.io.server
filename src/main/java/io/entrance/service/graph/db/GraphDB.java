
package io.entrance.service.graph.db;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

public enum GraphDB {
    INSTANCE; 

    private TitanGraph graph = null; 

    private static TitanGraph create() {
        BaseConfiguration config = new BaseConfiguration();
        Configuration storage = config.subset(GraphDatabaseConfiguration.STORAGE_NAMESPACE);
        storage.setProperty(GraphDatabaseConfiguration.STORAGE_BACKEND_KEY, "cassandra");

        TitanGraph graph = TitanFactory.open(config);
        return graph;
    }

    public TitanGraph getGraph() {
        createGraph();
        return graph; 
    }

    private void createGraph() {
        if (graph == null) {
            graph = GraphDB.create();
        }
    }

}
