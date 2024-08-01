<?php
require 'vendor/autoload.php';
include_once 'database.php';
include_once 'register.php';
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

// Instancia de la base de datos
$database = new Database();
$db = $database->getConnection();

// Instancia de la clase Register
$register = new Register($db);

// Obtención de datos JSON del cuerpo de la solicitud
$data = json_decode(file_get_contents("php://input"));

// Agregar un registro para verificar los datos recibidos
file_put_contents('php://stderr', print_r($data, TRUE));

// Verificación de los datos recibidos
if (isset($data->email) && isset($data->name) && isset($data->password)) {
    $register->email = $data->email;
    $register->name = $data->name;

    // Hash de la nueva contraseña
    $register->password = password_hash($data->password, PASSWORD_BCRYPT);

    // Actualización del registro
    if ($register->updateRegister()) {
        // Enviar correo con la nueva contraseña
        $newPassword = $data->password;
        sendPasswordResetEmail($register->email, $newPassword);
        
        http_response_code(200);
        echo json_encode(array("message" => "User was updated and email sent."));
    } else {
        http_response_code(503);
        echo json_encode(array("message" => "Unable to update user."));
    }
} else {
    http_response_code(400);
    echo json_encode(array("message" => "Incomplete data."));
}

// Función para enviar correo con la nueva contraseña
function sendPasswordResetEmail($toEmail, $newPassword) {
    $mail = new PHPMailer(true);

    try {
        // Configuración del servidor SMTP
        $mail->isSMTP();
        $mail->Host = 'smtp.gmail.com'; // Servidor SMTP de Gmail
        $mail->SMTPAuth = true;
        $mail->Username = 'em9845368@gmail.com'; // Tu dirección de correo electrónico
        $mail->Password = 'EAME81890'; // Tu contraseña de correo electrónico
        $mail->SMTPSecure = 'tls';
        $mail->Port = 587;

        // Destinatarios
        $mail->setFrom('em9845368@gmail.com', 'Edgar Mejia');
        $mail->addAddress($toEmail);

        // Contenido del correo
        $mail->isHTML(true);
        $mail->Subject = 'Your New Password';
        $mail->Body    = 'Your new temporary password is: ' . $newPassword;
        $mail->AltBody = 'Your new temporary password is: ' . $newPassword;

        $mail->send();
    } catch (Exception $e) {
        error_log("Message could not be sent. Mailer Error: {$mail->ErrorInfo}");
    }
}
?>
