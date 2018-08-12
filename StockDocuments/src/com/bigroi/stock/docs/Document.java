package com.bigroi.stock.docs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.bigroi.stock.util.LabelUtil;
import com.bigroi.stock.util.exception.StockRuntimeException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public abstract class Document<T> {

	private final Map<String, POIFSFileSystem> files;

	protected Document(String fileName, String fileExtention) {
		Builder<String, POIFSFileSystem> builder = ImmutableMap.builder();
		for (Locale locale : LabelUtil.getPassibleLanguages(null)){
			builder.put(locale.toString(), readFile(fileName, locale, fileExtention));
		}
		files = builder.build();
	}
	
	private POIFSFileSystem readFile(String fileName, Locale locale, String fileExtention) {
		fileName =fileName + "-" + locale.toString() + "." + fileExtention;
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
			return new POIFSFileSystem(inputStream);
		} catch (IOException e) {
			throw new StockRuntimeException(e);
		}
	}

	protected final HWPFDocument replaceText(Map<String, Object> replaceText, String locale) {
		try {
			HWPFDocument doc = new HWPFDocument(files.get(locale));
			Range r1 = doc.getRange();

			for (int i = 0; i < r1.numSections(); ++i) {
				Section s = r1.getSection(i);
				for (int x = 0; x < s.numParagraphs(); x++) {
					replaceInParagraph(s.getParagraph(x), replaceText);
				}
			}
			return doc;
		} catch (IOException e) {
			throw new StockRuntimeException(e);
		}
	}
	
	private void replaceInParagraph(Paragraph p, Map<String, Object> replaceText){
		for (int z = 0; z < p.numCharacterRuns(); z++) {
			CharacterRun run = p.getCharacterRun(z);
			String text = run.text();
			for (Entry<String, Object> entry : replaceText.entrySet()) {
				if (text.contains(entry.getKey())) {
					run.replaceText(entry.getKey(), String.valueOf(entry.getValue()));
				}
			}
		}
	}

	public abstract byte[] getDocument(T object);
}
