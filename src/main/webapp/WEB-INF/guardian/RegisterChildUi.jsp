<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Thu Feb 04 2021 13:30:37 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<html data-wf-page="5fcf5929bcd40b0dbd893629" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Register Child</title>
        <meta content="Register Child" property="og:title">
        <meta content="Register Child" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">
        <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css" type="text/css">
        <link href="css/normalize.css" rel="stylesheet" type="text/css">
        <link href="css/webflow.css" rel="stylesheet" type="text/css">
        <link href="css/parkland-preschool-project.webflow.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js"></script>
        <script>WebFont.load({google: {families: ["Merriweather:300,300italic,400,400italic,700,700italic,900,900italic", "Open Sans:300,300italic,400,400italic,600,600italic,700,700italic,800,800italic", "Roboto:100,100italic,300,300italic,regular,italic,500,500italic,700,700italic,900,900italic", "Comic Neue:300,300italic,regular,italic,700,700italic"]}});</script>
        <!-- [if lt IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js" type="text/javascript"></script><![endif] -->
        <script>!function (o, c) {
                var n = c.documentElement, t = " w-mod-";
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch")
            }(window, document);</script>
    </head>
    <body class="body">
        <c:import url="navbar.html" />

        <section id="contact-form" class="contact-form">
            <h2 class="heading-15">Register ${child.childFirstName} ${child.childLastName}</h2>
            <div class="w-container">

                <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
                <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

                    <div class="w-form">
                        <form action="registerchild" method="post" id="wf-form-Contact-Form" name="wf-form-Contact-Form" data-name="Contact Form">
                            <div data-duration-in="300" data-duration-out="100" class="w-tabs">
                                <div class="tabs-menu w-tab-menu">
                                    <a data-w-tab="Tab 1" class="tabs-menu w-inline-block w-tab-link 
                                    <c:if test="${mode eq 'fees'}">w--current</c:if>
                                        ">
                                        <h3 class="heading-16">Fees</h3>
                                    </a>
                                    <a data-w-tab="Tab 2" class="tab-link-tab-2 w-inline-block w-tab-link
                                    <c:if test="${mode eq 'classes'}">w--current</c:if>
                                        ">
                                        <h3 class="heading-17">Classes</h3>
                                    </a>
                                    </a>
                                    <a data-w-tab="Tab 3" class="tab-link-tab-3 w-inline-block w-tab-link
                                    <c:if test="${mode eq 'guardian'}">w--current</c:if>
                                        ">
                                        <h3 class="heading-18">Guardian Information</h3>
                                    </a>
                                    <a data-w-tab="Tab 4" class="tab-link-tab-4 w-inline-block w-tab-link
                                    <c:if test="${mode eq 'policies'}">w--current</c:if>
                                        ">
                                        <h3 class="heading-19">School Policies</h3>
                                    </a>
                                </div>
                                <div class="w-tab-content">
                                    <h3 style="text-align: center">To proceed to the next step, please make sure that the Signature and all the fields in Classes, Guardian Information, and School Policies tabs are filled out</h3>

                                    <div data-w-tab="Tab 1" class="tab-pane-tab-2 w-tab-pane">
                                        <h3>Monthly Fees</h3>
                                        <table class="table table-hover">
                                            <tr>
                                                <th>Class Type</th>
                                                <th>Preschool</th>
                                                <th>Junior K</th>
                                            </tr>
                                            <tr>
                                                <th>Registrations Fee</th>
                                                <th>$45</th>
                                                <th>$45</th>
                                            </tr>
                                            <tr>
                                                <td>Two days per week</td>
                                                <td>$170</td>
                                                <td>$200</td>
                                            </tr>
                                            <tr>
                                                <td>Three days per week</td>
                                                <td>$200</td>
                                                <td>$275</td>
                                            </tr>
                                        </table>

                                        <div class="text-block-29"><br>FIRST PAYMENT WILL INCLUDE ONE TIME $45.00 REGISTRATION FEE</div>
                                        <a href="/managechildren" class="button-11 w-button">Cancel</a>
                                        <a id="nextClasses" href="/registerchild?childId=${child.childId}&goTo=classes" onmouseover="changeClassChosen('${child.childId}')" style="display:block" class="button-12 w-button">Next</a>
                                </div>

                                <div data-w-tab="Tab 2" class="tab-pane-tab-2 w-tab-pane">                                    

                                    <h3>Select a Class</h3>
                                    <table class="table table-hover">
                                        <c:if test="${classes.size() == 0}">
                                            <b>There are no classes available. Check another time.</b>
                                        </c:if>

                                        <c:if test="${classes.size() != 0}">
                                            <tr>
                                                <th>Class Description</th>
                                                <th>Days</th>
                                                <th>Dates</th>
                                                <th>Times</th>
                                                <th>Enrolled</th>
                                                <th>Cost per Month</th>
                                            </tr>

                                            <c:forEach items="${classes}" var="classroom">
                                                <tr>
                                                    <td>
                                                        <input id="classChosen" onChange="changeClassChosen('${child.childId}')" type="radio" name="classChosen" value="${classroom.classroomId}" 
                                                               <c:if test="${classPicked == classroom.classroomId}"> checked</c:if>>
                                                        ${classroom.description}
                                                        <c:if test="${classroom.enrolled == classroom.capacity}">
                                                            <p class="alert alert-info">This class is full, continue to be added to the wait list or select another class.</p>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${classroom.days eq 'TTh'}">Tuesday/Thursday</c:when>
                                                            <c:when test="${classroom.days eq 'MWF'}">Monday/Wednesday/Friday</c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td><fmt:formatDate pattern="MMMM dd yyyy" value="${classroom.startDate}"/>-<fmt:formatDate pattern="MMMM dd yyyy" value="${classroom.endDate}"/></td>
                                                    <td><fmt:formatDate pattern="hh:mma" value="${classroom.startDate}"/>-<fmt:formatDate pattern="hh:mma" value="${classroom.endDate}"/></td>
                                                    <td>${classroom.enrolled}/${classroom.capacity}</td>
                                                    <td><fmt:formatNumber type="currency" value="${classroom.costPerMonth}"/> </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>

                                    </table>

                                    <div>
                                        <a id="nextGuardian" href="/registerchild?childId=${child.childId}&goTo=guardian" style="display:block" onmouseover="changeClassChosen('${child.childId}')" class="button-12 w-button">Next</a>
                                        <a href="managechildren" class="button-11 w-button">Cancel</a>
                                    </div>                                    
                                </div>


                                <div data-w-tab="Tab 3" class="tab-pane-tab-3 w-tab-pane tab-pane">
                                    <div class="w-row">
                                        <div class="w-col w-col-6">
                                            <label>Guardian 1 (Account Owner)</label>
                                            <input type="text" class="w-input" maxlength="256" value="${child.account.accountFirstName} ${child.account.accountLastName}" data-name="Guardian1Name" id="Guardian-2" required readOnly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.account.relationToChild}" id="Guardian1Relationship" readonly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.account.accountAddress}" data-name="Guardian1Address" id="Guardian1Address" required readOnly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.account.accountPhoneNumber}" data-name="Guardian1PhoneH" id="Guardian1PhoneH" required readOnly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.account.cellphoneNumber}" data-name="Guardian1PhoneC" id="Guardian1PhoneC" required readOnly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.account.workPhoneNumber}" data-name="Guardian1PhoneW" id="Guardian1PhoneW" required readOnly>
                                            <input type="email" class="w-input" maxlength="256" name="email" value="${child.account.email}" data-name="Email" id="Email-2" required readOnly>
                                        </div>
                                        <div class="w-col w-col-6">
                                            <label>Guardian 2</label>
                                            <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[0].emergencyFirstName} ${child.emergencyContactList[0].emergencyLastName}" required readonly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[0].relationToChild}" required readonly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[0].emergencyAddress}" required readonly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[0].homePhoneNumber}" required readonly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[0].workPhoneNumber}" required readonly>
                                            <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[0].cellphoneNumber}" required readonly>
                                        </div>
                                        <input type="hidden" name="childId" value="${child.childId}">
                                    </div>
                                    <h5>If there are any changes to <a href="/myaccount">account</a> or <a href="/managechildren?childId=${child.childId}">child information</a> please update them</h5>
                                    <a href="managechildren" class="button-11 w-button">Cancel</a>
                                    <a id="nextPolicies" href="/registerchild?childId=${child.childId}&goTo=policies" onmouseover="changeClassChosen('${child.childId}')" style="display:block" class="button-12 w-button">Next</a>
                                </div>

                                <div data-w-tab="Tab 4" class="tab-pane-tab-4 w-tab-pane w--tab-active">
                                    <h3>Discipline Policy:</h3>
                                    <div class="text-block-15">
                                        No corporal punishment will be administered by the staff of Parkland Preschool. In the event of a discipline problem, the child will be moved to a quiet area until he/she is settled. If a problem persists despite teacher intervention and communication with the parents/guardian, the school reserves the right to cancel the child’s enrollment.
                                    </div>
                                    <div class="text-block-19"> 
                                        By typing your name below, you are signing this
                                        Agreement electronically. You agree your electronic signature is the legal equivalent of your
                                        manual signature on this Agreement.
                                        <p>Please type exactly ${fn:toUpperCase(child.account.accountFirstName)} ${fn:toUpperCase(child.account.accountLastName)} in the space below to agree with the Discipline Policy</p>
                                    </div>
                                    <input type="text" name="disciplineSignature" value="${disciplineSignature}" class="w-input" required>

                                    <h3>Safety Policy:</h3>
                                    <div class="text-block-14">
                                        In the case of a serious accident or illness, I authorize the staff of Parkland Preschool to administer and/or seek medical attention for my child. Parkland Preschool staff will notify the guardian as soon as possible. The guardians are responsible for any costs incurred.
                                    </div>
                                    <div class="text-block-19">
                                        By typing your name below, you are signing this
                                        Agreement electronically. You agree your electronic signature is the legal equivalent of your
                                        manual signature on this Agreement.
                                        <p>Please type exactly ${fn:toUpperCase(child.account.accountFirstName)} ${fn:toUpperCase(child.account.accountLastName)} in the space below to agree with the Safety Policy</p>
                                    </div>
                                    <input type="text" name="safetySignature" value="${safetySignature}" class="w-input" required>

                                    <h3>Outdoor Policy:</h3>
                                    <div class="text-block-13">
                                        We occasionally go outdoors during good weather for physical activity.
                                    </div>
                                    <div class="text-block-19">
                                        By typing your name below, you are signing this
                                        Agreement electronically. You agree your electronic signature is the legal equivalent of your
                                        manual signature on this Agreement.
                                        <p>Please type exactly ${fn:toUpperCase(child.account.accountFirstName)} ${fn:toUpperCase(child.account.accountLastName)} in the space below to agree with the Outdoor Policy</p>
                                    </div>
                                    <input type="text" name="outdoorSignature" value="${outdoorSignature}" class="w-input" required>

                                    <h3>Sick Policy:</h3>
                                    <div class="text-block-16">
                                        Parkland Preschool follows the Child Care Licensing Regulations concerning communicable diseases in order to protect the health and safety of all children and staff members. A child, 
                                        who may be suffering from a disease in Schedule 1 of the Communicable Disease Regulations or exhibits any of the following symptoms within the past 24 hours, will not be permitted to be in attendance  at preschool:
                                    </div>
                                    <ul>
                                        <li>Diarrhea</li>
                                        <li>Fever</li>
                                        <li>Vomiting</li>
                                        <li>New or unexplained rash or cough</li>
                                        <li>A child requiring greater care and attention than can be provided without compromising the care<br>of the other children in the program</li>
                                    </ul>
                                    <div class="text-block-12">
                                        Children should be symptom free and/or on medication for 24 hours before returning to school or until the license holder is satisfied that the child no longer poses a health risk to persons on the program premises. 
                                        The above does not apply if the child&#x27;s guardian provides written notice from a physician indicating the child does not pose a health risk to persons on the program premises. If a child becomes ill while at preschool, 
                                        or where a staff member knows or has reason to believe that a child is exhibiting signs or symptoms of illness, the child will be separated from the class and made comfortable with a pillow and blanket where appropriate. 
                                        The guardian(s) will be notified and expected to pick up the child as soon as possible. If the guardian(s) cannot be reached the emergency contact will be notified and expected to pick up the child as soon as possible. 
                                        A primary staff member will directly supervise the sick child until removed from the program.<br><br>
                                        Please notify the preschool if your child develops any communicable diseases<br>(please ask us for us for a listing of communicable diseases if required)
                                    </div>
                                    <div class="text-block-19">
                                        By typing your name below, you are signing this
                                        Agreement electronically. You agree your electronic signature is the legal equivalent of your
                                        manual signature on this Agreement.
                                        <p>Please type exactly ${fn:toUpperCase(child.account.accountFirstName)} ${fn:toUpperCase(child.account.accountLastName)} in the space below to agree with the Sick Policy</p>
                                    </div>
                                    <input type="text" name="sickSignature" value="${sickSignature}" class="w-input" required>
                                    <br>

                                    <div class="text-block-19">Upon clicking Submit you have the option to pay for the registration fee via PayPal<br> After your payment has been processed, a spot will be reserved for your child for the class</div>

                                    <input type="submit" value="Register" data-wait="Please wait..." style="display:block" class="button-12 w-button">
                                    <a href="managechildren" class="button-11 w-button">Cancel</a>
                                </div>
                            </div>
                        </div>                        
                    </form>                  
                </div>                
            </div>
        </section>

        <c:import url="../footer.jsp" />

        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js"></script>
        <script src="js/prompts.js"></script>
        <script src="js/bootstrap/bootstrap.min.js"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>