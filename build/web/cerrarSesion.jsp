<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
    session.invalidate(); // invalida la sesión actual
    response.sendRedirect("index.jsp"); // redirige al usuario a la página de inicio
%>
</html>
