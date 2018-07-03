package com.bigroi.stock.docs;

//@Configuration
public class DocumentConfiguarator{

	private static final String DEAL_DOC_FILE_ANME = "Deal.doc";

//	@Bean
	public DealDocument getDealDocument() throws DocumentException{
		return new DealDocument(DEAL_DOC_FILE_ANME);
	}
	
}
