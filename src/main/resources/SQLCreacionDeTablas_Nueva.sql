BEGIN TRY
    BEGIN TRAN;
    ---------SCRIPT--------------------------
	CREATE TABLE Usuario ( 
            id INT AUTO_INCREMENT PRIMARY KEY,
            nickname VARCHAR(255) NOT NULL UNIQUE,
            correo VARCHAR(255) NOT NULL UNIQUE,
            user_password VARCHAR(255) NOT NULL,
            direccion VARCHAR(255),
            fecha_registro DATE NOT NULL DEFAULT CURRENT_DATE
      );
      
      

      CREATE TABLE Administrador ( 
            id INT AUTO_INCREMENT PRIMARY KEY, 
            id_usuario INT NOT NULL UNIQUE CONSTRAINT FK_Administrador REFERENCES Usuario(id)
      );
      
      CREATE TABLE Vendedor ( 
            id INT AUTO_INCREMENT PRIMARY KEY, 
            id_usuario INT NOT NULL UNIQUE CONSTRAINT FK_Administrador REFERENCES Usuario(id)

      );

      CREATE TABLE Comprador ( 
            id INT AUTO_INCREMENT PRIMARY KEY, 
            id_usuario INT NOT NULL UNIQUE CONSTRAINT FK_Administrador REFERENCES Usuario(id),
            puntos_responsabilidad INT NOT NULL CHECK(puntos_responsabilidad >= 0) DEFAULT 0
      );
      
      
      
      /*CREATE TABLE [dbo].[Comprador](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nickname] [nvarchar](50) NOT NULL,
	[correo] [nvarchar](50) NOT NULL,
	[user_password] [nvarchar](50) NOT NULL,
	[direccion] [nvarchar](50) NULL,
	[puntos_responsabilidad] [INT] NOT NULL,CONSTRAINT [PK_Comprador] PRIMARY KEY CLUSTERED ([id] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) 
      ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Comprador]  WITH CHECK ADD  CONSTRAINT [CK_Comprador] CHECK  (([puntos_responsabilidad]>=(0)))
      ALTER TABLE [dbo].[Comprador] CHECK CONSTRAINT [CK_Comprador]*/

--Compradores

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('mgirardot0','mgirardot0@gmail.com','uhyrfyjH6~r0|l+q5?I','9th Floor', '2024-02-20');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('mloody1','mloody1@gmail.com','uH6~r0|l+q5?I','Apt 1892', '2024-03-12');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('baire2','baire2@gmail.com','zX0%.0nD#','16th Floor', '2024-01-18');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('cfeares3','cfeares3@gmail.com','xB7=$d5EpMZ`E#I','PO Box 72149', '2024-02-05');
		  
INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('dpuckett4','dpuckett4@gmail.com','lR4"3/\asb|8Z','Suite 9', '2024-03-28');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('rslator5','rslator5@gmail.com','yV7}nvn6','Suite 89', '2024-01-30');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('tufts6','tufts6@gmail.com','gE5*M,%fp93<g','Apt 206', '2024-02-10');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('aguittet7','aguittet7@gmail.com','yR5>2ClOm$','4th Floor', '2024-03-05'); 

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('ndood8','ndood8@gmail.com','lX0>kdy?B8ldR','Room 1498', '2024-01-22');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('fsisley9','fsisley9@gmail.com','rL8`1xXfR1K&gI','PO Box 49455', '2024-02-15');

--Vendedores

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Jabbercube','jabbercube@gmail.com','jabbercube','Dirección de Jabbercube', '2024-01-05');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Thoughtbridge','thoughtbridge@gmail.com','thoughtbridge','Dirección de Thoughtbridge', '2024-03-20');
		   
INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Cogibox','cogibox@gmail.com','cogibox','Dirección de Cogibox', '2024-02-08');
		   
INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Rhyloo','rhyloo@gmail.com','rhyloo','Dirección de Rhyloo', '2024-01-10');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Dynabox','dynabox@gmail.com','dynabox','Dirección de Dynabox', '2024-03-02');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Jayo','jayo@gmail.com','jayo','Dirección de Jayo', '2024-02-25');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Buzzdog','buzzdog@gmail.com','buzzdog','Dirección de Buzzdog', '2024-01-15');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Zoomdog','zoomdog@gmail.com','zoomdog','Dirección de Zoomdog', '2024-03-08');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Livetube','livetube@gmail.com','livetube','Dirección de Livetube', '2024-02-20');

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('Gabvine','gabvine@gmail.com','gabvine','Dirección de Gabvine', '2024-01-28');

--Administrador

INSERT INTO [dbo].[Usuario]([nickname],[correo],[user_password],[direccion], [fecha_registro])
VALUES('admin','five_guys@gmail.com','admin','!G-0.4', '2024-01-29');



-- Inserts para la tabla Comprador


INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'mgirardot0' AND correo = 'mgirardot0@gmail.com' AND user_password = 'uhyrfyjH6~r0|l+q5?I' AND direccion = '9th Floor' AND fecha_registro = '2024-02-20';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'mloody1' AND correo = 'mloody1@gmail.com' AND user_password = 'uH6~r0|l+q5?I' AND direccion = 'Apt 1892' AND fecha_registro = '2024-03-12';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'baire2' AND correo = 'baire2@gmail.com' AND user_password = 'zX0%.0nD#' AND direccion = '16th Floor' AND fecha_registro = '2024-01-18';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'cfeares3' AND correo = 'cfeares3@gmail.com' AND user_password = 'xB7=$d5EpMZ`E#I' AND direccion = 'PO Box 72149' AND fecha_registro = '2024-02-05';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'dpuckett4' AND correo = 'dpuckett4@gmail.com' AND user_password = 'lR4"3/\asb|8Z' AND direccion = 'Suite 9' AND fecha_registro = '2024-03-28';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'rslator5' AND correo = 'rslator5@gmail.com' AND user_password = 'yV7}nvn6' AND direccion = 'Suite 89' AND fecha_registro = '2024-01-30';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'tufts6' AND correo = 'tufts6@gmail.com' AND user_password = 'gE5*M,%fp93<g' AND direccion = 'Apt 206' AND fecha_registro = '2024-02-10';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'aguittet7' AND correo = 'aguittet7@gmail.com' AND user_password = 'yR5>2ClOm$' AND direccion = '4th Floor' AND fecha_registro = '2024-03-05';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'ndood8' AND correo = 'ndood8@gmail.com' AND user_password = 'lX0>kdy?B8ldR' AND direccion = 'Room 1498' AND fecha_registro = '2024-01-22';

INSERT INTO Comprador (id_usuario, puntos_responsabilidad)
SELECT id, 0 FROM Usuario WHERE nickname = 'fsisley9' AND correo = 'fsisley9@gmail.com' AND user_password = 'rL8`1xXfR1K&gI' AND direccion = 'PO Box 49455' AND fecha_registro = '2024-02-15';


--Inserts para la tabla Vendedor

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Jabbercube')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Thoughtbridge')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Cogibox')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Rhyloo')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Dynabox')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Jayo')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Buzzdog')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Zoomdog')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Livetube')
);

INSERT INTO Vendedor (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'Gabvine')
);


--Insert tabla Administrador

INSERT INTO Administrador (id_usuario) VALUES (
    (SELECT id FROM Usuario WHERE nickname = 'admin')
);






/*CREATE TABLE [dbo].[Vendedor](
      [id] [int] IDENTITY(1,1) NOT NULL,[nickname] [nvarchar](50) NOT NULL,CONSTRAINT [PK_Vendedor] PRIMARY KEY CLUSTERED 
      ([id] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]*/



CREATE TABLE [dbo].[Categoria]([id] [int] IDENTITY(1,1) NOT NULL,[nombre] [nvarchar](50) NOT NULL,[categoria_principal] [nvarchar](50) NULL,
CONSTRAINT [PK_Categoria] PRIMARY KEY CLUSTERED ([id] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) 
ON [PRIMARY]) ON [PRIMARY]

