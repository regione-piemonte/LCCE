<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="root">${pageContext.request.contextPath}</c:set>

<c:import url="common/top.jsp" />
<%@include file="common/controllofunzionalita.jsp"%>


<div id="main_content" class="app-view">
	<div class="app-container">
		<div class="app-row justify-content-md-center">
			<div class="col-12 col-lg-12">
				<div class="menu-interno d-lg-block" id="menu-interno">
					<!-- finemenu dropdown responsive-->
					<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
						<c:if test="${OPRicercaUtente eq 'true'}">
							<li class="nav-item"><a class="nav-link"
								href="${root}/utenti"> <span>Gestione utenti</span>
							</a></li>
						</c:if>
						<c:if test="${OPRicercaRuolo eq 'true'}">
							<li class="nav-item"><a class="nav-link"
								href="${root}/ruoli"> <span>Gestione ruoli</span>
							</a></li>
						</c:if>
						<li class="nav-item"><a class="nav-link"
							href="${root}/profili"> <span>Gestione profili</span>
						</a></li>
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
							<li class="nav-item"><a class="nav-link active"
								href="${root}/rupar"> <span>Credenziali Rupar</span>
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

				<h1 class="titolocard mt-7">Cerca Credenziali Rupar</h1>
				<c:import url="common/alert.jsp"/>
				
				<form:form id="searchForm" action="/configuratore/ricercaRupar"
					method="post" modelAttribute="rupar">
					<form class="form-newruoli">
						<div class="card-box card-box-white card-box-grigio mt-2">
							<div class="card-box-body row">
								<div class="col-12">

									<div class="form-row">
										<div class="col-12 col-md-5">
											<div class="form-group form-group-gestutenti">
												<label for="utente" class="bmd-label-floating">Codice
													fiscale utente</label> <input type="text" class="form-control"
													id="Utente" name="cfUtente" value="${rupar.cfUtente}"
													path="cfUtente">

											</div>
										</div>
										<div class="col-12 col-md-5">
											<div class="form-group bmd-form-group">
												<label for="Operatore" class="bmd-label-floating">Codice
													fiscale richiedente</label> <input type="text" class="form-control"
													id="Operatore" name="cfOperatore"
													value="${rupar.cfOperatore}" path="cfOperatore">

											</div>
										</div>
									</div>
									<div class="form-row">
										<div class="col-12 col-md-5">
											<div class="form-group form-group">
												<label for="ticketRemedy" class="bmd-label-floating">Ticket Remedy</label> <input type="text" class="form-control"
													id="ticketRemedy" name="ticketRemedy" value="${rupar.ticketRemedy}"
													path="ticketRemedy">

											</div>
										</div>
									</div>
									<div class="form-row mt-7">
										<div class="col-12 col-md-6">

											<p class="p-titform">DATA RICHIESTA</p>
											<div class="row">
												<div class="col-12 col-sm-6">
													<div class="form-group input-group date"
														>
														<label for="Dal" class="bmd-label-floating">Dal</label> <input
															type="text" id="datepickerrupar" class="form-control" id="Dal"
															name="dataRichiestaDa" value="${rupar.dataRichiestaDa}"
															path="dataRichiestaDa">

														<div class="input-group-append">
															<button type="button" class="btn-calendar"></button>
														</div>
													</div>
												</div>
												<div class="col-12 col-sm-6">
													<div class="form-group input-group date"
														data-provide="datepicker">
														<label for="Al" class="bmd-label-floating">Al</label> <input
															type="text" id="datepickerrupar1" class="form-control" id="Al"
															name="dataRichiestaA" value="${rupar.dataRichiestaA}"
															path="dataRichiestaA" />

														<div class="input-group-append">
															<button type="button" class="btn-calendar"></button>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<form:hidden path="numeroPagina"/>
									<form:hidden path="numeroElementi"/>

									<div class="row prosegui mt-9">
										<div class="col-md-6 col-sm-12 indietro">
											<c:if test="${paginaRupar != null && paginaRupar.getElementiTotali() != 0 or not empty errori}">
												<a class="btn btn-info btn-primary"
													href="${root}/annullaRicercaRichieste" value="annulla">Annulla la ricerca</a>
											</c:if>
										</div>
										<div class="col-md-6 col-sm-12 text-md-right mt-2 mt-md-0">
											<input class="btn btn-primary" type="submit" value="Cerca">
										</div>
									</div>
									<div class="row prosegui mt-9">
									<div class="col-md-6 col-sm-12 text-md-left mt-2 mt-md-0">
										<a class="btn btn-info btn-primary"
										href="${root}/downloadFaq" value="annulla">FAQ per richiesta credenziali RUPAR</a>
										</div>
									</div>
								</div>
							</div>

						</div>
					</form>
				</form:form>

				<c:if test="${paginaRupar != null && paginaRupar.getElementiTotali() != 0 && empty errori}">
					<div class="row info-pagination mt-9 justify-content-between">
						<div class="col-12">
							<h1 class="title_prepagination episodi">${paginaRupar.getElementiTotali()}
								<c:choose>
									<c:when test="${paginaRupar.getElementiTotali() == 1}">
							 richiesta trovata
							 </c:when>
									<c:otherwise>
							 richieste trovate
							 </c:otherwise>
								</c:choose>
							</h1>
						</div>
					</div>
			</div>
			<div class="app-row justify-content-md-center mt-9">
				<div class="col-12">

					<div class="div-overflow">
						<table class="table table-hover table-sortable" id="myTable">
							<thead>
								<tr>
									<th scope="col">
										<div class="sortable">
											<span class="headerTxt"><abbr title="Codice fiscale">C.F.</abbr>
												Utente<a id="sort1" class="header" onclick="sortTable(0, 'sort1')"><span class="sr-only">ordina
														i dati</span></a>
										</div>
									</th>
									<th scope="col">
										<div class="sortable">
											<span class="headerTxt">Cognome utente<a id="sort2" class="header" onclick="sortTable(1, 'sort2')"><span class="sr-only">ordina
														i dati</span></a>
										</div>
									</th>
									<th scope="col">
										<div class="sortable">
											<span class="headerTxt">Data richiesta</span><a id="sort3" class="header" onclick="sortTable(2, 'sort3')"><span
												class="sr-only">ordine crescente</span></a>
										</div>
									</th>
									<th scope="col">
										<div class="sortable">
											<span class="headerTxt"><abbr title="Codice fiscale">C.F.</abbr>
												Operatore</span><a id="sort4" class="header" onclick="sortTable(3, 'sort4')"><span
												class="sr-only">ordine crescente</span></a>
										</div>
									</th>
									<th scope="col">
										<div class="sortable">
											<span class="headerTxt">Operatore richiedente</span><a id="sort5" class="header" onclick="sortTable(4, 'sort5')"><span
												class="sr-only">ordine decrescente</span></a>
										</div>
									</th>
									<th scope="col">
										<div class="sortable">
											<span class="headerTxt">Numero Ticket Remedy</span><a id="sort6" class="header" onclick="sortTable(5, 'sort6')"><span
												class="sr-only">ordine decrescente</span></a>
										</div>
									</th>


								</tr>
							</thead>
							<tbody>

								<c:forEach items="${paginaRupar.getElementi()}"
									var="richiestaRupar" varStatus="loop">
									<tr>
										<td>${richiestaRupar.utenteDto.codiceFiscale}</td>

										<td>${richiestaRupar.utenteDto.cognome} ${richiestaRupar.utenteDto.nome}</td>

										<td>${richiestaRupar.dataRichiesta}</td>

										<c:choose>
											<c:when test="${richiestaRupar.operatoreDto != null}">
												<td>${richiestaRupar.operatoreDto.codiceFiscale}</td>

												<td>${richiestaRupar.operatoreDto.cognome} ${richiestaRupar.operatoreDto.nome}</td>
											</c:when>
											<c:otherwise>
												<td class="cell-disable"></td>

												<td class="cell-disable"></td>
											</c:otherwise>
										</c:choose>

										<td>${richiestaRupar.ticketRemedy}</td>

									</tr>
								</c:forEach>


								<!-- fine toggle_target_3 -->
							</tbody>
						</table>
					</div>
					<!--/div-->
				</div>

			</div>
			<c:set var="pagina" value="${paginaRupar}" scope="request" />
			<c:set var="model" value="${rupar}" scope="request" />
			<c:import url="common/paginazione.jsp" />
			</c:if>
		</div>
		<!--fine app-row-->
	</div>
	<!--fine app-row-->
