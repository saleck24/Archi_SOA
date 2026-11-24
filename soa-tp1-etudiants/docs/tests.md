# Tests de l'API REST (Windows CMD / Terminal)

Toutes les requêtes `curl` ci-dessous sont formatées sur une seule ligne avec des guillemets échappés `\"` pour être **100% compatibles avec le terminal Windows (`cmd.exe`)**, PowerShell et Git Bash.

- Base URL globale : `http://localhost:8080/soa-tp1/api`

---

# SECTION 1 : Tests Ressource Étudiant (`/api/etudiants`)

Base URL : `http://localhost:8080/soa-tp1/api/etudiants`

### T1 : GET collection avant création
```cmd
curl -i http://localhost:8080/soa-tp1/api/etudiants
```
- **Code HTTP attendu** : 200 OK
- **Extrait de réponse** : `[]`
- **Verdict** : Réussi

### T2 : POST création d'un étudiant valide
```cmd
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Aminata\",\"email\":\"aminata@example.org\"}"
```
- **Code HTTP attendu** : 201 Created
- **En-tête Location** : `http://localhost:8080/soa-tp1/api/etudiants/1`
- **Corps de réponse** : `{"email":"aminata@example.org","id":1,"nom":"Aminata"}`
- **Verdict** : Réussi

### T3 : GET consultation de l'étudiant créé (ID 1)
```cmd
curl -i http://localhost:8080/soa-tp1/api/etudiants/1
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"email":"aminata@example.org","id":1,"nom":"Aminata"}`
- **Verdict** : Réussi

### T4 : GET ressource absente (ID 999)
```cmd
curl -i http://localhost:8080/soa-tp1/api/etudiants/999
```
- **Code HTTP attendu** : 404 Not Found
- **Corps de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : Réussi

### T5 : POST validation - nom vide et email invalide
```cmd
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"\",\"email\":\"incorrect\"}"
```
- **Code HTTP attendu** : 400 Bad Request
- **Corps de réponse** : `{"erreur":"DONNEES_INVALIDES","message":"Nom ou email invalide"}`
- **Verdict** : Réussi

### T6 : PUT mise à jour complète (remplacement nom et email)
```cmd
curl -i -X PUT http://localhost:8080/soa-tp1/api/etudiants/1 -H "Content-Type: application/json" -d "{\"nom\":\"Aminata Diop\",\"email\":\"aminata.diop@example.org\"}"
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"email":"aminata.diop@example.org","id":1,"nom":"Aminata Diop"}`
- **Verdict** : Réussi

### T7 : PUT étudiant inexistant (ID 999)
```cmd
curl -i -X PUT http://localhost:8080/soa-tp1/api/etudiants/999 -H "Content-Type: application/json" -d "{\"nom\":\"Inconnu\",\"email\":\"inconnu@example.org\"}"
```
- **Code HTTP attendu** : 404 Not Found
- **Corps de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : Réussi

### T8 : PUT validation - données invalides
```cmd
curl -i -X PUT http://localhost:8080/soa-tp1/api/etudiants/1 -H "Content-Type: application/json" -d "{\"nom\":\"\",\"email\":\"invalide\"}"
```
- **Code HTTP attendu** : 400 Bad Request
- **Corps de réponse** : `{"erreur":"DONNEES_INVALIDES","message":"Nom ou email invalide"}`
- **Verdict** : Réussi

### T9 : PATCH modification partielle (email seul)
```cmd
curl -i -X PATCH http://localhost:8080/soa-tp1/api/etudiants/1 -H "Content-Type: application/json" -d "{\"email\":\"nouvel.email@example.org\"}"
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"email":"nouvel.email@example.org","id":1,"nom":"Aminata Diop"}`
- **Verdict** : Réussi

### T10 : DELETE suppression de l'étudiant
```cmd
curl -i -X DELETE http://localhost:8080/soa-tp1/api/etudiants/1
```
- **Code HTTP attendu** : 204 No Content
- **Corps de réponse** : *(aucun corps)*
- **Verdict** : Réussi

### T11 : GET vérification post-suppression
```cmd
curl -i http://localhost:8080/soa-tp1/api/etudiants/1
```
- **Code HTTP attendu** : 404 Not Found
- **Corps de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : Réussi

### T12 : DELETE ressource inexistante
```cmd
curl -i -X DELETE http://localhost:8080/soa-tp1/api/etudiants/999
```
- **Code HTTP attendu** : 404 Not Found
- **Corps de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : Réussi

---

# SECTION 2 : Tests Ressource Département (`/api/departements`)

