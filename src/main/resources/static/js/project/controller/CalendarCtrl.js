app.controller('CalendarCtrl', CalendarCtrl);

function CalendarCtrl (moment) {
  var vm = this; 
  vm.viewDate = new Date();
  vm.calendarTitle='asdasd';
  vm.dayViewTimePositionOffset = 0;
  var left = function(dayEvent){
     	return dayEvent.left/10*2;
	 // return dayEvent.left;
  };
  vm.events = [
    {
      title: 'My event title', 
      type: 'info',
      startsAt: moment().hours(5).minutes(0).toDate(), // BREAKING CHANGE
      endsAt: moment().hours(6).minutes(0).toDate(), // BREAKING CHANGE
      //actions:actions,
      draggable: true,
      color: { // can also be calendarConfig.colorTypes.warning for shortcuts to the deprecated event types
          primary: '#e3bc08', // the primary event color (should be darker than secondary)
          secondary: '#fdf1ba' // the secondary event color (should be lighter than primary)
        },
      left : left
    },{
        title: 'My event title', 
        type: 'info',
        startsAt: moment().hours(5).minutes(0).toDate(), // BREAKING CHANGE
        endsAt: moment().hours(6).minutes(0).toDate(), // BREAKING CHANGE
        //actions:actions,
        draggable: true,
        color: { // can also be calendarConfig.colorTypes.warning for shortcuts to the deprecated event types
            primary: '#e3bc08', // the primary event color (should be darker than secondary)
            secondary: '#fdf1ba' // the secondary event color (should be lighter than primary)
          },
          left : left
      },
    {
        title: 'My event title1', 
        type: 'info',
        startsAt: moment().hours(5).minutes(0).toDate(), // BREAKING CHANGE
        endsAt: moment().hours(6).minutes(30).toDate(), // BREAKING CHANGE
        //actions:actions,
        draggable: true,
        left : left,
        color: { // can also be calendarConfig.colorTypes.warning for shortcuts to the deprecated event types
            primary: '#e3bc08', // the primary event color (should be darker than secondary)
            secondary: '#fdf1ba' // the secondary event color (should be lighter than primary)
          }
      }
  ];
  
  
  vm.eventEdited = function(calendarEvent){
	  console.log('eventEdited');
	  console.log(calendarEvent);
  }
  vm.eventDeleted = function(calendarEvent){
	  console.log('eventDeleted');
	  console.log(calendarEvent);
  }
  vm.eventClicked = function(calendarEvent){
	  console.log('eventClicked');
	  console.log(calendarEvent);
  }
  vm.eventTimesChanged = function(calendarEvent){
	  console.log('eventTimesChanged');
	  console.log(calendarEvent);
  };
  vm.cellModifier = function(cell) {
      cell.cssClass = 'calendar_row_style';
  }
  vm.timespanClicked = function(date) {
	  console.log('timespanClicked');
	  console.log(date);
  };
};