</div>
<!--chiudo app container -->
</div>

<c:import url="common/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/sortTable.js" lang="javascript"></script>
<script src="${pageContext.request.contextPath}/js/paginazione.js" lang="javascript"></script>
<script>
	$(function() {
		// set calendario
		$.datepicker.regional['it'] = {
			closeText: 'Chiudi',
			currentText: 'Oggi',
			monthNames: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'],
			monthNamesShort: ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Giu', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'],
			dayNames: ['Domenica', 'Luned&#236', 'Marted&#236', 'Mercoled&#236', 'Gioved&#236', 'Venerd&#236', 'Sabato'],
			dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'],
			dayNamesMin: ['Do', 'Lu', 'Ma', 'Me', 'Gio', 'Ve', 'Sa'],
			dateFormat: 'dd/mm/yy',
			firstDay: 1
		};

		$.datepicker.setDefaults($.datepicker.regional['it']);

		$("#datepickerrupar").datepicker();
		$("#datepickerrupar").siblings('div').find('button.btn-calendar').click(() => {
			$("#datepickerrupar").focus();
		});
		$("#datepickerrupar1").datepicker();
		$("#datepickerrupar1").siblings('div').find('button.btn-calendar').click(() => {
			$("#datepickerrupar1").focus();
		});
	});
</script>
