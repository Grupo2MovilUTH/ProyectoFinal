<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// Configuración de la base de datos
$host = 'bdrmuc7nwru4djyosxc3-mysql.services.clever-cloud.com';
$db = 'bdrmuc7nwru4djyosxc3'; // Cambia 'fooddeliveryapp' por el nombre de tu base de datos
$user = 'uggi18cilhguph0f';
$pass = 'MUjXO1ckWTPG4wJyH2XZ';

try {
    // Conectar a la base de datos
    $pdo = new PDO("mysql:host=$host;dbname=$db", $user, $pass);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Depuración: Imprimir método de solicitud
    error_log("Request Method: " . $_SERVER['REQUEST_METHOD']);

    // Verificar que la solicitud sea un POST
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        // Obtener los datos en formato JSON
        $data = json_decode(file_get_contents("php://input"), true);

        // Depuración: Imprimir datos recibidos
        error_log("Data Received: " . print_r($data, true));

        // Validar los datos requeridos
        if (!isset($data['type']) || !isset($data['name']) || !isset($data['email']) || !isset($data['password'])) {
            echo json_encode(['status' => 'error', 'message' => 'Missing parameters']);
            exit;
        }

        // Obtener y limpiar datos
        $type = trim($data['type']);
        $name = trim($data['name']);
        $email = trim($data['email']);
        $password = trim($data['password']);
        $email_verified_at = isset($data['email_verified_at']) ? trim($data['email_verified_at']) : null;
        $destination_address = isset($data['destination_address']) ? trim($data['destination_address']) : null;
        $destination_lat = isset($data['destination_lat']) ? trim($data['destination_lat']) : null;
        $destination_lon = isset($data['destination_lon']) ? trim($data['destination_lon']) : null;
        $remember_token = isset($data['remember_token']) ? trim($data['remember_token']) : null;
        $created_at = isset($data['created_at']) ? trim($data['created_at']) : null;
        $updated_at = isset($data['updated_at']) ? trim($data['updated_at']) : null;

        // Preparar la consulta SQL sin el campo 'id'
        $stmt = $pdo->prepare("INSERT INTO users 
            (type, name, email, email_verified_at, password, destination_address, destination_lat, destination_lon, remember_token, created_at, updated_at) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Encriptar la contraseña
        $hashedPassword = password_hash($password, PASSWORD_BCRYPT);

        // Ejecutar la consulta
        $stmt->execute([
            $type,
            $name,
            $email,
            $email_verified_at,
            $hashedPassword,
            $destination_address,
            $destination_lat,
            $destination_lon,
            $remember_token,
            $created_at,
            $updated_at
        ]);

        // Obtener el ID del registro recién insertado
        $lastId = $pdo->lastInsertId();

        // Enviar respuesta JSON con el ID del nuevo registro
        echo json_encode([
            'status' => 'success',
            'message' => 'Registration successful',
            'id' => $lastId
        ]);
    } else {
        echo json_encode(['status' => 'error', 'message' => 'Invalid request method']);
    }
} catch (PDOException $e) {
    // Depuración: Imprimir error del PDO
    error_log("Database Error: " . $e->getMessage());
    echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
}
?>
