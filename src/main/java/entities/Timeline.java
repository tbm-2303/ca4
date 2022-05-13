package entities;

import dtos.TimelineDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name ="Timeline")
public class Timeline implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "description", nullable = true, length = 1000)
    private String description;
    @Column(name = "startDate", nullable = false, length = 30)
    private String startDate;
    @Column(name = "endDate", nullable = false, length = 30)
    private String endDate;

    @OneToMany(mappedBy = "timeline", cascade = {CascadeType.ALL})
    private List<Spot> spotList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public Timeline(){

    }
    public Timeline(String name, String description, String startDate, String endDate, List<Spot> spots, User user){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.spotList = spots;
        this.user = user;
    }

    public Timeline(TimelineDTO timelineDTO){
        this.name = timelineDTO.getName();
        this.description = timelineDTO.getDescription();
        this.startDate = timelineDTO.getStartDate();
        this.endDate = timelineDTO.getEndDate();
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
    public List<Spot> getSpotList() {
        return spotList;
    }
    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void addSpot(Spot spot) {
        spotList.add(spot);
        spot.setTimeline(this);
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", spotList="  +
                ", user=" + user +
                '}';
    }
}