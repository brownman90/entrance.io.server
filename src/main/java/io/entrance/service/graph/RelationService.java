
package io.entrance.service.graph;

import io.entrance.model.Node;
import io.entrance.model.Relationship;
import io.entrance.service.activity.Verbs;
import io.entrance.service.graph.db.GraphDB;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Map;

/**
 * Commenting is an important part of social media interaction. This service
 * provides the functionality to comment on every kind of node/vertex possible. 
 * Other relating activities are able as well. Therefore this is 'RelationService' not
 * 'CommentService'.
 * 
 * @author jan.prill
 */
public class RelationService {

    private Node node;

    /**
     * Hidden default constructor.
     */
    @SuppressWarnings("unused")
    private RelationService() {

    }

    /**
     * Default constructor. Takes a node as the mandatory object, the commenting
     * should happen on.
     * 
     * @param node
     */
    public RelationService(Node node) {
        this.node = node;
    }
    
    /**
     * Constructor. Takes the id of a node that should be commented upon.
     * 
     * @param id
     * @throws Exception
     */
    public RelationService(Object id) throws Exception {
        this.node = Node.find(id);
    }

    /**
     * Comment on a specific node/vertex of information.
     * 
     * Comment on an outgoing node by providing relationship-properties, as well
     * as comment properties. This is transactional and committed at the end of
     * the transaction.
     * 
     * @param relProps - the properties of the commenting relationship that
     *            should be established.
     * @param commentProps - the properties of the comment itself. Especially
     *            it's content and metadata.
     * @return a pair consisting of the established
     *         comment-relationship and the comment node itself.
     * @throws Exception
     */
    public Pair<Relationship, Node> comment(Map<String, Object> relProps, Map<String, Object> commentProps) throws Exception {
        return createRelation(Verbs.COMMENT.label(), relProps, commentProps);
    }
    
    /**
     * Like a specific node/vertex of information.
     * 
     * @param relProps
     * @param likeProps
     * @return
     * @throws Exception
     */
    public Pair<Relationship, Node> like(Map<String, Object> relProps, Map<String, Object> likeProps) throws Exception {
        return createRelation(Verbs.LIKE.label(), relProps, likeProps);
    }

    /**
     * Helper method used by all relation activities.
     * 
     * @param activityVerb
     * @param relProps
     * @param targetNodeProps
     * @return
     * @throws Exception
     */
    private Pair<Relationship, Node> createRelation(String activityVerb, Map<String, Object> relProps, Map<String, Object> targetNodeProps) throws Exception {
        // make sure that the label is right
        relProps.put(Relationship.LABEL, activityVerb);
        // add the comment
        Pair<Relationship, Node> relationshipAndTargetNode = node.add(relProps, targetNodeProps);
        // commit
        GraphDB.INSTANCE.graph().commit();

        return relationshipAndTargetNode;
    }

}
