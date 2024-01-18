<%@page import="modelo.Admisnistrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script src="funciones.js" type="text/javascript"></script>
        <link href="estilos.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>     
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CONTACTOS</title>
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
                                <a class="nav-link" href="index.jsp">INICIO</a>
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

            <div class="row informacion filaIzquierda">
                <div class="col-md-9"> 
                    <h1 style="text-align-last: center">Te podes comunicar con nosotroe aqui..</h1>
                    <ol class="rSocial">
                        <li> <a class="redSocial" href=""><img class="logoRedSocial" src="img/whatsapp.png" alt="LOGO DE WHATSAPP"/> <h5> - WHATSAPP </h5> </a> </li>
                        <li> <a class="redSocial" href=""><img class="logoRedSocial" src="img/facebook.png" alt="LOGO FACEBOOK"/> <h5> - FACEBOOK </h5> </a> </li>
                        <li> <a class="redSocial" href=""><img class="logoRedSocial" src="img/instagram.png" alt="LOGO INSTAGRAM"/> <h5> - INSTAGRAM </h5> </a>  </li>
                        <li> <a class="redSocial" href=""><img class="logoRedSocial" src="img/tik-tok.png" alt="LOGO TIK-TOK"/> <h5> - TIK-TOK </h5> </a>  </li>
                    </ol>

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

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
    </body>
</html>

