<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Fri Feb 12 2021 08:12:38 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html data-wf-page="5fcf5929bcd40b1801893630" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Report</title>
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
        <style>
            #registrationTable th, #activeAccountsTable th, #inactiveAccountsTable th, #newAccountsTable th, #classListTable th, #entireClassTable th{
                background-color: #48abe0;
                color: white;
            }



        </style>
    </head>
    <body>
        <c:import url="navbar.html" />

        <div class="w-container"> 

            <h1 class="heading-7">Reports</h1>     

            <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                <div data-duration-in="300" data-duration-out="100" class="w-tabs">
                    <div class="tabs-menu-2 w-tab-menu">
                        <a data-w-tab="Tab 1" class="tab-link-tab-1-2 w-inline-block w-tab-link 
                        <c:if test="${mode eq 'accounts'}">w--current</c:if>
                            ">
                            <h3 class="heading-20">Accounts</h3>
                        </a>
                        <a data-w-tab="Tab 2" class="tab-link-tab-2-2 w-inline-block w-tab-link
                        <c:if test="${mode eq 'registrations'}">w--current</c:if>
                            ">
                            <h3 class="heading-22">Registrations</h3>
                        </a>
                        <a data-w-tab="Tab 3" class="tab-link-tab-3-2 w-inline-block w-tab-link
                        <c:if test="${mode eq 'classList'}">w--current</c:if>
                            ">
                            <h3 class="heading-21">Class List</h3>
                        </a>
                        <a data-w-tab="Tab 4" class="tab-link-tab-3-2 w-inline-block w-tab-link
                        <c:if test="${mode eq 'payments'}">w--current</c:if>
                            ">
                            <h3 class="heading-21">Payments</h3>
                        </a>

                    </div>
                    <div class="w-tab-content">
                        <div data-w-tab="Tab 1" class="w-tab-pane w--tab-active">

                            <form action="/PDFreports" method="post" target="_blank">
                                <input type="hidden" name="action" value="printActiveAccounts">
                                <input type="submit" value="Print Active Accounts Report" class="button-27 w-button" onclick="activeAccountsReport();"/>
                            </form><br>

                            <h3 style="display: inline">Active Accounts</h3>
                            <select name="" class="form-control" onchange="location = this.value;">
                                <option value="/report">None</option>
                                <option value="/report?activePeriod=week" <c:if test="${param.activePeriod eq 'week'}">selected</c:if>>Past week</option>
                                <option value="/report?activePeriod=month" <c:if test="${param.activePeriod eq 'month'}">selected</c:if>>Past month</option>
                            </select><br>
                            
                            <table class="table table-hover" id="activeAccountsTable">
                                <tr>
                                    <th style="background-color:#1679c4 ">Email Address</th>
                                    <th style="background-color:#1679c4 ">Name</th>
                                    <th style="background-color:#1679c4 ">Last Login Date</th>
                                    <th style="background-color:#1679c4 ">Account Type</th>
                                    <th style="background-color:#1679c4 ">Registered Children</th>
                                </tr>
                            <c:choose>
                                <c:when test="${activePeriod != null}"><c:set value="${activeAccounts}" var="accounts" /></c:when>
                                <c:when test="${activePeriod == null}"><c:set value="${accountList}" var="accounts" /></c:when>
                            </c:choose>
                            <c:forEach var="i" items="${accounts}">
                                <c:if test="${i.accountStatus}">
                                    <tr>
                                        <td>${i.email}</td>
                                        <td>${i.accountFirstName} ${i.accountLastName}</td>
                                        <td><fmt:formatDate pattern='yyyy-MM-dd hh:mma' value='${i.lastLoginDate}'/></td>
                                        <td>
                                            <c:if test="${fn:contains(i.accountType, 'A')}">Admin</c:if>
                                            <c:if test="${fn:contains(i.accountType, 'G')}">Guardian</c:if>
                                            <c:if test="${fn:contains(i.accountType, 'S')}">Staff</c:if>
                                            </td>
                                            <td>
                                            <c:forEach var="j" items="${i.getChildList()}">
                                                ${j.childFirstName} ${j.childLastName}<br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>

                        <form action="/PDFreports" method="post" target="_blank">
                            <input type="hidden" name="action" value="printInactiveAccounts">
                            <input type="submit" value="Print Inactive Accounts Report" class="button-27 w-button" onclick="inactiveAccountsReport();"/>

                        </form><br>
                        <h3 style="display: inline">Inactive Accounts</h3>

                        <table class="table table-hover" id="inactiveAccountsTable">
                            <tr>
                                <th style="background-color:#1679c4 ">Email Address</th>
                                <th style="background-color:#1679c4 ">Name</th>
                                <th style="background-color:#1679c4 ">Last Login Date</th>
                                <th style="background-color:#1679c4 ">Account Type</th>
                                <th style="background-color:#1679c4 ">Registered Children</th>
                            </tr>
                            <c:forEach var="i" items="${accountList}">
                                <c:if test="${!i.accountStatus}">
                                    <tr>
                                        <td>${i.email}</td>
                                        <td>${i.accountFirstName} ${i.accountLastName}</td>
                                        <td><fmt:formatDate pattern='yyyy-MM-dd hh:mma' value='${i.lastLoginDate}'/></td>
                                        <td>
                                            <c:if test="${fn:contains(i.accountType, 'A')}">Admin</c:if>
                                            <c:if test="${fn:contains(i.accountType, 'G')}">Guardian</c:if>
                                            <c:if test="${fn:contains(i.accountType, 'S')}">Staff</c:if>
                                            </td>
                                            <td>
                                            <c:forEach var="j" items="${i.getChildList()}">
                                                ${j.childFirstName} ${j.childLastName}<br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>


                        <button type="button" class="button-27 w-button" onclick="newAccountsReport()">Print New Accounts Report</button><br>

                        <h3 style="display: inline">New Accounts</h3>
                        <select name="" class="form-control" onchange="location = this.value;">
                            <option value="/report?newPeriod=week" <c:if test="${param.newPeriod eq 'week'}">selected</c:if>>Past week</option>
                            <option value="/report?newPeriod=month" <c:if test="${param.newPeriod eq 'month'}">selected</c:if>>Past month</option>
                            </select><br>

                            <!-- New accounts not yet implemented, just random code -->
                            <table class="table table-hover" id="newAccountsTable">
                                <tr>
                                    <th style="background-color:#1679c4 ">Email Address</th>
                                    <th style="background-color:#1679c4 ">Name</th>
                                    <th style="background-color:#1679c4 ">Last Login Date</th>
                                    <th style="background-color:#1679c4 ">Account Type</th>
                                    <th style="background-color:#1679c4 ">Registered Children</th>
                                </tr>
                            <c:forEach var="i" items="${newAccounts}">

                                <tr>
                                    <td>${i.email}</td>
                                    <td>${i.accountFirstName} ${i.accountLastName}</td>
                                    <td><fmt:formatDate pattern='yyyy-MM-dd hh:mma' value='${i.lastLoginDate}'/></td>
                                    <td>
                                        <c:if test="${fn:contains(i.accountType, 'A')}">Admin</c:if>
                                        <c:if test="${fn:contains(i.accountType, 'G')}">Guardian</c:if>
                                        <c:if test="${fn:contains(i.accountType, 'S')}">Staff</c:if>
                                        </td>
                                        <td>
                                        <c:forEach var="j" items="${i.getChildList()}">
                                            ${j.childFirstName} ${j.childLastName}<br>
                                        </c:forEach>
                                    </td>
                                </tr>

                            </c:forEach>
                        </table>

                    </div>

                    <div data-w-tab="Tab 2" class="w-tab-pane">

                        <button type="button" class="button-27 w-button" onclick="registrationReport()">Print Registrations Report</button><br>
                        <h3 style="display: inline">Registrations</h3>
                        <table class="table table-hover" id="registrationTable">
                            <tr>
                                <th style="background-color:#1679c4 ">Name</th>
                                <th style="background-color:#1679c4 ">Registration Date</th>
                                <th style="background-color:#1679c4 ">Class</th>
                                <th style="background-color:#1679c4 ">Days</th>
                                <th style="background-color:#1679c4 ">Registration Status</th>
                                <th style="background-color:#1679c4 ">Active/Inactive</th>
                            </tr>
                            <c:forEach var="reg" items="${registrationList}">
                                <tr>
                                    <td style="font-weight:bold">${reg.child.childFirstName} ${reg.child.childLastName}</td>
                                    <td><fmt:formatDate pattern='yyyy-MM-dd' value='${reg.registrationDate}'/></td>
                                    <td>${reg.classroom.description}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reg.classroom.days eq 'TTh'}">Tuesday/Thursday</c:when>
                                            <c:when test="${reg.classroom.days eq 'MWF'}">Monday/Wednesday/Friday</c:when>
                                        </c:choose>
                                    </td>
                                    <td style="font-weight:bold">
                                        <c:choose>
                                            <c:when test="${fn:contains(reg.registrationStatus, 'W')}">Wait listed</c:when>
                                            <c:when test="${fn:contains(reg.registrationStatus, 'N')}">Registration fee not paid</c:when>
                                            <c:when test="${fn:contains(reg.registrationStatus, 'P')}">Registration fee paid, waiting to be accepted</c:when>
                                            <c:when test="${fn:contains(reg.registrationStatus, 'D')}">Child information needs update</c:when>
                                            <c:when test="${fn:contains(reg.registrationStatus, 'R')}">Registered</c:when>
                                        </c:choose>

                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reg.registrationActive}">Active</c:when>
                                            <c:when test="${!reg.registrationActive}">Inactive</c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                    </div>


                    <div data-w-tab="Tab 3" class="w-tab-pane">
                        <div class="w-form">      
                            <h2>Classes</h2>
                            <table class="table table-hover" id="entireClassTable">
                                <tr>
                                    <th style="background-color:#1679c4 ">Description</th>
                                    <th style="background-color:#1679c4 ">Days</th>
                                    <th style="background-color:#1679c4 ">Date</th>
                                    <th style="background-color:#1679c4 ">Time</th>
                                    <th style="background-color:#1679c4 ">Enrolled</th>
                                    <th style="background-color:#1679c4 "></th>
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
                                            <form action="/report" method="POST">
                                                <input type="hidden" name="action" value="viewClass">
                                                <input type="hidden" name="classId" value="${i.classroomId}">
                                                <input type="submit" value="View Class List" class="btn btn-link"><br>
                                                <input type="hidden" name="mode" value="view">
                                            </form> 
                                        </td>
                                    </tr>                                        
                                </c:forEach>
                            </table>
                        </div>

                        <c:if test="${classToView != null}">

                            <button class="button-27 w-button" onclick="classListReport()">Print Class List</button><br>
                            <h3 style="display: inline">Class List</h3>
                            <table class="table table-hover" id="classListTable">
                                <tr>
                                    <th style="background-color:#1679c4 ">Name</th>
                                    <th style="background-color:#1679c4 ">Parent(Account Owner)</th>
                                    <th style="background-color:#1679c4 ">Home Phone</th>
                                    <th style="background-color:#1679c4 ">Cell Phone</th>
                                    <th style="background-color:#1679c4 ">Email</th>
                                </tr>

                                <c:forEach items="${classToView.registrationList}" var="registration">
                                    <c:if test="${fn:contains(registration.registrationStatus, 'R') || fn:contains(registration.registrationStatus, 'P')}">
                                        <tr>
                                            <td><a href="childinformation?childId=${registration.child.childId}" target="_blank">${registration.child.childFirstName} ${registration.child.childLastName}</a></td>
                                            <td>${registration.child.account.accountFirstName} ${registration.child.account.accountLastName}</td>
                                            <td>${registration.child.account.accountPhoneNumber}</td>
                                            <td>${registration.child.account.cellphoneNumber}</td>
                                            <td>${registration.child.account.email}</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>

                            </table>
                        </c:if>

                    </div>
                    <!--***************************************************************CLASS LIST ************************************************************************************************* -->                                        
                    <div data-w-tab="Tab 4" class="w-tab-pane">
                        <button type="button" class="button-27 w-button" onclick="paymentReport()">Print Payments Report</button><br>
                        <h3 style="display: inline">Payments</h3>
                        <c:if test="${months.size() == 0}">
                            <div class="alert alert-warning" style="text-align: center">
                                There are currently no payments recorded in the system
                            </div>
                        </c:if>

                        <table class="table table-hover" id="paymentTable">
                            <c:forEach items="${months}" var="month">

                                <tr>
                                    <th colspan="8" style="background-color:#1679c4 "><h3 class="heading-39" style="margin: 1px">${fn:substring(month, 0, 1)}${fn:toLowerCase(fn:substring(month, 1, -1))}</h3></th>
                                </tr>
                                <tr>
                                    <th>Date of Payment</th>
                                    <th>Payment Amount</th>
                                    <th>Child</th>
                                    <th>Parent</th>
                                    <th>Invoice Number</th>
                                    <th>Description</th>
                                    <th>Paid/Unpaid</th>
                                    <th>Method</th>
                                </tr>
                                <c:forEach items="${paymentList}" var="payment">
                                    <c:if test="${month eq payment.paymentMonth}">

                                        <tr>
                                            <td><fmt:formatDate pattern="MMM d, yyyy" value="${payment.paymentDate}"/></td>
                                            <td ><fmt:formatNumber type="currency" value="${payment.paymentTotal}"/></td>
                                            <td><a href="/childinformation?childId=${payment.registration.child.childId}">${payment.registration.child.childFirstName} ${payment.registration.child.childLastName}</a></td>
                                            <td>${payment.registration.child.account.accountFirstName} ${payment.registration.child.account.accountLastName}</td>
                                            <td>${payment.invoiceId}</td>
                                            <td>${payment.paymentDescription}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:contains(payment.paymentStatus, 'P')}">Paid</c:when>
                                                    <c:when test="${fn:contains(payment.paymentStatus, 'N')}">Unpaid</c:when>
                                                </c:choose>
                                            </td>
                                            <td>${payment.paymentMethod}</td>
                                        </tr>

                                    </c:if>
                                </c:forEach>

                            </c:forEach>
                        </table>
                    </div>





                </div>
            </div>
        </div>

        <c:import url="../footer.jsp" />
        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <script src="js/bootstrap/bootstrap.min.js"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
<!--        <script src="js/jquery-3.5.1.min.js"></script>
        <script src="js/report.js"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.3.1/jspdf.umd.min.js"></script>
        <script src="js/pdfautotable.js"></script>
        <script src="js/report2.js"></script>
    </body>
</html>
