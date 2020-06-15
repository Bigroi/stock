package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.ui.ProductForUI;
import com.bigroi.stock.bean.ui.TradeOffer;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    List<Product> getAllActiveProducts();

    List<ProductForUI> getAllActiveProductsForUI();

    void delete(long id);

    Product getProductById(long id);

    void merge(Product product);

    List<TradeOffer> getTradeOffers(long productId);

}
