Lösungsstrategie
================

Das Projekt PokéSearch wurde mit dem Ziel entwickelt, eine schlanke Anwendung bereitzustellen, mit der Benutzer gezielt nach einzelnen Pokémon suchen und diese in einem persönlichen Album speichern können. Dabei wird ein lokaler Cache implementiert, um wiederholte API-Zugriffe zu vermeiden und die Effizienz zu steigern.

Technologiewahl
---------------

Für die Umsetzung wurde bewusst auf moderne, bewährte Technologien gesetzt:

- **Spring Boot (Java 17)** für das Backend, um robuste REST-Endpunkte und einfache Integration mit externen Services zu ermöglichen
- **React mit Vite** für ein schnelles, komponentenbasiertes Frontend
- **PostgreSQL** als relationale Datenbank für Nutzer- und Pokémon-Daten
- **Docker** und **Docker Compose** zur Containerisierung und lokalen Ausführung
- **GitHub Actions** für Continuous Integration
- **SonarCloud** zur Analyse der Codequalität
- **Read the Docs** zur Veröffentlichung der Projektdokumentation
- **OpenAPI / Swagger UI** zur automatischen API-Dokumentation

Architekturansatz
-----------------

Die Anwendung folgt einem klaren Schichtenmodell mit getrennten Verantwortlichkeiten:

- **Präsentationsschicht**: React-Frontend mit Login-Funktionalität, Suchfeld und Ergebnisanzeige
- **Business-Logik**: Spring-Boot-Service-Schicht mit Caching-Logik, API-Aufrufen und Authentifizierung
- **Persistenzschicht**: PostgreSQL-Datenbank mit JPA-gestütztem Zugriff auf Benutzer, Album und Pokémon-Einträge

Ablauf und Vorgehen
-------------------

Die Entwicklung begann mit dem Aufbau und der vollständigen Umsetzung des Backends. Nach dem Aufsetzen der REST-Endpunkte wurde mithilfe von Postman und Integrationstests die Korrektheit sichergestellt. Parallel dazu wurden grundlegende Unit-Tests mit JUnit geschrieben.

Anschließend wurde das React-Frontend implementiert. Nach erfolgreicher Authentifizierung kann der Nutzer über eine Suchleiste ein Pokémon abfragen. Bei erfolgreicher Antwort aus dem Backend werden die Informationen im Frontend dargestellt.

Die Integration von Frontend und Backend erfolgte iterativ. Nach ersten erfolgreichen End-to-End-Läufen wurden weitere Validierungen implementiert, darunter:

- Speicherung des Pokémon im Album (nur bei Authentifizierung)
- Vermeidung mehrfacher API-Zugriffe durch Cache-Nutzung im Backend

Infrastruktur und CI/CD
------------------------

Zur Vereinfachung des Deployments wurde das gesamte Projekt dockerisiert. Die Anwendung lässt sich lokal mit einem einzigen Befehl starten (`docker compose up`). Dabei werden:

- Backend
- Frontend
- PostgreSQL

als separate Services ausgeführt.

Eine **GitHub Actions**-Pipeline übernimmt automatisiert:

- Unit- und Integrationstests
- statische Codeanalyse mit SonarCloud
- optionales Deployment

Teststrategie
-------------

Die Anwendung implementiert eine grundlegende Testpyramide:

- **Unit-Tests** mit JUnit für die Business-Logik
- **Integrationstests** mit Spring Boot und Testcontainers für DB-Zugriffe
- **End-to-End-Tests** (Playwright) zur Prüfung des Nutzerflows
- **Sicherheits-Tests** durch gezielte Integrationstests auf Login-geschützte Endpunkte

OpenAPI
-------

Die API-Dokumentation erfolgt automatisch durch Integration von **Swagger/OpenAPI**, lokal erreichbar unter:

::

   http://localhost:8080/swagger-ui/index.html

Ergebnis
--------

Durch diesen strukturierten, iterativen Entwicklungsprozess wurde eine moderne, wartbare und containerisierte Anwendung geschaffen, die zuverlässig Pokémon-Daten verarbeitet, effizient cached und in einem benutzerfreundlichen UI-Kontext darstellt.



