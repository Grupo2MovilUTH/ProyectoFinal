<?php

class DataBase
{
    private $host = "bdrmuc7nwru4djyosxc3-mysql.services.clever-cloud.com";
    private $database = "bdrmuc7nwru4djyosxc3";
    private $username = "uggi18cilhguph0f";
    private $password = "MUjXO1ckWTPG4wJyH2XZ";

    public $conexion;

    //funcion de conexion a la base de datos
    public function getConnection()
    {
        $this->conexion = null;

        try {
            $this->conexion = new PDO("mysql:host=" . $this->host . ";dbname=" . $this->database, $this->username, $this->password);
            $this->conexion->exec("set names utf8");
            echo "Conectado a la base de datos";
        } catch (PDOException $exception) {
            echo "No se pudo conectar a la base de datos: " . $exception->getMessage();
        }

        return $this->conexion;
    }
}


?>
