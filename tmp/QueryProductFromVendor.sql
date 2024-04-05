-- Get products from one vendor
SELECT * 
FROM Producto 
WHERE id_vendedor IN (
    SELECT id 
    FROM Vendedor 
    WHERE id IN (
        SELECT id 
        FROM Usuario 
        WHERE nickname = ?
    )
)
