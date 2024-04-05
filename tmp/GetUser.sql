SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro, c.puntos_responsabilidad
FROM Usuario u, Comprador c 
WHERE c.id_usuario = u.id 	AND u.nickname = ?


DELETE FROM Comprador
WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?);

DELETE FROM Usuario
WHERE nickname = ?;

DELETE FROM Carrito_Compra
WHERE id_comprador IN(SELECT id FROM Usuario WHERE nickname = ?);

DELETE FROM Guardar_Mas_Tarde
WHERE id IN(SELECT id FROM Usuario WHERE nickname = ?);

DELETE FROM Lista_De_Deseos
WHERE id IN(SELECT id FROM Usuario WHERE nickname = ?);