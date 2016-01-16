package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.CorrectionDAO;
import edu.upc.eetac.dsa.beeter.dao.CorrectionDAOImpl;
import edu.upc.eetac.dsa.beeter.dao.UserDAO;
import edu.upc.eetac.dsa.beeter.dao.UserDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.Correction;
import edu.upc.eetac.dsa.beeter.entity.CorrectionCollection;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("exam/{examid}/correction")
public class CorrectionResource
{
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(BeeterMediaType.BEETER_CORRECTION)
    public Response createExam(
            @PathParam("examid") String examid,
            @FormDataParam("text") String text,
            @FormDataParam("image") InputStream image,
            @FormDataParam("image") FormDataContentDisposition fileDisposition,
            @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (examid == null || text == null || image == null) {
            throw new BadRequestException("all parameters are mandatory");
        }
        CorrectionDAO correctionDAO   = new CorrectionDAOImpl();
        Correction correction     = null;
        AuthToken authToken = null;
        try {
            correction = correctionDAO.createCorrection(securityContext.getUserPrincipal().getName(), examid, text, image);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        } catch (Exception e) {
            System.out.println("hola");
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + correction.getId());

        return Response.created(uri).type(BeeterMediaType.BEETER_CORRECTION).entity(correction).build();
    }

    @GET
    @Produces(BeeterMediaType.BEETER_CORRECTION_COLLECTION)
    public CorrectionCollection getCorrections(@PathParam("examid") String examid, @QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        CorrectionCollection correctionCollection = null;
        CorrectionDAO CorrectionDAO = new CorrectionDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            correctionCollection = CorrectionDAO.getCorrections(examid, timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return correctionCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(BeeterMediaType.BEETER_CORRECTION)
    public Response getCorrection(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Correction correction = null;
        CorrectionDAO correctionDAO = new CorrectionDAOImpl();
        try {
            correction = correctionDAO.getCorrectionById(id);
            if (correction == null)
                throw new NotFoundException("Exam with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(correction.getCreated_at()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(correction).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @DELETE
    public void deleteCorrection(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        UserDAO userDAO = new UserDAOImpl();
        CorrectionDAO correctionDAO = new CorrectionDAOImpl();
        try {
            String ownerid = correctionDAO.getCorrectionById(id).getUser_id();
            String role = userDAO.getRoleUserById(id).getRole();
            if (!userid.equals(ownerid)|| !role.equals("admin"))
                throw new ForbiddenException("operation not allowed");
            if (!correctionDAO.deleteCorrection(id))
                throw new NotFoundException("User with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}
