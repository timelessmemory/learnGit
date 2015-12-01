$(function() {

    $("#examName").blur(function() {
        if ($(this).val().trim() == '') {
            $(this).val('');
            $(this).css("border", "1px dotted red");
            $(this).next().css("display", "inline-block");
        } else {
            $(this).val($(this).val().trim());
            $(this).css("border", "0px dotted red");
            $(this).next().css("display", "none");
        }
    });

    $("#description").blur(function() {
        if ($(this).val().trim() == '') {
            $(this).val('');
            $(this).css("border", "1px dotted red");
            $(this).next().css("display", "inline-block");
        } else {
            $(this).val($(this).val().trim());
            $(this).css("border", "0px dotted red");
            $(this).next().css("display", "none");
        }
    });

    $("#effectiveTime").blur(function() {
        if ($(this).val() == '') {
            $(this).css("border", "1px dotted red");
            $(this).next().css("display", "inline-block");
        } else {
            $(this).css("border", "0px dotted red");
            $(this).next().css("display", "none");
        }
    });

    $("#quantity").blur(function() {
        if ($(this).val().trim() == '') {
            $(this).val('');
            $("#quantitySpan").html("Quantity is required!");
            $(this).css("border", "1px dotted red");
            $("#quantitySpan").css("display", "inline-block");
        } else {
            var quantity = $(this).val().trim();
            if (!isInt(quantity)) {
                $(this).css("border", "1px dotted red");
                $("#quantitySpan").html("Please input number!");
                $("#quantitySpan").css("display", "inline-block");
            } else {
                $(this).val(quantity);
                $(this).css("border", "0px dotted red");
                $("#quantitySpan").css("display", "none");
                autoFill();
            }
        }
    });

    $("#point").blur(function() {
        if ($(this).val().trim() == '') {
            $(this).val('');
            $("#pointSpan").html("Point is Required!");
            $(this).css("border", "1px dotted red");
            $("#pointSpan").css("display", "inline-block");
        } else {
            var point = $(this).val().trim();
            if (!isIntOrDot(point)) {
                $(this).css("border", "1px dotted red");
                $("#pointSpan").html("Please input number or dot!");
                $("#pointSpan").css("display", "inline-block");    
            } else {
                $(this).val(point);
                $(this).css("border", "0px dotted red");
                $("#pointSpan").css("display", "none");
                autoFill();
            }
        }
    });
    
    function autoFill() {
        var quantity = $("#quantity").val().trim();
        var point = $("#point").val().trim();
        if (isInt(quantity) && isIntOrDot(point)) {
            var total = quantity*point;
            total = total + "";
            $("#totalScore").val(total);
            $("#totalScore").css("border", "0px dotted red");
            $("#scoreSpan").css("display", "none");
        }
    }

    $("#passCriteria").blur(function() {
        if ($(this).val().trim() == '') {
            $(this).val('');
            $("#passCriteriaSpan").html("PassCriteria is Required!");
            $(this).css("border", "1px dotted red");
            $("#passCriteriaSpan").css("display", "inline-block");
        } else {
            var passCriteria = $(this).val().trim();
            if (!isIntOrDot(passCriteria)) {
                $(this).css("border", "1px dotted red");
                $("#passCriteriaSpan").html("PassCriteria must be a number!");
                $("#passCriteriaSpan").css("display", "inline-block");
            } else {
                $(this).val(passCriteria);
                $(this).css("border", "0px dotted red");
                $("#passCriteriaSpan").css("display", "none");
            }
        }
    });

})

function hideDialog() {
   $("#dialog").hide(300);
   $("#mask").hide();
}

function showDialog() {
       $("#dialog").show(300);
       $("#mask").show();
}

function saveAsDraft() {
    $("#isDraft").val("1");
    $("#examForm").submit();
}

function createExam(url) {
    var isSubmit = true;

    var examName = $("#examName").val();
    var description = $("#description").val();
    var effectiveTime = $("#effectiveTime").val();
    var quantity = $("#quantity").val();
    var point = $("#point").val();
    var totalScore = $("#totalScore").val();
    var passCriteria = $("#passCriteria").val();

    if(examName == "") {
        isSubmit = false;
        $("#examName").css("border", "1px dotted red");
        $("#examName").next().css("display", "inline-block");
    } else {
        $("#examName").css("border", "0px dotted red");
        $("#examName").next().css("display", "none");
    }

    if(description == "") {
        isSubmit = false;
        $("#description").css("border", "1px dotted red");
        $("#description").next().css("display", "inline-block");
    } else {
        $("#description").css("border", "0px dotted red");
        $("#description").next().css("display", "none");
    }

    if(effectiveTime == "") {
        isSubmit = false;
        $("#effectiveTime").css("border", "1px dotted red");
        $("#timeSpan").css("display", "inline-block");
    } else {
        $("#effectiveTime").css("border", "0px dotted red");
        $("#timeSpan").css("display", "none");
    }

    if(quantity == "") {
        isSubmit = false;
        $("#quantity").css("border", "1px dotted red");
        $("#quantitySpan").css("display", "inline-block");
    } else if(!isInt(quantity)){
        isSubmit = false;
        $("#quantity").css("border", "1px dotted red");
        $("#quantitySpan").html("Please input number!");
        $("#quantitySpan").css("display", "inline-block");
    } else if (quantity.length > 10){
    	isSubmit = false;
    	$("#quantity").css("border", "1px dotted red");
        $("#quantitySpan").html("The number is too big!");
        $("#quantitySpan").css("display", "inline-block");
    } else{
        $("#quantity").css("border", "0px dotted red");
        $("#quantitySpan").css("display", "none");
    }
    
    if(point == "") {
        isSubmit = false;
        $("#point").css("border", "1px dotted red");
        $("#pointSpan").css("display", "inline-block");
    } else if (!isIntOrDot(point)) {
        isSubmit = false;
        $("#point").css("border", "1px dotted red");
        $("#pointSpan").html("Please input number or dot!");
        $("#pointSpan").css("display", "inline-block");    
    } else {
        $("#point").css("border", "0px dotted red");
        $("#pointSpan").css("display", "none");
    }

    if(totalScore == "") {
        isSubmit = false;
        $("#totalScore").css("border", "1px dotted red");
        $("#scoreSpan").css("display", "inline-block");
    } else {
        $("#totalScore").css("border", "0px dotted red");
        $("#scoreSpan").css("display", "none");
    }

    if(passCriteria == "") {
        isSubmit = false;
        $("#passCriteria").css("border", "1px dotted red");
        $("#passCriteriaSpan").css("display", "inline-block");
    } else if (!isIntOrDot(passCriteria)) {
        isSubmit = false;
        $("#passCriteria").css("border", "1px dotted red");
        $("#passCriteriaSpan").html("PassCriteria must be a number!");
        $("#passCriteriaSpan").css("display", "inline-block");
    } else {
        $("#passCriteria").css("border", "0px dotted red");
        $("#timeSpassCriteriaSpanpan").css("display", "none");
    }

    if (isSubmit == true) {
        $.ajax({
            type : 'post',
            url : url,
            data : {quantity : quantity},
            dataType : 'json',
            success : function(data) {
                if (data.result == 'yes') {
                    showDialog();
                } else {
                    $("#examForm").submit();
                }
            },
            error: function(){
                alert('error');
                return false;
            },
            async : false
        });
    }
}
