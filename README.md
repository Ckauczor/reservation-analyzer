# Ultra-Simple Reservation Analyzer

Ein minimalistischer Java-Service zur Extraktion von Reservierungsinformationen aus deutschen Textnachrichten mit nur 120 Zeilen Code.

## Was passiert in diesem Code?

Dieser Service verwendet **vier einfache Regex-Muster**, um Reservierungsdaten aus natürlichsprachigen deutschen Texten zu extrahieren:

### 1. **Personenanzahl erkennen** (`extractPeople`)
```java
Pattern: "(\\d+|zwei|drei|vier|fünf|sechs|sieben|acht|neun|zehn)\\s+(personen|leute|mann|frauen)"
```
- Findet Zahlen (1-9) oder deutsche Zahlwörter (zwei-zehn)
- Gefolgt von Personenbeschreibungen (personen, leute, mann, frauen)
- Beispiel: "6 Leuten" → 6, "drei Personen" → 3

### 2. **Datum extrahieren** (`extractDate`)
```java
// Monatsnamen: "(\\d{1,2})\\.\\s*(april|mai|juni)"
// Numerisch: "(\\d{1,2})\\.(\\d{1,2})\\b"
```
- Erkennt sowohl "9. April" als auch "19.3." Format
- Konvertiert Monatsnamen zu Zahlen
- Formatiert zu "DD.MM." Format

### 3. **Uhrzeit finden** (`extractTime`)
```java
// HH:MM Format: "(\\d{1,2}):(\\d{2})"
// Abends: "(\\d{1,2})\\s+uhr\\s+abends"
// Um Zeit: "um\\s+(\\d{1,2})\\b"
```
- Erkennt "20:00", "9 Uhr abends" (+12h), "um 12"
- Automatische Konvertierung zu 24h-Format
- Beispiel: "9 Uhr abends" → "21:00"

### 4. **Namen extrahieren** (`extractName`)
```java
Pattern: "(dank|gruß|grüßen)\\s+([a-zäöüß]+(?:\\s+[a-zäöüß]+)*)"
```
- Sucht nach Grußformeln (Dank, Gruß, Grüßen)
- Extrahiert den nachfolgenden Namen
- Automatische Großschreibung: "klaus müller" → "Klaus Müller"

## Beispiele

| Input | Output |
|-------|--------|
| `"für zwei Personen am 19.3. um 20:00 Uhr, Vielen Dank Klaus Müller"` | `(Klaus Müller, 19.03., 20:00, 2)` |
| `"mit sechs Leuten am 9. April 9:45 Uhr, Grüßen Maria Meier"` | `(Maria Meier, 09.04., 09:45, 6)` |
| `"für 8 Mann am 1.5. 9 Uhr abends, Gruß Franz Schulze"` | `(Franz Schulze, 01.05., 21:00, 8)` |
| `"für 5 Frauen am 10.8. um 12, Gruß Monika Schmidt"` | `(Monika Schmidt, 10.08., 12:00, 5)` |

## Projektstruktur

```
src/com/example/reservations/
├── Main.java                 # Test-Anwendung mit Beispielen
├── Reservation.java          # Einfache Datenklasse (4 Felder)
└── ReservationExtractor.java # Haupt-Extraktionslogik (73 Zeilen)
```

## Ausführen

```bash
# Kompilieren
javac src/com/example/reservations/*.java

# Ausführen
java -cp src com.example.reservations.Main
```

## Design-Prinzipien

### ✅ **Einfachheit**
- Nur **120 Zeilen** Haupt-Code
- **4 fokussierte Methoden** - eine pro Datenfeld
- **Keine externen Abhängigkeiten**
- **Keine komplexen Datenstrukturen**

### ✅ **Direkte Regex-Patterns**
- Inline-Regex statt gespeicherte Konstanten
- Switch-Statements statt Map-Lookups
- Einfache String-Operationen statt Stream-Processing

### ✅ **Klare Trennung**
- Eine Methode = Ein Datenfeld
- Keine verschachtelten Loops
- Direkte Return-Werte

## Warum ist das besser als komplexe Lösungen?

| Aspekt | Komplex | Ultra-Simple |
|--------|---------|--------------|
| **Lesbarkeit** | Verschachtelte Logik | 4 klare Methoden |
| **Wartbarkeit** | 200+ Zeilen | 120 Zeilen |
| **Verständlichkeit** | Maps, Streams, Loops | Direkte Switch/Regex |
| **Debugging** | Schwer zu verfolgen | Einfach zu testen |
| **Erweiterung** | Komplexe Refactoring | Neue Regex hinzufügen |

## Parallelen zur Finanzindustrie

Dieser ultra-simple Ansatz demonstriert wichtige Prinzipien für die Finanzindustrie:

### 1. **Pattern Recognition in Dokumenten**
- **Finanz**: Extraktion von IBAN, Beträgen, Vertragsdaten aus PDFs
- **Hier**: Extraktion von Namen, Datum, Zeit aus Reservierungstext
- **Vorteil**: Einfache Regex-Patterns sind auditierbar und nachvollziehbar

### 2. **Compliance & Nachvollziehbarkeit**
- **Finanz**: Regulatorische Anforderungen an transparente Algorithmen
- **Hier**: Jede Extraktion ist durch einfache Regex dokumentiert
- **Vorteil**: Keine "Black Box" - jeder Schritt ist verständlich

### 3. **Fehlerbehandlung in kritischen Systemen**
- **Finanz**: Transaktionen müssen auch bei partiellen Daten verarbeitet werden
- **Hier**: Graceful Degradation - auch bei fehlenden Feldern funktionsfähig
- **Vorteil**: Robustheit ohne Komplexität

### 4. **Performance in Hochfrequenz-Umgebungen**
- **Finanz**: Millisekunden entscheiden über Profit/Verlust
- **Hier**: Minimaler Overhead durch direkte String-Operationen
- **Vorteil**: Vorhersagbare Performance ohne Garbage Collection

### 5. **Risikomanagement durch Einfachheit**
- **Finanz**: "Keep it simple" reduziert operative Risiken
- **Hier**: 120 Zeilen = weniger Bugs, einfachere Tests
- **Vorteil**: Geringere Wahrscheinlichkeit für Produktionsfehler

## Fazit

**Manchmal ist weniger mehr.** Diese 120-Zeilen-Lösung zeigt, dass komplexe Probleme oft einfache Lösungen haben. In der Finanzindustrie, wo Zuverlässigkeit und Nachvollziehbarkeit kritisch sind, kann ein ultra-simpler Ansatz oft besser sein als eine über-engineerte Lösung.