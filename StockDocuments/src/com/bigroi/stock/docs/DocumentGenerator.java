package com.bigroi.stock.docs;

import java.util.Map;

public interface DocumentGenerator {

    byte[] generate(String fileName, Map<String, Object> params);

}
