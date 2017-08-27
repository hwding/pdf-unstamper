/*
  AUTH | hwding
  DATE | Aug 27 2017
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
import java.util.Collections;
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

                    List<Object> objects =
                            Collections.synchronizedList(pdfStreamParser.getTokens());

                    List<Object> cosNames =
                            objects.parallelStream()
                                    .filter(e -> e instanceof COSName)
                                    .collect(Collectors.toList());

                    Set<PDFont> pdFonts =
                            Collections.synchronizedSet(new HashSet<>());

                    cosNames.parallelStream()
                            .forEach(e -> {
                                /* Ignore Any Exception During Parallel Processing */
                                try {
                                    PDFont pdFont = pdPage.getResources().getFont(((COSName) e));
                                    if (pdFont != null)
                                        pdFonts.add(pdFont);
                                } catch (Exception ignored) {
                                }
                            });
                    /* END */
                    objects
                            .parallelStream()
                            .forEach(e -> {
                                        if (e instanceof COSString) {
                                    /* Ignore Any Exception During Parallel Processing */
                                            try {
                                                if (TextStampRecognizer.recognize(strings, ((COSString) e).getBytes(), pdFonts))
                                                    ((COSString) e).setValue(new byte[0]);
                                            } catch (Exception ignored) {
                                            }
                                        }
                                    }
                            );

                    PDStream newContents = new PDStream(pdDocument);
                    OutputStream out = newContents.createOutputStream();
                    ContentStreamWriter writer = new ContentStreamWriter(out);
                    writer.writeTokens(objects);
                    out.close();

                    pdPage.setContents(newContents);
                } catch (Exception e) {
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
