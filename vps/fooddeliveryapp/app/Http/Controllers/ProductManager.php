<?php

namespace App\Http\Controllers;

use App\Models\Products;
use Illuminate\Http\Request;

class ProductManager extends Controller
{
    function getProducts(){
        return Products::get();
    }
    function listProducts(){
        $products = $this->getProducts();
        return view("products", compact("products"));
    }

    function addProducts(Request $request){
        $product = new Products();
        $product->name = $request->name;
        $product->description = $request->description;
        $product->price = $request->price;
        $product->image = $request->image;
        if($product->save()){
            return redirect(route("products"))
                ->with("success", "Product added successfully");
        }
        return redirect(route("products"))
            ->with("error", "Failed  to add Product");
    }

    function deleteProducts(Request $request){
        if(Products::where("id",$request->id)->delete()){
            return redirect(route("products"))
                ->with("success", "Product deleted successfully");
        }
        return redirect(route("products"))
            ->with("error", "Failed  to delete Product");
    }
}
