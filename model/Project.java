package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Project {
    private int id;
    private int userId;
    private String name;
    private Calendar createdAt;
    private List<Milestone> milestones;


    public Project(String name, int userId){
        this.name = name;
        this.milestones = new ArrayList<Milestone>();
        this.createdAt = Calendar.getInstance();
        this.userId = userId;
    }

    public Project(String name, Calendar createdAt){
        this.name = name;
        this.milestones = new ArrayList<Milestone>();
        this.createdAt = createdAt;
    }

    public Project(int id, int userId, String name, Calendar createdAt){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.milestones = new ArrayList<Milestone>();
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

   

    public void setId(int id) {
        this.id = id;
    }
    
     public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
