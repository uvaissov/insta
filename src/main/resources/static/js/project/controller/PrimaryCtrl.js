app.controller('PrimaryCtrl', PrimaryCtrl);

function PrimaryCtrl ($http,$window,$cookies) {
	var $uploadCrop;
	var primary = this;
	var pointUrl = _contextPath+'cabinet/data/primary';
	
	primary.backgroundList = backItemsCasual ;
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
		    $http.post(_contextPath+'content/upload/logo',resp).then(
				       function(response){
				    	   $('#userLogo').html(response.data);
				    	   primary.tooglePhoto('finish');
				       }, 
				       function(response){
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
	primary.changeBackGround = function(){
		 $('#userBackground').click();
		 e.preventDefault();
	}
	primary.get = function(){
		$http.get(pointUrl, null)
		.then(
				function(response){
					primary.info = response.data;
					var textarea1 = document.getElementById('inputDescription');
					setTimeout(function(){
						textarea1.style.cssText = 'height:auto; padding:0';
					      // for box-sizing other than "content-box" use:
					      // el.style.cssText = '-moz-box-sizing:content-box';
						textarea1.style.cssText = 'height:' + textarea1.scrollHeight + 'px';
					    },0);
				}, 
				function(response){
					console.log(response);
					primary.message = response.message;
				}); 
	}
  

  
  // load info
  primary.get();
  
  primary.tooglePhoto = function(action){
	  if('cancel'===action || 'finish'=== action ){
		  $('#userImage').val('');
	  }
	  $("#modal-content,#modal-background").toggleClass("active");
  }
  
  
  $(document).ready(function() {
	  
	  //slicker
	  var slickerVal = Math.floor($window.innerWidth/180);
	  slickerVal=slickerVal<3?3:slickerVal>8?8:slickerVal;
	  $(".slicker-class").show();
	  $(".slicker-class").slick({
		  infinite : true,
		  slidesToShow : slickerVal,
		  speed : 400,
		  slidesToScroll : slickerVal
	  });
	  angular.element($window).bind('resize', function(){
	      slicker(Math.floor($window.innerWidth/180));
	    });
	  angular.forEach(primary.backgroundList, function(value, index) {
		  if (value.active === true) {
			  $('.slicker-class').slick('slickGoTo', index);
		  }
	  });
	  
  	//croppie
  	$(document.body).on("change", "#userImage", function() {
		readFile(this); 
	});
  //croppie
  	$(document.body).on("change", "#userBackgroundImage", function() {
		var input = this;
		
		if (input.files && input.files[0]) {
			
			if(input.files[0].size < (1048576*4)) {
			
				var reader = new FileReader();
				reader.onload = function (e) {
					$http.post(_contextPath+'content/upload/background',e.target.result).then(
						       function(response){
						    	   $('#userBackground').html(response.data);
						       }, 
						       function(response){
						    	   primary.message = response.message;
						       }
						    );
				}
				reader.readAsDataURL(input.files[0]);
			} else {
				alert(" bites\nToo big!");
			}
      }
		
		 
	});
	$uploadCrop = $('#upload-demo').croppie({
		viewport: {
			width: 200,
			height: 200,
			type: 'circle'
		},
	    boundary: {
	        width: 300,
	        height: 300
	    },
	    enableExif: true
	});
	
  });
  
  function slicker(slickerVal){
	  slickerVal=slickerVal<3?3:slickerVal>8?8:slickerVal;
	  $('.slicker-class').slick('slickSetOption', {slidesToShow:slickerVal,slidesToScroll:slickerVal});
  }
  function readFile(input) {
		if (input.files && input.files[0]) {
			$("#preloader").show();
			var reader = new FileReader();
			reader.onload = function (e) {
				$("#preloader").hide();
				primary.tooglePhoto('use');
				$uploadCrop.croppie('bind', {
					url: e.target.result
				}).then(function(){
					$uploadCrop.croppie('setZoom', 0.7);
				});
          }
          reader.readAsDataURL(input.files[0]);
      }
	}
  
  var textarea = document.getElementById('inputDescription');
  textarea.addEventListener('keydown', autosize);
  function autosize(){
    var el = this;
    setTimeout(function(){
      el.style.cssText = 'height:auto; padding:0';
      // for box-sizing other than "content-box" use:
      // el.style.cssText = '-moz-box-sizing:content-box';
      el.style.cssText = 'height:' + el.scrollHeight + 'px';
    },0);
  }
  
};


