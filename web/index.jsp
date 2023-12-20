
<%@page import="modelo.Comentarios"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Date"%>
<%@page import="modelo.Publicacionfotos"%>
<%@page import="modelo.Fotos"%>
<%@page import="java.util.Base64"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="controlador.FotosJpaController"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Publicacion"%>
<%@page import="controlador.PublicacionJpaController"%>
<%@page import="modelo.Admisnistrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="estilos.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>INICIO</title>
    </head>
    <body>
        <div class="container">    
            <div>
                <img src="img/logo-biblioteca.jpg" alt="Imagen de la Biblioteca Jose M. Estrada" class="imgLogoBiblioteca"/>
            </div>
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
                                <a class="nav-link" href="contactos.jsp">CONTACTO</a>
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
                                    String nombreUsuario = ((Admisnistrador) sesion.getAttribute("usuario")).getNombre();
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
            <div class="row informacion filaIzquierda">
                <div class="col-md-9">    
                    <h1 style="text-align-last: center">Contenido</h1>

                    <%
                        PublicacionJpaController publicacionController = new PublicacionJpaController();

                        // Obtén la lista de todas las publicaciones con sus fotos
                        List<Publicacion> publicaciones = publicacionController.findPublicacionEntities();

                        // Invierte el orden de la lista para que la última publicación cargada aparezca primero
                        Collections.reverse(publicaciones);

                        // Formato de fecha deseado
                        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    %>

                    <div class="contenedor-publicaciones" style="width: 100%;">
                        <%
                            // Itera sobre la lista de publicaciones
                            for (Publicacion publicacion : publicaciones) {
                                List<Publicacionfotos> publicacionfotosList = publicacion.getPublicacionfotosList();
                                int numFotos = publicacionfotosList.size();

                                if (numFotos > 0) {
                        %>
                        <div class="publicacion row">
                            <h2><%= publicacion.getTitulo()%></h2>
                            <div class="col-md-6">
                                <!-- Muestra el texto de la publicación -->

                                <p class="noticia-texto"><%= publicacion.getTexto()%></p>
                                <!-- Formatea y muestra la fecha de publicación -->

                            </div>

                            <!-- Contenedor principal para la imagen grande y miniaturas -->
                            <div class="col-md-6">
                                <%
                                    // Verifica si hay solo una imagen
                                    if (numFotos == 1) {
                                %>
                                <!-- Muestra la única imagen sin dividir en columnas -->
                                <div class="contenedor-imagenes mx-auto">
                                    <div class="imagen-unico">
                                        <img src='data:image/jpeg;base64, <%= publicacionfotosList.get(0).getIdFotos().getBase64Image()%>' alt='Imagen principal de la publicación' style="width: 100%; height: auto;">
                                    </div>
                                </div>
                                <%
                                } else {
                                %>
                                <!-- Muestra la imagen grande y miniaturas en columnas -->
                                <div class="contenedor-imagenes row mx-auto">
                                    <div class="imagen-grande col-8">
                                        <img src='data:image/jpeg;base64, <%= publicacionfotosList.get(0).getIdFotos().getBase64Image()%>' alt='Imagen principal de la publicación' style="width: 100%; height: auto;">
                                    </div>

                                    <div class="miniaturas-container col-4">
                                        <%
                                            for (int i = 1; i < numFotos; i++) {
                                                Fotos fotoMiniatura = publicacionfotosList.get(i).getIdFotos();
                                        %>
                                        <div class="miniatura">
                                            <img src='data:image/jpeg;base64, <%= fotoMiniatura.getBase64Image()%>' alt='Miniatura de la publicación' style="width: 100%; height: auto;">
                                        </div>
                                        <%
                                            }
                                        %>
                                    </div>
                                </div>
                                <%
                                    }
                                %>
                            </div>
                            <p class="fecha-publicacion"><%= formatoFecha.format(publicacion.getFechaPublicacion())%></p>
                            <div class="d-grid gap-2 d-md-block">

                                <button type="button" class="btn btn-outline-primary" id="likeButton">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-up-fill" viewBox="0 0 16 16">
                                    <path d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"/>
                                    </svg>
                                    <span id="likeCount">0</span> Like
                                </button>


                                <button type="button" class="btn btn-outline-primary" onclick="mostrarFormulario('<%= publicacion.getIdPublicacion()%>')">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-messenger" viewBox="0 0 16 16">
                                    <path d="M0 7.76C0 3.301 3.493 0 8 0s8 3.301 8 7.76-3.493 7.76-8 7.76c-.81 0-1.586-.107-2.316-.307a.639.639 0 0 0-.427.03l-1.588.702a.64.64 0 0 1-.898-.566l-.044-1.423a.639.639 0 0 0-.215-.456C.956 12.108 0 10.092 0 7.76m5.546-1.459-2.35 3.728c-.225.358.214.761.551.506l2.525-1.916a.48.48 0 0 1 .578-.002l1.869 1.402a1.2 1.2 0 0 0 1.735-.32l2.35-3.728c.226-.358-.214-.761-.551-.506L9.728 7.381a.48.48 0 0 1-.578.002L7.281 5.98a1.2 1.2 0 0 0-1.735.32z"/>
                                    </svg>
                                </button>

                                <button type="button" class="btn btn-outline-primary">compartir</button>



                                <div id="formulario_<%= publicacion.getIdPublicacion()%>" style="display: none; margin-top: 10px;">
                                    <form id="formComentario_<%= publicacion.getIdPublicacion()%>" action="SerGuardarComentario" method="post">
                                        <!-- Agrega un campo oculto para el ID de la publicación -->
                                        <input type="hidden" name="idPublicacion" value="<%= publicacion.getIdPublicacion()%>">

                                        <!-- Agrega campos para el nombre y la fecha -->
                                        <input type="text" name="nombreComentario" placeholder="NOMBRE Y APELLIDO"> 
                                        <input type="hidden" name="fechaComentario" value="<%= new java.util.Date()%>">

                                        <textarea name="comentario" class="form-control" placeholder="Escribe tu comentario"></textarea>
                                        <button type="submit" class="btn btn-primary enviarBtn">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send-fill" viewBox="0 0 16 16">
                                            <path d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855H.766l-.452.18a.5.5 0 0 0-.082.887l.41.26.001.002 4.995 3.178 3.178 4.995.002.002.26.41a.5.5 0 0 0 .886-.083l6-15Zm-1.833 1.89L6.637 10.07l-.215-.338a.5.5 0 0 0-.154-.154l-.338-.215 7.494-7.494 1.178-.471-.47 1.178Z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </div>

                                <!-- Muestra los comentarios -->
                                <div  class="cuadroComentario">
                                    <h4>Comentarios:</h4>
                                    <%
                                        // Obtén la lista de comentarios asociados a la publicación actual
                                        List<Comentarios> comentariosList = publicacion.getComentariosList();

                                        // Itera sobre la lista de comentarios
                                        for (Comentarios comentario : comentariosList) {
                                    %>
                                    <div class="comentario-container">
                                        <p class="comentario-nombre">
                                            <strong><%= comentario.getNombre()%>:</strong>
                                        </p>
                                        <p class="comentario-mensaje"><%= comentario.getMjsComentarios()%></p>
                                    </div>

                                    <%
                                        }
                                    %>
                                </div>

                            </div>
                            <!-- Agrega un separador visual entre publicaciones -->
                            <hr>

                        </div>
                        <%
                                }
                            }
                        %>
                    </div>
                </div>

                <div class="col-md-3 filaDerecha">
                    <h5>Estamos aquí</h5>
                    <div>
                        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d877.788941711587!2d-59.26091176931103!3d-28.35413993432944!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x944f0e4bfaa6fbb5%3A0x9a05076d3c2471ca!2sBiblioteca%20Popular%20%22Jos%C3%A9%20Manuel%20Estrada%22!5e0!3m2!1ses-419!2sar!4v1695090432080!5m2!1ses-419!2sar" width="100%" height="200" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                    </div>
                    <h5 id="tituloHoroscopos"></h5>

                    <script>
                        window.onload = function () {
                            var fechaActual = new Date();
                            var opcionesFecha = {year: 'numeric', month: 'long', day: 'numeric'};
                            var fechaFormateada = fechaActual.toLocaleDateString('es-ES', opcionesFecha);

                            var tituloHoroscopos = document.getElementById('tituloHoroscopos');
                            tituloHoroscopos.textContent = 'Horóscopos de hoy - ' + fechaFormateada;
                        }
                    </script>   
                    <div style="width:100%;text-align:center;line-height:11px;">
                        <iframe src="https://horoscopo.horoscope999.com/widget3_show.php?speed=2000&width=200&height=255&sr="
                                width="100%" height="255" scrolling=no marginwidth=0 marginheight=0 frameborder=0 border=0 style="border:0;margin:0;padding:0;"></iframe>
                        <br>
                        <div style="font-size:10px;background-color:#363942;">
                            <a style="font-size:10px;color:#dad7ce;text-decoration:none;" href="https://horoscopo.horoscope999.com" target="_blank">Horoscopo</a> &nbsp;
                        </div>
                    </div>
                </div>
            </div>
        </div>  
        <div class="container">
            <h3>pie de pagina</h3>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="funciones.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
    </body>
</html>
