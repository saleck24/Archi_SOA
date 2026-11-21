# TP1 SOA : API HTTP Etudiants (Jakarta EE 10)

API REST complète exposant `/api/etudiants` avec les opérations CRUD :
- `GET /api/etudiants` : lister tous les étudiants
- `GET /api/etudiants/{id}` : consulter un étudiant par son identifiant
- `POST /api/etudiants` : créer un nouvel étudiant (statut 201 Created + en-tête Location)
- `PUT /api/etudiants/{id}` : mise à jour complète (nom et email)
- `PATCH /api/etudiants/{id}` : mise à jour partielle (nom ou email ou les deux)
- `DELETE /api/etudiants/{id}` : supprimer un étudiant (statut 204 No Content)

## Prerequis

- JDK 17 pour compiler (Temurin ou équivalent)
- Apache Maven 3.8.6 ou supérieur
- Payara Server 7 Full Platform (nécessite Java 21 pour s'exécuter)
- curl (inclus dans Windows 11)

## Construction

```cmd
mvn clean package
```

Résultat attendu : `BUILD SUCCESS` et le fichier `target\soa-tp1.war`.

## Deploiement (Windows, cmd)

Payara 7 s'exécute avec Java 21 :

```cmd
set "JAVA_HOME=C:\chemin\vers\jdk-21"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd C:\chemin\vers\payara7\bin
asadmin.bat start-domain domain1
asadmin.bat deploy --force=true --contextroot soa-tp1 C:\chemin\absolu\vers\soa-tp1-etudiants\target\soa-tp1.war
```

## URL de base de l'API

http://localhost:8080/soa-tp1/api/etudiants

## Tests rapides des opérations HTTP

```cmd
:: 1. Lister les étudiants (initialement vide -> 200 OK [])
curl -i http://localhost:8080/soa-tp1/api/etudiants

:: 2. Créer un étudiant (-> 201 Created + Location)
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Aminata\",\"email\":\"aminata@example.org\"}"

:: 3. Consulter l'étudiant créé (-> 200 OK)
curl -i http://localhost:8080/soa-tp1/api/etudiants/1

:: 4. Mise à jour complète PUT (-> 200 OK)
curl -i -X PUT http://localhost:8080/soa-tp1/api/etudiants/1 -H "Content-Type: application/json" -d "{\"nom\":\"Aminata Diop\",\"email\":\"aminata.diop@example.org\"}"

:: 5. Mise à jour partielle PATCH (-> 200 OK)
curl -i -X PATCH http://localhost:8080/soa-tp1/api/etudiants/1 -H "Content-Type: application/json" -d "{\"email\":\"nouvel.email@example.org\"}"

:: 6. Supprimer un étudiant DELETE (-> 204 No Content)
curl -i -X DELETE http://localhost:8080/soa-tp1/api/etudiants/1

:: 7. Vérifier la suppression GET (-> 404 Not Found)
curl -i http://localhost:8080/soa-tp1/api/etudiants/1
```

Les résultats détaillés et captures sont documentés dans `docs/tests.md`, le contrat complet dans `docs/openapi.yaml`.

## Limites

Le stockage est en mémoire (`ConcurrentHashMap`) : les données sont perdues à chaque redéploiement ou redémarrage du serveur.
