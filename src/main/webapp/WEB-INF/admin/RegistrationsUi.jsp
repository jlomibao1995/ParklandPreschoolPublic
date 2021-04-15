<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Fri Feb 12 2021 08:12:38 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<html data-wf-page="5fcf5929bcd40b1801893630" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Registrations</title>
        <meta content="Registrations (Admin)" property="og:title">
        <meta content="Registrations (Admin)" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">
        <link href="css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="css/normalize.css" rel="stylesheet" type="text/css">
        <link href="css/webflow.css" rel="stylesheet" type="text/css">
        <link href="css/parkland-preschool-project.webflow.css" rel="stylesheet" type="text/css">
        <!--        <script src="js/jquery-3.5.1.min.js"></script>
                <script src="js/report.js"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.2/jspdf.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js" type="text/javascript"></script>
        <script type="text/javascript">WebFont.load({google: {families: ["Merriweather:300,300italic,400,400italic,700,700italic,900,900italic", "Roboto:100,100italic,300,300italic,regular,italic,500,500italic,700,700italic,900,900italic", "Comic Neue:300,300italic,regular,italic,700,700italic"]}});</script>
        <!-- [if lt IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js" type="text/javascript"></script><![endif] -->
        <script type="text/javascript">!function (o, c) {
                var n = c.documentElement, t = " w-mod-";
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch")
            }(window, document);</script>
    </head>
    <body class="body-3">
        <c:import url="navbar.html" />
        <div class="section-4">
            <h1 class="heading-7">Registrations</h1>           
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
                                <button type="button" onclick="deleteRegistration()" class="btn btn-danger">Delete Registration</button>
                            </div>
                        </div>
                    </div>
                </div>

                <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
                <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                    <div data-duration-in="300" data-duration-out="100" class="w-tabs">
                        <div class="tabs-menu-2 w-tab-menu">
                            <a data-w-tab="Tab 1" class="tab-link-tab-1-2 w-inline-block w-tab-link 
                            <c:if test="${mode eq 'pending'}">w--current</c:if>
                                ">
                                <h3 class="heading-20">Pending Approval</h3>
                            </a>
                            <a data-w-tab="Tab 2" class="tab-link-tab-2-2 w-inline-block w-tab-link
                            <c:if test="${mode eq 'active'}">w--current</c:if>
                                ">
                                <h3 class="heading-22">Active</h3>
                            </a>
                            <a data-w-tab="Tab 3" class="tab-link-tab-3-2 w-inline-block w-tab-link
                            <c:if test="${mode eq 'inactive'}">w--current</c:if>
                                ">
                                <h3 class="heading-21">Inactive</h3>
                            </a>
                        </div>
                        <div class="w-tab-content">

                            <!--***************************************************************PENDING REGISTRATIIONS ************************************************************************************** -->
                            <div data-w-tab="Tab 1" class="tab-pane-tab-1 w-clearfix w-tab-pane w--tab-active">
                                <div class="w-row">
                                    <div class="w-col w-col-3">

                                        <form action="/PDFreports" method="post" target="_blank">
                                            <br>
                                            <input type="hidden" name="action" value="printAllPendingRegistrations">
                                            <input type="submit" value="Print All Pending" class="search-button-2 w-button">
                                        </form>

                                        <h3>Children</h3>
                                        <form action="/registrations" method="get" class="w-form">
                                            <label for="search-2">Search</label>
                                            <input type="search" class="search-input w-input" maxlength="256" name="pendingKey" placeholder="Enter name…" id="search">
                                            <input type="hidden" name="mode" value="pending">  
                                            <input type="submit" value="Search" class="search-button-2 w-button">
                                        </form>
                                        <ul role="list">
                                        <c:forEach items="${pendingRegistrations}" var="registration">
                                            <li>
                                                <a href="registrations?registrationId=${registration.registrationId}&mode=pending">
                                                    ${registration.child.childFirstName} ${registration.child.childLastName}
                                                </a>
                                            </li>                        
                                        </c:forEach> 
                                    </ul>
                                </div>
                                <div class="w-col w-col-9">
                                    <c:if test="${registrationP != null}">
                                        <c:set var="child" scope="request" value="${registrationP.child}"/>
                                        <c:set var="registration" value="${registrationP}"/>

                                        <h3>Registration Details</h3>                                       
                                        <c:if test="${registration.registrationId != null}">
                                            <form action="/PDFreports" method="post" target="_blank">
                                                <input type="hidden" name="action" value="printRegistration">
                                                <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                                <input type="submit" value="Print as PDF" class="submit-button-16 w-button">
                                            </form>
                                        </c:if>
                                        <br>

                                        <form action="registrations" method="post" id="email-form-4" name="email-form-4" data-name="Email Form 4" class="w-clearfix">
                                            <h5>Class Selected</h5>



                                            <span id="classSelected" class="w-input">${registration.classroom.description}<c:choose><c:when test="${registration.classroom.days eq 'TTh'}">(Tuesday/Thursday)</c:when><c:when test="${registration.classroom.days eq 'MWF'}">(Monday/Wednesday/Friday)</c:when>
                                                </c:choose><fmt:formatDate pattern='MMMM dd yyyy' value='${registration.classroom.startDate}'/> - <fmt:formatDate pattern='MMMM dd yyyy' value='${registration.classroom.endDate}'/>
                                            </span>



                                            <h5>Registration Status</h5>
                                            <p class="w-input">
                                                <c:choose>
                                                    <c:when test="${!registration.registrationActive}">Registration inactive</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'W')}">Wait listed</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'N')}">Registration fee not paid</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'P')}">Registration fee paid, awaiting administration action</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'D')}">Registration denied</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'R')}">Registered</c:when>
                                                </c:choose>
                                            </p>

                                            <!-- **************CHILD INFORMATION (PENDING) **************************CHILD INFORMATION (PENDING)****************************CHILD INFORMATION (PENDING)********************************************** -->                                            
                                            <c:import url="../ChildInformation.jsp" />

                                        </form>
                                        <form action="/registrations" method="post">
                                            <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                            <input type="hidden" name="action" value="accept">
                                            <input type="hidden" name="mode" value="pending">
                                            <input type="submit" value="Accept Registration" class="button-12 w-button">
                                        </form>
                                        <form action="/registrations" method="post">
                                            <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                            <input type="hidden" name="mode" value="pending">
                                            <input type="hidden" name="action" value="deny">
                                            <input type="submit" value="Deny Registration" class="button-11 w-button">
                                        </form>

                                    </c:if>                                   
                                </div>
                            </div>
                        </div>


                        <!--***************************************************************ACTIVE REGISTRATIIONS ************************************************************************************** -->                                           
                        <div data-w-tab="Tab 2" class="w-tab-pane">
                            <div class="w-row">
                                <div class="w-col w-col-3">
                                    <br>
                                    <form action="/PDFreports" method="post" target="_blank">
                                        <input type="hidden" name="action" value="printAllActiveRegistrations">
                                        <input type="submit" value="Print All Active" class="search-button-2 w-button">
                                    </form>

                                    <h3>Children</h3>
                                    <form action="/registrations" method="get" class="w-form">
                                        <label for="search-2">Search</label>
                                        <input type="search" class="search-input w-input" maxlength="256" name="activeKey" placeholder="Enter name…" id="search">
                                        <input type="hidden" name="mode" value="active">  
                                        <input type="submit" value="Search" class="search-button-2 w-button">
                                    </form>
                                    <ul role="list">
                                        <c:forEach items="${activeRegistrations}" var="registration">
                                            <li>
                                                <a href="registrations?registrationId=${registration.registrationId}&mode=active">
                                                    ${registration.child.childFirstName} ${registration.child.childLastName}
                                                </a>
                                            </li>                        
                                        </c:forEach> 
                                    </ul>
                                </div>
                                <div class="w-col w-col-9">


                                    <h3>Registration Details</h3>

                                    <c:if test="${registrationA != null}">

                                        <c:set var="child" scope="request" value="${registrationA.child}"/>
                                        <c:set var="registration" value="${registrationA}"/>

                                        <form action="/PDFreports" method="post" target="_blank">
                                            <input type="hidden" name="action" value="printRegistration">
                                            <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                            <input type="submit" value="Print as PDF" class="submit-button-16 w-button">
                                        </form><br>

                                        <form action="/registrations" method="post" id="email-form-4" name="email-form-4" data-name="Email Form 4" class="w-clearfix">
                                            <h5>Class Selected</h5>



                                            <span id="classSelected" class="w-input">${registration.classroom.description}<c:choose><c:when test="${registration.classroom.days eq 'TTh'}">(Tuesday/Thursday)</c:when><c:when test="${registration.classroom.days eq 'MWF'}">(Monday/Wednesday/Friday)</c:when>
                                                </c:choose><fmt:formatDate pattern='MMMM dd yyyy' value='${registration.classroom.startDate}'/> - <fmt:formatDate pattern='MMMM dd yyyy' value='${registration.classroom.endDate}'/>
                                            </span>



                                            <h5>Registration Status</h5>
                                            <p class="w-input">
                                                <c:choose>
                                                    <c:when test="${!registration.registrationActive}">Registration inactive</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'W')}">Wait listed</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'N')}">Registration fee not paid</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'P')}">Registration fee paid, awaiting administration action</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'D')}">Registration denied</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'R')}">Registered</c:when>
                                                </c:choose>
                                            </p>

                                            <c:import url="../ChildInformation.jsp" />
                                        </form>

                                        <form action="/registrations" method="post" class="w-clearfix">
                                            <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                            <input type="hidden" name="mode" value="active">
                                            <input type="hidden" name="action" value="deactivate">
                                            <input type="submit" value="Move to Inactive" data-wait="Please wait..." class="submit-button-16 w-button">
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>

                        <!--***************************************************************INACTIVE REGISTRATIIONS ************************************************************************************** -->

                        <div data-w-tab="Tab 3" class="w-tab-pane">
                            <div class="w-row">
                                <div class="w-col w-col-3">
                                    <h3>Children</h3>
                                    <form action="/registrations" method="get" class="w-form">
                                        <label for="search-2">Search</label>
                                        <input type="search" class="search-input w-input" maxlength="256" name="inactiveKey" placeholder="Enter name…" id="search">
                                        <input type="hidden" name="mode" value="inactive">  
                                        <input type="submit" value="Search" class="search-button-2 w-button">
                                    </form>
                                    <ul role="list">
                                        <c:forEach items="${inactiveRegistrations}" var="registration">
                                            <li>
                                                <a href="registrations?registrationId=${registration.registrationId}&mode=inactive">
                                                    ${registration.child.childFirstName} ${registration.child.childLastName}
                                                </a>
                                            </li>                        
                                        </c:forEach> 
                                    </ul>
                                </div>
                                <div class="w-col w-col-9">
                                    <h3>Registration Details</h3>

                                    <c:if test="${registrationI != null}">

                                        <c:set var="child" scope="request" value="${registrationI.child}"/>
                                        <c:set var="registration" value="${registrationI}"/>

                                        <form action="/PDFreports" method="post" target="_blank">
                                            <input type="hidden" name="action" value="printRegistration">
                                            <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                            <input type="submit" value="Print as PDF" class="submit-button-16 w-button">
                                        </form><br>

                                        <form action="/registrations" method="post" id="email-form-4" name="email-form-4" data-name="Email Form 4" class="w-clearfix">
                                            <h5>Class Selected</h5>



                                            <span id="classSelected" class="w-input">${registration.classroom.description}<c:choose><c:when test="${registration.classroom.days eq 'TTh'}">(Tuesday/Thursday)</c:when><c:when test="${registration.classroom.days eq 'MWF'}">(Monday/Wednesday/Friday)</c:when>
                                                </c:choose><fmt:formatDate pattern='MMMM dd yyyy' value='${registration.classroom.startDate}'/> - <fmt:formatDate pattern='MMMM dd yyyy' value='${registration.classroom.endDate}'/>
                                            </span>



                                            <h5>Registration Status</h5>
                                            <p class="w-input">
                                                <c:choose>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'W')}">Wait listed</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'N')}">Registration fee not paid</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'P')}">Registration fee paid, awaiting administration action</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'D')}">Registration denied</c:when>
                                                    <c:when test="${fn:contains(registration.registrationStatus, 'R')}">Registered</c:when>
                                                </c:choose>
                                            </p>

                                            <c:import url="../ChildInformation.jsp" />
                                        </form>
                                        <form action="/registrations" method="post">
                                            <input type="hidden" name="mode" value="inactive">
                                            <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                            <input type="hidden" name="action" value="activate">
                                            <input type="submit" value="Move to Active" data-wait="Please wait..." class="submit-button-16 w-button">
                                        </form>
                                        <form action="/registrations" method="post" id="deleteRegistrationForm">
                                            <input type="hidden" name="mode" value="inactive">
                                            <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                            <input type="hidden" name="action" value="delete">

                                            <button type="button" class="btn btn-link" style="color:#cc0000" onclick="confirmDeleteRegistration()">
                                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                                Delete Registration
                                            </button>
                                            <!--                                            <input type="submit" value="Delete Registration" data-wait="Please wait..." class="button-11 w-button">-->
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:import url="../footer.jsp"/>
    <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="js/webflow.js" type="text/javascript"></script>
    <script src="js/prompts.js" type="text/javascript"></script>
    <script src="js/bootstrap/bootstrap.min.js"></script>
    <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
<!--    <script src="js/jquery-3.5.1.min.js"></script>
    <script src="js/report.js"></script>-->
</body>
</html>
