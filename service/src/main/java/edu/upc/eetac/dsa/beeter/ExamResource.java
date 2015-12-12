package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.ExamDAO;
import edu.upc.eetac.dsa.beeter.dao.ExamDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.Exam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("exams")
public class ExamResource
{
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_EXAM)
    public Response createSting(@FormParam("subject") String subject, @FormParam("text") String text, @FormParam("statement_url") String statement, @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (subject == null || text == null || statement == null)
            throw new BadRequestException("all parameters are mandatory");
        ExamDAO examDAO = new ExamDAOImpl();
        Exam exam = null;
        AuthToken authToken = null;
        try {
            exam = examDAO.createExam(securityContext.getUserPrincipal().getName(), subject, text, statement);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + exam.getId());
        return Response.created(uri).type(BeeterMediaType.BEETER_STING).entity(exam).build();
    }

}
