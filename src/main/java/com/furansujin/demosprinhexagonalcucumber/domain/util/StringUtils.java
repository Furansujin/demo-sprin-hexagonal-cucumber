package com.furansujin.demosprinhexagonalcucumber.domain.util;

import java.text.Normalizer;

public class StringUtils {


        public static String cleanString(String input) {
            if (input == null) {
                return null;
            }

            // Remove non-printable characters
            String printable = input.replaceAll("\\p{C}", " ");

            // Normalize string - this will convert accented characters to their normalized form,
            // e.g., "é" will become "e". This may not be what you want in all cases.
            String normalized = Normalizer.normalize(printable, Normalizer.Form.NFD);
            String nonAccented = normalized.replaceAll("\\p{M}", "");

            // Remove any remaining control characters (e.g., null, backspace, etc.)
            String noControlChars = nonAccented.replace("\0", "");

            // Replace newline characters, tabulations and multiple spaces with a single space
            String cleaned = noControlChars.replaceAll("\\r\\n|\\r|\\n|\\t|\\s+", " ");

            // Truncate string if it's too long for your database field
            String truncated = cleaned;
//            if (truncated.length() > 255) {
//                truncated = truncated.substring(0, 255);
//            }

            return truncated;
        }
    }
