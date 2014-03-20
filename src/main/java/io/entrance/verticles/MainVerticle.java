package io.entrance.verticles;

import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

/**
 * Main starter verticle. test
 * 
 * @author jan.prill
 *
 */
public class MainVerticle extends Verticle {

    @Override
    public void start() {
        JsonObject config = new JsonObject();
        config.putBoolean("auto-redeploy", true);
        
        container.deployModule("io.entrance~mod-entrance-io-auth~1.0.0-SNAPSHOT", config);
        container.deployVerticle("io.entrance.HttpPingVerticle", config);
        container.deployVerticle("io.entrance.verticles.BusVerticle", config);
        container.deployVerticle("io.entrance.verticles.RestApiVerticle", config);
    }

    
    
}
