/*
  AUTH | hwding
  DATE | Sep 10 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.core;

import com.sun.istack.internal.NotNull;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.Set;

class TextStampRecognizer {

    private static boolean recognizeWithFont(
            @NotNull String[] keywords,
            @NotNull byte[] inputText,
            @NotNull Set<PDFont> pdFonts,
            @NotNull boolean useStrict) {
        String encodedInput = generateByteString(inputText);
        for (PDFont f : pdFonts) {
            if (f == null) continue;
            for (String k : keywords) {
                try {
                    byte[] encodedKeywordBytes = f.encode(k);
                    final String encodedKeyword = generateByteString(encodedKeywordBytes);
                    if (checkDuplicate(encodedInput, encodedKeyword, useStrict)) return true;
                } catch (IOException | IllegalArgumentException ignored) {
                }
            }
        }
        return false;
    }

    private static boolean recognizePlain(
            @NotNull String[] keywords,
            @NotNull byte[] inputText,
            @NotNull boolean useStrict
    ) {
        for (String k : keywords)
            if (checkDuplicate(new String(inputText), k, useStrict)) return true;
        return false;
    }

    private static boolean checkDuplicate(
            @NotNull String input,
            @NotNull String keyword,
            @NotNull boolean useStrict) {
        if (useStrict) {
            if (input.equals(keyword)) return true;
        } else {
            if (input.contains(keyword)) return true;
        }
        return false;
    }

    static boolean recognize(@NotNull String[] keywords,
                             @NotNull byte[] inputText,
                             @NotNull Set<PDFont> pdFonts,
                             @NotNull boolean useStrict) {
        return recognizePlain(keywords, inputText, useStrict) ||
                recognizeWithFont(keywords, inputText, pdFonts, useStrict);
    }

    private static String generateByteString(@NotNull byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes)
            stringBuilder.append(Byte.toString(b));
        return stringBuilder.toString();
    }
}
