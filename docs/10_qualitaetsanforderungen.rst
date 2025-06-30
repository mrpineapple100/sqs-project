Qualitätsanforderungen
=======================

Das Projekt PokéSearch wurde mit besonderem Fokus auf Wartbarkeit, Testbarkeit und Benutzerfreundlichkeit umgesetzt. Zur Sicherstellung der Qualität wurden moderne Tools und Strategien eingesetzt.

**Playwright**

Für End-to-End-Tests wurde **Playwright** verwendet. Damit werden komplette Nutzerszenarien automatisiert getestet, z. B. Login, Pokémon-Suche und das Speichern ins Album. Die Tests wurden lokal entwickelt und in die GitHub Actions Pipeline integriert, um bei jedem Commit automatisch ausgeführt zu werden.

**SonarCloud**

Zur automatisierten Überprüfung der Codequalität wurde **SonarCloud** integriert. Alle gemeldeten Verstöße und Schwächen im Code wurden behoben, um einen sauberen, wartbaren Code zu gewährleisten. Die Testabdeckung im Backend liegt bei mindestens 80 %.

**OpenAPI**

Die **API-Dokumentation** erfolgt über SpringDoc/OpenAPI. Dies erhöht die Transparenz der Schnittstellen und erleichtert sowohl Entwicklung als auch Test und Wartung.

Qualitätsbaum
-------------

.. image:: images/10_quality_tree.png
   :alt: Qualitätsbaum des Projekts

Qualitätsszenarien
------------------

+----------------------+------------------------------------------------------+---------------------------------------------------------------+
| **Attribut**         | **Szenario**                                         | **Maßnahme**                                                  |
+======================+======================================================+===============================================================+
| **Portability**      | Nutzungsszenario: Die Anwendung läuft in Containern. | Verwendung von Docker & Docker Compose                        |
|                      | Änderungsszenario: Wechsel der Infrastruktur.       | Containerisierte Bereitstellung & plattformunabhängige Tests |
+----------------------+------------------------------------------------------+---------------------------------------------------------------+
|                      | Nutzungsszenario: Das Frontend funktioniert in      | Tests mit Playwright auf verschiedenen Browsern               |
|                      | unterschiedlichen Browsern gleich.                 |                                                               |
|                      | Änderungsszenario: Neue Browser-Versionen.          | Regelmäßige Updates & Playwright-Tests                        |
+----------------------+------------------------------------------------------+---------------------------------------------------------------+
| **Usability**        | Nutzungsszenario: Pokémon-Suche ist intuitiv.       | Minimalistische UI mit klarer Eingabemaske                    |
|                      | Änderungsszenario: Nutzerfeedback zur UI.           | Feedback erfassen & Änderungen mit Playwright validieren      |
+----------------------+------------------------------------------------------+---------------------------------------------------------------+
|                      | Nutzungsszenario: Album ist einfach erreichbar.     | Klare Navigation und Zustandsanzeige im Frontend              |
+----------------------+------------------------------------------------------+---------------------------------------------------------------+
| **Reliability**      | Nutzungsszenario: Die Anwendung reagiert stabil     | Unit-Tests, Integrationstests, E2E-Tests mit Playwright       |
|                      | auf korrekte & fehlerhafte Anfragen.               |                                                               |
|                      | Änderungsszenario: Neuer Feature-Code wird getestet.| GitHub Actions mit automatisierter Testausführung             |
+----------------------+------------------------------------------------------+---------------------------------------------------------------+
|                      | Nutzungsszenario: Caching funktioniert wie erwartet.| Caching-Verhalten durch Integrationtests abgedeckt            |
|                      | Änderungsszenario: Änderungen in Caching-Logik.     | Testszenarien regelmäßig anpassen                             |
+----------------------+------------------------------------------------------+---------------------------------------------------------------+

