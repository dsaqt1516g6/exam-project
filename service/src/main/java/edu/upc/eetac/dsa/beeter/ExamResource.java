package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.ExamDAO;
import edu.upc.eetac.dsa.beeter.dao.ExamDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.Exam;
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
}
