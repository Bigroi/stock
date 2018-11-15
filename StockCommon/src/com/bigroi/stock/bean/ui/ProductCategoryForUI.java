package com.bigroi.stock.bean.ui;

import java.text.DecimalFormat;

import com.bigroi.stock.bean.db.ProductCategory;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class ProductCategoryForUI {

	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("# ###.##");
	
	@Id
	private final long id;
	
	@Column(value = "label.product_category.name", allowSorting = true, responsivePriority = -4)
	private final String categoryName;
	
	@Column(value = "label.product_category.archive", responsivePriority = -3)
	private final String removed;
	
	@Edit(edit="getProductCategoryDialogParams")
	@Column(value = "label.product_category.edit", responsivePriority = -4)
	private final String edit;
	
	public ProductCategoryForUI(ProductCategory category) {
		this.id = category.getId();
		this.categoryName = category.getCategoryName();
		this.removed = category.getRemoved();
		this.edit = "YNN";
	}
}
