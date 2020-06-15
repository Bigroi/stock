package com.bigroi.stock.messager.generator;

import java.util.Map;

public interface Generator {

    String generateBasedOnTemplateString(String template, Map<String, ?> params);

    String generateBasedOnTemplateFile(String templateName, Map<String, ?> params);

}
