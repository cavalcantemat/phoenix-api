-- Produtos
CREATE TABLE products (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

INSERT INTO products (name, description)
VALUES
('Barcelona', ''),
('Real Madrid', 'A tradição ganha um toque de ouro nesta camisa 1 do Real Madrid 23/24. Mantendo uma base toda branca e elegante, o uniforme continua a linha do design imponente, produzindo uma camisa leve inspirada na lendária história do futebol. <br> <br> Homenageando os uniformes mais antigos, os detalhes dourados dos uniformes anteriores foram reintroduzidos junto com o tom azul-marinho. Uma estampa sublimada atualiza a clássica cor branca e uma nova silhueta dá um toque mais casual. O tecido HEAT.RDY maximiza o fluxo de ar para manter sua pele refrescada e o escudo estampado por calor, além da inscrição \"¡Hala Madrid!\" na gola mostram sua mentalidade vitoriosa.'),
('Manchester United', ''),
('Chelsea', ''),
('Bayern Munich', ''),
('Paris Saint-Germain', ''),
('Juventus', ''),
('Liverpool', ''),
('Atletico Madrid', ''),
('Arsenal', ''),
('Borussia Dortmund', ''),
('Inter Milan', ''),
('AC Milan', ''),
('Tottenham Hotspur', ''),
('Ajax', ''),
('Manchester City', ''),
('Napoli', ''),
('Roma', ''),
('Bayer Leverkusen', ''),
('Sevilla', ''),
('Lyon', ''),
('Everton', ''),
('Leicester City', ''),
('Wolverhampton', ''),
('Athletic Bilbao', ''),
('Valencia', ''),
('Villarreal', ''),
('Celtic', ''),
('Porto', ''),
('Benfica', '');