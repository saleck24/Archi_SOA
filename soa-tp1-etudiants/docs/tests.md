# Tests du TP1 : API Etudiants (CRUD Complet : GET, POST, PUT, PATCH, DELETE)

Base URL : `http://localhost:8080/soa-tp1/api/etudiants`

---

## T1 : GET collection avant création
**Requête**
```bash
curl -i http://localhost:8080/soa-tp1/api/etudiants
```
- **Code HTTP obtenu** : 200 OK
- **Extrait de réponse** : `[]`
- **Verdict** : réussi

---

## T2 : POST étudiant valide
**Requête**
```bash
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants \
  -H "Content-Type: application/json" \
  -d "{\"nom\":\"Aminata\",\"email\":\"aminata@example.org\"}"
```
- **Code HTTP obtenu** : 201 Created
- **Extrait de réponse** : En-tête `Location: http://localhost:8080/soa-tp1/api/etudiants/1` et corps `{"email":"aminata@example.org","id":1,"nom":"Aminata"}`
- **Verdict** : réussi

---

## T3 : GET étudiant créé
**Requête**
```bash
curl -i http://localhost:8080/soa-tp1/api/etudiants/1
```
- **Code HTTP obtenu** : 200 OK
- **Extrait de réponse** : `{"id":1,"nom":"Aminata","email":"aminata@example.org"}`
- **Verdict** : réussi

---

## T4 : GET identifiant absent
**Requête**
```bash
curl -i http://localhost:8080/soa-tp1/api/etudiants/999
```
- **Code HTTP obtenu** : 404 Not Found
- **Extrait de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : réussi

---

## T5 : POST nom vide / email invalide
**Requête**
```bash
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants \
  -H "Content-Type: application/json" \
  -d "{\"nom\":\"\",\"email\":\"incorrect\"}"
```
- **Code HTTP obtenu** : 400 Bad Request
- **Extrait de réponse** : `{"erreur":"DONNEES_INVALIDES","message":"Nom ou email invalide"}`
- **Verdict** : réussi

---

## T6 : PUT mise à jour complète (étudiant existant)
**Requête**
```bash
curl -i -X PUT http://localhost:8080/soa-tp1/api/etudiants/1 \
  -H "Content-Type: application/json" \
  -d "{\"nom\":\"Aminata Diop\",\"email\":\"aminata.diop@example.org\"}"
```
- **Code HTTP obtenu** : 200 OK
- **Extrait de réponse** : `{"email":"aminata.diop@example.org","id":1,"nom":"Aminata Diop"}`
- **Verdict** : réussi

---

## T7 : PUT étudiant inexistant
**Requête**
```bash
curl -i -X PUT http://localhost:8080/soa-tp1/api/etudiants/999 \
  -H "Content-Type: application/json" \
  -d "{\"nom\":\"Inconnu\",\"email\":\"inconnu@example.org\"}"
```
- **Code HTTP obtenu** : 404 Not Found
- **Extrait de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : réussi

---

## T8 : PUT validation données invalides
**Requête**
```bash
curl -i -X PUT http://localhost:8080/soa-tp1/api/etudiants/1 \
  -H "Content-Type: application/json" \
  -d "{\"nom\":\"\",\"email\":\"invalide\"}"
```
- **Code HTTP obtenu** : 400 Bad Request
- **Extrait de réponse** : `{"erreur":"DONNEES_INVALIDES","message":"Nom ou email invalide"}`
- **Verdict** : réussi

---

## T9 : PATCH modification partielle (email uniquement)
**Requête**
```bash
curl -i -X PATCH http://localhost:8080/soa-tp1/api/etudiants/1 \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"nouvel.email@example.org\"}"
```
- **Code HTTP obtenu** : 200 OK
- **Extrait de réponse** : `{"email":"nouvel.email@example.org","id":1,"nom":"Aminata Diop"}` (le nom reste intact, l'email est mis à jour)
- **Verdict** : réussi

---

## T10 : DELETE étudiant existant
**Requête**
```bash
curl -i -X DELETE http://localhost:8080/soa-tp1/api/etudiants/1
```
- **Code HTTP obtenu** : 204 No Content
- **Extrait de réponse** : *(aucun corps, HTTP 204 No Content)*
- **Verdict** : réussi

---

## T11 : GET étudiant après suppression
**Requête**
```bash
curl -i http://localhost:8080/soa-tp1/api/etudiants/1
```
- **Code HTTP obtenu** : 404 Not Found
- **Extrait de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : réussi

---

## T12 : DELETE étudiant inexistant
**Requête**
```bash
curl -i -X DELETE http://localhost:8080/soa-tp1/api/etudiants/999
```
- **Code HTTP obtenu** : 404 Not Found
- **Extrait de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : réussi
