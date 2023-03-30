package com.etzgh.xportal.app;

import com.etzgh.xportal.controller.AbsaUBPProxy;
import com.etzgh.xportal.controller.AdminProxy;
import com.etzgh.xportal.controller.AppSettings;
import com.etzgh.xportal.controller.AuditTrailProxy;
import com.etzgh.xportal.controller.BTWInvestigationProxy;
import com.etzgh.xportal.controller.BalanceProxy;
import com.etzgh.xportal.controller.BankCollectionsProxy;
import com.etzgh.xportal.controller.BogProxy;
import com.etzgh.xportal.controller.CardTransReportProxy;
import com.etzgh.xportal.controller.ChangePassword;
import com.etzgh.xportal.controller.ChangePasswordNew;
import com.etzgh.xportal.controller.ChangePasswordToken;
import com.etzgh.xportal.controller.CmsProxy;
import com.etzgh.xportal.controller.ConsoleFilesProxy;
import com.etzgh.xportal.controller.ControllerFactory;
import com.etzgh.xportal.controller.CorporatePayProxy;
import com.etzgh.xportal.controller.CscProxy;
import com.etzgh.xportal.controller.ElevyProxy;
import com.etzgh.xportal.controller.EmiProxy;
import com.etzgh.xportal.controller.ExtRequestProxy;
import com.etzgh.xportal.controller.FundgateProxy;
import com.etzgh.xportal.controller.FundsTransferProxy;
import com.etzgh.xportal.controller.GipProxy;
import com.etzgh.xportal.controller.GraElevyReportProxy;
import com.etzgh.xportal.controller.HttpSessionCollector;
import com.etzgh.xportal.controller.JustpayProxy;
import com.etzgh.xportal.controller.LoginAuthenticator;
import com.etzgh.xportal.controller.Logout;
import com.etzgh.xportal.controller.MerchantPayProxy;
import com.etzgh.xportal.controller.MerchantsInfo;
import com.etzgh.xportal.controller.MobileAppProxy;
import com.etzgh.xportal.controller.MobileInvestProxy;
import com.etzgh.xportal.controller.MobilemoneyReprocessingProxy;
import com.etzgh.xportal.controller.MomoProxy;
import com.etzgh.xportal.controller.NlaLiquidationReportProxy;
import com.etzgh.xportal.controller.PasswordReset;
import com.etzgh.xportal.controller.PayfluidProxy;
import com.etzgh.xportal.controller.PbcProxy;
import com.etzgh.xportal.controller.PdfProxy;
import com.etzgh.xportal.controller.RefreshToken;
import com.etzgh.xportal.controller.ReprocessProxy;
import com.etzgh.xportal.controller.ReversalProxy;
import com.etzgh.xportal.controller.SmsPortalReportProxy;
import com.etzgh.xportal.controller.SugarWalletProxy;
import com.etzgh.xportal.controller.TmcProxy;
import com.etzgh.xportal.controller.TmcProxy2;
import com.etzgh.xportal.controller.TreeProxy;
import com.etzgh.xportal.controller.UserProfile;
import com.etzgh.xportal.controller.UssdCardTransProxy;
import com.etzgh.xportal.controller.VTUProxy;
import com.etzgh.xportal.controller.ValidateOtp;
import com.etzgh.xportal.controller.VasgateProxy;
import com.etzgh.xportal.controller.VasgateReprocessProxy;
import com.etzgh.xportal.controller.VerificationProxy;
import com.etzgh.xportal.controller.WhitelistBlacklistProxy;
import com.etzgh.xportal.servlets.EchoServlet;
import com.etzgh.xportal.servlets.HelloServlet;
import com.etzgh.xportal.utility.AuthenticationFilter;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.CorsFilter;
import com.etzgh.xportal.utility.LoadSalt;
import com.etzgh.xportal.utility.OriginFilter;
import com.etzgh.xportal.utility.ValidatePeriod;
import com.etzgh.xportal.utility.ValidateSalt;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.encoding.EncodingHandler;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.util.Headers;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;

