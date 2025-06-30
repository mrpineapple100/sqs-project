Randbedingungen
===============

Technische Randbedingungen
--------------------------

**Programmiersprachen und Frameworks**

- **Backend**: Spring Boot (Java 17)
- **Frontend**: React mit Vite
- **Datenbank**: PostgreSQL

**Infrastruktur & Werkzeuge**

- GitHub als Versionsverwaltung und Hosting-Plattform
- GitHub Actions als CI/CD-Pipeline zur Ausführung von Tests und Analysen
- SonarCloud zur statischen Codeanalyse
- Docker zur Containerisierung aller Komponenten
- Docker Compose zur Orchestrierung der Anwendung für lokale Entwicklung und Deployment
- Read the Docs zur öffentlichen Bereitstellung der Projektdokumentation
- arc42 als Template für die Architekturdokumentation

**Vorgaben zur Softwarequalität**

- Testabdeckung im Backend von mindestens 80 % (über Unit- und Integrationstests)
- Dokumentiertes Testkonzept entlang der Testpyramide:
  - Unit-Tests (JUnit)
  - Integrationstests (z. B. gegen PostgreSQL-Testcontainer)
  - End-to-End-Tests (Playwright im Frontend)
  - Sicherheits-Tests auf abgesicherte Endpunkte
  - einfache Lasttests
- Keine offenen statischen Analysefehler bei SonarCloud
- Nutzung ausfallsicherer Patterns für externe Schnittstellen (PokéAPI)

Organisatorische Randbedingungen
--------------------------------

- Das Projekt wurde **alleinverantwortlich** im Rahmen des Moduls **Software-Qualitätssicherung (SQS)** entwickelt.
- Der **Abgabetermin ist der 01.07.2025**.
- Die Projektlaufzeit betrug ein Semester.
- Die Betreuung erfolgte durch den Dozenten während regelmäßiger Übungsphasen.
- Das Repository musste öffentlich auf **GitHub** gehostet werden – andere Plattformen waren ausgeschlossen.
- Die Verwendung von ChatGPT zur Unterstützung war erlaubt, jedoch sollte die Umsetzung eigenständig erfolgen.
- Die Projektarbeit ist Bestandteil der **Gesamtbewertung im Modul SQS**.

