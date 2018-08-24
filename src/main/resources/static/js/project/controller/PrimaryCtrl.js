app.controller('PrimaryCtrl', PrimaryCtrl);

function PrimaryCtrl ($http,$window,$cookies) {
	var cropper;
	var primary = this;
	var pointUrl = _contextPath+'cabinet/data/primary';
	
	var input = document.getElementById('userImage');
	var image = document.getElementById('image');
	var $modal = $('#centralModal');
	
	primary.message = null;
	primary.saveInfo = function(info){
		if(	angular.isDefined(info) &&
	  		angular.isDefined(info.username)){
			$http.post(pointUrl, info)
			.then(
				       function(response){
				    	   alert('Info updated');
				       }, 
				       function(response){
				    	   console.log(response);
				    	   primary.message = response.message;
				       }
				    );
	  	}
	};
	
	primary.changePhoto = function(){
        var canvas;
        $modal.modal('hide');
        if (cropper) {
          canvas = cropper.getCroppedCanvas({
            width: 160,
            height: 160,
          });
          canvas.toBlob(function (blob) {
            $http.post(_contextPath+'content/upload/logo',canvas.toDataURL()).then(
				       function(response){
				    	   $('#userLogo').html(response.data);
				       }, 
				       function(response){
				    	   primary.message = response.message;
				       }
				    );
          });
        }
	};
	primary.rotate = function(grad){
		if (cropper) {
			cropper.rotate(grad);
		}
	}
	
	primary.get = function(){
		$http.get(pointUrl, null)
		.then(
				function(response){
					primary.info = response.data;
					var textarea1 = document.getElementById('inputDescription');
					setTimeout(function(){
						textarea1.style.cssText = 'height:auto; padding:0';
					 	textarea1.style.cssText = 'height:' + textarea1.scrollHeight + 'px';
					    },0);
				}, 
				function(response){
					primary.message = response.message;
				}); 
	}
  

  
  // load info
  primary.get();
 
  
  $(document).ready(function() {
	  input.addEventListener('change', function (e) {
	        var files = e.target.files;
	        var done = function (url) {
	          input.value = '';
	          image.src = url;
	          //$alert.hide();
	          $modal.modal('show');
	        };
	        var reader;
	        var file;
	        var url;

	        if (files && files.length > 0) {
	          file = files[0];

	          if (URL) {
	            done(URL.createObjectURL(file));
	          } else if (FileReader) {
	            reader = new FileReader();
	            reader.onload = function (e) {
	              done(reader.result);
	            };
	            reader.readAsDataURL(file);
	          }
	        }
	      });
	  
	  $modal.on('shown.bs.modal', function () {
	        cropper = new Cropper(image, {
	          aspectRatio: 1,
	          viewMode: 1,
	          restore: false,
	          guides: false,
	          center: false,
	          highlight: false,
	          //autoCropArea: 0.65,
	          zoom:0.65,
	          cropBoxMovable: false,
	          cropBoxResizable: false,
	          toggleDragModeOnDblclick: false,
	          
	          dragMode: 'move'
	        });
	      }).on('hidden.bs.modal', function () {
	        cropper.destroy();
	        cropper = null;
	      });
	
  });
  
  

  var textarea = document.getElementById('inputDescription');
  textarea.addEventListener('keydown', autosize);
  function autosize(){
    var el = this;
    setTimeout(function(){
      el.style.cssText = 'height:auto; padding:0';
      el.style.cssText = 'height:' + el.scrollHeight + 'px';
    },0);
  }
  
};


