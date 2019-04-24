/**
 * Login Servlet
 *
 * @author Tojosoa Ramarlina
 */

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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private final H2User H2USER = new H2User();
    private final H2Project H2PROJECT = new H2Project();
    private final H2Milestone H2MILESTONE = new H2Milestone();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        //Id of the user
        String idAsString = request.getParameter("id");

        if(idAsString == null){ //User is logging in
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = this.H2USER.findByEmailPassword(email, password);
            System.out.print("Got the user about to find his projects");
            if(user != null){
                user.setProjects(this.H2PROJECT.findByUserId(user.getId()));
                for(Project project : user.getProjects()){
                    project.setMilestones(this.H2MILESTONE.findByProjectId(project.getId()));
                }
                request.setAttribute("user", user);
                request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
            }
        } else { // User is already logged in and wants to log out
            request.getRequestDispatcher("views/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }
}