INSERT INTO [dbo].[Categoria]([nombre],[categoria_principal])VALUES('Alimentación',NULL);
INSERT INTO [dbo].[Categoria]([nombre],[categoria_principal])VALUES('Moda',NULL);
INSERT INTO [dbo].[Categoria]([nombre],[categoria_principal])VALUES('Electrónica',NULL);
INSERT INTO [dbo].[Categoria]([nombre],[categoria_principal])VALUES('Higiene',NULL);


CREATE TABLE [dbo].[Producto]([id] [int] IDENTITY(1,1) NOT NULL,[nombre] [nvarchar](50) NOT NULL,[id_vendedor] [int] NOT NULL,
[precio] [int] NOT NULL,[material] [nvarchar](50) NOT NULL,[descripcion] [nvarchar](255) NOT NULL,[id_categoria] [int] NULL,
CONSTRAINT [PK_Producto] PRIMARY KEY CLUSTERED ([id] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
ALTER TABLE [dbo].[Producto]  WITH CHECK ADD  CONSTRAINT [FK_Producto_Categoria] FOREIGN KEY([id_categoria])REFERENCES [dbo].[Categoria] ([id])
ALTER TABLE [dbo].[Producto] CHECK CONSTRAINT [FK_Producto_Categoria]
ALTER TABLE [dbo].[Producto]  WITH CHECK ADD  CONSTRAINT [CK_Producto] CHECK  (([precio]>=(0)))
ALTER TABLE [dbo].[Producto] CHECK CONSTRAINT [CK_Producto]

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Bolso',3,40,'Glass','Bolso de mano',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Reloj',5,77.67,'Brass','Reloj de pulsera',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Camisa',7,16.44,'Wood','Camisa de algodón',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Bolso',9,40.90,'Brass','Bolso de mano',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Bolso',1,90,'Glass','Bolso de mano',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Camisa',8,250.8,'Rubber','Camisa de algodón',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Bolso',10,90.9,'Glass','Bolso de mano',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Pantalones',6,78.8,'Plastic','Pantalones Vaqueros',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Pantalones',5,409,'Glass','Pantalones Vaqueros',2);

INSERT INTO [dbo].[Producto]([nombre],[id_vendedor],[precio],[material],[descripcion],[id_categoria])
VALUES('Pantalones ',2,25.7,'Vinyl','Pantalones Vaqueros',2);

CREATE TABLE [dbo].[Caracteristica]([id] [int] IDENTITY(1,1) NOT NULL,[nombre] [nvarchar](50) NOT NULL,[id_categoria] [int] NOT NULL,
[valor] [nvarchar](50) NOT NULL,[id_producto] [int] NOT NULL,CONSTRAINT [PK_Caracteristica] PRIMARY KEY CLUSTERED ([id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
ALTER TABLE [dbo].[Caracteristica]  WITH CHECK ADD  CONSTRAINT [FK_Caracteristica_Caracteristica] FOREIGN KEY([id_producto])REFERENCES [dbo].[Producto] ([id])
ALTER TABLE [dbo].[Caracteristica] CHECK CONSTRAINT [FK_Caracteristica_Caracteristica]
ALTER TABLE [dbo].[Caracteristica]  WITH CHECK ADD  CONSTRAINT [FK_Caracteristica_Categoria] FOREIGN KEY([id_categoria])REFERENCES [dbo].[Categoria] ([id])
ALTER TABLE [dbo].[Caracteristica] CHECK CONSTRAINT [FK_Caracteristica_Categoria]

INSERT INTO [dbo].[Caracteristica]([nombre],[id_categoria],[valor],[id_producto])VALUES ('Peso: ',1,'3kg',3);
INSERT INTO [dbo].[Caracteristica]([nombre],[id_categoria],[valor],[id_producto])VALUES ('Talla: ',2,'M',4);
INSERT INTO [dbo].[Caracteristica]([nombre],[id_categoria],[valor],[id_producto])VALUES ('Memoria: ',3,'16GB',2);
INSERT INTO [dbo].[Caracteristica]([nombre],[id_categoria],[valor],[id_producto])VALUES ('Color: ',4,'Azul',7);

CREATE TABLE [dbo].[Carrito_Compra](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_comprador] [int] NOT NULL,
      CONSTRAINT [PK_Carrito_Compra] PRIMARY KEY CLUSTERED ([id] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Carrito_Compra]  WITH CHECK ADD  CONSTRAINT [FK_Carrito_Compra_Comprador] FOREIGN KEY([id_comprador])REFERENCES [dbo].[Comprador] ([id])
      ALTER TABLE [dbo].[Carrito_Compra] CHECK CONSTRAINT [FK_Carrito_Compra_Comprador]

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(1);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(2);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(3);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(4);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(5);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(6);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(7);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(8);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(9);

INSERT INTO [dbo].[Carrito_Compra]([id_comprador])
VALUES(10);


CREATE TABLE [dbo].[Productos_Carrito](
	[id_carrito] [int] NOT NULL,[id_producto] [int] NOT NULL,CONSTRAINT [PK_Productos_Carrito] PRIMARY KEY CLUSTERED ([id_carrito] ASC,[id_producto] ASC)WITH 
      (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Productos_Carrito]  WITH CHECK ADD  CONSTRAINT [FK_Productos_Carrito_Carrito_Compra] FOREIGN KEY([id_carrito])REFERENCES [dbo].[Carrito_Compra] ([id])
      ALTER TABLE [dbo].[Productos_Carrito] CHECK CONSTRAINT [FK_Productos_Carrito_Carrito_Compra]
      ALTER TABLE [dbo].[Productos_Carrito]  WITH CHECK ADD  CONSTRAINT [FK_Productos_Carrito_Producto] FOREIGN KEY([id_producto])REFERENCES [dbo].[Producto] ([id])
      ALTER TABLE [dbo].[Productos_Carrito] CHECK CONSTRAINT [FK_Productos_Carrito_Producto]

CREATE TABLE [dbo].[Lista_De_Deseos](
	[id] [int] IDENTITY(1,1) NOT NULL,[id_comprador] [int] NOT NULL,CONSTRAINT [PK_Lista_De_Deseos] PRIMARY KEY CLUSTERED ([id] ASC)WITH 
      (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Lista_De_Deseos]  WITH CHECK ADD  CONSTRAINT [FK_Lista_De_Deseos_Comprador] FOREIGN KEY([id_comprador])REFERENCES [dbo].[Comprador] ([id])
      ALTER TABLE [dbo].[Lista_De_Deseos] CHECK CONSTRAINT [FK_Lista_De_Deseos_Comprador]

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(1);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(2);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(3);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(4);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(5);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(6);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(7);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(8);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(9);

INSERT INTO [dbo].[Lista_De_Deseos]([id_comprador])
VALUES(10);


CREATE TABLE [dbo].[Productos_Lista_Deseos](
	[id_carrito] [int] NOT NULL,[id_producto] [int] NOT NULL,CONSTRAINT [PK_Productos_Lista_Deseos] PRIMARY KEY CLUSTERED ([id_carrito] ASC,[id_producto] ASC)WITH
      (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Productos_Lista_Deseos]  WITH CHECK ADD  CONSTRAINT [FK_Productos_Lista_Deseos_Lista_De_Deseos] FOREIGN KEY([id_carrito])REFERENCES [dbo].[Lista_De_Deseos] ([id])
      ALTER TABLE [dbo].[Productos_Lista_Deseos] CHECK CONSTRAINT [FK_Productos_Lista_Deseos_Lista_De_Deseos]
      ALTER TABLE [dbo].[Productos_Lista_Deseos]  WITH CHECK ADD  CONSTRAINT [FK_Productos_Lista_Deseos_Producto] FOREIGN KEY([id_producto])REFERENCES [dbo].[Producto] ([id])
      ALTER TABLE [dbo].[Productos_Lista_Deseos] CHECK CONSTRAINT [FK_Productos_Lista_Deseos_Producto]


CREATE TABLE [dbo].[Pedido](
	[id] [int] IDENTITY(1,1) NOT NULL,[id_comprador] [int] NOT NULL,CONSTRAINT [PK_Pedido] PRIMARY KEY CLUSTERED ([id] ASC)WITH 
      (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Pedido]  WITH CHECK ADD  CONSTRAINT [FK_Pedido_Comprador] FOREIGN KEY([id_comprador])REFERENCES [dbo].[Comprador] ([id])
      ALTER TABLE [dbo].[Pedido] CHECK CONSTRAINT [FK_Pedido_Comprador]

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(1);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(2);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(3);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(4);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(5);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(6);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(7);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(8);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(9);

INSERT INTO [dbo].[Pedido]([id_comprador])
VALUES(10);



CREATE TABLE [dbo].[Detalle_Pedido]([id] [int] IDENTITY(1,1) NOT NULL,[id_pedido] [int] NOT NULL,[cantidad] [int] NOT NULL,[id_producto] [int] NOT NULL,
CONSTRAINT [PK_Detalle_Pedido] PRIMARY KEY CLUSTERED ([id] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]

ALTER TABLE [dbo].[Detalle_Pedido]  WITH CHECK ADD  CONSTRAINT [FK_Detalle_Pedido_Pedido] FOREIGN KEY([id_producto])REFERENCES [dbo].[Producto] ([id])
ALTER TABLE [dbo].[Detalle_Pedido] CHECK CONSTRAINT [FK_Detalle_Pedido_Pedido]
ALTER TABLE [dbo].[Detalle_Pedido]  WITH CHECK ADD  CONSTRAINT [CK_Detalle_Pedido] CHECK  (([cantidad]>=(1)))
ALTER TABLE [dbo].[Detalle_Pedido] CHECK CONSTRAINT [CK_Detalle_Pedido]



INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(1,10,2);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(2,9,1);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(3,8,10);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(4,7,4);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(5,6,5);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(6,5,1);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(7,4,2);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(8,3,3);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(9,2,2);

INSERT INTO [dbo].[Detalle_Pedido]([id_pedido],[cantidad],[id_producto])
VALUES(10,1,4);


CREATE TABLE [dbo].[Envio](
	[id] [int] IDENTITY(1,1) NOT NULL,[id_pedido] [int] NOT NULL,[estado_envio] [nvarchar](50) NOT NULL,[coste_total_productos_enviados] [float] NOT NULL,CONSTRAINT [PK_Envio] PRIMARY KEY CLUSTERED ([id] ASC)
      WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Envio]  WITH CHECK ADD  CONSTRAINT [FK_Envio_Pedido] FOREIGN KEY([id_pedido])REFERENCES [dbo].[Pedido] ([id])
      ALTER TABLE [dbo].[Envio] CHECK CONSTRAINT [FK_Envio_Pedido]
      ALTER TABLE [dbo].[Envio]  WITH CHECK ADD  CONSTRAINT [CK_Envio] CHECK  (([coste_total_productos_enviados]>=(0)))
      ALTER TABLE [dbo].[Envio] CHECK CONSTRAINT [CK_Envio]

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(1,'En tránsito', 35.5);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(2,'En tránsito', 55);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(3,'Enviado', 95);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(4,'En reparto', 32);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(5,'En tránsito', 33);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(6,'En reparto', 3.78);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(7,'Enviado', 90);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(8,'Pagado', 120);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(9,'En tránsito', 67);

INSERT INTO [dbo].[Envio]([id_pedido],[estado_envio],[coste_total_productos_enviados])
VALUES(10,'En reparto', 23.6);


CREATE TABLE [dbo].[Retos](
	[id] [int] IDENTITY(1,1) NOT NULL,[recompensa] [nvarchar](50) NOT NULL,CONSTRAINT [PK_Retos] PRIMARY KEY CLUSTERED ([id] ASC)WITH 
      (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('diamante');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('oro');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('plata');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('platino');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('oro');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('oro');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('diamante');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('plata');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('diamante');

INSERT INTO [dbo].[Retos]([recompensa])
VALUES('platino');


CREATE TABLE [dbo].[Productos_Guardar_Mas_Tarde](
	[id] [int] IDENTITY(1,1) NOT NULL,[id_producto] [int] NOT NULL,CONSTRAINT [PK_Productos_Guardar_Mas_Tarde] PRIMARY KEY CLUSTERED ([id] ASC)WITH 
      (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Productos_Guardar_Mas_Tarde]  WITH CHECK ADD  CONSTRAINT [FK_Productos_Guardar_Mas_Tarde_Producto] FOREIGN KEY([id_producto])REFERENCES [dbo].[Producto] ([id])
      ALTER TABLE [dbo].[Productos_Guardar_Mas_Tarde] CHECK CONSTRAINT [FK_Productos_Guardar_Mas_Tarde_Producto]

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(1);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(2);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(3);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(4);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(5);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(6);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(7);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(8);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(9);

INSERT INTO [dbo].[Productos_Guardar_Mas_Tarde]([id_producto])
VALUES(10);


CREATE TABLE [dbo].[Guardar_Mas_Tarde](
	[id_guardar_mas_tarde] [int] NOT NULL,[id_comprador] [int] NOT NULL,CONSTRAINT [PK_Guardar_Mas_Tarde] PRIMARY KEY CLUSTERED ([id_guardar_mas_tarde] ASC,[id_comprador] ASC)WITH
      (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Guardar_Mas_Tarde]  WITH CHECK ADD  CONSTRAINT [FK_Guardar_Mas_Tarde_Comprador] FOREIGN KEY([id_comprador])REFERENCES [dbo].[Comprador] ([id])
      ALTER TABLE [dbo].[Guardar_Mas_Tarde] CHECK CONSTRAINT [FK_Guardar_Mas_Tarde_Comprador]
      ALTER TABLE [dbo].[Guardar_Mas_Tarde]  WITH CHECK ADD  CONSTRAINT [FK_Guardar_Mas_Tarde_Productos_Guardar_Mas_Tarde] FOREIGN KEY([id_guardar_mas_tarde])REFERENCES [dbo].[Productos_Guardar_Mas_Tarde] ([id])
      ALTER TABLE [dbo].[Guardar_Mas_Tarde] CHECK CONSTRAINT [FK_Guardar_Mas_Tarde_Productos_Guardar_Mas_Tarde]

CREATE TABLE [dbo].[Consejos](
	[id] [int] IDENTITY(1,1) NOT NULL,[id_producto] [int] NOT NULL,CONSTRAINT [PK_Consejos] PRIMARY KEY CLUSTERED ([id] ASC)WITH
      (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]
      ALTER TABLE [dbo].[Consejos]  WITH CHECK ADD  CONSTRAINT [FK_Consejos_Producto] FOREIGN KEY([id_producto])REFERENCES [dbo].[Producto] ([id])
      ALTER TABLE [dbo].[Consejos] CHECK CONSTRAINT [FK_Consejos_Producto]
      
INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(1);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(3);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(3);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(4);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(5);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(5);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(6);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(7);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(9);

INSERT INTO [dbo].[Consejos]([id_producto])
VALUES(10);

	  ---------FIN SCRIPT--------------------------
    COMMIT;
END TRY
BEGIN CATCH
    IF @@TRANCOUNT > 0
        ROLLBACK;
    
    -- Mostrar información sobre el error
    SELECT 
        ERROR_NUMBER() AS ErrorNumber,
        ERROR_MESSAGE() AS ErrorMessage,
        ERROR_SEVERITY() AS ErrorSeverity,
        ERROR_STATE() AS ErrorState;
END CATCH;
