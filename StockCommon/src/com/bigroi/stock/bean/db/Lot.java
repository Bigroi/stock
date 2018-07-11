package com.bigroi.stock.bean.db;

import java.util.Objects;

public class Lot extends Bid{
	
	private String foto;

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Lot && ((Lot)obj).getId() == this.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
	
	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
}