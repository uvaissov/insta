app.controller('DesignCtrl', DesignCtrl);
function DesignCtrl () {
  var me = this; 
  me._contextPath = _contextPath+"cabinet/container/";
  me.type='material';
  me.changeType =function(type){
	  me.type=type;
  }
  
  
  $("#custom1").spectrum({ color: "rgb(87, 168, 203)",showAlpha: true,chooseText: "Выбрать",cancelText: "Отмена" ,replacerClassName: 'color-input-swatch'});
  $("#custom2").spectrum({ color: "rgb(202, 155, 155)",showAlpha: true,chooseText: "Выбрать",cancelText: "Отмена", replacerClassName: 'color-input-swatch'});
};