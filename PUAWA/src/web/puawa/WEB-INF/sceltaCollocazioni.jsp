<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
	function setChecked(radioId, labelId) {
		if (radioId.checked) {
			labelId.click();
		}
	}
</script>

<div class="app-row justify-content-md-center">
	<c:if
		test="${sessionScope.data.utente.viewListaCollocazioni != null && fn:length(sessionScope.data.utente.viewListaCollocazioni) > 0 }">
		<div class="col-12 col-md-9">
			<h1>Collocazione</h1>
			<form>
				<div class="card-box card-box-white">
					<div class="card-box-body">
						<c:forEach
							items="${sessionScope.data.utente.viewListaCollocazioni}"
							var="collocazione">
							<div class="col-12 col-md-12">
								<a id="${collocazione.labelId}"
									href="<c:url value='/handleCollocazioni'>
                    						<c:param name="codiceCollocazioneSelezionato" value="${collocazione.colCodice}" />
                    						<c:param name="codiceColAziendaSelezionata" value="${collocazione.colCodAzienda}" />
                    					 </c:url>">
									<c:choose>
										<c:when
											test="${sessionScope.data.colCodiceCollocazioneSelezionata.equals(collocazione.colCodice) &&
												sessionScope.data.codiceColAziendaSelezionata.equals(collocazione.colCodAzienda)}">
											<label class="radio-inline"> <input type="radio"
												name="aslRadios" id="${collocazione.radioId}"
												onclick="setChecked(${collocazione.radioId}, ${collocazione.labelId});"
												checked> ${collocazione.colCodAzienda} -
												${collocazione.viewColCodice} -
												${collocazione.viewColDescrizione} </input>
											</label>
										</c:when>
										<c:otherwise>
											<label class="radio-inline"> <input type="radio"
												name="aslRadios" id="${collocazione.radioId}"
												onclick="setChecked(${collocazione.radioId}, ${collocazione.labelId});">
												${collocazione.colCodAzienda} -
												${collocazione.viewColCodice} -
												${collocazione.viewColDescrizione} </input>
											</label>
										</c:otherwise>
									</c:choose>
								</a>
							</div>
						</c:forEach>
						<c:choose>
							<c:when
								test="${sessionScope.data.colCodiceCollocazioneSelezionata != null}">
								<div class="button-row row-center">
									<a class="btn btn-primary" href="<c:url value='/continue'/>"
										role="button"> Prosegui </a>
								</div>
							</c:when>
						</c:choose>
					</div>
				</div>
			</form>
		</div>
	</c:if>
</div>