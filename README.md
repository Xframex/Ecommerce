# IsmaCart — Full-Stack E-Commerce Platform

<p align="center">
  <img src="https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot_3.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Angular_17-DD0031?style=for-the-badge&logo=angular&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Stripe-008CDD?style=for-the-badge&logo=stripe&logoColor=white"/>
  <img src="https://img.shields.io/badge/Okta-007DC1?style=for-the-badge&logo=okta&logoColor=white"/>
  <img src="https://img.shields.io/badge/OAuth2-3C3C3D?style=for-the-badge&logo=auth0&logoColor=white"/>
  <img src="https://img.shields.io/badge/JPA/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/>
  <img src="https://img.shields.io/badge/HTTPS-003A70?style=for-the-badge&logo=letsencrypt&logoColor=white"/>
</p>

> **A production-grade e-commerce backend built with Spring Boot 3.2 + Angular 17, featuring secure checkout powered by Stripe, OAuth2 authentication via Okta, and a RESTful API architecture.**

---

## Architecture Overview

```mermaid
graph TB
    subgraph CLIENT["Angular Frontend (localhost:4200)"]
        A[Product Catalog]
        B[User Auth UI]
        C[Checkout Form]
        D[Order History]
    end

    subgraph SERVER["Spring Boot Backend (localhost:9898 - HTTPS)"]
        direction TB
        subgraph WEB["Web Layer"]
            RC[CheckoutController]
            RS[StripeController]
            RDR[Spring Data REST - Products, Categories]
        end
        subgraph SVC["Service Layer"]
            CSI[CheckoutService]
            SS[StripeService]
        end
        subgraph DAO["Data Access Layer"]
            PR[ProductRepository]
            PCR[ProductCategoryRepository]
            CR[CustomerRepository]
            OR[OrderRepository]
            CtR[CountryRepository]
            SR[StateRepository]
        end
        subgraph SEC["Security Layer"]
            SC[SecurityConfiguration - Okta OAuth2]
            JWT[JWT Validation]
        end
    end

    subgraph DB["MySQL Database"]
        DB_P[(products, product_category)]
        DB_O[(orders, order_item)]
        DB_C[(customer, address)]
        DB_L[(country, state)]
    end

    subgraph EXT["External Services"]
        OKTA[Okta - OAuth2 JWT Issuer]
        STRIPE[Stripe - Payment Intents]
    end

    CLIENT -- HTTPS + JWT --> WEB
    WEB --> SVC
    SVC --> DAO
    DAO --> DB
    RC --> CSI
    RS --> SS
    SS -- API Key --> STRIPE
    SC -- JWT Verification --> OKTA
    RDR -- Exposed as /api/** --> WEB

    style CLIENT fill:#f5f5f5,stroke:#333,stroke-width:2px
    style SERVER fill:#e8f5e9,stroke:#2e7d32,stroke-width:3px
    style DB fill:#fff3e0,stroke:#e65100,stroke-width:2px
    style EXT fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
```

---

## ✨ Key Features

| Feature | Implementation |
|---|---|
| **Product Catalog** | Spring Data REST auto-exposes `/api/products` with pagination, search by name, and filtering by category |
| **Secure Checkout** | `POST /api/checkout/purchase` — processes order + verifies Stripe PaymentIntent |
| **Stripe Payments** | `POST /api/checkout/create-payment-intent` — server-side PaymentIntent creation |
| **OAuth2 / JWT Auth** | Okta resource server protects `/api/orders/**`; all other endpoints public |
| **HTTPS only** | Server runs on port **9898** with PKCS12 keystore, SSL enabled |
| **CORS** | Whitelisted origins: `https://localhost:4200`, `http://localhost:4200` |
| **Read-Only REST** | PUT/POST/DELETE/PATCH disabled on Product, Category, Country, State, Order (safety) |
| **Entity ID Exposure** | All JPA entity IDs exposed in JSON responses for frontend consumption |

---

## Checkout Flow — 3D Sequence

