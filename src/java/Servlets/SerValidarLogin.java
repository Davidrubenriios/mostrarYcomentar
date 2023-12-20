package Servlets;

import controlador.AdmisnistradorJpaController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Admisnistrador;

@WebServlet(name = "SerValidarLogin", urlPatterns = {"/SerValidarLogin"})
public class SerValidarLogin extends HttpServlet {

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
        String email = request.getParameter("inputEmail");
        String clave = request.getParameter("inputPassword");
        AdmisnistradorJpaController adminController = new AdmisnistradorJpaController();

        Admisnistrador administrador = adminController.findByEmail(email);

        if (administrador != null && administrador.getClave().equals(clave)) {
            // Usuario y contraseña válidos
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", administrador);
            response.sendRedirect("administrador.jsp?exito=true");

        } else {
            // Usuario o contraseña incorrectos
            response.sendRedirect("login.jsp?error=true");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
