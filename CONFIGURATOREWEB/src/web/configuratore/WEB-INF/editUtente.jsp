<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="common/top.jsp" />

<jsp:useBean id="command" scope="request" type="it.csi.solconfig.configuratoreweb.presentation.model.FormNuovoUtente"/>
<jsp:useBean id="fromConfiguratore" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="contratti" scope="request" type="java.util.List"/>
<jsp:useBean id="profileComplete" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="saved" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="mail" scope="request" type="java.lang.Boolean"/>


<%@include file="common/controllofunzionalita.jsp" %>
<div id="main_content" class="app-view">
    <div class="app-container">
        <div class="app-row justify-content-md-center">
            <div class="col-12 col-lg-8">
                <div class="link mt-9">
                    <form id="backForm" action="<c:url value="/cercaUtente" />" method="post"
                          class="form-inline">
                        <input type="hidden" name="codiceFiscale" value="${command.cf}"/>
                        <a href="#" class="link-prev"> Indietro</a>
                    </form>
                </div>
                <h1 class="titolocard mt-9">Modifica utente</h1>

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

                <form method="post" id="disableAllConfigsForm"
                      action="<c:url value="/modificaUtente/${command.id}/disabilitaConfig" />"></form>

                <form method="post" id="formMail"
                      action="${pageContext.request.contextPath}/modificaUtente/invioEmail/${command.cf}"></form>
                      
                <form method="post" id="formRupar"
                      action="${pageContext.request.contextPath}/modificaUtente/richiestaCredenzialiRupar/${command.cf}">
                    <input type="hidden" name="profileComplete" value="${profileComplete}" /> </form>                      

                <form:form method="post" action="${pageContext.request.contextPath}/modificaUtente/${command.id}">
                    <form:errors path="*" element="div" cssClass="alert alert-danger"/>
                    <!-- inizio box dati utente-->
                    <div class="card-box card-box-white card-box-notifiche mt-2 user-data-group">
                        <div class="card-box-body row">
                            <div class="col-12">
                                <h2 class="titolocard">Dati utente
                                    <a class="btn btn-link btn-settings" id="userDataToggleBtn">
                                        <span class="fa fa-pencil icon-btn-settings"></span>
                                    </a>
                                </h2>
                            </div>
                            <div class="col-12">
                                <div class="form-group bmd-form-group">
                                    <custom:custominput name="nome" type="text" label="Nome"
                                                        maxlength="20" size="20"
                                                        readonly="true"
                                                        helpMessage="Inserire il nome"/>
                                </div>

                                <div class="form-group bmd-form-group">
                                    <custom:custominput name="cognome" type="text" label="Cognome"
                                                        maxlength="20" size="20"
                                                        readonly="true"
                                                        helpMessage="Inserire il cognome"/>
                                </div>

                                <div class="form-group input-group bmd-form-group date is-focused" data-provide="datepicker">
                                    <custom:customdatepicker name="dataDiNascita" label="Data di nascita"
                                                             readonly="true"/>
                                </div>

                                <div class="form-group bmd-form-group">
                                    <custom:custominput name="cf" type="text" label="Codice fiscale"
                                                        readonly="true"
                                                        helpMessage="Inserire il codice fiscale"/>
                                </div>

                                <div class="form-group bmd-form-group">
                                    <custom:custominput name="provinciaDiNascita" type="text"
                                                        label="Provincia di nascita"
                                                        readonly="true"
                                                        helpMessage="Inserire la provincia di nascita"/>
                                </div>

                                <div class="form-group bmd-form-group">
                                    <custom:custominput name="comuneDiNascita" type="text" label="Comune di nascita"
                                                        readonly="true"
                                                        helpMessage="Inserire il comune di nascita"/>
                                </div>

                                <div class="radio-container">
                                    <p class="mt-2">Sesso</p>
                                    <label class="radio-inline">
                                        <input type="radio" name="sessoUtente" value="M"
                                               <c:if test="${command.sesso eq 'M'}">checked="checked"</c:if>
                                               <c:if test="${(true) and command.sesso ne 'M'}">disabled</c:if>
                                        /> maschio
                                        <form:hidden path="sesso"/>
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="sessoUtente" value="F"
                                               <c:if test="${command.sesso eq 'F'}">checked="checked"</c:if>
                                               <c:if test="${(true) and command.sesso ne 'F'}">disabled</c:if>
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
                                            <form:checkbox id="statoUtente" path="stato" checked="${command.stato eq true ? 'checked' : ''}"/> Attivo
                                            <form:hidden path="stato"/>
                                        </label>
                                      </div>
                                    <form:errors path="stato" cssClass="text-danger" element="small"/>
                                </span>

                                <div class="form-group input-group bmd-form-group date is-focused" data-provide="datepicker">
                                    <custom:customdatepicker name="dataFineValidita" label="Data fine validit&agrave;"/>
                                </div>

                                <div class="form-group date bmd-form-group is-filled">
                                    <form:label path="contratto" class="bmd-label-floating">Inquadramento
                                        contrattuale</form:label>
                                    <form:select class="custom-select" path="contratto">
                                        <form:option value="">-</form:option>
                                        <form:options items="${contratti}" itemLabel="descrizione" itemValue="id"/>
                                    </form:select>
                                    <form:errors path="contratto" element="small" cssClass="text-danger"/>
                                    <c:if test="${not fromConfiguratore}">
                                        <input type="hidden" name="contratto" value="${command.contratto}"
                                               data-default-readonly="${not fromConfiguratore}"/>
                                    </c:if>
                                </div>
                                <c:if test="${fromConfiguratore}">
                                    <p class="p-titform mt-6">INSERITO DA CONFIGURATORE</p>
                                </c:if>
                            </div>
                            <div class="col-12">
                                <div class="row prosegui mt-9">
                                    <div class="col-md-6 col-12 indietro">
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

                    <div class="row mt-7">
                        <div class="col-12">
                            <button class="btn btn-outline-danger" type="button" id="disableAllConfigsBtn"
                                    <c:if test="${not profileComplete}"><c:out value="disabled='disabled'"/></c:if>>
                                Disabilita tutte le configurazioni
                                <div class="ripple-container"></div>
                                <div class="ripple-container"></div>
                            </button>
                        </div>
                    </div>
					
					<br><br>

                    <div class="accordion">
                        <!-- RUOLI -->
                        <div class="card accordion-box roles-accordion">
                            <div class="card-header" id="headingOne">
                                <h3 class="mb-0">
                                    <button class="btn btn-link text-left collapsed" data-toggle="collapse"
                                            data-target="#collapseOne" type="button"
                                            aria-expanded="false" aria-controls="collapseOne">
                                        Ruolo
                                    </button>
                                </h3>
                            </div>

                            <div id="collapseOne" class="collapse" aria-labelledby="headingOne"
                                 data-parent="#accordion">
                                <div class="card-body">
                                    <div class="titolocard">
                                        <a class="btn btn-link btn-settings" href="#" id="rolesToggleBtn">
                                            <span class="fa fa-pencil icon-btn-settings"></span>
                                        </a>
                                    </div>
                                    <div class="row">
                                        <!-- START CONTENT -->
                                        <div class="col-12">
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
                                            <input type="text"  style="display: none;" id="mailInviata" hidden>
                                            

                                            <div id="rolesContainer"></div>
                                           

                                            <div>
                                                <a class="btn btn-outline-aggiungi" role="button" id="addRoleBtn">Aggiungi
                                                    ruolo</a>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="row prosegui mt-9">
                                                <div class="col-md-6 col-sm-12 indietro">
                                                    <button class="btn btn-primary btn-outline-primary" type="button"
                                                            id="rolesUndoBtn">Annulla
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- END CONTENT -->
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- COLLOCAZIONI -->
                        <div class="card accordion-box collocations-accordion">
                            <div class="card-header" id="headingTwo">
                                <h3 class="mb-0">
                                    <button class="btn btn-link text-left collapsed" data-toggle="collapse"
                                            data-target="#collapseTwo" type="button"
                                            aria-expanded="false" aria-controls="collapseTwo">
                                        Collocazione
                                    </button>
                                </h3>
                            </div>

                            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
                                 data-parent="#accordion">
                                <div class="card-body">
                                    <div class="titolocard">
                                        <a class="btn btn-link btn-settings" href="#" id="collocationsToggleBtn">
                                            <span class="fa fa-pencil icon-btn-settings"></span>
                                        </a>
                                    </div>
                                    <div class="row">
                                        <!-- START CONTENT -->
                                        <div class="col-12">
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

                                            <div id="collocationContainer"></div>

                                            <div class="mt-6">
                                                <a class="btn btn-outline-aggiungi" role="button"
                                                   id="addCollocationBtn">Aggiungi
                                                    sede operativa</a>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="row prosegui mt-9">
                                                <div class="col-md-6 col-sm-12 indietro">
                                                    <button class="btn btn-primary btn-outline-primary"
                                                            id="collocationsUndoBtn" type="button">
                                                        Annulla
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- END CONTENT -->
                                    </div>
                                </div>
                            </div>
                        </div>

						<h2 class="titolocard mt-9">Riepilogo configurazioni</h2>

                        <!-- SOL -->

                      
                        <form:hidden path="profiliSol"/>

                        <div id="solDefaultsContainer"></div>
                    </div>

                    <div id="solContainer"></div>

                    <div class="row mt-6">
                        <div class="col">
                            <a class="btn btn-outline-aggiungi-primary" role="button" id="addSolBtn">
                                Nuova Configurazione</a>
                        </div>
                    </div>

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
								<div class="alert alert-warning mt-7" role="alert"
									style="display: none !important;">
									<p>Attenzione!</p>
									<p>Con la richiesta credenziali RUPAR viene avviato il
										processo di richiesta credenziali.</p>
									<p>Sei sicuro di voler proseguire?</p>
									<div class="row prosegui mt-9">
										<div class="col-md-6 col-sm-12 indietro">
											<input class="btn btn-primary btn-outline-primary"
												type="submit" value="Annulla">
										</div>
										<div class="col-md-6 col-12 text-md-right mt-2 mt-md-0">
											<input class="btn btn-primary" type="submit"
												value="Invia richiesta">
										</div>
									</div>
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

                    <div class="row prosegui mt-9">
                        <div class="col-md-4 col-sm-12 indietro">
                            <a class="btn btn-primary btn-outline-primary" href="#" id="backResultBtn">Torna ai
                                risultati</a>
                        </div>
                        <div class="col-md-4 col-12 text-md-center mt-2 mt-md-0">
                            <a class="btn btn-primary btn-outline-primary"
                               href="<c:url value="/utenti" />" id="backHomeBtn">Torna alla home</a>
                        </div>
                        <div class="col-md-4 col-12 text-md-right mt-2 mt-md-0">
                            <a class="btn btn-primary" href="#" id="saveFormBtn">Salva</a>
                        </div>
                    </div>

                </form:form>
            </div>
            <!--fine app-row-->

        </div>
        <!--fine app-row-->

    </div>
