# Tests du TP1 : API Etudiants

Base URL : `http://localhost:8080/soa-tp1/api/etudiants`

## T1 : GET collection avant création

**Requête**
```
curl -i http://localhost:8080/soa-tp1/api/etudiants
```
**Code HTTP obtenu** : 200
**Extrait de réponse** : `[]`
**Verdict** : réussi

## T2 : POST étudiant valide

**Requête**
```
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Aminata\",\"email\":\"aminata@example.org\"}"
```
**Code HTTP obtenu** : 201
**Extrait de réponse** : `http://localhost:8080/soa-tp1/api/etudiants/1` et `{"email":"aminata@example.org","id":1,"nom":"Aminata"}`
**Verdict** : réussi

## T3 : GET étudiant créé

**Requête**
```
curl -i http://localhost:8080/soa-tp1/api/etudiants/1
```
**Code HTTP obtenu** : 200
**Extrait de réponse** : `{"id":1,"nom":"Aminata","email":"aminata@example.org"}`
**Verdict** : réussi

## T4 : GET identifiant absent

**Requête**
```
curl -i http://localhost:8080/soa-tp1/api/etudiants/999
```
**Code HTTP obtenu** : 404
**Extrait de réponse** : `{"message":"Identifiant inconnu","erreur":"ETUDIANT_INTROUVABLE"}`
**Verdict** : réussi

## T5 : POST nom vide / email invalide

**Requête**
```
curl -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"\",\"email\":\"incorrect\"}"
```
**Code HTTP obtenu** : 400
**Extrait de réponse** : `{"message":"Nom ou email invalide","erreur":"DONNEES_INVALIDES"}`
**Verdict** : réussi