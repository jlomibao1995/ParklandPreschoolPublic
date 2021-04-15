/* 
 * Functions for prompting to ocnfirm their actions before being redirected
 * to the servlet
 */

function confirmWithdraw(name) {

    var date = new Date();
    var day = date.getDay();
    var month = date.toLocaleString("default", {month: "long"});
    var week = (day - 1) / 7;
    var content;

    if (week >= 3) {
        content = "Any fees paid" + " for the month of " + month + " will not be refunded.<br>" +
                " Are you sure you want to withraw for this class?";
    } else {
        content = "Any fees paid" + " for the month of " + month + " will be refunded and " +
                "processed by the administrator within 7 business days.<br>" +
                " Are you sure you want to withraw for this class?";
    }

    $("#modal-body").html(content);
    $("#ModalTitle").html("Withdraw " + name);

    $('#promptDialogue').modal('show');
}

function withdraw() {
    document.getElementById("withdrawForm").submit();
}

function confirmRemove(name) {
    document.getElementById("ModalTitle").firstChild.nodeValue = "Remove Child from Account";
    var content = "Are you sure you want to remove " + name + " from your account?";
    $("#modal-body").html(content);
    $('#promptDialogue').modal('show');
}

function remove() {
    document.getElementById("removeForm").submit();
}

function confirmDeleteClass() {
    document.getElementById("ModalTitle").firstChild.nodeValue = "Delete Class";
    var content = "Deleting the class will delete all registrations associated with the class.<br>" +
            " Are you sure you want to delete this class?";
    $("#modal-body").html(content);
    $('#promptDialogue').modal('show');
}

function deleteClass() {
    document.getElementById("deleteClassForm").submit();
}

function confirmDeleteAccount() {
    document.getElementById("ModalTitle").firstChild.nodeValue = "Delete Account";

    var content = "Deleting the account will delete all child and registrations associated with the account.<br>" +
            " Are you sure you want to delete this account?";
    $("#modal-body").html(content);
    $('#promptDialogue').modal('show');
}

function deleteAccount() {
    document.getElementById("deleteAccountForm").submit();
}

function changeClassChosen(childId) {
    var classPicked = $('input[name="classChosen"]:checked').val();

    document.getElementById("nextClasses").href = "/registerchild?goTo=classes&classPicked=" + classPicked + "&childId=" + childId;
    document.getElementById("nextGuardian").href = "/registerchild?goTo=guardian&classPicked=" + classPicked + "&childId=" + childId;
    document.getElementById("nextPolicies").href = "/registerchild?goTo=policies&classPicked=" + classPicked + "&childId=" + childId;
}

function confirmDeleteRegistration() {
    document.getElementById("ModalTitle").firstChild.nodeValue = "Delete Registration";

    var content = "Deleting this registration will delete the registration from the class and the database.<br>" +
            " Are you sure you want to delete this registration?";
    $("#modal-body").html(content);
    $('#promptDialogue').modal('show');
}

function deleteRegistration() {
    document.getElementById("deleteRegistrationForm").submit();
}

function deactivateAccount() {
    document.getElementById("deactivateAccountForm").submit();
}


