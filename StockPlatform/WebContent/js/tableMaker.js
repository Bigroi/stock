'use strict';
function makeTable(url, tableElement){
	
	var model;
	
	var tableData;
	
	var table;
	
	$.getJSON(url, function(answer){
		model = answer.data.model;
		
		tableData = answer.data.table;
		tableData.rowCallback = rowCallback;
		tableData.language = getLanguage();
		tableData.initComplete = initHeader;
		table = $(tableElement).DataTable(tableData);

		if ($(".add-button").length > 0){
			var editForm = window[model.editForm];
			editForm($(".add-button"), table, model);
		}
		
	});
	
	function rowCallback($row, data, index) {
		var edit = data[model.editColumn];
		
		if (model.statusColumn){
			addStatusSwitcher(data, $row);
		}
		
		if(model.editColumn){
			addEditRemoveIcons(data, $row);
		}
	}
	
	function addEditRemoveIcons(data, $row){
		var id = data[model.idColumn];
		var $editRemove = getCell(model.editColumn, $row);
        $editRemove.textContent = "";
        
        var $edit = $("<div class='no-edit'>");
        var $remove = $("<div class='no-remove'>");
        var $details = $("<div class='no-details'>");
        
        if(data[model.editColumn][0] == "Y"){ 
            $edit.removeClass("no-edit");
            $edit.addClass("edit");
            var editForm = window[model.editForm];
            if (editForm){
				editForm($edit, table, model, id);
            } else {
            	$edit.click(function(){document.location = model.editForm + "?id=" + id});
            }
        }
		if(data[model.editColumn][1] == "Y"){ 
            $remove.removeClass("no-remove");
            $remove.addClass("remove");
            $remove.click(function(event){
            	 $.getJSON(
            			 model.removeUrl, 
            			 {id:id}, 
            			 function(answer){
            				 table.row($(event.target).parents('tr')).remove().draw();
            				 console.log(answer)
            			 }
            	);
            });
        }
		if(data[model.editColumn][2] == "Y"){ 
			$details.removeClass("no-details");
			$details.addClass("details");
            var detailsForm = window[model.detailsUrl];
            if (detailsForm){
            	detailsForm($edit, table, model, id);
            } else {
            	$details.click(function(){document.location = model.detailsUrl + "?id=" + id});
            }
        }
		$editRemove.append($edit[0]);
		$editRemove.append($remove[0]);
		$editRemove.append($details[0]);
	}
	
	function getCell(columnName, $row){
		for (var i = 0; i < $row.children.length; i++){
			if (tableData.columns[i].data == columnName){
				return $row.children[i];
			}
		}
	}
	
	function addStatusSwitcher(data, $row){
		var id = data[model.idColumn];
		
		var $statusTd = getCell(model.statusColumn, $row);
        var $switcher = $("<div class='swtitch-row-off'>");
		if (data[model.statusColumn] == "ACTIVE"){
			$switcher.removeClass("swtitch-row-off");
			$switcher.addClass("swtitch-row-on");
		}
        $statusTd.textContent = "";
        $switcher.click(function (event){
        	var url = data[model.statusColumn] == "ACTIVE" ? 
        			model.deactivateUrl : 
        			model.activateUrl;
        	$.getJSON(url, {id:id}, function(answer){
        		if (answer.result > 0){	
        			table.rows().every( function () {
        			    var d = this.data();
        			    if (d[model.idColumn] == id){
        			    	d[model.statusColumn] == "ACTIVE" ?
	        					d[model.statusColumn] = "INACTIVE" :
	        					d[model.statusColumn] = "ACTIVE";
        			    	this.data(d);
        			    	return;
        			    }
        			} );
        			table.draw();
        		}
//        			event.target.classList.toggle("swtitch-row-off");
//                    event.target.classList.toggle("swtitch-row-on");
//        			table.
//                    var d = data;
//        			d[model.statusColumn] == "ACTIVE" ?
//        					data[model.statusColumn] = "INACTIVE" :
//        					data[model.statusColumn] = "ACTIVE";
        			
        	});
        });
        $statusTd.append($switcher[0]);
	}

	function getLanguage(){
		return {
		    decimal:		window.l10n["label.table.decimal"],
		    emptyTable:		window.l10n["label.table.emptyTable"],
		    info:			window.l10n["label.table.info"],
		    infoEmpty:		window.l10n["label.table.infoEmpty"],
		    infoFiltered:	window.l10n["label.table.infoFiltered"],
		    infoPostFix:	window.l10n["label.table.infoPostFix"],
		    thousands:		window.l10n["label.table.thousands"],
		    lengthMenu:		window.l10n["label.table.lengthMenu"],
		    loadingRecords:	window.l10n["label.table.loadingRecords"],
		    processing:		window.l10n["label.table.processing"],
		    search:			window.l10n["label.table.search"],
		    zeroRecords:	window.l10n["label.table.zeroRecords"],
		    paginate: {
		        first:		window.l10n["label.table.paginate_first"],
		        last:		window.l10n["label.table.paginate_last"],
		        next:		window.l10n["label.table.paginate_next"],
		        previous:	window.l10n["label.table.paginate_previous"]
		    },
		    aria: {
		        sortAscending:	window.l10n["label.table.aria_sortAscending"],
		        sortDescending:	window.l10n["label.table.aria_sortAscending"]
		    }
		}
	}
	
	
	function localizeHeader(thisObject){
		thisObject.api().columns().every(function() {
			var column = this;
			var $header = $(column.header());
			$header[0].textContent = window.l10n[$header[0].textContent];
		});
	}
	
	function addFilters(tableObject){
		tableElement.find('thead tr th').addClass('table-head-up');
		tableElement.find('thead tr').clone(true).appendTo( tableElement.find('thead') );
		tableElement.find('thead tr:eq(1) th').each( function (i) {
			var column = tableObject.api().column(i);
			var fieldName = column.dataSrc();
			console.log(fieldName);
			var $th =  $(this);
	        var title = $th.text();
	        $th.removeAttr('class');
	        $th.addClass('table-head-down');
			if (model.filterColumns[fieldName] == 'TEXT'){
				addTextFilter($th, column, title);
			} else if (model.filterColumns[fieldName] == 'SELECT'){
				addSelectFilter($th, column, title);
			} else {
				$th.html('');
			}
	    } );
		
		function addTextFilter($th, column, title){
			$th.html( '<input type="text" placeholder="Search '+title+'" />' );
	        $( 'input', this ).on( 'keyup change', function () {
	            if ( column.search() !== this.value ) {
	            	column.search( this.value ).draw();
	            }
	            return false;
	        } ).on('click', function(){return false;});
		}
		
		function addSelectFilter($th, column, title){
			$th.html( '<select><option value="">Select ' + title + '</option></select>');
	        var $select = $( 'select', this );
	        
	        $select.on( 'change', function () {
	        	var val = $.fn.dataTable.util.escapeRegex($(this).val());
	        	column.search( val ? '^'+val+'$' : '', true, false ).draw();
	        	return false;
	        }).on('click', function(){return false;});
	        
	        column.data().unique().sort().each( function ( d, j ) {
	        	$select.append( '<option value="'+d+'">'+d+'</option>' )
            } );
		}
	}
	
	function initHeader(){
		localizeHeader(this);
		addFilters(this);
	}
}