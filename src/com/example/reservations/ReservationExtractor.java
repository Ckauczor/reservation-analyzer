package com.example.reservations;

import java.util.regex.*;

public class ReservationExtractor {

    // Pattern-Konstanten für bessere Lesbarkeit und Performance
    private static final Pattern PEOPLE_PATTERN = Pattern.compile(
        "(\\d+|zwei|drei|vier|fünf|sechs|sieben|acht|neun|zehn)" +
        "\\s+" +
        "(personen|leute|mann|frauen)"
    );
    
    private static final Pattern DATE_MONTH_NAME_PATTERN = Pattern.compile(
        "(\\d{1,2})" +
        "\\." +
        "\\s*" +
        "(april|mai|juni)"
    );
    
    private static final Pattern DATE_NUMERIC_PATTERN = Pattern.compile(
        "(\\d{1,2})" +
        "\\." +
        "(\\d{1,2})" +
        "\\b"
    );
    
    private static final Pattern TIME_HHMM_PATTERN = Pattern.compile(
        "(\\d{1,2})" +
        ":" +
        "(\\d{2})"
    );
    
    private static final Pattern TIME_EVENING_PATTERN = Pattern.compile(
        "(\\d{1,2})" +
        "\\s+" +
        "uhr" +
        "\\s+" +
        "abends"
    );
    
    private static final Pattern TIME_UM_PATTERN = Pattern.compile(
        "um" +
        "\\s+" +
        "(\\d{1,2})" +
        "\\b"
    );
    
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "(dank|gruß|grüßen)" +
        "\\s+" +
        "([a-zäöüß]+(?:\\s+[a-zäöüß]+)*)"
    );

    public static Reservation extract(String text) {
        text = text.toLowerCase();
        
        // Extract all 4 fields with simple regex
        int people = extractPeople(text);
        String date = extractDate(text);
        String time = extractTime(text);
        String name = extractName(text);

        return new Reservation(name, date, time, people);
    }

    private static int extractPeople(String text) {
        Matcher m = PEOPLE_PATTERN.matcher(text);
        if (m.find()) {
            String num = m.group(1);
            if (num.matches("\\d+")) return Integer.parseInt(num);
            return switch (num) {
                case "zwei" -> 2; case "drei" -> 3; case "vier" -> 4; case "fünf" -> 5;
                case "sechs" -> 6; case "sieben" -> 7; case "acht" -> 8; case "neun" -> 9; case "zehn" -> 10;
                default -> 0;
            };
        }
        return 0;
    }

    private static String extractDate(String text) {
        // dd.month
        Matcher m = DATE_MONTH_NAME_PATTERN.matcher(text);
        if (m.find()) {
            int month = switch (m.group(2)) { case "april" -> 4; case "mai" -> 5; default -> 6; };
            return String.format("%02d.%02d.", Integer.parseInt(m.group(1)), month);
        }
        // dd.mm
        m = DATE_NUMERIC_PATTERN.matcher(text);
        if (m.find()) return String.format("%02d.%02d.", Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        return "";
    }

    private static String extractTime(String text) {
        // hh:mm
        Matcher m = TIME_HHMM_PATTERN.matcher(text);
        if (m.find()) return String.format("%02d:%02d", Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        
        // h uhr abends
        m = TIME_EVENING_PATTERN.matcher(text);
        if (m.find()) return String.format("%02d:00", Integer.parseInt(m.group(1)) + 12);
        
        // just h (noon)
        m = TIME_UM_PATTERN.matcher(text);
        if (m.find()) return String.format("%02d:00", Integer.parseInt(m.group(1)));
        return "";
    }

    private static String extractName(String text) {
        Matcher m = NAME_PATTERN.matcher(text);
        if (m.find()) {
            String[] words = m.group(2).split("\\s+");
            StringBuilder result = new StringBuilder();
            for (String word : words) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
            return result.toString().trim();
        }
        return "Unbekannt";
    }
}