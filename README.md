## cqs & ddd demo 

This application describes how cqs (command query separation) and with some DDD (Domain Driven Design) building blocks 
can be use to structure a single BundleContext within a microservice architecture (for example).

### Running the application

You can run your application:
```
like a normal java or maven app
```

### Rest End-Points

1. Add a User:

   POST URL: localhost:8080/users
   
   Request Body:

   { "userId": , "firstName": "HWUX", "lastName": "HXZE", "email": "gksr@gmail.com"}

2. Get User by Id:

   GET URL: localhost:8080/users/{userId}

3. Ge all users:

   GET URL: localhost:8080/users