<%@page import="it.csi.dma.dmadd.interfacews.SiNo"%>
<%@page import="it.csi.dma.dmaccbl.monit.EndPoint"%>
<%@page import="it.csi.dma.dmaccbl.monit.Verifiche"%>
<%@page import="it.csi.dma.dmaccbl.monit.GetVerifiche"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Verifiche effettuate</title>
</head>
<body>
<%
Long timeout = 1000L;
String tagStart = "";
if ((request.getParameter("parm1") != "") && (request.getParameter("parm1") != null)) {
	timeout = Long.valueOf(request.getParameter("parm1"));	
}
List<EndPoint> result = null;
%>
<br>
<form action="monitor.jsp" method="get">
  <table border="0">
    <tr>
      <td>
        Timeout:</b>
      </td>
      <td>
        <input type="String" name="parm1" size="14" value="<%=timeout.toString() %>"/>
      </td>
      <td>
        <input type="Submit" value="Submit"/>
      </td>
      </tr>
  </table>
</form>
<p>
<br>

<table border="5">
   <tr>
      <td>
        <b>Nome</b>
      </td>
      <td>
        <b>Tipo</b>
      </td>
      <td>
        <b>Ambiente</b>
      </td>
      <td>
        <b>URL</b>
      </td>
      <td>
        <b>Stato</b>
      </td>
      <td>
        <b>Errore</b>
      </td>
   </tr>

<%
try {
	//Verifiche ver = new Verifiche();
	GetVerifiche getVer = new GetVerifiche();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	//Verifiche ver = (Verifiche) ctx.getBeansOfType(Verifiche.class).values().toArray()[0];
	Object bean = ctx.getBean("verifiche2");
	//out.println("Oggetto: "+bean);
	Verifiche ver = (Verifiche)bean;
	
	
	getVer.setTimeout(timeout);
	
	result = ver.getVerifiche(getVer);
	for (EndPoint ep : result) {
		if (ep.getStato() == SiNo.SI) {
			tagStart = "<td bgcolor=#00CC00>";
		} else {
			tagStart = "<td bgcolor=#FF0000>";
		}
		out.println("<tr><td>"+ep.getNome()+"</td><td>"+ep.getTipo()+"</td><td>"+ep.getAmbiente()+"</td><td>"+ep.getUrl()+"</td>"+tagStart+ep.getStato()+"</td><td>"+ep.getErrore()+"</td><td></tr>");
	}

}
catch (Exception e) {
	e.printStackTrace();
	out.println("Errore: "+e.getMessage()+" "+e.getCause());
//result = "Web service unreachable tonight :-(";
}
%>
</table>

</body>
</html>