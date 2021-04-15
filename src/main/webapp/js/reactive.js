//formats phone number
$('[type="tel"]').mask('(000) 000-0000');
$("#postalCode").mask('S0S 0S0');
$("#postalCode2").mask('S0S 0S0');

$("#password").focus(() => {
    var message = "<b>Password must have the following:" +
            "<br> At least 8 characters" + 
            "<br> At least one uppercase and lowercase letter" + 
            "<br> At least one number" +
            "<br> At least one special character e.g. !@#? as long as its not < or > </b>";
    $("#passwordMessage").html(message);
});
document.getElementsByTagName()
//checks if the password requirements are met
$("#password").blur(() => {
    var password = $("#password").val();
    
    if (password.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)){
        $("#passwordMessage").html("");
    } else {
        var message = "<b><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span> Password must have the following:" +
            "<br> At least 8 characters" + 
            "<br> At least one uppercase and lowercase letter" + 
            "<br> At least one number" +
            "<br> At least one special character e.g. !@#? as long as its not < or > </b>";
            $("#passwordMessage").html(message);
    } 
});

//checks if both passwords match and displays a message if not
$("#confirmPassword").keyup(() => {
   var password = $("#password").val();
   var confirmPassword = $("#confirmPassword").val();
   
   if (password !== confirmPassword){
       var message = "<b>You passwords don't match, please enter again</b>";
       $("#passwordMatch").html(message);
   } else {
       $("#passwordMatch").html("");
   }
});

//checks if both passwords match and display a message if not
$("#password").keyup(() => {
   var password = $("#password").val();
   var confirmPassword = $("#confirmPassword").val();
   
   if (password !== confirmPassword){
       var message = "<b>You passwords don't match, please enter again</b>";
       $("#passwordMatch").html(message);
   } else {
       $("#passwordMatch").html("");
   }
});

$("#password2").focus(() => {
    var message = "<b>Password must have the following:" +
            "<br> At least 8 characters" + 
            "<br> At least one uppercase and lowercase letter" + 
            "<br> At least one number" +
            "<br> At least one special character e.g. !@#? as long as its not < or > </b>";
    $("#passwordMessage2").html(message);
});

//checks if the password requirements are met
$("#password2").blur(() => {
    var password = $("#password2").val();
    
    if (password.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)){
        $("#passwordMessage2").html("");
    } else {
        var message = "<b><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span> Password must have the following:" +
            "<br> At least 8 characters" + 
            "<br> At least one uppercase and lowercase letter" + 
            "<br> At least one number" +
            "<br> At least one special character e.g. !@#? as long as its not < or > </b>";
            $("#passwordMessage2").html(message);
    }
});
