<?php
include_once 'DataBase.php';

$database = new DataBase();
$db = $database->getConnection();

$userEmail = isset($_GET['user_email']) ? $_GET['user_email'] : '';
$distanceInput = isset($_GET['distance']) ? (float)$_GET['distance'] : 0; // Obtener la distancia desde la solicitud

$response = array();

try {
    $query = "
        SELECT 
            cart.id, 
            cart.item_id, 
            products.name, 
            products.description, 
            products.price
        FROM 
            cart 
        JOIN 
            products 
        ON 
            cart.item_id = products.id 
        WHERE 
            cart.user_email = :user_email
    ";
    $stmt = $db->prepare($query);
    $stmt->bindParam(':user_email', $userEmail);
    $stmt->execute();

    $response['cart'] = array();

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $item = array(
            'id' => $row['id'],
            'item_id' => $row['item_id'],
            'name' => $row['name'],
            'description' => $row['description'],
            'price' => $row['price']
        );
        array_push($response['cart'], $item);
    }

    // Distancia fija
    $fixedDistance = 2000;
    // Restar la distancia actual (recibida desde el cliente)
    $remainingDistance = $fixedDistance - $distanceInput;

    // Asegúrate de que la distancia restante no sea negativa
    $remainingDistance = max($remainingDistance, 0);

    // Añadir la distancia restante y la duración a la respuesta
    $response['distance'] = $remainingDistance;
    $response['duration'] = 1200;

    header('Content-Type: application/json');
    echo json_encode($response);
} catch (Exception $e) {
    $response['error'] = $e->getMessage();
    header('Content-Type: application/json');
    echo json_encode($response);
}
?>
