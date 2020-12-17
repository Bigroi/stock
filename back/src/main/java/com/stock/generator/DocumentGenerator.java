package com.stock.generator;

import java.util.Map;

public interface DocumentGenerator {

    byte[] generate(String fileName, Map<String, Object> params);

}
