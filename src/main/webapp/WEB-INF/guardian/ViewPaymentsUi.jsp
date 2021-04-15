<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Sun Feb 21 2021 05:13:28 GMT+0000 (Coordinated Universal Time)  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<html data-wf-page="603180510f36e8d275d58de5" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | View Payments</title>
        <meta content="View Payments (Admin)" property="og:title">
        <meta content="View Payments (Admin)" property="twitter:title">
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

        <div class="w-container">
            <h1 class="heading-32">View Payments</h1>

            <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                <h2>Filter:</h2>

                <form action="/viewpayments" method="post">
                    <div class="form-group">
                        <h3>Child</h3><select class="form-control" name="childSelected">
                            <option value="" selected disabled hidden>Select a child</option>
                        <c:forEach items="${childList}" var="child">
                            <option value="${child.childId}">${child.childFirstName} ${child.childLastName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <input type="submit" value="Filter by Child" class="search-button-2 w-button">
                    <input type="hidden" name="action" value="filterChild">
                </div>
            </form>

            <c:if test="${payments != null}">
                <c:forEach items="${months}" var="month">
                    <table class="table table-hover">
                        <tr>
                            <th colspan="9"><h2 class="heading-39">${month}</h2></th>
                        </tr>
                        <tr>
                            <th>Date of Payment</th>
                            <th>Payment Amount</th>
                            <th>Child</th>
                            <th>Invoice Number</th>
                            <th>Description</th>
                            <th>Paid/Unpaid</th>
                            <th>Method</th>
                            <th></th>
                        </tr>
                        <c:forEach items="${payments}" var="payment">
                            <c:if test="${month eq payment.paymentMonth}">
                                <tr>
                                    <td><fmt:formatDate pattern="MMM d, yyyy" value="${payment.paymentDate}"/></td>
                                    <td ><fmt:formatNumber type="currency" value="${payment.paymentTotal}"/></td>
                                    <td><a href="/managechildren?childId=${payment.registration.child.childId}">${payment.registration.child.childFirstName} ${payment.registration.child.childLastName}</a></td>
                                    <td>${payment.invoiceId}</td>
                                    <td>${payment.paymentDescription}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:contains(payment.paymentStatus, 'P')}">Paid</c:when>
                                            <c:when test="${fn:contains(payment.paymentStatus, 'N')}">Unpaid</c:when>
                                        </c:choose>
                                    </td>
                                        <td>${payment.paymentMethod}</td>
                                    <td>
                                        <c:if test="${fn:contains(payment.paymentStatus, 'N') && payment.paymentDescription ne 'full payment'}">
                                            <a href="payregistration?paymentId=${payment.paymentId}" class="search-button-2 w-button">Pay</a>
                                        </c:if>
                                    </td>
                                </tr>                                
                            </c:if>
                        </c:forEach>                  
                    </table>                    
                </c:forEach>
                <div class="alert alert-warning">
                    <c:forEach items="${payments}" var="payment">
                        <c:if test="${payment.paymentDescription eq 'full payment' && fn:contains(payment.paymentStatus, 'N')}">
                            <p>If you would like to pay for the rest of the year <a href="payregistration?paymentId=${payment.paymentId}" class="search-button-2 w-button">Pay</a> 
                                the full payment of <fmt:formatNumber type="currency" value="${payment.paymentTotal}"/></p>
                            </c:if>
                        </c:forEach>
                </div>
            </c:if>

            <c:if test="${payments == null}">
                <c:if test="${months.size() == 0}">
                    <div class="alert alert-warning" style="text-align: center">
                        There are currently no payments recorded in the system
                    </div>
                </c:if>

                <c:forEach items="${months}" var="month">
                    <table class="table table-hover">
                        <tr>
                            <th colspan="9"><h2 class="heading-39">${month}</h2></th>
                        </tr>
                        <tr>
                            <th>Date of Payment</th>
                            <th>Payment Amount</th>
                            <th>Child</th>
                            <th>Invoice Number</th>
                            <th>Description</th>
                            <th>Paid/Unpaid</th>
                            <th>Method</th>
                            <th></th>
                        </tr>
                        <c:forEach items="${childList}" var="child">
                            <c:forEach items="${child.registrationList}" var="registration">
                                <c:forEach items="${registration.paymentDetailsList}" var="payment">
                                    <c:if test="${month eq payment.paymentMonth}">
                                        <tr>
                                            <td><fmt:formatDate pattern="MMM d, yyyy" value="${payment.paymentDate}"/></td>
                                            <td ><fmt:formatNumber type="currency" value="${payment.paymentTotal}"/></td>
                                            <td><a href="/managechildren?childId=${payment.registration.child.childId}">${payment.registration.child.childFirstName} ${payment.registration.child.childLastName}</a></td>
                                            <td>${payment.invoiceId}</td>
                                            <td>${payment.paymentDescription}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:contains(payment.paymentStatus, 'P')}">Paid</c:when>
                                                    <c:when test="${fn:contains(payment.paymentStatus, 'N')}">Unpaid</c:when>
                                                </c:choose>
                                            </td>
                                                <td>${payment.paymentMethod}</td>
                                            <td>
                                                <c:if test="${fn:contains(payment.paymentStatus, 'N')}">
                                                    <a href="payregistration?paymentId=${payment.paymentId}" class="search-button-2 w-button">Pay</a>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </c:forEach>
                    </table>
                </c:forEach>      
            </c:if>
        </div>        

        <c:import url="../footer.jsp" />

        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>
