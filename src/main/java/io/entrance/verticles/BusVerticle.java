
package io.entrance.verticles;

import io.entrance.model.Node;
import io.entrance.model.Relationship;
import io.entrance.service.graph.CommentService;
import io.entrance.service.graph.GraphService;

import org.javatuples.Triplet;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BusVerticle extends Verticle {

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        JsonObject config = new JsonObject().putString("prefix", "/eventbus");
        JsonArray permissions = setupPermissions(); 
        vertx.createSockJSServer(server).bridge(config, permissions, permissions);
        setupEventBus();

        server.listen(8080);
    }

    private JsonArray setupPermissions() {
        JsonArray permissions = new JsonArray();
        permissions.add(new JsonObject());
        return permissions;
    }

    private void setupEventBus() {
        EventBus eb = vertx.eventBus();

        vertx.setPeriodic(5000, new Handler<Long>() {
            @Override
            public void handle(Long timerID) {
                vertx.eventBus().publish("news-feed", "more news!");
            }
        });

        // Register Handler 1
        eb.registerHandler("vertx.mongopersistor", new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> msg) {
                vertx.eventBus().publish("news-feed", "so das kommt jetzt aus dem mongopersistor.");
                container.logger().info("Handler 1 received: " + msg.toString());
                msg.reply(new JsonObject().putString("status", "ok").putArray("results", new JsonArray(new GraphService().allVerticesJson())));
            }
        });

        // Register create vertex handler
        eb.registerHandler("io.entrance.create_vertex", new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> msg) {
                Map<String, String> properties = new HashMap<String, String>();
                properties.put("Hallo", "Yeah! Erzeugt.");
                for (Entry<String, Object> entry : msg.body().toMap().entrySet()) {
                    properties.put(entry.getKey(), entry.getValue().toString());
                }
                msg.reply(new JsonObject().putString("status", "ok").putObject("result", new JsonObject(new GraphService().createVertexJson(properties))));
            }
        });

        // Register activity handler. An activity would be 'commenting' for
        // example.
        eb.registerHandler("io.entrance.activity", new Handler<Message<JsonObject>>() {

            @Override
            public void handle(Message<JsonObject> msg) {
                Map<String, Object> relProps = new HashMap<String, Object>();
                relProps.put("todo", "cope with rel-props");
                Map<String, Object> properties = extractProperties(msg);
                Object id = properties.remove("id");
                try {
                    // TODO: we need to return json right away
                    Triplet<Node, Relationship, Node> thread = new CommentService(id).comment(relProps, properties);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * Extract all properties from a message transferred over the bus.
     * 
     * @param msg
     * @return
     */
    private Map<String, Object> extractProperties(Message<JsonObject> msg) {
        Map<String, Object> properties = new HashMap<String, Object>();
        for (Entry<String, Object> entry : msg.body().toMap().entrySet()) {
            properties.put(entry.getKey(), entry.getValue().toString());
        }

        return properties;
    }

}
