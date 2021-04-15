<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Thu Feb 04 2021 12:22:45 GMT+0000 (Coordinated Universal Time)  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html data-wf-page="601245f5a29e4d7b9af66b86" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | My account</title>
        <meta content="My account" property="og:title">
        <meta content="My account" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
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
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch");
            }(window, document);</script>
    </head>
    <body>
        <div class="w-container">
            <h1 class="heading-12">My Account</h1>

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
                            Deactivating the account will disable access to your child registrations and account.<br>
                            Are you sure you want to deactivate this account?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="button" onclick="deactivateAccount()" class="btn btn-danger">Deactivate Account</button>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

            <c:if test="${mode ne 'edit'}">
                <div class="w-form">
                    <form id="email-form" name="email-form" data-name="Email Form">
                        <div class="w-row">
                            <div class="w-col w-col-6">
                                <label>First name</label>
                                <input type="text" class="text-field-14 w-input" maxlength="256" name="firstName" value="${account.accountFirstName}" readonly>

                                <label>Address</label>
                                <input type="text" class="text-field-19 w-input" maxlength="256" name="address" value="${account.accountAddress}" readonly>

                                <label>Province</label>
                                <input type="text" class="text-field-15 w-input" maxlength="256" name="province" value="Alberta" placeholder="Province" readonly>

                                <label>Email</label>
                                <input type="email" class="text-field-20 w-input" maxlength="256" name="email" value="${account.email}" readonly>

                                <label>Cell phone</label>
                                <input type="tel" class="text-field-16 w-input" maxlength="256" name="cellPhone" required value="${account.cellphoneNumber}" readonly>

                            </div>
                            <div class="w-clearfix w-col w-col-6">
                                <label>Last name</label>
                                <input type="text" class="text-field-14 w-input" maxlength="256" name="lastName" value="${account.accountLastName}" readonly>

                                <label>City</label>
                                <input type="text" class="text-field-19 w-input" maxlength="256" name="city" value="Calgary" required readonly>

                                <label>Postal code</label>
                                <input type="text" id="postalCode" class="text-field-15 w-input" maxlength="256" name="postalCode" required value="${account.accountPostalCode}" readonly>  

                                <label>Home phone</label>
                                <input type="tel" class="text-field-15 w-input" maxlength="256" name="homePhone" value="${account.accountPhoneNumber}" readonly> 

                                <label>Work phone</label>
                                <input type="tel" class="text-field-17 w-input" maxlength="256" name="workPhone" value="${account.workPhoneNumber}" readonly>
                            </div>
                            <a href="myaccount?mode=edit" class="submit-button-16 w-button">Edit Account</a>
                        </div>
                    </form>
                </div>

