app.controller('DesignCtrl', DesignCtrl);
function DesignCtrl ($http,$window,$cookies) {
  var me = this; 
  me._contextPath = _contextPath+"cabinet/container/";
  me.type='material';
  me.backgroundList = backItemsMaterial ;
  me.changeType =function(type){
	  me.type=type;
  }
  
  
  me.rowClassBack = function(div,background){
		var str = background.name;
		if(background.active===true){
			str+=' selected';
		}
		return str;
	};
  
  
  
$(document).ready(function() {
	  //spectrum
	  $("#custom1").spectrum({ color: "rgb(87, 168, 203)",showAlpha: true,chooseText: "Выбрать",cancelText: "Отмена" ,replacerClassName: 'color-input-swatch'});
	  $("#custom2").spectrum({ color: "rgb(202, 155, 155)",showAlpha: true,chooseText: "Выбрать",cancelText: "Отмена", replacerClassName: 'color-input-swatch'});
	  
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
	  angular.forEach(me.backgroundList, function(value, index) {
		  if (value.active === true) {
			  $('.slicker-class').slick('slickGoTo', index);
		  }
	  });
	  
  	function setColorBackground(){
  		
  		$.each(me.backgroundList, function( index, value ) {
  		var id = value.name+'_img';
  		var svg = value.body.replace('fill="#000"', 'fill="' + '#000' + '" fill-opacity="' + 1 + '"').replace(/\"/g, '\'').replace(/\</g, '%3C').replace(/\>/g, '%3E').replace(/\&/g, '%26').replace(/\#/g, '%23');
   		var url = 'url("data:image/svg+xml,' + svg + '")';
   		$('#'+id).css("backgroundImage",url);
   		console.log(id);
   		console.log(url);
   		console.log($('#'+id));
   		
  		});
  	};
  	setTimeout(() => {
  		setColorBackground();
	}, 1000);
  	
  
  function slicker(slickerVal){
	  slickerVal=slickerVal<3?3:slickerVal>8?8:slickerVal;
	  $('.slicker-class').slick('slickSetOption', {slidesToShow:slickerVal,slidesToScroll:slickerVal});
  }
});
  
};