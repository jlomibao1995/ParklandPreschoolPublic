<%-- 
    Document   : ManageAccounts
    Created on : Feb 19, 2021, 2:42:22 AM
    Author     : 608787
--%>
<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Fri Feb 19 2021 09:38:54 GMT+0000 (Coordinated Universal Time)  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html data-wf-page="602f3ab532216e039d5a95fe" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Manage Accounts</title>
        <meta content="Manage Accounts" property="og:title">
        <meta content="Manage Accounts" property="twitter:title">
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
                            <button type="button" onclick="deleteAccount()" class="btn btn-danger">Delete Account</button>
                        </div>
                    </div>
                </div>
            </div>

            <h1 class="heading-24">Manage Accounts</h1>

            <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                <div data-duration-in="300" data-duration-out="100" class="w-tabs">
                    <div class="tabs-menu-3 w-tab-menu">
                        <a data-w-tab="Tab 1" class="tab-link-tab-1-3 w-inline-block w-tab-link 
                        <c:if test="${mode eq 'add'}">w--current</c:if>">
                            <h3 class="heading-26">Add</h3>
                        </a>
                        <a data-w-tab="Tab 2" class="tab-link-tab-2-3 w-inline-block w-tab-link 
                        <c:if test="${mode eq 'edit'}">w--current</c:if>">
                            <h3 class="heading-25">Edit</h3>
                        </a>
                        <a data-w-tab="Tab 3" class="tab-link-tab-3-3 w-inline-block w-tab-link
                        <c:if test="${mode eq 'active'}">w--current</c:if>">
                            <h3 class="heading-27">Active</h3>
                        </a>
                        <a data-w-tab="Tab 4" class="tab-link-tab-3-3 w-inline-block w-tab-link
                        <c:if test="${mode eq 'inactive'}">w--current</c:if>">
                            <h3 class="heading-27">Inactive</h3>
                        </a>
                    </div>


                    <div class="w-tab-content">
                        <div data-w-tab="Tab 1" class="w-tab-pane w--tab-active">
                            <h3>Add an Account</h3>
                            <div class="w-form">
                                <!--<form id="email-form" name="email-form" data-name="Email Form">-->
                                <form action="manageaccounts" method="POST">
                                    <input type="hidden" name="action" value="addAccount">
                                    <select name="accountType" data-name="AccountType" required class="form-control">
                                        <option value="" selected disabled hidden>Account Type</option>
                                        <option value="A">Admin</option>
                                        <option value="S">Staff</option>
                                        <option value="G">Guardian</option>
                                    </select><br>
                                    <div class="w-row">
                                        <div class="w-col w-col-6">
                                            <label>First name</label>
                                            <input type="text" class="text-field-14 w-input" maxlength="256" name="firstName" value="${firstName}"
                                               required pattern="[a-zA-Z-\s]+" title="Name can't contain special characters">

                                        <label>Address</label>
                                        <input type="text" class="text-field-19 w-input" maxlength="256" name="address" value="${address}" pattern="[a-zA-z0-9-\.\s]+" required>

                                        <label>Province</label>
                                        <input type="text" class="text-field-15 w-input" maxlength="256" name="province" value="Alberta" placeholder="Province" required readonly>

                                        <label>Email</label>
                                        <input type="email" class="text-field-20 w-input" maxlength="256" name="email" value="${email}" required>

                                        <label>Cell phone</label>
                                        <input type="tel" class="text-field-16 w-input" maxlength="256" name="cellPhone" required value="${cellPhone}"
                                               pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required>

                                        <label>Password</label>
                                        <input type="password" class="text-field-21 w-input" maxlength="256" name="password" id="password" required
                                               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$">

                                        <div id="passwordMessage" class=""></div>
                                    </div>
                                    <div class="w-col w-col-6">
                                        <label>Last name</label>
                                        <input type="text" class="text-field-14 w-input" maxlength="256" name="lastName"
                                               required pattern="[a-zA-Z-\s]+" title="Name can't contain special characters" value="${lastName}">

                                        <label>City</label>
                                        <input type="text" class="text-field-19 w-input" maxlength="256" name="city" value="Calgary" required readonly>

                                        <label>Postal code</label>
                                        <input type="text" id="postalCode" class="text-field-15 w-input" maxlength="256" name="postalCode" required value="${postalCode}"
                                               pattern="[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]" title="Postal code must be in the format of A1B 2C3">  

                                        <label>Home phone</label>
                                        <input type="tel" class="text-field-15 w-input" maxlength="256" name="homePhone" value="${homePhone}"
                                               pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required> 

                                        <label>Work phone</label>
                                        <input type="tel" class="text-field-17 w-input" maxlength="256" name="workPhone" value="${workPhone}"
                                               pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required>

                                        <label>Confirm password</label>
                                        <input type="password" class="text-field-18 w-input" maxlength="256" name="confirmPassword" id="confirmPassword" required>

                                        <div class="" id="passwordMatch"></div>
                                        <input type="hidden" name="action" value="addAccount">
                                        <input type="submit" value="Create Account" data-wait="Please wait..." class="submit-button-16 w-button">
                                    </div>

                                </div>

                            </form>    
                        </div>
                    </div>


                    <div data-w-tab="Tab 2" class="w-tab-pane">
                        <h3>Edit an Account</h3>
                        <div class="w-row">
                            <div class="w-col w-col-4">
                                <form action="manageaccounts" method="GET" class="w-form">
                                    <label for="search">Search</label>
                                    <input type="text" class="search-input w-input" maxlength="256" name="query" placeholder="Search…" id="search">
                                    <input type="submit" value="Search" class="search-button-2 w-button">
                                </form>
                                <ul role="list">
                                    <c:forEach var="i" items="${accountList}">
                                        <c:if test="${i.accountStatus == true}">
                                            <li>
                                                <a href="/manageAccounts?accountSelected=${i.accountId}">${i.accountFirstName} ${i.accountLastName}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </div>
                            <div class="w-col w-col-8">
                                <div class="w-form">
                                    <form action="manageaccounts" method="post">
                                        <label>Account Type</label>
                                        <select name="accountType" data-name="AccountType" required class="form-control">
                                            <option value="" selected disabled hidden>Account Type</option>
                                            <option value="A" <c:if test="${fn:contains(account.accountType, 'A')}">selected</c:if>>Admin</option>
                                            <option value="S" <c:if test="${fn:contains(account.accountType, 'S')}">selected</c:if>>Staff</option>
                                            <option value="G" <c:if test="${fn:contains(account.accountType, 'G')}">selected</c:if>>Guardian</option>
                                            </select><br>

                                            <label>First name</label>
                                            <input type="text" class="text-field-14 w-input" maxlength="256" name="firstName" value="${account.accountFirstName}"
                                               required pattern="[a-zA-Z-\s]+" title="Name can't contain special characters">

                                        <label>Last name</label>
                                        <input type="text" class="text-field-14 w-input" maxlength="256" name="lastName"
                                               required pattern="[a-zA-Z-\s]+" title="Name can't contain special characters" value="${account.accountLastName}">

                                        <label>Address</label>
                                        <input type="text" class="text-field-19 w-input" maxlength="256" name="address" value="${account.accountAddress}" pattern="[a-zA-z0-9-\.\s]+" required>

                                        <label>Postal code</label>
                                        <input type="text" id="postalCode2" class="text-field-15 w-input" maxlength="256" name="postalCode" required value="${account.accountPostalCode}"
                                               pattern="[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]" title="Postal code must be in the format of A1B 2C3">  

                                        <label>City</label>
                                        <input type="text" class="text-field-19 w-input" maxlength="256" name="city" value="Calgary" required readonly>

                                        <label>Province</label>
                                        <input type="text" class="text-field-15 w-input" maxlength="256" name="province" value="Alberta" placeholder="Province" required readonly>

                                        <label>Email</label>
                                        <input type="email" class="text-field-20 w-input" maxlength="256" name="email" value="${account.email}" required>

                                        <label>Cell phone</label>
                                        <input type="tel" class="text-field-16 w-input" maxlength="256" name="cellPhone" required value="${account.cellphoneNumber}"
                                               pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required>

                                        <label>Home phone</label>
                                        <input type="tel" class="text-field-15 w-input" maxlength="256" name="homePhone" value="${account.accountPhoneNumber}"
                                               pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required> 

                                        <label>Work phone</label>
                                        <input type="tel" class="text-field-17 w-input" maxlength="256" name="workPhone" value="${account.workPhoneNumber}"
                                               pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required>                                      

