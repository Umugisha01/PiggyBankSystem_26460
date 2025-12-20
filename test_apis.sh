#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== PiggyBank API Testing Script ==="

# 1. Register User
echo "1. Registering new user..."
curl -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "email": "test@example.com",
    "password": "password123",
    "phoneNumber": "+250788123456",
    "locationId": 1
  }'

echo -e "\n\n2. Login user..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }')

TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "JWT Token: $TOKEN"

# 3. Test protected endpoints
echo -e "\n\n3. Getting user profile..."
curl -X GET "$BASE_URL/api/users/1" \
  -H "Authorization: Bearer $TOKEN"

echo -e "\n\n4. Deposit money..."
curl -X POST "$BASE_URL/api/accounts/1/deposit?amount=1000" \
  -H "Authorization: Bearer $TOKEN"

echo -e "\n\n5. Check balance..."
curl -X GET "$BASE_URL/api/accounts/user/1/balance" \
  -H "Authorization: Bearer $TOKEN"

echo -e "\n\n6. Get users by location..."
curl -X GET "$BASE_URL/api/users/location/province/Kigali" \
  -H "Authorization: Bearer $TOKEN"

echo -e "\n\nAPI Testing Complete!"