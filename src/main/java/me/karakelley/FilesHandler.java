package me.karakelley;

import me.karakelley.http.server.FilePresenter;
import me.karakelley.http.server.Handler;
import me.karakelley.http.server.filesystem.PublicDirectory;
import me.karakelley.http.server.handlers.staticfilestrategies.RetrieveResourceStrategy;
import me.karakelley.http.server.http.HttpMethod;
import me.karakelley.http.server.http.Request;
import me.karakelley.http.server.http.Response;
import me.karakelley.http.server.http.responses.MethodNotAllowed;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FilesHandler implements Handler {
  private static Map<HttpMethod, Handler> fileStrategies;
  private static PublicDirectory publicDirectory = null;
  private static FilePresenter filePresenter = null;

  public FilesHandler(PublicDirectory publicDirectory, FilePresenter filePresenter) {
    this.publicDirectory = publicDirectory;
    this.filePresenter = filePresenter;
    setupFileStrategies();
  }

  @Override
  public Response respond(Request request) {
    Handler handler = fileStrategies.get(request.getMethod());
    if (handler == null) {
      return new MethodNotAllowed();
    }
    return handler.respond(request);
  }

  private static void setupFileStrategies() {
    fileStrategies = new HashMap<>();
    fileStrategies.put(HttpMethod.GET, new RetrieveResourceStrategy(publicDirectory, filePresenter));
    fileStrategies = Collections.unmodifiableMap(fileStrategies);
  }
}
