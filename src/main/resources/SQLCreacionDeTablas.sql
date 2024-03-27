-- Creación de tablas
-- Tabla CONSUMIDOR
CREATE TABLE CONSUMIDOR (
    id_consumidor INT PRIMARY KEY,
    nickname VARCHAR(50),
    user_password VARCHAR(50),
    direccion VARCHAR(255),
    puntos_responsabilidad INT
);

-- Datos de CONSUMIDOR
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('406', 'mgirardot0', 'pD2$!{|U72v6', '9th Floor', 1);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('663', 'mloody1', 'uH6~r0|l+q5?I', 'Apt 1892', 2);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('737', 'baire2', 'zX0%.0nD#"', '16th Floor', 3);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('777', 'cfeares3', 'xB7=$d5EpMZ`E#I|', 'PO Box 72149', 4);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('753', 'dpuckett4', 'lR4"3/\asb|8Z', 'Suite 9', 5);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('251', 'rslator5', 'yV7}nvn6', 'Suite 89', 6);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('701', 'ftufts6', 'gE5*M,%fp93<g', 'Apt 206', 7);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('038', 'aguittet7', 'yR5>2ClOm$', '4th Floor', 8);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('892', 'ndood8', 'lX0>kdy?B8ldR', 'Room 1498', 9);
insert into CONSUMIDOR (id_consumidor, nickname, user_password, direccion, puntos_responsabilidad) values ('399', 'fsisley9', 'rL8`1xXfR1K&gI', 'PO Box 49455', 10);


-- Tabla VENDEDOR
CREATE TABLE VENDEDOR (
    id_vendedor INT PRIMARY KEY,
    nombre_vendedor VARCHAR(50)
);

-- Datos VENDEDOR
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('880', 'Jabbercube');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('303', 'Thoughtbridge');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('062', 'Cogibox');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('281', 'Rhyloo');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('087', 'Dynabox');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('409', 'Jayo');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('796', 'Buzzdog');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('375', 'Zoomdog');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('807', 'Livetube');
insert into VENDEDOR (id_vendedor, nombre_vendedor) values ('368', 'Gabvine');

-- Tabla PRODUCTO
CREATE TABLE PRODUCTO (
    id_producto INT PRIMARY KEY,
    id_vendedor INT,
    precio DECIMAL(10, 2),
    material VARCHAR(50),
    descripcion VARCHAR(255),
    FOREIGN KEY (id_vendedor) REFERENCES VENDEDOR(id_vendedor)
);

-- Datos PRODUCTO
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (1, '796', 6.93, 'Glass', 'Bolso de mano');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (2, '087', 77.67, 'Brass', 'Reloj de pulsera');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (3, '303', 16.44, 'Wood', 'Camisa de algodón');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (4, '409', 86.01, 'Brass', 'Bolso de mano');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (5, '807', 29.38, 'Glass', 'Bolso de mano');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (6, '281', 89.7, 'Rubber', 'Camisa de algodón');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (7, '062', 27.78, 'Glass', 'Bolso de mano');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (8, '880', 81.85, 'Vinyl', 'Pantalones vaqueros');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (9, '062', 74.41, 'Plastic', 'Pantalones vaqueros');
insert into PRODUCTO (id_producto, id_vendedor, precio, material, descripcion) values (10, '087', 70.36, 'Granite', 'Pantalones vaqueros');

-- Tabla CARRITO COMPRA
CREATE TABLE CARRITO_COMPRA (
    id_carrito INT PRIMARY KEY,
    id_consumidor INT,
    FOREIGN KEY (id_consumidor) REFERENCES CONSUMIDOR(id_consumidor)
);

-- Datos CARRITO COMPRA
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (1, '663');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (2, '399');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (3, '399');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (4, '406');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (5, '892');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (6, '251');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (7, '892');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (8, '777');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (9, '737');
insert into CARRITO_COMPRA (id_carrito, id_consumidor) values (10, '251');


-- Tabla PRODUCTOS_CARRITO
CREATE TABLE PRODUCTOS_CARRITO (
    id_carrito INT,
    id_producto INT,
    FOREIGN KEY (id_carrito) REFERENCES CARRITO_COMPRA(id_carrito),
    FOREIGN KEY (id_producto) REFERENCES PRODUCTO(id_producto),
    PRIMARY KEY (id_carrito, id_producto)
);


-- Tabla LISTA DE DESEOS
CREATE TABLE LISTA_DE_DESEOS (
    id_lista_deseos INT PRIMARY KEY,
    id_consumidor INT,
    FOREIGN KEY (id_consumidor) REFERENCES CONSUMIDOR(id_consumidor)
);

-- Datos LISTA DE DESEOS
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (1, '701');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (2, '753');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (3, '406');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (4, '399');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (5, '892');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (6, '251');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (7, '038');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (8, '753');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (9, '038');
insert into LISTA_DE_DESEOS (id_lista_deseos, id_consumidor) values (10, '753');


-- Tabla PRODUCTOS_LISTA DESEOS
CREATE TABLE PRODUCTOS_LISTA_DESEOS (
    id_carrito INT,
    id_producto INT,
    FOREIGN KEY (id_carrito) REFERENCES LISTA_DE_DESEOS(id_lista_deseos),
    FOREIGN KEY (id_producto) REFERENCES PRODUCTO(id_producto),
    PRIMARY KEY (id_carrito, id_producto)
);

