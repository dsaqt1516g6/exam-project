package edu.upc.eetac.dsa.beeter;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Main class.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    private static String baseURI;

    public static String getBaseURI() {
        if (baseURI == null) {
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("beeter");
            baseURI = prb.getString("beeter.context");
        }
        return baseURI;
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.upc.eetac.dsa.beeter package
        final ResourceConfig rc = new BeeterResourceConfig();

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseURI()), rc);
        PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("beeter");
        String imagesFolder = prb.getString("upload_folder");
        String imagesCorrectionsFolder = prb.getString("upload_folder_correction");

        HttpHandler httpHandler = new StaticHttpHandler(imagesFolder);
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, "/images/");

        HttpHandler correctionsHttpHandler = new StaticHttpHandler(imagesCorrectionsFolder);
        httpServer.getServerConfiguration().addHttpHandler(correctionsHttpHandler, "/images-corrections/");

        return httpServer;
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        final HttpServer server = startServer();
    }
}

