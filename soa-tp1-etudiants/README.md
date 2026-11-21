# TP1 SOA : API HTTP Etudiants (Jakarta EE 10)

API REST qui expose `/api/etudiants` (GET liste, GET par id, POST), avec stockage en memoire.

## Prerequis

- JDK 17 pour compiler (Temurin ou equivalent)
- Apache Maven 3.8.6 ou superieur
- Payara Server 7 Full Platform (necessite Java 21 pour s'executer)
- curl (inclus dans Windows 11)

## Construction

```cmd
mvn clean package
```

Resultat attendu : `BUILD SUCCESS` et le fichier `target\soa-tp1.war`.

## Deploiement (Windows, cmd)

Payara 7 s'execute avec Java 21 :

```cmd
set "JAVA_HOME=C:\chemin\vers\jdk-21"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd C:\chemin\vers\payara7\bin
asadmin.bat start-domain domain1
asadmin.bat deploy --force=true --contextroot soa-tp1 C:\chemin\absolu\vers\soa-tp1-etudiants\target\soa-tp1.war
```

## URL de test

http://localhost:8080/soa-tp1/api/etudiants

## Tests rapides

```cmd
curl -i http://localhost:8080/soa-tp1/api/etudiants
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Aminata\",\"email\":\"aminata@example.org\"}"
curl -i http://localhost:8080/soa-tp1/api/etudiants/1
curl -i http://localhost:8080/soa-tp1/api/etudiants/999
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"\",\"email\":\"incorrect\"}"
```

Les resultats detailles sont dans `docs/tests.md`, le contrat dans `docs/openapi.yaml`.

## Limites

Le stockage est en memoire : les donnees sont perdues a chaque redeploiement ou redemarrage.