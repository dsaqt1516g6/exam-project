package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.*;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.Correction;
import edu.upc.eetac.dsa.beeter.entity.Exam;
import edu.upc.eetac.dsa.beeter.entity.Like;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("like/{type}")
public class LikeResource
{

    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_LIKE)
    public Response createLike(@PathParam("type") String type, @FormParam("liked_id") String liked_id, @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (type == null || liked_id == null)
            throw new BadRequestException("all parameters are mandatory");
        LikeDAO       likeDAO       = new LikeDAOImpl();
        Like          like          = null;
        ExamDAO       examDAO       = new ExamDAOImpl();
        Exam          exam          = null;
        CorrectionDAO correctionDAO = new CorrectionDAOImpl();
        Correction    correction          = null;
        AuthToken     authToken     = null;
        try {
            exam = examDAO.getExamById(liked_id);
            correction = correctionDAO.getCorrectionById(liked_id);
            if (exam == null && correction == null)
                throw new NotFoundException("Exam or Correction with id = " + liked_id + " doesn't exist");
            like = likeDAO.createLike(securityContext.getUserPrincipal().getName(), liked_id, type);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + like.getId());
        return Response.created(uri).type(BeeterMediaType.BEETER_LIKE).entity(like).build();
    }

    @Path("/{id}")
    @DELETE
    public void deleteExam(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        LikeDAO likeDAO = new LikeDAOImpl();
        try {
            String ownerid = likeDAO.getLikeById(id).getUser_id();
            if (!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if (!likeDAO.deleteLike(id))
                throw new NotFoundException("User with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


}
