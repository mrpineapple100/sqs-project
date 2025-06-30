Verteilungssicht
================



Begründung für die Architekturaufteilung
----------------------------------------

Die Architektur von **PokéSearch** folgt bewährten Prinzipien moderner Webentwicklung und trennt die Hauptkomponenten sauber über Docker-Container. Dadurch ergeben sich mehrere Vorteile:

**Skalierbarkeit und Flexibilität**

- Frontend und Backend laufen in separaten Containern.
- Komponenten können unabhängig voneinander skaliert oder ausgetauscht werden.
- Zukünftige Erweiterungen (z. B. Monitoring, Logging, eigene API-Gateways) können ohne Umstrukturierung ergänzt werden.

**Entkopplung und Wartbarkeit**

- Die PokéAPI ist ein externer Dienst und muss nicht selbst gehostet werden.
- PostgreSQL läuft isoliert im eigenen Container mit persistentem Volume.
- Die Trennung ermöglicht einfaches Debugging und gezielte Updates einzelner Teile der Anwendung.

**Sicherheit**

- Das Backend ist das einzige Element mit direktem Zugriff auf die externe PokéAPI und die PostgreSQL-Datenbank.
- Nur das Backend stellt REST-Endpunkte für das Frontend bereit.
- Durch Docker-Netzwerke und geeignete Port-Freigaben werden Zugriffsmöglichkeiten kontrolliert.

**Technologische Vielfalt und Integration**

- PostgreSQL (Open Source) als robuste Datenbank
- Spring Boot und React als moderne Kerntechnologien
- Externe Dienste (PokéAPI, GitHub, SonarCloud) werden gezielt integriert, ohne monolithische Koppelung

**Microservices-Prinzipien**

- Auch wenn PokéSearch kein klassisches Microservices-System ist, folgt es vielen Microservice-Prinzipien:
  - Zustandsloses Backend
  - Trennung von Daten und Logik
  - Kommunikation über klar definierte REST-Schnittstellen
  - Infrastruktur via Docker Compose orchestriert

Docker Compose Übersicht
-------------------------

Die Anwendung wird über eine `docker-compose.yml` orchestriert. Das Ziel ist, die gesamte Anwendung mit einem einzigen Befehl (`docker compose up`) startbar zu machen.

::

   +-------------------+         +-------------------+         +---------------------------+
   |                   |         |                   |         |                           |
   |      db           |<--------|      backend       |<--------|       frontend            |
   |  postgres:15      |         |  pokesearch_back   |         |  pokesearch_front         |
   |  Port: 5432       |         |  Ports: 8080       |         |  Port: 3000               |
   |  Volume: db_data  |         |  Depends on: db    |         |  Build: ./frontend        |
   |                   |         |                   |         |  Depends on: backend      |
   +-------------------+         +-------------------+         +---------------------------+

**Komponentenbeschreibung**

- **db**
  - Container: `postgres:15`
  - Port: `5432`
  - Datenvolumen: `db_data`
- **backend**
  - Container: `pokesearch_back`
  - Ports: `8080`
  - Zugriff auf: PokéAPI, PostgreSQL
- **frontend**
  - Container: `pokesearch_front`
  - Port: `3000`
  - Baut aus lokalem `frontend/`-Verzeichnis

**Startbefehl**

::

   docker compose up --build


