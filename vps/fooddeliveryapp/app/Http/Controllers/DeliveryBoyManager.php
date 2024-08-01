<?php

namespace App\Http\Controllers;

use App\Models\Orders;
use Illuminate\Http\Request;

class DeliveryBoyManager extends Controller
{
    function getDelivery(Request $request)
    {
        $delivery = Orders::where("delivery_boy_email", $request->email)
            ->where("status", "assigned")->orderBy("id", "DESC")->get();
        return $delivery;
    }

    function markStatus(Request $request, $status)
    {
        $order = Orders::where("id", $request->order_id)->first();
        $order->status = $status;
        if ($order->save()) {
            return "success";
        }
        return "failed";
    }

    function markStatusSuccess(Request $request)
    {
        return $this->markStatus($request, "success");
    }

    function markStatusFailed(Request $request)
    {
        return $this->markStatus($request, "failed");
    }
}
