/*
  AUTH | hwding
  DATE | Nov 16 2018
  DESC | textual watermark remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.core;

import com.amastigote.unstamper.log.GeneralLogger;
import com.sun.istack.internal.NotNull;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class Processor {

    private static final byte[] empBytes = new byte[0];
    private static final List empList = Collections.emptyList();

    public static void process(
            @NotNull File file,
            @NotNull String[] strings,
            @NotNull boolean useStrict,
            @NotNull boolean removeAnnotations) {
        GeneralLogger.Processor.procInProgress(file.getName());

        try {
            if (!file.canWrite()) {
                GeneralLogger.File.notWritable(file.getName());
                return;
            }
            PDDocument pdDocument = PDDocument.load(file);
            pdDocument.getPages().forEach(pdPage -> {
                try {
                    /* START: loading font resources from current page */
                    PDFStreamParser pdfStreamParser = new PDFStreamParser(pdPage);
                    pdfStreamParser.parse();

                    Set<PDFont> pdFonts = new HashSet<>();

                    pdPage.getResources().getFontNames().forEach(e -> {
                        /* Ignore Any Exception During Parallel Processing */
                        try {
                            PDFont pdFont = pdPage.getResources().getFont(e);
                            if (pdFont != null)
                                pdFonts.add(pdFont);
                        } catch (Exception ignored) {
                        }
                    });
                    /* END */

                    /*
                    * to handle both string array and string
                    * */
                    List<Object> objects = pdfStreamParser.getTokens();
                    for (int i = 0;i < objects.size();i++) {
                        Object e = objects.get(i);
                        if (e instanceof Operator) {
                            Operator op = (Operator)e;
                            String testStr = "";
                            boolean isArray = false;
                            if (op.getName().equals("TJ") || (op.getName().equals("Tj"))) {
                                if (i == 0) continue;
                                if (objects.get(i - 1) instanceof COSArray) {
                                    isArray = true;
                                    COSArray array = (COSArray)objects.get(i - 1);
                                    StringBuffer buffer = new StringBuffer();
                                    for (int j = 0;j < array.size();j++) {
                                        if (array.getString(j) == null) continue;
                                        buffer.append(array.getString(j));
                                    }
                                    testStr = buffer.toString();
                                } else if (objects.get(i - 1) instanceof COSString) {
                                    COSString str = (COSString) objects.get(i - 1);
                                    testStr = str.toString();
                                } else {
                                    continue;
                                }
                                try {
                                    if (TextStampRecognizer.recognize(strings, testStr.getBytes(), pdFonts, useStrict)) {
                                        if (isArray) ((COSArray)objects.get(i - 1)).clear();
                                        else ((COSString) objects.get(i - 1)).setValue(empBytes);
                                        if (removeAnnotations) {
                                            //noinspection unchecked
                                            pdPage.setAnnotations(empList);
                                        }
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    }

                    /* START: write modified tokens back to the stream */
                    PDStream newContents = new PDStream(pdDocument);
                    OutputStream out = newContents.createOutputStream();
                    ContentStreamWriter writer = new ContentStreamWriter(out);
                    writer.writeTokens(objects);
                    out.close();
                    /* END */

                    pdPage.setContents(newContents);
                } catch (Exception e) {
                    GeneralLogger.Processor.errorProcess(file.getName() + e.getMessage());
                }
            });

            /* write back to the file */
            pdDocument.save(file);
            pdDocument.close();
        } catch (IOException e) {
            GeneralLogger.Processor.errorLoadPdf(file.getName());
        }
    }
}
