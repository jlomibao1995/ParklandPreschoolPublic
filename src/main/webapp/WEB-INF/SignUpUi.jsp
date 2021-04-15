<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Sat Feb 06 2021 02:16:24 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html data-wf-page="6018e23a2305aad929e81f16" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Sign Up</title>
        <meta content="Untitled" property="og:title">
        <meta content="Untitled" property="twitter:title">
        <meta content="width=100, initial-scale=1" name="viewport">
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

            <div style="display: block;margin: auto;width: 49%">
                <c:if test="${success != null && success}"><div class="alert alert-success" >The account has been added successfully. Please check your email to activate your account.</div></c:if>
                <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if> 
                </div>           

                <div class="container-9 w-container">   
                    <h1 class="heading-29">Sign Up</h1>
                    <div class="w-form">

                        <form id="wf-form-Contact-Form" name="wf-form-Contact-Form" data-name="Contact Form" action="signup" method="post">
                            <div class="w-row">
                                <div class="column-11 w-col w-col-6" style="border-right: 2px; border-right-color: #1679c4;">
                                    <label style="color: white;">First name</label>
                                    <input type="text" class="text-field-14 w-input" maxlength="256" name="firstName" value="${firstName}"
                                       required pattern="[a-zA-Z]+" title="Name can't contain special characters">

                                <label style="color: white;">Last name</label>
                                <input type="text" class="text-field-14 w-input" maxlength="256" name="lastName"
                                       required pattern="[a-zA-Z]+" title="Name can't contain special characters" value="${lastName}">

                                <label style="color: white;">Address</label>
                                <input type="text" class="text-field-19 w-input" maxlength="256" name="address" value="${address}" pattern="[a-zA-z0-9-\.\s]+" required>

                                <label style="color: white;">City</label>
                                <input type="text" class="text-field-19 w-input" maxlength="256" name="city" value="Calgary" required readonly>

                                <label style="color: white;">Province</label>
                                <input type="text" class="text-field-15 w-input" maxlength="256" name="province" value="Alberta" required readonly>

                                <label style="color: white;">Password</label>
                                <input type="password" class="text-field-21 w-input" maxlength="256" name="password" id="password" required
                                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"> 
                                <div id="passwordMessage" class=""  style="color: white;"></div>

                            </div>
                            <div class="column-12 w-col w-col-6">

                                <label style="color: white;">Email</label>
                                <input type="email" class="text-field-20 w-input" maxlength="256" name="email" value="${email}" required>

                                <label style="color: white;">Home phone</label>
                                <input type="tel" class="text-field-15 w-input" maxlength="256" name="homePhone" value="${homePhone}"
                                       pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required> 

                                <label style="color: white;">Cell phone</label>
                                <input type="tel" class="text-field-16 w-input" maxlength="256" name="cellPhone" required value="${cellPhone}"
                                       pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required>

                                <label style="color: white;">Work phone</label>
                                <input type="tel" class="text-field-17 w-input" maxlength="256" name="workPhone" value="${workPhone}"
                                       pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required>    

                                <label style="color: white;">Postal code</label>
                                <input type="text" id="postalCode" class="text-field-15 w-input" maxlength="256" name="postalCode" required value="${postalCode}"
                                       pattern="[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]" title="Postal code must be in the format of A1B 2C3"> 

                                <label style="color: white;">Confirm password</label>
                                <input type="password" class="text-field-18 w-input" maxlength="256" name="confirmPassword" id="confirmPassword" required>

                                <div class="" id="passwordMatch"  style="color: white;"></div>
                            </div>
                        </div>
                        <input type="submit" value="Sign Up " data-wait="Please wait..." class="submit-button-11 w-button">
                    </form>
                </div>
            </div>
            <div class="lead center-block" style="text-align: center">
                <br>Already have an account? <a href="/login">Login here.</a>
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
