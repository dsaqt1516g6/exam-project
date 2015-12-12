package edu.upc.eetac.dsa.beeter;

import edu.upc.eetac.dsa.beeter.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.AuthToken;
import edu.upc.eetac.dsa.beeter.entity.User;
import edu.upc.eetac.dsa.beeter.dao.AuthTokenDAO;
import edu.upc.eetac.dsa.beeter.dao.UserDAOImpl;
import edu.upc.eetac.dsa.beeter.dao.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.sql.SQLException;

/**
 * Created by sergio on 10/09/15.
 */
@Path("login")
public class LoginResource {
    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_AUTH_TOKEN)
    public AuthToken login(@FormParam("name") String name, @FormParam("password") String password) {
        if(name == null || password == null)
            throw new BadRequestException("all parameters are mandatory");

        User user = null;
        AuthToken authToken = null;
        try{
            UserDAO userDAO = new UserDAOImpl();
            user = userDAO.getUserByname(name);
            if(user == null)
                throw new BadRequestException("loginid " + name + " not found.");
            if(!userDAO.checkPassword(user.getId(), password))
                throw new BadRequestException("incorrect password");

            AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
            authTokenDAO.deleteToken(user.getId());
            authToken = authTokenDAO.createAuthToken(user.getId());
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        return authToken;
    }

    @DELETE
    public void logout(){
        String userid = securityContext.getUserPrincipal().getName();
        AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
        try {
            authTokenDAO.deleteToken(userid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
