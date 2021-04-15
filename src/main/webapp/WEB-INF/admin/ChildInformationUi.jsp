<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Tue Feb 16 2021 06:53:00 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<html data-wf-page="6018e6c7cc4d9a301d13dd45" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Child Information</title>
        <meta content="Manage Children" property="og:title">
        <meta content="Manage Children" property="twitter:title">
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
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch")
            }(window, document);</script>
    </head>
    <body>
        <c:import url="navbar.html" />
        <h1 class="heading-14">${child.childFirstName} ${child.childLastName}</h1>
        <div class="container-4 w-container">

            <c:if test="${param.childId != null}">
                <div div class="w-row">

                    <h3>Registration Details</h3>
                    <table class="table table-hover">
                        <tr>
                            <th>Class</th>
                            <th>Dates</th>
                            <th>Days</th>
                            <th>Registration Status</th>
                            <th>Spots</th>
                            <th></th>
                        </tr>
                        <c:forEach var="reg" items="${child.registrationList}">
                            <tr>
                                <td>${reg.classroom.description}</td>
                                <td><fmt:formatDate pattern="MMMM dd yyyy" value="${reg.classroom.startDate}"/>-<fmt:formatDate pattern="MMMM dd yyyy" value="${reg.classroom.endDate}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${reg.classroom.days eq 'TTh'}">Tuesday/Thursday</c:when>
                                        <c:when test="${reg.classroom.days eq 'MWF'}">Monday/Wednesday/Friday</c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:contains(reg.registrationStatus, 'W')}">Wait listed</c:when>
                                        <c:when test="${fn:contains(reg.registrationStatus, 'N')}">Registration fee not paid</c:when>
                                        <c:when test="${fn:contains(reg.registrationStatus, 'P')}">Registration fee paid, waiting to be accepted</c:when>
                                        <c:when test="${fn:contains(reg.registrationStatus, 'D')}">Registration denied</c:when>
                                        <c:when test="${fn:contains(reg.registrationStatus, 'R')}">Registered</c:when>
                                    </c:choose>
                                </td>
                                <td>${reg.classroom.enrolled}/${reg.classroom.capacity}</td>
                                <td>
                                    <a data-toggle="collapse" href="#${reg.registrationId}" aria-expanded="false" aria-controls="collapseExample">Select registration</a>
                                </td>
                            </tr>
                            <tr class="collapse active in" id="${reg.registrationId}">
                                <td colspan="2">
                                    <form action="/PDFreports" method="post" target="_blank">
                                        <input type="hidden" name="action" value="printRegistration">
                                        <input type="hidden" name="registrationId" value="${reg.registrationId}">
                                        <input type="submit" value="Print Registration" class="search-button-2 w-button">
                                    </form>
                                </td>

                                <td colspan="4">
                                    <form action="childinformation" method="post" class="form-inline">
                                        <select name="class" class="form-control">
                                            <option value="" selected disabled hidden>Select a class</option>
                                            <c:forEach items="${classes}" var="classroom" varStatus="loop">
                                                <option value="${classroom.classroomId}"
                                                        <c:if test="${registration.classroom.classroomId == classroom.classroomId}"> selected</c:if>>${classroom.description} 
                                                    <c:choose>
                                                        <c:when test="${classroom.days eq 'TTh'}">(Tuesday/Thursday)</c:when>
                                                        <c:when test="${classroom.days eq 'MWF'}">(Monday/Wednesday/Friday)</c:when>
                                                    </c:choose>
                                                    <fmt:formatDate pattern='MMMM dd, yyyy' value='${classroom.startDate}'/> - <fmt:formatDate pattern='MMMM dd, yyyy' value='${classroom.endDate}'/></option>
                                                </c:forEach>
                                        </select>
                                        <input type="hidden" name="childId" value="${param.childId}">
                                        <input type="hidden" name="registration" value="${reg.registrationId}">
                                        <input type="submit" value="Change Class" class="button-27 w-button">

                                        <input type="hidden" name="action" value="move">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>


            <hr><br>
            <c:import url="../ChildInformation.jsp" /> 
        </div>

        <c:import url="../footer.jsp" />     
        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>