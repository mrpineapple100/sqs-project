Bausteinsicht
=============

Whitebox Gesamtsystem
----------------------

Das Gesamtsystem besteht aus mehreren miteinander verbundenen Komponenten, die gemeinsam die Anwendung **PokéSearch** bilden. Dieses Übersichtsdiagramm (ARK – Architekturkontext, Bausteine, Beziehungen) bietet einen Überblick über die Interaktionen und Abhängigkeiten zwischen den einzelnen Modulen.

.. Hinweis::
   Eine grafische Darstellung (C4-Modell) wird zusätzlich als Teil der Abgabe mit Structurizr erstellt und hier in finaler Version eingebunden.

Enthaltene Bausteine
---------------------

- **Spring Boot Backend** – REST-API, Authentifizierung, Caching, Datenzugriff
- **React Frontend** – Benutzeroberfläche mit Login, Suchfunktion und Album
- **PostgreSQL Datenbank** – Speicherung von Userdaten, gecacheten Pokémon und Album-Einträgen
- **Docker Container** – Isolierte Umgebung für Backend, Frontend und Datenbank
- **GitHub Actions** – CI/CD-Pipeline für Tests und Deployment
- **Playwright** – End-to-End-Tests des Frontends
- **SonarCloud Analyse** – Statische Codeanalyse für das Backend
- **OpenAPI / Swagger** – Automatische API-Dokumentation

Wichtige Schnittstellen
------------------------

- **RESTful API** zwischen Backend und Frontend (JSON über HTTP)
- **JDBC/JPA** zwischen Backend und PostgreSQL
- **Docker-Netzwerk** zur Kommunikation zwischen den Containern
- **GitHub Actions** als CI/CD-Controller
- **Swagger UI** zur API-Dokumentation im Backend
- **SonarCloud** zur Analyse über GitHub-Integration

Spring Boot Backend
--------------------

**Zweck / Verantwortung**

Das Spring Boot Backend ist verantwortlich für:

- Bereitstellung der REST-API
- Authentifizierung und Autorisierung
- Aufruf der externen PokéAPI
- Caching der Ergebnisse in der PostgreSQL-Datenbank
- Verwaltung des persönlichen Pokémon-Albums

**Schnittstellen**

- REST-API-Endpunkte für Frontend-Kommunikation
- Zugriffsschicht (Repository) zur PostgreSQL-Datenbank
- Anbindung an externe Schnittstelle: `https://pokeapi.co/api/v2/pokemon/{pokemonName}`

**Erfüllte Anforderungen**

- Kapselung der Geschäftslogik
- Implementierung von Caching, Sicherheit und Datenspeicherung
- Automatisierte Tests zur Sicherstellung der Funktionalität
- Konfiguration über Docker & GitHub Actions

**Offene Punkte / Risiken**

- Potenzielle API-Ratenlimits der PokéAPI
- Skalierbarkeit bei erhöhter Nutzeranzahl (z. B. parallele Caching-Requests)

React Frontend
---------------

**Zweck / Verantwortung**

Das React-Frontend stellt die grafische Benutzeroberfläche bereit. Es umfasst folgende Kernkomponenten:

- **Main**: Einstiegspunkt der Anwendung
- **App**: Zentrale Router- und UI-Komponente
- **LandingPage**: Startseite für nicht eingeloggte Benutzer
- **SearchPage**: Seite mit der Pokémon-Suchfunktion
- **AlbumPage**: Anzeige aller gespeicherten Pokémon im Album
- **Login/Register**: Authentifizierungskomponenten

**Schnittstellen**

- REST-API-Aufrufe an das Backend (z. B. `/api/pokemon`, `/api/album`)
- Zugriff auf JWT aus dem LocalStorage für authentifizierte Requests
- Interaktion mit React State / Context zur Sitzungsverwaltung

**Erfüllte Anforderungen**

- Responsive und minimalistische UI mit Material UI
- Umsetzung der Authentifizierung mit Formulareingabe
- Implementierung einer einfachen, intuitiven Nutzerführung

**Offene Punkte / Risiken**

- Offline-Verfügbarkeit aktuell nicht vorgesehen
- Kein State-Persist über Sessions hinweg außer durch JWT



