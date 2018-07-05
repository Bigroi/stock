package com.bigroi.stock.docs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public abstract class Document<T> {

	private final POIFSFileSystem file;

	protected Document(String fileName) throws DocumentException {
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
			file = new POIFSFileSystem(inputStream);
		} catch (IOException e) {
			throw new DocumentException(e);
		}
	}

	protected final HWPFDocument replaceText(Map<String, Object> replaceText) throws DocumentException {
		try {
			HWPFDocument doc = new HWPFDocument(file);
			Range r1 = doc.getRange();

			for (int i = 0; i < r1.numSections(); ++i) {
				Section s = r1.getSection(i);
				for (int x = 0; x < s.numParagraphs(); x++) {
					Paragraph p = s.getParagraph(x);
					for (int z = 0; z < p.numCharacterRuns(); z++) {
						CharacterRun run = p.getCharacterRun(z);
						String text = run.text();
						for (String key : replaceText.keySet()) {
							if (text.contains(key)) {
								run.replaceText(key, String.valueOf(replaceText.get(key)));
							}
						}
					}
				}
			}
			return doc;
		} catch (IOException e) {
			throw new DocumentException(e);
		}
	}

	public abstract byte[] getDocument(T object) throws DocumentException;
}
