
## Kredencijali
- **USER**: `user@uns.ac.rs`, password
- **ADMIN**: `admin@uns.ac.rs`, password
- **OWNER**: `owner@uns.ac.rs`, password

---

## CurrencyExchange
- **GET**: `http://localhost:8765/currency-exchange?from=RSD&to=USD`

---

## CryptoExchange
- **GET**: `http://localhost:8765/crypto-exchange?from=ETH&to=BTC`

---

## Users

### Autorizovani OWNER and ADMIN
- **GET**: `http://localhost:8765/users`

### Autorizovan OWNER
- **POST**: `http://localhost:8765/users`
  - **Body**:
    ```json
    {
        "email": "novi.admin@uns.ac.rs",
        "password": "password123",
        "role": "ADMIN"
    }
    ```
- **PUT**: `http://localhost:8765/users`
  - **Body**:
    ```json
    {
        "email": "novi.admin@uns.ac.rs",
        "password": "password1",
        "role": "USER"
    }
    ```
- **DELETE**: `http://localhost:8765/users/id/5`

### Autorizovan USER
- **POST**: `http://localhost:8765/users`
  - **Body**:
    ```json
    {
        "email": "novi.user@uns.ac.rs",
        "password": "password1",
        "role": "USER"
    }
    ```
- **PUT**: `http://localhost:8765/users`
  - **Body**:
    ```json
    {
        "email": "novi.user@uns.ac.rs",
        "password": "password123",
        "role": "USER"
    }
    ```

---

## BankAccount

### Autorizovan ADMIN
- **GET**: `http://localhost:8765/bank-account`
- **POST**: `http://localhost:8765/bank-account/create`
  - **Body**:
    ```json
    {
        "email": "milos@uns.ac.rs",
        "eur_amount": 700.00,
        "gbp_amount": 750.00,
        "chf_amount": 500.00,
        "usd_amount": 1000.00,
        "rsd_amount": 50000.00
    }
    ```
- **PUT**: `http://localhost:8765/bank-account/update`
  - **Body**:
    ```json
    {
        "email": "milos@uns.ac.rs",
        "eur_amount": 1250.00,
        "gbp_amount": 1000.00,
        "chf_amount": 300.00,
        "usd_amount": 1500.00,
        "rsd_amount": 450000.00
    }
    ```

### Autorizovan USER
- **GET**: `http://localhost:8765/bank-account/myAccount`

---

## CryptoWallet

### Autorizovan ADMIN
- **GET**: `http://localhost:8765/crypto-wallet`
- **POST**: `http://localhost:8765/crypto-wallet/create`
  - **Body**:
    ```json
    {
        "email": "milos@uns.ac.rs",
        "btc_amount": 1.00,
        "eth_amount": 12.00,
        "bnb_amount": 250.00
    }
    ```
- **PUT**: `http://localhost:8765/crypto-wallet/update`
  - **Body**:
    ```json
    {
        "email": "milos@uns.ac.rs",
        "btc_amount": 1.00,
        "eth_amount": 14.00,
        "bnb_amount": 230.00
    }
    ```

### Autorizovan USER
- **GET**: `http://localhost:8765/crypto-wallet/myWallet`

---

## CurrencyConversion(Autorizovan USER)
- **GET**: `http://localhost:8765/currency-conversion?from=EUR&to=USD&quantity=200`

---

## CryptoConversion(Autorizovan USER)
- **GET**: `http://localhost:8765/crypto-conversion?from=BTC&to=ETH&quantity=1`

---

## TradeService(Autorizovan USER)
- **GET**: `http://localhost:8765/trade-service?from=ETH&to=RSD&quantity=1`
- **GET**: `http://localhost:8765/trade-service?from=USD&to=ETH&quantity=1250`

---
