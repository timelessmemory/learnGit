function heighlight() {
    $(".item").mouseover(function() {
        $(this).css("background-color", "#D2DAE3");
    }).mouseout(function() {
        $(this).css("background-color", "#FFF");
    });
}

function hideTip() {
     setTimeout(function() {
         document.getElementById("flashMessageDiv").style.display = "none";
         window.location.reload();
       },3000);
}

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

function hideDialog() {
    $("#mask").css("display", 'none')
    $("#dialog").css("display", 'none')
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
        $('#dialog').css("display", 'block');
        $('#mask').css("display", 'block');
    } else {
        $("#tipMessage").html("Please choose which to delete!");
        $('#tipDialog').css("display", 'block');
        $('#mask').css("display", 'block');
    }
}


function customPagination(curPage,pageCount) {
    var paginationObj = document.getElementById("putPages");
    var path = '';
    if (curPage < 9) {
        if (pageCount > 9) {
            for (var i = 0; i < 9; i++) {
                var aObj = document.createElement("a");
                aObj.className = "page";
                aObj.innerHTML = (i+1);
                var pageNumber = (i+1);
                var path = urlOne + sortArray + urlTwo + columnArray + urlThree + pageNumber + urlFour;
                if ((i+1) == curPage) {
                    aObj.style.color = "#FE9901";
                    aObj.style.fontSize = "15px";
                }
                aObj.href = path;
                paginationObj.appendChild(aObj);
            }
        } else {
            for (var i = 0; i < pageCount; i++) {
                var aObj = document.createElement("a");
                aObj.className = "page";
                aObj.innerHTML = (i+1);
                var pageNumber = (i+1);
                var path = urlOne + sortArray + urlTwo + columnArray + urlThree + pageNumber + urlFour;
                if ((i+1) == curPage) {
                    aObj.style.color = "#FE9901";
                    aObj.style.fontSize = "15px";
                }
                aObj.href = path;
                paginationObj.appendChild(aObj);
            }
        }
    } else {
        var prepOffset = 4;
        var nextOffset = 4;
        for ( var i = 0;i <= 4; i++) {
            if ((curPage+i) == pageCount) {
                prepOffset = 8-i;
                nextOffset = i;
            }
        }
        for (var i = prepOffset ; i >= 1; i--) {
            var aObj = document.createElement("a");
            aObj.className = "page";
            aObj.innerHTML = (curPage - i);
            var pageNumber = (curPage - i);
            var path = urlOne + sortArray + urlTwo + columnArray + urlThree + pageNumber + urlFour;
            if ((i+1) == curPage) {
                aObj.style.color = "#FE9901";
                aObj.style.fontSize = "15px";
            }
            aObj.href = path;
            paginationObj.appendChild(aObj);
        }
        var aObj = document.createElement("a");
        aObj.className = "page";
        aObj.innerHTML = (curPage);
        var pageNumber = (curPage);
        var path = urlOne + sortArray + urlTwo + columnArray + urlThree + pageNumber + urlFour;
        aObj.style.color = "#FE9901";
        aObj.style.fontSize = "15px";
        aObj.href = path;
        paginationObj.appendChild(aObj);
        for (var i = 1; i <= nextOffset; i++) {
            var aObj = document.createElement("a");
            aObj.className = "page";
            aObj.innerHTML = (curPage + i);
            var pageNumber = (curPage + i);
            var path = urlOne + sortArray + urlTwo + columnArray + urlThree + pageNumber + urlFour;

            if ((i+1) == curPage) {
                aObj.style.color = "#FE9901";
                aObj.style.fontSize = "15px";
            }
            aObj.href = path;
            paginationObj.appendChild(aObj);
        }
    }
}

function prepPage(curPage) {
    curPage -= 1;
    if (curPage == 0) {
        curPage = 1;
        $("#tipMessage").html("This is the first page!");
        $('#tipDialog').css("display", 'block');
        $('#mask').css("display", 'block');
        return ;
    }
    window.location.href = urlOne + sortArray + urlTwo + columnArray + urlThree + curPage + urlFour;
}

function nextPage(curPage, totalPage) {
    curPage += 1;
    if (curPage > totalPage) {
        curPage = totalPage;
        $("#tipMessage").html("This is the last page!");
        $('#tipDialog').css("display", 'block');
        $('#mask').css("display", 'block');
        return ;
    }
    window.location.href = urlOne + sortArray + urlTwo + columnArray + urlThree + curPage + urlFour;
}

function go(totalPage) {
    var pageInputObj = $("#goPageNumber");
    var curPage = pageInputObj.val();
    if (!isInt(curPage)) {
        $("#tipMessage").html("Please input legal number!");
        $('#tipDialog').css("display", 'block');
        $('#mask').css("display", 'block');
        pageInputObj.val('');
        return;
    }
    if (curPage <= 0) {
        curPage = 1;
    }
    if (curPage > totalPage) {
        curPage = totalPage;
    }
    window.location.href = urlOne + sortArray + urlTwo + columnArray + urlThree + curPage + urlFour;
}

