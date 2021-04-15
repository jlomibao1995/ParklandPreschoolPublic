<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Thu Feb 04 2021 01:43:54 GMT+0000 (Coordinated Universal Time)  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html data-wf-page="6016658445ef1c94c64b1a2b" data-wf-site="5fcbddeaf750a0c0eb0dba3c">


    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Manage Classes</title>
        <meta content="Manage Classroom" property="og:title">
        <meta content="Manage Classroom" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">
        <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css" type="text/css">
        <link href="css/normalize.css" rel="stylesheet" type="text/css">
        <link href="css/webflow.css" rel="stylesheet" type="text/css">
        <link href="css/parkland-preschool-project.webflow.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js" type="text/javascript"></script>
        <script type="text/javascript">WebFont.load({google: {families: ["Merriweather:300,300italic,400,400italic,700,700italic,900,900italic", "Open Sans:300,300italic,400,400italic,600,600italic,700,700italic,800,800italic", "Roboto:100,100italic,300,300italic,regular,italic,500,500italic,700,700italic,900,900italic", "Comic Neue:300,300italic,regular,italic,700,700italic"]}});</script>
    </head>

    <body>
        <c:import url="navbar.html" />

        <div class="w-container">

            <!-- Modal Dialogue Box-->
            <div class="modal fade" id="promptDialogue" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="ModalTitle">Modal title</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" id="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="button" onclick="deleteClass()" class="btn btn-danger">Delete Class</button>
                        </div>
                    </div>
                </div>
            </div>

            <h1 class="heading-24">View Classes</h1>

            <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                <div class="w-form">      
                    <h2>Classes</h2>
                    <table class="table table-hover">
                        <tr>
                            <th>Description</th>
                            <th>Days</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Enrolled</th>
                            <td></td>
                        </tr>
                    <c:forEach var="i" items="${classList}">
                        <tr <c:if test="${i.classroomId == classToView.classroomId}">class="info"</c:if>>
                            <td>${i.description}</td>
                            <td> 
                                <c:choose>
                                    <c:when test="${i.days eq 'TTh'}">(Tuesday/Thursday)</c:when>
                                    <c:when test="${i.days eq 'MWF'}">(Monday/Wednesday/Friday)</c:when>
                                </c:choose>
                            </td>
                            <td><fmt:formatDate pattern="MMMM dd yyyy" value="${i.startDate}"/>-<fmt:formatDate pattern="MMMM dd yyyy" value="${i.endDate}"/></td>    
                            <td><fmt:formatDate pattern="hh:mma" value="${i.startDate}"/>-<fmt:formatDate pattern="hh:mma" value="${i.endDate}"/></td>
                            <td>${i.enrolled}/ ${i.capacity}</td>

                            <td>
                                <form action="/viewclasses" method="POST">
                                    <input type="hidden" name="action" value="viewClass">
                                    <input type="hidden" name="classId" value="${i.classroomId}">
                                    <input type="submit" value="View Class List" class="btn btn-link"><br>
                                </form> 
                            </td>
                        </tr>                                        
                    </c:forEach>
                </table>
                <c:if test="${classToView != null}">
                    <form>
                        <button class="button-27 w-button" onclick="classListReport()">Print Class List</button><br>
                    </form>

                    <h2 style="display: inline">Class List</h2>
                    <table class="table table-hover" id="classListTable">
                        <tr>
                            <th>Name</th>
                            <th>Parent(Account Owner)</th>
                            <th>Home Phone</th>
                            <th>Cell Phone</th>
                            <th>Email</th>
                        </tr>
                        <c:set var="emailList" value="parklandpreschoolteam@gmail.com" />
                        <c:forEach items="${classToView.registrationList}" var="registration">
                            <c:if test="${fn:contains(registration.registrationStatus, 'R') || fn:contains(registration.registrationStatus, 'P')}">
                                <tr>
                                    <td><a href="childinformation?childId=${registration.child.childId}" target="_blank">${registration.child.childFirstName} ${registration.child.childLastName}</a></td>
                                    <td>${registration.child.account.accountFirstName} ${registration.child.account.accountLastName}</td>
                                    <td>${registration.child.account.accountPhoneNumber}</td>
                                    <td>${registration.child.account.cellphoneNumber}</td>
                                    <td><a href="mailto:${registration.child.account.email}">${registration.child.account.email}</a></td>
                                </tr>
                                <c:set var="emailList" value="${emailList},${registration.child.account.email}" />
                            </c:if>
                        </c:forEach>
                    </table>
                      <a href="mailto:${emailList}" class="button-27 w-button">Email Class</a>  
                </c:if>
            </div>
        </div>

        <c:import url="../footer.jsp"/>

        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <script src="js/prompts.js" type="text/javascript"></script>
        <script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.3.1/jspdf.umd.min.js"></script>
        <script src="js/pdfautotable.js"></script>
        <script src="js/report2.js"></script>
    </body>
</html>
