-- INSERTAR NUEVO COMPRADOR
INSERT Usuario(nickname,correo,user_password,direccion,fecha_registro)
VALUES(?,?,?,?,?);

INSERT Comprador(id_usuario,puntos_responsabilidad)
SELECT id,0 FROM Usuario WHERE nickname = ?;

INSERT Carrito_Compra(id_comprador)
SELECT id FROM Usuario WHERE nickname = ?;

INSERT Guardar_Mas_Tarde(id_comprador)
SELECT id FROM Usuario WHERE nickname = ?;

INSERT Lista_De_Deseos(id_comprador)
SELECT id FROM Usuario WHERE nickname = ?;
-- INSERTAR NUEVO VENDEDOR
INSERT Usuario(nickname,correo,user_password,direccion,fecha_registro)
VALUES(?,?,?,?,?);

INSERT Vendedor(id_usuario)
SELECT id FROM Usuario WHERE nickname = ?;

-- INSERTAR NUEVO ADMINISTRADOR
INSERT Usuario(nickname,correo,user_password,direccion,fecha_registro)
VALUES(?,?,?,?,?);

INSERT Comprador(id_usuario,puntos_responsabilidad)
SELECT id,0 FROM Usuario WHERE nickname = ?