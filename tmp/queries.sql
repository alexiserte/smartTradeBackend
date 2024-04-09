SELECT nombre FROM Producto WHERE id_vendedor = ?

SELECT nombre,id_vendedor,id_categoria,descripcion,precio FROM Producto WHERE id_categoria = ANY(SELECT id FROM Categoria WHERE id = ?)

SELECT COUNT(*) FROM Producto WHERE nombre = ? AND id_vendedor = ANY(SELECT id FROM Vendedor WHERE nombre = ?)

SELECT nombre, id_categoria, id_vendedor, precio, descripcion FROM Producto WHERE nombre = ? AND id_vendedor = ?;