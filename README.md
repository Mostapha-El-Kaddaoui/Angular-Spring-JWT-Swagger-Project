# Tresorory Frontend (Angular)
## Composantes Overview
![image](https://github.com/user-attachments/assets/6aef8dff-e9c6-4752-8c1c-053da1eb23db)

---
## Partie Front-End avec Angular

Le dossier `src/app` contient toute la logique de l'application Angular. Voici une description des principaux dossiers et fichiers présents :

### Composants (components)

- **accounts/**  
  Composant dédié à la gestion des comptes bancaires (affichage, opérations, etc.).  
  Contient : HTML, CSS, TypeScript, et tests.

- **admin-template/**  
  Composant servant de modèle (template) pour la partie administration de l’application.  
  Gère la mise en page et le design commun.

- **change-password/**  
  Composant pour permettre aux utilisateurs de modifier leur mot de passe.

- **customers/**  
  Composant dédié à la gestion des clients (affichage liste clients, détails, etc.).

- **home/**  
  Page d'accueil de l'application, souvent le premier écran visible après connexion.

- **login/**  
  Composant pour la gestion de l’authentification (formulaire de connexion).

- **navbar/**  
  Barre de navigation commune à toutes les pages.

- **new-customer/**  
  Formulaire pour créer un nouveau client.

- **not-authorized/**  
  Page affichée lorsqu’un utilisateur tente d’accéder à une ressource pour laquelle il n’a pas les droits.

### Services (services)

- **account.service.ts**  
  Service pour gérer les appels API liés aux comptes bancaires.

- **auth.service.ts**  
  Service pour gérer l’authentification, les tokens JWT, etc.

- **customer.service.ts**  
  Service pour gérer les appels API liés aux clients.

Chaque service a son fichier de test (`*.spec.ts`).

### Gardiens (guards)

- **authentication.guard.ts**  
  Garde qui protège les routes nécessitant une connexion.

- **authorization.guard.ts**  
  Garde qui protège les routes selon les rôles/utilisateur.

### Intercepteurs (interceptors)

- **app-http.interceptor.ts**  
  Intercepteur HTTP pour ajouter automatiquement le token d'authentification aux requêtes sortantes, gérer les erreurs globalement, etc.

### Modèles (model)

- **account.model.ts**  
  Définition des interfaces/types pour les comptes.

- **customer.model.ts**  
  Définition des interfaces/types pour les clients.

### Fichiers racines

- **app.component.ts / html / css**  
  Composant racine de l’application.

- **app.routes.ts**  
  Fichier de configuration des routes Angular (définit la navigation dans l’application).

- **app.config.ts**  
  Configuration générale de l’application (exemple : constantes, URL API, etc.).

- **main.ts**  
  Point d’entrée principal qui lance l’application Angular.

- **index.html**  
  Page HTML principale qui charge l’application Angular.

- **styles.css**  
  Styles globaux applicables à toute l’application.

---

### 2.2. Interfaces

#### Hero Page 
![image](https://github.com/user-attachments/assets/0c725ed4-33c3-4c05-a6e6-ab8c99e887f8)

#### Login Page  
![image](https://github.com/user-attachments/assets/b625898b-768b-4d38-a16d-51ce43c264dd)

#### Change Password Interface  
![image](https://github.com/user-attachments/assets/6a5fcd4c-f75a-45c3-8872-3f34bb663053)

#### Customer List, Search, and Delete  
![image](https://github.com/user-attachments/assets/2bb1dcc4-5109-4f39-a7ac-db738c5e3fd2)
![image](https://github.com/user-attachments/assets/129bdc33-8953-4da5-bd72-ca4638930ae6)

#### Create Customer Form  
![image](https://github.com/user-attachments/assets/e5ab082d-73cb-44a0-a61b-3dbcf8bb8d2b)
![image](https://github.com/user-attachments/assets/8c305035-0ea1-4d85-8ada-1ea73f138933)

#### Accounts List and Operations Get 
![image](https://github.com/user-attachments/assets/b9f081f4-6692-489b-a450-cf9433988434)

