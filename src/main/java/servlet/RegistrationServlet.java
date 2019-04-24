package servlet;

import db.H2Milestone;
import db.H2Project;
import db.H2User;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private final H2User H2USER = new H2User();
    private final H2Project H2PROJECT = new H2Project();
    private final H2Milestone H2MILESTONE = new H2Milestone();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        //Id of the user
        String idAsString = request.getParameter("id");

        if(idAsString == null){ // User is not registered yet
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = new User(firstName, lastName, email,password);
            this.H2USER.add(user);

            request.getRequestDispatcher("/LoginServlet").forward(request, response);
        } else { //User is registered and wants to delete their account
            int id = Integer.parseInt(request.getParameter("id"));

            this.H2USER.remove(id);
            request.getRequestDispatcher("views/index.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("views/register.jsp").forward(request, response);
    }
}
