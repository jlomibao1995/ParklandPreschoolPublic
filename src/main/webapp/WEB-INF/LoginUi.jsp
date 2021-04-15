<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Wed Feb 10 2021 02:54:29 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html data-wf-page="601de8952f9a5a3d84f4e889" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Login</title>
        <meta content="Login" property="og:title">
        <meta content="Login" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">
        <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css" type="text/css">
        <link href="css/normalize.css" rel="stylesheet" type="text/css">
        <link href="css/webflow.css" rel="stylesheet" type="text/css">
        <link href="css/parkland-preschool-project.webflow.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js" type="text/javascript"></script>
        <script type="text/javascript">WebFont.load({google: {families: ["Merriweather:300,300italic,400,400italic,700,700italic,900,900italic", "Open Sans:300,300italic,400,400italic,600,600italic,700,700italic,800,800italic", "Roboto:100,100italic,300,300italic,regular,italic,500,500italic,700,700italic,900,900italic", "Comic Neue:300,300italic,regular,italic,700,700italic"]}});</script>
        <!-- [if lt IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js" type="text/javascript"></script><![endif] -->
        <script type="text/javascript">!function (o, c) {
                var n = c.documentElement, t = " w-mod-";
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch")
            }(window, document);</script>
    </head>
    <body>
        <c:import url="navbar.html" />
        <section id="contact-form" class="contact-form-2"> 

            <div class="container-5 w-container">
                <div class="w-form">    
                    <c:choose>
                        <c:when test="${mode eq 'forgot'}">
                            <h1 class="heading-36">Forgot password</h1>
                            <form action="login" method="post">
                                <input type="email" name="email" data-name="Email" placeholder="Email Address" class="text-field-25 w-input">
                                <input type="hidden" name="action" value="forgotPassword">
                                <input type="hidden" name="mode" value="forgot">
                                <input type="submit" value="Send reset link" class="submit-button-14 w-button">
                                <a href="login" class="link-3">Login</a>
                            </form>                               
                        </c:when>
                        <c:when test="${mode eq 'login'}">
                            <form action="login" method="post">
                                <h1 class="heading-36">Login</h1>
                                <input type="email" value="${email}" class="text-field-25 w-input" maxlength="256" name="email" data-name="Email" placeholder="Email Address" id="Email">
                                <input type="password" value="${password}" class="text-field-25 w-input" maxlength="256" name="password" data-name="Password" placeholder="Password" id="Password" required=""> 

                                <input type="submit" value="Login" data-wait="Please wait..." class="submit-button-14 w-button">
                                <input type="hidden" name="action" value="login">
                                <input type="hidden" name="mode" value="login">
                                <a href="login?action=forgot" class="link-3">Forgot Password?</a>
                            </form>
                        </c:when>
                        <c:when test="${mode eq 'reset' || uuid != null}">
                            <h1 class="heading-36">Reset password</h1>
                            <form action="login" method="post">
                                <input type="password" class="text-field-25 w-input" maxlength="256" name="password" placeholder="Password" id="password" required
                                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$">

                                <div id="passwordMessage" class="">
                                    <b> Password must have the following:" +
                                        <br> At least 8 characters
                                        <br> At least one uppercase and lowercase letter
                                        <br> At least one number
                                        <br> At least one special character e.g. !@#? as long as its not < or > </b>
                                </div><br>

                                <input type="password" class="text-field-25 w-input" maxlength="256" name="confirmPassword" data-name="Password" placeholder="Confirm password" maxlength="256" name="confirmPassword" id="confirmPassword" required>
                                <br><div class="" id="passwordMatch"></div>
                                <input type="hidden" name="uuid" value="${uuid}"/>
                                <input type="hidden" name="mode" value="reset">
                                <input type="submit" value="Change password" data-wait="Please wait..." class="submit-button-14 w-button">
                                <input type="hidden" name="action" value="resetPassword">
                            </form>
                        </c:when>
                    </c:choose>

                    <c:if test="${success != null && success}"><div>${message}</div></c:if>
                    <c:if test="${success != null && !success}"><div><br><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ${message}</div></c:if> 
                    </div>                        
                </div>   
                <div class="lead center-block" style="text-align: center">
                    <br>Don't have an account? <a href="/signup">Sign Up.</a>
                </div>
            </section>

        <c:import url="footer.jsp" />

        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <script src="js/reactive.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>
