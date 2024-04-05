DELETE FROM Comprador
WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = '?');

DELETE FROM Usuario
WHERE nickname = '?'