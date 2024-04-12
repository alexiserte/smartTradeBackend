SELECT nombre FROM Producto WHERE id_vendedor = ?

SELECT nombre,id_vendedor,id_categoria,descripcion,precio FROM Producto WHERE id_categoria = ANY(SELECT id FROM Categoria WHERE id = ?)

SELECT COUNT(*) FROM Producto WHERE nombre = ? AND id_vendedor = ANY(SELECT id FROM Vendedor WHERE nombre = ?)
 
SELECT nombre, id_categoria, id_vendedor, precio, descripcion FROM Producto WHERE nombre = ? AND id_vendedor IN(SELECT id FROM Usuario WHERE nickname = ? AND id IN(SELECT id_usuario FROM Vendedor))

SELECT SUM(cantidad) FROM Detalle_Pedido WHERE id_pedido = ANY(SELECT id FROM Pedido WHERE id_comprador = ANY(SELECT id_usuario FROM Comprador WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ?)))

SELECT SUM(cantidad) FROM Detalle_Pedido WHERE id_producto = ANY(SELECT id FROM Producto WHERE id_vendedor = ANY(SELECT id_usuario FROM Vendedor WHERE id_usuario = ANY(SELECT id FROM Usuario WHERE nickname = ? OR correo = ?)))