#!/bin/bash

# Flight Booking System API Test Script
echo "🛫 Testing Flight Booking System APIs"
echo "======================================"

BASE_URL="http://localhost:8080"

echo "1. Testing Flight Search..."
curl -s "$BASE_URL/api/flights/search?origin=NYC&destination=LAX&departureDate=2024-12-25T08:00:00" | jq '.'

echo -e "\n2. Testing Flight Details..."
curl -s "$BASE_URL/api/flights/1" | jq '.'

echo -e "\n3. Testing Price Calculation..."
curl -s "$BASE_URL/api/pricing/calculate?flightId=1&seats=2" | jq '.'

echo -e "\n4. Testing Booking Creation..."
curl -s -X POST "$BASE_URL/api/bookings" \
  -H "Content-Type: application/json" \
  -d '{
    "flightId": 1,
    "passengerName": "John Doe",
    "passengerEmail": "john@example.com",
    "seats": 2
  }' | jq '.'

echo -e "\n5. Testing Inventory Check..."
curl -s "$BASE_URL/api/inventory/flight/1" | jq '.'

echo -e "\n6. Testing Payment Processing..."
curl -s -X POST "$BASE_URL/api/payments/process" \
  -H "Content-Type: application/json" \
  -d '{
    "bookingReference": "BK12345678",
    "amount": 599.98,
    "paymentMethod": "CREDIT_CARD"
  }' | jq '.'

echo -e "\n✅ API Testing Complete!"