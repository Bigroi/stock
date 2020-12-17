package com.stock.generator;

import java.io.InputStream;
import java.util.Map;

public interface Generator {

    InputStream generateBasedOnTemplateString(String template, Map<String, ?> params);

    String generateBasedOnTemplateFile(String templateName, Map<String, ?> params);

}
