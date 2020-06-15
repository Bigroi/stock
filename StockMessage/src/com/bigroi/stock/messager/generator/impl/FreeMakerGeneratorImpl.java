package com.bigroi.stock.messager.generator.impl;

import com.bigroi.stock.messager.generator.Generator;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;

public class FreeMakerGeneratorImpl implements Generator {

    @Override
    public String generateBasedOnTemplateString(String template, Map<String, ?> params) {
        return generate(cfg -> new Template("string generation", new StringReader(template), cfg), params);
    }

    @Override
    public String generateBasedOnTemplateFile(String templateName, Map<String, ?> params) {
        return generate(cfg -> cfg.getTemplate(templateName), params);
    }

    private String generate(ThrowableFunction<Configuration, Template> templateCreator, Map<String, ?> params) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setLocale(Locale.US);
            cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/"));

            Template template = templateCreator.apply(cfg);

            var outputStream = new ByteArrayOutputStream();
            var writer = new OutputStreamWriter(outputStream);
            template.process(params, writer);
            return new String(outputStream.toByteArray());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private interface ThrowableFunction<T, R> {
        R apply(T argument) throws Throwable;
    }
}
