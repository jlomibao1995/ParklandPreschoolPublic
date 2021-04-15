<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Tue Feb 16 2021 06:53:00 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<html data-wf-page="6018e6c7cc4d9a301d13dd45" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Manage Children</title>
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
        <h1 class="heading-14">Manage Children</h1>
        <div class="container-4 w-container">
            <a id="w-node-button-1d13dd45" href="managechildren?mode=add" class="button-27 w-button">Add Child</a>
        </div>

        <div class="container-4 w-container">

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
                            Are you sure you want to withdraw this child?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="button" onclick="withdraw()" class="btn btn-primary">Withdraw</button>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                <div class="w-row">

                    <table class="table table-hover table-bordered">
                   <c:forEach items="${children}" var="childList">
                        <tr colspan="6" <c:if test="${childList.childId == child.childId}">class="info"</c:if>>
                            <td><a href="managechildren?childId=${childList.childId}" class="link-4">${childList.childFirstName} ${childList.childLastName}</a></td>
                        </tr>
                    </c:forEach>
                </table>                                              
            </div>
            
            <c:if test="${param.childId != null}">
                <div div class="w-row">

                    <a href="registerchild?childId=${param.childId}" class="button-27 w-button">Register for Class</a><br>
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
                            <tr class="collapse active" id="${reg.registrationId}">
                                <c:if test="${!fn:contains(reg.registrationStatus, 'W') && reg.registrationActive}">
                                    <td colspan="2">
                                        <c:forEach items="${reg.paymentDetailsList}" var="payment">
                                            <c:if test="${fn:contains(payment.paymentStatus, 'N')}">
                                                <a href="payregistration?paymentId=${payment.paymentId}" class="search-button-2 w-button"> 
                                                    <c:choose>
                                                        <c:when test="${payment.paymentDescription ne 'registration fee' && payment.paymentDescription ne 'full payment'}">Pay for ${payment.paymentMonth}</c:when>
                                                        <c:otherwise>Pay ${payment.paymentDescription} </c:otherwise>
                                                    </c:choose>
                                                </a><br><br>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td colspan="2">
                                        <form action="/PDFreports" method="post" target="_blank">
                                            <input type="hidden" name="action" value="printRegistration">
                                            <input type="hidden" name="registrationId" value="${reg.registrationId}">
                                            <input type="submit" value="Print Registration" class="search-button-2 w-button">
                                        </form>
                                    </td>
                                    <td colspan="2">
                                        <form id="withdrawForm" action="/managechildren" method="post">
                                            <input type="hidden" name="action" value="withdraw">
                                            <input type="hidden" name="registrationId" value="${reg.registrationId}">
                                            <input type="hidden" name="childId" value="${reg.child.childId}">
                                            <button type="button" onclick="confirmWithdraw('${reg.child.childFirstName} ${reg.child.childLastName}')" class="btn btn-link">Withdraw</button>
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>

                </div>
                <hr><br>
                <div class="w-layout-grid grid-4">
                    <a href="managechildren?mode=update&childId=${param.childId}" class="button-27 w-button">Edit Child Information</a>
                </div>


                <c:import url="../ChildInformation.jsp" />
            </c:if>
        </div>

        <c:import url="../footer.jsp" />

        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <script src="js/prompts.js" type="text/javascript"></script>
        <script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>
