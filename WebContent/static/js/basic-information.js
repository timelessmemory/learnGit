function hideTip() {
     setTimeout(function() {
       $("#flashMessageDiv").hide();
     },3000);
}

function showPhotoDialog() {
    var dialogObj = $("#dialog");
    dialogObj.show();
}
      
  function closeDialog() {
      var dialogObj = $("#dialog");
      dialogObj.hide();
  }
  
  function change() {
      var file = document.getElementById("imgFile");
      var fileSize;
      if (file) {
    	  fileSize = document.getElementById("imgFile").files[0].size;
      }
      if (fileSize > 10000000) {
          document.getElementById('tipMessage').innerHTML = 'The file size should be less than 10000000B!';
          document.getElementById('tipDialog').style.display = 'block';
          document.getElementById('mask').style.display = 'block';
          return;
      }

      var ext = file.value.substring(file.value.lastIndexOf(".") + 1).toLowerCase();
      if(ext != 'png' && ext != 'jpg' && ext !=' jpeg'){
          document.getElementById('tipMessage').innerHTML = 'Please choose picture like jpg, png, jpeg!';
          document.getElementById('tipDialog').style.display = 'block';
          document.getElementById('mask').style.display = 'block';
          return;
      }
      html5Reader(file);
  }

  function html5Reader(file) {
      var file = file.files[0];
      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function(e) {
         var pic = document.getElementById("preview");
         pic.src=this.result;
      }
  }
  
  function upload() {
      var file = document.getElementById("imgFile");
      if (file.value != '') {
          var form = document.getElementById("formUpload");
          form.submit();
      }
      return false;
  }