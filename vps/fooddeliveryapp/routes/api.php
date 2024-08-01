<?php

use App\Http\Controllers\AuthApiManager;
use App\Http\Controllers\DeliveryBoyManager;
use App\Http\Controllers\OrderManager;
use App\Http\Controllers\ProductManager;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\CustomerManager;

Route::any("/users/login", [AuthApiManager::class, "login"]);
Route::any("/users/register", [AuthApiManager::class, "registration"]);

Route::any("/users/delivery", [DeliveryBoyManager::class, "getDelivery"]);
Route::any("/users/delivery/success", [DeliveryBoyManager::class, "markStatusSuccess"]);
Route::any("/users/delivery/failed", [DeliveryBoyManager::class, "markStatusFailed"]);


Route::any("/product/list", [ProductManager::class, "getProducts"]);


Route::any("/users/cart/add", [OrderManager::class, "addToCart"]);
Route::any("/users/cart/remove", [OrderManager::class, "removeFromCart"]);
Route::any("/users/cart/list", [OrderManager::class, "getCart"]);
Route::any("/users/cart/confirm", [OrderManager::class, "confirmCart"]);
Route::any("/users/cart/clear", [OrderManager::class, "clearCart"]);
Route::any("/users/orders/list", [OrderManager::class, "getOrders"]);

Route::any("/users/address/update", [CustomerManager::class, "updateAddress"]);

