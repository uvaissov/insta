app.controller('PrimaryCtrl', PrimaryCtrl);

function PrimaryCtrl ($http) {
	var $uploadCrop;
	var primary = this;
	var pointUrl = _contextPath+'cabinet/data/primary';
	
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
	primary.changePhoto = function(){
		$uploadCrop.croppie('result', {
			type: 'canvas',
			size: 'viewport',
			format: 'png',
			circle: false
		}).then(function (resp) {
		    $http.post(_contextPath+'content',resp).then(
				       function(response){
				    	   alert('Info updated');
				       }, 
				       function(response){
				    	   console.log(response);
				    	   primary.message = response.message;
				       }
				    );
		});
	};
	primary.rowClassBack = function(background){
		var str = background.name;
		if(background.active===true){
			str+=' selected';
		}
		return str;
	};
	primary.selectBackground=function(item){
		angular.forEach(primary.backgroundList, function(value) {
			if(value.active===true){
				value.active=false;
			}
		});
		item.active=true;
	};
	primary.get = function(){
		$http.get(pointUrl, null)
		.then(
				function(response){
					primary.info = response.data;
				}, 
				function(response){
					console.log(response);
					primary.message = response.message;
				}); 
	}
  

  
  // load info
  primary.get();
  
  primary.tooglePhoto = function(){
	  $("#modal-content,#modal-background").toggleClass("active");
  }	
  
  $(document).ready(function() {
	  //slicker
	  $(".slicker-class").slick({
		  infinite : true,
		  slidesToShow : 3,
		  speed : 400,
		  slidesToScroll : 3
	  });
	  angular.forEach(primary.backgroundList, function(value, index) {
		  if (value.active === true) {
			  $('.slicker-class').slick('slickGoTo', index);
		  }
	  });
	  
  	//croppie
  	$(document.body).on("change", "#userImage", function() {
	  	$("#preloader").show();
		readFile(this); 
	});
  
  
  	function readFile(input) {
		if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
            	$("#preloader").hide();
            	primary.tooglePhoto();
            	$uploadCrop.croppie('bind', {
            		url: e.target.result
            	}).then(function(){ 
            		$uploadCrop.croppie('setZoom', 0.7);
				});
            }
            reader.readAsDataURL(input.files[0]);
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
	
	
  });
  
};


