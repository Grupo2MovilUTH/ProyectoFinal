<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Session;

class AuthManager extends Controller
{
    function login(){
        return view("login");
    }

    function loginPost(Request $request){
        $request->validate([
            'email' => 'required|email',
            'password' => 'required'
        ]);
        $credentials = $request->only("email", "password");
        if (Auth::attempt($credentials)) {
            return redirect()->intended(route("dashboard"))->with("success", "Login success");
        }
        return redirect()->intended(route("login"))->with("error", "Login success");

    }

    function logout(){
        Session::flush();
        Auth::logout();
        return redirect(route("login"));
    }
}
