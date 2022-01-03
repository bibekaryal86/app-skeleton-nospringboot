package nospring.service.skeleton.app.server;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import nospring.service.skeleton.app.servlet.AppReset;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import nospring.service.skeleton.app.filter.ServletFilter;
import nospring.service.skeleton.app.util.Util;
import nospring.service.skeleton.app.servlet.AppPing;

import java.util.EnumSet;

@Slf4j
public class ServerJetty {

    public void start() throws Exception {
        log.info("Start Jetty Server Initialization!!!");

        QueuedThreadPool threadPool = new QueuedThreadPool(Util.SERVER_MAX_THREADS, Util.SERVER_MIN_THREADS, Util.SERVER_IDLE_TIMEOUT);
        Server server = new Server(threadPool);

        try (ServerConnector connector = new ServerConnector(server)) {
            String port = Util.getSystemEnvProperty(Util.SERVER_PORT);
            connector.setPort(port == null ? 8080 : Integer.parseInt(port));
            server.setConnectors(new Connector[]{connector});
        }

        server.setHandler(getServletHandler());
        server.start();
        log.info("Finish Jetty Server Initialization!!!");
    }

    private ServletHandler getServletHandler() {
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addFilterWithMapping(ServletFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        servletHandler.addServletWithMapping(AppPing.class, Util.CONTEXT_PATH + "/tests/ping");
        servletHandler.addServletWithMapping(AppReset.class, Util.CONTEXT_PATH + "/tests/reset");

        return servletHandler;
    }
}