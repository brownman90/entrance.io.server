package io.entrance.verticles;

import org.vertx.java.platform.Verticle;

/**
 * Main starter verticle.
 * 
 * @author jan.prill
 *
 */
public class Main extends Verticle {

    @Override
    public void start() {
        container.deployVerticle("io.entrance.HttpPingVerticle");
        container.deployVerticle("io.entrance.verticles.BusVerticle");
    }

    
    
}