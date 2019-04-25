<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 05/04/2019
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../css/style.css" type="text/css">
    <%--    <link rel="stylesheet" href="../css/materialize.css" type="text/css">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="icon" type="image/jpg" href="../images/favicon.jpg">
    <title>MilestonePlanner | login</title>
</head>
<body style="	background-image: linear-gradient(to right, #61892f,#86c232);
">
    <section id="login" class="container section">
        <div  class="row">
            <div class="col s12 l8 offset-l2">
            <ul class="tabs tabs-fixed-width z-depth-1">
                <li class="tab white"><a class="light-green-text text-accent-4 text-bold active" href="#login-form">Login</a></li>
                <li class="tab white"><a class="light-green-text text-accent-4 text-bold" href="#registration-form">Register</a></li>
            </ul>
        </div>
        </div>
        <div  class="row">
            <div class="col s12 l8 offset-l2">
                <div id="login-form">
                    <form method="post" action="LoginServlet">
                        <div class="row">
                            <div class="input-field col s12">
                                <i class="material-icons prefix">email</i>
                                <input type="text" name="email" placeholder="john@domainname.com" id="login_email">
                                <label for="login_email">Email</label>
                            </div>

                            <div class="input-field col s12">
                                <i class="material-icons prefix">lock_outline</i>
                                <input type="password" name="password" placeholder="password" id="login_password">
                                <label for="login_password">Password</label>
                            </div>
                            <div class="input-field col s12">
                                <button class="waves-effect waves-light btn light-green accent-4 right" type="submit">Login
                                    <i class="material-icons right">send</i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div  class="row" >
            <div class="col s12 l8 offset-l2">
                <div id="registration-form">
                    <form method="post" action="RegistrationServlet">
                        <div class="row">
                            <div class="input-field col s12">
                                <i class="material-icons prefix">account_circle</i>
                                <input type="text" name="firstName" placeholder="John" id="firstName">
                                <label for="firstName">First name</label>
                            </div>

                            <div class="input-field col s12">
                                <i class="material-icons prefix">account_circle</i>
                                <input type="text" name="lastName" placeholder="Doe" id="lastName">
                                <label for="lastName">Last name</label>
                            </div>

                            <div class="input-field col s12">
                                <i class="material-icons prefix">email</i>
                                <input type="text" name="email" placeholder="john@domainname.com" id="register_email">
                                <label for="register_email">Email</label>
                            </div>

                            <div class="input-field col s12">
                                <i class="material-icons prefix">lock_outline</i>
                                <input type="password" name="password" placeholder="password" id="register_password">
                                <label for="register_password">Password</label>
                            </div>

                            <div class="input-field col s12">
                                <i class="material-icons prefix">lock_outline</i>
                                <input type="password" name="confirmPassword" placeholder="Confirm password" id="confirm_password">
                                <label for="confirm_password">Confirm password</label>
                            </div>

                            <div class="input-field col s12">
                                <button class="waves-effect waves-light btn light-green accent-4 right" type="submit">Sign up
                                    <i class="material-icons right">send</i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>

<script src="https://code.jquery.com/jquery-3.4.0.js"
        integrity="sha256-DYZMCC8HTC+QDr5QNaIcfR7VSPtcISykd+6eSmBW5qo="
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script src="../js/js.js"></script>
</html>
