<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="common/top.jsp" />

<div id="main_content" class="app-view app-view-home pb-12">
	<div class="app-container">
		<c:if test="${errori != null}">
			<div id="alertMessage" name="alertMessage"
				class="app-row justify-content-md-center">
				<div class="col-12 col-md-9">
					<div class="alert alert-warning" role="alert">
						<c:forEach items="${errori}" var="errore">
							<!-- <p>Inserire un messaggio di alert nel caso in cui ci siano problemi</h6> -->
							<p>${errore.descrizione}
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
		<div class="app-row justify-content-md-center">
			<div class="col-12 col-md-9">
				<div class="card-box card-box-white">
					<div class="card-box-body">
						<form:form action="/puawa/comunicazioneCittadino"
							modelAttribute="notificaData" method="post">
							<h2 class="mb-6">Notifica Cittadino</h2>
							<div class="row">
								<div class="col-12 col-md-6 mb-0">
									<h6>Codice azienda</h6>
									<div class="form-group">
										<input id="codiceAziendaText"
											class="form-control col-12 col-md-10"
											value="${sessionScope.data.utente.viewCollocazione.colCodAzienda}"
											readonly="readonly" disabled="disabled" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12 col-md-6 mt-6 mb-0">
									<h6>Email Azienda</h6>
									<div class="form-group">
										<form:input path="emailAzienda" id="emailAziendaText"
											class="form-control col-12 col-md-10" />
									</div>
								</div>
								<div class="col-12 col-md-6 mt-6 mb-0">
									<h6>Numero di telefono azienda</h6>
									<div class="form-group">
										<form:input path="numeroTelefonoAzienda"
											id="numeroTelefonoAziendaText" type="text" min="1" step="1"
											maxlength="10" class="form-control col-12 col-md-10" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12 col-md-6 mt-6 mb-0">
									<h6>Codice fiscale Assistito</h6>
									<label>(max 16 caratteri - XXXXXX00X00X000X)</label>
									<div class="form-group">
										<form:input path="codiceFiscaleAssistito"
											id="codiceFiscaleAssistitoText" maxlength="16"
											class="form-control col-12 col-md-10" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12 col-md-12 mt-5 mb-0">
									<h4>Push</h4>
								</div>
								<div class="col-12 col-md-6 mt-2 mb-0">
									<h6>Titolo</h6>
									<label>(max 50 caratteri)</label>
									<div class="form-group">
										<form:textarea path="titoloPush" id="titoloPushText"
											name="titoloPushText" cols="17" rows="1" maxlength="50"
											class="form-control col-12 col-md-10" />
									</div>
								</div>
								<div class="col-12 col-md-6 mt-2 mb-0">
									<h6>Testo</h6>
									<label>(max 200 caratteri)</label>
									<div class="form-group">
										<form:textarea path="testoPush" id="testoPushText"
											name="testoPushText" cols="17" rows="1" maxlength="200"
											class="form-control col-12 col-md-10" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12 col-md-12 mt-5 mb-0">
									<h4>E-mail</h4>
								</div>
								<div class="col-12 col-md-6 mt-2 mb-0">
									<h6>Oggetto</h6>
									<label>(max 50 caratteri)</label>
									<div class="form-group">
										<form:textarea path="oggettoEmail" id="oggettoText" cols="17"
											rows="1" maxlength="50" class="form-control col-12 col-md-10" />
									</div>
								</div>
								<div class="col-12 col-md-6 mt-2 mb-0">
									<h6>Testo</h6>
									<label>(max 1000 caratteri)</label>
									<div class="form-group">
										<form:textarea path="testoEmail" id="testoEmailText" cols="17"
											rows="1" maxlength="1000"
											class="form-control col-12 col-md-10" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12 col-md-12 mt-5 mb-0">
									<h4 class="h4">Sito</h4>
								</div>
								<div class="col-12 col-md-6 mt-2 mb-0">
									<h6 class="mb-0">Titolo</h6>
									<div class="form-group">
										<input path="titoloSito" id="titoloSitoText"
											readonly="readonly"
											value="Messaggio dalla tua Azienda Sanitaria"
											disabled="disabled" class="form-control col-12 col-md-10" />
									</div>
								</div>
								<div class="col-12 col-md-6 mt-2 mb-0">
									<h6>Testo</h6>
									<label>(max 300 caratteri)</label>
									<div class="form-group">
										<form:textarea path="testoSito" id="testoSitoText"
											maxlength="300" cols="17" rows="1"
											class="form-control col-12 col-md-10" />
									</div>
								</div>
							</div>
							<div class="form-group mt-6 clearfix">
								<input id="conferma" name="conferma"
									class="btn btn-primary float-right" type="submit" role="button"
									value="Conferma" />
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<c:import url="common/footer.jsp" />