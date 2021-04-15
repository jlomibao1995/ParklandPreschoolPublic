<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Tue Feb 16 2021 06:53:00 GMT+0000 (Coordinated Universal Time)  -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<html data-wf-page="6018e6c7cc4d9a301d13dd45" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Child Information</title>
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
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch");
            }(window, document);</script>
    </head>
    <body>        
        <div class="w-form">
            <div class="alert alert-info">
                <a role="button" data-toggle="collapse" href="#childInfo" aria-expanded="false" aria-controls="collapseExample">
                    <h4>Child Information</h4>
                </a>
            </div><br>

            <div class="collapse" id="childInfo">
                <div class="well">
                    <label>Name:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.childFirstName} ${child.childLastName}" data-name="ClassesSelected" id="ClassesSelected" readonly>
                    <div class="w-row">
                        <div class="w-col w-col-6">
                            <label>Address:</label>
                            <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.childAddress}" data-name="Address" id="Address" readonly>
                            <label>Gender</label>
                            <input type="text" id="gender" name="Gender" data-name="Gender" class="text-field-14 w-input" 
                                   value="<c:if test='${fn:contains(child.childGender, "M")}'>Boy</c:if><c:if test='${fn:contains(child.childGender, "F")}'>Girl</c:if>" readonly>


                                <label>Doctor:</label>
                                <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.doctor}" data-name="Doc Office Name" id="DocOfficeName" required="" readonly>
                            <label>Clinic/Doctor Phone Number: </label>
                            <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.medicalPhoneNumber}" required readonly>
                        </div>
                        <div class="w-col w-col-6">
                            <label>Birthdate:</label>
                            <input type="date" class="text-field-14 w-input" maxlength="256" name="birthdate" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${child.childBirthdate}'/>" readonly>
                            <label>Postal Code:</label>
                            <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.childPostalCode}" data-name="Postal Code" id="PostalCode" required="" readonly>
                            <label>Alberta Health Care Number:</label>
                            <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.healthcareNum}" data-name="Health Care" id="HealthCare" required="" readonly>
                            <label>Clinic: </label>
                            <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.clinic}" data-name="Doc Office Num" id="DocOfficeNum" required="" readonly>
                        </div>
                    </div>
                    <div class="w-row">
                        <div class="w-col w-col-7">
                            <div class="text-block-16">Are your child&#x27;s immunization shots up to date?</div>
                            <div class="text-block-16">Has your child had chicken pox?</div>
                            <div class="text-block-16">Will your child be on medications while at school?</div>
                        </div>
                        <div class="w-col w-col-1">
                            <label class="w-radio">
                                <input type="radio" data-name="shots" id="yes" name="shots" value="yes" class="w-form-formradioinput w-radio-input" 
                                       <c:if test="${child.immunizationUpdated}">checked</c:if> disabled>
                                       <span for="yes" class="w-form-label">Yes</span>
                                </label>
                                <label class="w-radio">
                                    <input type="radio" data-name="pox" id="yes-2" name="pox" value="yes" class="w-form-formradioinput w-radio-input" 
                                    <c:if test="${child.hadChickenPox}">checked</c:if>   disabled>
                                    <span for="yes" class="w-form-label">Yes</span>
                                </label>
                                <label class="w-radio">
                                    <input type="radio" data-name="medications" id="yes-2" name="medications" value="yes" class="w-form-formradioinput w-radio-input" 
                                    <c:if test="${child.medications}">checked</c:if>   disabled>
                                    <span for="yes" class="w-form-label">Yes</span>
                                </label>
                            </div>
                            <div class="w-col w-col-1">
                                <label class="w-radio">
                                    <input type="radio" data-name="shots" id="no" name="shots" value="no" class="w-form-formradioinput w-radio-input" 
                                    <c:if test="${!child.immunizationUpdated}">checked</c:if>   disabled>
                                    <span for="no" class="w-form-label">No</span>
                                </label>
                                <label class="w-radio">
                                    <input type="radio" data-name="pox" id="no-2" name="pox" value="no" class="w-form-formradioinput w-radio-input" 
                                    <c:if test="${!child.hadChickenPox}">checked</c:if>   disabled>
                                    <span for="no" class="w-form-label">No</span>
                                </label>
                                <label class="w-radio">
                                    <input type="radio" data-name="medications" id="no-2" value="no" class="w-form-formradioinput w-radio-input" 
                                    <c:if test="${!child.medications}">checked</c:if>   disabled>
                                    <span for="no" class="w-form-label">No</span>
                                </label>
                            </div>
                        </div>
                        <div>
                            <br> 
                            <label>Allergies/Dietary Requirements</label>
                            <textarea rows="4" class="text-field-14 w-input" maxlength="256" readonly>${child.allergy}</textarea>
                        <label>Special Medical Conditions:</label>
                        <textarea type="text" rows="4" class="text-field-14 w-input" readonly>${child.medicalConditions}</textarea>
                    </div>
                </div>
            </div>

            <div class="alert alert-info">
                <a role="button" data-toggle="collapse" href="#guardianInfo" aria-expanded="false" aria-controls="collapseExample">
                    <h4>Guardian Information</h4>
                </a>
            </div><br>

            <div class="collapse" id="guardianInfo">
                <div class="well">
                    <h5>Guardian 1 (Account Owner):</h5>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.account.accountFirstName} ${child.account.accountLastName}" required="" readonly>
                    <label>Relation to Child:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.account.relationToChild}" required="" readonly>
                    <label for="name">Main Contact (Email)</label>
                    <a href="mailto:${child.account.email}">${child.account.email}</a><hr>

                    <h5>Guardian 2</h5>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[0].emergencyFirstName} ${child.emergencyContactList[0].emergencyLastName}" required="" readonly>
                    <label>Address:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[0].emergencyAddress}" required="" readonly>
                    <label>Relation to Child:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[0].relationToChild}" required="" readonly>
                    <label>Home Phone: </label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[0].homePhoneNumber}" required="" readonly>
                    <label>Work Phone: </label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[0].workPhoneNumber}" required="" readonly>
                    <label>Cell Phone: </label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[0].cellphoneNumber}" required="" readonly><hr>

                    <h5>Other Caregiver</h5>
                    <label>Name:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[2].emergencyFirstName} ${child.emergencyContactList[2].emergencyLastName}" required="" readonly>
                    <label>Address:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[2].emergencyAddress}" required="" readonly>
                    <label>Phone: </label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[2].homePhoneNumber}" required="" readonly>
                </div>
            </div>


            <div class="alert alert-info">
                <a role="button" data-toggle="collapse" href="#emergencyInfo" aria-expanded="false" aria-controls="collapseExample">
                    <h4>Emergency Contact</h4>
                </a>
            </div>

            <div class="collapse" id="emergencyInfo">
                <div class="well">

                    <label>Name:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[1].emergencyFirstName} ${child.emergencyContactList[1].emergencyLastName}" required="" readonly>
                    <label>Address:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[1].emergencyAddress}" required="" readonly>
                    <label>Relation to Child:</label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[1].relationToChild}" required="" readonly>
                    <label>Home Phone: </label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[1].homePhoneNumber}" required="" readonly>
                    <label>Cell Phone: </label>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.emergencyContactList[1].cellphoneNumber}" required="" readonly><br><hr>

                    <h4>Authorized Pickup</h4>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.authorizedPickupList[0].authorizedFirstName} ${child.authorizedPickupList[0].authorizedLastName}" readonly>
                    <input type="text" class="text-field-14 w-input" maxlength="256" value="${child.authorizedPickupList[1].authorizedFirstName} ${child.authorizedPickupList[1].authorizedLastName}" readonly>
                </div>
            </div>
        </div>
        
    </body>
</html>
