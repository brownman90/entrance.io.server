
package io.entrance.service.graph;

import io.entrance.model.Node;
import io.entrance.model.Relationship;
import io.entrance.service.graph.db.GraphDB;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Map;

/**
 * Commenting is an important part of social media interaction. This service
 * provides the functionality to comment on every kind of node/vertex possible.
 * 
 * @author jan.prill
 */
public class CommentService {

    private static final String VERB_COMMENT = "comment";

    private Node node;

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private CommentService() {

    }

    /**
     * Default constructor. Takes a node as the mandatory object, the commenting
     * should happen on.
     * 
     * @param node
     */
    public CommentService(Node node) {
        this.node = node;
    }
    
    /**
     * Constructor. Takes the id of a node that should be commented upon.
     * 
     * @param id
     * @throws Exception
     */
    public CommentService(Object id) throws Exception {
        this.node = Node.find(id);
    }

    /**
     * Comment on an outgoing node by providing relationship-properties, as well
     * as comment properties. This is transactional and committed at the end of
     * the transaction.
     * 
     * @param outNode - the node the comment happens upon.
     * @param relProps - the properties of the commenting relationship that
     *            should be established.
     * @param commentProps - the properties of the comment itself. Especially
     *            it's content and metadata.
     * @return a triplet consisting of the outgoing node, the established
     *         comment-relationship and the comment node itself.
     * @throws Exception
     */
    public Triplet<Node, Relationship, Node> comment(Map<String, Object> relProps, Map<String, Object> commentProps) throws Exception {
        // make sure that the label is right
        relProps.put(Relationship.LABEL, VERB_COMMENT);
        // add the comment
        Pair<Relationship, Node> relationshipAndComment = node.add(relProps, commentProps);
        // commit
        GraphDB.INSTANCE.getGraph().commit();

        return Triplet.with(node, relationshipAndComment.getValue0(), relationshipAndComment.getValue1());
    }

}
