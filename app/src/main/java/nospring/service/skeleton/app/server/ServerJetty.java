package nospring.service.skeleton.app.server;

import static nospring.service.skeleton.app.util.Util.*;

import jakarta.servlet.DispatcherType;
import java.util.EnumSet;
import nospring.service.skeleton.app.filter.ServletFilter;
import nospring.service.skeleton.app.servlet.AppPing;
import nospring.service.skeleton.app.servlet.AppReset;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class ServerJetty {

  public void start() throws Exception {
    QueuedThreadPool threadPool =
        new QueuedThreadPool(SERVER_MAX_THREADS, SERVER_MIN_THREADS, SERVER_IDLE_TIMEOUT);
    Server server = new Server(threadPool);

    try (ServerConnector connector = new ServerConnector(server)) {
      String port = getSystemEnvProperty(SERVER_PORT);
      connector.setPort(port == null ? 8080 : Integer.parseInt(port));
      server.setConnectors(new Connector[] {connector});
    }

    server.setHandler(getServletHandler());
    server.start();
  }

  private ServletContextHandler getServletHandler() {
    ServletContextHandler servletHandler = new ServletContextHandler();
    servletHandler.addFilter(ServletFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

    servletHandler.addServlet(AppPing.class, CONTEXT_PATH + "/tests/ping");
    servletHandler.addServlet(AppReset.class, CONTEXT_PATH + "/tests/reset");

    return servletHandler;
  }
}
