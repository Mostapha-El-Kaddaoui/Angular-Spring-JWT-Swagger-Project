# Tresorory Management

A comprehensive treasury management system designed to handle financial transactions securely and efficiently.

## Technologies Used

- **Spring Framework** - Core framework for building the backend.
- **Spring Security** - For authentication and authorization.
- **Angular** - Frontend framework for building the user interface.
- **Swagger** - API documentation and testing tool.
- **MySQL** - Relational database management system.
- **Lombok** - Reduces boilerplate code in Java.
- **JWT (JSON Web Tokens)** - For secure token-based authentication.
- ...and other supporting libraries and tools.

## Entités JPA Créées

Dans le cadre du développement du backend du projet E-Banking, j'ai créé les entités suivantes en utilisant JPA :

- `Customer` : représente le client de la banque.
- `BankAccount` : classe mère abstraite pour les comptes bancaires.
- `SavingAccount` : entité représentant un compte épargne.
- `CurrentAccount` : entité représentant un compte courant.
- `AccountOperation` : entité représentant les opérations effectuées sur un compte (crédit, débit, etc.).

Chaque entité est liée avec les autres via des relations JPA (`@OneToMany`, `@ManyToOne`, etc.).

## Repositories JPA

J'ai également créé les interfaces nécessaires pour la gestion des entités via **Spring Data JPA** :

- `CustomerRepository.java`
- `BankAccountRepository.java`
- `AccountOperationRepository.java`

## Test de la Couche DAO

Pour tester la couche **DAO (Data Access Object)**, j'ai utilisé la classe principale `TresororyApplication.java` afin d'injecter les repositories et de simuler l'ajout de données.

### Exemple de sauvegarde de clients :

```java
Stream.of("Hassan", "Yassine", "Khalid").forEach(name -> {
    Customer customer = Customer.builder()
        .name(name)
        .email(name + "@gmail.com")
        .build();

    customerRepository.save(customer);
});
```
## Couche DTO (Data Transfer Objects)

Pour séparer la logique métier de la représentation des données exposées, j'ai créé une **couche DTO** qui permet de transférer les données entre les différentes couches (Service Web).

### Liste des classes DTO créées :

- `CustomerDTO.java` : représente les données d'un client.
- `BankAccountDTO.java` : DTO générique pour les comptes bancaires.
- `SavingAccountDTO.java` : spécialisation pour les comptes épargne.
- `CurrentAccountDTO.java` : spécialisation pour les comptes courants.
- `AccountOperationDTO.java` : représente les opérations effectuées sur un compte.
- `AccountHistoryDTO.java` : utilisé pour afficher l'historique d’un compte (liste paginée d'opérations, solde, etc.).

## Couche Service

La **couche service** joue un rôle central dans l'architecture de l'application. Elle assure la **liaison entre la couche DAO (accès aux données via les repositories)** et la **couche Web (présentation et API REST)**.

### Fichiers de la couche service :

- `BankAccountService.java` : Interface qui définit les opérations métiers exposées (comme créer un compte, consulter le solde, effectuer une opération...).
- `BankAccountServiceImpl.java` : Implémentation concrète de l’interface, contenant la logique métier principale.

## Couche Web (RestControllers)

J'ai créé deux contrôleurs REST pour gérer les différentes parties de l'application :

- **CustomerRestController.java** :  
  Contrôleur dédié à la gestion des clients (Customer).  
  Il expose des API REST pour créer, récupérer, mettre à jour ou supprimer des clients.

- **BankAccountRestAPI.java** :  
  Contrôleur dédié à la gestion des comptes bancaires (BankAccount).  
  Il permet d'effectuer des opérations telles que consulter un compte, afficher l'historique des opérations, créer des comptes, etc.

---

## Tester les Web Services RESTful

Comme j'ai appliqué des principes de sécurité (authentification via JWT) sur mes APIs, il n'est pas possible d'utiliser Swagger directement pour tester les services (Swagger UI ne gère pas la gestion des tokens dans ce contexte).

À la place, j'utilise l'outil **HTTP API Client** intégré dans IntelliJ pour tester les endpoints sécurisés.

### Exemple de test du contrôleur `CustomerRestController` :

Requête GET pour récupérer la liste des clients :

```http
GET http://localhost:8085/customers
Accept: application/json
Authorization: Bearer Token
```
### La reponse : 
![./image.png…]()




