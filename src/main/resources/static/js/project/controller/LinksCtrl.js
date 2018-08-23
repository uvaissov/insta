app.controller('LinksCtrl', LinksCtrl);

function LinksCtrl ($http) {
  var me = this;
  var pointUrl = _contextPath+'cabinet/data/links';
  me.message = null;
  me.save = function(model){
		if(	angular.isDefined(model))
			$http.post(pointUrl, model)
			.then(
				       function(response){
				    	   alert('Info updated');
				       }, 
				       function(response){
				    	   me.message = response.message;
				       }
				    );
	  	
  };

  me.get = function(){
	  $http.get(pointUrl, null)
		.then(
			       function(response){
			    	   me.data = response.data;
			       }, 
			       function(response){
			    	   me.message = response.message;
			       }
			    ); 
  }
  
  // load info
  me.get();
	  $(document).ready(function() {
		  var cols = document.querySelectorAll('#columns .column');
		    [].forEach.call(cols, function (col) {
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
		            dt.setData('text', dragSrcEl.innerHTML);

		            // customize drag image for one of the panels
		            if (dt.setDragImage instanceof Function && e.target.innerHTML.indexOf('X') > -1) {
		                var img = new Image();
		                img.src = 'dragimage.jpg';
		                dt.setDragImage(img, img.width / 2, img.height / 2);
		            }
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
		        [].forEach.call(cols, function (col) {
		            col.style.opacity = '';
		            col.classList.remove('over');
		        });
		    }
		    function handleDrop(e) {
		        if (dragSrcEl) {
		            e.stopPropagation();
		            e.stopImmediatePropagation();
		            e.preventDefault();
		            if (dragSrcEl != this) {
		                dragSrcEl.innerHTML = e.target.innerHTML;
		                this.innerHTML = e.dataTransfer.getData('text');
		            }
		        }
		    }
	});
  
	  
  
	  
};



;