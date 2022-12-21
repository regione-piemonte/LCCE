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
						<li class="nav-item"><a class="nav-link"
												href="${root}/ruoli"> <span>Gestione ruoli</span>
						</a></li>
						<c:if test="${OPRicercaProfilo eq 'true'}">
							<li class="nav-item"><a class="nav-link"
													href="${root}/profili"> <span>Gestione profili</span>
							</a></li>
						</c:if>
						<c:if test="${OPRicercaApplicazione eq 'true'}">
							<li class="nav-item"><a class="nav-link active"
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
						<c:if test="${OPInserisciNuovoSOL eq 'true'}">
						<div class="card-box card-box-ruolo">
							<a href="${root}/inserimentoNuovaApplicazione">
                <span class="card-box-image">
                  <img class="" src="/ris/im/icone/diario.svg" alt="" /><!-- l'img cambierÃ  a seconda dell'elemento -->
                </span>
								<span class="card-box-text">
                 							 Inserisci nuovo SOL
               					 </span>
							</a>
						</div>
						</c:if>
						<!-- Fine box con immagine-->
					</div>
				</div>
				<h1 class="titolocard mt-9">Cerca SOL</h1>
				<c:import url="common/alert.jsp"/>
				
				<form:form id="searchForm" action="${root}/searchApplicazione" method="post" modelAttribute="applicazione">
				<div class="form-gray">
					<div class="form-row">
						<div class="col-5">
							<div class="form-group mx-sm-3 mb-2">
								<label for="codice" class="bmd-label-floating">Codice</label>
								<input type="text" class="form-control" id="codice" name= "codice"
										maxlength="150" value="${applicazione.codice}" path="codice"/>
							</div>
						</div>
						<div class="col-7">
							<div class="form-group mx-sm-3 mb-2">
								<label for="Descrizione" class="bmd-label-floating">Descrizione</label>
								<input type="text" class="form-control" id="Descrizione" name= "descrizione"
										maxlength="150" value="${applicazione.descrizione}" path="descrizione"/>
							</div>
						</div>
					</div>
				</div>
				


				<form:hidden path="numeroPagina"/>
				<form:hidden path="numeroElementi"/>

				<div class="row prosegui mt-9">
					<div class="col-md-6 offset-md-6 colcol-sm-12 conferma">
					
						<input class="btn btn-primary" type="submit" value="Cerca">
						<c:if test="${paginaSOL != null && paginaSOL.getElementiTotali() != 0}">
						<a class="btn btn-info btn-primary"
							href="${root}/applicazioni" value="annulla">Annulla</a>
					</c:if>
				</div>   
				</div> 
				</form:form>

				<c:if test="${paginaSOL != null && paginaSOL.getElementiTotali() != 0 && empty errori}">

				<div class="row info-pagination mt-9 justify-content-between">
					<div class="col-12" >
						<h2 class="title_prepagination episodi">${paginaSOL.getElementiTotali()}
								<c:choose>
									<c:when test="${paginaSOL.getElementiTotali() == 1}">
							 Applicazione trovata
							 </c:when>
									<c:otherwise>
							 Applicazioni trovate
							 </c:otherwise>
							 </c:choose>
							</h2>
						<p class="note">Seleziona una applicazione per modificarla</p>
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
									<th scope="col"><span class="headerTxt">Inserito da configuratore</span></th>
									<th scope="col"><span class="headerTxt">Azioni</span></th>

								</tr>
								</thead>
								<tbody>
									
									<c:forEach items="${paginaSOL.getElementi()}" var="risSOL" varStatus="loop">
											<tr>
											<td>${risSOL.codice}</td>
											<td>${risSOL.descrizioneWebapp}</td>

												
												
												
												<td> 
												<c:if test="${risSOL.flagConfiguratore == 'S'}">
												<span id="attivo_1" class="ico_attivo" title="si" href="#table_row1">
                        							<span class="sr-only">si </span><span>&nbsp;</span></span>
                       								</c:if>
                       								</td>
                       							 		
												<td><c:if test="${OPModificaApplicazionec eq 'true' && risSOL.flagConfiguratore=='S'}">
												<c:url var="updateApplicazione" value="/updateApplicazione">
    												<c:param name="id"  value="${risSOL.id}" />
														</c:url>
												<a id="modifica_1" class="ico_modifica" title="modifica"  href="${updateApplicazione}" ></a>			                 
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
					<c:set var="pagina" value="${paginaSOL}" scope="request" />
					<c:set var="model" value="${applicazione}" scope="request" />
					<c:import url="common/paginazione.jsp" />
				</c:if>
			</div><!--fine app-row-->
		</div><!--fine app-row-->




	</div><!--chiudo app container -->
</div>

<c:import url="common/footer.jsp" />


<script src="${pageContext.request.contextPath}/js/sortTable.js" lang="javascript"></script>
<script src="${pageContext.request.contextPath}/js/paginazione.js" lang="javascript"></script>