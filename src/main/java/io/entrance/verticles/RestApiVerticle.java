package io.entrance.verticles;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

public class RestApiVerticle extends Verticle {

    @Override
    public void start() {
        super.start();
        
        HttpServer server = vertx.createHttpServer();
    }

    private RouteMatcher setupRoutes() {
        RouteMatcher matcher = new RouteMatcher();
        
        /**
         * VERTICES. READ-operation.
         * 
         * GET all vertices. They might be limited by parameter :limit.
         */
        matcher.get("/api/v1/vertices/:limit", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest request) {
                
            }
        });
        
        /**
         * VERTICES. CREATE-operation.
         * 
         * POST a new vertex to the collection of vertices.
         */
        matcher.post("/api/v1/vertices", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest request) {
                
            }
        });
        
        
        
        
        
        
        return matcher;
    }
    
    
}
