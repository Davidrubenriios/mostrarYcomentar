
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Fotos"%>
<%@page import="controlador.FotosJpaController"%>
<%@page import="modelo.Admisnistrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="estilos.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>     
        <script>


            function toggleSelectAll() {
                var checkboxes = document.querySelectorAll('.seleccionarImagen');
                var selectedPhotos = [];

                checkboxes.forEach(function (checkbox) {
                    checkbox.checked = !checkbox.checked;
                    if (checkbox.checked) {
                        selectedPhotos.push(checkbox.value);
                    }
                });

                sessionStorage.setItem('selectedPhotos', selectedPhotos);

                // Actualiza el campo oculto en el formulario de publicación
                document.getElementById('fotosSeleccionadasInput').value = selectedPhotos.join(',');
            }
        </script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IMAGENES</title>
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg bg-body-tertiary">
                <div class="container-fluid">
                    <a class="navbar-brand" href="index.jsp"> <b> BIBLIOTECA </b> </a>
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
            <h1 style="text-align-last: center">Contenido</h1>
            <div class="row informacion filaIzquierda">
                <div class="col-8">
                    <!-- Agrega este botón donde desees en tu HTML -->
                    <button type="button" class="btn btn-success" onclick="enviarImagenesSeleccionadas()">Enviar Imágenes Seleccionadas</button>

                    <div class="imagenPreviews">
                        <%
                            FotosJpaController objFotosCtl = new FotosJpaController();
                            List<Fotos> listaFoto = objFotosCtl.listarFotos();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            for (int i = listaFoto.size() - 1; i >= 0; i--) {
                                Fotos fotos = listaFoto.get(i);
                                String fechaFormateada = sdf.format(fotos.getFechaFoto());
                        %>
                        <div class="tarjetImg" id="imagen_<%= fotos.getIdFotos()%>" onclick="seleccionarTarjeta('<%= fotos.getIdFotos()%>')">
                            <input type="checkbox" class="seleccionarImagen" value="<%= fotos.getIdFotos()%>" onclick="handleCheckboxSelection('<%= fotos.getIdFotos()%>')">
                            <p class="textoImg"><%= fotos.getNombreFoto()%> <%= fechaFormateada%></p>
                            <img class="secNotImg" src="data:image/jpeg;base64,<%= fotos.getBase64Image()%>">
                        </div>
                        <% }%>
                    </div>            
                </div>
                <div class="col-4 filaDerecha">
                    <h2>Cargar Foto</h2>
                    <div class="formDeImg">
                        <form action="SerGuardarImg" method="post" enctype="multipart/form-data" class="formulario">
                            <div class="campoFormulario">
                                <label for="imgFoto" >Seleccionar Foto:</label>
                                <input type="file" name="imgFoto" id="imgFoto" onchange="generarNombreYFecha()"> 
                            </div>
                            <div class="campoFormulario">
                                <label for="nombreFoto">Nombre de la Foto:</label>
                                <input type="text" name="nombreFoto" id="nombreFoto">
                            </div>
                            <div class="campoFormulario">
                                <label for="fechaFoto">Fecha de la Foto:</label>
                                <input type="date" name="fechaFoto" id="fechaFoto">
                            </div>
                            <div class="campoFormulario">
                                <input type="submit" value="Guardar Foto">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>  
        <div class="container">
            <h3>pie de pagina</h3>
        </div>
        <script src="funciones.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
    </body>
</html>