<!--                                        <label>Password</label>
                                        <input type="password" class="text-field-21 w-input" maxlength="256" name="password" required id="password2"
                                               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$">

                                        <div id="passwordMessage2" class=""></div>-->

                                        <input type="hidden" name="action" value="edit">
                                        <input type="hidden" name="accountSelected" value="${account.accountId}">
                                        <input type="hidden" name="mode" value="edit">
                                        <input type="submit" value="Save Changes" data-wait="Please wait..." class="submit-button-16 w-button">
                                        <a href="/manageaccounts" class="button-27 w-button">Cancel</a>
                                    </form>
                                </div>
                            </div>  
                        </div>
                    </div>


                    <div data-w-tab="Tab 3" class="w-tab-pane">
                        <div class="w-row">
                            <div class="w-col w-col-3">
                                <h3>Active Accounts</h3>
                            </div>
                        </div>
                        <div class="w-form">

                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Email Address</th>
                                        <th>Name</th>
                                        <th>Last Login Date</th>
                                        <th>Account Type</th>
                                        <th>Registered Children</th>
                                        <th>Function</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="i" items="${accountList}">
                                        <c:if test="${i.accountStatus == true}">
                                            <tr>
                                                <td><a href="mailto:${i.email}">${i.email}</a></td>
                                                <td>${i.accountFirstName} ${i.accountLastName}</td>
                                                <td><fmt:formatDate pattern="MM/dd/yyyy hh:mma" value="${i.lastLoginDate}"/></td>
                                                <td>
                                                    <!--${i.accountType}-->
                                                    <c:if test="${fn:contains(i.accountType, 'A')}">Admin</c:if>
                                                    <c:if test="${fn:contains(i.accountType, 'G')}">Guardian</c:if>
                                                    <c:if test="${fn:contains(i.accountType, 'S')}">Staff</c:if>
                                                    </td>
                                                    <td>
                                                    <c:forEach var="j" items="${i.getChildList()}">
                                                        ${j.childFirstName} ${j.childLastName}<br>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                    <form action="manageaccounts" method="POST">
                                                        <input type="hidden" name="action" value="deactivate">
                                                        <input type="hidden" name="accountId" value="${i.accountId}">
                                                        <input type="submit" value="Move to inactive" class="button-29 w-button">
                                                    </form>
