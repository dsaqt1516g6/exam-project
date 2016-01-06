package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.ExamDAO;
import edu.upc.eetac.dsa.beeter.dao.ExamDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.Exam;
import edu.upc.eetac.dsa.beeter.entity.ExamCollection;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("exam")
public class ExamResource
{
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(BeeterMediaType.BEETER_EXAM)
    public Response createExam(
            @FormDataParam("subject") String subject,
            @FormDataParam("text") String text,
            @FormDataParam("image") InputStream image,
            @FormDataParam("image") FormDataContentDisposition fileDisposition,
            @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (subject == null || text == null || image == null) {
            throw new BadRequestException("all parameters are mandatory");
        }
        ExamDAO   examDAO   = new ExamDAOImpl();
        Exam      exam      = null;
        AuthToken authToken = null;
        try {
            exam = examDAO.createExam(securityContext.getUserPrincipal().getName(), subject, text, image);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        } catch (Exception e) {
            System.out.println("hola");
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + exam.getId());

        return Response.created(uri).type(BeeterMediaType.BEETER_EXAM).entity(exam).build();
    }


    @GET
    @Produces(BeeterMediaType.BEETER_EXAM_COLLECTION)
    public ExamCollection getExams(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        ExamCollection examCollection = null;
        ExamDAO examDAO = new ExamDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            examCollection = examDAO.getExams(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return examCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(BeeterMediaType.BEETER_EXAM)
    public Response getExam(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Exam exam = null;
        ExamDAO examDAO = new ExamDAOImpl();
        try {
            exam = examDAO.getExamById(id);
            if (exam == null)
                throw new NotFoundException("Exam with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(exam.getCreated_at()));

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
            rb = Response.ok(exam).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/subject/{subject}")
    @GET
    @Produces(BeeterMediaType.BEETER_CORRECTION_COLLECTION)
    public ExamCollection getExamsBySubject(@PathParam("subject") String subject, @QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        ExamCollection examCollection = null;
        ExamDAO examDAO = new ExamDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            examCollection = examDAO.getExamBySubject(subject, timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return examCollection;
    }
    @Path("/{id}")
    @DELETE
    public void deleteExam(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        ExamDAO examDAO = new ExamDAOImpl();
        try {
            String ownerid = examDAO.getExamById(id).getUserid();
            if (!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if (!examDAO.deleteExam(id))
                throw new NotFoundException("User with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}
