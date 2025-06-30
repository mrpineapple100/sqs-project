Laufzeitsicht
=============

Szenario 1: Erfolgreiche Pokémon-Suche (Cache Miss)
---------------------------------------------------

**Ablaufbeschreibung**

1. **Benutzeranfrage im Frontend**  
   Der Benutzer öffnet die React-Anwendung und gibt in der Suchleiste den Namen eines Pokémon ein (z. B. „pikachu“).

2. **Anfrage an das Backend**  
   Das Frontend sendet eine HTTP GET-Anfrage an das Spring Boot Backend an den Endpunkt:

   ::

      GET /api/pokemon/pikachu

3. **Überprüfung des Caches im Backend**  
   Das Backend prüft, ob Informationen zu „pikachu“ bereits in der PostgreSQL-Datenbank vorhanden sind.

4. **Cache-Miss**  
   Die Informationen befinden sich **nicht** in der lokalen Datenbank.

5. **Anfrage an die externe PokéAPI**  
   Das Backend ruft die Daten bei der PokéAPI ab:

   ::

      GET https://pokeapi.co/api/v2/pokemon/pikachu

6. **Empfang und Speicherung der Daten**  
   Das Backend erhält die JSON-Antwort von der PokéAPI, speichert sie in der PostgreSQL-Datenbank (Caching).

7. **Antwort an das Frontend**  
   Das Backend sendet die erhaltenen Pokémon-Daten als JSON an das Frontend zurück.

8. **Anzeige im Frontend**  
   Die Pokémon-Informationen werden im UI dargestellt (Name, Typ, Bild, Fähigkeiten etc.).





Szenario 2: Erfolgreiche Pokémon-Suche (Cache Hit)
---------------------------------------------------

**Ablaufbeschreibung**

1. **Benutzeranfrage im Frontend**  
   Ein Benutzer gibt im Suchfeld erneut „pikachu“ ein.

2. **Anfrage an das Backend**  
   Anfrage wird an denselben Endpunkt gestellt:

   ::

      GET /api/pokemon/pikachu

3. **Cache-Hit im Backend**  
   Das Backend erkennt, dass die Daten zu „pikachu“ bereits in der PostgreSQL-Datenbank vorhanden sind.

4. **Antwort an das Frontend**  
   Die gespeicherten Daten werden direkt zurückgegeben – **kein externer API-Aufruf notwendig**.

5. **Anzeige im Frontend**  
   Das Frontend zeigt die bekannten Pokémon-Infos aus dem Cache.



Szenario 3: Fehlgeschlagene Pokémon-Suche (falscher Name)
----------------------------------------------------------

**Ablaufbeschreibung**

1. **Benutzeranfrage im Frontend**  
   Ein Benutzer gibt einen ungültigen Pokémon-Namen ein (z. B. „pikachuu“).

2. **Anfrage an das Backend**

   ::

      GET /api/pokemon/pikachuu

3. **Cache-Miss im Backend**  
   Das Backend findet keine Einträge in der lokalen Datenbank.

4. **Anfrage an die PokéAPI**

   ::

      GET https://pokeapi.co/api/v2/pokemon/pikachuu

5. **Fehlerantwort der PokéAPI**  
   Die PokéAPI antwortet mit:

   ::

      404 Not Found

6. **Fehlerbehandlung im Backend**  
   Das Backend gibt eine strukturierte Fehlerantwort zurück.

7. **Anzeige im Frontend**  
   Die Anwendung zeigt dem Benutzer eine passende Fehlermeldung, z. B.:

   ::

      „Das eingegebene Pokémon existiert nicht.“