<!--                                                    <form method="post" action="sendmessage">
                                                        <input type="hidden" name="action" value="emailPopup">
                                                        <input type="hidden" name="accountEmail" value="${i.email}">
                                                        <input type="submit" value="Email" class="button-29 w-button">
                                                    </form>-->
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>


                    <div data-w-tab="Tab 4" class="w-tab-pane">
                        <div class="w-row">
                            <div class="w-col w-col-3">
                                <h3>Inactive Accounts</h3>
                            </div>
                        </div>
                        <div class="w-form">
                            <table class="table table-hover"">
                                <thead>
                                    <tr>
                                        <th>Email Address</th>
                                        <th>Name</th>
                                        <th>Last Login Date</th>
                                        <th>Account Type</th>
                                        <th>Registered Children</th>
                                        <th>Function</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="i" items="${accountList}">
                                        <c:if test="${i.accountStatus == false}">
                                            <tr <c:if test="${i.accountId == inactiveAccount}">class="info"</c:if>>
                                                <td><a href="mailto:${i.email}">${i.email}</a></td>
                                                <td>${i.accountFirstName} ${i.accountLastName}</td>
                                                <td><fmt:formatDate pattern="MM/dd/yyyy hh:mma" value="${i.lastLoginDate}"/></td>
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
                                                <td>
                                                    <form action="manageaccounts" method="POST">
                                                        <input type="hidden" name="action" value="selectInactiveAccount">
                                                        <input type="hidden" name="inactiveSelected" value="${i.accountId}">
                                                        <input type="submit" value="Select Account" class="btn btn-link">
                                                    </form>                                                    
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:if test="${inactiveAccount != null}">                                

                                <form action="manageaccounts" method="POST">
                                    <input type="hidden" name="action" value="activate">
                                    <input type="hidden" name="accountId" value="${inactiveAccount}">
                                    <input type="submit" value="Move to active" class="submit-button-16 w-button">
                                </form>

                                <a href="/manageaccounts" class="button-27 w-button">Cancel</a>

                                <form action="manageAccounts" method="POST" id="deleteAccountForm">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="accountId" value="${inactiveAccount}">
                                    <button type="button" onclick="confirmDeleteAccount()" style="color:#cc0000; margin-top: 12px;" class="btn btn-link">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Delete Account</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <c:import url="../footer.jsp" />
        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <script src="js/reactive.js" type="text/javascript"></script>
        <script src="js/prompts.js" type="text/javascript"></script>
        <script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>
