app.controller('SettingCtrl', SettingCtrl);

function SettingCtrl() {
  var set = this; 
  var settingPath = "/setting/";
  
  set.tabList = tabsItems ;
  set.pageUrl = null;
  angular.forEach(set.tabList, function(value) {
	  if(value.active===true){
		  set.pageUrl = settingPath+value.itemPage
	  }
  });
  set.changeContent=function(item){
	  angular.forEach(set.tabList, function(value) {
		  if(value.active===true){
			  value.active=false;
		  }
		});
	  item.active=true;
	  set.pageUrl = settingPath+item.itemPage
  }
};