Einführung und Ziele
====================

Projektüberblick
----------------

Im Projekt **PokéSearch** geht es um den Aufbau einer Webanwendung zur Anzeige und Verwaltung von Pokémon-Informationen. Ziel ist es, ein sauberes Zusammenspiel zwischen Frontend, Backend und einer externen API (PokéAPI) zu realisieren. Die App bietet eine einfache, fokussierte Nutzeroberfläche zur Suche und Anzeige einzelner Pokémon und zur Verwaltung eines persönlichen Albums.

Die Anwendung nutzt folgende Technologien:

- **Spring Boot** für das Backend (API-Anbindung, Authentifizierung, Caching)
- **React** für das Frontend (UI/Interaktion)
- **PostgreSQL** als Datenbank (für User-Infos, gecachete Pokémon und das Album)
- **Docker Compose** zur einfachen lokalen Ausführung

Das Projekt verwendet zur Datenbeschaffung die öffentlich verfügbare **PokéAPI**: https://pokeapi.co/

Funktionalität
--------------

Die Kernfunktionen der Anwendung lassen sich wie folgt zusammenfassen:

- Nutzerregistrierung und Login
- Suchleiste zur Abfrage eines bestimmten Pokémon
- Anzeige relevanter Pokémon-Informationen (Bild, Typ, Fähigkeiten etc.)
- Speicherung des Pokémon im persönlichen Album (persistiert in der Datenbank)
- Wiederanzeige gespeicherter Pokémon im Album
- Caching: bereits abgefragte Pokémon werden aus der lokalen Datenbank geladen, nicht erneut von der externen API

Aufgabenstellung
----------------

Die Aufgabenstellung basiert auf dem Vorschlag, eine Anwendung zu bauen, die auf eine externe API zugreift, die Daten zwischenspeichert (Caching), speichert und visuell verfügbar macht.

Das Projekt erfüllt diese Vorgaben exemplarisch mit dem Konzept „PokéAlbum“: ein persönliches Sammelalbum für Pokémon. Die Anwendung liefert grundlegende Funktionalität, Fokus liegt dabei auf einem stabilen technischen Unterbau und klarer Trennung der Verantwortlichkeiten zwischen den Komponenten. UI-Verfeinerung ist optional.

Qualitätsziele
--------------

Im Folgenden werden zentrale Qualitätsziele definiert, die das Projekt mit entsprechenden Maßnahmen adressiert:

+---------------------+---------------------------------------------------------------+------------------------------------------------------+
| Qualitätskriterium  | Beschreibung                                                  | Maßnahmen                                            |
+=====================+===============================================================+======================================================+
| **Reliability**     | Die Anwendung soll auch bei API-Ausfällen oder Netzproblemen | - Verwendung eines lokalen Caches                    |
|                     | stabil und informativ bleiben.                                | - Unit- und Integrationstests                        |
|                     |                                                               | - Validierung und Fehlerbehandlung im Backend        |
+---------------------+---------------------------------------------------------------+------------------------------------------------------+
| **Portability**     | Die Anwendung soll unabhängig von der Umgebung lauffähig     | - Containerisierung via Docker                      |
|                     | sein und sich einfach starten lassen.                         | - Nutzung von Docker Compose                         |
+---------------------+---------------------------------------------------------------+------------------------------------------------------+
| **Usability**       | Die Anwendung soll leicht verständlich und schnell bedienbar | - Klare UI-Struktur im Frontend                     |
|                     | sein.                                                         | - E2E-Tests mit Playwright zur Sicherung der UX      |
+---------------------+---------------------------------------------------------------+------------------------------------------------------+



