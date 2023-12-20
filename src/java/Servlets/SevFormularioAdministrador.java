
package Servlets;

import controlador.AdmisnistradorJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Admisnistrador;


@WebServlet(name = "SevFormularioAdministrador", urlPatterns = {"/SevFormularioAdministrador"})
public class SevFormularioAdministrador extends HttpServlet {

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
        processRequest(request, response);
           // Obtener los parámetros del formulario
        String nombre = request.getParameter("txtNombre");
        String apellido = request.getParameter("txtApellido");
        String email = request.getParameter("txtEmail");
        String clave = request.getParameter("txtClave");
        String dniString = request.getParameter("txtDni");
        int dni = Integer.parseInt(dniString);
        String codigoAreaString = request.getParameter("txtCodigoArea");
        int codigoArea = Integer.parseInt(codigoAreaString);
        String telefonoString = request.getParameter("txtTelefono");
        int telefono = Integer.parseInt(telefonoString);

        // Crear una instancia de Admisnistrador y setear los valores
        Admisnistrador administrador = new Admisnistrador();
        administrador.setNombre(nombre);
        administrador.setApellido(apellido);
        administrador.setEmail(email);
        administrador.setClave(clave);
        administrador.setDni(dni);
        administrador.setCodigoArea(codigoArea);
        administrador.setTelefono(telefono);

        // Guardar en la base de datos utilizando JPA
        AdmisnistradorJpaController controller = new AdmisnistradorJpaController();
        controller.create(administrador);

        // Redirigir a una página de confirmación o donde sea necesario
        response.sendRedirect("index.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
