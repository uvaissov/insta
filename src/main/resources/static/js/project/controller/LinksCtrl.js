app.controller('LinksCtrl', LinksCtrl);

function LinksCtrl($http) {
	var me = this;
	var pointUrl = _contextPath + 'cabinet/data/links';
	me.addButtons = addButtons;
	me.message = null;
//сохранение данных
me.save = function(model) { 
	if (angular.isDefined(model)) {
		if(!angular.isDefined(model.title)){
			alert('Please insert title!'|| model.title.trim()==='');
			return;
		}
		if(!angular.isDefined(model.value) || model.value.trim()===''){
			alert('Please insert title!');
			return;
		}
		$http.post(pointUrl, model).then(function(response) {
			me.get(); 
			$('#editLinkModal').modal('hide');
		}, function(response) {
			me.message = response.message;
		});
	}
};
//редактирование
me.editLink=function(model){
	me.data=model;
	$('#editLinkModal').modal('show');
}
//удаление
me.deleteLink = function(model){
	me.confirm = {title:'Удаление',text:'Вы действительно хотите удалить ссылку '+model.urlName+'?'};
	//генерируем функцию для для дейсвия удаление
	me.confirm.action=function(){
		$http.delete(pointUrl+'/'+model.id).then(function(response) {
			$('#confirmModalSm').modal('hide');
			me.get(); 
		}, function(response) {
			me.message = response.message;
		});
	}
	$('#confirmModalSm').modal('show');
}
//выбор сервиса
me.selectLink = function(el,button){ 
	//готовим данные для записи в сервер	
	me.data = {
				id : -1,
				urlName : button.name,
				title : button.name,
				urlIcon : button.iconName,
				textPrefix : button.textPrefix,
				dataType : button.dataType,
				urlId : button.id
				};
	
	angular.forEach(me.addButtons, function(value, key) {
		//убераем отметки если они были ранее поставлены
		  value.selected=undefined;
		});
	//ставим отметку что он выбрал сервис
	button.selected=true;
};

//получение данных с сервера
me.get = function() { 
	$http.get(pointUrl, null).then(function(response) {
		me.urls = response.data;
		setTimeout(() => {
			//запустим перетаскивание после перерисовки элементов
			me.initDragAndDrop();
		}, 50);
	}, function(response) {
		me.message = response.message;
	});
};

me.changePosition = function(from,to){
	$http.post(pointUrl+'/position', {from:from,to:to}).then(function(response) {
		me.get(); 
	}, function(response) {
		me.message = response.message;
	});
}

// load info
// в конце логики получим данные
me.get(); 

me.initDragAndDrop = function(){
	//Drag and Drop
	var cols = document.querySelectorAll('#columns .column');
	[].forEach.call(cols, function(col) {
		col.addEventListener('dragstart', handleDragStart, false);
		col.addEventListener('dragenter', handleDragEnter, false)
		col.addEventListener('dragover', handleDragOver, false);
		col.addEventListener('dragleave', handleDragLeave, false);
		col.addEventListener('drop', handleDrop, false);
		col.addEventListener('dragend', handleDragEnd, false);
	});
	var dragSrcEl = null;
	function handleDragStart(e) {
		if (e.target.className.indexOf('column') > -1) {
			dragSrcEl = e.target;
			dragSrcEl.style.opacity = '0.4';
			var dt = e.dataTransfer;
			dt.effectAllowed = 'move';
			dt.setData('text/html', dragSrcEl.innerHTML);
		}
	}
	function handleDragOver(e) {
		if (dragSrcEl) {
			e.preventDefault();
			e.dataTransfer.dropEffect = 'move';
		}
	}
	function handleDragEnter(e) {
		if (dragSrcEl) {
			e.target.classList.add('over');
		}
	}
	function handleDragLeave(e) {
		if (dragSrcEl) {
			e.target.classList.remove('over');
		}
	}
	function handleDragEnd(e) {
		dragSrcEl = null;
		[].forEach.call(cols, function(col) {
			col.style.opacity = '';
			col.classList.remove('over');
		});
		turnOff();
	}
	function handleDrop(e) {
		if (dragSrcEl) {
			e.stopPropagation();
			e.stopImmediatePropagation();
			e.preventDefault();
			var dataTransfer = e.dataTransfer;
			var i = 0;
			var element = e.target
			while (!element.getAttribute("draggable") && i < 3) {
				element = element.parentElement;
				i++;
			}
			if (!element.getAttribute("draggable"))
				return false;
			if (dragSrcEl != this) {
				dragSrcEl.innerHTML = element.innerHTML;
				this.innerHTML = dataTransfer.getData('text/html');
				me.changePosition($(dragSrcEl).attr('url-position'),$(this).attr('url-position'))
			}
		}
	}
	function turnOn() {
		$(".column").attr('draggable', 'true');
	};
	function turnOff() {
		$(".column").removeAttr('draggable');
		$(".drag-icon").on('mouseenter', function() {
			turnOn();
		});
		$(".drag-icon").on('touchstart', function() {
			turnOn();
		});
		$(".drag-icon").on('mouseleave', function() {
				turnOff();
			});
		};
		
		turnOff();
	};

	$(document).ready(function() {
		
	});

};

;