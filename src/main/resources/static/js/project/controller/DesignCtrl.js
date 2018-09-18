app.controller('DesignCtrl', DesignCtrl);
function DesignCtrl($http, $window, $cookies) {
	var me = this;
	var pointUrl = _contextPath+'cabinet/data/design';
	me._contextPath = _contextPath + "cabinet/container/";
	me.type = backgroundData.type;
	me.backgroundList = backItemsMaterial;
	me.backgroundListC = backItemsCasual;
	me.changeType = function(type) {
		me.type = type;
		me.slickerClass = me.type == 'material' ? '.slicker-material'
				: '.slicker-casual'
		me.slickGoTo();
		if (me.type == 'material')
			me.setColorBackground();
	}

	me.init = {
		mainColor : backgroundData.element_color,
		backgroundColor : backgroundData.background_color
	};
	me.mainColor = me.init.mainColor;
	me.backgroundColor = me.init.backgroundColor;

	me.saveInfo = function() {
		var data = {
			'type' : me.type,
			'element_color' : me.mainColor,
			'background_color':me.backgroundColor
		}
		angular.forEach(me.backgroundListC, function(value) {
			if (value.active === true) {
				data.background = value.name;
			}
		});
		angular.forEach(me.backgroundList, function(value) {
			if (value.active === true) {
				data.background_svg = value.name;
			}
		});

		$http.post(pointUrl, data).then(function(response) {
			 $.notify("Изменения сохранены", "success");
		}, function(response) {
			me.message = response.message;
			 $.notify(me.message, "error");
		});
	};

	me.rowClassBack = function(div, background) {
		var str = background.name;
		if (background.active === true) {
			str += ' selected';
		}
		return str;
	};

	me.selectBackground = function(item) {
		angular.forEach(me.type == 'material' ? me.backgroundList
				: me.backgroundListC, function(value) {
			if (value.active === true) {
				value.active = false;
			}
		});
		item.active = true;
		me.saveInfo();
	};

	me.setColorBackground = function() {
		$.each(me.backgroundList, function(index, value) {
			var id = value.name + '_img';
			var svg = value.body.replace('fill="#000"',
					'fill="' + me.mainColor + '" fill-opacity="' + 1 + '"')
					.replace(/\"/g, '\'').replace(/\</g, '%3C').replace(/\>/g,
							'%3E').replace(/\&/g, '%26').replace(/\#/g, '%23');
			var url = 'url("data:image/svg+xml,' + svg + '")';
			$(document.getElementById(id)).css("backgroundImage", url);
			$(document.getElementById(id)).css("backgroundColor",
					me.backgroundColor);
		});
	};

	$(document).ready(
			function() {
				//spectrum
				$("#custom1").spectrum({
					color : me.mainColor,
					chooseText : "Выбрать",
					cancelText : "Отмена",
					replacerClassName : 'color-input-swatch',
					preferredFormat : "hex",
					clickoutFiresChange : false,
					move : function move(color) {
						me.mainColor = color.toHexString();
						me.setColorBackground();
					},
					change : function change(color) {
						me.mainColor = color.toHexString();
						me.init.mainColor = color.toHexString();
						me.setColorBackground();
						me.saveInfo();
					},
					hide : function change(color) {
						if(me.mainColor != me.init.mainColor){
							me.mainColor = me.init.mainColor
						}
						me.setColorBackground();
					}

				});
				$("#custom2").spectrum({
					color : me.backgroundColor,
					chooseText : "Выбрать",
					cancelText : "Отмена",
					replacerClassName : 'color-input-swatch',
					preferredFormat : "hex",
					clickoutFiresChange : false,
					move : function move(color) {
						me.backgroundColor = color.toHexString();
						me.setColorBackground();
					},
					change : function change(color) {
						me.backgroundColor = color.toHexString();
						me.init.backgroundColor = color.toHexString();
						me.setColorBackground();
						me.saveInfo();
					},
					hide : function change(color) {
						if(me.backgroundColor != me.init.backgroundColor){
							me.backgroundColor = me.init.backgroundColor
						}
						me.setColorBackground();
					}

				});
				//slicker
				var slickerVal = Math.floor($window.innerWidth / 180);
				slickerVal = slickerVal < 3 ? 3 : slickerVal > 8 ? 8
						: slickerVal;
				$(".slicker-class").show();
				$(".slicker-class").slick({
					infinite : true,
					slidesToShow : slickerVal,
					speed : 400,
					slidesToScroll : slickerVal
				});
				angular.element($window).bind('resize', function() {
					me.slicker(Math.floor($window.innerWidth / 180));
				});
				me.slickGoTo = function() {
					var IsGoed = false;
					angular.forEach(me.type == 'material' ? me.backgroundList
							: me.backgroundListC, function(value, index) {
						if (value.active === true) {
							$(me.slickerClass).slick('slickGoTo', index);
							IsGoed = true;
						}
					});
					if (IsGoed === false) {
						$(me.slickerClass).slick('slickGoTo', 0);
					}
				};
				me.slicker = function(slickerVal) {
					slickerVal = slickerVal < 3 ? 3 : slickerVal > 8 ? 8
							: slickerVal;
					$(me.slickerClass).slick('slickSetOption', {
						slidesToShow : slickerVal,
						slidesToScroll : slickerVal
					});
				};

				me.changeType(me.type);

			});

};