```mermaid
sequenceDiagram
    participant User as 👤 User
    participant Angular as 🌐 Angular App
    participant API as 🔧 Spring Boot API
    participant Stripe as 💳 Stripe
    participant DB as 💾 MySQL

    User->>Angular: Browse products & add to cart
    User->>Angular: Click "Checkout"
    Angular->>API: POST /api/checkout/create-payment-intent
    API->>Stripe: PaymentIntent.create(amount, currency)
    Stripe-->>API: Returns client_secret
    API-->>Angular: { clientSecret: "pi_xxx_secret_xxx" }
    Angular->>Stripe: stripe.confirmCardPayment(clientSecret)
    Stripe-->>Angular: PaymentIntent result (succeeded)
    Angular->>API: POST /api/checkout/purchase
    Note over API: StripeService.retrievePaymentIntent()<br/>verifies status == "succeeded"
    API->>DB: Save Customer (or find existing by email)
    API->>DB: Save Order + OrderItems
    API->>DB: Save Address (billing + shipping)
    API-->>Angular: { orderTrackingNumber: "uuid" }
    Angular-->>User: ✅ Order Confirmation
```

---

## Data Model — Entity Relationship

```mermaid
erDiagram
    ProductCategory ||--o{ Product : "has many"
    Product ||--o{ OrderItem : "appears in"
    Order ||--|{ OrderItem : "contains"
    Customer ||--o{ Order : "places"
    Order ||--|| Address : "shipping address"
    Order ||--|| Address : "billing address"
    Country ||--o{ State : "has states"

    ProductCategory {
        bigint id PK
        string category_name
    }

    Product {
        bigint id PK
        string sku
        string name
        text description
        decimal unit_price
        string image_url
        boolean active
        int units_in_stock
        datetime date_created
        datetime last_updated
        bigint category_id FK
    }

    Order {
        bigint id PK
        string order_tracking_number UK
        decimal total_quantity
        double total_price
        string status
        datetime date_created
        datetime last_updated
        bigint customer_id FK
        bigint shipping_address_id FK
        bigint billing_address_id FK
    }

    Customer {
        bigint id PK
        string first_name
        string last_name
        string email UK
    }

    Address {
        bigint id PK
        string street
        string city
        string state
        string country
        string zip_code
    }
```

---

## Tech Stack

| Technology | Proficiency |
|---|---|
| Java 21 / Spring Boot 3.2 | ██████████ |
| Angular 17 / TypeScript | ████████░░ |
| REST API Design | ██████████ |
| Stripe Payments | ████████░░ |
| OAuth2 / Okta | ████████░░ |
| JPA / Hibernate | ████████░░ |
| MySQL | ██████████ |
| HTTPS / SSL | ██████░░░░ |
| Git / CI | ██████░░░░ |

---

## 📁 Project Structure

```
E-commerce Project/
├── BackEnd/
│   └── Ecommerce/                        # Spring Boot 3.2 Application
│       ├── src/main/java/com/ismail/Ecommerce/
│       │   ├── config/
│       │   │   ├── SecurityConfiguration.java   # Okta OAuth2 + JWT config
│       │   │   ├── MyDataRestConfig.java        # CORS, ID exposure, HTTP method restrictions
│       │   │   └── MyAppConfig.java
│       │   ├── controller/
│       │   │   ├── CheckoutController.java      # POST /api/checkout/purchase
│       │   │   └── StripeController.java         # POST /api/checkout/create-payment-intent
│       │   ├── dao/                             # Spring Data JPA Repositories
│       │   │   ├── ProductRepository.java       # findByCategoryId, findByNameContaining
│       │   │   ├── CustomerRepository.java      # findByEmail
│       │   │   ├── OrderRepository.java
│       │   │   ├── ProductCategoryRepository.java
│       │   │   ├── CountryRepository.java
│       │   │   └── StateRepository.java
│       │   ├── dto/
│       │   │   ├── Purchase.java                # Order + Customer + Addresses + PaymentIntentId
│       │   │   ├── PurchaseResponse.java        # Order tracking number response
│       │   │   ├── PaymentInfo.java             # Amount + currency
│       │   │   └── PaymentResponse.java         # Stripe client secret
│       │   ├── entity/
│       │   │   ├── Product.java                 # @ManyToOne -> ProductCategory
│       │   │   ├── ProductCategory.java         # @OneToMany -> Product
│       │   │   ├── Order.java                   # @OneToMany -> OrderItem, @ManyToOne -> Customer
│       │   │   ├── OrderItem.java
│       │   │   ├── Customer.java
│       │   │   ├── Address.java
│       │   │   ├── Country.java
│       │   │   └── State.java
│       │   └── service/
│       │       ├── CheckoutService.java         # Interface
│       │       ├── CheckoutServicImplementation.java  # Place order + verify Stripe payment
│       │       └── StripeService.java           # Create & retrieve PaymentIntents
│       └── src/main/resources/
│           └── application.properties           # DB, Stripe, Okta, SSL config
│
└── Frontend/
    └── angular-Ecommerce/                      # Angular 17 Frontend
        ├── src/
        ├── ssl-localhost/                      # Local HTTPS certificates
        └── angular.json
```

