/*
  AUTH | hwding
  DATE | Mar 08 2019
  DESC | textual watermark remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.core;

import com.sun.istack.internal.NotNull;
import org.apache.pdfbox.pdmodel.font.PDCIDFontType0;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType3Font;

import java.util.Objects;
import java.util.Set;

class TextStampRecognizer {

    private static boolean recognizeWithFont(
            @NotNull String[] keywords,
            @NotNull byte[] inputText,
            @NotNull Set<PDFont> pdFonts,
            @NotNull boolean useStrict) {
        final String encodedInput = generateByteString(inputText);
        for (PDFont f : pdFonts) {
            if (Objects.isNull(f)) {
                continue;
            }

            /* do not encode unsupported font */
            if ((f instanceof PDType0Font && ((PDType0Font) f).getDescendantFont() instanceof PDCIDFontType0)
                    || f instanceof PDType3Font) {
                continue;
            }

            for (String k : keywords) {
                try {
                    final byte[] encodedKeywordBytes = f.encode(k);
                    final String encodedKeyword = generateByteString(encodedKeywordBytes);

                    if (checkDuplicate(encodedInput, encodedKeyword, useStrict)) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return false;
    }

    static boolean recognizePlain(
            @NotNull String[] keywords,
            @NotNull byte[] inputText,
            @NotNull boolean useStrict
    ) {
        for (String k : keywords) {
            if (checkDuplicate(new String(inputText), k, useStrict)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDuplicate(
            @NotNull String input,
            @NotNull String keyword,
            @NotNull boolean useStrict) {
        if (useStrict) {
            return input.equals(keyword);
        } else {
            return input.contains(keyword);
        }
    }

    static boolean recognize(@NotNull String[] keywords,
                             @NotNull byte[] inputText,
                             @NotNull Set<PDFont> pdFonts,
                             @NotNull boolean useStrict) {
        return recognizePlain(keywords, inputText, useStrict) ||
                recognizeWithFont(keywords, inputText, pdFonts, useStrict);
    }

    private static String generateByteString(@NotNull byte[] bytes) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(b);
        }
        return stringBuilder.toString();
    }
}
