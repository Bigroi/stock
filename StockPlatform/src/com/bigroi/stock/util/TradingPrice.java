package com.bigroi.stock.util;

public class TradingPrice {
	public static double getPrice(double sellerPrice, double customerPrice) {
		return (sellerPrice + customerPrice) / 2;
	}
}
