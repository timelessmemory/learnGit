$(function() {
    
    var fuc = function blurResult() {
    	if ($(this).val().trim() == '') {
            $(this).val('');
            $(this).addClass('jsCss');
            $(this).next().addClass('jsCssShow');
            $(this).next().removeClass('jsCssHide');
        } else {
            $(this).val($(this).val().trim());
            $(this).removeClass('jsCss');
            $(this).next().addClass('jsCssHide');
            $(this).next().removeClass('jsCssShow');
        }
    }

    function stylesheetShow(objhtml, objThiscCss, message) {
    	objThiscCss.val('');
    	objhtml.html(message);
    	objThiscCss.addClass('jsCss');
    	objhtml.addClass('jsCssShow');
    	objhtml.removeClass('jsCssHide');
    }
    
    function stylesheetHide(objThiscCss, objCss, value) {
    	objThiscCss.val(value);
    	objThiscCss.removeClass('jsCss');
    	objCss.addClass('jsCssHide');
    	objCss.removeClass('jsCssShow');
    }
    
    $("#examName").blur(fuc);

    $("#description").blur(fuc);

    $("#effectiveTime").blur(fuc);

    $("#quantity").blur(function() {
        if ($(this).val().trim() == '') {
            stylesheetShow($("#quantitySpan"), $(this), "Quantity is required!");
        } else {
            var quantity = $(this).val().trim();
            if (!isInt(quantity)) {
            	stylesheetShow($("#quantitySpan"), $(this), "Please input a number!");
            } else {
            	stylesheetHide($(this), $("#quantitySpan"), quantity);
                autoFill();
            }
        }
    });

    $("#point").blur(function() {
        if ($(this).val().trim() == '') {
            stylesheetShow($("#pointSpan"), $(this), "Point is Required!");
        } else {
            var point = $(this).val().trim();
            if (!isIntOrDot(point)) {
            	stylesheetShow($("#pointSpan"), $(this), "Please input a number or dot!");
            } else {
            	stylesheetHide($(this), $("#pointSpan"), point);
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
        }
    }

    $("#passCriteria").blur(function() {
        if ($(this).val().trim() == '') {
            stylesheetShow($("#passCriteriaSpan"), $(this), "PassCriteria is Required!");
        } else {
            var passCriteria = $(this).val().trim();
            if (!isIntOrDot(passCriteria)) {
            	stylesheetShow($("#passCriteriaSpan"), $(this), "PassCriteria must be a number!");
            } else {
            	stylesheetHide($(this), $("#passCriteriaSpan"), passCriteria);
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

function style(obj, objNext) {
    obj.addClass('jsCss');
    objNext.addClass('jsCssShow');
    objNext.removeClass('jsCssHide');
}

function styleHide(obj, objNext) {
    obj.removeClass('jsCss');
    objNext.addClass('jsCssHide');
    objNext.removeClass('jsCssShow');
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
        style($("#examName"), $("#examNameSpan"));
    } else {
        styleHide($("#examName"), $("#examNameSpan"));
    }

    if(description == "") {
        isSubmit = false;
        style($("#description"), $("#descriptionSpan"));
    } else {
        styleHide($("#description"), $("#descriptionSpan"));
    }

    if(effectiveTime == "") {
        isSubmit = false;
        style($("#effectiveTime"), $("#timeSpan"));
    } else {
        styleHide($("#effectiveTime"), $("#timeSpan"));
    }

    if(quantity == "") {
        isSubmit = false;
        style($("#quantity"), $("#quantitySpan"));
    } else if(!isInt(quantity)){
        isSubmit = false;
        $("#quantitySpan").html("Please input a number!");
        style($("#quantity"), $("#quantitySpan"));
    } else if (quantity.length > 8){
        isSubmit = false;
        $("#quantitySpan").html("The number is too big!");
        style($("#quantity"), $("#quantitySpan"));
    } else {
        styleHide($("#quantity"), $("#quantitySpan"));
    }
    
    if(point == "") {
        isSubmit = false;
        style($("#point"), $("#pointSpan"));
    } else if (!isIntOrDot(point)) {
        isSubmit = false;
        $("#pointSpan").html("Please input a number or dot!");
        style($("#point"), $("#pointSpan"));
    } else {
        styleHide($("#point"), $("#pointSpan"));
    }

    if(totalScore == "") {
        isSubmit = false;
        style($("#totalScore"), $("#scoreSpan"));
    } else {
        styleHide($("#totalScore"), $("#scoreSpan"));
    }

    if(passCriteria == "") {
        isSubmit = false;
        style($("#passCriteria"), $("#passCriteriaSpan"));
    } else if (!isIntOrDot(passCriteria)) {
        isSubmit = false;
        $("#passCriteriaSpan").html("PassCriteria must be a number!");
        style($("#passCriteria"), $("#passCriteriaSpan"));
    } else {
        styleHide($("#passCriteria"), $("#timeSpassCriteriaSpanpan"));
    }

    if (isSubmit == true) {
        $("#examForm").submit();
    }
}
