# TP1 SOA : API HTTP Jakarta EE 10 (Etudiants, Departements, Modules)

API REST complète exposant trois ressources avec les opérations CRUD complètes (GET, POST, PUT, PATCH, DELETE) :
- `/api/etudiants` : gestion des étudiants
- `/api/departements` : gestion des départements
- `/api/modules` : gestion des modules d'enseignement

## Prerequis

- JDK 17 pour compiler (Temurin ou équivalent)
- Apache Maven 3.8.6 ou supérieur
- Payara Server 7 Full Platform (nécessite Java 21 pour s'exécuter)
- curl (inclus nativement dans Windows)

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

## URL de base des ressources

- Étudiants : `http://localhost:8080/soa-tp1/api/etudiants`
- Départements : `http://localhost:8080/soa-tp1/api/departements`
- Modules : `http://localhost:8080/soa-tp1/api/modules`

## Exemples rapides de tests curl (Windows CMD)

```cmd
:: 1. Lister les étudiants (tableau vide au démarrage)
curl -i http://localhost:8080/soa-tp1/api/etudiants

:: 2. Créer un étudiant (201 Created + Location)
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Aminata\",\"email\":\"aminata@example.org\"}"

:: 3. Créer un département (201 Created + Location)
curl -i -X POST http://localhost:8080/soa-tp1/api/departements -H "Content-Type: application/json" -d "{\"nom\":\"Informatique\",\"code\":\"INFO\"}"

:: 4. Créer un module (201 Created + Location)
curl -i -X POST http://localhost:8080/soa-tp1/api/modules -H "Content-Type: application/json" -d "{\"code\":\"SOA\",\"nom\":\"Architecture Orientee Services\",\"coefficient\":4}"

:: 5. Mettre à jour un module avec PUT
curl -i -X PUT http://localhost:8080/soa-tp1/api/modules/1 -H "Content-Type: application/json" -d "{\"code\":\"SOA-ADV\",\"nom\":\"SOA et Microservices\",\"coefficient\":5}"

:: 6. Supprimer un département avec DELETE (204 No Content)
curl -i -X DELETE http://localhost:8080/soa-tp1/api/departements/1
```

Tous les tests détaillés et les codes HTTP sont documentés dans [`docs/tests.md`](docs/tests.md).  
Le contrat d'interface complet est disponible dans [`docs/openapi.yaml`](docs/openapi.yaml).