Base URL : `http://localhost:8080/soa-tp1/api/departements`

### TD1 : GET collection avant création
```cmd
curl -i http://localhost:8080/soa-tp1/api/departements
```
- **Code HTTP attendu** : 200 OK
- **Extrait de réponse** : `[]`
- **Verdict** : Réussi

### TD2 : POST création d'un département valide
```cmd
curl -i -X POST http://localhost:8080/soa-tp1/api/departements -H "Content-Type: application/json" -d "{\"nom\":\"Informatique\",\"code\":\"INFO\"}"
```
- **Code HTTP attendu** : 201 Created
- **En-tête Location** : `http://localhost:8080/soa-tp1/api/departements/1`
- **Corps de réponse** : `{"code":"INFO","id":1,"nom":"Informatique"}`
- **Verdict** : Réussi

### TD3 : GET consultation du département créé (ID 1)
```cmd
curl -i http://localhost:8080/soa-tp1/api/departements/1
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"code":"INFO","id":1,"nom":"Informatique"}`
- **Verdict** : Réussi

### TD4 : PUT remplacement complet du département
```cmd
curl -i -X PUT http://localhost:8080/soa-tp1/api/departements/1 -H "Content-Type: application/json" -d "{\"nom\":\"Informatique et Telecoms\",\"code\":\"INFO-TEL\"}"
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"code":"INFO-TEL","id":1,"nom":"Informatique et Telecoms"}`
- **Verdict** : Réussi

### TD5 : PATCH modification partielle (nom seul)
```cmd
curl -i -X PATCH http://localhost:8080/soa-tp1/api/departements/1 -H "Content-Type: application/json" -d "{\"nom\":\"Departement d Informatique\"}"
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"code":"INFO-TEL","id":1,"nom":"Departement d Informatique"}`
- **Verdict** : Réussi

