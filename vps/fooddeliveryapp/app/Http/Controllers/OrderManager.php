<?php

namespace App\Http\Controllers;

use App\Models\Cart;
use App\Models\Orders;
use App\Models\Products;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Http;

class OrderManager extends Controller
{
    function newOrders()
    {
        $orders = Orders::where("status", "open")->get();
        $orders = json_decode(json_encode($orders));
        $delivery_boys = User::where("type", "delivery")->get();
        $products = Products::get();
        foreach ($orders as $key => $order) {
            $order_item_ids = json_decode($order->items);
            foreach ($order_item_ids as $key2 => $order_item) { //pizza = 2 and fried rice =1
                foreach ($products as $product) {
                    if ($order_item->item_id == $product->id) {
                        $orders[$key]->item_details[$key2] = $product;
                    }
                }
            }
        }
        return view("dashboard", compact("orders", "delivery_boys"));
    }

    function assignOrder(Request $request)
    {
        $order = Orders::where("id", $request->order_id)->first();
        $order->delivery_boy_email = $request->delivery_boy_email;
        $order->status = "assigned";
        if ($order->save()) {
            return redirect(route("dashboard"))
                ->with("success", "Order assigned successfully");
        }
        return redirect(route("dashboard"))
            ->with("error", "Failed to assign Order");
    }

    function listOrders()
    {
        $orders = Orders::orderBy("id", "DESC")->get();
        $orders = json_decode(json_encode($orders));
        $products = Products::get();
        foreach ($orders as $key => $order) {
            $order_item_ids = json_decode($order->items);
            foreach ($order_item_ids as $key2 => $order_item) { //pizza = 2 and fried rice =1
                foreach ($products as $product) {
                    if ($order_item->item_id == $product->id) {
                        $orders[$key]->item_details[$key2] = $product;
                    }
                }
            }
        }
        return view("order", compact("orders"));
    }

    function addToCart(Request $request)
    {
        $cart = new Cart();
        $cart->item_id = $request->item_id;
        $cart->user_email = $request->user_email;
        if ($cart->save()) {
            return "success";
        }
        return "failed";
    }

    function removeFromCart(Request $request)
    {
        $cart = Cart::where("item_id", $request->item_id)
            ->where("user_email", $request->user_email)->first();
        if ($cart == null) {
            return "failed";
        }
        if ($cart->delete()) {
            return "success";
        }

        return "failed";
    }

    function getCart(Request $request)
    {
        $item_id = array();
        $count_items = DB::select(
            "SELECT item_id, COUNT(item_id) as num_item from cart
                                           where user_email = '" . $request->user_email . "'
                                           GROUP BY item_id");
        foreach ($count_items as $key => $item) {
            $item_id[$key] = $item->item_id;
        }
        $user = User::where("email", $request->user_email)->first();
        $dis_dur = $this->calculateEstimatedTime($user);
        $products = Products::whereIn('id', $item_id)->get();
        foreach ($count_items as $item) {
            foreach ($products as $key => $product) {
                if ($item->item_id == $product->id) {
                    $products[$key]->numItem = $item->num_item;
                }
            }
        }
        $data = array();
        array_push($data, array("cart" => json_decode($products),
            "duration" => $dis_dur['duration'], "distance" => $dis_dur['distance']));
        return $data;
    }

    function confirmCart(Request $request)
    {
        $cart = Cart::select("item_id")->where("user_email", $request->user_email)->get();
        if (empty($cart->first())) {
            return "failed";
        }
        $user = User::where("email", $request->user_email)->first();
        $order = new Orders();
        $order->customer_email = $request->user_email;
        $order->items = $cart;
        $order->status = "open";
        $order->destination_address = $user->destination_address;
        $order->destination_lat = $user->destination_lat;
        $order->destination_lon = $user->destination_lon;
        if ($order->save()) {
            if ($this->clearCart($request) == "success") {
                return "success";
            }
        }

        return "failed";
    }

    function clearCart(Request $request)
    {
        if (Cart::where("user_email", $request->user_email)->delete()) {
            return "success";
        }

        return "failed";
    }

    function calculateEstimatedTime($user)
    {
        /*$origin_lat = 10.0274266;
        $origin_lon = 76.3058943;*/

        $origin_lat = 34.0581903; // Los Angeles - Starbucks location
        $origin_lon = -118.2383913;

        $apiURL = "https://api.nextbillion.io/distancematrix/json?origins=$origin_lat,$origin_lon&destinations=$user->destination_lat,$user->destination_lon&mode=4w&key=b98e9dd2f9414231bae19340b76feff0"; // go to nextbillion.ai
        $response = json_decode(Http::get($apiURL));
        $dist_dur['distance'] = $response->rows[0]->elements[0]->distance->value;
        $dist_dur['duration'] = $response->rows[0]->elements[0]->duration->value;

        return $dist_dur;
    }

    function getOrders(Request $request){
        $orders = Orders::where("customer_email", $request->email)->orderBy("id", "DESC")->get();
        $orders = json_decode(json_encode($orders));
        $products = Products::get();
        foreach ($orders as $key => $order) {
            $order_item_ids = json_decode($order->items);
            foreach ($order_item_ids as $key2 => $order_item) { //pizza = 2 and fried rice =1
                foreach ($products as $product) {
                    if ($order_item->item_id == $product->id) {
                        $orders[$key]->item_details[$key2] = $product;
                    }
                }
            }
        }

        return $orders;
    }

}
