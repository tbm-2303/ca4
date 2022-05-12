package dtos;

import entities.Spot;
import entities.Timeline;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class TimelineDTO {
    private Integer id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private List<SpotDTO> spotDTOList = new ArrayList<>();
    private UserDTO userDTO;

    public TimelineDTO() {
    }

    public TimelineDTO( String name, String description, String startDate, String endDate, UserDTO userDTO) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userDTO = userDTO;
    }

    public TimelineDTO(Timeline timeline) {
        this.id = timeline.getId();
        this.name = timeline.getName();
        this.description = timeline.getDescription();
        this.startDate = timeline.getStartDate();
        this.endDate = timeline.getEndDate();
        this.userDTO = new UserDTO(timeline.getUser());
        for (Spot spot : timeline.getSpotList()) {
            this.spotDTOList.add(new SpotDTO(spot));
        }

    }

    public static List<TimelineDTO> getDtos(List<Timeline> timelines) {
        List <TimelineDTO> timelineDTOS = new ArrayList<>();
        if (timelines != null){
            timelines.forEach(t -> timelineDTOS.add(new TimelineDTO(t)));
        }
        return timelineDTOS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<SpotDTO> getSpotList() {
        return spotDTOList;
    }

    public void setSpotList(List<SpotDTO> spotList) {
        this.spotDTOList = spotList;
    }

    public UserDTO getUser() {
        return userDTO;
    }

    public void setUser(UserDTO user) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        return "TimelineDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", spotList=" + spotDTOList +
                ", user=" + userDTO +
                '}';
    }
}