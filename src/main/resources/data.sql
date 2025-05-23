-- User Data
-- Passwords: admin123 and user123 (BCrypt encoded)
INSERT INTO users (id, username, password, roles, created_at, updated_at) 
VALUES 
    ('3a2c1e4b-5d8f-42a1-9b7c-8e5d3f2a1c4b', 'admin', '$2a$10$qdeu.smterURBKOdu7zd1u4IX0eGJ9Qmwqk5Qb.HzU51bsedcRHqm', 'ADMIN', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('4b2d1c5a-6e1f-47b2-8c3d-9a4e5f6b7c8d', 'user', '$2a$10$/obHOQz5/1K/YV.A5A2GHe8Ci3z1pl22j6/ildCx.NhvHVGptgth2', 'USER', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Event Data
INSERT INTO events (id, name, date, venue, created_at, updated_at)
VALUES
    ('5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c', 'Summer Music Festival', '2023-07-15 14:00:00Z', 'Central Park', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('6d4e3b2a-8e5f-49b3-1d6e-2b4a6c8e0d2a', 'Tech Conference 2023', '2023-09-25 09:00:00Z', 'Convention Center', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('7e5f4c3b-9f6f-50c4-2e7f-3c5b7d9f1e3b', 'Comedy Night', '2023-06-10 19:30:00Z', 'Laugh Factory', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Ticket Data
INSERT INTO tickets (id, code, event_id, attendee_name, status, purchase_date, created_at, updated_at)
VALUES
    ('8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c', 'SMF-001', '5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c', 'John Smith', 'ACTIVE', '2023-05-20 10:15:00Z', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('9f7a6e5f-2b8c-52e6-4a9b-5e7d9f1a3b5d', 'SMF-002', '5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c', 'Jane Doe', 'ACTIVE', '2023-05-21 14:30:00Z', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('1a8b7f6a-3c9d-53f7-5a1b-6f8e0a2b4c6e', 'TC-001', '6d4e3b2a-8e5f-49b3-1d6e-2b4a6c8e0d2a', 'Robert Johnson', 'ACTIVE', '2023-08-15 09:45:00Z', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('2b9c8a7b-4d1e-54a8-6b2c-7a9f1b3c5d7f', 'TC-002', '6d4e3b2a-8e5f-49b3-1d6e-2b4a6c8e0d2a', 'Sarah Williams', 'USED', '2023-08-16 11:20:00Z', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('3c1d9a8b-5e2f-55a9-7c3d-8a1b2c4d6e8a', 'CN-001', '7e5f4c3b-9f6f-50c4-2e7f-3c5b7d9f1e3b', 'Michael Brown', 'ACTIVE', '2023-06-01 15:10:00Z', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());