package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.CommentDAO;
import edu.upc.eetac.dsa.beeter.dao.CommentDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.Comment;
import edu.upc.eetac.dsa.beeter.entity.CommentCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("exam/{id}/comment")
public class CommentResource
{       @Context
        private SecurityContext securityContext;
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_COMMENT)
    public Response createComment(@PathParam("id") String id, @FormParam("text") String text, @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (id == null || text == null)
            throw new BadRequestException("all parameters are mandatory");
        CommentDAO commentDAO = new CommentDAOImpl();
        Comment comment = null;
        AuthToken authToken = null;
        try {
            comment = commentDAO.createComment(securityContext.getUserPrincipal().getName(), id, text);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comment.getId());
        return Response.created(uri).type(BeeterMediaType.BEETER_COMMENT).entity(comment).build();
    }


    @GET
    @Produces(BeeterMediaType.BEETER_COMMENT_COLLECTION)
    public CommentCollection getComments(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        CommentCollection commentCollection = null;
        CommentDAO commentDAO = new CommentDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            commentCollection = commentDAO.getComments(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return commentCollection;
    }

}
