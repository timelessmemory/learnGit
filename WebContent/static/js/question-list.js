function isSelect() {
      var checkObjs = document.getElementsByClassName('checkbox');
      var checkFlagObj = document.getElementById('checkFlag');
      for (var i=0; i<checkObjs.length; i++) {
          if (checkFlagObj.checked == false) {
              checkObjs[i].checked = false;
          } else {
              checkObjs[i].checked = true;
          }
      }
}

function hideTip() {
     setTimeout(function() {
         document.getElementById("flashMessageDiv").style.display = "none";
         window.location.reload();
       },3000);
}

function hideDialog() {
    $("#mask").hide();
    $("#dialog").hide();
}

function isShowDialog() {
    var isShow = false;
    var checkObjs = document.getElementsByClassName('checkbox');

    for (var i=0; i<checkObjs.length; i++) {
        if (checkObjs[i].checked == true) {
            isShow = true;
            break;
        }
    }

    if (isShow == true) {
        $('#dialog').show();
        $('#mask').show();
    } else {
        $("#tipMessage").html("Please choose which to delete!");
        $('#tipDialog').show();
        $('#mask').show();
    }
}

function autoCheckboxBind() {
      var checkFlagObj = $('#checkFlag');
    var checkObjs = document.getElementsByClassName('checkbox');
    for (var i=0; i<checkObjs.length; i++) {
        checkObjs[i].onclick = function() {
          for (var j=0; j<checkObjs.length; j++) {
              if(checkObjs[j].checked == false) {
                  checkFlagObj.attr("checked", false);
                  break;
              }
          }
        }
    }
}

function sort(path) {
    var searchText = $('#searchText').val();
    var orderObj = $('#order');
    var className = orderObj.attr("class");
    if (searchText) {
        if (isSearch == true) {
            if (className == 'increase_pic') {
                ajaxSearch(path, searchText, "DSC", "", returnPerPage, questionPrefix, detailhref, editHref);
            } else {
                ajaxSearch(path, searchText, "ASC", "", returnPerPage, questionPrefix, detailhref, editHref);
             }
        } else {
            if (className == 'increase_pic') {
                ajaxSearch(path, "", "DSC", "", returnPerPage, questionPrefix, detailhref, editHref);
             } else {
                ajaxSearch(path, "", "ASC", "", returnPerPage, questionPrefix, detailhref, editHref);
             }
        }
    } else {
        if (className == 'increase_pic') {
            ajaxSearch(path, "", "DSC", "", returnPerPage, questionPrefix, detailhref, editHref);
         } else {
            ajaxSearch(path, "", "ASC", "", returnPerPage, questionPrefix, detailhref, editHref);
         }
    }
}

function search(path) {
    var searchText = document.getElementById('searchText').value;
    ajaxSearch(path, searchText, '', '', returnPerPage, questionPrefix, detailhref, editHref);
}

function hideTipDialog() {
    $("#mask").hide();
    $("#tipDialog").hide();
}

function deleteAll(url, path) {
    var checkObjs = document.getElementsByClassName("checkbox");
    var deleteItems = [];
    for (var i=0; i<checkObjs.length; i++) {
        if (checkObjs[i].checked == true) {
            deleteItems.push(checkObjs[i].value);
        }
    }
    ajaxDelete(url, path, questionCondition, deleteItems, orderflag, returnCurrentPage, returnPerPage, questionPrefix, detailhref, editHref);
}

function ajaxDelete(url, path, questionCondition, items, orderflag, curPage, perPage, questionPrefix, detailhref, editHref) {
    $.ajax({
        type : 'get',
        url : url + "?items=" + items,
        dataType : 'json',
        success : function(data) {
            var message = data.result;
            $('#dialog').hide();
            $('#mask').hide();
            ajaxSearch(path, questionCondition, orderflag, curPage, perPage, questionPrefix, detailhref, editHref);
            $('#flashMessageDiv').html(message);
            $('#flashMessageDiv').show();
            setTimeout(function() {
                $("#flashMessageDiv").hide();
            },3000);
        },
        error: function(){
            console.log("error");
        },
        async : false
    });
}

