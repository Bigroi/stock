'use strict';
window.l10n = { };
function localization(){
	var labels = ['label.deal.time',
	'label.deal.productName',
	'label.deal.partnerAddress',
	'label.deal.partnerComment',
	'label.deal.status',
	'label.deal.price',
	'label.deal.volume',
	'label.deal.partnerPhone',
	'label.deal.partnerRegNumber',
	'label.deal.partnerName',
	'label.deal.approve',
	'label.deal.reject',
	'label.deal.deal',
	'label.deal.foto',
	'label.deal.not_authorized',
	'label.deal.approved',
	'label.deal.rejected',
	'label.lot.product',
	'label.lot.status',
	'label.lot.min_price',
	'label.lot.max_volume',
	'label.lot.exp_date',
	'label.lot.edit',
	'label.lot.creation_date',
	'label.lot.description',
	'label.lot.min_volume',
	'label.lot.my_lots',
	'label.lot.start_trading',
	'label.lot.cancel',
	'label.lot.lotForm',
	'label.lot.list',
	'label.lot.delivery',
	'label.lot.packaging',
	'label.lot.foto',
	'label.lot.expDate_error',
	'label.lot.maxVolume_error',
	'label.lot.minVolume_error',
	'label.lot.minPrice_error',
	'label.lot.product_error',
	'label.lot.not_found',
	'label.user.not_found',
	'label.tender.product',
	'label.tender.status',
	'label.tender.max_price',
	'label.tender.max_volume',
	'label.tender.exp_date',
	'label.tender.creation_date',
	'label.tender.edit',
	'label.tender.description',
	'label.tender.min_volume',
	'label.tender.cancel',
	'label.tender.start_trading',
	'label.tender.tenderForm',
	'label.tender.list',
	'label.tender.delivery',
	'label.tender.packaging',
	'label.tender.expDate_error',
	'label.tender.maxVolume_error',
	'label.tender.minVolume_error',
	'label.tender.maxPrice_error',
	'label.tender.product_error',
	'label.product.name',
	'label.product.description',
	'label.product.archive',
	'label.product.products',
	'label.product.productForm',
	'label.product.price',
	'label.product.lot_volume',
	'label.product.tender_volume',
	'label.product.edit',
	
	'label.users.login', //??????????????????????
	'label.users.company_id',
	
	'label.account.name',
	'label.account.phone',
	'label.account.reg_number',
	'label.account.status',
	'label.account.password',
	'label.account.address',
	'label.account.country',
	'label.account.city',
	'label.account.my_lots',
	'label.account.my_tenders',
	'label.account.account',
	'label.account.invite_success',
	'label.account.edit_success',
	'label.account.email',
	'label.account.message',
	'label.account.fb_success',
	'label.account.contact_us',
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
	'label.button.modify',
	'label.button.back',
	'label.button.create',
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