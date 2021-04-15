<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Sat Feb 06 2021 02:16:24 GMT+0000 (Coordinated Universal Time)  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html data-wf-page="601a4ef657edde325099c11a" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Backup and Restore</title>
        <meta content="Backup and Restore" property="og:title">
        <meta content="Backup and Restore" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">
        <link href="css/normalize.css" rel="stylesheet" type="text/css">
        <link href="css/webflow.css" rel="stylesheet" type="text/css">
        <link href="css/parkland-preschool-project.webflow.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js" type="text/javascript"></script>
        <script type="text/javascript">WebFont.load({google: {families: ["Merriweather:300,300italic,400,400italic,700,700italic,900,900italic", "Open Sans:300,300italic,400,400italic,600,600italic,700,700italic,800,800italic", "Roboto:100,100italic,300,300italic,regular,italic,500,500italic,700,700italic,900,900italic", "Comic Neue:300,300italic,regular,italic,700,700italic"]}});</script>
        <!-- [if lt IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js" type="text/javascript"></script><![endif] -->
        <script type="text/javascript">!function (o, c) {
                var n = c.documentElement, t = " w-mod-";
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch")
            }(window, document);</script>
    </head>
    <body>
        <c:import url="navbar.html" />
        <h1 class="heading-33">Backup and Restore</h1>
        <div class="w-row">
            <div class="w-col w-col-3"></div>
            <div class="column-10 w-clearfix w-col w-col-3">
                <a href="#" class="button-17 w-button">Backup ON</a>
            </div>
            <div class="column-9 w-col w-col-3">
                <a href="#" class="button-18 w-button">Backup OFF</a>
            </div>
            <div class="w-col w-col-3"></div>
        </div>
        <div class="container-6 w-container">
            <div class="form-block-2 w-form">
                <h3 class="heading-34">Create Backup</h3>
                <form id="wf-form-Backup-Form" name="wf-form-Backup-Form" data-name="Backup Form"><label for="name">File location for Backup</label><input type="text" class="text-field-23 w-input" maxlength="256" name="name" data-name="Name" placeholder="C:\BackupFiles" id="name"><label for="email">How often would you like the system backed up?</label><label class="radio-button-field-2 w-radio"><input type="radio" data-name="BackupTime" id="Once" name="BackupTime" value="Once" class="w-form-formradioinput w-radio-input"><span for="Once" class="w-form-label">Only Once</span></label><label class="radio-button-field w-radio"><input type="radio" data-name="BackupTime" id="Daily" name="BackupTime" value="Daily" class="w-form-formradioinput w-radio-input"><span for="Daily" class="w-form-label">Daily </span></label><label class="radio-button-field w-radio"><input type="radio" data-name="BackupTime" id="Weekly" name="BackupTime" value="Weekly" class="w-form-formradioinput w-radio-input"><span for="Weekly" class="w-form-label">Weekly</span></label><label class="radio-button-field w-radio"><input type="radio" data-name="BackupTime" id="Monthly" name="BackupTime" value="Monthly" class="w-form-formradioinput w-radio-input"><span for="Monthly" class="w-form-label">Monthly</span></label><label class="radio-button-field w-radio"><input type="radio" data-name="BackupTime" id="Yearly" name="BackupTime" value="Yearly" class="w-form-formradioinput w-radio-input"><span for="Yearly" class="w-form-label">Yearly</span></label><label class="w-radio"><input type="radio" data-name="BackupTime" id="YearlyOnDate" name="BackupTime" value="YearlyOnDate" class="w-form-formradioinput w-radio-input"><span for="YearlyOnDate" class="w-form-label">On a specific date each year</span></label><input type="text" class="text-field-24 w-input" maxlength="256" name="field" data-name="Field" placeholder="Jan 1" id="field" required=""><input type="submit" value="Create Backup" data-wait="Please wait..." class="submit-button-12 w-button"></form>
                <div class="success-message-3 w-form-done">
                    <div>Backup Created and Scheduled</div>
                </div>
                <div class="error-message-4 w-form-fail">
                    <div>Invalid information entered. Please try again.</div>
                </div>
            </div>
        </div>
        <div class="w-container">
            <h3 class="heading-35">Revert to Backup</h3>
            <div class="w-row">
                <div class="w-col w-col-6">
                    <ul role="list" class="list-8">
                        <li>
                            <a href="#">C:\Backups\Backup 5</a>
                        </li>
                        <li>
                            <a href="#">C:\Backups\Backup 4</a>
                        </li>
                        <li>
                            <a href="#">C:\Backups\Backup 3</a>
                        </li>
                        <li>
                            <a href="#">C:\Backups\Backup 2</a>
                        </li>
                        <li>
                            <a href="#">C:\Backups\Backup 1</a>
                        </li>
                    </ul>
                </div>
                <div class="w-col w-col-6">
                    <div class="w-form">
                        <form id="email-form" name="email-form" data-name="Email Form"><label for="name-2">Date Created</label><input type="text" class="w-input" maxlength="256" name="name-2" data-name="Name 2" placeholder="" id="name-2"><label for="email">Time Created</label><input type="email" class="w-input" maxlength="256" name="email" data-name="Email" placeholder="" id="email" required=""><label for="email-2">File Location</label><input type="email" class="w-input" maxlength="256" name="email-2" data-name="Email 2" placeholder="" id="email-2" required="">
                            <div class="text-block-30">Clicking Revert to Backup will ERASE all data not backed up. Make sure you have backed up any data you would like saved before clicking.</div><input type="submit" value="Revert to Backup" data-wait="Please wait..." class="submit-button-15 w-button">
                        </form>
                        <div class="w-form-done">
                            <div>Thank you! Your submission has been received!</div>
                        </div>
                        <div class="w-form-fail">
                            <div>Oops! Something went wrong while submitting the form.</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <c:import url="../footer.jsp" />
        
        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>