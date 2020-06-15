package com.bigroi.stock.docs.impl;

import com.bigroi.stock.docs.DocumentGenerator;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentGeneratorImpl implements DocumentGenerator {

    private final Map<String, POIFSFileSystem> files;

    public DocumentGeneratorImpl(List<String> fileNames) {
        files = fileNames.stream().collect(Collectors.toMap(fileName -> fileName, this::readFile));
    }

    private POIFSFileSystem readFile(String fileName) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            return new POIFSFileSystem(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] generate(String fileName, Map<String, Object> params) {
        try {
            var doc = new HWPFDocument(files.get(fileName));
            var r1 = doc.getRange();

            for (var i = 0; i < r1.numSections(); ++i) {
                var s = r1.getSection(i);
                for (int x = 0; x < s.numParagraphs(); x++) {
                    replaceInParagraph(s.getParagraph(x), params);
                }
            }
            var baos = new ByteArrayOutputStream();
            doc.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void replaceInParagraph(Paragraph p, Map<String, Object> replaceText) {
        for (var z = 0; z < p.numCharacterRuns(); z++) {
            var run = p.getCharacterRun(z);
            var text = run.text();
            replaceText.forEach((key, value) -> {
                if (text.contains(key)) {
                    run.replaceText(key, String.valueOf(value));
                }
            });
        }
    }
}