function checkBoxFun() {
    var checkObjs = document.getElementsByClassName('checkbox');
    var checkFlagObj = document.getElementById('checkFlag');
    for (var i=0; i<checkObjs.length; i++) {
        checkObjs[i].onclick = function() {
          for (var j=0; j<checkObjs.length; j++) {
              if(checkObjs[j].checked == false) {
                  checkFlagObj.checked = false;
                  break;
              }
          }
        }
    }
}

function hideTipDialog() {
    $("#mask").css("display", "none");
    $("#tipDialog").css("display", "none");
}

function deleteAll(url) {
    var checkObjs = document.getElementsByClassName("checkbox");
    var deleteItems = [];
    for (var i=0; i<checkObjs.length; i++) {
        if (checkObjs[i].checked == true) {
            deleteItems.push(checkObjs[i].value);
        }
    }
    window.location.href = url + deleteItems;
}

function searchForExam() {
    var searchText = document.getElementById('searchText').value;
    searchText = encodeURIComponent(searchText);
    var dateFrom = document.getElementById('dateFrom').value;
    dateFrom = encodeURIComponent(dateFrom);
    var dateTo = document.getElementById('dateTo').value;
    dateTo = encodeURIComponent(dateTo);
    window.location.href = urlFive + searchText + urlSix + dateFrom + urlSeven + dateTo;
}

function autoClick(event, totalPage) {
    if (event.keyCode == 13) {
        if (event.keyCode == 13) {
        	if ($('#goPageNumber').is(':focus')) {
        		go(totalPage);
        	}
        	if ($('#searchText').is(':focus')) {
        		 searchForExam();
        	}
        }
    }
}

$(function() {
     var orderObj = document.getElementById('order');
     var nameOrderObj = document.getElementById('nameOrder');
     var timeOrderObj = document.getElementById('timeOrder');
     orderObj.onclick = function() {
         if ('id' == columnArray[0]) {
             if (sortArray[0] == 'ASC') {
                 sortArray[0] = 'DESC';
             } else {
                 sortArray[0] = 'ASC';
             }
         } else if ('id' == columnArray[1]) {
             columnArray[1] = columnArray[0];
             columnArray[0] = 'id';
             if (sortArray[1] == 'ASC') {
                 sortArray[1] = 'DESC';
             } else {
                 sortArray[1] = 'ASC';
             }
             var tmp = sortArray[0];
             sortArray[0] = sortArray[1];
             sortArray[1] = tmp;
         } else {
             columnArray.pop();
             columnArray.unshift('id');
             
             if (sortArray[2] == 'ASC') {
                 sortArray.pop();
                 sortArray.unshift('DESC');
             } else {
                 sortArray.pop();
                 sortArray.unshift('ASC');
             }
         }
         window.location.href = urlOne + sortArray + urlTwo + columnArray + urlEight;
     }

     nameOrderObj.onclick = function() {
         if ('exam_name' == columnArray[0]) {
             if (sortArray[0] == 'ASC') {
                 sortArray[0] = 'DESC';
             } else {
                 sortArray[0] = 'ASC';
             }
         } else if ('exam_name' == columnArray[1]) {
             columnArray[1] = columnArray[0];
             columnArray[0] = 'exam_name';
             if (sortArray[1] == 'ASC') {
                 sortArray[1] = 'DESC';
             } else {
                 sortArray[1] = 'ASC';
             }
             var tmp = sortArray[0];
             sortArray[0] = sortArray[1];
             sortArray[1] = tmp;
         } else {
             columnArray.pop();
             columnArray.unshift('exam_name');
             
             if (sortArray[2] == 'ASC') {
                 sortArray.pop();
                 sortArray.unshift('DESC');
             } else {
                 sortArray.pop();
                 sortArray.unshift('ASC');
             }
         }
         window.location.href = urlOne + sortArray + urlTwo + columnArray + urlEight;
     }

     timeOrderObj.onclick = function() {
         if ('effective_time' == columnArray[0]) {
             if (sortArray[0] == 'ASC') {
                 sortArray[0] = 'DESC';
             } else {
                 sortArray[0] = 'ASC';
             }
         } else if ('effective_time' == columnArray[1]) {
             columnArray[1] = columnArray[0];
             columnArray[0] = 'effective_time';
             if (sortArray[1] == 'ASC') {
                 sortArray[1] = 'DESC';
             } else {
                 sortArray[1] = 'ASC';
             }
             var tmp = sortArray[0];
             sortArray[0] = sortArray[1];
             sortArray[1] = tmp;
         } else {
             columnArray.pop();
                columnArray.unshift('effective_time');
                
                if (sortArray[2] == 'ASC') {
                    sortArray.pop();
                    sortArray.unshift('DESC');
                } else {
                    sortArray.pop();
                    sortArray.unshift('ASC');
                }
                
         }
         window.location.href = urlOne + sortArray + urlTwo + columnArray + urlEight;
     }
})

function selectChange(obj) {
    window.location.href = urlOne + sortArray + urlTwo + columnArray + urlNine + obj.value + urlTen;
}