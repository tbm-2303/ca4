package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import entities.Role;
import errorhandling.NotFoundException;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Path("user")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    SecurityContext securityContext;
    @Context
    private UriInfo context;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public String count(){
       Long count = FACADE.getUserCount();
        return "{\"count\":"+count+"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public Response getAllUsers() throws EntityNotFoundException {
        List<UserDTO> userDTOS = FACADE.getAllUsers();
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(userDTOS))
                .build();
    }

}
