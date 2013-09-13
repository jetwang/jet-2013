package com.wind.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.InheritedChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class Jetty {
    private static final String CONTEXT_PATH = "";

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        SslContextFactory factory = new SslContextFactory();
        factory.setKeyStorePath("src/test/keystore.jks");
        factory.setKeyStorePassword("storepass");
        factory.setKeyManagerPassword("keypass");
        SslSocketConnector connector = new SslSocketConnector(factory);
        connector.setPort(8443);
        server.addConnector(connector);
        InheritedChannelConnector inheritedChannelConnector = new InheritedChannelConnector();
        inheritedChannelConnector.setPort(8080);
        server.addConnector(inheritedChannelConnector);

        WebAppContext context = new WebAppContext();
        context.setConfigurationClasses(new String[]{WebInfConfiguration.class.getName(), WebXmlConfiguration.class.getName()});
        context.setResourceBase("src/main/webapp");
        context.setContextPath(CONTEXT_PATH);
        context.setParentLoaderPriority(true);
        server.setHandler(context);

        try {
            server.start();
            if (!context.isAvailable()) throw new IllegalStateException("context deployed failed");
            server.join();
        } catch (Exception e) {
            server.stop();
            throw new IllegalStateException("jetty failed to start: " + e.getMessage(), e);
        }
    }
}

