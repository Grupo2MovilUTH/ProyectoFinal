<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">{{config("app.name")}}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-link active" aria-current="page" href="{{route("dashboard")}}">Home</a>
                <a class="nav-link" href="{{route("products")}}">Products</a>
                <a class="nav-link" href="{{route("order.list")}}">Orders</a>
                <a class="nav-link" href="{{route("logout")}}">Logout</a>
            </div>
        </div>
    </div>
</nav>
