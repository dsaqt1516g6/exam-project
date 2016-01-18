package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.SubjectDAO;
import edu.upc.eetac.dsa.beeter.dao.SubjectDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.Subject;
import edu.upc.eetac.dsa.beeter.entity.SubjectCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("subject")
public class SubjectResource
{
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_SUBJECT)
    public Response createSubject(@FormParam("name") String name, @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (name == null)
            throw new BadRequestException("all parameters are mandatory");
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        Subject    subject    = null;
        AuthToken  authToken  = null;
        try {
            subject = subjectDAO.createSubject(name);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + subject.getId());
        return Response.created(uri).type(BeeterMediaType.BEETER_SUBJECT).entity(subject).build();
    }

    @GET
    @Produces(BeeterMediaType.BEETER_SUBJECT_COLLECTION)
    public SubjectCollection getSubjects(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        SubjectCollection subjectCollection = null;
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            subjectCollection = subjectDAO.getSubjects(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return subjectCollection;
    }
}