function customPagination(curPage,pageCount, path) {
    var paginationObj = $("#putPages");
    paginationObj.html('');
    if (pageCount < 5) {
        for (var i = 0; i < pageCount; i++) {
            var aObj = getPaginationFragment(path, i + 1);
            if ((i+1) == curPage) {
                aObj.addClass('aShow');
            }
            paginationObj.append(aObj);
        }
    } else {
        if (curPage-3 > 1) {
            var aObj = getPaginationFragment(path, 1);
            paginationObj.append(aObj);
            
            var aObject = $("<a>").attr("class", "page").html("...");
            paginationObj.append(aObject);
            
            for (var i = curPage-2; i <= curPage; i++) {
                var aObj = getPaginationFragment(path, i);
                if (i == curPage) {
                    aObj.addClass('aShow');
                }
                paginationObj.append(aObj);
            }
        } else {
            for (var i = 1; i <= curPage; i++) {
                var aObj = getPaginationFragment(path, i);
                if (i == curPage) {
                    aObj.addClass('aShow');
                }
                paginationObj.append(aObj);
            }
        }
        if (curPage + 3 < pageCount) {
            for (var i = curPage + 1; i <= curPage + 2; i++) {
                var aObj = getPaginationFragment(path, i)
                paginationObj.append(aObj);
            }
            
            var aObject = $("<a>").attr("class", "page").html("...");
            paginationObj.append(aObject);
            
            var aObj = getPaginationFragment(path, pageCount);
            paginationObj.append(aObj);
        } else {
            for (var i = curPage + 1; i <= pageCount; i++) {
                var aObj = getPaginationFragment(path, i);
                paginationObj.append(aObj);
            }
        }
    }
}

function getPaginationFragment(path, i) {
    var aObj = $("<a>").attr("class", "page").html(i);
    var pageNumber = i;
    if (questionCondition == null) {
       (function(num) {
           aObj.on('click', function() {ajaxSearch(path, "", orderflag, num, returnPerPage, questionPrefix, detailhref, editHref);});
       })(pageNumber)
    } else {
       (function(num) {
           aObj.on('click', function() {ajaxSearch(path, questionCondition, orderflag, num, returnPerPage, questionPrefix, detailhref, editHref);});
       })(pageNumber)
    }
    return aObj;
}

function prepPage(curPage, path) {
    curPage -= 1;
    if (curPage == 0) {
        curPage = 1;
        $("#tipMessage").html("This is the first page!");
        $('#tipDialog').show();
        $('#mask').show();
        return ;
    }
    
    if (questionCondition == null) {
        ajaxSearch(path, '', orderflag, curPage, returnPerPage, questionPrefix, detailhref, editHref);
    } else {
        ajaxSearch(path, questionCondition, orderflag, curPage, returnPerPage, questionPrefix, detailhref, editHref);
    }
}

function nextPage(curPage, totalPage, path) {
    curPage += 1;
    if (curPage > totalPage) {
        curPage = totalPage;
        $("#tipMessage").html("This is the last page!");
        $('#tipDialog').show();
        $('#mask').show();
        return ;
    }
    
    if (questionCondition == null) {
        ajaxSearch(path, '', orderflag, curPage, returnPerPage, questionPrefix, detailhref, editHref);
    } else {
        ajaxSearch(path, questionCondition, orderflag, curPage, returnPerPage, questionPrefix, detailhref, editHref);
    }
}

function go(totalPage, path) {
    var pageInputObj = $("#goPageNumber");
    var curPage = pageInputObj.val();
    
    if (!isInt(curPage)) {
        $("#tipMessage").html("Please input legal number!");
        $('#tipDialog').show();
        $('#mask').show();
        pageInputObj.val('');
        return;
    }
    if (curPage <= 0) {
        curPage = 1;
    }
    if (curPage > totalPage) {
        curPage = totalPage;
    }
    if (questionCondition == null) {
        ajaxSearch(path, '', orderflag, curPage, returnPerPage, questionPrefix, detailhref, editHref);
    } else {
        ajaxSearch(path, questionCondition, orderflag, curPage, returnPerPage, questionPrefix, detailhref, editHref);
    }
}

