package me.karakelley;

import me.karakelley.http.server.*;
import me.karakelley.http.server.filesystem.PublicDirectory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;


class AppTest {

  @Test
  void testVisitingRootDisplaysReactHtmlFiles() {
    ClientHelper client = new ClientHelper();
    HttpServer server = setServerWithRoutes();
    startOnNewThread(server);
    connectClient(server, client);

    String request = "GET / HTTP/1.1\r\n\r\n";
    List<String> response = client.sendMessage(request);
    assertTrue(response.contains("HTTP/1.1 200 OK"));
    assertTrue(response.get(4).contains("<title>React App</title>"));
  }

  @Test
  void testAppDoesNotSupportPOST() {
    ClientHelper client = new ClientHelper();
    HttpServer server = setServerWithRoutes();
    startOnNewThread(server);
    connectClient(server, client);

    String request = "POST /hey.txt HTTP/1.1\r\n\r\n";
    List<String> response = client.sendMessage(request);
    assertTrue(response.contains(("HTTP/1.1 405 Method Not Allowed")));
  }

  @Test
  void testAppDoesNotSupportPUT() {
    ClientHelper client = new ClientHelper();
    HttpServer server = setServerWithRoutes();
    startOnNewThread(server);
    connectClient(server, client);

    String request = "PUT /hey.txt HTTP/1.1\r\n\r\n";
    List<String> response = client.sendMessage(request);
    assertTrue(response.contains(("HTTP/1.1 405 Method Not Allowed")));
  }

  @Test
  void testAppDoesNotSupportDELETE() {
    ClientHelper client = new ClientHelper();
    HttpServer server = setServerWithRoutes();
    startOnNewThread(server);
    connectClient(server, client);

    String request = "DELETE /hey.txt HTTP/1.1\r\n\r\n";
    List<String> response = client.sendMessage(request);
    assertTrue(response.contains(("HTTP/1.1 405 Method Not Allowed")));
  }



  private void startOnNewThread(HttpServer httpServer) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(httpServer::start);
  }

  private HttpServer setServerWithRoutes() {
    Handler app = new App(PublicDirectory.create("./lib/build"));

    ServerConfiguration serverConfiguration = new ServerConfiguration();
    serverConfiguration.setHandler(app);
    serverConfiguration.setPort("0");
    return new HttpServer(serverConfiguration, new ConnectionHandler(), new RequestReaderFactory());
  }


  private void connectClient(HttpServer httpServer, ClientHelper client) {
    try {
      client.connectWithTry("127.0.0.1", httpServer);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
