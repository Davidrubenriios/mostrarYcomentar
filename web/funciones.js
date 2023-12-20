
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

// Obtener el botón y el contador por ID
const likeButton = document.getElementById('likeButton');
const likeCount = document.getElementById('likeCount');

// Inicializar el contador
let count = 0;

// Agregar un evento de clic al botón
likeButton.addEventListener('click', function () {
    // Incrementar el contador
    count++;

    // Actualizar el contenido del contador
    likeCount.textContent = count;
});

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



