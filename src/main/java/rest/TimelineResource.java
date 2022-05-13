package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.TimelineDTO;
import entities.Spot;
import entities.Timeline;
import entities.User;
import errorhandling.NotFoundException;
import facades.TimelineFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Path("timeline")
public class TimelineResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final TimelineFacade FACADE = TimelineFacade.getTimelineFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final  UserFacade userFacade = UserFacade.getUserFacade(EMF);

    @Context
    SecurityContext securityContext;

    @Context
    private UriInfo uriInfo;

    //ikke testet
    //id'et skal bruges til at oprette timelinen
    //det skal sættes i frontenden - laves med en metode

//get amount of persons
    @Path("test")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String test() {
        return "{hello}";  
    }




    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<TimelineDTO> dtoList = new ArrayList<>();
        for (Timeline tl : FACADE.getAll()) {
            dtoList.add(new TimelineDTO(tl));
        }
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(dtoList))
                .build();
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getQuizCount() {
        long count = FACADE.getCount();
        return "{\"count\":"+count+"}";
    }
}