---

## 🔌 API Endpoints

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/products` | Public | Paginated product list |
| `GET` | `/api/products/search/findByCategoryId?id={id}` | Public | Filter by category |
| `GET` | `/api/products/search/findByNameContaining?name={name}` | Public | Search by name |
| `GET` | `/api/product-category` | Public | All categories |
| `GET` | `/api/countries` | Public | All countries |
| `GET` | `/api/states` | Public | All states |
| `POST` | `/api/checkout/create-payment-intent` | Public | Creates Stripe PaymentIntent |
| `POST` | `/api/checkout/purchase` | Public | Places order (with Stripe verification) |
| `*` | `/api/orders/**` | **JWT Required** | Order CRUD (authenticated only) |

> All other entity endpoints are **read-only** — PUT/POST/PATCH/DELETE disabled for safety.

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- Node.js 18+ & npm
- MySQL 8.0+
- Stripe account (test keys)
- Okta account (for OAuth2)

### Backend Setup

```bash
cd BackEnd/Ecommerce

# Configure environment variables
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
export STRIPE_SECRET_KEY=sk_test_...
export OKTA_CLIENT_ID=...
export OKTA_ISSUER=https://{your-okta-domain}/oauth2/default
export SSL_PASSWORD=your-keystore-password

# Run the application (port 9898, HTTPS)
./mvnw spring-boot:run
```

### Frontend Setup

```bash
cd Frontend/angular-Ecommerce
npm install
ng serve --ssl --ssl-cert ./ssl-localhost/localhost.crt --ssl-key ./ssl-localhost/localhost.key
```

---

## 🛡️ Security Architecture

```mermaid
flowchart LR
    A[Angular App] -->|HTTPS + Bearer JWT| B[Spring Boot]
    B --> C{Okta JWT Decoder}
    C -->|Valid Token| D[Access /api/orders/**]
    C -->|Invalid/Expired| E[401 Unauthorized]
    A -->|No Token| F[Public Endpoints<br/>Products, Categories, Checkout]
    B --> G[CSRF Disabled<br/>Stateless Session]
    B --> H[CORS Whitelist<br/>localhost:4200]

    style A fill:#90caf9
    style B fill:#81c784
    style C fill:#ffb74d
    style D fill:#a5d6a7
    style E fill:#ef9a9a
```

---

## ⚡ Performance Considerations

- **Spring Data REST** auto-generates paginated, sorted queries — no boilerplate
- **JPA Lazy Loading** prevents N+1 on large product catalogs
- **Stripe API** called server-side — secret key never exposed to client
- **HTTPS** enforced globally; localhost dev also uses SSL certificates
- **MySQL 8** with proper indexing on `email` (unique), `category_id`, `order_tracking_number`

---

## 📬 Contact

Built by **Ismail** — feel free to connect or contribute!

[![GitHub](https://img.shields.io/badge/GitHub-Xframex-181717?style=for-the-badge&logo=github)](https://github.com/Xframex)
[![Repo](https://img.shields.io/badge/Repo-Ecommerce-6DB33F?style=for-the-badge&logo=github)](https://github.com/Xframex/Ecommerce)
