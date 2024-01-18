
//----CONFIRMACION DE CERRAR SESIÓN----//
function mostrarConfirmacionCerrarSesion() {
    if (confirm("¿Está seguro de que desea cerrar la sesión?")) {
        window.location.href = "cerrarSesion.jsp";
    }
}

// Función para mostrar imágenes cargadas en un collage
function mostrarImagenesCargadas(input) {
    var imagenPreviews = document.getElementById('imagenPreviews');
    if (input.files && input.files.length > 0) {
        for (var i = 0; i < input.files.length; i++) {
            var imagen = document.createElement('img');
            imagen.src = URL.createObjectURL(input.files[i]);
            imagen.style.maxWidth = "100%";
            imagen.style.maxHeight = "100%";
            imagenPreviews.appendChild(imagen);
        }
    }
}

function generarNombreYFecha() {
    var inputFile = document.getElementById("imgFoto");
    var nombreInput = document.getElementById("nombreFoto");
    var fechaInput = document.getElementById("fechaFoto");
    if (inputFile.files.length > 0) {
        var nombreArchivo = inputFile.files[0].name;
        var nombreSinExtension = nombreArchivo.split('.').slice(0, -1).join('.');
        nombreInput.value = nombreSinExtension;
        var fechaActual = new Date().toISOString().split('T')[0]; // Obtiene la fecha actual en formato YYYY-MM-DD
        fechaInput.value = fechaActual;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    var checkboxes = document.querySelectorAll('.seleccionarImagen');
    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            var idImagen = this.parentElement.id.split('_')[1];
            // Puedes realizar acciones basadas en la selección/deselección del checkbox
            console.log('Imagen seleccionada:', idImagen, 'Seleccionada:', this.checked);
            // Actualizar la clase de la tarjeta al cambiar el estado del checkbox
            var tarjeta = this.parentElement;
            tarjeta.classList.toggle('seleccionada', this.checked);
        });
    });
});
function seleccionarTarjeta(id) {
    var checkbox = document.querySelector('#imagen_' + id + ' .seleccionarImagen');
    checkbox.checked = !checkbox.checked; // Invertir el estado del checkbox al hacer clic en la tarjeta

    // Actualizar la clase de la tarjeta al cambiar el estado del checkbox
    var tarjeta = checkbox.parentElement;
    tarjeta.classList.toggle('seleccionada', checkbox.checked);
}

/* VER IMAGEN EN EL FORMULARIO */

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
// Agrega esta función en tu script JavaScript
function enviarImagenesSeleccionadas() {
    var checkboxes = document.querySelectorAll('.seleccionarImagen');
    var selectedPhotos = [];
    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            selectedPhotos.push(checkbox.value);
        }
    });
    // Verifica si al menos una imagen está seleccionada
    if (selectedPhotos.length > 0) {
// Puedes redirigir a otra página y pasar los IDs como parámetros de la URL
        var url = "publicacion.jsp?idImagenes=" + selectedPhotos.join(',');
        window.location.href = url;
    } else {
        alert("Selecciona al menos una imagen antes de enviar.");
    }
}

/*formulario de comentario de la publicacion*/
function mostrarFormulario(publicacionId) {
// Oculta todos los formularios
    $('[id^="formulario_"]').hide();
    // Muestra el formulario específico de la publicación seleccionada
    $('#formulario_' + publicacionId).show();
}

function enviarComentario(publicacionId) {
// Puedes realizar acciones adicionales antes de enviar el formulario si es necesario

// Envía el formulario directamente
    $('#formComentario_' + publicacionId).submit();
}

function cerrarFormulario(id) {
    document.getElementById("formulario_" + id).style.display = "none";
}

function mostrarFormulario(publicacionId) {
    var formulario = document.getElementById("formulario_" + publicacionId);
    formulario.style.display = "block";
    formulario.style.position = "fixed";
    formulario.style.top = "50%";
    formulario.style.left = "50%";
    formulario.style.transform = "translate(-50%, -50%)";
}

function cerrarFormulario(id) {
    var formulario = document.getElementById("formulario_" + id);
    formulario.style.display = "none";
}

/* RESPUESTA EN PANTALLA OCULTO*/
function mostrarOcultarRespuestas(idComentario, cantidadRespuestas) {
    var respuestasContainer = $('#respuestasContainer_' + idComentario);
    var botonRespuestas = $('#respuestasButton_' + idComentario);
    if (respuestasContainer.is(':hidden')) {
        respuestasContainer.show();
        botonRespuestas.text('Ocultar = ' + cantidadRespuestas);
    } else {
        respuestasContainer.hide();
        botonRespuestas.text('Ver respuestas = ' + cantidadRespuestas);
    }
}

/* FORMULARIO DE RESPUESTA */

function mostrarFormularioRespuesta(idComentario) {
    document.getElementById("formularioRespuesta_" + idComentario).style.display = "block";
}

function cerrarFormularioRespuesta(idComentario) {
    document.getElementById("formularioRespuesta_" + idComentario).style.display = "none";
}



