$(function() {
    $(".option_question").blur(function() {
        if ($(this).val().trim() != "") {
            $(this).css("border", "0px dotted red");
            $(this).next().css("display", "none");
            $(this).val($(this).val().trim());
        } else {
            $(this).css("border", "1px dotted red");
            $(this).next().css("display", "inline-block");
            $(this).val($(this).val().trim());
        }
    });
});

function editQues() {
    var isSubmit = true;
    var titleValue = $("#question-title").val();
    var answerAValue = $("#answerA").val();
    var answerBValue = $("#answerB").val();
    var answerCValue = $("#answerC").val();
    var answerDValue = $("#answerD").val();

    var titleErrorObj = $("#titleErrorMessage");
    var answerAErrorObj = $("#answerAErrorMessage");
    var answerBErrorObj = $("#answerBErrorMessage");
    var answerCErrorObj = $("#answerCErrorMessage");
    var answerDErrorObj = $("#answerDErrorMessage");

    if (titleValue == "") {
        isSubmit = false;
        titleErrorObj.css("display" , "inline-block");
        $("#questionTitle").css("border", "1px dotted red");
    } else {
        titleErrorObj.css("display" , "none");
        $("#questionTitle").css("border", "0px dotted red");
    }

    if (!answerAValue) {
        isSubmit = false;
        answerAErrorObj.css("display" , "inline-block");
        $("#answerA").css("border", "1px dotted red");
    } else {
        answerAErrorObj.css("display" , "none");
        $("#answerA").css("border", "0px dotted red");
    }

    if (!answerBValue) {
        isSubmit = false;
        answerBErrorObj.css("display" , "inline-block")
        $("#answerB").css("border", "1px dotted red");
    } else {
        answerBErrorObj.css("display" , "none");
        $("#answerB").css("border", "0px dotted red");
    }

    if (!answerCValue) {
        isSubmit = false;
        answerCErrorObj.css("display" , "inline-block")
        $("#answerC").css("border", "1px dotted red");
    } else {
        answerCErrorObj.css("display" , "none");
        $("#answerC").css("border", "0px dotted red");
    }

    if (!answerDValue) {
        isSubmit = false;
        answerDErrorObj.css("display" , "inline-block")
        $("#answerD").css("border", "1px dotted red");
    } else {
        answerDErrorObj.css("display" , "none");
        $("#answerD").css("border", "0px dotted red");
    }

    if (isSubmit == true) {
        document.getElementById("questionForm").submit();
    }
}

function clearForm() {
    document.getElementById("questionForm").reset();
}