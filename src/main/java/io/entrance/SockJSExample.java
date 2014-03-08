
package io.entrance;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.core.sockjs.SockJSSocket;
import org.vertx.java.platform.Verticle;

public class SockJSExample extends Verticle {

    public void start() {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                if (req.path().equals("/"))
                    req.response().sendFile("sockjs/index.html"); // Serve the
                                                                  // html
            }
        });

        SockJSServer sockServer = vertx.createSockJSServer(server);

        sockServer.installApp(new JsonObject().putString("prefix", "/testapp"), new Handler<SockJSSocket>() {
            public void handle(final SockJSSocket sock) {
                sock.dataHandler(new Handler<Buffer>() {
                    public void handle(Buffer data) {
                        container.logger().info(String.format("This was send to me: %s", data));
                        sock.write(data); // Echo it back
                    }
                });
            }
        });

        server.listen(8080);
        
        container.logger().info("Webserver started, listening on port: 8080 !!");
    }
}
