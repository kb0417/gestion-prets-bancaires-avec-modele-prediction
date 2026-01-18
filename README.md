# Application de Gestion des Prêts Bancaires avec Prédiction du Risque

## Présentation du projet

Ce projet consiste à développer une application web Java EE permettant la gestion complète des clients, des prêts bancaires et des utilisateurs, intégrant un module d’intelligence artificielle pour la prédiction du risque de défaut de prêt.

L’objectif est de simuler un système bancaire professionnel en remplaçant une gestion manuelle (Excel) par une plateforme sécurisée, centralisée et enrichie par des outils d’aide à la décision.

---

## Architecture globale

L’application repose sur une architecture multi-tiers :

- Interface utilisateur web (JSP)
- Serveur applicatif Java EE (Tomcat)
- Base de données relationnelle PostgreSQL
- Serveur d’intelligence artificielle Flask (Python)

Le serveur JEE communique avec le serveur Flask via des requêtes HTTP REST (JSON).

---

## Technologies utilisées

### Backend
- Java EE (Servlets, JSP, JSTL)
- Apache Tomcat 9
- JDBC
- PostgreSQL
- Pattern DAO
- Filtres de sécurité (AuthFilter)

### Intelligence artificielle
- Python 3
- Flask
- Scikit-learn
- Joblib
- Modèle de régression logistique
- Extension prévue : réseau de neurones

### Frontend
- JSP
- HTML / CSS
- JavaScript
- Pagination dynamique
- Dashboard interactif

### Outils
- Eclipse IDE
- Git / GitHub
- Postman / CURL

---

## Structure du projet
```bash
GestionPretsBancaires/
│
├── src/
│ ├── main/java
│ │ ├── dao/ Accès aux données
│ │ ├── model/ Entités métier
│ │ ├── servlet/ Contrôleurs
│ │ ├── filter/ Sécurité
│ │ └── util/ Utilitaires (DB, Flask client)
│ │
│ └── main/webapp
│ ├── WEB-INF/ JSP sécurisées
│ ├── css/
│ ├── js/
│ └── images/
│
├── ai-flask/ Serveur IA (Flask)
│ ├── app.py
│ ├── modele_risque_pret.pkl
│ ├── scaler.pkl
│ ├── requirements.txt
│
├── .gitignore
└── README.md
```

---

## Gestion des rôles

Deux rôles sont définis :

- ADMIN : gestion des utilisateurs, clients et prêts
- AGENT : gestion des clients et des prêts, évaluation du risque

L’accès est sécurisé par :
- authentification (login / mot de passe)
- sessions HTTP
- filtre d’authentification

---

## Fonctionnalités principales

### Clients
- Ajout, modification et suppression
- Recherche multicritère
- Consultation des prêts associés
- Suppression interdite si des prêts existent

### Prêts
- CRUD complet
- Association à un client
- Filtrage et recherche
- Évaluation du risque via l’IA

### Prédiction du risque
- Appel à une API Flask
- Retour d’une probabilité et d’une interprétation
- Historisation des résultats en base

### Dashboard
- Nombre de clients
- Nombre de prêts
- Montant total
- Nombre de prêts à risque
- Accès rapide aux fonctionnalités principales

---

## Lancement du projet

### Base de données
- Créer la base PostgreSQL
- Exécuter les scripts SQL (client, pret, utilisateur, evaluation_risque)
- Configurer la connexion dans DBConnection.java

### Lancer le serveur Flask

```bash
cd ai-flask
pip install -r requirements.txt
python app.py

```
### API disponible sur :

```bash
http://localhost:5000/predict
```

### Lancer l’application JEE

- Importer le projet dans Eclipse
- Déployer sur Apache Tomcat
- Accéder à :

```bash
http://localhost:8080/GestionPretsBancaires
```
## Communication JEE – Flask

- Le serveur JEE envoie les données du prêt au format JSON
- Le serveur Flask retourne :
   - une probabilité de risque
   - une interprétation (faible ou élevé)

- Le résultat est affiché et sauvegardé en base

## Perspectives d’amélioration
- Comparaison entre régression logistique et réseau de neurones
- Ajout de graphiques statistiques (répartition par ville)
- Mise en cache des prédictions IA
- Conteneurisation avec Docker

# Auteur
-BADJAMLA Essorong

-Étudiant en informatique – 4ᵉ année

-Projet académique : Gestion bancaire et intelligence artificielle

