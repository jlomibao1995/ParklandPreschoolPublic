<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Wed Feb 10 2021 05:38:37 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Checkout</title>
        <meta content="Checkout" property="og:title">
        <meta content="Checkout" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">
        <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css" type="text/css">
        <link href="css/normalize.css" rel="stylesheet" type="text/css">
        <link href="css/webflow.css" rel="stylesheet" type="text/css">
        <link href="css/parkland-preschool-project.webflow.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js" type="text/javascript"></script>
        <script type="text/javascript">WebFont.load({google: {families: ["Merriweather:300,300italic,400,400italic,700,700italic,900,900italic", "Roboto:100,100italic,300,300italic,regular,italic,500,500italic,700,700italic,900,900italic", "Comic Neue:300,300italic,regular,italic,700,700italic"]}});</script>
        <!-- [if lt IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js" type="text/javascript"></script><![endif] -->
        <script type="text/javascript">!function (o, c) {
                var n = c.documentElement, t = " w-mod-";
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch");
            }(window, document);</script>
    </head>
    <body>
        <c:import url="navbar.html" />
        <h1 class="heading-37">Registration Payment</h1>
        <div class="container-7 w-container">

            <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                <table class="table table-hover">
                    <tr>
                        <th>Description</th>
                        <td>${payment.paymentDescription}</td>
                </tr>
                <tr>
                    <th>Registered Child</th>
                    <td>${payment.registration.child.childFirstName} ${payment.registration.child.childLastName}</td>
                </tr>
                <tr>
                    <th>Class</th>
                    <td>${payment.registration.classroom.description}</td>
                </tr>
                <tr>
                    <th>Class Times</th>
                    <td>
                        <fmt:formatDate pattern="hh:mm" value="${payment.registration.classroom.startDate}"/>-<fmt:formatDate pattern="hh:mm" value="${payment.registration.classroom.endDate}"/>
                    </td>
                </tr>
                <tr>
                    <th>Class Days</th>
                    <td>
                        <c:choose>
                            <c:when test="${payment.registration.classroom.days eq 'TTh'}">(Tuesday/Thursday)</c:when>
                            <c:when test="${payment.registration.classroom.days eq 'MWF'}">(Monday/Wednesday/Friday)</c:when>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>Payment made to</th>
                    <td>Parkland Preschool</td>
                </tr>
                <tr>
                    <th>Total</th>
                    <td><fmt:formatNumber value="${payment.paymentTotal}" type="currency" /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <c:if test="${payment.paymentDescription eq 'registration fee'}">
                            <b>To finalize your registration and reserve a spot continue to pay the registration fee with PayPal</b>
                        </c:if>
                    </td>
                </tr>
            </table>


            <form action="/authorizepayment" method="post">
                <input type="hidden" name="paymentId" value="${payment.paymentId}"/>
                <input type="submit" class="button-27 w-button" value="Sign In with PayPal"/>
            </form>
            <a href="/managechildren" class="btn btn-link">Pay Later</a>
        </div>

        <c:import url="../footer.jsp" />

        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>