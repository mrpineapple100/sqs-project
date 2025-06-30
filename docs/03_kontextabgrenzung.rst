Kontextabgrenzung
=================

Fachlicher Kontext
------------------

Der Benutzer nutzt die PokéSearch-Webanwendung, um nach Informationen zu einem bestimmten Pokémon zu suchen. Diese werden entweder:

- aus der lokalen PostgreSQL-Datenbank geladen (wenn bereits zwischengespeichert),
- oder bei erstmaliger Abfrage von der externen PokéAPI geholt und anschließend in der Datenbank gespeichert (Caching).

Nach erfolgreicher Suche kann der Benutzer das Pokémon seinem persönlichen Sammelalbum hinzufügen. Gespeicherte Pokémon sind jederzeit wieder abrufbar.

Zugriff auf die Album-Funktion sowie das Speichern eines Pokémon ist ausschließlich für eingeloggte Benutzer möglich.

Externe Schnittstelle: PokéAPI
------------------------------

Die Anwendung nutzt die öffentlich zugängliche REST-API [PokéAPI](https://pokeapi.co/) zur Beschaffung von Pokémon-Daten.

**Beispiel-Endpunkt zur Pokémon-Suche:**

::

   GET https://pokeapi.co/api/v2/pokemon/{pokemonName}

**Parameter:**

- ``pokemonName``: Pfadparameter, z. B. „pikachu“ – bestimmt das Pokémon, das abgefragt werden soll.

**Antworten:**

- ``200 OK`` – Erfolgreiche Antwort mit JSON-Daten zum Pokémon
- ``404 Not Found`` – Pokémon existiert nicht oder wurde falsch geschrieben

**Media Type:** application/json

Die API liefert Informationen zu Art, Fähigkeiten, Typen, Basiswerten, Bildern und mehr.

Technischer Kontext
-------------------

Die Anwendung folgt einem klassischen Drei-Schichten-Modell mit sauberer Trennung von Frontend, Backend und Persistenz.

**Frontend**

- React mit Vite
- Material UI Komponenten
- Kommunikation mit Backend über REST-Endpunkte

**Backend**

- Spring Boot (Java 17)
- REST-API mit abgesicherten und öffentlichen Endpunkten
- Service-Schicht mit Caching-Logik
- Swagger UI zur Dokumentation (lokal unter http://localhost:8080/swagger-ui/index.html)

**Persistenz**

- PostgreSQL (v15) zur Speicherung von:
  - Benutzerinformationen
  - bereits abgefragten (gecacheten) Pokémon
  - Sammelalbum-Einträgen der Benutzer

**Testing**

- Unit-Tests mit JUnit (für Services und Controller)
- Integrationstests mit Spring Boot Test und Testcontainers (PostgreSQL)
- End-to-End-Tests im Frontend (Playwright vorbereitet)
- Sicherheitsrelevante Endpunkte werden durch gezielte Integrationstests überprüft

**Build- und Laufzeitumgebung**

- Docker-Containerisierung für alle Komponenten
- Docker Compose für einfachen Projektstart mit nur einem Befehl
- GitHub Actions zur CI/CD:
  - Automatisierter Build
  - Testausführung
  - Statische Codeanalyse über SonarCloud



