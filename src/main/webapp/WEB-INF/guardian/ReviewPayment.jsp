<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Wed Feb 10 2021 05:38:37 GMT+0000 (Coordinated Universal Time)  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html data-wf-page="60235e6a7a2e0a82950d3fb6" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool | Review Payment</title>
        <meta content="Review Payment" property="og:title">
        <meta content="Review Payment" property="twitter:title">
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
        <h1 class="heading-37">Review Payment</h1>
        <div class="container-7 w-container">
            <h2 class="heading-38">Transaction Details</h2>
            <div class="text-block-32">Description: ${transaction.description}</div>
            <div class="text-block-32">Subtotal: ${transaction.amount.details.subtotal} CAD</div>
            <div class="text-block-32">Total: ${transaction.amount.total} CAD</div>
            <h2 class="heading-38">Payer Information</h2>
            <div class="text-block-32">First name: ${payer.firstName}</div>
            <div class="text-block-32">Last name: ${payer.lastName}</div>
            <div class="text-block-32">Email: ${payer.email}</div>
            <div class="text-block-32">Address: ${address.line1}</div>
            <div class="text-block-32">City: ${address.city}</div>
            <div class="text-block-32">Country: ${address.countryCode}</div>
            <div class="text-block-32">Postal code: ${address.postalCode}</div>
            <form action="executepayment" method="post">
                <input type="hidden" name="paymentId" value="${param.paymentId}" />
                <input type="hidden" name="PayerID" value="${param.PayerID}"/>
                <input type="submit" value="Pay with PayPal" class="button-26 w-button">
            </form>
        </div>
                
        <c:import url="../footer.jsp" />
        
        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>