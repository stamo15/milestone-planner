<%--
  Created by IntelliJ IDEA.
  User: Salmane Tamo
  Date: 05/04/2019
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--Redirect to login page if not logged in--%>
<c:if test="${user == null}">
    <c:redirect url = "/views/login.jsp"/>
</c:if>

<%--Displaying dashboard--%>
<html>
    <head>
        <link rel="stylesheet" href="../css/style.css" type="text/css">
        <%--    <link rel="stylesheet" href="../css/materialize.css" type="text/css">--%>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="icon" type="image/jpg" href="../images/favicon.jpg">
        <title>MilestonePlanner | Dashboard</title>
    </head>
    <body id="dashboard">
        <header>
            <nav>
                <div class="nav-wrapper row">
                    <div class="col s3 left">
                        <a href="#!" class="brand-logo" style="font-size: 1.5rem;/*! font-style: unset; */font-family: 'Courier New', Courier, monospace;">Milestone Planner</a>
                    </div>
                    <div class="col s6 hide-on-med-and-down">
                        <form>
                            <div class="input-field">
                                <input id="search" type="search" placeholder="Search here..." required>
                                <label class="label-icon" for="search"><i class="material-icons">search</i></label>
                                <i class="material-icons">close</i>
                            </div>
                        </form>
                    </div>
                    <div class="col s3">
                        <div id="dropdown-user" class="dropdown-content">
                            <ul>
                                <li><a href="#!"><i class="material-icons">perm_identity</i>Edit profile</a></li>
                                <li><a data-target="logout-modal" href="!#" class="modal-trigger"><i class="material-icons">exit_to_app</i>Logout</a></li>
                                <li class="divider"></li>
                                <li><a data-target="unregister-modal" href="!#" class="modal-trigger"><i class="material-icons">power_settings_new</i>Delete Account</a></li>
                            </ul>
                        </div>
                        <div id="logout-modal" class="modal modal-fixed-footer teal-text">
                            <div class="modal-content">
                                <h2>Logout </h2>
                                <p>Are your sure you want to logout?</p>
                            </div>
                            <div class="modal-footer row">
                                <div class="modal-close col s4 offset-s1 center-align">
                                    <form action="LoginServlet" method="post">
                                        <input type="hidden" name="id" value="${user.getId()}">
                                        <button type="submit" value="Logout" class="waves-effect waves-light btn btn-block red">Logout</button>
                                    </form>
                                </div>
                                <div class="modal-close col s4 offset-s2 center-align">
                                    <button class="waves-effect waves-light btn btn-block light-green accent-4">Stay connected</button>
                                </div>
                            </div>
                        </div>
                        <div id="unregister-modal" class="modal modal-fixed-footer teal-text">
                            <div class="modal-content">
                                <h2>Delete account </h2>
                                <p>Are your sure you want to delete your account?</p>
                            </div>
                            <div class="modal-footer row">
                                <div class="modal-close col s4 offset-s1 center-align">
                                    <form action="RegistrationServlet" method="post">
                                        <input type="hidden" name="id" value="${user.getId()}">
                                        <button type="submit" value="Delete Account" class="waves-effect waves-light btn btn-block red">Delete account</button>
                                    </form>
                                </div>
                                <div class="modal-close col s4 offset-s2 center-align">
                                    <button class="waves-effect waves-light btn btn-block light-green accent-4">Stay connected</button>
                                </div>
                            </div>
                        </div>
                        <ul class="right">
                            <li><a class="btn btn-depressed dropdown-trigger dropdown-fixed-width" href="#!" data-target="dropdown-user"><i class="material-icons white-text right">arrow_drop_down</i> ${user.getFirstName()} </a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <main style="min-height: 700px;">
            <div>
                <c:set var = "countProjects" scope = "session" value = "${user.getProjects().size()}"/>
                <c:choose>
                    <%--No projects--%>
                    <c:when test="${countProjects == 0}">
                        <p style="margin: 0; text-align: center"><br>You have 0 projects.</p>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="i" begin = "0" end="${countProjects}" step="3">
                            <div class="row section container">
                            <c:forEach var="j" begin = "${i}" end="${i + 2}">
                                <c:choose>
                                    <c:when test="${j < countProjects}">
                                        <c:set var = "project" scope = "session" value = "${user.getProjects().get(j)}"/>
                                        <div class="col s4">
                                            <div class="col-content" style="overflow: auto;max-height: 400px; box-shadow: 0 15px 20px -15px rgba(0, 0, 0, 0.61), 0 55px 50px -35px rgba(0, 0, 0, 0.05), 0 85px 60px -25px rgba(0, 0, 0, 0.1)">
                                                <div class="responsive-table table-status-sheet">
                                                    <table class="border" style="background-color:#fff9c4">
                                                        <thead>
                                                            <tr>
                                                                <td>
                                                                    <div>
                                                                        <p style="font-style: italic;">
                                                                            <c:out value="${project.getName()}"/>
                                                                            <a class="dropdown-trigger dropdown-fixed-width" href="#!" data-target="dropdown-project${project.getId()}">
                                                                                <i class="material-icons right">more_vert</i>
                                                                            </a>
                                                                        </p>
                                                                        <small class="left">${project.getMilestones().size()} miletones</small>
                                                                        <small class="right">Created on 12/12/12</small>
                                                                        <div id="dropdown-project${project.getId()}" class="dropdown-content">
                                                                            <ul>
                                                                                <li>
                                                                                    <a data-target="edit-project-modal${project.getId()}" href="!#" class="modal-trigger">
                                                                                        <i class="material-icons">edit</i>Edit project</a>
                                                                                </li>
                                                                                <li>
                                                                                    <a data-target="delete-project-modal${project.getId()}" href="!#" class="modal-trigger">
                                                                                        <i class="material-icons">delete</i>Delete project</a>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:choose>
                                                                <c:when test="${project.getMilestones().size() == 0}">
                                                                    <tr>
                                                                        <td>
                                                                            <p style="font-style: italic;">This project has no milestones<br>How about you set some?</p>
                                                                        </td>
                                                                    </tr>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:forEach items="${project.getMilestones()}" var="milestone">
                                                                        <tr>
                                                                            <td>
                                                                                <div class="row">
                                                                                    <div class="col s12 m12">
                                                                                        <div class="card blue-grey darken-1">
                                                                                            <div class="card-content white-text">
                                                                                                <div class="card-title">
                                                                                                    ${milestone.getDescription()}
                                                                                                        <a class="dropdown-trigger dropdown-fixed-width" href="#!" data-target="dropdown-milestone${milestone.getId()}">
                                                                                                            <i class="material-icons right">more_vert</i>
                                                                                                        </a>
                                                                                                        <div id="dropdown-milestone${milestone.getId()}" class="dropdown-content">
                                                                                                            <ul>
                                                                                                                <li>
                                                                                                                    <a data-target="edit-milestone-modal${milestone.getId()}" href="!#" class="modal-trigger">
                                                                                                                        <i class="material-icons">edit</i>Edit milestone</a>
                                                                                                                </li>
                                                                                                                <li>
                                                                                                                    <a data-target="share-milestone-modal${milestone.getId()}" href="!#" class="modal-trigger">
                                                                                                                        <i class="material-icons">delete</i>Share milestone</a>
                                                                                                                </li>
                                                                                                                <li>
                                                                                                                    <a data-target="delete-milestone-modal${milestone.getId()}" href="!#" class="modal-trigger">
                                                                                                                        <i class="material-icons">delete</i>Delete milestone</a>
                                                                                                                </li>
                                                                                                            </ul>
                                                                                                        </div>
                                                                                                    <c:choose>
                                                                                                        <c:when test="${milestone.getPriority().toString() == \"HIGH\"}">
                                                                                                            <span class="red badge right" style="color: white; border-radius: 7%; font-weight: bold">HIGH</span>
                                                                                                        </c:when>
                                                                                                        <c:when test="${milestone.getPriority().toString() == \"MEDIUM\"}">
                                                                                                            <span class="yellow badge right" style="color: white; border-radius: 7%; font-weight: bold">MEDIUM</span>
                                                                                                        </c:when>
                                                                                                        <c:otherwise>
                                                                                                            <span class="green badge right" style="color: white; border-radius: 7%; font-weight: bold">LOW</span>
                                                                                                        </c:otherwise>
                                                                                                    </c:choose>
                                                                                                </div>
                                                                                                <ul>
                                                                                                    <li>
                                                                                                        <i class="material-icons">date_range</i>
                                                                                                        <small>Due date: <fmt:formatDate value="${milestone.getDueDate().getTime()}" pattern="yyyy-MM-dd"/></small>
                                                                                                    </li>
                                                                                                    <c:choose>
                                                                                                        <c:when test="${milestone.isCompleted() == true}">
                                                                                                            <li>
                                                                                                                <i class="material-icons">check_box</i>
                                                                                                                <small>Completed on: <fmt:formatDate value="${milestone.getCompletionDate().getTime()}" pattern="yyyy-MM-dd"/></small>
                                                                                                            </li>
                                                                                                        </c:when>
                                                                                                        <c:otherwise>
                                                                                                            <li>
                                                                                                                <i class="material-icons">check_box_outline_blank</i>
                                                                                                                <small>Not completed</small>
                                                                                                            </li>
                                                                                                        </c:otherwise>
                                                                                                    </c:choose>
                                                                                                </ul>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div id="edit-milestone-modal${milestone.getId()}" class="modal modal-fixed-footer teal-text">
                                                                                    <div class="modal-content">
                                                                                        <h2>Edit milestone</h2>
                                                                                        <div>
                                                                                            <form action="MilestoneServlet" method="post">
                                                                                                <input type="hidden" name="id" value="${milestone.getId()}">
                                                                                                <input type="hidden" name="userId" value="${user.getId()}">
                                                                                                <input type="hidden" name="projectId" value="${project.getId()}">
                                                                                                <input type="hidden" name="_method" value="put">
                                                                                                <div class="input-field col s12">
                                                                                                    <i class="material-icons prefix">description</i>
                                                                                                    <input type="text" name="description" placeholder="Milestone description" id="description${milestone.getId()}" value="${milestone.getDescription()}">
                                                                                                    <label for="description${milestone.getId()}">Milestone Description</label>
                                                                                                </div>
                                                                                                <div class="input-field col s12">
                                                                                                    <select name="priority" id="priority${milestone.getId()}">
                                                                                                        <c:forTokens items="LOW,MEDIUM,HIGH" delims="," var="priority">
                                                                                                            <option value="${priority}"
                                                                                                                    <c:if test="${milestone.getPriority().toString() == priority}">
                                                                                                                        <c:out value="selected"/>
                                                                                                                    </c:if> >${priority}
                                                                                                            </option>
                                                                                                        </c:forTokens>
                                                                                                    </select>
                                                                                                    <label for="priority${milestone.getId()}">Milestone priority</label>
                                                                                                </div>
                                                                                                <div class="input-field col s12">
                                                                                                    <input id="dueDate${milestone.getId()}" type="date" name="dueDate" value=<fmt:formatDate value="${milestone.getDueDate().getTime()}" pattern="yyyy-MM-dd"/>>
                                                                                                    <label for="dueDate${milestone.getId()}">Milestone due date</label>
                                                                                                </div>
                                                                                                <c:choose>
                                                                                                    <c:when test="${milestone.isCompleted() == true}">
                                                                                                        <div class="input-field col s12">
                                                                                                            <label>
                                                                                                                <input type="radio" name="isCompleted" value="true" checked><span>Completed</span>
                                                                                                            </label>
                                                                                                        </div>
                                                                                                        <div class="input-field col s12">
                                                                                                            <label>
                                                                                                                <input type="radio" name="isCompleted" value="false"><span>Not Completed</span>
                                                                                                            </label>
                                                                                                        </div>
                                                                                                    </c:when>
                                                                                                    <c:otherwise>
                                                                                                        <div class="input-field col s12">
                                                                                                            <label>
                                                                                                                <input type="radio" name="isCompleted" value="true"><span>Completed</span>
                                                                                                            </label>
                                                                                                        </div>
                                                                                                        <div class="input-field col s12">
                                                                                                            <label>
                                                                                                                <input type="radio" name="isCompleted" value="false" checked><span>Not Completed</span>
                                                                                                            </label>
                                                                                                        </div>
                                                                                                    </c:otherwise>
                                                                                                </c:choose>
                                                                                                <div class="input-field col s12">
                                                                                                    <button class="waves-effect waves-light btn btn-block light-green accent-4 modal-close" type="submit">Save
                                                                                                        <i class="material-icons right">save</i>
                                                                                                    </button>
                                                                                                </div>
                                                                                            </form>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div id="delete-milestone-modal${milestone.getId()}" class="modal modal-fixed-footer teal-text">
                                                                                    <div class="modal-content">
                                                                                        <h2>Delete project</h2>
                                                                                        <p>Are you sure you want to delete this milestone?</p>
                                                                                        <div>
                                                                                            <form action="MilestoneServlet" method="post">
                                                                                                <input type="hidden" name="id" value="${milestone.getId()}">
                                                                                                <input type="hidden" name="userId" value="${user.getId()}">
                                                                                                <input type="hidden" name="projectId" value="${project.getId()}">
                                                                                                <input type="hidden" name="_method" value="delete">
                                                                                                <div class="input-field col s12">
                                                                                                    <button class="waves-effect waves-light btn btn-block red modal-close" type="submit">Delete
                                                                                                        <i class="material-icons right">delete</i>
                                                                                                    </button>
                                                                                                </div>
                                                                                            </form>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <a  data-target="add-milestone-modal${project.getId()}" href="!#" class="btn-floating btn-large waves-effect waves-light red modal-trigger" style="bottom: 35px;float: right;right: 15px;"><i class="material-icons">add</i></a>
                                        </div>
                                        <div id="add-milestone-modal${project.getId()}" class="modal modal-fixed-footer teal-text">
                                            <div class="modal-content">
                                                <h2>Add milestone</h2>
                                                <div>
                                                    <form action="MilestoneServlet" method="post">
                                                        <input type="hidden" name="userId" value="${user.getId()}">
                                                        <input type="hidden" name="projectId" value="${project.getId()}">
                                                        <input type="hidden" name="_method" value="post">
                                                        <div class="input-field col s12">
                                                            <i class="material-icons prefix">description</i>
                                                            <input type="text" name="description" placeholder="Milestone description" id="descriptionNew">
                                                            <label for="descriptionNew">Milestone Description</label>
                                                        </div>
                                                        <div class="input-field col s12">
                                                            <select name="priority" id="priorityNew">
                                                                <c:forTokens items="LOW,MEDIUM,HIGH" delims="," var="priority">
                                                                    <option value="${priority}">${priority}</option>
                                                                </c:forTokens>
                                                            </select>
                                                            <label for="priorityNew">Milestone priority</label>
                                                        </div>
                                                        <div class="input-field col s12">
                                                            <input id="dueDateNew" type="date" name="dueDate">
                                                            <label for="dueDateNew">Milestone due date</label>
                                                        </div>
                                                        <div class="input-field col s12">
                                                            <button class="waves-effect waves-light btn btn-block light-green accent-4 modal-close" type="submit">Add milestone
                                                                <i class="material-icons right">add</i>
                                                            </button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="edit-project-modal${project.getId()}" class="modal modal-fixed-footer teal-text">
                                            <div class="modal-content">
                                                <h2>Edit project</h2>
                                                <div>
                                                    <form action="ProjectServlet" method="post">
                                                        <input type="hidden" name="userId" value="${user.getId()}">
                                                        <input type="hidden" name="id" value="${project.getId()}">
                                                        <input type="hidden" name="_method" value="put">
                                                        <div class="input-field col s12">
                                                            <i class="material-icons prefix">description</i>
                                                            <input type="text" name="name" placeholder="Project name" id="name${project.getId()}" value="${project.getName()}">
                                                            <label for="name${project.getId()}">Project name</label>
                                                        </div>

                                                        <div class="input-field col s12">
                                                            <button class="waves-effect waves-light btn btn-block light-green accent-4 modal-close" type="submit">Save
                                                                <i class="material-icons right">save</i>
                                                            </button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="delete-project-modal${project.getId()}" class="modal modal-fixed-footer teal-text">
                                            <div class="modal-content">
                                                <h2>Delete project</h2>
                                                <p>Are you sure you want to delete this project?</p>
                                                <div>
                                                    <form action="ProjectServlet" method="post">
                                                        <input type="hidden" name="userId" value="${user.getId()}">
                                                        <input type="hidden" name="id" value="${project.getId()}">
                                                        <input type="hidden" name="_method" value="delete">
                                                        <div class="input-field col s12">
                                                            <button class="waves-effect waves-light btn btn-block red modal-close" type="submit">Delete
                                                                <i class="material-icons right">delete</i>
                                                            </button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-content">
                                            <div class="col s4"></div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <a  data-target="add-project-modal${user.getId()}" href="!#" class="btn-large waves-effect waves-light btn-depressed modal-trigger" style="margin-left: 70%;margin-bottom: 20px; bottom: 2%; position:fixed">Add Project</a>
                <div id="add-project-modal${user.getId()}" class="modal modal-fixed-footer teal-text">
                    <div class="modal-content">
                        <h2>Add project</h2>
                        <div>
                            <form action="ProjectServlet" method="post">
                                <input type="hidden" name="userId" value="${user.getId()}">
                                <input type="hidden" name="_method" value="post">
                                <div class="input-field col s12">
                                    <i class="material-icons prefix">description</i>
                                    <input type="text" name="name" placeholder="Project name" id="nameNew">
                                    <label for="nameNew">Project name</label>
                                </div>

                                <div class="input-field col s12">
                                    <button class="waves-effect waves-light btn btn-block light-green accent-4 modal-close" type="submit">Add project
                                        <i class="material-icons right">add</i>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <script src="https://code.jquery.com/jquery-3.4.0.js"
                integrity="sha256-DYZMCC8HTC+QDr5QNaIcfR7VSPtcISykd+6eSmBW5qo="
                crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <script src="../js/js.js"></script>
    </body>
</html>
