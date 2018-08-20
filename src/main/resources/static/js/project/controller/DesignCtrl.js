app.controller('DesignCtrl', DesignCtrl);
function DesignCtrl ($http,$window,$cookies) {
  var me = this; 
  me._contextPath = _contextPath+"cabinet/container/";
  me.type='material';
  me.backgroundList = backItemsMaterial ;
  me.changeType =function(type){
	  me.type=type;
  }
  
  me.init = { mainColor:"rgb(87, 168, 203)" , backgroundColor:"rgb(202, 155, 155)" };
  me.mainColor = me.init.mainColor;
  me.backgroundColor = me.init.backgroundColor;
  
  
  me.rowClassBack = function(div,background){
		var str = background.name;
		if(background.active===true){
			str+=' selected';
		}
		return str;
	};
  
  
  
$(document).ready(function() {
	  //spectrum
	  $("#custom1").spectrum(
			  { 
				  color: me.backgroundColor,
				  chooseText: "Выбрать",
				  cancelText: "Отмена" ,
				  replacerClassName: 'color-input-swatch',
		            preferredFormat: "hex",
		            clickoutFiresChange: true,
		            move: function move(color) {
		            	me.mainColor = color.toHexString();
		            	setColorBackground();
		            },
		            change: function change(color) {
		            	me.mainColor = color.toHexString();
		            	setColorBackground();
		            }
					  
			  });
	  $("#custom2").spectrum(
			  { 
				  color: me.mainColor,
				  chooseText: "Выбрать",
				  cancelText: "Отмена" ,
				  replacerClassName: 'color-input-swatch',
		            preferredFormat: "hex",
		            clickoutFiresChange: true,
		            move: function move(color) {
		            	me.backgroundColor = color.toHexString();
		            	setColorBackground();
		            },
		            change: function change(color) {
		            	me.backgroundColor = color.toHexString();
		            	setColorBackground();
		            }
					  
			  });
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
	  setColorBackground();
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
	  		var svg = value.body.replace('fill="#000"', 'fill="' +me.mainColor  + '" fill-opacity="' + 1 + '"').replace(/\"/g, '\'').replace(/\</g, '%3C').replace(/\>/g, '%3E').replace(/\&/g, '%26').replace(/\#/g, '%23');
	   		var url = 'url("data:image/svg+xml,' + svg + '")';
	   		$(document.getElementById(id)).css("backgroundImage",url);
	   		$(document.getElementById(id)).css("backgroundColor",me.backgroundColor);
  		});
  		  		
  	};
  
  function slicker(slickerVal){
	  slickerVal=slickerVal<3?3:slickerVal>8?8:slickerVal;
	  $('.slicker-class').slick('slickSetOption', {slidesToShow:slickerVal,slidesToScroll:slickerVal});
  }
});
  
};