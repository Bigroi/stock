'use strict';
$(document).ready(function(){
	var container = $("#main-table")
	if (container.length > 0){
		makeTable(container.attr("data-url"), container);
	}
	function makeTable(url, tableElement){
		
		var model;
		
		var tableData;
		
		var table;
		
		$.getJSON(url, function(answer){
			tableElement.addClass('display responsive nowrap');
			tableElement.css('width','100%');
			model = answer.data.model;
			
			tableData = answer.data.table;
			localizeHeader(tableData);
			tableData.rowCallback = rowCallback;
			tableData.drawCallback = removePagination;
			tableData.language = getLanguage();
			tableData.initComplete = initHeader;
			tableData.aaSorting = [];
			table = $(tableElement).DataTable(tableData);
	
			if ($(".add-button").length > 0){
				var editForm = window[model.editForm];
	            if (editForm){
					editForm($(".add-button"), $(tableElement), model);
	            } else {
	            	$(".add-button").click(function(){document.location = model.editForm;});
	            }
			}
			
		});
		
		function rowCallback($row, data, index) {
			var edit = data[model.editColumn];
			
			if (model.statusColumn){
				addStatusSwitcher(data, $row);
			}
			
			if(model.editColumn){
				addEditRemoveIcons(data, $row, this);
			}
		}
		
		function addEditRemoveIcons(data, $row, $table){
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
					editForm($edit, $table, model, id);
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
	            			 }
	            	);
	            });
	        }
			if(data[model.editColumn][2] == "Y"){ 
				$details.removeClass("no-details");
				var detailsLabel = l10n.translate("label.table.details");
				$details.addClass("details").text(detailsLabel);
	            var detailsForm = window[model.detailsUrl];
	            if (detailsForm){
	            	detailsForm($edit, $table, model, id);
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
				$($row).removeClass("inactive-row");
			} else {
				$($row).each(function(indx){
					$(this).addClass("inactive-row");
				});
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
	        			    	this.data(d).draw(false);
	        			    	return;
	        			    }
	        			} );
	        		} else {
	        			showMessageDialog(answer.message, "error");
	        		}
	        	});
	        });
	        $statusTd.append($switcher[0]);
		}
	
		function getLanguage(){
			return {
			    decimal:		l10n.translate("label.table.decimal"),
			    emptyTable:		l10n.translate("label.table.emptyTable"),
			    info:			l10n.translate("label.table.info"),
			    infoEmpty:		l10n.translate("label.table.infoEmpty"),
			    infoFiltered:	l10n.translate("label.table.infoFiltered"),
			    infoPostFix:	l10n.translate("label.table.infoPostFix"),
			    thousands:		l10n.translate("label.table.thousands"),
			    lengthMenu:		l10n.translate("label.table.lengthMenu"),
			    loadingRecords:	l10n.translate("label.table.loadingRecords"),
			    processing:		l10n.translate("label.table.processing"),
			    search:			l10n.translate("label.table.search"),
			    zeroRecords:	l10n.translate("label.table.zeroRecords"),
			    paginate: {
			        first:		l10n.translate("label.table.paginate_first"),
			        last:		l10n.translate("label.table.paginate_last"),
			        next:		l10n.translate("label.table.paginate_next"),
			        previous:	l10n.translate("label.table.paginate_previous")
			    },
			    aria: {
			        sortAscending:	l10n.translate("label.table.aria_sortAscending"),
			        sortDescending:	l10n.translate("label.table.aria_sortAscending")
			    }
			}
		}
		
		
		function localizeHeader(tableData){
			for (var i=0; i < tableData.columns.length ; i++  ) {
				var title = tableData.columns[i].title;
				title =  l10n.translate(title);
				tableData.columns[i].title = title;
			}
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
				$th.html( '<input type="text" />' );
		        $( 'input', this ).on( 'keyup change', function () {
		            if ( column.search() !== this.value ) {
		            	column.search( this.value ).draw();
		            }
		            return false;
		        } ).on('click', function(){return false;});
			}
			
			function addSelectFilter($th, column, title){
				$th.html( '<select><option value="">------</option></select>');
		        var $select = $( 'select', $th );
		        
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
		function removePagination(){
			if($("td").is(".dataTables_empty")) {
				$("#main-table_paginate").css("display","none");
			} else {
				$("#main-table_paginate").css("display","block");
			}
		}
		function initHeader(){
			addFilters(this);
		}
	}
});