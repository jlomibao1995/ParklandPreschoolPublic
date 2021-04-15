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

            <h1 class="heading-24">Manage Classes</h1>

            <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                <div data-duration-in="300" data-duration-out="100" class="w-tabs">
                    <div class="tabs-menu-3 w-tab-menu">
                        <a data-w-tab="Tab 1" class="tab-link-tab-1-3 w-inline-block w-tab-link
                        <c:if test="${mode eq 'add'}">w--current</c:if>
                            ">
                            <h3 class="heading-26">Add Class</h3>
                        </a>
                        <a data-w-tab="Tab 2" class="tab-link-tab-2-3 w-inline-block w-tab-link
                        <c:if test="${mode eq 'view'}">w--current</c:if>
                            ">
                            <h3 class="heading-25">Class List</h3>
                        </a>
                        <a data-w-tab="Tab 3" class="tab-link-tab-3-3 w-inline-block w-tab-link 
                        <c:if test="${mode eq 'edit'}">w--current</c:if>
                            ">
                            <h3 class="heading-27">Edit Class</h3>
                        </a>
                        <a data-w-tab="Tab 4" class="tab-link-tab-3-3 w-inline-block w-tab-link 
                        <c:if test="${mode eq 'waitlist'}">w--current</c:if>
                            ">
                            <h3 class="heading-27">Waitlist</h3>
                        </a>
                    </div>
                    <div class="w-tab-content">
                        <div data-w-tab="Tab 1" class="w-tab-pane">
                            <h3>Add a class</h3>
                            <div>
                                <form action="manageclass" method="post">
                                    <input type="hidden" name="action" value="addClass">
                                    Description: <input type="text" name="description"class="w-input" maxlength="256" data-name="Field" placeholder="Class Description" required>
                                    Capacity:<input type="number" name="capacity"  placeholder="(Number of students)" min="0" max="100" class="w-input" maxlength="256" data-name="Field" required>
                                    Teacher:
                                    <select name="accountId" required>
                                    <c:forEach var="i" items="${teacherList}">
                                        <option value="${i.accountId}" class="w-dropdown" maxlength="256">${i.accountFirstName}</option>
                                    </c:forEach>
                                </select><br><br>
                                <div class="w-row">
                                    <div class="column-11 w-col w-col-6">
                                        Start Date:<input type="datetime-local" name="startDate" class="w-input" maxlength="256" data-name="Field" required>
                                        End Date:<input type="datetime-local" name="endDate" class="w-input" maxlength="256" data-name="Field" required>
                                        Days:<br>
                                        <select id="days" name="days" class="w-select" required>
                                            <option value="" selected disabled hidden>Choose here</option>
                                            <option value="TTh">Tuesday/Thursday</option>
                                            <option value="MWF">Monday/Wednesday/Friday</option>
                                        </select>
                                    </div>
                                    <div class="column-11 w-col w-col-6">
                                        Age Group:<input type="number" name="ageGroup" placeholder="(3 or 4)" class="w-input" min="3" max="4" maxlength="256" data-name="Field" required>
                                        Year:<input type="number" name="year" placeholder="(Year of class)" min="2020" max="2999" class="w-input" maxlength="256" data-name="Field" required>
                                        Cost:<input type="number" name="costPerMonth" placeholder="(170)" class="w-input" min="0" max="10000" maxlength="256" data-name="Field" required>
                                    </div>
                                </div>
                                <br>
                                <input type="hidden" name="mode" value="add">
                                <input type="submit" value="Create class" class="w-button submit-button">
                            </form>
                        </div>
                        <!--                            <div class="w-form-done">
                                                        <div>Thank you! Your submission has been received!</div>
                                                    </div>
                                                    <div class="w-form-fail">
                                                        <div>Oops! Something went wrong while submitting the form.</div>
                                                    </div>-->
                    </div>





                    <div data-w-tab="Tab 2" class="w-tab-pane">
                        <div class="w-form">      
                            <c:set var="emailList" value="parklandpreschoolteam@gmail.com" />

                            <c:forEach var="i" items="${classList}">
                                <c:forEach items="${i.registrationList}" var="registration">
                                    <c:if test="${fn:contains(registration.registrationStatus, 'R') || fn:contains(registration.registrationStatus, 'P')}">
                                        <c:set var="emailList" value="${emailList},${registration.child.account.email}" />
                                    </c:if>
                                </c:forEach>
                            </c:forEach>

                            <a href="mailto:${emailList}" class="button-27 w-button">Email All</a><br> 

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
                                            <form action="manageclass" method="POST">
                                                <input type="hidden" name="action" value="viewClass">
                                                <input type="hidden" name="classId" value="${i.classroomId}">
                                                <input type="submit" value="View Class List" class="btn btn-link"><br>
                                                <input type="hidden" name="mode" value="view">
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
                                        <th>Change Class</th> 
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
                                                <td><a href="childinformation?childId=${registration.child.childId}" target="_blank">Change Class</a></td>
                                            </tr>
                                            <c:set var="emailList" value="${emailList},${registration.child.account.email}" />
                                        </c:if>
                                    </c:forEach>

                                </table>
                                <a href="mailto:${emailList}" class="button-27 w-button">Email Class</a> 
                            </c:if>
                        </div>
                    </div>


                    <!--w--tab-active-->
                    <div data-w-tab="Tab 3" class="w-tab-pane"> 
                        <h3>Edit a class</h3>
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
                                <tr <c:if test="${i.classroomId == classToEdit.classroomId}">class="info"</c:if>>
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
                                        <form action="manageclass" method="POST">
                                            <input type="hidden" name="action" value="openEdit">
                                            <input type="hidden" name="classId" value="${i.classroomId}">
                                            <input type="submit" value="Edit" data-wait="Please wait..." class="button-27 w-button"><br>
                                            <input type="hidden" name="mode" value="edit">
                                        </form> 
                                    </td>
                                </tr>                                        
                            </c:forEach>
                        </table>

                        <c:if test="${classToEdit != null}">
                            <div style="padding: 3vh; background-color: E5F4F9;">
                                <form action="manageclass" method="POST">
                                    <input type="hidden" name="action" value="editClass"><br>
                                    <input type="hidden" name="classId" value="${classToEdit.classroomId}">
                                    Description: <input type="text" name="description"class="w-input" value="${classToEdit.description}" maxlength="256" data-name="Field" placeholder="Class Description" required>
                                    Capacity:<input  type="number" name="capacity"  placeholder="(Number of students)" min="0" max="100" value="${classToEdit.capacity}" class="w-input" maxlength="256" data-name="Field" required>
                                    Teacher:
                                    <select name="accountId" required>
                                        <c:forEach var="i" items="${teacherList}">
                                            <option value="${i.accountId}" class="w-dropdown" maxlength="256">
                                                <c:if test="${i.accountId == classToEdit.account.accountId}">
                                                    Selected: 
                                                    ${i.accountFirstName}
                                                </c:if>
                                                <c:if test="${i.accountId != classToEdit.account.accountId}">
                                                    ${i.accountFirstName}
                                                </c:if>
                                            </option>
                                        </c:forEach>
                                    </select><br><br>
                                    <div class="w-row">

                                        <div class="column-11 w-col w-col-6">
                                            Start Date:<input type="datetime-local" name="startDate" value="${startDate}" class="w-input" maxlength="256" data-name="Field" required>
                                            End Date:<input type="datetime-local" name="endDate" value="${endDate}" class="w-input" maxlength="256" data-name="Field" required>
                                            Days:<br>
                                            <select id="days" name="days" class="form-control" required>
                                                <option value="" selected disabled hidden>Choose here</option>
                                                <option value="TTh" <c:if test="${classToEdit.days eq 'TTh'}">selected</c:if>>Tuesday/Thursday</option>
                                                <option value="MWF" <c:if test="${classToEdit.days eq 'MWF'}">selected</c:if>>Monday/Wednesday/Friday</option>
                                                </select>
                                            </div>
                                            <div class="column-11 w-col w-col-6">
                                                Age Group:<input type="number" name="ageGroup" value="${classToEdit.ageGroup}" placeholder="(3 or 4)" class="w-input" min="3" max="4" maxlength="256" data-name="Field" required>
                                            Year:<input type="number" name="year" value="${classToEdit.year}" placeholder="(Year of class)"  class="w-input" min="2020" max="2999" maxlength="256" data-name="Field" required>
                                            Cost:<input type="number" name="costPerMonth" value="${classToEdit.costPerMonth}" placeholder="(170)" class="w-input" min="0" max="10000" maxlength="256" data-name="Field" required>
                                        </div>
                                    </div>
                                    <br>
                                    <input type="hidden" name="mode" value="edit">
                                    <input type="submit" value="Save edits" class="submit-button-7 w-button"> 
                                    <a href="/manageclass?mode=edit" class="button-27 w-button">Cancel</a>
                                </form>
                                <form action="manageclass" method="POST" id="deleteClassForm">
                                    <input type="hidden" name="action" value="removeClass">
                                    <input type="hidden" name="classId" value="${classToEdit.classroomId}">
                                    <button type="button" onclick="confirmDeleteClass()" data-wait="Please wait..." style="color:#cc0000; margin-top: 12px;" class="btn btn-link">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Delete Class</button>
                                    <input type="hidden" name="mode" value="edit">
                                </form>  
                            </div>
                        </c:if>
                    </div>









                    <div data-w-tab="Tab 4" class="w-tab-pane">
                        <h3>Class List</h3>
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
                                <tr <c:if test="${i.classroomId == classSelected.classroomId}">class="info"</c:if>>
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

                                    <td><a href="/manageclass?classSelectedId=${i.classroomId}&mode=waitlist">View List</a></td>
                                </tr>                                        
                            </c:forEach>
                        </table>
                        <c:if test="${classSelected != null}">
                            <div class="row">
                                <div class="col-lg-8">
                                    <h3>Wait List</h3>
                                    <table class="table table-hover">
                                        <tr>
                                            <th>Spot</th>
                                            <th>Name</th>
                                            <th>Registration Date</th>
                                            <th>Parent (Account Owner)</th>
                                            <th>Email</th>
                                            <th></th>
                                        </tr>
                                        <c:set var="count" value="0" scope="page" />
                                        <c:forEach items="${classSelected.registrationList}" var="registration">
                                            <c:if test="${fn:contains(registration.registrationStatus, 'W')}">
                                                <c:set var="count" value="${count + 1}" scope="page"/>
                                                <tr>
                                                    <td>${count}</td>
                                                    <td><a href="/childinformation?childId=${registration.child.childId}">${registration.child.childFirstName} ${registration.child.childLastName}</a></td>
                                                    <td><fmt:formatDate pattern='MMMM dd, yyyy' value='${registration.registrationDate}'/></td>
                                                    <td>${registration.child.account.accountFirstName} ${registration.child.account.accountLastName}</td>
                                                    <td><a href="mailto:${registration.child.account.email}">${registration.child.account.email}</a></td>
                                                    <td>
                                                        <form action="/manageclass" method="post">
                                                            <input type="hidden" name="action" value="offerSpot">
                                                            <input type="hidden" name="registrationId" value="${registration.registrationId}">
                                                            <input type="submit" value="Offer spot" class="button-27 w-button">
                                                            <input type="hidden" name="mode" value="waitlist">
                                                        </form>
                                                    <td>
                                                    </c:if>
                                            </tr>
                                        </c:forEach>   
                                    </table>
                                    <h3>Pending Payment</h3>
                                    <table class="table table-hover">
                                        <tr>
                                            <th>Spot</th>
                                            <th>Name</th>
                                            <th>Registration Date</th>
                                            <th>Parent (Account Owner)</th>
                                            <th>Email</th>
                                            <th></th>
                                        </tr>
                                        <c:set var="count" value="0" scope="page" />
                                        <c:forEach items="${classSelected.registrationList}" var="registration" varStatus="loop">

                                            <c:if test="${fn:contains(registration.registrationStatus, 'N')}">   
                                                <c:set var="count" value="${count + 1}" scope="page"/>
                                                <tr>
                                                    <td>${count}</td>
                                                    <td><a href="/childinformation?childId=${registration.child.childId}">${registration.child.childFirstName} ${registration.child.childLastName}</a></td>
                                                    <td>
                                                        <p><fmt:formatDate pattern='MMMM dd, yyyy' value='${registration.registrationDate}'/></p>
                                                        <p><fmt:formatDate pattern='hh:mma' value='${registration.registrationDate}'/></p>
                                                    </td>
                                                    <td>${registration.child.account.accountFirstName} ${registration.child.account.accountLastName}</td>
                                                    <td><a href="mailto:${registration.child.account.email}">${registration.child.account.email}</a></td>
                                                    <td><a href="/registrations?mode=active&registrationId=${registration.registrationId}" target="_blank">Deactivate Registration</a></td>
                                                </tr>
                                            </c:if>


                                        </c:forEach>  
                                    </table>
                                </div>
                                <div class="col-xs-4">
                                    <ul>
                                        <c:forEach items="${classSelected.registrationList}" var="registration" varStatus="loop">
                                            <c:if test="${loop.index == 0}"><h3>Class List</h3></c:if>
                                            <c:if test="${fn:contains(registration.registrationStatus, 'P') || fn:contains(registration.registrationStatus, 'R')}">
                                                <li><a href="/childinformation?childId=${registration.child.childId}" target="_blank">${registration.child.childFirstName} ${registration.child.childLastName}</a></li>
                                                </c:if>
                                            </c:forEach>   
                                    </ul>
                                </div>
                            </div>
                        </c:if>

                    </div>

                </div>


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
