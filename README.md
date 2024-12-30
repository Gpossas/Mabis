# Digital Menu Order System

## How it works
- The waiter check-in the table for the clients -> the table status is set to active and is generated a secret token for the table and uploaded as QR code to S3
- Clients scan the QR code to get the secret token
- Only clients with the secret token of the table OR users with OWNER/WAITER roles can place orders
- Users with OWNER/COOK roles can update order status
- When the table is check-out the token is removed and the table status is set to inactive

## Features
- When the client scan the table QR code
- Used Strategy and Factory patterns to allow an specific client to choose which storage service to upload images on the fly. (made just because I was curious in how to develop that, this is not a multi-tenant application)
- Upload QR code and Menu itens images to S3 by defualt, made decoupled to allow other storage services
- Generate QR code with secret token to allow the clients in the table to place orders
  
## Technologies Used
- Backend: Spring Boot, Spring Security
- Authentication: Spring security with JSON Web Tokens (JWT) and roles
- Storage service: Amazon S3
- QR Code Generation: ZXing library

## Setup
- Update application.properties with your database and S3 credentials.
- Create JWT_SECRET env variable
