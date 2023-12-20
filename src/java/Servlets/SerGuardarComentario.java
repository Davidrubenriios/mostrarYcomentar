package Servlets;

import controlador.ComentariosJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Comentarios;
import modelo.Publicacion;
import modelo.Publicacionfotos;

/**
 *
 * @author DAVID
 */
@WebServlet(name = "SerGuardarComentario", urlPatterns = {"/SerGuardarComentario"})
public class SerGuardarComentario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario de comentarios
        String idPublicacionStr = request.getParameter("idPublicacion");
        String nombreComentario = request.getParameter("nombreComentario");
        String comentario = request.getParameter("comentario");


        try {
            // Convertir idPublicacion a Integer
            Integer idPublicacion = Integer.parseInt(idPublicacionStr);

            // Crear un nuevo objeto Comentarios
            Comentarios nuevoComentario = new Comentarios();
            nuevoComentario.setNombre(nombreComentario);
            nuevoComentario.setMjsComentarios(comentario);
            nuevoComentario.setFecha(new Date());

            // Configurar la relación con la Publicacion correspondiente
            Publicacion publicacion = new Publicacion();
            publicacion.setIdPublicacion(idPublicacion);
            nuevoComentario.setIdPublicacion(publicacion);

            // Guardar el comentario en la base de datos
            ComentariosJpaController comentariosController = new ComentariosJpaController();
            comentariosController.create(nuevoComentario);

            // Redirigir a la página principal u otra página de éxito
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } catch (NumberFormatException e) {
            // Manejar el caso en que idPublicacion no es un entero válido
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } catch (Exception e) {
            // Manejar otras excepciones
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
