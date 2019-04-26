package model;

import db.HashingUtil;

import java.util.Calendar;

public class Milestone {
    private int id;
    private int projectId;
    private String description;
    private Calendar completionDate;
    private Calendar dueDate;
    private boolean isCompleted;
    private Priority priority;

    public Milestone(String description, Priority priority){
        this.description = description;
        this.priority = priority;
        this.completionDate = Calendar.getInstance();
        this.dueDate = Calendar.getInstance();
        this.isCompleted = false;
    }

    public Milestone(int projectId, String description, Priority priority,
                     Calendar dueDate, boolean isCompleted){
        this.description = description;
        this.priority = priority;
        this.completionDate = null;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.projectId = projectId;
    }

    public Milestone(int id, int projectId, String description, Priority priority,
                     Calendar completionDate, Calendar dueDate, boolean isCompleted){
        this.description = description;
        this.priority = priority;
        this.completionDate = completionDate;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.projectId = projectId;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Calendar completionDate) {
        this.completionDate = completionDate;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int id) {
        this.projectId = id;
    }

    public String getHash(){
        return HashingUtil.getHashedPassword(Integer.toString(this.id), "");
    }
}
