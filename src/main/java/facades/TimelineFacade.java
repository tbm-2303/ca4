package facades;

import dtos.SpotDTO;
import dtos.TimelineDTO;
import entities.Spot;
import entities.Timeline;
import entities.User;
import errorhandling.NotFoundException;

import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.sql.Time;
import java.util.List;

public class TimelineFacade {

    private static TimelineFacade instance;
    private static EntityManagerFactory emf;


    private TimelineFacade() {

    }

    public static TimelineFacade getTimelineFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TimelineFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<Timeline> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t", Timeline.class);
        return query.getResultList();
    }


    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(t) FROM Timeline t").getSingleResult();
        } finally {
            em.close();
        }
    }

























    public Timeline createTimeline(Timeline timeline) throws IllegalStateException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(timeline);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return timeline;
    }
    public Timeline getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Timeline timeline;
        try {
            timeline = em.find(Timeline.class, id);
            if (timeline == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return timeline;
    }


    //Sprint 2
    /*public TimelineDTO deleteTimeline(Integer id){
    }*/

    //denne metode gemmes til rapporten
    /*public List<TimelineDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        //TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t", Timeline.class);
        TypedQuery<Timeline> query1 = em.createQuery("SELECT t FROM Timeline t WHERE t.user.id = :id", Timeline.class);
        List<Timeline> timelines = query1.getResultList();
        return TimelineDTO.getDtos(timelines);
    }*/

    //Test er lavet og virker
    public List<TimelineDTO> getAll(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            User user1 = em.find(User.class, user.getUserName());
            String username = user.getUserName();
            TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t WHERE t.user.userName = :username", Timeline.class);
            query.setParameter("username", username);
            List<Timeline> timelines = query.getResultList();
            return TimelineDTO.getDtos(timelines);
        } finally {
            em.close();
        }
    }

    //test mangler
    public Long getTimelineCount() {
        EntityManager em = emf.createEntityManager();
        try {
            Long timelineCount = (Long) em.createQuery("SELECT COUNT(t) FROM Timeline t").getSingleResult();
            return timelineCount;
        } finally {
            em.close();
        }

    }

    //test virker
    public String editInterval(Integer id, String startDate, String endDate) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t WHERE t.id = :id", Timeline.class);
            query.setParameter("id", id);
            Timeline tm = query.getSingleResult();
            tm.setStartDate(startDate);
            tm.setEndDate(endDate);
            //em.persist(tm);
            return (tm.getStartDate() + tm.getEndDate());
        } finally {
            em.close();
        }
        //timelineDTO id
        //select timeline where id = ?
        //getStartDate + getEndDate
        //Tag to nye datoer med
        //Timeline
    }

    public TimelineDTO seeTimeline(Integer id) {
        //get the timeline from id with select t from....
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t WHERE t.id = :id", Timeline.class);
            query.setParameter("id", id);
            Timeline timeline = query.getSingleResult();
            return new TimelineDTO(timeline);

        } finally {
            em.close();
        }
        //set the result to a dto
        //return th result
    }

    //test og endpoint mangler
    public Timeline deleteTimeline(Long id) {
        EntityManager em = emf.createEntityManager();
        Timeline timeline = em.find(Timeline.class, id);
        if (timeline == null) {
            throw new WebApplicationException("The timeline does not exist");
        }
        try {
            em.getTransaction().begin();
            TypedQuery<Spot> spotQuery = em.createQuery("DELETE FROM Spot s WHERE s.timeline.id = :id", Spot.class);
            spotQuery.setParameter("id", id);
            spotQuery.executeUpdate();
            TypedQuery<Timeline> query = em.createQuery("DELETE FROM Timeline t WHERE t.id = :id", Timeline.class);
            query.setParameter("id", id);
            query.executeUpdate();
            em.getTransaction().commit();
            return timeline;
            } finally {
                em.close();
            }
        }



}