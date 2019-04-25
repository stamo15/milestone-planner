package model;

import db.H2Project;

import java.util.ArrayList;
import java.util.List;

public class MilestonePlanner {
    private List<Project> projects;
    private H2Project h2Project;

    public MilestonePlanner(){
        this.projects = new ArrayList<Project>();
        this.h2Project = new H2Project();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public H2Project getH2Project() {
        return h2Project;
    }

    public void setH2Project(H2Project h2Project) {
        this.h2Project = h2Project;
    }
}
