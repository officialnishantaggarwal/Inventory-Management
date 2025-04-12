INSERT INTO orders (total_price, order_status) VALUES
(120.50, 'PENDING'),
(89.99, 'SHIPPED'),
(250.00, 'DELIVERED'),
(45.00, 'CANCELLED'),
(310.75, 'PENDING'),
(199.99, 'SHIPPED'),
(150.30, 'PENDING'),
(78.60, 'DELIVERED'),
(500.00, 'SHIPPED'),
(60.00, 'PENDING');

INSERT INTO order_item (order_id, product_id, quantity) VALUES
(1, 101, 2),
(1, 102, 1),
(2, 103, 1),
(2, 104, 2),
(3, 105, 5),
(4, 106, 1),
(5, 107, 2),
(5, 108, 1),
(5, 109, 3),
(6, 110, 2),
(7, 111, 2),
(7, 112, 1),
(8, 113, 1),
(9, 114, 4),
(9, 115, 2),
(10, 116, 2);