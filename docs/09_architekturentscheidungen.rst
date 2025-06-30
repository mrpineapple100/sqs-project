Architekturentscheidungen
==========================

Die Architekturentscheidungen für das Projekt **PokéSearch** wurden bewusst getroffen, um eine robuste, wartbare und containerisierte Anwendung zu ermöglichen, die sich gut testen, erweitern und betreiben lässt.

Technologiewahl
---------------

**Spring Boot**

- Die neueste stabile Version wurde verwendet, um aktuelle Features und Sicherheitsupdates zu nutzen.
- Vorteile: einfache Konfiguration, breite Community, integrierte Unterstützung für REST, Security, Datenbankzugriffe (JPA), OpenAPI-Dokumentation.

**React mit Vite**

- Für das Frontend wurde React in Kombination mit Vite gewählt – Vite bietet schnellen Build und moderne Entwicklungsumgebung.
- Vorteile: Komponentenbasiertes UI, modernes Tooling, gute Integration mit Playwright für E2E-Tests.

**PostgreSQL**

- Als relationale Datenbank wurde PostgreSQL verwendet, da es stabil, leistungsfähig und mit Spring Boot hervorragend integrierbar ist.
- Bekannte Technologie mit robuster Unterstützung für JSON, Constraints und Transaktionen.

Schichtenmodell
---------------

Die Anwendung basiert auf einem klassischen Drei-Schichten-Modell:

- **Präsentationsschicht**: React-Frontend mit Routing, State-Management und Authentifizierung
- **Geschäftslogik**: Service-Schicht im Spring Boot Backend (z. B. Caching, Fehlerbehandlung, Datenvalidierung)
- **Datenzugriffsschicht**: JPA-Repositories zur Interaktion mit PostgreSQL

Diese Trennung erhöht:

- Lesbarkeit und Testbarkeit des Codes
- Austauschbarkeit einzelner Komponenten
- Klarheit in Verantwortlichkeiten

Entwicklungsprozess
--------------------

**Backend-First-Ansatz**

- Die Entwicklung startete mit dem Backend, insbesondere mit REST-Endpunkten und der Caching-Logik.
- Die Anbindung an die PokéAPI wurde vorgezogen, um früh realistische Daten testen zu können.
- Unit- und Integrationstests wurden parallel mit JUnit entwickelt.

**Frontend-Entwicklung**

- Nach stabiler Backend-Basis wurde das React-Frontend entwickelt.
- Integration der Suche, Authentifizierung und Album-Komponenten erfolgte iterativ.
- Erste manuelle Tests mit Postman wurden durch automatisierte E2E-Tests mit Playwright ergänzt.

Testing
-------

**Teststrategie**

- **JUnit** für Unit- und Integrationstests im Backend
- **Spring Boot Test + Testcontainers** zur Simulation echter Datenbankzugriffe
- **Playwright** für browserbasierte End-to-End-Tests
- Postman wurde initial für manuelles Testen verwendet

**Abgedeckte Testszenarien**

- Login, Registrierung
- Caching-Verhalten bei Pokémon-Suche
- Fehlerbehandlung bei ungültigen Eingaben
- Album-Funktionen (Hinzufügen, Anzeigen)

Deployment und Containerisierung
--------------------------------

**Docker**

- Alle Komponenten (Frontend, Backend, PostgreSQL) sind dockerisiert
- Stellt eine einheitliche, reproduzierbare Umgebung sicher

**Docker Compose**

- Ermöglicht das Starten aller Services mit einem einzigen Befehl
- Wird für lokale Entwicklung und Testing verwendet

**GitHub Actions**

- CI/CD-Pipeline baut automatisch:
  - Docker-Images
  - Führt Tests aus (JUnit, Playwright)
  - Lintet Dockerfiles
  - Führt SonarCloud-Analyse durch
- Dient zur kontinuierlichen Qualitäts- und Funktionssicherung

Fazit
-----

Die gewählten Technologien und Strukturen ermöglichen eine stabile, nachvollziehbare und wartbare Architektur, die alle Anforderungen des SQS-Moduls erfüllt und einfach erweitert werden kann.

