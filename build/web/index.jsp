
<%@page import="controlador.RespuestaJpaController"%>
<%@page import="modelo.Respuesta"%>
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
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="estilos.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script async defer crossorigin="anonymous" src="https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v13.0&appId=TU_APP_ID" nonce="YOUR_NONCE"></script>
        <script src="funciones.js" type="text/javascript"></script>


        <title>INICIO</title>
    </head>
    <body>
        <div class="container" id="inicio">    
            <div class="background-container">
                <img src="img/logoBiblioteca1.png" alt="Imagen de la Biblioteca Jose M. Estrada" class="imgLogoBiblioteca"/>
                <h1 class="animated-text">BIBLIOTECA POPULAR</h1>
                <h3 class="animated1-text">JOSÉ MANUEL ESTRADA</h3>
                <a href="#seccionNoticia">
                    <button class="animated-text8" >
                        <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor" class="bi bi-chevron-double-down" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M1.646 6.646a.5.5 0 0 1 .708 0L8 12.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708"/>
                        <path fill-rule="evenodd" d="M1.646 2.646a.5.5 0 0 1 .708 0L8 8.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708"/>
                        </svg>       
                    </button>
                </a>         
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
                                <a class="nav-link active" aria-current="page" href="todaLasPublicaciones.jsp">TODAS LAS PUBLICACIONES</a>
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
            <div id="seccionNoticia"></div>
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

                        // Muestra solo la última publicación
                        if (!publicaciones.isEmpty()) {
                            Publicacion publicacion = publicaciones.get(0);
                            List<Publicacionfotos> publicacionfotosList = publicacion.getPublicacionfotosList();
                            int numFotos = publicacionfotosList.size();

                            if (numFotos > 0) {
                    %>
                    <h2><%= publicacion.getTitulo()%></h2>

                    <!-- Contenedor principal para la imagen grande y miniaturas -->
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
                                    <img src='data:image/jpeg;base64, <%= fotoMiniatura.getBase64Image()%>' alt='Miniatura de la publicación' style="width: 100%; height: 100%; object-fit: cover;">
                                </div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                        <%
                            }
                        %>
                        <!-- Muestra el texto de la publicación -->
                        <p class="noticia-texto"><%= publicacion.getTexto()%></p>

                        <!-- Formatea y muestra la fecha de publicación -->
                        <p class="fecha-publicacion"><%= formatoFecha.format(publicacion.getFechaPublicacion())%></p>
                        <div class="d-grid gap-2 d-md-block">

                            <button type="button" class="btn btn-outline-primary" id="likeButton_<%= publicacion.getIdPublicacion()%>" onclick="incrementarLikes('<%= publicacion.getIdPublicacion()%>')">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-up-fill" viewBox="0 0 16 16">
                                <path d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"/>
                                </svg>
                                <span id="likeCount_<%= publicacion.getIdPublicacion()%>">0</span> Like
                            </button>
  
                            <%
                                List<Comentarios> comentariosList = publicacion.getComentariosList();

                            %>
                            <button class="btn btn-outline-primary" id="botonVerComentarios_<%= publicacion.getIdPublicacion()%>" onclick="mostrarOcultarComentarios('<%= publicacion.getIdPublicacion()%>', '<%= comentariosList.size()%>')">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-wechat" viewBox="0 0 16 16">
                                <path d="M11.176 14.429c-2.665 0-4.826-1.8-4.826-4.018 0-2.22 2.159-4.02 4.824-4.02S16 8.191 16 10.411c0 1.21-.65 2.301-1.666 3.036a.32.32 0 0 0-.12.366l.218.81a.6.6 0 0 1 .029.117.166.166 0 0 1-.162.162.2.2 0 0 1-.092-.03l-1.057-.61a.5.5 0 0 0-.256-.074.5.5 0 0 0-.142.021 5.7 5.7 0 0 1-1.576.22M9.064 9.542a.647.647 0 1 0 .557-1 .645.645 0 0 0-.646.647.6.6 0 0 0 .09.353Zm3.232.001a.646.646 0 1 0 .546-1 .645.645 0 0 0-.644.644.63.63 0 0 0 .098.356"/>
                                <path d="M0 6.826c0 1.455.781 2.765 2.001 3.656a.385.385 0 0 1 .143.439l-.161.6-.1.373a.5.5 0 0 0-.032.14.19.19 0 0 0 .193.193q.06 0 .111-.029l1.268-.733a.6.6 0 0 1 .308-.088q.088 0 .171.025a6.8 6.8 0 0 0 1.625.26 4.5 4.5 0 0 1-.177-1.251c0-2.936 2.785-5.02 5.824-5.02l.15.002C10.587 3.429 8.392 2 5.796 2 2.596 2 0 4.16 0 6.826m4.632-1.555a.77.77 0 1 1-1.54 0 .77.77 0 0 1 1.54 0m3.875 0a.77.77 0 1 1-1.54 0 .77.77 0 0 1 1.54 0"/>
                                </svg> <span class=" numeroConetador contadorComentarios_<%= publicacion.getIdPublicacion()%>"> <%= comentariosList.size()%> </span>
                            </button>

                            <button type="button" class="btn btn-outline-primary" onclick="mostrarBotonesDeCompartir('<%= publicacion.getIdPublicacion()%>', '<%= publicacion.getTitulo()%>', '<%= request.getRequestURL()%>')">
                                Compartir 
                            </button>

                            <div id="BotonesDeCompartir" class="BotonesDeCompartir">
                                <div class="cerrarBtnContainer">
                                    <button type="button" class="btn cerrarBtn" onclick="cerrarBotonesDeCompartir('<%= publicacion.getIdPublicacion()%>')">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-square-fill" viewBox="0 0 16 16">
                                        <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708"/>
                                        </svg>
                                    </button>
                                </div>
                                <h2 id="ventanaEmergenteTitulo"></h2>
                                <button class="btn btn-outline-primary botonFace" type="button" onclick="compartirEnFacebook()">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-facebook" viewBox="0 0 16 16">
                                    <path d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951"/>
                                    </svg>
                                </button>
                                <button class="btn btn-outline-success botonWhat" type="button" onclick="compartirEnWhatsApp('<%= publicacion.getIdPublicacion()%>', '<%= publicacion.getTitulo()%>', '<%= request.getRequestURL()%>')">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-whatsapp" viewBox="0 0 16 16">
                                    <path d="M13.601 2.326A7.854 7.854 0 0 0 7.994 0C3.627 0 .068 3.558.064 7.926c0 1.399.366 2.76 1.057 3.965L0 16l4.204-1.102a7.933 7.933 0 0 0 3.79.965h.004c4.368 0 7.926-3.558 7.93-7.93A7.898 7.898 0 0 0 13.6 2.326zM7.994 14.521a6.573 6.573 0 0 1-3.356-.92l-.24-.144-2.494.654.666-2.433-.156-.251a6.56 6.56 0 0 1-1.007-3.505c0-3.626 2.957-6.584 6.591-6.584a6.56 6.56 0 0 1 4.66 1.931 6.557 6.557 0 0 1 1.928 4.66c-.004 3.639-2.961 6.592-6.592 6.592zm3.615-4.934c-.197-.099-1.17-.578-1.353-.646-.182-.065-.315-.099-.445.099-.133.197-.513.646-.627.775-.114.133-.232.148-.43.05-.197-.1-.836-.308-1.592-.985-.59-.525-.985-1.175-1.103-1.372-.114-.198-.011-.304.088-.403.087-.088.197-.232.296-.346.1-.114.133-.198.198-.33.065-.134.034-.248-.015-.347-.05-.099-.445-1.076-.612-1.47-.16-.389-.323-.335-.445-.34-.114-.007-.247-.007-.38-.007a.729.729 0 0 0-.529.247c-.182.198-.691.677-.691 1.654 0 .977.71 1.916.81 2.049.098.133 1.394 2.132 3.383 2.992.47.205.84.326 1.129.418.475.152.904.129 1.246.08.38-.058 1.171-.48 1.338-.943.164-.464.164-.86.114-.943-.049-.084-.182-.133-.38-.232z"/>
                                    </svg>
                                </button>
                            </div>



                            <!-- FORMULARIO DE COMENTARIO -->

                            <div id="formulario_<%= publicacion.getIdPublicacion()%>" class="fromularioComentario">
                                <div class="cerrarBtnContainer">
                                    <button type="button" class="btn cerrarBtn" onclick="cerrarFormulario('<%= publicacion.getIdPublicacion()%>')">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-square-fill" viewBox="0 0 16 16">
                                        <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708"/>
                                        </svg>
                                    </button>
                                </div>
                                <form id="formComentario_<%= publicacion.getIdPublicacion()%>" action="SerGuardarComentario" method="post">
                                    <!-- Agrega un campo oculto para el ID de la publicación -->
                                    <input type="hidden" name="idPublicacion" value="<%= publicacion.getIdPublicacion()%>">

                                    <!-- Agrega campos para el nombre y la fecha -->
                                    <input type="text" name="nombreComentario" required="" placeholder="NOMBRE Y APELLIDO"> 
                                    <input type="hidden" name="fechaComentario" value="<%= new java.util.Date()%>">

                                    <textarea name="comentario" class="form-control" placeholder="Escribe tu comentario" required="" ></textarea>
                                    <button type="submit" class="btn btn-primary enviarBtn">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send-fill" viewBox="0 0 16 16">
                                        <path d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855H.766l-.452.18a.5.5 0 0 0-.082.887l.41.26.001.002 4.995 3.178 3.178 4.995.002.002.26.41a.5.5 0 0 0 .886-.083l6-15Zm-1.833 1.89L6.637 10.07l-.215-.338a.5.5 0 0 0-.154-.154l-.338-.215 7.494-7.494 1.178-.471-.47 1.178Z"/>
                                        </svg>
                                    </button>
                                </form>
                            </div>

                            <!-- Muestra los comentarios -->
                            <!-- Contenedor de comentarios con un ID único -->
                            <div id="comentariosContainer_<%= publicacion.getIdPublicacion()%>" class="comentarios-container" style="display: none;">
                                <button type="button" class="btn btn-outline-primary" onclick="mostrarFormulario('<%= publicacion.getIdPublicacion()%>')">
                                    Comentar.
                                </button>
                                <div class="cuadroComentario">
                                    <%
                                        // Itera sobre la lista de comentarios en orden inverso
                                        for (int i = comentariosList.size() - 1; i >= 0; i--) {
                                            Comentarios comentario = comentariosList.get(i);
                                    %>

                                    <div class="comentario-container">
                                        <p class="comentario-nombre">
                                            <strong class="nombre-con-dos-puntos"><%= comentario.getNombre()%></strong>
                                        </p>
                                        <p class="comentario-mensaje"><%= comentario.getMjsComentarios()%></p>
                                        <button class="respuestaComentario btn btn-success" onclick="mostrarFormularioRespuesta('<%= comentario.getIdComentarios()%>')">
                                            Responder
                                        </button>
                                    </div>

                                    <!-- Mostrar respuestas -->
                                    <div class="respuestas-container" id="respuestasContainer_<%= comentario.getIdComentarios()%>" style="display: none;">
                                        <% RespuestaJpaController respuestaController = new RespuestaJpaController();
                                            // Obtén la lista de respuestas asociadas a este comentario
                                            List<Respuesta> respuestasList = respuestaController.findRespuestasByIdComentario(comentario.getIdComentarios());
                                            // Invierte el orden de la lista
                                            Collections.reverse(respuestasList);
                                            // Itera sobre la lista de respuestas
                                            for (Respuesta respuesta : respuestasList) {
                                        %>
                                        <div class="respuesta-container">
                                            <p class="respuesta-nombre">
                                                <strong class="nombre-con-dos-puntos"><%= respuesta.getNombre()%></strong>
                                            </p>
                                            <p class="respuesta-mensaje"><%= respuesta.getMsjRespuesta()%></p>
                                        </div>
                                        <% }%>
                                    </div>
                                    <!-- Botón para mostrar/ocultar respuestas -->
                                    <button class="botonMostrarRespuestas" id="respuestasButton_<%= comentario.getIdComentarios()%>" onclick="mostrarOcultarRespuestas('<%= comentario.getIdComentarios()%>', <%= respuestasList.size()%>)">
                                        Ver respuestas = <%= respuestasList.size()%>
                                    </button>
                                    <!-- FORMULARIO DE RESPUESTA -->
                                    <div id="formularioRespuesta_<%= comentario.getIdComentarios()%>" class="fromularioRespuesta">
                                        <div class="cerrarBtnContainer">
                                            <button type="button" class="btn cerrarBtn" onclick="cerrarFormularioRespuesta('<%= comentario.getIdComentarios()%>')">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-square-fill" viewBox="0 0 16 16">
                                                <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 1 1 .708-.708"/>
                                                </svg>
                                            </button>
                                        </div>
                                        <form id="formRespuesta_<%= comentario.getIdPublicacion()%>" action="ServGuardarRespuesta" method="post">
                                            <!-- Agrega un campo oculto para el ID del comentario -->
                                            <input type="hidden" name="idComentario" value="<%= comentario.getIdComentarios()%>">

                                            <!-- Agrega campos para el nombre y la fecha -->
                                            <input type="text" name="nombreRespuesta" placeholder="NOMBRE Y APELLIDO"> 
                                            <input type="hidden" name="fechaRespuesta" value="<%= new java.util.Date()%>">

                                            <textarea name="respuesta" class="form-control" placeholder="Escribe tu respuesta"></textarea>
                                            <button type="submit" class="btn btn-primary enviarBtn1">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send-fill" viewBox="0 0 16 16">
                                                <path d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855H.766l-.452.18a.5.5 0 0 0-.082.887l.41.26.001.002 4.995 3.178 3.178 4.995.002.002.26.41a.5.5 0 0 0 .886-.083l6-15Zm-1.833 1.89L6.637 10.07l-.215-.338a.5.5 0 0 0-.154-.154l-.338-.215 7.494-7.494 1.178-.471-.47 1.178Z"/>
                                                </svg>
                                            </button>
                                        </form>
                                    </div>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                        </div>
                        <%
                                }
                            }
                        %>
                        <div id="carouselExampleAutoplaying" class="carousel slide carousel-container" data-bs-ride="carousel">
                            <div class="carousel-inner" style="height: 150px; overflow: hidden;"> <!-- Ajusta la altura según tus necesidades -->
                                <div class="carousel-item active">
                                    <img src="img/imgPubli.png" alt="" class="d-block w-100 carousel-image">
                                </div>
                                <div class="carousel-item">
                                    <img src="img/imgPubli.png" alt="" class="d-block w-100 carousel-image">
                                </div>
                                <div class="carousel-item">
                                    <img src="img/imgPubli.png" alt="" class="d-block w-100 carousel-image">
                                </div>
                            </div>
                            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div><br>
                        <div class="row d-flex align-items-stretch">

                            <h2 style="text-align-last: center">Contenidos anteriores</h2>
                            <!-- Tarjeta de la penúltima publicación -->
                            <div class="col-md-6">
                                <% if (publicaciones.size() > 1) {
                                        Publicacion penultimaPublicacion = publicaciones.get(1);
                                        List<Publicacionfotos> penultimaFotos = penultimaPublicacion.getPublicacionfotosList();
                                        String penultimaTexto = penultimaPublicacion.getTexto();

                                        // Limitar el texto a una línea (por ejemplo, los primeros 50 caracteres)
                                        if (penultimaTexto.length() > 50) {
                                            penultimaTexto = penultimaTexto.substring(0, 50) + "...";
                                        }
                                %>
                                <div class=" h-100 bordeTarjeta">
                                    <img src='data:image/jpeg;base64, <%= penultimaFotos.get(0).getIdFotos().getBase64Image()%>' class="card-img-top" alt="Imagen de la penúltima publicación">
                                    <div class="card-body">
                                        <br>
                                        <h5 class="card-title"><%= penultimaPublicacion.getTitulo()%></h5>
                                        <br>
                                        <p class="card-text"><%= penultimaTexto%></p>
                                        <a href="todaLasPublicaciones.jsp?id=<%= penultimaPublicacion.getIdPublicacion()%>#publicacion_<%= penultimaPublicacion.getIdPublicacion()%>" class="btn btn-primary">Ver más</a>
                                    </div>
                                </div>
                                <% } %>
                            </div>

                            <!-- Tarjeta de la anterior publicación -->
                            <div class="col-md-6">
                                <% if (publicaciones.size() > 2) {
                                        Publicacion anteriorPublicacion = publicaciones.get(2);
                                        List<Publicacionfotos> anteriorFotos = anteriorPublicacion.getPublicacionfotosList();
                                        String anteriorTexto = anteriorPublicacion.getTexto();

                                        // Limitar el texto a una línea (por ejemplo, los primeros 50 caracteres)
                                        if (anteriorTexto.length() > 50) {
                                            anteriorTexto = anteriorTexto.substring(0, 50) + "...";
                                        }
                                %>
                                <div class=" h-100 bordeTarjeta">
                                    <img src='data:image/jpeg;base64, <%= anteriorFotos.get(0).getIdFotos().getBase64Image()%>' class="card-img-top" alt="Imagen de la anterior publicación">

                                    <div class="card-body">
                                        <br>
                                        <h5 class="card-title"><%= anteriorPublicacion.getTitulo()%></h5>
                                        <br>
                                        <p class="card-text"><%= anteriorTexto%></p>
                                        <a href="todaLasPublicaciones.jsp?id=<%= anteriorPublicacion.getIdPublicacion()%>#publicacion_<%= anteriorPublicacion.getIdPublicacion()%>" class="btn btn-primary">Ver más</a>
                                    </div>
                                </div>
                                <% }%>
                            </div>
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
            <a href="#inicio" id="floating-btn" class="animated-text8">
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-chevron-double-up" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M7.646 2.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1-.708.708L8 3.707 2.354 9.354a.5.5 0 1 1-.708-.708z"/>
                <path fill-rule="evenodd" d="M7.646 6.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1-.708.708L8 7.707l-5.646 5.647a.5.5 0 0 1-.708-.708z"/>
                </svg>
            </a>
            <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
            <script src="funciones.js" type="text/javascript"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
    </body>
</html>
