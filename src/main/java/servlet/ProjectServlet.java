package servlet;

import db.H2Milestone;
import db.H2Project;
import db.H2User;
import model.Project;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ProjectServlet")
public class ProjectServlet extends HttpServlet {
    private final H2Project H2PROJECT = new H2Project();
    private final H2User H2USER = new H2User();
    private final H2Milestone H2MILESTONE = new H2Milestone();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        int userId = Integer.parseInt(request.getParameter("userId"));

        String name = request.getParameter("name");

        String method = request.getParameter("_method");
        switch (method){
            case "post":
                Project project = new Project(name, userId);
                this.H2PROJECT.add(project);
                break;
            case "put":
                int id = Integer.parseInt(request.getParameter("id"));
                this.H2PROJECT.update(id, name);
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                this.H2PROJECT.remove(id);
                break;
        }

        User user = this.H2USER.find(userId);
        user.setProjects(this.H2PROJECT.findByUserId(user.getId()));

        for(Project project : user.getProjects()){
            project.setMilestones(this.H2MILESTONE.findByProjectId(project.getId()));
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
    }

}
