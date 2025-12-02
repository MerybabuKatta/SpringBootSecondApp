-- Sample flight data
INSERT INTO flights (flight_number, airline, origin, destination, departure_time, arrival_time, total_seats, available_seats, base_price) VALUES
('AA101', 'American Airlines', 'NYC', 'LAX', '2024-12-25 08:00:00', '2024-12-25 11:30:00', 180, 150, 299.99),
('UA202', 'United Airlines', 'NYC', 'LAX', '2024-12-25 14:00:00', '2024-12-25 17:30:00', 200, 180, 319.99),
('DL303', 'Delta Airlines', 'LAX', 'NYC', '2024-12-26 09:00:00', '2024-12-26 17:30:00', 160, 140, 289.99),
('AA404', 'American Airlines', 'CHI', 'MIA', '2024-12-25 10:00:00', '2024-12-25 13:00:00', 150, 120, 249.99),
('UA505', 'United Airlines', 'MIA', 'CHI', '2024-12-26 15:00:00', '2024-12-26 18:00:00', 170, 160, 259.99),
('DL606', 'Delta Airlines', 'NYC', 'CHI', '2024-12-25 12:00:00', '2024-12-25 14:30:00', 140, 100, 199.99),
('AA707', 'American Airlines', 'CHI', 'NYC', '2024-12-26 16:00:00', '2024-12-26 18:30:00', 140, 130, 209.99),
('UA808', 'United Airlines', 'LAX', 'MIA', '2024-12-25 11:00:00', '2024-12-25 18:00:00', 190, 170, 399.99);