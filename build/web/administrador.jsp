<%@page import="modelo.Admisnistrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="funciones.js" type="text/javascript"></script>
        <link href="estilos.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ADMINISTRADRO</title>
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
            <%
                String exito = request.getParameter("exito");
                if ("true".equals(exito)) {
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ¡Inicio de sesión exitoso!
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
                }
            %>
            <div class="panelAdm">
            <h2>Panel Administrador</h2>
            <button class="btnAdm">
                <a class="btnLinkAdm" href="formularioAdministrador.jsp">nuevo administrador</a>
            </button>
            <button class="btnAdm">
                <a  class="btnLinkAdm" href="publicacion.jsp">publicaciones</a>
            </button>
            </div>

        </div>  
        <div class="container">
            <h3>pie de pagina</h3>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>


    </body>
</html>
