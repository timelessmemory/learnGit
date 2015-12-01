$(function() {
    $(".option_question").blur(function() {
        if ($(this).val().trim() != "") {
            $(this).css("border","1px solid #2E4358");
            $(this).next().css("display","none");
            $(this).val($(this).val().trim());
        } else {
            $(this).css("border","1px dotted red");
            $(this).next().css("display","inline-block");
            $(this).val($(this).val().trim());
        }
    });

    $("#questionTitle").blur(function() {
    	if ($(this).val().trim() != "") {
            $(this).css("border","1px solid #2E4358");
            $(this).val($(this).val().trim());
        } else {
            $(this).css("border","1px dotted red");
            $('#titleErrorMessage').css("display", "inline-block");
            $(this).val($(this).val().trim());
        }
    });
    
    $("input[type='radio']").click(function() {
    	$(this).attr("checked", true);
    	$("input[type='text']").css("background-color", "#FFF");
    	$(this).next().next().css("background-color", "#D2DAE3");
//    	}
    });
});

function createQues() {
    var isSubmit = true;
    var titleValue = $("#questionTitle").val();
    var answerAValue = $("#answerA").val();
    var answerBValue = $("#answerB").val();
    var answerCValue = $("#answerC").val();
    var answerDValue = $("#answerD").val();

    var titleErrorObj = $("#titleErrorMessage");
    var answerAErrorObj = $("#answerAErrorMessage");
    var answerBErrorObj = $("#answerBErrorMessage");
    var answerCErrorObj = $("#answerCErrorMessage");
    var answerDErrorObj = $("#answerDErrorMessage");

    if (!titleValue) {
        isSubmit = false;
        titleErrorObj.css("display" , "inline-block");
        $("#questionTitle").css("border","1px dotted red");
    } else {
        titleErrorObj.css("display" , "none");
        $("#questionTitle").css("border","0px dotted red");
    }

    if (!answerAValue) {
        isSubmit = false;
        answerAErrorObj.css("display" , "inline-block");
        $("#answerA").css("border","1px dotted red");
    } else {
        answerAErrorObj.css("display" , "none");
        $("#answerA").css("border","0px dotted red");
    }

    if (!answerBValue) {
        isSubmit = false;
        answerBErrorObj.css("display" , "inline-block")
        $("#answerB").css("border","1px dotted red");
    } else {
        answerBErrorObj.css("display" , "none");
        $("#answerB").css("border","0px dotted red");
    }

    if (!answerCValue) {
        isSubmit = false;
        answerCErrorObj.css("display" , "inline-block")
        $("#answerC").css("border","1px dotted red");
    } else {
        answerCErrorObj.css("display" , "none");
        $("#answerC").css("border","0px dotted red");
    }

    if (!answerDValue) {
        isSubmit = false;
        answerDErrorObj.css("display" , "inline-block")
        $("#answerD").css("border","1px dotted red");
    } else {
        answerDErrorObj.css("display" , "none");
        $("#answerD").css("border","0px dotted red");
    }

    if (isSubmit == true) {
        document.getElementById("questionForm").submit();
    }
}

function validationQues(url) {
    var value = $.trim($('#questionTitle').val());
    if (value == '') {
        $('#titleErrorMessage').html('Question is required!');
        $('#titleErrorMessage').css("display", "inline-block");
        return;
    }
    $.ajax({
        type : 'post',
        url : url,
        data : {ajaxtitle : value},
        dataType : 'json',
        success : function(data) {
            if (data.result == 'exist') {
                $('#titleErrorMessage').html('This question has existed!');
                $("#questionTitle").css("border","1px dotted red");
                $('#titleErrorMessage').css("display", "inline-block");
            } else {
                $('#titleErrorMessage').html('Question is required!');
                $('#titleErrorMessage').css("display", "none");
            }
        },
        error: function(){
            console.log('error');
        },
        async : false
    });
 }