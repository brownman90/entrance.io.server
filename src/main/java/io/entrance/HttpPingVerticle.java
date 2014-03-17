
package io.entrance;

import io.entrance.model.Node;
import io.entrance.model.Relationship;
import io.entrance.service.eventbus.ServerHook;
import io.entrance.service.graph.RelationService;
import io.entrance.service.graph.GraphService;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.platform.Verticle;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpPingVerticle extends Verticle {

    public void start() {

        HttpServer server = vertx.createHttpServer();

        RouteMatcher rm = new RouteMatcher();

        rm.get("/all", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().end(new GraphService().readAllVerticesJson());
            }
        });

        rm.get("/create", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                Map<String, String> props = new HashMap<String, String>();
                props.put("Hallo", "Welt");
                props.put("wie", "geht");
                props.put("es", "dir");
                req.response().end("Hello entrance.io " + new GraphService().createVertexJson(props));
            }
        });

        server = server.requestHandler(rm);

        // sock server
        JsonArray permitted = new JsonArray();
        // TODO: Security: Currently lets everything through.
        permitted.add(new JsonObject()); 

        ServerHook hook = new ServerHook(container.logger());

        SockJSServer sockJSServer = vertx.createSockJSServer(server);
        // sockJSServer.setHook(hook);
        sockJSServer.bridge(new JsonObject().putString("prefix", "/eventbus"), permitted, permitted);

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
                msg.reply(new JsonObject().putString("status", "ok").putArray("results", new JsonArray(new GraphService().readAllVerticesJson())));
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
        
        // Register activity handler. An activity would be 'commenting' for example.
        eb.registerHandler("io.entrance.activity", new Handler<Message<JsonObject>>() {

            @Override
            public void handle(Message<JsonObject> msg) {
                Map<String, Object> relProps = new HashMap<String, Object>();
                relProps.put("todo", "cope with rel-props");
                Map<String, Object> properties = extractProperties(msg);
                Object id = properties.remove("id");
                try {
                    // TODO: we need to return json right away
                    Pair<Relationship, Node> thread = new RelationService(id).comment(relProps, properties);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        });
        

        server.listen(8888);
        container.logger().info("Webserver started, listening on port: 8888 !!");
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
