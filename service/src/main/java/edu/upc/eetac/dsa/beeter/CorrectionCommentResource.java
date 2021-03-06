package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.CorrectionCommentDAO;
import edu.upc.eetac.dsa.beeter.dao.CorrectionCommentDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.CorrectionComment;
import edu.upc.eetac.dsa.beeter.entity.CorrectionCommentCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("exam/{examid}/correction/{correctionid}/comment")
public class CorrectionCommentResource
{
    @Context
    private SecurityContext securityContext;
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_COMMENT_CORRECTION)
    public Response createCorrectionComment(@PathParam("examid") String examid, @PathParam("correctionid") String id, @FormParam("text") String text, @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (id == null || text == null)
            throw new BadRequestException("all parameters are mandatory");
        CorrectionCommentDAO commentDAO = new CorrectionCommentDAOImpl();
        CorrectionComment comment = null;
        AuthToken authToken = null;
        try {
            comment = commentDAO.createCorrectionComment(securityContext.getUserPrincipal().getName(), examid, id, text);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comment.getId());
        return Response.created(uri).type(BeeterMediaType.BEETER_COMMENT_CORRECTION).entity(comment).build();
    }

    @GET
    @Produces(BeeterMediaType.BEETER_COMMENT_COLLECTION)
    public CorrectionCommentCollection getComments(@PathParam("correctionid") String id, @QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        CorrectionCommentCollection commentCollection = null;
        CorrectionCommentDAO commentDAO = new CorrectionCommentDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            commentCollection = commentDAO.getComments(id, timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return commentCollection;
    }

}
