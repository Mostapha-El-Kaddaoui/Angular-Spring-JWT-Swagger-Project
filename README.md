# Angular-Spring-JWT-Swagger-Project

## Overview
This project is a modern e-banking frontend built with Angular. It provides a secure, role-based interface for managing customers and accounts, with JWT authentication, route guards, and a clean UI using Tailwind CSS. The frontend communicates with a Spring Boot backend (see Back-End branch) via RESTful APIs.

---

## Table of Contents
- [Project Structure](#project-structure)
- [Models](#models)
- [Services](#services)
- [Security](#security)
- [Guards](#guards)
- [Interceptors](#interceptors)
- [Components](#components)
- [Routing](#routing)
- [Web APIs](#web-apis)
- [How to Run](#how-to-run)

---

## Project Structure
```
src/app/
  accounts/           # Account management UI
  admin-template/     # Admin layout
  change-password/    # Change password UI
  customers/          # Customer management UI
  guards/             # Route guards (auth, role)
  home/               # Home page
  interceptors/       # HTTP interceptors
  login/              # Login UI
  model/              # TypeScript interfaces (Account, Customer)
  navbar/             # Navigation bar
  new-customer/       # New customer form
  not-authorized/     # Not authorized page
  services/           # Angular services (API, Auth)
  app.component.*     # Root component
  app.config.ts       # App-wide providers
  app.routes.ts       # Angular routes
```

---

## Models
### `AccountDetails` (`model/account.model.ts`)
- `accountId: string`
- `balance: number`
- `currentPage: number`
- `totalPages: number`
- `pageSize: number`
- `accountOperationDTOS: AccountOperation[]`

### `AccountOperation` (`model/account.model.ts`)
- `id: number`
- `operationDate: Date`
- `amount: number`
- `type: string`
- `description: string`

### `Customer` (`model/customer.model.ts`)
- `id: number`
- `name: string`
- `email: string`

---

## Services
### `AccountService` (`services/account.service.ts`)
- `getAccounts(): Observable<AccountDetails[]>` — List all accounts
- `searchAccounts(accid: string, page: number, size: number): Observable<AccountDetails>` — Get account details and operations
- `updateAccount(accountId: string, accountData: any): Observable<any>` — Update account info
- `deleteAccount(id: any)` — (Not implemented)

### `CustomerService` (`services/customer.service.ts`)
- `getCustomers(): Observable<Customer[]>` — List all customers
- `searchCustomers(keyword: string): Observable<Customer[]>` — Search customers by keyword
- `saveCustomer(customer: Customer): Observable<Customer>` — Add a new customer
- `deleteCustomer(id: number): Observable<any>` — Delete a customer

### `AuthService` (`services/auth.service.ts`)
- `login(username: string, password: string)` — Authenticate and get JWT
- `loadProfile(data: any)` — Decode JWT, set user info
- `logout()` — Clear session, redirect to login
- `loadJwtFromLocalStorage()` — Restore session from localStorage

---

## Security
- **JWT Authentication:**
  - Login via `/auth/login` endpoint (POST, username/password)
  - JWT token is stored in localStorage and attached to all API requests (except login)
- **Role-based Authorization:**
  - User roles are decoded from JWT (`scope` claim)
  - Only users with `ADMIN` role can access certain routes (e.g., create customer)

---

## Guards
### `AuthenticationGuard` (`guards/authentication.guard.ts`)
- Protects routes that require authentication
- Redirects to `/login` if not authenticated

### `AuthorizationGuard` (`guards/authorization.guard.ts`)
- Protects routes that require specific roles (e.g., `ADMIN`)
- Redirects to `/admin/notauthorized` if role check fails

---

## Interceptors
### `AppHttpInterceptor` (`interceptors/app-http.interceptor.ts`)
- Attaches `Authorization: Bearer <token>` header to all outgoing HTTP requests (except login)
- Handles 401 errors globally (logs out user)

---

## Components
- **AppComponent:** Root component, loads user session
- **NavbarComponent:** Navigation bar, shows user info, logout
- **LoginComponent:** Login form, handles authentication
- **HomeComponent:** Welcome page
- **AdminTemplateComponent:** Layout for admin pages
- **CustomersComponent:** List/search/delete customers
- **NewCustomerComponent:** Add new customer form
- **AccountsComponent:** List/search/update accounts, view operations
- **ChangePasswordComponent:** (UI only, not implemented)
- **NotAuthorizedComponent:** Shown when user lacks permissions

---

## Routing
Defined in `app.routes.ts`:
- `/home` — Home page
- `/login` — Login page
- `/admin` — Admin dashboard (protected)
  - `/customers` — Customer management
  - `/accounts` — Account management
  - `/customers/new-customer` — Add customer (ADMIN only)
  - `/notauthorized` — Not authorized page
- `/changepassword` — Change password

---

## Web APIs (Backend Endpoints Consumed)
- `POST /auth/login` — Authenticate user, returns JWT
- `GET /customers` — List all customers
- `GET /customers/search?keyword=...` — Search customers
- `POST /customers` — Add new customer
- `DELETE /customers/{id}` — Delete customer
- `GET /accounts` — List all accounts
- `GET /accounts/{accid}/pageoperations?page=...&size=...` — Get account details and operations
- `PUT /accounts/{accountId}` — Update account

---

## How to Run
1. Install dependencies:
   ```sh
   npm install
   ```
2. Start the Angular app:
   ```sh
   npm start
   ```
3. The app runs at [http://localhost:4200](http://localhost:4200)

---

## Notes
- This frontend expects a backend running at `http://localhost:8085` (Spring Boot, see Back-End branch)
- All API calls are made to this backend
- JWT token is required for all API calls except login
- UI is styled with Tailwind CSS

---

## License
MIT

