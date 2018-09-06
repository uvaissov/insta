app.controller('LinksCtrl', LinksCtrl);

function LinksCtrl($http) {
	var me = this;
	var pointUrl = _contextPath + 'cabinet/data/links';
	me.addButtons = addButtons;
	me.message = null;
	me.save = function(model) {
		if (angular.isDefined(model))
			$http.post(pointUrl, model).then(function(response) {
				alert('Info updated');
			}, function(response) {
				me.message = response.message;
			});

	};
	me.selectLink = function(el,button){
		console.log(el,button);
	};

	me.get = function() {
		$http.get(pointUrl, null).then(function(response) {
			me.urls = response.data;
		}, function(response) {
			me.message = response.message;
		});
	};

	// load info
	me.get();
	$(document).ready(function() {
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
				}
			}
		}
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
		}
		function turnOn() {
			$(".column").attr('draggable', 'true');
		}
		turnOff();
		
		//

	});

};

;