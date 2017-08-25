/*
  AUTH | hwding
  DATE | Aug 25 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.core;

import com.amastigote.unstamper.log.GeneralLogger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Processor {
    public static void process(File file, String[] strings) {
        try {
            if (!file.canWrite()) {
                GeneralLogger.File.notWritable(file.getName());
                return;
            }
            PDDocument pdDocument = PDDocument.load(file);
            pdDocument.getPages().forEach(pdPage -> {
                try {
                    /* START: loading font resources for further parsing */
                    PDFStreamParser pdfStreamParser = new PDFStreamParser(pdPage);
                    pdfStreamParser.parse();
                    List<Object> objects = pdfStreamParser.getTokens();
                    List<Object> cosNames = objects.parallelStream()
                            .filter(e -> e instanceof COSName)
                            .collect(Collectors.toList());
                    Set<PDFont> pdFonts = new HashSet<>();
                    cosNames.forEach(e -> {
                        try {
                            PDFont pdFont = pdPage.getResources().getFont(((COSName) e));
                            if (pdFont != null)
                                pdFonts.add(pdFont);
                        } catch (IOException ignored) {
                        }
                    });
                    /* END */
                    for (Object o : objects) {
                        if (o instanceof COSString) {
                            if (TextStampRecognizer.recognize(strings, ((COSString) o).getBytes(), pdFonts))
                                ((COSString) o).setValue(new byte[0]);
                        }
                    }
                    PDStream newContents = new PDStream(pdDocument);
                    OutputStream out = newContents.createOutputStream();
                    ContentStreamWriter writer = new ContentStreamWriter(out);
                    writer.writeTokens(objects);
                    out.close();
                    pdPage.setContents(newContents);
                } catch (IOException e) {
                    GeneralLogger.Processor.errorProcess(file.getName());
                }
            });
            pdDocument.save(file);
            pdDocument.close();
        } catch (IOException e) {
            GeneralLogger.Processor.errorLoadPdf(file.getName());
        }
    }
}
