-- Products in a certain shopping cart, knowing the nickname
SELECT p.* 
FROM Producto p
JOIN Productos_Carrito pc ON p.id = pc.id
JOIN Carrito_Compra cc ON pc.id_carrito = cc.id
JOIN Usuario u ON cc.id_comprador = u.id
WHERE u.nickname = ?
