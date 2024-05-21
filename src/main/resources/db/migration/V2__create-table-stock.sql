-- Estoque
CREATE TABLE stock (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    product_id INT NOT NULL,
    size VARCHAR(50),
    color jsonb,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Barcelona
INSERT INTO stock (product_id, size, color, quantity, price)
VALUES
(1, 'P', '["blue","garnet"]', 50, 139.99),
(1, 'M', '["blue","garnet"]', 50, 139.99),
(1, 'G', '["blue","garnet"]', 50, 139.99),

-- Real Madrid
(2, 'P', '["white"]', 50, 142.99),
(2, 'M', '["white"]', 50, 142.99),
(2, 'G', '["white"]', 50, 142.99),

-- Manchester United
(3, 'P', '["red"]', 50, 134.99),
(3, 'M', '["red"]', 50, 134.99),
(3, 'G', '["red"]', 50, 134.99),

-- Chelsea
(4, 'P', '["blue"]', 50, 136.99),
(4, 'M', '["blue"]', 50, 136.99),
(4, 'G', '["blue"]', 50, 136.99),

-- Bayern Munich
(5, 'P', '["red","white"]', 50, 138.99),
(5, 'M', '["red","white"]', 50, 138.99),
(5, 'G', '["red","white"]', 50, 138.99),

-- Paris Saint-Germain
(6, 'P', '["blue","red"]', 50, 141.99),
(6, 'M', '["blue","red"]', 50, 141.99),
(6, 'G', '["blue","red"]', 50, 141.99),

-- Juventus
(7, 'P', '["black","white"]', 50, 137.99),
(7, 'M', '["black","white"]', 50, 137.99),
(7, 'G', '["black","white"]', 50, 137.99),

-- Liverpool
(8, 'P', '["red"]', 50, 140.99),
(8, 'M', '["red"]', 50, 140.99),
(8, 'G', '["red"]', 50, 140.99),

-- Atletico Madrid
(9, 'P', '["red","white"]', 50, 135.99),
(9, 'M', '["red","white"]', 50, 135.99),
(9, 'G', '["red","white"]', 50, 135.99),

-- Arsenal
(10, 'P', '["red","white"]', 50, 139.99),
(10, 'M', '["red","white"]', 50, 139.99),
(10, 'G', '["red","white"]', 50, 139.99),

-- Borussia Dortmund
(11, 'P', '["yellow","black"]', 50, 133.99),
(11, 'M', '["yellow","black"]', 50, 133.99),
(11, 'G', '["yellow","black"]', 50, 133.99),

-- Inter Milan
(12, 'P', '["blue","black"]', 50, 136.99),
(12, 'M', '["blue","black"]', 50, 136.99),
(12, 'G', '["blue","black"]', 50, 136.99),

-- AC Milan
(13, 'P', '["red","black"]', 50, 138.99),
(13, 'M', '["red","black"]', 50, 138.99),
(13, 'G', '["red","black"]', 50, 138.99),

-- Tottenham Hotspur
(14, 'P', '["white","blue"]', 50, 134.99),
(14, 'M', '["white","blue"]', 50, 134.99),
(14, 'G', '["white","blue"]', 50, 134.99),

-- Ajax
(15, 'P', '["red","white"]', 50, 137.99),
(15, 'M', '["red","white"]', 50, 137.99),
(15, 'G', '["red","white"]', 50, 137.99),

-- Manchester City
(16, 'P', '["blue"]', 50, 140.99),
(16, 'M', '["blue"]', 50, 140.99),
(16, 'G', '["blue"]', 50, 140.99),

-- Napoli
(17, 'P', '["blue"]', 50, 142.99),
(17, 'M', '["blue"]', 50, 142.99),
(17, 'G', '["blue"]', 50, 142.99),

-- Roma
(18, 'P', '["red","yellow"]', 50, 134.99),
(18, 'M', '["red","yellow"]', 50, 134.99),
(18, 'G', '["red","yellow"]', 50, 134.99),

-- Bayer Leverkusen
(19, 'P', '["red","black"]', 50, 136.99),
(19, 'M', '["red","black"]', 50, 136.99),
(19, 'G', '["red","black"]', 50, 136.99),

-- Sevilla
(20, 'P', '["white","red"]', 50, 141.99),
(20, 'M', '["white","red"]', 50, 141.99),
(20, 'G', '["white","red"]', 50, 141.99),

-- Lyon
(21, 'P', '["blue","white","red"]', 50, 139.99),
(21, 'M', '["blue","white","red"]', 50, 139.99),
(21, 'G', '["blue","white","red"]', 50, 139.99),

-- Everton
(22, 'P', '["blue"]', 50, 137.99),
(22, 'M', '["blue"]', 50, 137.99),
(22, 'G', '["blue"]', 50, 137.99),

-- Leicester
(23, 'P', '["blue"]', 50, 138.99),
(23, 'M', '["blue"]', 50, 138.99),
(23, 'G', '["blue"]', 50, 138.99),

-- Wolverhampton
(24, 'P', '["orange","black"]', 50, 135.99),
(24, 'M', '["orange","black"]', 50, 135.99),
(24, 'G', '["orange","black"]', 50, 135.99),

-- Athletic Bilbao
(25, 'P', '["red","white"]', 50, 140.99),
(25, 'M', '["red","white"]', 50, 140.99),
(25, 'G', '["red","white"]', 50, 140.99),

-- Valencia
(26, 'P', '["orange"]', 50, 133.99),
(26, 'M', '["orange"]', 50, 133.99),
(26, 'G', '["orange"]', 50, 133.99),

-- Villarreal
(27, 'P', '["yellow"]', 50, 142.99),
(27, 'M', '["yellow"]', 50, 142.99),
(27, 'G', '["yellow"]', 50, 142.99),

-- Celtic
(28, 'P', '["green","white"]', 50, 134.99),
(28, 'M', '["green","white"]', 50, 134.99),
(28, 'G', '["green","white"]', 50, 134.99),

-- Porto
(29, 'P', '["blue","white"]', 50, 136.99),
(29, 'M', '["blue","white"]', 50, 136.99),
(29, 'G', '["blue","white"]', 50, 136.99),

-- Benfica
(30, 'P', '["red","white"]', 50, 138.99),
(30, 'M', '["red","white"]', 50, 138.99),
(30, 'G', '["red","white"]', 50, 138.99);