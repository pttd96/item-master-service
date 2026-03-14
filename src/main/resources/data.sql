INSERT INTO buyer (id, email, name, created_time, updated_time) VALUES (1, 'alice@example.com', 'Alice', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO buyer (id, email, name, created_time, updated_time) VALUES (2, 'bob@example.com', 'Bob', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO buyer (id, email, name, created_time, updated_time) VALUES (3, 'charlie@example.com', 'Charlie', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO item (id, item_class, subclass, department, price, buyer_id, created_time, updated_time) VALUES (1, 'Electronics', 'Phone', 'Mobile', 699.99, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO item (id, item_class, subclass, department, price, buyer_id, created_time, updated_time) VALUES (2, 'Electronics', 'Laptop', 'Computers', 1199.50, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO item (id, item_class, subclass, department, price, buyer_id, created_time, updated_time) VALUES (3, 'Home', 'Vacuum', 'Cleaning', 249.99, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO item (id, item_class, subclass, department, price, buyer_id, created_time, updated_time) VALUES (4, 'Home', 'Cookware', 'Kitchen', 89.90, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO item (id, item_class, subclass, department, price, buyer_id, created_time, updated_time) VALUES (5, 'Sports', 'Treadmill', 'Fitness', 899.99, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO item (id, item_class, subclass, department, price, buyer_id, created_time, updated_time) VALUES (6, 'Clothing', 'Jacket', 'Apparel', 129.95, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO item (id, item_class, subclass, department, price, buyer_id, created_time, updated_time) VALUES (7, 'Clothing', 'Sneakers', 'Footwear', 149.90, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
