
package io.entrance.verticles;

import io.entrance.service.graph.GraphService;
import io.vertx.rxcore.RxSupport;
import io.vertx.rxcore.java.eventbus.RxMessage;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

import rx.Observable;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

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

        /**
         * VERTICES. READ-operation. GET all vertices. They might be limited by
         * parameter :limit.
         */
        matcher.get("/api/v1/vertices/:limit", new Handler<HttpServerRequest>() {
            @Override
            public void handle(final HttpServerRequest request) {
                Observable.from(request).subscribe(new Action1<HttpServerRequest>() {
                    @Override
                    public void call(HttpServerRequest request) {
                        request.response().end(new GraphService().allVerticesJson());
                    }
                });
            }
        });

        /**
         * VERTICES. CREATE-operation. POST a new vertex to the collection of
         * vertices.
         */
        matcher.post("/api/v1/vertices", new Handler<HttpServerRequest>() {
            @Override
            public void handle(final HttpServerRequest request) {

            }
        });

        /**
         * VERTEX. UPDATE-operation. POST an update to the specified vertex
         * (:id) and act accordingly on the server.
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
