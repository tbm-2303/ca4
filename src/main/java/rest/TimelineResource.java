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

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/create")
    public Response createTimeline(String jsonContext) throws NotFoundException {
        TimelineDTO timelineDTO = GSON.fromJson(jsonContext, TimelineDTO.class);
        User user = userFacade.getUserByName(timelineDTO.getUserName());
        List<Spot> spotlist = new ArrayList<>();//spotlist is empty when u intitially create a timeline.

        Timeline timeline = new Timeline(
                timelineDTO.getName(),
                timelineDTO.getDescription(),
                timelineDTO.getStartDate(),
                timelineDTO.getEndDate(),
                spotlist,
                user);

        Timeline createdTimeline = FACADE.createTimeline(timeline);

        TimelineDTO createdDTO = new TimelineDTO(createdTimeline);
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(createdDTO))
                .build();
    }
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        TimelineDTO found = new TimelineDTO(FACADE.getById(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(found))
                .build();
    }

    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) throws NotFoundException {
        TimelineDTO deleted = new TimelineDTO(FACADE.deleteTimeline(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(deleted))
                .build();
    }
}