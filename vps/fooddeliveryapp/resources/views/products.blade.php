@extends('layout')
@section('content')
    <main class="container my-5" style="max-width: 900px">
        <div class="row">
            @if ($errors->any())
                <div class="col-12">
                    @foreach ($errors->all() as $error)
                        <div class="alert alert-danger">{{$error}}</div>
                    @endforeach
                </div>
            @endif
            @if(session()->has('success'))
                <div class="alert alert-success alert-dismissible">
                    {{session('success')}}
                </div>
            @endif
            @if(session()->has('error'))
                <div class="alert alert-danger alert-dismissible">
                    {{session('error')}}
                </div>
            @endif
            <div class="col-6 col-md-6">
                <div class="fs-5 fw-bold mb-2">Add new item:</div>
                <form method="POST" action="{{route("product.add")}}">
                    @csrf
                    <div class="mb-3">
                        <label class="form-label">Enter name of item</label>
                        <input type="text" name="name" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Enter item description</label>
                        <input type="text" name="description" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Enter item price</label>
                        <input type="text" name="price" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Enter item image url</label>
                        <input type="text" name="image" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
            <div class="col-12 col-md-6">
                <div class="fs-5 fw-bold mb-2 text-decoration-underline">List of all item:</div>
                <ul class="list-group">
                    @foreach($products as $product)
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-8">
                                    <div class="fw-bold">{{$product->name}} | Price: ₹{{$product->price}}</div>
                                    <small>{{$product->description}}</small>
                                </div>
                                <div class="col-4">
                                    <img src="{{$product->image}}" width="100%" height="auto">
                                    <a href="{{route("product.delete")}}?id={{$product->id}}">Delete</a>
                                </div>
                            </div>
                        </li>
                    @endforeach
                </ul>
            </div>
        </div>
    </main>
@endsection