<!--                <div class="w-container">
                    <h2 class="heading-30">Notifications</h2>
                    <div class="w-row">
                        <div class="w-col w-col-6">
                            <div>
                                <a href="#" class="notifcationsub w-button">Subscribe to Email Notifications</a>
                            </div>
                            <ul role="list">
                                <li>Billy Smith has been placed in Class A</li>
                                <li>Mandy Smith has been placed in Class C</li>
                            </ul>
                        </div>
                        <div class="w-col w-col-6">
                            <div class="w-clearfix">
                                <a href="#" class="notifcationunsub w-button">Unsubscribe from Email Notifications</a>
                            </div>
                            <ul role="list" class="w-list-unstyled">
                                <li class="list-item-21">15:20 Aug 19 2020 </li>
                                <li class="list-item-22">15:45 Aug 19 2020</li>
                            </ul>
                        </div>
                    </div>
                </div>-->
                <div></div>
                <div class="w-container">
                    <c:if test="${fn:contains(account.accountType, 'A') || fn:contains(account.accountType, 'S') || 
                                  (fn:contains(account.accountType, 'G') && account.childList[0].registrationList[0] != null)}">
                        <h2 class="heading-31">Current Events</h2>
                    <div class="html-embed w-embed w-iframe">
                        <iframe src="https://calendar.google.com/calendar/embed?height=600&amp;wkst=1&amp;bgcolor=%23ffffff&amp;ctz=America%2FEdmonton&amp;src=cGFya2xhbmRwcmVzY2hvb2x0ZWFtQGdtYWlsLmNvbQ&amp;src=YWRkcmVzc2Jvb2sjY29udGFjdHNAZ3JvdXAudi5jYWxlbmRhci5nb29nbGUuY29t&amp;src=ZW4uY2FuYWRpYW4jaG9saWRheUBncm91cC52LmNhbGVuZGFyLmdvb2dsZS5jb20&amp;color=%23039BE5&amp;color=%2333B679&amp;color=%230B8043&amp;title=Parkland%20Preschool%20" style="border:solid 1px #777" width="800" height="600" frameborder="0" scrolling="no"></iframe>
                    </div>
                    </c:if>
                    
                </div>

            </c:if>
            <c:if test="${mode eq 'edit'}">
                <div class="w-form">
                    <form action="myaccount" method="post">
                        <div class="w-row">
                            <div class="w-col w-col-6">
                                <label>First name</label>
                                <input type="text" class="text-field-14 w-input" maxlength="256" name="firstName" value="${account.accountFirstName}"
                                       required pattern="[a-zA-Z-\s]+" title="Name can't contain special characters">

                                <label>Address</label>
                                <input type="text" class="text-field-19 w-input" maxlength="256" name="address" value="${account.accountAddress}" pattern="[a-zA-z0-9-\.\s]+" required>

                                <label>Province</label>
                                <input type="text" class="text-field-15 w-input" maxlength="256" name="province" value="Alberta" placeholder="Province" required readonly>

                                <label>Email</label>
                                <input type="email" class="text-field-20 w-input" maxlength="256" name="email" value="${account.email}" required>

                                <label>Cell phone</label>
                                <input type="tel" class="text-field-16 w-input" maxlength="256" name="cellPhone" required value="${account.cellphoneNumber}"
                                       pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required>

                                <label>Password</label>
                                <input type="password" class="text-field-21 w-input" maxlength="256" name="password" id="password"
                                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$" value="">

                                <div id="passwordMessage" class=""></div>
                            </div>
                            <div class="w-clearfix w-col w-col-6">
                                <label>Last name</label>
                                <input type="text" class="text-field-14 w-input" maxlength="256" name="lastName"
                                       required pattern="[a-zA-Z-\s]+" title="Name can't contain special characters" value="${account.accountLastName}">

                                <label>City</label>
                                <input type="text" class="text-field-19 w-input" maxlength="256" name="city" value="Calgary" required readonly>

                                <label>Postal code</label>
                                <input type="text" id="postalCode" class="text-field-15 w-input" maxlength="256" name="postalCode" required value="${account.accountPostalCode}"
                                       pattern="[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]" title="Postal code must be in the format of A1B 2C3">  

                                <label>Home phone</label>
                                <input type="tel" class="text-field-15 w-input" maxlength="256" name="homePhone" value="${account.accountPhoneNumber}"
                                       pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required> 

                                <label>Work phone</label>
                                <input type="tel" class="text-field-17 w-input" maxlength="256" name="workPhone" value="${account.workPhoneNumber}"
                                       pattern="^\(\d{3}\)\s\d{3}-\d{4}" title="Phone must be in the format of (403) 123-4567" required>

                                <label>Confirm password</label>
                                <input type="password" class="text-field-18 w-input" maxlength="256" name="confirmPassword" id="confirmPassword">

                                <div class="" id="passwordMatch"></div>
                            </div>                            
                        </div>

                        <input type="hidden" name="action" value="edit">
                        <input type="submit" value="Save Changes" data-wait="Please wait..." class="submit-button-16 w-button">
                        <a href="myaccount" class="button-27 w-button">Cancel</a>
                    </form>
                    <form action="/myaccount" method="post" id="deactivateAccountForm">
                        <input type="hidden" name="action" value="deactivateAccount">
                        <button type="button" data-toggle="modal" data-target="#promptDialogue" class="btn btn-link" style="color:#cc0000">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Deactivate Account</button>
                    </form>
                </div> 
            </c:if>

            <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
            <script src="js/prompts.js" type="text/javascript"></script>
            <script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
            <script src="js/webflow.js" type="text/javascript"></script>
            <script src="js/reactive.js" type="text/javascript"></script>
            <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>