### TD6 : GET département inexistant (ID 999)
```cmd
curl -i http://localhost:8080/soa-tp1/api/departements/999
```
- **Code HTTP attendu** : 404 Not Found
- **Corps de réponse** : `{"erreur":"DEPARTEMENT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : Réussi

### TD7 : POST validation - nom ou code manquant
```cmd
curl -i -X POST http://localhost:8080/soa-tp1/api/departements -H "Content-Type: application/json" -d "{\"nom\":\"\",\"code\":\"\"}"
```
- **Code HTTP attendu** : 400 Bad Request
- **Corps de réponse** : `{"erreur":"DONNEES_INVALIDES","message":"Nom et code du département requis"}`
- **Verdict** : Réussi

### TD8 : DELETE suppression du département (ID 1)
```cmd
curl -i -X DELETE http://localhost:8080/soa-tp1/api/departements/1
```
- **Code HTTP attendu** : 204 No Content
- **Corps de réponse** : *(aucun corps)*
- **Verdict** : Réussi

### TD9 : GET vérification après suppression
```cmd
curl -i http://localhost:8080/soa-tp1/api/departements/1
```
- **Code HTTP attendu** : 404 Not Found
- **Corps de réponse** : `{"erreur":"DEPARTEMENT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : Réussi

---

# SECTION 3 : Tests Ressource Module (`/api/modules`)

Base URL : `http://localhost:8080/soa-tp1/api/modules`

### TM1 : GET collection avant création
```cmd
curl -i http://localhost:8080/soa-tp1/api/modules
```
- **Code HTTP attendu** : 200 OK
- **Extrait de réponse** : `[]`
- **Verdict** : Réussi

### TM2 : POST création d'un module valide
```cmd
curl -i -X POST http://localhost:8080/soa-tp1/api/modules -H "Content-Type: application/json" -d "{\"code\":\"SOA\",\"nom\":\"Architecture Orientee Services\",\"coefficient\":4}"
```
- **Code HTTP attendu** : 201 Created
- **En-tête Location** : `http://localhost:8080/soa-tp1/api/modules/1`
- **Corps de réponse** : `{"coefficient":4,"code":"SOA","id":1,"nom":"Architecture Orientee Services"}`
- **Verdict** : Réussi

### TM3 : GET consultation du module créé (ID 1)
```cmd
curl -i http://localhost:8080/soa-tp1/api/modules/1
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"coefficient":4,"code":"SOA","id":1,"nom":"Architecture Orientee Services"}`
- **Verdict** : Réussi

### TM4 : PUT remplacement complet du module
```cmd
curl -i -X PUT http://localhost:8080/soa-tp1/api/modules/1 -H "Content-Type: application/json" -d "{\"code\":\"SOA-ADV\",\"nom\":\"SOA et Microservices Avances\",\"coefficient\":5}"
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"coefficient":5,"code":"SOA-ADV","id":1,"nom":"SOA et Microservices Avances"}`
- **Verdict** : Réussi

### TM5 : PATCH modification partielle (coefficient seul)
```cmd
curl -i -X PATCH http://localhost:8080/soa-tp1/api/modules/1 -H "Content-Type: application/json" -d "{\"coefficient\":6}"
```
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"coefficient":6,"code":"SOA-ADV","id":1,"nom":"SOA et Microservices Avances"}`
- **Verdict** : Réussi

### TM6 : GET module inexistant (ID 999)
```cmd
curl -i http://localhost:8080/soa-tp1/api/modules/999
```
- **Code HTTP attendu** : 404 Not Found
- **Corps de réponse** : `{"erreur":"MODULE_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : Réussi

### TM7 : POST validation - champs manquants
```cmd
curl -i -X POST http://localhost:8080/soa-tp1/api/modules -H "Content-Type: application/json" -d "{\"code\":\"\",\"nom\":\"\"}"
```
- **Code HTTP attendu** : 400 Bad Request
- **Corps de réponse** : `{"erreur":"DONNEES_INVALIDES","message":"Code et nom du module requis"}`
- **Verdict** : Réussi

### TM8 : DELETE suppression du module (ID 1)
```cmd
curl -i -X DELETE http://localhost:8080/soa-tp1/api/modules/1
```
- **Code HTTP attendu** : 204 No Content
- **Corps de réponse** : *(aucun corps)*
- **Verdict** : Réussi

### TM9 : GET vérification après suppression
```cmd
curl -i http://localhost:8080/soa-tp1/api/modules/1
```
- **Code HTTP attendu** : 404 Not Found
- **Corps de réponse** : `{"erreur":"MODULE_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : Réussi


---

# SECTION 4 : Tests des Relations (1-N et N-N)

### TR1 : Assigner un département à un étudiant (Relation 1-N)
```cmd
curl -i -X PUT http://localhost:8080/soa-tp1/api/etudiants/1/departement/1
```
- **Description** : Affecte l'étudiant ID 1 au département ID 1.
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : Objet étudiant contenant `"departementId":1`
- **Verdict** : Réussi

### TR2 : Consulter le département d'un étudiant (Relation 1-N)
```cmd
curl -i http://localhost:8080/soa-tp1/api/etudiants/1/departement
```
- **Description** : Récupère les données du département auquel appartient l'étudiant ID 1.
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `{"code":"INFO","id":1,"nom":"Informatique"}`
- **Verdict** : Réussi

### TR3 : Lister tous les étudiants d'un département (Relation 1-N)
```cmd
curl -i http://localhost:8080/soa-tp1/api/departements/1/etudiants
```
- **Description** : Retourne la liste des étudiants rattachés au département ID 1.
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : Tableau contenant l'étudiant ID 1
- **Verdict** : Réussi

### TR4 : Inscrire un étudiant à un module (Relation N-N)
```cmd
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants/1/modules/1
```
- **Description** : Inscrit l'étudiant ID 1 au module ID 1.
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : Objet étudiant contenant `"moduleIds":[1]`
- **Verdict** : Réussi

### TR5 : Lister les modules suivis par un étudiant (Relation N-N)
```cmd
curl -i http://localhost:8080/soa-tp1/api/etudiants/1/modules
```
- **Description** : Récupère la liste complète des objets `Module` auxquels l'étudiant ID 1 est inscrit.
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : `[{"coefficient":4,"code":"SOA","id":1,"nom":"Architecture Orientee Services"}]`
- **Verdict** : Réussi

### TR6 : Lister tous les étudiants inscrits à un module (Relation N-N)
```cmd
curl -i http://localhost:8080/soa-tp1/api/modules/1/etudiants
```
- **Description** : Retourne la liste des étudiants inscrits au module ID 1.
- **Code HTTP attendu** : 200 OK
- **Corps de réponse** : Tableau JSON contenant l'étudiant ID 1
- **Verdict** : Réussi

### TR7 : Désinscrire un étudiant d'un module (Relation N-N)
```cmd
curl -i -X DELETE http://localhost:8080/soa-tp1/api/etudiants/1/modules/1
```
- **Description** : Retire le module ID 1 de la liste des modules de l'étudiant ID 1.
- **Code HTTP attendu** : 204 No Content
- **Corps de réponse** : *(aucun corps)*
- **Verdict** : Réussi
