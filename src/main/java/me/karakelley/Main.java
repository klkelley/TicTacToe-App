package me.karakelley;

import me.karakelley.http.application.CommandLineArguments;
import me.karakelley.http.application.exit.SystemExit;
import me.karakelley.http.server.*;
import me.karakelley.http.server.filesystem.PublicDirectory;


import java.util.Map;

public class Main {
  public static void main(String[] args) {
    Map<String, String> argsHash = new CommandLineArguments().parse(args, new SystemExit());
    ServerConfiguration serverConfiguration = new ServerConfiguration();
    serverConfiguration.setPort(argsHash.get("port"));
    serverConfiguration.setHandler(new App(PublicDirectory.create(argsHash.get("directory"))));
    HttpServer server = new HttpServer(serverConfiguration, new ConnectionHandler(), new RequestReaderFactory());

    server.start();
  }
}