public class AppServer {

    private static final Config config = new Config();
    private static final Logger log = Logger.getLogger(AppServer.class.getName());
    private final String host;
    private final int port;
    private final List<Pair<String, HttpHandler>> handlers;
    private static String apiPath;

    static {
        PropertyConfigurator.configure("cfg" + File.separator + "log4j.config");
    }

    public static void main(String[] args) throws ServletException {

        String host = config.getProperty("APPLICATION_HOST");
        String portValue = config.getProperty("APPLICATION_PORT");
        int port = 0;
        apiPath = config.getProperty("APPLICATION_API_PATH");

        if (portValue.isEmpty()) {
            log.info("Could not find property value 'APPLICATION_PORT' in config file");
            return;
        }
        try {
            port = Integer.valueOf(portValue);
        } catch (Exception er) {
            log.info("Invalid property value :::" + portValue + " for 'APPLICATION_PORT' in config file");
            return;
        }

        if (apiPath == null || apiPath.isEmpty()) {
            log.info("Could not find property value 'APPLICATION_API_PATH' in config file");
            return;
        }

        new AppServer(host, port)
                .addHandler("/", redirectRoot())
                .addHandler("/api", new InternalApiHandler())
                .addHandler("/" + apiPath, createServletHandler())
                .addHandler("/" + apiPath + "Api", createRestApiHandler("/" + apiPath + "Api", "/"))
                .start();
    }

    public AppServer(final String host, final Integer port) {
        this.host = host;
        this.port = port;
        this.handlers = new ArrayList<>();
    }

    protected AppServer addHandler(final String path, final HttpHandler handler) {
        this.handlers.add(Pair.of(path, handler));
        return this;
    }

    protected void start() {

        PathHandler pathHandlers = Handlers.path();
        this.handlers.forEach((handler) -> {
            pathHandlers.addPrefixPath(handler.getLeft(), handler.getRight());
        });

        HttpHandler encodingHandler = new EncodingHandler.Builder().build(null)
                .wrap(pathHandlers);

        Undertow server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(encodingHandler)
                .build();

        server.start();
    }

    private static HttpHandler redirectRoot() {
        final ResourceManager staticResources = new ClassPathResourceManager(
                AppServer.class.getClassLoader(), "static");
        final ResourceHandler resourceHandler = new ResourceHandler(staticResources);
        resourceHandler.setWelcomeFiles("home.html");
        return resourceHandler;
    }

    private static class InternalApiHandler implements HttpHandler {

        @Override
        public void handleRequest(final HttpServerExchange exchange) throws Exception {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("Hello World From the InternalApiHandler");
        }
    }

