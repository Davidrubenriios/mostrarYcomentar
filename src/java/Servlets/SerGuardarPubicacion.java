package Servlets;

import controlador.FotosJpaController;
import controlador.PublicacionJpaController;
import controlador.PublicacionfotosJpaController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Fotos;
import modelo.Publicacion;
import modelo.Admisnistrador;
import modelo.Publicacionfotos;

@WebServlet(name = "SerGuardarPubicacion", urlPatterns = {"/SerGuardarPubicacion"})
public class SerGuardarPubicacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mostrarYcomentarPU");
        EntityManager em = emf.createEntityManager();

        String titulo = request.getParameter("titulo");
        String contenido = request.getParameter("contenido");

        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formato.format(fechaActual);

        try {
            em.getTransaction().begin();

            // Guardar información en la tabla Publicacion
            Publicacion publicacion = new Publicacion();
            publicacion.setTitulo(titulo);
            publicacion.setTexto(contenido);
            publicacion.setFechaPublicacion(fechaActual);

            // Obtener ID del administrador desde la sesión
            Admisnistrador administrador = (Admisnistrador) request.getSession().getAttribute("usuario");

            // Establecer el administrador en la publicación
            publicacion.setIdAdministrador(administrador);

            PublicacionJpaController publicacionController = new PublicacionJpaController(emf);
            publicacionController.create(publicacion);

            // Recorrer las imágenes y guardar los IDs en la tabla Publicacionfotos
            String[] idFotos = request.getParameterValues("idFoto");
            int idPublicacion = publicacion.getIdPublicacion();
            PublicacionfotosJpaController publicacionfotosController = new PublicacionfotosJpaController(emf);
            FotosJpaController objFotosCtl = new FotosJpaController();
            for (String idFoto : idFotos) {
                int idFotoInt = Integer.parseInt(idFoto);
                Fotos foto = objFotosCtl.obtenerFotoPorId(idFotoInt);

                Publicacionfotos publicacionfotos = new Publicacionfotos();
                publicacionfotos.setIdPublicacion(publicacion);
                publicacionfotos.setIdFotos(foto);

                publicacionfotosController.create(publicacionfotos);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

        response.sendRedirect("index.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
