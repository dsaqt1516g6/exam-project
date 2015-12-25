package edu.upc.eetac.dsa.beeter;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by sergio on 7/09/15.
 */
public class BeeterResourceConfig extends ResourceConfig {
    public BeeterResourceConfig() {
        packages("edu.upc.eetac.dsa.beeter");
        packages("edu.upc.eetac.dsa.auth");
        packages("edu.upc.eetac.dsa.cors");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
        register(MultiPartFeature.class);
        property(ServerProperties.TRACING, "ALL");
        property(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
    }
}
