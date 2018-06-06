app.controller('PrimaryCtrl', PrimaryCtrl);
var linkCtrlPrimary;
function PrimaryCtrl ($http) {
  var primary = this;
  linkCtrlPrimary = primary;
  var pointUrl = _contextPath+'cabinet/data/primary';
  primary.switch ="setting";
  primary.backgroundList = backItems ;
  primary.message = null;
  primary.saveInfo = function(info){
		if(	angular.isDefined(info) &&
	  		angular.isDefined(info.username)){
			angular.forEach(primary.backgroundList, function(value) {
				  if(value.active===true){
					  info.background = value.name;
				  }
			});
			
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
  primary.setPhoto = function(){
	  primary.switch="photo";
  };
  primary.rowClassBack = function(background){
	  var str = background.name;
	  if(background.active===true){
		 str+=' selected';
	  }
	  return str;
  }
  primary.selectBackground=function(item){
	  angular.forEach(primary.backgroundList, function(value) {
		  if(value.active===true){
			  value.active=false;
		  }
		});
	  item.active=true;
  }
  primary.get = function(){
	  $http.get(pointUrl, null)
		.then(
			       function(response){
			    	   primary.info = response.data;
			       }, 
			       function(response){
			    	   console.log(response);
			    	   primary.message = response.message;
			       }
			    ); 
  }
  
  MyPhoto.init();
  primary.bindCroppie = function(file){
//	  console.log('txtUploadFile');
//	  var ctrl = angular.element("#txtUploadFile");
//	   ctrl.on('change', primary.fileNameChanged);
//	   ctrl.click();
	  //primary.setPhoto();
	  console.log('MyPhoto.init()');
  };
  
  primary.fileNameChanged = function (input) {
	  var reader = new FileReader();
	  console.log(input);
	  if (input.target.files && input.target.files[0]) {
		  
		  reader.onload = function (e) {
			  primary.setPhoto();
			  if(!croppie) {
				  croppie = $('#main-cropper').croppie({
				   	    viewport: {
				        width: 200,
				        height: 200,
				        type: 'circle'
				    },
				    boundary: {
				        width: 300,
				        height: 300
				    }
				});
			  }
			  croppie.bind({
			    url: e.target.result
			});
		  }
		  reader.readAsDataURL(input.target.files[0]);
	  }
	  
	}
  
  // load info
  primary.get();
  
  primary.tooglePhoto = function(){
	  $("#modal-content,#modal-background").toggleClass("active");
  }	
  
  $(document).ready(function() {
	  $(".slicker-class").slick({
		infinite : true,
		slidesToShow : 3,
		speed : 400,
		slidesToScroll : 3
	  });
	  
	  $(document.body).on("change", "#userImage", function() {
		  	$("#preloader").show();
			readFile(this); 
			
			
		});
	  
	  var $uploadCrop;
	  function readFile(input) {
			if (input.files && input.files[0]) {
	            var reader = new FileReader();
	            reader.onload = function (e) {
	            	$("#preloader").hide();
	            	primary.tooglePhoto();
	            	$uploadCrop.croppie('bind', {
	            		url: e.target.result
	            	}).then(function(){ 
					  //$uploadCrop.croppie('setZoom', 0.7);
					});
	            }
	            
	            reader.readAsDataURL(input.files[0]);
	        }
	        else {
		        //alert("Sorry - you're browser doesn't support the FileReader API");
		    }
		}
	  	
		$uploadCrop = $('#upload-demo').croppie({
			enableExif: true,
			viewport: {
				width: 200,
				height: 200,
				type: 'circle'
			},
		    boundary: {
		        width: 300,
		        height: 300
		    }			
		});
	  angular.forEach(primary.backgroundList, function(value, index) {
		if (value.active === true) {
			$('.slicker-class').slick('slickGoTo', index);
		}
	  });
	
  });
  
};


var MyPhoto = (function() {

	function Upload() {
		$('.upload-result').on('click', function (ev) {
			$uploadCrop.croppie('result', {
				type: 'canvas',
				size: 'viewport',
				format: 'png',
				circle: false
			}).then(function (resp) {
				$.post(
					"/settings/ajax.php?type=user_img", {
					    img: resp
					},
					onAjaxSuccess
				);
				function onAjaxSuccess(data) {
					$("#targetLayer").html(data);
					$(".top .profile").html(data+"<p>Profile Settings</p>");
					$(".user_img2 span").html(data);
					$("#sign_photo").html(data);
					$(".loader").fadeOut(200);
					$(".crop_bg").removeClass("crop_bg_show");

					$("#sign_photo img").css({
						"width": "45px",
						"height": "45px",
						"border-radius": "45px",
						"float": "left",
						"margin-right": "16px",
						"margin-top": "-5px"
					});

				}
			});
		});
	}

	function init() {
		Upload();
	}

	return {
		init: init
	};
})();