    private static HttpHandler createServletHandler() throws ServletException {

        ResourceManager staticResources = new ClassPathResourceManager(AppServer.class.getClassLoader(), "static");

        final ResourceManager cachedResources
                = new CachingResourceManager(100, 65536, new DirectBufferCache(1024, 10, 10480), staticResources, (int) Duration.ofDays(1).getSeconds());

        DeploymentInfo di = Servlets.deployment()
                .setClassLoader(AppServer.class.getClassLoader())
                .setContextPath("/" + apiPath)
                .setDeploymentName("Servlet APIs")
                .setResourceManager(cachedResources)
                .addWelcomePage("index.html")
                .addListeners(Servlets.listener(HttpSessionCollector.class))
                .addFilter(Servlets.filter("LoadSalt", LoadSalt.class)).addFilterUrlMapping("LoadSalt", "*.html", DispatcherType.REQUEST)
                .addFilter(Servlets.filter("AuthenticationFilter", AuthenticationFilter.class)).addFilterUrlMapping("AuthenticationFilter", "/api/*", DispatcherType.REQUEST)
                .addFilter(Servlets.filter("OriginFilter", OriginFilter.class)).addFilterUrlMapping("OriginFilter", "/api/*", DispatcherType.REQUEST)
                .addFilter(Servlets.filter("ValidateSalt", ValidateSalt.class)).addFilterUrlMapping("ValidateSalt", "/api/*", DispatcherType.REQUEST)
                .addFilter(Servlets.filter("CorsFilter", CorsFilter.class)).addFilterUrlMapping("CorsFilter", "*", DispatcherType.REQUEST)
                .addFilter(Servlets.filter("ValidatePeriod", ValidatePeriod.class)).addFilterUrlMapping("ValidatePeriod", "/api/*", DispatcherType.REQUEST)
                .addServlets(
                        Servlets.servlet("helloServlet", HelloServlet.class).addMapping("/api/hello"),
                        Servlets.servlet("echoServlet", EchoServlet.class).addMapping("/api/echo"),
                        Servlets.servlet("UserProfile", UserProfile.class).addMapping("/api/UserProfile"),
                        Servlets.servlet("ChangePassword", ChangePassword.class).addMapping("/api/ChangePassword"),
                        Servlets.servlet("ChangePasswordNew", ChangePasswordNew.class).addMapping("/api/ChangePasswordNew"),
                        Servlets.servlet("Authenticator", LoginAuthenticator.class).addMapping("/api/Authenticator"),
                        Servlets.servlet("MerchantsInfo", MerchantsInfo.class).addMapping("/api/MerchantsInfo"),
                        Servlets.servlet("PortalSettings", AppSettings.class).addMapping("/api/PortalSettings"),
                        Servlets.servlet("Admin", AdminProxy.class).addMapping("/api/Admin"),
                        Servlets.servlet("PasswordReset", PasswordReset.class).addMapping("/api/PasswordReset"),
                        Servlets.servlet("ValidateOtp", ValidateOtp.class).addMapping("/api/ValidateOtp"),
                        Servlets.servlet("Logout", Logout.class).addMapping("/api/Logout"),
                        Servlets.servlet("RefreshToken", RefreshToken.class).addMapping("/api/RefreshToken"),
                        Servlets.servlet("MobileAppMgmt", MobileAppProxy.class).addMapping("/api/MobileAppMgmt"),
                        Servlets.servlet("Reversal", ReversalProxy.class).addMapping("/api/Reversal"),
                        Servlets.servlet("Reprocess", ReprocessProxy.class).addMapping("/api/Reprocess"),
                        Servlets.servlet("FundGate", FundgateProxy.class).addMapping("/api/FundGate"),
                        Servlets.servlet("JustPay", JustpayProxy.class).addMapping("/api/JustPay"),
                        Servlets.servlet("MerchantPay", MerchantPayProxy.class).addMapping("/api/MerchantPay"),
                        Servlets.servlet("MobileMoney", MomoProxy.class).addMapping("/api/Mobilemoney"),
                        Servlets.servlet("Tmc", TmcProxy.class).addMapping("/api/Tmc"),
                        Servlets.servlet("Tmc2", TmcProxy2.class).addMapping("/api/Tmc2"),
                        Servlets.servlet("Vasbill", VasgateProxy.class).addMapping("/api/Vasbill"),
                        Servlets.servlet("Csc", CscProxy.class).addMapping("/api/Csc"),
                        Servlets.servlet("AuditTrail", AuditTrailProxy.class).addMapping("/api/AuditTrail"),
                        Servlets.servlet("WalletBalance", BalanceProxy.class).addMapping("/api/WalletBalance"),
                        Servlets.servlet("CorporatePay", CorporatePayProxy.class).addMapping("/api/CorporatePay"),
                        Servlets.servlet("ExportPdf", PdfProxy.class).addMapping("/api/ExportPdf"),
                        Servlets.servlet("CardTransactions", CardTransReportProxy.class).addMapping("/api/CardTransactions"),
                        Servlets.servlet("Cms", CmsProxy.class).addMapping("/api/Cms"),
                        Servlets.servlet("BOG", BogProxy.class).addMapping("/api/BOG"),
                        Servlets.servlet("AbsaUBP", AbsaUBPProxy.class).addMapping("/api/AbsaUBP"),
                        Servlets.servlet("Gip", GipProxy.class).addMapping("/api/Gip"),
                        Servlets.servlet("VTU", VTUProxy.class).addMapping("/api/VTU"),
                        Servlets.servlet("ResetAccount", ChangePasswordToken.class).addMapping("/api/ResetAccount"),
                        Servlets.servlet("BankCollection", BankCollectionsProxy.class).addMapping("/api/BankCollection"),
                        Servlets.servlet("MobileInvest", MobileInvestProxy.class).addMapping("/api/MobileInvest"),
                        Servlets.servlet("TreeMerchantPay", TreeProxy.class).addMapping("/api/TreeMerchantPay"),
                        Servlets.servlet("Payfluid", PayfluidProxy.class).addMapping("/api/Payfluid"),
                        Servlets.servlet("SugarWallet", SugarWalletProxy.class).addMapping("/api/SugarWallet"),
                        Servlets.servlet("BulkProcess", ReprocessProxy.class).addMapping("/api/BulkProcess"),
                        Servlets.servlet("ConsoleFiles", ConsoleFilesProxy.class).addMapping("/api/ConsoleFiles").setMultipartConfig(new MultipartConfigElement("")),
                        Servlets.servlet("Pbc", PbcProxy.class).addMapping("/api/Pbc"),
                        Servlets.servlet("BTWInvestigation", BTWInvestigationProxy.class).addMapping("/api/BTWInvestigation"),
                        Servlets.servlet("initiateVasReprocessing", VasgateReprocessProxy.class).addMapping("/api/VasgateReprocess"),
                        Servlets.servlet("Elevy", ElevyProxy.class).addMapping("/api/Elevy"),
                        Servlets.servlet("nlaliquidationreport", NlaLiquidationReportProxy.class).addMapping("/api/nlaliquidationreport"),
                        Servlets.servlet("Mobilemoneyreprocessing", MobilemoneyReprocessingProxy.class).addMapping("/api/Mobilemoneyreprocessing"),
                        Servlets.servlet("Emi", EmiProxy.class).addMapping("/api/Emi"),
                        Servlets.servlet("Verification", VerificationProxy.class).addMapping("/api/Verification"),
                        Servlets.servlet("whitelistblacklistinsert", WhitelistBlacklistProxy.class).addMapping("/api/whitelistblacklistinsert"),
                        Servlets.servlet("UssdCardTransactions", UssdCardTransProxy.class).addMapping("/api/UssdCardTransactions"),
                        Servlets.servlet("fundstransfer", FundsTransferProxy.class).addMapping("/api/fundstransfer"),
                        Servlets.servlet("ExtRequests", ExtRequestProxy.class).addMapping("/api/ExtRequests"),
                        Servlets.servlet("SmsPortalReport", SmsPortalReportProxy.class).addMapping("/api/SmsPortalReport"),
                        Servlets.servlet("GraElevyReport", GraElevyReportProxy.class).addMapping("/api/GraElevyReport")

                );
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(di);
        manager.deploy();
        HttpHandler servletHandler = manager.start();
        return servletHandler;
    }

    private static HttpHandler createRestApiHandler(final String contextPath, final String appPath) throws ServletException {
        final UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        deployment.setApplicationClass(ControllerFactory.class.getName());
        DeploymentInfo di = server.undertowDeployment(deployment, appPath)
                .setClassLoader(AppServer.class.getClassLoader())
                .setContextPath(contextPath)
                .setDeploymentName("Rest APIs");
        di.addListeners(Servlets.listener(Listener.class));
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(di);
        manager.deploy();
        HttpHandler servletHandler = manager.start();
        return servletHandler;
    }
}
