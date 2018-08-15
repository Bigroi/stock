package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Label;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class LabelForUI {
	
	@Id
	private final long id;
	
	@Column(value = "label.labels.category", responsivePriority=-1)
	private final String category;
	
	@Column(value = "label.labels.name", responsivePriority=-2)
	private final String name;
	
	@Column(value = "label.labels.english", responsivePriority=-3)
	private final String enUs;
	
	@Column(value = "label.labels.poland", responsivePriority=-4)
	private final String pl;
	
	@Column(value = "label.labels.russian", responsivePriority=-5)
	private final String ruRu;
	
	@Column(value = "label.labels.edit", responsivePriority=-6)
	@Edit(edit="getLabelDialogParams", remove="/label/json/admin/Delete.spr")
	private final String edit;
	
	public LabelForUI(Label label) {
		this.id = label.getId();
		this.category = label.getCategory();
		this.name = label.getName();
		this.enUs = label.getEnUs();
		this.pl = label.getPl();
		this.ruRu = label.getRuRu();
		this.edit = "YNN";
	}
}
