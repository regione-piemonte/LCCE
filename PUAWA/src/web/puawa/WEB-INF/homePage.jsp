<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="common/top.jsp" />

<div id="main_content" class="app-view app-view-home-fse">
	<div class="app-container">
		<c:if test="${errori!=null}">
			<div class="app-row justify-content-md-center">
				<div class="col-12 col-md-9">
					<div class="alert alert-warning" role="alert">
						<c:forEach items="${errori}" var="errore">
							<p>${errore.descrizione}</p>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
		<form>
			<div class="app-row justify-content-md-center">
				<div class="col-12 col-lg-9">
					<div class="box-icon-ssn">
						<h2 class="text-white h6 pl-3">Servizi abilitati</h2>
						<div class="row">
							<c:forEach items="${sessionScope.data.utente.listaApplicazioni}"
									   var="applicazione">
								<c:if test = "${applicazione.bottone.equals('S')}">
									<div class="col-12 col-md-4">
										<div class="box-icon-ssn-card">
											<div class="row">
												<div class="col-4 col-img">
													<img class=""
														 src="@prefissoRisorseStatiche@<c:out value='${applicazione.pathImmagine}'/>"
														 alt="" />
												</div>
												<div class="col-8">
													<a
															href="<c:url value='/redirect'>
																							<c:param name="codiceApplicazione" value="${applicazione.codice}" />
																						  </c:url>">
														<c:out value = "${applicazione.descrizione}"/> </a>
												</div>
											</div>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<div class="row">
							<c:forEach items="${sessionScope.data.utente.listaApplicazioni}"
								var="applicazione">
								<c:if
									test="${empty applicazione.bottone or applicazione.bottone.equals('N')}">
									<div class="col-12 report-link">
										<a
											href="<c:url value='/redirect'>
																<c:param name="codiceApplicazione" value="${applicazione.codice}" />
															  </c:url>">
											<c:out value = "${applicazione.descrizione}"/> <i class="fas fa-angle-right pl-1"> </i>
										</a>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<c:import url="common/footer.jsp" />