/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.RoleDTO;
import dtos.SpotDTO;
import dtos.TimelineDTO;
import dtos.UserDTO;
import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Populator {

    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        UserFacade userFacade = UserFacade.getUserFacade(emf);
        RoleFacade roleFacade = RoleFacade.getRoleFacade(emf);
        TimelineFacade timelineFacade = TimelineFacade.getTimelineFacade(emf);
        SpotFacade spotFacade = SpotFacade.getSpotFacade(emf);

        System.out.println("hello");
        List<Timeline> timelineList = timelineFacade.getAll();
        for (Timeline tl : timelineList) {
            System.out.println(tl.getId());

        }
    }
    
    public static void main(String[] args) {
        populate();
    }
}
