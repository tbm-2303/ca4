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
        List<TimelineDTO> timelineDTOS = timelineFacade.getAll2();
        StringBuilder stringBuilder2 = new StringBuilder();
        for (TimelineDTO tl : timelineDTOS) {
            stringBuilder2.append(tl.toString()).append("\n");
        }
        System.out.println(stringBuilder2.toString());

        List<UserDTO> userDTOS = userFacade.getAllUsers();
        StringBuilder stringBuilder = new StringBuilder();

        for (UserDTO x : userDTOS) {
            stringBuilder.append(x.toString()).append("\n");
        }
        System.out.println(stringBuilder.toString());
    }
    
    public static void main(String[] args) {
        populate();
    }
}
