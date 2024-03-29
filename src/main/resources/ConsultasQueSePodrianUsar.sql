-- SELECCIONAR PRODUCTOS YA COMPRADOS POR UN USUARIO
SELECT p.nombre
FROM producto p, detalle_pedido pe, comprador c, pedido ped
WHERE c.nombre = ? AND ped.id_comprador = c.id_comprador AND pe.id_pedido = ped.id_pedido AND pe.id_articulo = p.id_articulo
ORDER BY p.nombre

-- CARRITO DE LA COMPRA ASOCIADO A UN USUARIO
SELECT c.id_carrito
FROM  carrito c, comprador co
WHERE c.id_comprador = co.?