function mostrarFormularioRespuesta(idComentario) {
    var formulario = document.getElementById("formularioRespuesta_" + idComentario);
    formulario.style.display = "block";
    formulario.style.position = "fixed";
    formulario.style.top = "50%";
    formulario.style.left = "50%";
    formulario.style.transform = "translate(-50%, -50%)";
}

function cerrarFormularioRespuesta(idComentario) {
    var formulario = document.getElementById("formularioRespuesta_" + idComentario);
    formulario.style.display = "none";
}

/* BOTON DE  LINK */

   function incrementarLikes(idPublicacion) {
        // Incrementar el contador en el front-end
        var likeCountElement = document.getElementById("likeCount_" + idPublicacion);
        var currentCount = parseInt(likeCountElement.innerText, 10);
        var newCount = currentCount + 1;
        likeCountElement.innerText = newCount;

        // Llamar al servlet para actualizar en el backend
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "guardarMeGusta", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Manejar la respuesta del servlet si es necesario
            }
        };
        xhr.send("idPublicacion=" + idPublicacion + "&newCount=" + newCount);
    }




// BOTON DE COMPARTIR EN FACE Y WHATSAPP //

function mostrarBotonesDeCompartir(idPublicacion, titulo, url) {
    var formulario = document.getElementById("BotonesDeCompartir");
    formulario.style.display = "block";
    formulario.style.position = "fixed";
    formulario.style.top = "50%";
    formulario.style.left = "50%";
    formulario.style.transform = "translate(-50%, -50%)";
    document.getElementById("ventanaEmergenteTitulo").innerText = "Compatir esto: " + titulo;
}

function cerrarBotonesDeCompartir(idComentario) {
    var formulario = document.getElementById("BotonesDeCompartir");
    formulario.style.display = "none";
}

function cerrarVentanaEmergente() {
    var ventanaEmergente = document.getElementById("ventanaEmergente");
    if (ventanaEmergente) {
        ventanaEmergente.style.display = "none";
    }
}

function obtenerUrlActual() {
    return window.location.href;
}

function compartirEnFacebook() {
    var url = obtenerUrlActual();
    var enlace = "https://www.facebook.com/sharer/sharer.php?u=" + encodeURIComponent(url);
    window.open(enlace, "_blank");
}

function compartirEnWhatsApp(idPublicacion, titulo, url) {
    var mensajeWhatsApp = "¡Echa un vistazo a esta publicación!\n" + titulo + "\n" + url;
    var enlace = "https://wa.me/?text=" + encodeURIComponent(mensajeWhatsApp);
    window.open(enlace, "_blank");
}

//BARRA DE NAVEGACION ESTATICA EN LA PARTE SUPERIOR DE LA PAGINA//

$(document).ready(function () {
    var navbar = $(".navbar");
    var lastScrollTop = 0;
    var timeout;

    function showNavbar() {
        navbar.addClass("fixed-top");
        navbar.removeClass("transparent-bg").animate({
            width: "100%",
            marginLeft: "auto",
            marginRight: "auto"
        }, 500); // Animación de 500ms para aparecer suavemente
    }

    $(window).scroll(function () {
        var scroll = $(window).scrollTop();

        clearTimeout(timeout);

        if (scroll > lastScrollTop) {
            // Hacia abajo
            if (scroll > navbar.height()) {
                showNavbar();
            }
        } else {
            // Hacia arriba
            hideNavbar();
        }

        timeout = setTimeout(hideNavbar, 2000); // Desaparecer después de 2 segundos de inactividad
        lastScrollTop = scroll;
    });

    function hideNavbar() {
        navbar.removeClass("fixed-top");
        navbar.addClass("transparent-bg").animate({
            width: "100%"
        }, 500); // Animación de 500ms para desaparecer suavemente
    }
});



//BOTON DE MOSTRAR OCULTAR COMENTARIOS //


function mostrarOcultarComentarios(idPublicacion, cantidadComentario) {
    var comentariosContainer = document.getElementById('comentariosContainer_' + idPublicacion);
    var contadorComentarios = document.querySelector('.contadorComentarios_' + idPublicacion);
    // Si el contenedor de comentarios está oculto, muéstralo
    if (comentariosContainer.style.display === 'none') {
        comentariosContainer.style.display = 'block';
    } else {
// Si el contenedor de comentarios está visible, ocúltalo
        comentariosContainer.style.display = 'none';
    }

// Actualizar solo la cantidad de comentarios en el botón
    contadorComentarios.innerText = +cantidadComentario;
}

//BOTON FLOTANTE PARA IN AL INICIO DE LA PAGINA//
window.onscroll = function () {
    scrollFunction()
};

function scrollFunction() {
    var btn = document.getElementById("floating-btn");
    // Muestra el botón cuando el desplazamiento es mayor a 500px
    if (document.body.scrollTop > 500 || document.documentElement.scrollTop > 500) {
        btn.style.display = "block";
    } else {
        btn.style.display = "none";
    }
}
