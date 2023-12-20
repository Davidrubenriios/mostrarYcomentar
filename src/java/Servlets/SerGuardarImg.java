package Servlets;

import controlador.FotosJpaController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.Fotos;

@WebServlet(name = "SerGuardarImg", urlPatterns = {"/SerGuardarImg"})
@MultipartConfig
public class SerGuardarImg extends HttpServlet {

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
        String nombre = request.getParameter("nombreFoto");
        String fecha = request.getParameter("fechaFoto");

        Part filePart = request.getPart("imgFoto");
        if (filePart != null && filePart.getInputStream() != null) {
            byte[] imgData = readBytesFromInputStream(filePart.getInputStream());

            // Convertir la imagen a Base64 para almacenarla en la base de datos
            String base64Image = Base64.getEncoder().encodeToString(imgData);

            Fotos fotos = new Fotos();
            fotos.setNombreFoto(nombre);
            fotos.setImgFoto(imgData); // Guardar como bytes en la base de datos

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaDate = null;
            try {
                fechaDate = sdf.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(SerGuardarImg.class.getName()).log(Level.SEVERE, null, ex);
            }
            fotos.setFechaFoto(fechaDate);

            FotosJpaController objFotosCtl = new FotosJpaController();
            objFotosCtl.create(fotos);

            response.sendRedirect("seleccionDeImagen.jsp");
        } else {
            response.sendRedirect("seleccionDeImagen.jsp");
        }
    }

    private byte[] readBytesFromInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[8192]; // Tamaño del buffer
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        return output.toByteArray();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
