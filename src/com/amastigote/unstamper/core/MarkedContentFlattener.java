/*
  AUTH | hwding
  DATE | Mar 08 2019
  DESC | textual watermark remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.core;

import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDMarkedContent;

import java.util.List;
import java.util.function.Consumer;

public class MarkedContentFlattener implements Consumer<PDMarkedContent> {
    private List<PDMarkedContent> containerRef;

    MarkedContentFlattener container(final List<PDMarkedContent> containerRef) {
        this.containerRef = containerRef;
        return this;
    }

    void flatten(final List<PDMarkedContent> contents) {
        contents
                .parallelStream()
                .forEachOrdered(c -> {
                    containerRef.add(c);
                    this.accept(c);
                });
    }

    @Override
    public void accept(final PDMarkedContent pdMarkedContent) {
        pdMarkedContent
                .getContents()
                .parallelStream()
                .forEachOrdered(c -> {
                    if (c instanceof PDMarkedContent) {
                        final PDMarkedContent content = (PDMarkedContent) c;

                        containerRef.add(content);
                        this.accept(content);
                    }
                });
    }
}
