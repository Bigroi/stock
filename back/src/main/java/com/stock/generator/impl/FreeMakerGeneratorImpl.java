package com.stock.generator.impl;

import com.stock.generator.Generator;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Locale;
import java.util.Map;

public class FreeMakerGeneratorImpl implements Generator {

    @Override
    public InputStream generateBasedOnTemplateString(String template, Map<String, ?> states) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setLocale(Locale.US);

            Template processor = new Template("name", new StringReader(template), cfg);

            var outputStream = new ByteArrayOutputStream();
            var writer = new OutputStreamWriter(outputStream);
            processor.process(states, writer);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateBasedOnTemplateFile(String templateName, Map<String, ?> params) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setLocale(Locale.US);
            cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/"));

            Template template = cfg.getTemplate(templateName);

            var outputStream = new ByteArrayOutputStream();
            var writer = new OutputStreamWriter(outputStream);
            template.process(params, writer);
            return new String(outputStream.toByteArray());
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
