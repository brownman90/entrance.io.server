package io.entrance.verticles;

import io.entrance.model.json.JsonDTO;
import io.entrance.service.graph.GraphService;
import io.entrance.service.graph.dsl.Graph;
import io.entrance.service.json.gson.GSON;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import rx.Observable;
import rx.util.functions.Action1;

public class RestApiVerticle extends Verticle {

   @Override
   public void start() {
      super.start();

      HttpServer server = vertx.createHttpServer();
      RouteMatcher rm = setupRoutes();
      server.requestHandler(rm);
      server.listen(8899);
   }

   private RouteMatcher setupRoutes() {
      RouteMatcher matcher = new RouteMatcher();

      matcher.options("/api/v1/vertex", new Handler<HttpServerRequest>() {
         @Override
         public void handle(HttpServerRequest request) {
            request.response().headers().add("Access-Control-Allow-Origin", "*");
            request.response().headers().add("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS");
            request.response().headers().add("Access-Control-Allow-Headers", "Content-Type");
            request.response().end();
         }
      });

      /**
       * VERTICES. READ-operation. GET all vertices. They might be limited by
       * parameter :limit.
       */
      matcher.get("/api/v1/vertex", new Handler<HttpServerRequest>() {
         @Override
         public void handle(final HttpServerRequest request) {
            Observable.from(request).subscribe(new Action1<HttpServerRequest>() {
               @Override
               public void call(final HttpServerRequest request) {
                  // request.response().end(new
                  // GraphService().readAllVerticesJson());
                  request.response().headers().add("Access-Control-Allow-Origin", "*");

                  request.response().end(new Graph().find().all().json());
               }
            });
         }
      });

      matcher.get("/api/v1/vertex/:id", new Handler<HttpServerRequest>() {
         @Override
         public void handle(final HttpServerRequest request) {
            Observable.from(request).subscribe(new Action1<HttpServerRequest>() {
               @Override
               public void call(final HttpServerRequest request) {
                  request.response().headers().add("Access-Control-Allow-Origin", "*");
                  request.response().end(new GraphService().readVertexWrapperJson(request.params().get("id")));
               }
            });
         }
      });

      matcher.post("/api/v1/vertex", new Handler<HttpServerRequest>() {
         @Override
         public void handle(HttpServerRequest request) {
            Observable.from(request).subscribe(new Action1<HttpServerRequest>() {
               @Override
               public void call(final HttpServerRequest request) {
                  request.response().headers().add("Access-Control-Allow-Origin", "*");
                  request.response().headers().add("Access-Control-Allow-Headers", "Content-Type");
                  request.bodyHandler(new Handler<Buffer>() {
                     @Override
                     public void handle(Buffer buffer) {
                        String json = buffer.toString();
                        JsonDTO dto = GSON.INSTANCE.gson().fromJson(json, JsonDTO.class);
                        container.logger().info("DTO: " + dto);

                        String nodeJson;
                        if ("comment".equals(dto.getType())) {
                           nodeJson = Graph.Node().create(dto.getInProperties()).rel("comment").on(Graph.Node().find().by(dto.getOut()).getNode()).getRelation().json();
                        } else {
                           nodeJson = new Graph().create(dto.getInProperties()).getNode().json();
                        }

                        vertx.eventBus().send("io.entrance.deliver-news", new JsonObject(nodeJson));
                        request.response().end(nodeJson);
                     }
                  });
               }
            });
         }
      });

      /**
       * VERTICES. CREATE-operation. POST a new vertex to the collection of
       * vertices.
       */
      matcher.post("/api/v1/vertex/:id", new Handler<HttpServerRequest>() {
         @Override
         public void handle(final HttpServerRequest request) {

         }
      });

      /**
       * VERTEX. DELETE-operation. DELETE the specified vertex (:id).
       */
      matcher.delete("/api/v1/vertex/:id", new Handler<HttpServerRequest>() {
         @Override
         public void handle(final HttpServerRequest request) {

         }
      });

      return matcher;
   }
}
