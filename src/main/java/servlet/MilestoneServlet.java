package servlet;

import db.H2;
import db.H2Milestone;
import db.H2Project;
import db.H2User;
import model.Milestone;
import model.Project;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet("/MilestoneServlet")
public class MilestoneServlet extends HttpServlet {
    private final H2Project H2PROJECT = new H2Project();
    private final H2User H2USER = new H2User();
    private final H2Milestone H2MILESTONE = new H2Milestone();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        int userId = Integer.parseInt(request.getParameter("userId"));
        int projectId = Integer.parseInt(request.getParameter("projectId"));

        String description = request.getParameter("description");
        String priority = request.getParameter("priority");
        String dueDate = request.getParameter("dueDate");
        String isCompleted = request.getParameter("isCompleted");

        String method = request.getParameter("_method");
        switch (method){
            case "post":
                Milestone milestone = null;
                try {
                    milestone = new Milestone(projectId, description, this.H2MILESTONE.stringToPriority(priority),
                            H2.setDate(dueDate), false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                this.H2MILESTONE.add(milestone);
                break;
            case "put":
                int id = Integer.parseInt(request.getParameter("id"));
                try {
                    this.H2MILESTONE.update(id, description, this.H2MILESTONE.stringToPriority(priority),
                            H2.setDate(dueDate), Boolean.valueOf(isCompleted));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                this.H2MILESTONE.remove(id);
                break;
        }

        User user = this.H2USER.find(userId);
        user.setSharedMilestones(this.H2MILESTONE.findSharedMilestones(user.getId()));
        user.setProjects(this.H2PROJECT.findByUserId(user.getId()));

        for(Project project : user.getProjects()){
            project.setMilestones(this.H2MILESTONE.findByProjectId(project.getId()));
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
