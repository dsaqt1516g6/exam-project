package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.CommentDAO;
import edu.upc.eetac.dsa.beeter.dao.CommentDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.Comment;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("exam/comment")
public class CommentResource
{       @Context
        private SecurityContext securityContext;
    @Path("/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_COMMENT)
    public Response createSting(@PathParam("id") String id, @FormParam("text") String text, @Context UriInfo uriInfo) throws URISyntaxException
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


}
