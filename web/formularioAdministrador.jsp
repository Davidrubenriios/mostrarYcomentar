
<%@page import="modelo.Admisnistrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="estilos.css" rel="stylesheet" type="text/css"/>
        <script src="funciones.js" type="text/javascript"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

        <title>FORMULARIO</title>
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg bg-body-tertiary">
                <div class="container-fluid">
                    <a class="navbar-brand" href="index.jsp"><b> BIBLIOTECA </b></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-end" id="navbarNavDropdown">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="#">QUIENES SOMOS</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">CONTACTO</a>
                            </li>
                            <%
                                HttpSession sesion = request.getSession(false);

                                if (sesion != null && sesion.getAttribute("usuario") != null) {
                                    // El administrador está autenticado
                            %>
                            <li class="nav-item">
                                <a class="nav-link" href="administrador.jsp">ADMINISTRADOR</a>
                            </li>
                            <%
                            } else {
                                // El administrador no está autenticado
                            %>
                            <li class="nav-item">
                                <a class="nav-link" href="login.jsp">ADMINISTRADOR</a>
                            </li>
                            <%
                                }
                            %>

                            <%
                                if (sesion != null && sesion.getAttribute("usuario") != null) {
                                    // El administrador está autenticado
                                    String nombreUsuario = ((Admisnistrador) sesion.getAttribute("usuario")).getNombre(); // Obtener el nombre del usuario desde el objeto Admisnistrador
                            %>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <%= nombreUsuario%>
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="javascript:mostrarConfirmacionCerrarSesion()">Cerrar Sesión</a></li>
                                </ul>
                            </li>
                            <% }%>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div class="container">
            <h1>Formulario</h1>
            <form class="row g-3" action="SevFormularioAdministrador" method="POST">
                <div class="col-md-6">
                    <label for="txtNombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="txtNombre" name="txtNombre" required>
                </div>
                <div class="col-md-6">
                    <label for="txtApellido" class="form-label">Apellido</label>
                    <input type="text" class="form-control" id="txtApellido" name="txtApellido" required>
                </div>
                <div class="col-md-8">
                    <label for="txtEmail" class="form-label">Email</label>
                    <input type="email" class="form-control" id="txtEmail" name="txtEmail" required>
                </div>
                <div class="col-md-4">
                    <label for="txtClave" class="form-label">Password</label>
                    <input type="password" class="form-control" id="txtClave" name="txtClave" required>
                </div>
                <div class="col-12">
                    <label for="txtDni" class="form-label">DNI</label>
                    <input type="number" class="form-control" id="txtDni" name="txtDni" placeholder="1234" required>
                </div>
                <div class="col-md-4">
                    <label for="txtCodigoArea" class="form-label">Codigo de area</label>
                    <input type="number" class="form-control" id="txtCodigoArea" name="txtCodigoArea" required>
                </div>
                <div class="col-md-8">
                    <label for="txtTelefono" class="form-label">Telefono</label>
                    <input type="number" class="form-control" id="txtTelefono" name="txtTelefono" required>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>  
        <div class="container">
            <h3>pie de pagina</h3>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>

    </body>
</html>
