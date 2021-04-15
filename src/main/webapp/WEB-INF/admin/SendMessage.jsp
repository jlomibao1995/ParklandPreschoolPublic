<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Send message</title>
        <meta content="Send message" property="og:title">
        <meta content="Send message" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">
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
    <section id="contact-form" style="padding-top: 100px;padding-bottom: 100px;">
        <div class="w-container" style="padding-right: 20px;padding-left: 20px;border-radius: 10px;background-color: #48abe0;">
            <h2 style="color: #fff;">Email an Account</h2>
            <div class="w-form">
                <form id="wf-form-Contact-Form" name="wf-form-Contact-Form" data-name="Contact Form" action="testemail" method="POST">
                    <div><label for="Email" id="contact-email" class="field-label">Destination Email</label>
                        <input type="email" class="w-input" maxlength="256" name="accountTo" value="${accountEmail}" required="">
                    </div>
                    <div><label class="field-label">Subject</label>
                        <input type="text" class="w-input" maxlength="256" name="subject" required="">
                        <label class="field-label">Message</label>
                        <textarea data-name="Message" maxlength="10000" id="Message" name="content" class="w-input"></textarea>
                    </div>
                    <input type="submit" value="Submit" data-wait="Please wait..." class="submit-button-14 w-button">
                    <a href="/manageAccounts" class="w-button" style="margin-left:740px;border-radius:10px;background-color:#f03;">Go Back</a> 
                </form>
                <div class="w-form-done">
                    <div>Your Email has been successfully sent!</div>
                </div>
                <div class="w-form-fail">
                    <div>Oops! Something went wrong while submitting the form.</div>
                </div>
            </div>
        </div>
    </section>
    <c:import url="../footer.jsp" />
    <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="js/webflow.js" type="text/javascript"></script>
    <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
</body>
</html>