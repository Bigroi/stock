'use strict';
$(document).ready(function(){
	$("*[id*=main-table]:visible").each(function() {
		var $container = $(this);
		makeTable($container.attr("data-url"), $container);
	});
});
function makeTable(url, $tableElement){
	
	var model;
	
	var tableData;
	
	var table;
	
	$.getJSON(getContextRoot() + url, function(answer){
		$tableElement.addClass('display responsive nowrap');
		$tableElement.css('width','100%');
		model = answer.data.model;
		
		tableData = answer.data.table;
		localizeHeader(tableData);
		tableData.rowCallback = rowCallback;
		tableData.drawCallback = removePagination;
		tableData.language = getLanguage();
		tableData.aaSorting = [];
		table = $tableElement.DataTable(tableData);

		var addButtonClass = $tableElement.attr("data-add-button");
		if (!addButtonClass){
			addButtonClass = "add-button";
		}
		if ($("." + addButtonClass).length > 0){
			var editFormParams = window[model.editForm];
            if (editFormParams){
            	$("." + addButtonClass).on("click", function(){
            		showDialog(editFormParams(-1, $tableElement, model));
            	});
            } else {
            	$("." + addButtonClass).click(function(){document.location = getContextRoot() + model.editForm;});
            }
		}
	});
	
	function rowCallback($row, data, index) {
		if (model.statusColumn){
			addStatusSwitcher(data, $row);
		}
		
		if(model.editColumn && getCell(model.editColumn, $row)){
			addEditRemoveIcons(data, $row, this);
		}
		if (data.alert) {
			var $alert = $(`<span class='bid-alert' style="font-size: 1em">${l10n.translate('label.alert.' + data.alert)}</span>`);
			getCell("productName", $row).append($alert[0]);
		}
	}
	
	function addEditRemoveIcons(data, $row, $table){
		var id = data[model.idColumn];
		var $editRemove = getCell(model.editColumn, $row);
        $editRemove.textContent = "";
        
        var $edit = $("<div class='no-edit'></div>");
        var $remove = $("<div class='no-remove'></div>");
        var $details = $("<div class='no-details'></div>");
        
        if(data[model.editColumn][0] == "Y"){ 
            $edit.removeClass("no-edit");
            $edit.addClass("edit");
            var editFormParams = window[model.editForm];
            if (editFormParams){
            	$edit.on("click", function(){
            		showDialog(editFormParams(id, $table, model));
            	});
            } else {
            	$edit.click(function(){document.location = getContextRoot() + model.editForm + "?id=" + id});
            }
        }
		if(data[model.editColumn][1] == "Y"){ 
            $remove.removeClass("no-remove");
            $remove.addClass("remove");
            $remove.click(function(event){
            	 $.getJSON(
            			 getContextRoot() + model.removeUrl, 
            			 {id:id}, 
            			 function(answer){
            				 if (answer.result > 0){
            					 table.row($(event.target).parents('tr')).remove().draw();
            				 } else {
            					 showMessageDialog(answer.message, "error");
            				 }
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
            	detailsForm(id, $table, model);
            } else {
            	$details.click(function(){document.location = getContextRoot() + model.detailsUrl + "?id=" + id});
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
        	$.getJSON(getContextRoot() + url, {id:id}, function(answer){
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
        			if (answer.message) {
						showMessageDialog(answer.message, "success");
					}
        		} else {
        			showMessageDialog(answer.message, "error");
        		}
        	});
        });
        $statusTd.append($switcher[0]);
	}

	function getLanguage(){
		return {
		    decimal:			l10n.translate("label.table.decimal"),
		    emptyTable:			l10n.translate("label.table.emptyTable"),
		    info:				l10n.translate("label.table.info"),
		    infoEmpty:			l10n.translate("label.table.infoEmpty"),
		    infoFiltered:		l10n.translate("label.table.infoFiltered"),
		    infoPostFix:		l10n.translate("label.table.infoPostFix"),
		    thousands:			l10n.translate("label.table.thousands"),
		    lengthMenu:			l10n.translate("label.table.lengthMenu"),
		    loadingRecords:		l10n.translate("label.table.loadingRecords"),
		    processing:			l10n.translate("label.table.processing"),
		    search:  			l10n.translate("label.table.search"),
		    searchPlaceholder:  l10n.translate("label.table.searchPlaceholder"),
		    zeroRecords:		l10n.translate("label.table.zeroRecords"),
		    paginate: {
		        first:			l10n.translate("label.table.paginate_first"),
		        last:			l10n.translate("label.table.paginate_last"),
		        next:			l10n.translate("label.table.paginate_next"),
		        previous:		l10n.translate("label.table.paginate_previous")
		    },
		    aria: {
		        sortAscending:	l10n.translate("label.table.aria_sortAscending"),
		        sortDescending:	l10n.translate("label.table.aria_sortDescending")
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
	
	function removePagination(){
		if($("td").is(".dataTables_empty")) {
			$("#main-table_paginate").css("display","none");
		} else {
			$("#main-table_paginate").css("display","block");
		}
	}
}