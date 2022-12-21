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
						<li class="nav-item"><a class="nav-link active"
												href="${root}/ruoli"> <span>Gestione ruoli</span>
						</a></li>
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
				<div class="app-row mt-6 justify-content-md-center">
					<div class="col-12">
						<!-- inizio box con immagine-->
						<c:if test="${OPInserisciNuovoRuolo eq 'true'}">
						<div class="card-box card-box-ruolo">
							<a href="${root}/inserimentoRuoli">
                <span class="card-box-image">
                  <img class="" src="/ris/im/icone/diario.svg" alt="" /><!-- l'img cambierÃ  a seconda dell'elemento -->
                </span>
								<span class="card-box-text">
                 							 Inserisci nuovo ruolo
               					 </span>
							</a>
						</div>
						</c:if>
						<!-- Fine box con immagine-->
					</div>
				</div>
				<h1 class="titolocard mt-9">Cerca ruolo</h1>
				<c:import url="common/alert.jsp"/>
				
				<form:form id="searchForm" action="${root}/searchRuoli" method="post" modelAttribute="ruolo">
				<div class="form-gray">
					<div class="form-row">
						<div class="col-5">
							<div class="form-group mb-2 bmd-form-group">
								<label for="Codice" class="bmd-label-floating">Codice</label>
								<input type="text" class="form-control" id="Codice" name= "codice" 
										maxlength="20" value="${ruolo.codice}" path="codice"/>
							</div>
						</div>
						<div class="col-5">
							<div class="form-group mx-sm-3 mb-2">
								<label for="Descrizione" class="bmd-label-floating">Descrizione</label>
								<input type="text" class="form-control" id="Descrizione" name= "descrizione"
										maxlength="150" value="${ruolo.descrizione}" path="descrizione"/>
							</div>
						</div>
					</div>
				</div>
				<div class="link mt-2">
					<a class="link-filter" data-toggle="collapse" 
					href="#collapseFilter" role="button" aria-expanded="true" 
					aria-controls="collapseFilter">Filtri </a>
					<div class="show" id="collapseFilter">
						<div class="card card-body">
							<div class="form-check">
								<input class="form-check-input" type="checkbox" value="true" 
								id="defaultCheck1" name="flagConfiguratore" path="flagConfiguratore">
									<c:if test="${ruolo.flagConfiguratore eq 'true'}">
											<script type="text/javascript">
											document.getElementById("defaultCheck1").checked = true;
											</script>
										</c:if>
								<label class="form-check-label" for="defaultCheck1">
									Inserito da configuratore
								</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox" value="true" 
								id="defaultCheck2" name="flagNonAttivo" path="flagNonAttivo" >
								<c:if test="${ruolo.flagNonAttivo eq 'true'}">
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
									<c:if test="${ruolo.flagAttivo eq 'true'}">
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
						<c:if test="${paginaRuoli != null && paginaRuoli.getElementiTotali() != 0}">
						<a class="btn btn-info btn-primary"
							href="${root}/annullaRicerca" value="annulla">Annulla</a>
					</c:if>
				</div>   
				</div> 
				</form:form>

				<c:if test="${paginaRuoli != null && paginaRuoli.getElementiTotali() != 0 && empty errori}">

				<div class="row info-pagination mt-9 justify-content-between">
					<div class="col-12" >
						<h2 class="title_prepagination episodi">${paginaRuoli.getElementiTotali()}
								<c:choose>
									<c:when test="${paginaRuoli.getElementiTotali() == 1}">
							 ruolo trovato
							 </c:when>
									<c:otherwise>
							 ruoli trovati
							 </c:otherwise>
							 </c:choose>
							</h2>
						<p class="note">Seleziona un ruolo per modificarlo</p>
					</div>
				</div>

				<div class="app-row justify-content-md-center">
					<div class="col-12">

						<div class="div-overflow">
							<table class="table table-hover table-sortable" id="myTable">
								<thead>
								<tr>
									<th scope="col">
										<div class="sortable"><span class="headerTxt">Codice</span><a id="sort1" class="header" onclick="sortTable(0, 'sort1')"><span class="sr-only">ordina i dati</span></a></div>
									</th>
									<th scope="col">
										<div class="sortable"><span class="headerTxt">Descrizione</span><a id="sort2" class="header" onclick="sortTable(1, 'sort2')"><span class="sr-only">ordine crescente</span></a></div>
									</th>
									<th scope="col">
										<div class="sortable"><span class="headerTxt">Stato</span><a id="sort3" class="header" onclick="sortTable(2, 'sort3')"><span class="sr-only">ordine decrescente</span></a></div>
									</th>
									<th scope="col"><span class="headerTxt">Inserito da configuratore</span></th>
									<th scope="col"><span class="headerTxt">Azioni</span></th>

								</tr>
								</thead>
								<tbody>
									
									<c:forEach items="${paginaRuoli.getElementi()}" var="risRuoli" varStatus="loop">
											<tr>
											<td>${risRuoli.codice}</td>
											<td>${risRuoli.descrizione}</td>
											<td>
											
										
												
													<c:choose>
												<c:when test="${sysdate >= risRuoli.dataInizioValidita 
															  && (sysdate <= risRuoli.dataFineValidita
															  || empty risRuoli.dataFineValidita)}">
												Attivo
												</c:when>
													<c:otherwise>
												NON Attivo
														</c:otherwise>
												</c:choose>
													
												
												
												</td>	
												<td> 
												<c:if test="${risRuoli.flagConfiguratore == 'S'}">
												<span id="attivo_1" class="ico_attivo" title="si" href="#table_row1">
                        							<span class="sr-only">si </span><span>&nbsp;</span></span>
                       								</c:if>
                       								</td>
                       							 		
												<td><c:if test="${OPModificaRuolo eq 'true' && risRuoli.flagConfiguratore=='S'}">
												<c:url var="updateRuolo" value="/updateRuolo">
    												<c:param name="id"  value="${risRuoli.id}" />
														</c:url>
												<a id="modifica_1" class="ico_modifica" title="modifica"  href="${updateRuolo}" ></a>			                 
													<span class="sr-only">Modifica</span><span>&nbsp;</span>																									
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
					<c:set var="pagina" value="${paginaRuoli}" scope="request" />
					<c:set var="model" value="${ruolo}" scope="request" />
					<c:import url="common/paginazione.jsp" />
				</c:if>
			</div><!--fine app-row-->
		</div><!--fine app-row-->




	</div><!--chiudo app container -->
</div>

<c:import url="common/footer.jsp" />


<script src="${pageContext.request.contextPath}/js/sortTable.js" lang="javascript"></script>
<script src="${pageContext.request.contextPath}/js/paginazione.js" lang="javascript"></script>