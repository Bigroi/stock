'use strict';
window.l10n = { };
function localization(){
	var labels = ['label.deal.time',
	'label.deal.productName',
	'label.deal.partnerAddress',
	'label.deal.partnerComment',
	'label.deal.status',
	'label.lot.product',
	'label.lot.status',
	'label.lot.min_price',
	'label.lot.max_volume',
	'label.lot.exp_date',
	'label.lot.edit',
	'label.lot.creation_date',
	'label.user.not_found',
	'label.tender.product',
	'label.tender.status',
	'label.tender.max_price',
	'label.tender.max_volume',
	'label.tender.exp_date',
	'label.tender.creation_date',
	'label.tender.edit',
	'label.product.name',
	'label.product.description',
	'label.product.archive',
	'label.users.login',
	'label.users.company_id',
	'label.account.name',
	'label.account.phone',
	'label.account.reg_number',
	'label.account.status',
	'label.button.login',
	'label.button.reset',
	'label.button.cancel',
	'label.button.delete',
	'label.button.start_trading',
	'label.button.stop_trading',
	'label.button.save',
	'label.button.save_start_trading',
	'label.button.end_trading',
	'label.button.invite',
	'label.button.send',
	'label.button.ok',
	'label.table.decimal',
	'label.table.emptyTable',
	'label.table.info',
	'label.table.infoEmpty',
	'label.table.infoFiltered',
	'label.table.infoPostFix',
	'label.table.thousands',
	'label.table.lengthMenu',
	'label.table.loadingRecords',
	'label.table.processing',
	'label.table.search',
	'label.table.zeroRecords',
	'label.table.paginate_first',
	'label.table.paginate_last',
	'label.table.paginate_next',
	'label.table.paginate_previous',
	'label.table.aria_sortAscending',
	'label.table.aria_sortDescending',
	'label.table.details',
	'label.product.price',
	'label.product.lot_volume',
	'label.product.tender_volume',
	'label.product.edit',
	'label.tradeOffers.graph_title',
	'label.tradeOffers.price',
	'label.tradeOffers.volume',
	]
	
    $.ajax("/l10n/json/Labels.spr",{
    	type: "POST",
    	data: {json: JSON.stringify(labels)}, 
    	success: function(answer){
    				try{
    					window.l10n = answer.data;
    				}catch(e){
    					console.log(e);
    				}
    			},
    	dataType: "json",
    	async: false
    })
}

function translate(word){
	var translation = window.l10n[word];
	if (translation || translation == ''){
		return translation;
	} else {
		return "not found " + word;
	}
}