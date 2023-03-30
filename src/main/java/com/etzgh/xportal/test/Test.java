package com.etzgh.xportal.test;

import com.etzgh.xportal.servlets.EchoServlet;
import com.etzgh.xportal.servlets.HelloServlet;
import com.etzgh.xportal.utility.GeneralUtility;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.util.Headers;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class Test {

    private static final Logger log = Logger.getLogger(Test.class.getName());
    private static GeneralUtility utility = new GeneralUtility();

    public static void main(String[] args) throws Exception {

        String user_code = "[1],[17]|0060000244:0067510000010000,[2000000000000048]|000,[2000000000000049]|SCB:6PHY,[2000000000000053]|GOTV,[2000000000000054]|GOTV~TELESOL,[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|SurflineTopup,[2500000000000049]|2,[2500000000000050]|38,[29],[71]|0060010112,[91]|ALL";
        String type_id = "[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[21],[25],[26],[27],[28],[30],[31],[33],[41]";
        Boolean allowed = (!type_id.isEmpty() && (type_id.contains("[0]") || (type_id.contains("[5]") && !utility.getRoleParameters("[91]", user_code).isEmpty())));

        System.out.println("ALLOWED: " + allowed);
        System.out.println("type_id.contains(\"[0]\") " + type_id.contains("[0]"));

        System.out.println("type_id.contains(\"[5]\"): " + type_id.contains("[5]"));

        System.out.println("utility.getRoleParameters(\"[91]\", user_code): " + utility.getRoleParameters("[91]", user_code));

        System.out.println("utility.getRoleParameters(\"[91]\", user_code).isEmpty(): " + utility.getRoleParameters("[91]", user_code).isEmpty());

    }

    private static HttpHandler createStaticResourceHandler() {
        final ResourceManager staticResources = new ClassPathResourceManager(
                Test.class.getClassLoader(), "static");
        final ResourceHandler resourceHandler = new ResourceHandler(staticResources);
        resourceHandler.setWelcomeFiles("index.html");
        return resourceHandler;
    }

    private static class InternalRestHandler implements HttpHandler {

        @Override
        public void handleRequest(final HttpServerExchange exchange) throws Exception {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("Hello World U");
        }
    }

    private static HttpHandler createServletHandler() throws Exception {
        DeploymentInfo di = Servlets.deployment()
                .setClassLoader(Test.class.getClassLoader())
                .setContextPath("/serv")
                .setDeploymentName("My Servlets")
                .addServlets(
                        Servlets.servlet("helloServlet", HelloServlet.class).addMapping("/hello"),
                        Servlets.servlet("echoServlet", EchoServlet.class).addMapping("/echo")
                );
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(di);
        manager.deploy();
        HttpHandler servletHandler = manager.start();
        return servletHandler;
    }

    private static HttpHandler createRestApiHandler() throws Exception {
        final UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(MyApp.class.getName());
        DeploymentInfo di = server.undertowDeployment(deployment, "/api")
                .setClassLoader(Test.class.getClassLoader())
                .setContextPath("/rest")
                .setDeploymentName("My API");
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(di);
        manager.deploy();
        HttpHandler servletHandler = manager.start();
        return servletHandler;
    }
}
