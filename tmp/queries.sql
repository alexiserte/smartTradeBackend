SELECT nombre FROM Producto WHERE id_vendedor = ?

SELECT nombre,id_vendedor,id_categoria,descripcion,precio FROM Producto WHERE id_categoria = ANY(SELECT id FROM Categoria WHERE id = ?)