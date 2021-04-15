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
        <c:if test="${success != null && success}"><div class="alert alert-success">${message}</div></c:if>
        <c:if test="${success != null && !success}"><div class="alert alert-danger">${message}</div></c:if>

            <div class="w-row">
                <div class="w-col w-col-6">
                    <label>First Name:</label>
                    <input type="text" class="w-input" maxlength="256" name="firstName" value="${child.childFirstName}" data-name="Child Name"id="ChildName" required="">
                <label>Address:</label>
                <input type="text" class="w-input" maxlength="256" name="address" value="${child.childAddress}" data-name="Address" id="Address">
                <label>Gender</label>
                <select id="gender" name="gender" data-name="Gender" class="w-select" required>
                    <option value="" selected disabled hidden>Choose here</option>
                    <option value="M" <c:if test="${fn:contains(child.childGender, 'M')}">selected</c:if>>Boy</option>
                    <option value="F" <c:if test="${fn:contains(child.childGender, 'F')}">selected</c:if>>Girl</option>
                    </select>
                    <label>Doctor:</label>
                    <input type="text" class="w-input" maxlength="256" name="doctorName" value="${child.doctor}" data-name="Doc Office Name" id="DocOfficeName">
                <label>Clinic/Doctor Phone Number: </label>
                <input type="tel" class="w-input" maxlength="256" name="medicalPhoneNumber" value="${child.medicalPhoneNumber}" required="">
            </div>
            <div class="w-col w-col-6">
                <label>Last Name: </label>
                <input type="text" class="w-input" maxlength="256" name="lastName" value="${child.childLastName}" data-name="Child Name" id="ChildName" required="">
                <label>Birthdate:</label>
                <div class="w-row">
                    <div class="w-col w-col-3">
                        <select name="month" class="w-select" required>
                            <option value="" selected disabled hidden>Month</option>
                            <option value="01" <c:if test="${month == '01'}">selected</c:if>>January</option>
                                <option value="02" <c:if test="${month == '02'}">selected</c:if>>February</option>
                                <option value="03" <c:if test="${month == '03'}">selected</c:if>>March</option>
                                <option value="04" <c:if test="${month == '04'}">selected</c:if>>April</option>
                                <option value="05" <c:if test="${month == '05'}">selected</c:if>>May</option>
                                <option value="06" <c:if test="${month == '06'}">selected</c:if>>June</option>
                                <option value="07" <c:if test="${month == '07'}">selected</c:if>>July</option>
                                <option value="08" <c:if test="${month == '08'}">selected</c:if>>August</option>
                                <option value="09" <c:if test="${month == '09'}">selected</c:if>>September</option>
                                <option value="10" <c:if test="${month == '10'}">selected</c:if>>October</option>
                                <option value="11" <c:if test="${month == '11'}">selected</c:if>>November</option>
                                <option value="12" <c:if test="${month == '12'}">selected</c:if>>December</option>
                            </select>
                        </div>
                        <div class="w-col w-col-3"><input type="number" name="day" max="31" min="1" class="w-input" required placeholder="Day" value="<fmt:formatDate pattern='dd' value='${child.childBirthdate}'/>"></div>
                    <div class="w-col w-col-3"><input type="number" name="year" max="3000" min="2017" class="w-input" required placeholder="Year" value="<fmt:formatDate pattern='yyyy' value='${child.childBirthdate}'/>"></div>
                </div>
                <label>Postal Code:</label>
                <input type="text" class="w-input" maxlength="256" name="postalCode" value="${child.childPostalCode}" data-name="Postal Code" id="postalCode" required="">
                <label>Alberta Health Care Number:</label>
                <input type="text" class="w-input" maxlength="256" name="healthCareNum" value="${child.healthcareNum}" data-name="Health Care" id="HealthCare" required="">
                <label>Clinic: </label>
                <input type="text" class="w-input" maxlength="256" name="clinic" value="${child.clinic}" data-name="Doc Office Num" id="DocOfficeNum">
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
                    <input type="radio" data-name="shots" id="yes" name="shots" value="true" class="w-form-formradioinput w-radio-input" 
                           <c:if test="${child.immunizationUpdated}">checked</c:if> required>
                           <span for="yes" class="w-form-label">Yes</span>
                    </label>
                    <label class="w-radio">
                        <input type="radio" data-name="pox" id="yes-2" name="pox" value="true" class="w-form-formradioinput w-radio-input" 
                        <c:if test="${child.hadChickenPox}">checked</c:if> required>
                        <span for="yes" class="w-form-label">Yes</span>
                    </label>
                    <label class="w-radio">
                        <input type="radio" data-name="medications" id="yes-2" name="medications" value="true" class="w-form-formradioinput w-radio-input" 
                        <c:if test="${child.medications}">checked</c:if>>
                        <span for="yes" class="w-form-label">Yes</span>
                    </label>
                </div>
                <div class="w-col w-col-1">
                    <label class="w-radio">
                        <input type="radio" data-name="shots" id="no" name="shots" value="false" class="w-form-formradioinput w-radio-input" 
                        <c:if test="${!child.immunizationUpdated}">checked</c:if> required>
                        <span for="no" class="w-form-label">No</span>
                    </label>
                    <label class="w-radio">
                        <input type="radio" data-name="pox" id="no-2" name="pox" value="false" class="w-form-formradioinput w-radio-input" 
                        <c:if test="${!child.hadChickenPox}">checked</c:if> required>
                        <span for="no" class="w-form-label">No</span>
                    </label>
                    <label class="w-radio">
                        <input type="radio" data-name="medications" id="no-2" name="medications" value="false" class="w-form-formradioinput w-radio-input" 
                        <c:if test="${!child.medications}">checked</c:if>>
                        <span for="no" class="w-form-label">No</span>
                    </label>
                </div>
                <div class="w-col w-col-3"></div>
            </div>
            <div>
                <br> 
                <label>Allergies/Dietary Requirements</label>
                <textarea rows="4" name="allergies" class="w-input" maxlength="256">${child.allergy}</textarea>
            <label>Special Medical Conditions:</label>
            <textarea type="text" name="medicalConditions" rows="4" class="w-input">${child.medicalConditions}</textarea>
        </div>
        <div class="w-row">
            <br><hr>
            <h4>Guardian Information</h4>
            <label>Guardian 1 (Account Owner):</label>
            <input type="text" class="w-input" maxlength="256" name="accountName" value="${child.account.accountFirstName} ${child.account.accountLastName}" required="" readonly>
            <label>Relation to Child:</label>
            <input type="text" class="w-input" maxlength="256" value="${child.account.relationToChild}" name="accountRelation" required=""><br>

            <h5>Guardian 2</h5>
            <div class="w-col w-col-6">
                <label>First Name:</label>
                <input type="text" class="w-input" value="${child.emergencyContactList[0].emergencyFirstName}" name="guardian2FirstName" maxlength="256" required="">
                <label>Address:</label>
                <input type="text" class="w-input" value="${child.emergencyContactList[0].emergencyAddress}" name="guardian2Address" maxlength="256" required="">
                <label>Home Phone: </label>
                <input type="tel" class="w-input" value="${child.emergencyContactList[0].homePhoneNumber}" name="guardian2HomePhone" maxlength="256" required="">
                <label>Work Phone: </label>
                <input type="tel" class="w-input" value="${child.emergencyContactList[0].workPhoneNumber}" name="guardian2WorkPhone" maxlength="256" required=""><br>
            </div>
            <div class="w-col w-col-6">
                <label>Last Name:</label>
                <input type="text" value="${child.emergencyContactList[0].emergencyLastName}" name="guardian2LastName" class="w-input" maxlength="256" required="">
                <label>Relation to Child:</label>
                <input type="text" class="w-input" value="${child.emergencyContactList[0].relationToChild}" name="guardian2Relation" maxlength="256" required="">
                <label>Cell Phone: </label>
                <input type="tel" class="w-input" value="${child.emergencyContactList[0].cellphoneNumber}" name="guardian2CellPhone" maxlength="256" required="">
            </div>
        </div>
        <div class="w-row">
            <h5>Other Caregiver</h5>
            <div class="w-col w-col-6">
                <label>First Name:</label>
                <input type="text" class="w-input" value="${child.emergencyContactList[2].emergencyFirstName}" name="otherFirstName" maxlength="256" required="">
                <label>Address:</label>
                <input type="text" class="w-input" value="${child.emergencyContactList[2].emergencyAddress}" name="otherAddress" maxlength="256" required="">
            </div>
            <div class="w-col w-col-6">
                <label>Last Name:</label>
                <input type="text" class="w-input" value="${child.emergencyContactList[2].emergencyLastName}" name="otherLastName" maxlength="256" required="">
                <label>Phone: </label>
                <input type="tel" class="w-input" value="${child.emergencyContactList[2].homePhoneNumber}" name="otherPhone" maxlength="256" required="">
            </div>
        </div>

        <div class="w-row">
            <br><hr>
            <h4>Emergency Contact (Other than guardian)</h4>
            <div class="w-col w-col-6">
                <label>First Name:</label>
                <input type="text" class="w-input" value="${child.emergencyContactList[1].emergencyFirstName}" maxlength="256" name="emergencyFirstName" required="">
                <label>Address:</label>
                <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[1].emergencyAddress}" name="emergencyAddress" required="">
                <label>Home Phone: </label>
                <input type="tel" class="w-input" maxlength="256" value="${child.emergencyContactList[1].homePhoneNumber}" name="emergencyHomePhone" required="">
            </div>
            <div class="w-col w-col-6">
                <label>Last Name:</label>
                <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[1].emergencyLastName}" name="emergencyLastName" required="">
                <label>Relation to Child:</label>
                <input type="text" class="w-input" maxlength="256" value="${child.emergencyContactList[1].relationToChild}" name="emergencyRelation" required="">
                <label>Cell Phone: </label>
                <input type="tel" class="w-input" maxlength="256" value="${child.emergencyContactList[1].cellphoneNumber}" name="emergencyCellPhone" required="">
            </div>
        </div>

        <div class="w-row">
            <br><hr>
            <h4>Authorized Pickup</h4>
            <div class="w-col w-col-6">
                <label>Authorized Pickup 1 First Name: </label>
                <input type="text" class="w-input" maxlength="256" value="${child.authorizedPickupList[0].authorizedFirstName}" name="authorized1FirstName" required="">
                <label>Authorized Pickup 2 First Name: </label>
                <input type="text" class="w-input" maxlength="256" value="${child.authorizedPickupList[1].authorizedFirstName}" name="authorized2FirstName" required="">
            </div>
            <div class="w-col w-col-6">
                <label>Last name: </label>
                <input type="text" class="w-input" maxlength="256" value="${child.authorizedPickupList[0].authorizedLastName}" name="authorized1LastName" required="">
                <label>Last name: </label>
                <input type="text" class="w-input" maxlength="256" value="${child.authorizedPickupList[1].authorizedLastName}" name="authorized2LastName" required="">
            </div>
        </div>

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