-- Tabla PEDIDO
CREATE TABLE PEDIDO (
    id_pedido INT PRIMARY KEY,
    id_consumidor INT,
    articulo VARCHAR(255),
    cantidad_de_producto INT,
    FOREIGN KEY (id_consumidor) REFERENCES CONSUMIDOR(id_consumidor)
);

-- Datos PEDIDO
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (1, '038', 'sombrero', 68);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (2, '892', 'camisa', 50);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (3, '399', 'sombrero', 12);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (4, '753', 'chaqueta', 40);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (5, '406', 'zapatos', 80);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (6, '399', 'chaqueta', 31);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (7, '777', 'sombrero', 68);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (8, '663', 'sombrero', 84);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (9, '038', 'camisa', 21);
insert into PEDIDO (id_pedido, id_consumidor, articulo, cantidad_de_producto) values (10, '737', 'pantalón', 29);

-- Tabla ENVÍO
CREATE TABLE ENVIO (
    id_envio INT PRIMARY KEY,
    id_pedido INT,
    estado_envio VARCHAR(50),
    coste_total_productos_enviados FLOAT,
    FOREIGN KEY (id_pedido) REFERENCES PEDIDO(id_pedido)
);

-- Datos ENVÍO
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (1, 3, 'Retenido', 267.71);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (2, 9, 'Pendiente', 10.91);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (3, 2, 'Devuelto', 291.5);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (4, 4, 'En tránsito', 963.64);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (5, 1, 'Entregado', 535.69);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (6, 8, 'Retenido', 758.23);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (7, 6, 'Entregado', 921.39);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (8, 8, 'Retenido', 335.1);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (9, 9, 'Entregado', 621.94);
insert into ENVIO (id_envio, id_pedido, estado_envio, coste_total_productos_enviados) values (10, 2, 'Devuelto', 19.29);


-- Tabla RETOS
CREATE TABLE RETOS (
    reto_id INT PRIMARY KEY,
    recompensa VARCHAR(255)
);

-- Datos RETOS
insert into RETOS (reto_id, recompensa) values (1, 'diamante');
insert into RETOS (reto_id, recompensa) values (2, 'diamante');
insert into RETOS (reto_id, recompensa) values (3, 'prata');
insert into RETOS (reto_id, recompensa) values (4, 'platina');
insert into RETOS (reto_id, recompensa) values (5, 'platina');
insert into RETOS (reto_id, recompensa) values (6, 'prata');
insert into RETOS (reto_id, recompensa) values (7, 'bronze');
insert into RETOS (reto_id, recompensa) values (8, 'prata');
insert into RETOS (reto_id, recompensa) values (9, 'platina');
insert into RETOS (reto_id, recompensa) values (10, 'prata');



-- Tabla PRODUCTO_GUARDAR_MAS_TARDE
CREATE TABLE PRODUCTO_GUARDAR_MAS_TARDE (
    id_guardar_mas_tarde INT PRIMARY KEY,
    id_producto INT,
    FOREIGN KEY (id_producto) REFERENCES PRODUCTO(id_producto)
);

-- Datos PRODUCTO_GUARDAR_MAS_TARDE
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (1, 7);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (2, 8);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (3, 2);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (4, 9);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (5, 1);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (6, 9);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (7, 3);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (8, 3);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (9, 3);
insert into PRODUCTO_GUARDAR_MAS_TARDE (id_guardar_mas_tarde, id_producto) values (10, 10);



-- Tabla GUARDAR_MAS_TARDE
CREATE TABLE GUARDAR_MAS_TARDE (
    id_guardar_mas_tarde INT,
    id_consumidor INT,
    FOREIGN KEY (id_guardar_mas_tarde) REFERENCES PRODUCTO_GUARDAR_MAS_TARDE(id_guardar_mas_tarde),
    FOREIGN KEY (id_consumidor) REFERENCES CONSUMIDOR(id_consumidor),
    PRIMARY KEY (id_guardar_mas_tarde, id_consumidor)
);

-- Datos GUARDAR_MAS_TARDE



-- Tabla CONSEJOS (parece estar relacionada con los consumidores, aunque en el diagrama no se especifica claramente)
CREATE TABLE CONSEJOS (
    consejo_id INT PRIMARY KEY,
    id_producto INT,
    FOREIGN KEY (id_producto) REFERENCES PRODUCTO(id_producto)
);

-- Datos CONSEJOS
insert into CONSEJOS (consejo_id, id_producto) values (1, 8);
insert into CONSEJOS (consejo_id, id_producto) values (2, 5);
insert into CONSEJOS (consejo_id, id_producto) values (3, 10);
insert into CONSEJOS (consejo_id, id_producto) values (4, 2);
insert into CONSEJOS (consejo_id, id_producto) values (5, 8);
insert into CONSEJOS (consejo_id, id_producto) values (6, 10);
insert into CONSEJOS (consejo_id, id_producto) values (7, 10);
insert into CONSEJOS (consejo_id, id_producto) values (8, 3);
insert into CONSEJOS (consejo_id, id_producto) values (9, 8);
insert into CONSEJOS (consejo_id, id_producto) values (10, 9);



