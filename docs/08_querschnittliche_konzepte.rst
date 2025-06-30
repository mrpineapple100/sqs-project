Querschnittliche Konzepte
==========================

Dieses Kapitel beschreibt übergreifende Architekturkonzepte, die im gesamten System eine Rolle spielen und nicht an eine einzelne Schicht gebunden sind.

UML Diagramm des Backends
--------------------------

.. image:: images/08_backend_uml.png
   :alt: Klassendiagramm oder Komponentenübersicht des Spring Boot Backends

Dieses Diagramm zeigt die wichtigsten Klassen und deren Beziehungen im Backend – z. B. Controller, Services, Repositories und Datenmodelle für Pokémon, User und Album.

PostgreSQL-Datenbank
---------------------

.. image:: images/08_db_schema.png
   :alt: Vereinfachtes ER-Diagramm oder SQL-Schema der PostgreSQL-Datenbank

Die PostgreSQL-Datenbank enthält u. a. folgende Tabellen:

- `users`: Enthält Benutzerinformationen inkl. Login-Daten (hashed)
- `pokemon`: Enthält gecachete Pokémon-Daten (z. B. Name, Typ, Bild, Fähigkeiten)
- `album_entries`: Verknüpft Benutzer mit gespeicherten Pokémon (User-Pokémon-Beziehung)

GitHub Actions CI/CD-Pipeline
-----------------------------

Die Anwendung nutzt **GitHub Actions**, um eine automatisierte Build-, Test- und Analysepipeline bereitzustellen. Nach jedem Commit wird der Code validiert und in Docker-Images überführt.

.. image:: images/08_github_actions_pipeline.png
   :alt: Visualisierung der GitHub Actions-Pipeline

**Beispielstruktur (vereinfacht)**

::

   +------------------------------+             +-----------------------------------+
   |                              |             |                                   |
   |       Lint-Dockerfiles       |------------>| Build-Frontend-and-Backend-and-   |
   |   - Lint backend Dockerfile  |             | push-images                       |
   |   - Lint frontend Dockerfile |             |   - Build backend with Maven      |
   |                              |             |   - Build frontend with npm       |
   |                              |             |   - Push Docker images to GHCR    |
   +------------------------------+             +-----------------------------------+
                                                    |              |
                                                    V              V
                                         +-------------------+  +-------------------+
                                         |                   |  |                   |
                                         |   Backend-Tests   |  |   Playwright E2E  |
                                         |   - Run JUnit     |  |   - Frontend E2E  |
                                         |   - Integration   |  |   - Auth Flow etc.|
                                         +-------------------+  +-------------------+

Hinweis: Die `README.md` ist vom automatisierten Linting ausgenommen.

Testing mit Playwright
-----------------------

Für End-to-End-Tests kommt **[Playwright](https://playwright.dev/)** zum Einsatz. Playwright simuliert echte Benutzerinteraktionen im Browser und prüft zentrale Abläufe wie:

- Login und Registrierung
- Pokémon-Suche
- Album-Funktion (Hinzufügen / Anzeigen)
- Fehlerbehandlung bei ungültigen Anfragen

Playwright-Tests können lokal oder über GitHub Actions ausgeführt werden.

SonarCloud Codeanalyse
------------------------

Das Projekt ist in **SonarCloud** integriert, um automatisiert:

- statische Codeanalyse
- Clean-Code-Verstöße
- Testabdeckung (Coverage)

zu prüfen.

Ein Beispiel-Link zu SonarCloud:  
`https://sonarcloud.io/project/overview?id=pokesearch`

> Die Testabdeckung im Backend liegt bei ≥ 80 % (Unit + Integration).

OpenAPI-Dokumentation
----------------------

Das Spring Boot Backend nutzt **SpringDoc OpenAPI**, um die REST-Endpunkte automatisch zu dokumentieren.

Lokaler Zugriff via Swagger UI:

::

   http://localhost:8080/swagger-ui/index.html

Die OpenAPI-Dokumentation ermöglicht:

- schnelle Sicht auf vorhandene Endpunkte
- automatisierte Tests (z. B. via Postman Import)
- Validierung der Parameter und Response-Struktur