</div>

<c:import url="utentePrototipi.jsp"/>

<c:import url="common/footer.jsp"/>

<script src="<c:url value="/js/userPage.js" />" lang="javascript"></script>

<script>
    $(() => userPage.initDataEditPage(${not fromConfiguratore or command.idAura ne null}));
</script>

<c:if test="${saved}">
	<c:choose>
		<c:when test="${empty mailaura && empty nomailaura}">
			<script>
        		$(() => userPage.showAlert(userPage.messages.confirmUserEditSaved.replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}').replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}'), null, 'info'))
    		</script>	
		</c:when>
		<c:otherwise>
			<script>
				$(() => userPage.showAlertAura(userPage.messages.confirmUserEditSaved.replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}').replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}'), '${mailaura}', '${nomailaura}'));
			</script>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${mail}">
	<c:choose>
		<c:when test="${empty mailaura && empty nomailaura}">
    		<script>
        		$(() => userPage.showAlert(userPage.messages.confirmUserEditSavedErrorMail.replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}').replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}'), null, 'info'))
    		</script>
    	</c:when>
    	<c:otherwise>
			<script>
				$(() => userPage.showAlertAura(userPage.messages.confirmUserEditSavedErrorMail.replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}').replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}'), '${mailaura}', '${nomailaura}'));
			</script>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${nomail}">
    <script>
        $(() => userPage.showAlert(userPage.messages.confirmUserEditSavedNoMail.replace('\${name}', '${command.nome}').replace('\${surname}', '${command.cognome}'), null, 'info'))
    </script>
</c:if>
