package com.bigroi.stock.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartTrace {

	private List<Double> x = new ArrayList<>();
	
	private List<Double> y = new ArrayList<>();

	public final String type = "scatter";
	
	public final String mode = "markers";
	
	private final Map<String, Object> marker = new HashMap<>();
	
	public void addDot(double x, double y){
		this.x.add(x);
		this.y.add(y);
	}
	
	public boolean isEmpty(){
		return x.size() == 0;
	}
	
	public Map<String, Object> getMarker() {
		return marker;
	}

	public void setColor(String color) {
		marker.put("color", color);
	}
}
