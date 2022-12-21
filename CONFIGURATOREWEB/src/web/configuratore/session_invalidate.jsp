<%@ page import="java.util.Enumeration,
                 java.io.ByteArrayOutputStream,
                 java.io.ObjectOutputStream,
                 java.io.NotSerializableException"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Session Objects</title></head>
  <body>
    <h1>Session Objects</h1>
    <table border="1">
        <tr width="100%">
            <th>Key</th>
            <th>Value</th>
            <th>Data</th>
        </tr>
        <%
            long sessionSize=0L;
            Enumeration e=session.getAttributeNames();
            session.invalidate();
        %>
    </table>
    <p>Sessione invalidata</p>
  </body>
</html>
