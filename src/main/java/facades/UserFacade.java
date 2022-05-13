package facades;

import com.google.gson.JsonObject;
import dtos.UserDTO;
import entities.Role;
import entities.Timeline;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public List<UserDTO> getAllUsers() throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> typedQueryUser
                = em.createQuery("SELECT u FROM User u", User.class);
        List<User> userList = typedQueryUser.getResultList();

        List<UserDTO> userDTOS = new ArrayList<>();
        for (User u : userList) {
            userDTOS.add(new UserDTO(u));
        }
        return userDTOS;
    }

    //dette er kun for alm bruger(altid user role)
    public UserDTO create(UserDTO userDTO){
        EntityManager em = getEntityManager();
        User user = new User(userDTO.getUserName(), userDTO.getPassword());
        Role role = new Role("user");
        user.addRole(role);
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UserDTO getUserByName(String username) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        if (user == null)
            throw new EntityNotFoundException("No user with this name exists");
        return new UserDTO(user);
    }

    public Long getUserCount(){
        EntityManager em = getEntityManager();
        try{
            return (Long) em.createQuery("SELECT COUNT(u) FROM User u").getSingleResult();
        }finally{
            em.close();
        }
    }
























    public JsonObject getRandomCatFact() {
        return Utility.fetchData("https://catfact.ninja/fact");
    }

    public JsonObject getRandomJoke() {
        return Utility.fetchData("https://api.chucknorris.io/jokes/random");
    }


    //-------------------------------------------nye metoder







}
