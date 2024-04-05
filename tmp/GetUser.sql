SELECT u.nickname, u.correo, u.user_password,u.direccion,u.fecha_registro, c.puntos_responsabilidad
FROM Usuario u, Comprador c 
WHERE c.id_usuario = u.id 	AND u.nickname = ?