package edu.upc.eetac.dsa.beeter.entity;

import edu.upc.eetac.dsa.beeter.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;


public class BeeterRootAPI {
    @InjectLinks({
            @InjectLink(resource = BeeterRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self bookmark home", title = "Beeter Root API"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "login", title = "Login", type= BeeterMediaType.BEETER_AUTH_TOKEN),
            @InjectLink(resource = ExamResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-exams", title = "Current exams", type= BeeterMediaType.BEETER_EXAM_COLLECTION),
            @InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-user", title = "Register", type= BeeterMediaType.BEETER_AUTH_TOKEN),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout", condition="${!empty resource.userid}"),
            @InjectLink(resource = ExamResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-exam", title = "Create exam", condition="${!empty resource.userid}", type=BeeterMediaType.BEETER_EXAM),
            @InjectLink(resource = UserResource.class, method="getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", condition="${!empty resource.userid}", type= BeeterMediaType.BEETER_USER, bindings = @Binding(name = "id", value = "${resource.userid}"))
    })

    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
