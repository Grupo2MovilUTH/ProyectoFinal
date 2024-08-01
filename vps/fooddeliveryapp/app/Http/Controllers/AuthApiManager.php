<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

class AuthApiManager extends Controller
{
    function login(Request $request)
    {
        if (empty($request->email) && empty($request->password)) {
            return array("status" => "failed", "message" => "All fields are required");
        }

        $user = User::where("email", $request->email)->first();
        if (!$user) {
            return array("status" => "failed", "message" => "Retry with correct credentials");
        }

        $credentials = $request->only("email", "password");
        if (Auth::attempt($credentials)) {
            return array("status" => "success",
                "message" => "Login successful",
                "name" => $user->name, "email" => $user->email);
        }

        return array("status" => "failed", "message" => "Retry with correct credentials");


    }

    function registration(Request $request){
        if (empty($request->name) && empty($request->email) && empty($request->password)) {
            return "failed";
        }

        $user = User::create([
            'type' => "customer",
            "name" => $request->name,
            "email" => $request->email,
            "password" => Hash::make($request->password)
        ]);

        if(!$user){
            return "failed";
        }

        return "success";
    }
}
