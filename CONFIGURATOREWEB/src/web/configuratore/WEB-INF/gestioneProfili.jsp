<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="root">${pageContext.request.contextPath}</c:set>

<c:import url="common/top.jsp" />
<%@include file="common/controllofunzionalita.jsp" %>
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
						<li class="nav-item"><a class="nav-link active"
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
				<div class="app-row mt-6 justify-content-md-center">
					<div class="col-12">
					<c:if test="${OPInserisciNuovoProfilo eq 'true'}">
							<!-- inizio box con immagine-->
							<div class="card-box card-box-ruolo">
								<a href="${root}/inserimentoProfili"> <span
									class="card-box-image"> <img class=""
										src="/ris/im/icone/diario.svg" alt="" /> <!-- l'img cambierÃ  a seconda dell'elemento -->
								</span> <span class="card-box-text"> Inserisci nuovo profilo </span>
								</a>
							</div>
							<!-- Fine box con immagine-->
						</c:if>
					</div>
				</div>



				<h1 class="titolocard mt-9">Cerca profilo</h1>
				<c:import url="common/alert.jsp" />
				<form:form id="searchForm" action="/configuratore/cercaprofili" method="post"
					modelAttribute="profilo">
					<div class="form-gray">
						<div class="form-row">
							<div class="col-5">
								<div class="form-group mb-2 bmd-form-group">
									<label for="Codice" class="bmd-label-floating">Codice</label>
									<form:input type="text" class="form-control" id="Codice"
										name="codice" value="${profilo.codice}" path="codice" />
								</div>
							</div>
							<div class="col-5">
								<div class="form-group mx-sm-3 mb-2">
									<label for="Descrizione" class="bmd-label-floating">Descrizione</label>
									<input type="text" class="form-control" id="Descrizione"
										name="descrizione" value="${profilo.descrizione}"
										path="descrizione" />
								</div>
							</div>
						</div>
						<div class="form-row">
							<div class="col-5">
								<div class="form-group mb-2 is-filled">
									<label for="exampleSelect1" class="bmd-label-floating">SOL</label>
									<form:select class="form-control" id="exampleSelect1"
										path="idApplicazione">
										<form:option value="">
													Seleziona...</form:option>
										<c:forEach items="${sessionScope.data.applicazioneDtoList}"
											var="applicazione">
											<form:option value="${applicazione.id}">
													${applicazione.descrizione}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>

						</div>


					</div>
					<div class="link mt-2">
						<a class="link-filter" data-toggle="collapse"
							href="#collapseFilter" role="button" aria-expanded="true"
							aria-controls="collapseFilter"> Filtri </a>
						<div class="show" id="collapseFilter">
							<div class="card card-body">
								<div class="form-check">

									<input class="form-check-input" type="checkbox" value="true"
										id="defaultCheck2" name="flagNonAttivo" path="flagNonAttivo">
										<c:if test="${profilo.flagNonAttivo eq 'true'}">
											<script type="text/javascript">
											document.getElementById("defaultCheck2").checked = true;
											</script>
										</c:if>

									<label class="form-check-label" for="defaultCheck2">
										Stato non attivo </label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value="true"
										id="defaultCheck3" name="flagAttivo" path="flagAttivo">
										
										<c:if test="${profilo.flagAttivo eq 'true'}">
											<script type="text/javascript">
											document.getElementById("defaultCheck3").checked = true;
											</script>
										</c:if>
										
									<label class="form-check-label" for="defaultCheck3">
										Stato attivo </label>
								</div>
							</div>
						</div>
					</div>

				<form:hidden path="numeroPagina"/>
				<form:hidden path="numeroElementi"/>

					<div class="row prosegui mt-9">
						<div class="col-md-6 offset-md-6 colcol-sm-12 conferma">

							<input class="btn btn-primary" type="submit" value="Cerca">
							<c:if test="${paginaProfili != null && paginaProfili.getElementiTotali() != 0}">
								<a class="btn btn-info btn-primary"
									href="${root}/profili" value="annulla">Annulla</a>
							</c:if>
						</div>
				</form:form>

				<c:if test="${paginaProfili != null && paginaProfili.getElementiTotali() != 0 && empty errori}">

					<div class="row info-pagination mt-9 justify-content-between">
						<div class="col-12">
							<h2 class="title_prepagination episodi">${paginaProfili.getElementiTotali()}
								<c:choose>
									<c:when test="${paginaProfili.getElementiTotali() == 1}">
							 profilo trovato
							 </c:when>
									<c:otherwise>
							 profili trovati
							 </c:otherwise>
								</c:choose>
							</h2>
							<p class="note">Seleziona un profilo per modificarlo</p>
						</div>

					</div>

					<div class="app-row justify-content-md-center">
						<div class="col-12">

							<div class="div-overflow">
								<table class="table table-hover table-sortable" id="myTable">
									<thead>
										<tr>
											<th scope="col">
												<div class="sortable">
													<span class="headerTxt">Codice</span><a id="sort1" class="header" onclick="sortTable(0, 'sort1')"><span class="sr-only">ordina i dati</span></a>
												</div>
											</th>
											<th scope="col">
												<div class="sortable">
													<span class="headerTxt">Descrizione</span><a id="sort2" class="header" onclick="sortTable(1, 'sort2')"><span
														class="sr-only">ordine crescente</span></a>
												</div>
											</th>
											<th scope="col">
												<div class="sortable">
													<span class="headerTxt">Stato</span><a id="sort3" class="header" onclick="sortTable(2, 'sort3')"><span
														class="sr-only">ordine decrescente</span></a>
												</div>
											</th>
											<th scope="col"><span class="headerTxt">SOL di
													riferimento</span></th>
											<th scope="col"><span class="headerTxt">Azioni</span></th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${paginaProfili.getElementi()}" var="risprofilo" varStatus="loop">
											<tr>
												<td>${risprofilo.codiceFunzione}</td>
												<td>${risprofilo.descrizioneFunzione}</td>
												<td>
												<c:choose>
												<c:when test="${stato != null}">
													<c:choose>
														<c:when test="${stato == true}">
														Attivo
														</c:when>
														<c:otherwise>
														NON Attivo
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${sysdate >= risprofilo.dataInizioValidita 
																			&& (sysdate <= risprofilo.dataFineValidita
																				|| empty risprofilo.dataFineValidita)}">
														Attivo
														</c:when>
														<c:otherwise>
														NON Attivo
														</c:otherwise>
													</c:choose>
												</c:otherwise>
												</c:choose>
													</td>
												<td>${risprofilo.applicazioneDto.descrizione}</td>
												<td><c:if
														test="${OPModificaProfilo eq 'true'}">

														<c:url var="updateProfilo" value="/updateProfilo">
    												<c:param name="idFunzione" value="${risprofilo.idFunzione}" />
														</c:url>
														
														<a id="modifica_1" class="ico_modifica" title="modifica"
															href="${updateProfilo}"> <span class="sr-only">Modifica
														</span><span>&nbsp;</span></a>
													</c:if></td>
											</tr>
										</c:forEach>
										<!-- fine toggle_target_3 -->
									</tbody>
								</table>
							</div>





							<!--/div-->
						</div>



					

					</div>
					<c:set var="pagina" value="${paginaProfili}" scope="request" />
					<c:set var="model" value="${profilo}" scope="request" />
					<c:import url="common/paginazione.jsp" />
				</c:if>
			</div>
			<!--fine app-row-->
		</div>
		<!--fine app-row-->
	</div>
</div>

<c:import url="common/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/sortTable.js" lang="javascript"></script>
<script src="${pageContext.request.contextPath}/js/paginazione.js" lang="javascript"></script>