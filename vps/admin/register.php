<?php
class Register {
    private $conexion;
    private $table = "users";

    public $id;
    public $type;
    public $name;
    public $email;
    public $email_verified_at;
    public $password;
    public $destination_address;
    public $destination_lat;
    public $destination_lon;
    public $remember_token;
    public $created_at;
    public $updated_at;

    public function __construct($db) {
        $this->conexion = $db;
    }

    public function createRegister() {
        $consulta = "INSERT INTO " . $this->table . " 
                    SET type = :type, name = :name, email = :email, email_verified_at = :email_verified_at, 
                    password = :password, destination_address = :destination_address, 
                    destination_lat = :destination_lat, destination_lon = :destination_lon, 
                    remember_token = :remember_token, created_at = :created_at, updated_at = :updated_at";
        $comando = $this->conexion->prepare($consulta);

        // Sanitización de datos
        $this->type = htmlspecialchars(strip_tags($this->type));
        $this->name = htmlspecialchars(strip_tags($this->name));
        $this->email = htmlspecialchars(strip_tags($this->email));
        $this->email_verified_at = htmlspecialchars(strip_tags($this->email_verified_at));
        $this->password = htmlspecialchars(strip_tags($this->password));
        $this->destination_address = htmlspecialchars(strip_tags($this->destination_address));
        $this->destination_lat = htmlspecialchars(strip_tags($this->destination_lat));
        $this->destination_lon = htmlspecialchars(strip_tags($this->destination_lon));
        $this->remember_token = htmlspecialchars(strip_tags($this->remember_token));
        $this->created_at = htmlspecialchars(strip_tags($this->created_at));
        $this->updated_at = htmlspecialchars(strip_tags($this->updated_at));

        // Encriptar la contraseña
        $hashed_password = password_hash($this->password, PASSWORD_BCRYPT);

        // Bind de datos
        $comando->bindParam(":type", $this->type);
        $comando->bindParam(":name", $this->name);
        $comando->bindParam(":email", $this->email);
        $comando->bindParam(":email_verified_at", $this->email_verified_at);
        $comando->bindParam(":password", $hashed_password);
        $comando->bindParam(":destination_address", $this->destination_address);
        $comando->bindParam(":destination_lat", $this->destination_lat);
        $comando->bindParam(":destination_lon", $this->destination_lon);
        $comando->bindParam(":remember_token", $this->remember_token);
        $comando->bindParam(":created_at", $this->created_at);
        $comando->bindParam(":updated_at", $this->updated_at);

        if ($comando->execute()) {
            return true;
        }

        return false;
    }

    public function updateRegister() {
        // Construir la consulta SQL
        $query = "UPDATE " . $this->table . " 
                  SET name = :name" . 
                  (isset($this->password) ? ", password = :password" : "") . 
                  " WHERE email = :old_email";
    
        // Preparar la consulta
        $stmt = $this->conexion->prepare($query);
    
        // Limpiar datos
        $this->name = htmlspecialchars(strip_tags($this->name));
        $this->email = htmlspecialchars(strip_tags($this->email));
        $old_email = htmlspecialchars(strip_tags($this->email)); // Email actual usado para la búsqueda
    
        // Encriptar la contraseña si se proporciona
        if (isset($this->password) && !empty($this->password)) {
            $this->password = password_hash($this->password, PASSWORD_BCRYPT);
        }
    
        // Enlazar parámetros
        $stmt->bindParam(':name', $this->name);
        $stmt->bindParam(':old_email', $old_email);
    
        if (isset($this->password) && !empty($this->password)) {
            $stmt->bindParam(':password', $this->password);
        }
    
        // Ejecutar la consulta
        if ($stmt->execute()) {
            return true;
        }
    
        return false;
    }    
}
?>
