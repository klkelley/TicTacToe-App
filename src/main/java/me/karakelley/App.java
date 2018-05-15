package me.karakelley;


import me.karakelley.http.application.presenters.HtmlFilePresenter;
import me.karakelley.http.application.requestmatchers.AlwaysMatchesMatcher;
import me.karakelley.http.server.Handler;
import me.karakelley.http.server.filesystem.PublicDirectory;
import me.karakelley.http.server.handlers.Router;
import me.karakelley.http.server.http.Request;
import me.karakelley.http.server.http.Response;

public class App implements Handler {
  private final Router router;

  App(PublicDirectory publicDirectory) {
    this.router = new Router()
            .route(new AlwaysMatchesMatcher(), new FilesHandler(publicDirectory, new HtmlFilePresenter(publicDirectory)));
  }

  @Override
  public Response respond(Request request) {
    return router.respond(request);
  }
}
