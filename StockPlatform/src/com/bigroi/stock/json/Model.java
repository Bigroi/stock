package com.bigroi.stock.json;

import java.util.HashMap;
import java.util.Map;

class Model {

	private Map<Integer, String> custSortFn = new HashMap<>();
	
	private Map<Integer, Boolean> allowSorting = new HashMap<>();
	
	public Map<Integer, String> getCustSortFn() {
		return custSortFn;
	}
	
	public Map<Integer, Boolean> getAllowSorting() {
		return allowSorting;
	}
	
	public void addAllowSorting(int columnIndex, boolean allow){
		if (allow){
			allowSorting.put(columnIndex, Boolean.TRUE);
		}
	}
	
	public void addCustSortFn(int columnIndex, String function){
		if (function != null && function.length() != 0){
			custSortFn.put(columnIndex, function);
		}
	}

	public void exclude(int index) {
		Map<Integer, Boolean> newMap = new HashMap<>();
		for (Integer key : allowSorting.keySet()){
			if (key < index){
				newMap.put(index, allowSorting.get(index));
			} else if (key > index){
				newMap.put(index - 1, allowSorting.get(index));
			}
		}
		allowSorting = newMap;
	}
}
