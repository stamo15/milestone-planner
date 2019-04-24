<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 04/04/2019
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Milestone Planner</title>
    <link rel="stylesheet" href="../css/style.css" type="text/css">
    <link rel="icon" type="image/jpg" href="../images/favicon.jpg">
<%--    <link rel="stylesheet" href="../css/materialize.css" type="text/css">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body id="index" style="background: #203A43">
    <div class="main-container" style="height: auto">
        <div class="title">
            <h4>milestoneplanner</h4>
        </div>
        <div class="hero">
            <h1>Take control,<br>Manage your time and achieve your goals.</h1>
        </div>
        <div class="description">
            <p>
                <span>milestoneplanner</span> allows you to better achieve your goals through milestones that you set
                for yourself. You can create projects and have different milestones for each project. This is a way for you to not
                only track your progress but also hold you accountable.
            </p>
        </div>
        <br>
        <div class="buttons center">
            <a class="waves-effect waves-light btn light-green accent-4" href="LoginServlet">Login</a>
            <a class="waves-effect waves-light btn light-green accent-4" href="RegistrationServlet">Register</a>
        </div>
    </div>
</body>
</html>
