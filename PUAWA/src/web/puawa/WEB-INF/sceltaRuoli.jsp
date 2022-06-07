<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:import url="common/top.jsp" />

<div id="main_content" class="app-view app-view-home">
	<div class="app-container">
		<c:choose>
			<c:when test="${errore != null}">
				<div class="app-row justify-content-md-center">
					<div class="col-12 col-md-9">
						<div class="alert alert-warning" role="alert">
							<!-- <p>Inserire un messaggio di alert nel caso in cui ci siano problemi</p> -->
							<p>${errore.descrizione}</p>
						</div>
					</div>
				</div>
			</c:when>
		</c:choose>
		<c:if
			test="${sessionScope.data.utente.listaRuoli != null && fn:length(sessionScope.data.utente.listaRuoli) > 0 }">
			<div class="app-row justify-content-md-center">
				<div class="col-12 col-md-9">
					<h1>Ruoli</h1>
					<div class="row">
						<c:forEach items="${sessionScope.data.utente.listaRuoli}"
							var="ruolo">
							<div class="col-sm-4 col-lg-3">
								<a
									href="<c:url value='/sceltaCollocazioni'>
                    						<c:param name="codiceRuoloSelezionato" value="${ruolo.codice}" />
                    						<c:param name="descrizioneRuoloSelezionata" value="${ruolo.descrizione}" />
                    					 </c:url>">
									<c:choose>
										<c:when
											test='${ruolo.codice.equals(sessionScope.data.codiceRuoloSelezionato)}'>
											<div class="card-box card-box-ruolo active">
										</c:when>
										<c:otherwise>
											<div class="card-box card-box-ruolo">
										</c:otherwise>
									</c:choose> <!-- inizio box con immagine--> <span id="idRuolo"
									class="card-box-image"> <c:if
											test="${ruolo.codice.equals('MMG')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_generale.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('FAR')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_farmacista.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('MEDOSP')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_dirigente_sanitario.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('MEDRSA')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_rsa.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('PLS')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_pediatra.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('MEDRP')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_patologia.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('OPI')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_operatore_info.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('OAM')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_generica.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('DAM')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_generica.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('DRS')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_dirigente_sanitario.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('MRP')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_patologia.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('RSA')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_rsa.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('INF')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_personale_infermieristico.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('AAS')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_personale_specializzazione.svg"
												alt="" />
										</c:if> <c:if test="${ruolo.codice.equals('PSS')}">
											<img class=""
												src="@prefissoRisorseStatiche@im/icone/ruoli/ico_professionista_sociale.svg"
												alt="" />
										</c:if>
								</span> <span class="card-box-text"> ${ruolo.descrizione} </span>
							</div>
							</a>
					</div>
					</c:forEach>
				</div>
			</div>
	</div>
	<c:if test='${sessionScope.data.codiceRuoloSelezionato != null}'>
		<c:import url="sceltaCollocazioni.jsp" />
	</c:if>
</div>
</c:if>
</div>
<c:import url="common/footer.jsp" />
