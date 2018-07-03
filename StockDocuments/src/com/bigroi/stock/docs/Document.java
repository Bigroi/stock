package com.bigroi.stock.docs;

public abstract class Document<T> {

//	private final POIFSFileSystem file;
//
//	protected Document(String fileName) throws DocumentException {
//		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
//			file = new POIFSFileSystem(inputStream);
//		} catch (IOException e) {
//			throw new DocumentException(e);
//		}
//	}
//
//	protected final HWPFDocument replaceText(Map<String, String> replaceText) throws DocumentException {
//		try {
//			HWPFDocument doc = new HWPFDocument(file);
//			Range r1 = doc.getRange();
//
//			for (int i = 0; i < r1.numSections(); ++i) {
//				Section s = r1.getSection(i);
//				for (int x = 0; x < s.numParagraphs(); x++) {
//					Paragraph p = s.getParagraph(x);
//					for (int z = 0; z < p.numCharacterRuns(); z++) {
//						CharacterRun run = p.getCharacterRun(z);
//						String text = run.text();
//						for (String key : replaceText.keySet()) {
//							if (text.contains(key)) {
//								run.replaceText(key, replaceText.get(key));
//							}
//						}
//					}
//				}
//			}
//			return doc;
//		} catch (IOException e) {
//			throw new DocumentException(e);
//		}
//	}
//
//	public abstract byte[] getDocument(T object) throws DocumentException;
}
