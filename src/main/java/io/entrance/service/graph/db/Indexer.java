package io.entrance.service.graph.db;

import java.util.HashSet;
import java.util.Set;

import com.tinkerpop.blueprints.Vertex;

public enum Indexer {
	INSTANCE;

	private Set<String> vertexCentricIndices;

	private Indexer() {
		fillVertexCentricIndices();
	}

	private void fillVertexCentricIndices() {
		if (vertexCentricIndices == null) {
			vertexCentricIndices = GraphDB.INSTANCE.graph().getIndexedKeys(
					Vertex.class);
		}
		if (vertexCentricIndices == null) {
			vertexCentricIndices = new HashSet<String>();
		}
	}
	
	public boolean createKeys(Set<String> keys) {
		boolean keysCreated = false;

		fillVertexCentricIndices();
		
		if (!keys.removeAll(vertexCentricIndices)) {
			for (String key : keys) {
				GraphDB.INSTANCE.graph().makeKey(key);
				vertexCentricIndices.add(key);
				keysCreated = true;
			}
		}
		
		return keysCreated;
	}
}
