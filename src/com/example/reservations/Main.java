package com.example.reservations;

public class Main {
    public static void main(String[] args) {
        String text1 = "Hallo, bitte für zwei Personen einen Tisch am 19.3. um 20:00 Uhr, Vielen Dank Klaus Müller";
        String text2 = "Sehr geehrte Damen Herren, wir würden gern am 9. April 9:45 Uhr mit sechs Leuten zum Brunch kommen, Mit freundlichen Grüßen Maria Meier";
        String text3 = "Guten Tag, einen Tisch für 8 Mann am 1.5. 9 Uhr abends, Gruß Franz Schulze";
        String text4 = "Hey, wir brauchen einen Tisch für 5 Frauen am 10.8. um 12, Gruß Monika Schmidt";


        System.out.println(ReservationExtractor.extract(text1));
        System.out.println(ReservationExtractor.extract(text2));
        System.out.println(ReservationExtractor.extract(text3));
        System.out.println(ReservationExtractor.extract(text4));
    }
}