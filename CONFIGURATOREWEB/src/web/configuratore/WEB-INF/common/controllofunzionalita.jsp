<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<c:set var="OPRicercaUtente" value="false"/>
<c:set var="OPInserisciNuovoUtente" value="false"/>
<c:set var="OPExportUtentiAbilitazioni" value="false"/>
<c:set var="OPRichiestaCredenzialiRUPAR" value="false"/>
<c:set var="OPInvioMailUtente" value="false"/>
<c:set var="OPModificaConfigurazioneUtente" value="false"/>
<c:set var="OPExportUtentiAbilitazioni" value="false"/>
<c:set var="OPRicercaRuolo" value="false"/>
<c:set var="OPInserisciNuovoRuolo" value="false"/>
<c:set var="OPModificaRuolo" value="false"/>
<c:set var="OPRicercaProfilo" value="false"/>
<c:set var="OPInserisciNuovoProfilo" value="false"/>
<c:set var="OPModificaProfilo" value="false"/>
<c:set var="OPRicercaCredenzialiRUPAR" value="false"/>
<c:set var="OPListaSOLconConfiguratore" value="false"/>
<c:set var="OPListaProfiliCompleta" value="false"/>
<c:set var="OPRicercaAbilitazioneMassiva" value="false"/>
<c:set var="OPRicercaDisabilitazioneMassiva" value="false"/>
<c:set var="OPRVisualizzaStoricoMassiva" value="false"/>

<c:forEach items="${sessionScope.data.utente.funzionalitaAbilitate}" var="funzionalita">

 <c:if test="${funzionalita eq 'OP-RicercaUtente'}">
 	<c:set var="OPRicercaUtente" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-InserisciNuovoUtente'}">
 	<c:set var="OPInserisciNuovoUtente" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-ExportUtentiAbilitazioni'}">
 	<c:set var="OPExportUtentiAbilitazioni" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-RichiestaCredenzialiRUPAR'}">
 	<c:set var="OPRichiestaCredenzialiRUPAR" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-InvioMailUtente'}">
 	<c:set var="OPInvioMailUtente" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-ModificaConfigurazioneUtente'}">
 	<c:set var="OPModificaConfigurazioneUtente" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-ExportUtentiAbilitazioni'}">
 	<c:set var="OPExportUtentiAbilitazioni" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-RicercaRuolo'}">
 	<c:set var="OPRicercaRuolo" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-InserisciNuovoRuolo'}">
 	<c:set var="OPInserisciNuovoRuolo" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-ModificaRuolo'}">
 	<c:set var="OPModificaRuolo" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-RicercaProfilo'}">
 	<c:set var="OPRicercaProfilo" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-InserisciNuovoProfilo'}">
 	<c:set var="OPInserisciNuovoProfilo" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-ModificaProfilo'}">
 	<c:set var="OPModificaProfilo" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-RicercaCredenzialiRUPAR'}">
 	<c:set var="OPRicercaCredenzialiRUPAR" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-ListaSOLconConfiguratore'}">
 	<c:set var="OPListaSOLconConfiguratore" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-ListaProfiliCompleta'}">
 	<c:set var="OPListaProfiliCompleta" value="true"/>
 </c:if>
  <c:if test="${funzionalita eq 'OP-RicercaAbilitazioneMassiva'}">
 	<c:set var="OPRicercaAbilitazioneMassiva" value="true"/>
 </c:if>
   <c:if test="${funzionalita eq 'OP-RicercaDisabilitazioneMassiva'}">
 	<c:set var="OPRicercaDisabilitazioneMassiva" value="true"/>
 </c:if>
 <c:if test="${funzionalita eq 'OP-VisualizzaStoricoMassiva'}">
	<c:set var="OPRVisualizzaStoricoMassiva" value="true"/>
</c:if>
 
 <%-- nuove funzioni --%>
 <c:if test="${funzionalita eq 'OP-RicercaApplicazione'}">
	<c:set var="OPRicercaApplicazione" value="true"/>
</c:if>
 <c:if test="${funzionalita eq 'OP-ModificaApplicazione'}">
	<c:set var="OPModificaApplicazionec" value="true"/>
</c:if>
 <c:if test="${funzionalita eq 'OP-InserisciApplicazione'}">
	<c:set var="OPInserisciNuovoSOL" value="true"/>
</c:if>
 <c:if test="${funzionalita eq 'OP-RicercaFunzionalita'}">
	<c:set var="OPRicercaFunzionalita" value="true"/>
</c:if>
 <c:if test="${funzionalita eq 'OP-ModificaFunzionalita'}">
	<c:set var="OPModificaFunzionalita" value="true"/>
</c:if>
 <c:if test="${funzionalita eq 'OP-InserisciFunzionalita'}">
	<c:set var="OPInserisciFunzionalita" value="true"/>
</c:if>
  
 
</c:forEach>