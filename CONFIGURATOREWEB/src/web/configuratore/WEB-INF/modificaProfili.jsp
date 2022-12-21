<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page
	import="it.csi.solconfig.configuratoreweb.presentation.model.InserisciProfiloModel"%>
<c:import url="common/top.jsp" />
<%@include file="common/controllofunzionalita.jsp"%>
<div id="main_content" class="app-view">
	<div class="app-container">
		<div class="app-row justify-content-md-center">
			<div class="col-12 col-lg-8">
				<div class="link mt-9">
					<a href="/configuratore/profili" class="link-prev"> Indietro</a>
				</div>
				<h1 class="titolocard mt-9">Modifica profilo</h1>
				<c:import url="common/alert.jsp" />

				<!-- inizio box dati utente-->

				<div class="card-box card-box-white card-box-notifiche mt-2">
					<div class="card-box-body">
						<div class="row">
							<div class="col-12">
								<h2 class="titolocard">Profilo</h2>
							</div>
						</div>
						<form:form action="/configuratore/modProfili" method="post"
							modelAttribute="profilo">
							<div class="row">
								<div class="col-12 col-md-6">
									<p class="p-titform">SELEZIONA SERVIZI ONLINE</p>
									<div class="form-group mb-2 input-group">
										<label for="servizioOnline" class="bmd-label-floating">SOL</label>
										<form:select class="form-control" id="servizioOnline"
											path="idApplicazione">
											<%-- <form:option value="">
																Seleziona...</form:option> --%>
											<c:forEach items="${sessionScope.data.applicazioneDtoList}"
												var="applicazione">
												<c:if test="${applicazione.id eq profilo.idApplicazione}">
													<form:option value="${applicazione.id}"
														name="${applicazione.id}">
														${applicazione.descrizione}
														</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12 col-md-6">
									<div class="form-group input-group date">
										<label for="sedeOperativa" class="bmd-label-floating">Codice</label>
										<input type="text" class="form-control" id="codice"
											name="codice" value="${profilo.codice}" path="codice"
											readonly />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12 col-md-6">
									<div class="form-group input-group date">
										<label for="sedeOperativa" class="bmd-label-floating">Descrizione</label>
										<input type="text" class="form-control" id="descrizione"
											name="descrizione" value="${profilo.descrizione}"
											path="descrizione" maxlength="150" />
									</div>
								</div>
							</div>

							<div class="row my-6">
								<div class="col-12">
									<div class="accordion">
										<div class="card accordion-box azioni-box">
											<div class="card-header" id="headingOne">
												<h3 class="mb-0">
													<button
														class="btn btn-link text-left collapsed accordion-function"
														data-toggle="collapse" data-target="#collapseOne"
														aria-expanded="false" aria-controls="collapseOne"
														onClick="return false;">Aggiungi funzionalita' /
														Azioni al profilo</button>
												</h3>
											</div>

											<div id="collapseOne" class="collapse"
												aria-labelledby="headingOne" data-parent="#accordion">
												<div class="card card-body">
													<div class="row mb-2">
														<div class="col-12">
															<div class="form-check">
																<c:if test="${albero.checkNull eq false}">
																	<input id="select-all" class="form-check-input"
																		type="checkbox" value="" id="defaultCheck1">
																	<label class="form-check-label" for="defaultCheck1">
																		Tutti </label>
																</c:if>
															</div>
														</div>
													</div>

													<c:import url="common/updateShowFunctionTree.jsp" />
													<input type="hidden" name="idFunzione"
														value="${profilo.idFunzione}">
												</div>
												<!--vecchio accordion-->

											</div>
										</div>
									<!-- RUOLI -->
                        <div class="card accordion-box roles-accordion">
                            <div class="card-header" id="headingOne">
                                <h3 class="mb-0">
                                    <button class="btn btn-link text-left collapsed" data-toggle="collapse"
                                            data-target="#collapseTwo" type="button"
                                            aria-expanded="false" aria-controls="collapseTwo">
                                        Ruolo
                                    </button>
                                </h3>
                            </div>

                            <div id="collapseTwo" class="collapse" aria-labelledby="headingOne"
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
									<!-- END RUOLI -->
										<div class="row">
											<div class="col-12">
												<div class="switch mt-2">
													<label> <c:set var="attivo" value=""
															scope="request" /> <c:if test="${profilo.active}">
															<c:set var="attivo" value="checked" scope="request" />
														</c:if> <input type="checkbox" name="Active" id="Active"
														${attivo}> Attivo
													</label>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<div class="row prosegui mt-9">
										<div class="col-md-6 col-12 indietro">
											<c:url var="updateProfilo" value="/updateProfilo">
												<c:param name="idFunzione" value="${profilo.idFunzione}" />
											</c:url>
											<a class="btn btn-info btn-primary" href="${updateProfilo}"
												value="annulla">Annulla</a>
										</div>
										<div class="col-md-6 col-12 text-md-right mt-2 mt-md-0">
											<input id="subButton" class="btn btn-primary" type="submit"
												value="Salva">
										</div>
									</div>
								</div>
							</div>
						</form:form>
					</div>

				</div>
			</div>
		</div>
		<!--fine app-row-->
	</div>
</div>
<script type="text/javascript">
document.getElementById('select-all').onclick = function() {
	  var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	  for (var checkbox of checkboxes) {
		  if(checkbox.id != "Active"){
	    	checkbox.checked = this.checked;
		  }
	  }
	}	
	
window.preventAccordion = function(e) {   
	  e.stopPropagation();
	}
</script>
<c:import url="common/footer.jsp" />

<c:import url="profilePrototipi.jsp"/>

<script src="<c:url value="/js/profilePage.js" />" lang="javascript"></script>

<script>
    $(() => profilePage.initDataEditProfilePage());
</script>

