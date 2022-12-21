<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="root">${pageContext.request.contextPath}</c:set>

<%@ page import="it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp" %>
<c:set var="UTENTE_NON_PRESENTE" value="<%=ConstantsWebApp.UTENTE_NON_PRESENTE%>"/>
<c:set var="PAGINAZIONE_20_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_20_ELEMENTI%>"/>
<c:set var="PAGINAZIONE_50_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_50_ELEMENTI%>"/>
<c:set var="PAGINAZIONE_100_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_100_ELEMENTI%>"/>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="common/top.jsp"/>
<%@include file="common/controllofunzionalita.jsp" %>
<div id="main_content" class="app-view">
    <div class="app-container">
        <div class="app-row justify-content-md-center">
            <div class="col-12 col-lg-12">
                <div class="menu-interno d-lg-block" id="menu-interno">
                    <!-- finemenu dropdown responsive-->
                    <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                        <li class="nav-item"><a class="nav-link active"
                                                href="${root}/utenti"> <span>Gestione utenti</span>
                        </a></li>
                        <c:if test="${OPRicercaRuolo eq 'true'}">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${root}/ruoli"> <span>Gestione ruoli</span>
                            </a></li>
                        </c:if>
                        <c:if test="${OPRicercaProfilo eq 'true'}">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${root}/profili"> <span>Gestione profili</span>
                            </a></li>
                        </c:if>
						<c:if test="${OPRicercaApplicazione eq 'true'}">
							<li class="nav-item"><a class="nav-link"
													href="${root}/applicazioni"> <span>Gestione applicazioni</span>
							</a></li>
						</c:if>
                        <c:if test="${OPRicercaFunzionalita eq 'true'}">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${root}/funzionalita"> <span>Gestione funzionalità</span>
                            </a></li>
                        </c:if>
                        <c:if test="${OPRicercaCredenzialiRUPAR eq 'true'}">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${root}/rupar"> <span>Credenziali
									Rupar</span>
                            </a></li>
                        </c:if>
                        <c:if test="${OPRicercaAbilitazioneMassiva eq 'true'}">
	                        <li class="nav-item"><a class="nav-link"
	                                                href="${root}/abilitazione-massiva"> <span>Abilitazione massiva</span>
	                        </a></li>
                        </c:if>
                        <c:if test="${OPRicercaDisabilitazioneMassiva eq 'true'}">
	                        <li class="nav-item"><a class="nav-link"
	                                                href="${root}/disabilitazione-massiva"> <span>Disabilitazione massiva</span>
	                        </a></li>
                        </c:if>
                    </ul>
                </div>
			</div>
			<div class="col-12 col-lg-8">
                <h1 class="titolocard mt-7">Cerca utente</h1>
                <div class="card-box card-box-white card-box-notifiche">
                    <div class="card-box-body row">
                        <div class="col-12">
                            <form:form id="searchForm" action="/configuratore/cercaUtente" method="post"
                                       modelAttribute="utente" class="form-inline">
                                <div class="form-group form-group-gestutenti">
                                    <label for="cf" class="bmd-label-floating">Codice Fiscale</label>
                                    <form:input type="text" class="form-control" id="cf"
                                                name="cf" value="${utente.codiceFiscale}" path="codiceFiscale"/>
                                    <form:hidden path="numeroPagina"/>
                                    <form:hidden path="numeroElementi"/>
                                </div>
                                <span class="form-group bmd-form-group"> <!-- needed to match padding for floating labels -->
                                  <button type="submit" class="btn btn-primary" id="buttonRicerca")>Cerca</button>
                                </span>
                            </form:form>
                        </div>
                    </div>

                </div>
            </div><!--fine app-row-->


        </div>
        <!--fine app-row-->
                

        <div class="app-row justify-content-md-center mt-7 text-center">
    		<div class="col-12 col-lg-8">
            <c:if test="${OPExportUtentiAbilitazioni == true}">
                <spring:url value="/esportaUtenti" var="xlsURL"></spring:url>
				<a class="btn btn-primary btn-outline-primary" href="${xlsURL}" role="button" title="Il presente Excel contiene l'elenco degli utenti abilitati appartenenti alla tua azienda">
                    Esporta in formato XLS tutti gli utenti e le relative configurazioni
                </a>
            </c:if>
        	</div>
        </div>


        <c:if test="${not empty errori}">
            <c:forEach items="${errori}" var="errore">
                <c:choose>
                    <c:when test="${errore.getCodice().equals(UTENTE_NON_PRESENTE)}">
                        <div class="app-row justify-content-md-center mt-4">
                            <div class="col-12 col-lg-8">
                                <p>Hai cercato CF ${utente.codiceFiscale}</p>

                                <div class="row">
                                    <div class="col-12">
                                        <c:if test="${paginaUtenti.getElementiTotali() == 0}">
                                            <p><strong>Nessun utente trovato</strong></p>
                                        </c:if>
                                    </div>
                                    <div class="col-4">
                                        <form action="/configuratore/annullaRicerca" method="POST">
                                            <input type="submit" class="btn btn-primary btn-outline-primary"
                                                   role="button" value="Annulla la ricerca"/>
                                        </form>
                                    </div>
                                </div>
                                <div class="alert alert-warning mt-7" role="alert">
                                    <p><strong>ATTENZIONE</strong></p>
                                    <p>${errore.getDescrizione()}</p>
                                </div>
                                <!-- <c:if test="${OPInserisciNuovoUtente == true}">
                                    <div class="card-box card-box-newuser">
                                        <form action="/configuratore/inserisciUtente" method="GET"
                                              id="formInserisciUtente">
                                            <input type="hidden" name="cf"/>
                                            <a href="#" id="insertUtenteSubmitBtn">
                                        <span class="card-box-image">
                                            <img class="" src="/ris/im/icone/newuser.svg" alt="">
                                            <!-- l'img cambierÃ  a seconda dell'elemento -->
                                  <!--       </span>
                                                <span class="card-box-text">
                                            Inserisci nuovo utente
                                        </span>
                                            </a>
                                        </form>
                                    </div>
                                </c:if> -->
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:import url="common/alert.jsp" />
						<div class="app-row justify-content-md-center mt-4">
                            <div class="col-12 col-lg-8">
                                <div class="col-12">
                                    <%--<p>
                                        <strong>${errore.getDescrizione()}</strong>
                                    </p>--%>
                                </div>
                                <div class="col-4">
                                    <form action="/configuratore/annullaRicerca" method="POST">
                                        <input type="submit" class="btn btn-primary btn-outline-primary"
                                               role="button" value="Annulla la ricerca"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>

        <c:if test="${paginaUtenti != null && paginaUtenti.getElementiTotali() != 0}">
            <div class="app-row justify-content-md-center mt-4">
                <div class="col-12 col-lg-8">
                  <div class="row">
                    <div class="col-12">
                    </div>
                    <div class="col-4">
                       <form action="/configuratore/annullaRicerca" method="POST">
                            <input type="submit" class="btn btn-primary btn-outline-primary"
                                   role="button" value="Annulla la ricerca"/></form>
                    </div>
                  </div>
                </div>
              </div>
        </c:if>

        <c:if test="${paginaUtenti != null && paginaUtenti.getElementiTotali() != 0}">
            <div class="row info-pagination mt-9 justify-content-between">
                <div class="col-12">
                    <h2 class="title_prepagination episodi">${paginaUtenti.getElementiTotali()} Risultati trovati</h2>
                    <p class="note">Seleziona un profilo per modificarlo o eliminarlo</p>
                </div>

            </div>

            <div class="app-row justify-content-md-center">
                <div class="col-12">

                    <div class="div-overflow">
                        <table class="table table-hover table-sortable">
                            <thead>
                            <tr>
                                <th scope="col"><span class="headerTxt">Utente</span></th>
                                <th scope="col"><span class="headerTxt">Stato</span></th>
                                <th scope="col"><span class="headerTxt">Ruolo</span></th>
                                <th scope="col"><span class="headerTxt">Servizi online</span></th>
                                <th scope="col"><span class="headerTxt">Collocazione</span></th>
                                <th scope="col"><span class="headerTxt">Inserito da configuratore</span></th>
                                <th scope="col"><span class="headerTxt">Azioni</span></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${paginaUtenti.getElementi()}" var="item" varStatus="loop">
                                <tr>
                                    <c:if test="${loop.index == 0}">
                                        <td rowspan="${paginaUtenti.getElementi().size()}">${item.nome} ${item.cognome}</td>
                                    </c:if>
                                    <td>${item.stato}</td>
                                    <td>${item.ruolo}</td>

                                    <c:choose>
                                        <c:when test="${sessionScope.data.utente.profilo == 'SUPERUSERCONF'}">
                                        <td>
                                                ${item.servizioOnLine}
                                        </td>
                                        <td>
                                                ${item.collocazione}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${item.inseritoDalConfiguratore == 'S'}">
                                            <span class="ico_attivo" title="si">
                                                                <span class="sr-only">si </span><span>&nbsp;</span>
                                            </span>
                                                </c:when>
                                                <c:otherwise>


                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="${item.servizioOnLine == null ? 'cell-disable' : ''}">
                                                ${item.servizioOnLine}
                                        </td>
                                        <td class="${item.collocazione == null ? 'cell-disable' : ''}">
                                                ${item.collocazione}
                                        </td>
                                        <td class="${item.inseritoDalConfiguratore == null ? 'cell-disable' : ''}">
                                            <c:choose>
                                                <c:when test="${item.inseritoDalConfiguratore == 'S'}">
                                            <span class="ico_attivo" title="si">
                                                                <span class="sr-only">si </span><span>&nbsp;</span>
                                            </span>
                                                </c:when>
                                                <c:otherwise>


                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:otherwise>
                                    </c:choose>

                                    <c:if test="${loop.index == 0}">
                                        <td rowspan="${paginaUtenti.getElementi().size()}">
                                            <c:if test="${OPModificaConfigurazioneUtente == true}">
                                                <form action="/configuratore/modificaUtente" method="GET"
                                                      id="formModificaUtente">
                                                    <input type="hidden" name="cf"/>
                                                    <a class="ico_modifica" title="modifica" href="#"
                                                       id="modificaUtenteSubmitBtn">
                                                        <span class="sr-only">modifica </span><span>&nbsp;</span>
                                                    </a>
                                                </form>
                                            </c:if>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>


                    <!--/div-->
                </div>


            </div>
            <div class="row">
                <div class="col-12" id="pageLinkBox">
                    Seleziona il numero di elementi da visualizzare:
                    <a class="page-link-view" href="#" data-elementi="${PAGINAZIONE_20_ELEMENTI}">20</a> |
                    <a class="page-link-view" href="#" data-elementi="${PAGINAZIONE_50_ELEMENTI}">50</a> |
                    <a class="page-link-view" href="#" data-elementi="${PAGINAZIONE_100_ELEMENTI}">100</a>
                </div>
            </div>
            <div class="row pagination mt-9">
                <div class="col-12" id="pageNavigation">
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <c:forEach begin="1" end="${paginaUtenti.getPagineTotali()}" step="1" var="index">
                                <c:if test="${index == 1}">
                                    <c:choose>
                                        <c:when test="${utente.numeroPagina <= 1}">
                                            <li class="page-item disabled"><a class="page-link disabled" href="#">Precedente</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link" href="#"
                                                                     data-pagina="${utente.numeroPagina-1}">Precedente</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:choose>
                                    <c:when test="${index == utente.numeroPagina}">
                                        <li class="page-item active"><a class="page-link" href="#"
                                                                        data-pagina="${index}">${index}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link" href="#"
                                                                 data-pagina="${index}">${index}</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${index == paginaUtenti.getPagineTotali()}">
                                    <c:choose>
                                        <c:when test="${utente.numeroPagina >= paginaUtenti.getPagineTotali()}">
                                            <li class="page-item disabled"><a class="page-link disabled" href="#">Successivo</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link" href="#"
                                                                     data-pagina="${utente.numeroPagina+1}">Successivo</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:forEach>

                        </ul>
                    </nav>
                </div>
            </div>
        </c:if>
    </div>
</div>

<c:import url="common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/gestioneUtenti.js" lang="javascript"></script>