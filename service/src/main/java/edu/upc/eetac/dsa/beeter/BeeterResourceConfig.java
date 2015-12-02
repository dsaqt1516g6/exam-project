package edu.upc.eetac.dsa.beeter;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

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
    }
}
