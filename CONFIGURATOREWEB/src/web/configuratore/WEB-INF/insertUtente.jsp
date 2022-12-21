<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="common/top.jsp" />

<jsp:useBean id="command" scope="request" type="it.csi.solconfig.configuratoreweb.presentation.model.FormNuovoUtente"/>
<jsp:useBean id="dataFromAura" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="contratti" scope="request" type="java.util.List"/>
<jsp:useBean id="dataSent" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="profileComplete" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="mailaura" scope="request" class="java.lang.String"/>

<%@include file="common/controllofunzionalita.jsp" %>
<div id="main_content" class="app-view">
    <div class="app-container">
        <div class="app-row justify-content-md-center">
            <div class="col-12 col-lg-8">
                <div class="link mt-9">
                    <form id="backForm" action="<c:url value="/cercaUtente" />" method="post"
                          class="form-inline">
                        <input type="hidden" name="codiceFiscale" value="${command.cf}"/>
                        <a class="link-prev"
                               href="<c:url value="/utenti" />">Indietro</a>
                        <!-- <a href="#" class="link-prev"> Indietro</a> -->
                    </form>
                </div>
                <h1 class="titolocard mt-9">Configurazione utente</h1>

                <c:if test="${dataSent}">
                    <c:if test="${empty errori}">
                        <div class="alert alert-success" role="alert">
                            <p>La configurazione &egrave; stata salvata correttamente</p>
	                        <c:if test="${not empty mailaura}">
	                        	<p>${mailaura}</p>
	                        </c:if>
                        </div>
                    </c:if>

                    <c:if test="${not empty errori}">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${errore.getTipoMessaggio() eq 'S'}">
                                <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        ${errore.getDescrizione()}
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                            </c:if>
                            <c:if test="${errore.getTipoMessaggio() eq 'W'}">
                                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                        ${errore.getDescrizione()}
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                            </c:if>
                            <c:if test="${errore.getTipoMessaggio() eq 'E'}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        ${errore.getDescrizione()}
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </c:if>

                <form:errors path="*" element="div" cssClass="alert alert-danger"/>

                <form method="post" id="formMail" action="${pageContext.request.contextPath}/inserisciUtente/invioEmail/${command.cf}"></form>

                <form method="post" id="formRupar"
                      action="${pageContext.request.contextPath}/inserisciUtente/richiestaCredenzialiRupar/${command.cf}">
                    <input type="hidden" name="profileComplete" value="${profileComplete}" /> </form>

                <c:if test="${not dataSent}">
                    <form:form method="post" action="${pageContext.request.contextPath}/inserisciUtente">
	             		<spring:bind path="profiliSol">
	                        <c:if test="${status.error}">
	                            <div class="alert alert-danger">
	                                <c:forEach items="${status.errorMessages}" var="message">
	                                    <p>${message}</p>
	                                </c:forEach>
	                            </div>
	                        </c:if>
	                    </spring:bind>
                        <!-- inizio box dati utente-->
                        <div class="card-box card-box-white card-box-notifiche mt-2 user-data-group">
                            <div class="card-box-body row">
                                <div class="col-12">
                                    <h2 class="titolocard">Dati utente</h2>
                                </div>
                                <div class="col-12">
                                    <form:hidden path="idAura" value="${command.idAura}"/>

                                    <div class="form-group bmd-form-group">
                                        <custom:custominput name="nome" type="text" label="Nome"
                                                            maxlength="20" size="20"
                                                            readonly="${dataFromAura}"
                                                            helpMessage="Inserire il nome"/>
                                    </div>

                                    <div class="form-group bmd-form-group">
                                        <custom:custominput name="cognome" type="text" label="Cognome"
                                                            maxlength="20" size="20"
                                                            readonly="${dataFromAura}"
                                                            helpMessage="Inserire il cognome"/>
                                    </div>

                                    <div class="form-group input-group bmd-form-group date is-focused" data-provide="datepicker">
                                        <custom:customdatepicker name="dataDiNascita" label="Data di nascita"
                                                                 readonly="${dataFromAura}"/>
                                    </div>

                                    <div class="form-group bmd-form-group">
                                        <custom:custominput name="cf" type="text" label="Codice fiscale"
                                                            readonly="${dataFromAura}"
                                                            helpMessage="Inserire il codice fiscale"/>
                                    </div>

                                    <div class="form-group bmd-form-group">
                                        <custom:custominput name="provinciaDiNascita" type="text"
                                                            label="Provincia di nascita"
                                                            readonly="${dataFromAura}"
                                                            helpMessage="Inserire la provincia di nascita"/>
                                    </div>

                                    <div class="form-group bmd-form-group">
                                        <custom:custominput name="comuneDiNascita" type="text" label="Comune di nascita"
                                                            readonly="${dataFromAura}"
                                                            helpMessage="Inserire il comune di nascita"/>
                                    </div>

                                    <div class="radio-container">
                                        <p class="mt-2">Sesso</p>
                                        <label class="radio-inline">
                                            <input type="radio" name="sesso" value="M"
                                                   <c:if test="${command.sesso eq 'M'}">checked="checked"</c:if>
                                                   <c:if test="${dataFromAura && command.sesso ne 'M'}">disabled</c:if>
                                            /> maschio
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="sesso" value="F"
                                                   <c:if test="${command.sesso eq 'F'}">checked="checked"</c:if>
                                                   <c:if test="${dataFromAura && command.sesso ne 'F'}">disabled</c:if>
                                            /> femmina
                                        </label>
                                        <form:errors path="sesso" cssClass="small text-danger" element="p"/>
                                    </div>

                                    <div class="form-group bmd-form-group">
                                        <custom:custominput name="email" type="email" label="Email"
                                                            helpMessage="Inserire l'email"/>
                                    </div>

                                    <div class="form-group bmd-form-group">
                                        <custom:custominput name="telefono" type="text" label="Numero di cellulare"
                                                            helpMessage="Inserire il numero di cellulare"/>
                                    </div>

                                    <span class="bmd-form-group is-filled">
                                    <div class="switch mt-2">
                                        <p class="mt-2">Stato</p>
                                        <label>
                                            <form:checkbox id="stato" path="stato" checked="checked"/> Attivo
                                        </label>
                                      </div>
                                    <form:errors path="stato" cssClass="text-danger" element="small"/>
                                </span>

                                    <div class="form-group input-group date bmd-form-group" data-provide="datepicker">
                                        <custom:customdatepicker name="dataFineValidita"
                                                                 label="Data fine validit&agrave;"/>
                                    </div>

                                    <div class="form-group date bmd-form-group is-filled">
                                        <form:label path="contratto" class="bmd-label-floating">Inquadramento
                                            contrattuale</form:label>
                                        <form:select class="custom-select" path="contratto">
                                            <form:option value="">-</form:option>
                                            <form:options items="${contratti}" itemLabel="descrizione" itemValue="id"/>
                                        </form:select>
                                        <form:errors path="contratto" element="small" cssClass="text-danger"/>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="row prosegui mt-9">
                                        <div class="col-12 indietro">
                                            <button class="btn btn-primary btn-outline-primary" type="button"
                                                    id="undoUserDataBtn">
                                                Annulla
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- fine box dati utente-->

                        <!-- RUOLI -->
                        <div class="card-box card-box-white card-box-notifiche mt-2">
                            <div class="card-box-body row">
                                <div class="col-12">
                                    <h2 class="titolocard">Ruolo</h2>
                                    <spring:bind path="ruoli">
                                        <c:if test="${status.error}">
                                            <div class="alert alert-danger">
                                                <c:forEach items="${status.errorMessages}" var="message">
                                                    <p>${message}</p>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </spring:bind>

                                    <form:hidden path="ruoli"/>
                                    <form:hidden path="ruoliFromOpessan"/>

                                    <div id="rolesContainer"></div>

                                    <div>
                                        <a class="btn btn-outline-aggiungi" role="button" id="addRoleBtn">Aggiungi
                                            ruolo</a>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="row prosegui mt-9">
                                        <div class=" col-sm-12 indietro">
                                            <button class="btn btn-primary btn-outline-primary" type="button"
                                                    id="rolesUndoBtn">Annulla
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- COLLOCAZIONI -->
                        <div class="card-box card-box-white card-box-notifiche mt-2">
                            <div class="card-box-body row">
                                <div class="col-12">
                                    <h2 class="titolocard">Collocazione</h2>
                                    <spring:bind path="collocazioni">
                                        <c:if test="${status.error}">
                                            <div class="alert alert-danger">
                                                <c:forEach items="${status.errorMessages}" var="message">
                                                    <p>${message}</p>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </spring:bind>

                                    <form:hidden path="collocazioni"/>
                                    <form:hidden path="collocazioniFromOpessan"/>

                                    <div id="collocationContainer"></div>

                                    <div class="mt-6">
                                        <a class="btn btn-outline-aggiungi" role="button" id="addCollocationBtn">Aggiungi
                                            sede operativa</a>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="row prosegui mt-9">
                                        <div class=" col-sm-12 indietro">
                                            <button class="btn btn-primary btn-outline-primary" id="collocationsUndoBtn"
                                                    type="button">
                                                Annulla
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- SOL -->
                        <form:hidden path="profiliSol"/>

                        <div id="solContainer"></div>

                        <div class="row mt-6">
                            <div class="col">
                                <a class="btn btn-outline-aggiungi-primary" role="button" id="addSolBtn">Nuova Configurazione</a>
                            </div>
                        </div>

                        <div class="row prosegui mt-9">
                            <div class="col-md-6 col-sm-12 indietro">
                                <button class="btn btn-primary btn-outline-primary" type="button" id="formUndoBtn">
                                    Annulla
                                </button>
                            </div>
                            <div class="col-md-6 col-12 text-md-right mt-2 mt-md-0">
                                <button class="btn btn-primary" type="button" id="formSubmitBtn">Salva</button>
                            </div>
                        </div>
                    </form:form>
                </c:if>

                <c:if test="${dataSent}">
                    <c:if test="${OPInvioMailUtente == true}">
                        <c:choose>
                            <c:when test="${profileComplete}">
                                <div class="card-box card-box-ruolo mt-7">
                                    <a href="#" id="sendMailToUserBtn">
                                    <span class="card-box-image">
                                        <img src="/ris/im/icone/scrivi.svg" alt="">
                                    </span>
                                        <span class="card-box-text">Invia una email all'utente profilato</span>
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="card-box card-box-ruolo mt-7" style="filter: grayscale(1)">
                                    <a style="cursor: default">
                                    <span class="card-box-image">
                                        <img src="/ris/im/icone/scrivi.svg" alt="">
                                    </span>
                                        <span class="card-box-text text-muted">Invia una email all'utente profilato</span>
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>

                    <c:if test="${OPRichiestaCredenzialiRUPAR == true}">
                        <div class="card-box card-box-ruolo mt-7">
                            <a href="#" id="askForRuparBtn">
                                <span class="card-box-image">
                                    <img src="/ris/im/icone/credenziali.svg" alt="">
                                </span>
                                <span class="card-box-text">Richiedi credenziali RUPAR</span>
                            </a>
                        </div>
                        <div class="row prosegui mt-9">
                            <div class="col-md-6 col-sm-12 text-md-left mt-2 mt-md-0">
                                <a class="btn btn-info btn-primary"
                                href="/configuratore/downloadFaq" value="faq">FAQ per richiesta credenziali RUPAR</a>
                                </div>
                            </div>
                    </c:if>
                    <div class="alert alert-warning mt-7" role="alert" style="display: none !important;">
                        <p>Attenzione!</p>
                        <p>
                            Con la richiesta credenziali RUPAR viene avviato il processo di richiesta credenziali.
                        </p>
                        <p>Sei sicuro di voler proseguire?</p>
                        <div class="row prosegui mt-9">
                            <div class="col-md-6 col-sm-12 indietro">
                                <input class="btn btn-primary btn-outline-primary" type="submit" value="Annulla">
                            </div>
                            <div class="col-md-6 col-12 text-md-right mt-2 mt-md-0">
                                <input class="btn btn-primary" type="submit" value="Invia richiesta">
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
            <!--fine app-row-->

        </div>
        <!--fine app-row-->

    </div>
</div>

<c:import url="utentePrototipi.jsp"/>

<c:import url="common/footer.jsp"/>

<script src="<c:url value="/js/userPage.js" />" lang="javascript"></script>

<c:choose>
    <c:when test="${not dataSent}">
        <script>
            $(() => userPage.initDataInsertPage());
        </script>
    </c:when>
    <c:otherwise>
        <script>
            $(() => userPage.initDataSentSuccessPage());
        </script>
    </c:otherwise>
</c:choose>