function autoClick(event, path, totalPage) {
    if (event.keyCode == 13) {
    	if ($('#goPageNumber').is(':focus')) {
    		go(totalPage, path);
    	}
    	if ($('#searchText').is(':focus')) {
    		search(path);
    	}
    }
}

function ajaxSearch(url, condition, orderflag, curPage, perPage, questionPrefix, detailhref, editHref) {
    $.ajax({
        type : 'post',
        url : url,
        data : {questionCondition : condition, orderFlag : orderflag, curPage : curPage, perPage : perPage},
        dataType : 'json',
        success : function(data) {
            $('#listItems').html('');
            var html = convertJsonToHtml(data, detailhref, questionPrefix, editHref);
            $('#listItems').append(html);
            $('#checkFlag').attr("checked", false);
            autoCheckboxBind();
            isSearch = data.isQuestionSearch;
            returnPerPage = data.pagination.pageSize;
            orderflag = data.orderflag;
            questionCondition = data.questionCondition;
            returnCurrentPage = data.pagination.currentPage;
            returnTotalPage = data.pagination.totalPage;
            if (orderflag == 'ASC') {
                $("#order").attr("class", "increase_pic");
                $("#order").attr("src", "static/images/ICN_Increase_10x15.png.png");
            } else if (orderflag == 'DSC'){
                $("#order").attr("class", "decrease_pic");
                $("#order").attr("src", "static/images/ICN_Decrese_10x15.png.png");
            }
            var path = url;
            customPagination(returnCurrentPage, returnTotalPage, path);
            heighlight();
        },
        error: function(){
            console.log("error");
        },
        async : false
    });
}

function gethref(obj) {
    window.location.href = $(obj).attr('value');
}

function convertJsonToHtml(jsonData, detailhref, questionPrefix, editHref) {
    var questionArray = jsonData.questionList;
    var div = $("<div>");
    for (var i = 0; i < questionArray.length; i++) {
        var item = $("<div class='item'>");
        $("<label class='userid'>").html(i+1).appendTo(item);
        $("<label class='user question-id'>").html(questionPrefix + questionArray[i].id ).appendTo(item);
        var title = $("<label class='user description'>").appendTo(item);
        $("<a class='des-a'>").attr("href", detailhref + questionArray[i].id).html(htmlEncode(questionArray[i].title)).attr("title", questionArray[i].title).appendTo(title);
        var edit = $("<label class='edit'>").appendTo(item);
        (function(path) {
            $("<img/>").attr("src", "static/images/ICN_Edit_15x15.png.png").on("click", function() {window.location.href = path}).appendTo(edit);
        })(editHref + questionArray[i].id);
        var checkbox = $("<label class='check'>").appendTo(item);
        $("<input class='checkbox' type='checkbox'>").val(questionArray[i].id).appendTo(checkbox);
        div.append(item);
    }
    return div;
}

function showPic(orderflag) {
    if (orderflag == 'ASC') {
        $("#order").attr("class", "increase_pic");
        $("#order").attr("src", "static/images/ICN_Increase_10x15.png.png");
    } else {
        $("#order").attr("class", "decrease_pic");
        $("#order").attr("src", "static/images/ICN_Decrese_10x15.png.png");
    }
}

function selectFun(returnPerPage) {
    var selectObj = $('#selectPage');
    if (returnPerPage == null) {
        selectObj.val(15);
    } else {
        selectObj.val(returnPerPage);
    }
    selectObj.change(function() {
        ajaxSearch(path, questionCondition, orderflag, returnCurrentPage, this.value, questionPrefix, detailhref, editHref);
    });
}

$(function() {
    heighlight();
});

function heighlight() {
    $(".item").mouseover(function() {
        $(this).addClass('light');
        $(this).removeClass('grey');
    }).mouseout(function() {
        $(this).addClass('grey');
        $(this).removeClass('light');
    });
}