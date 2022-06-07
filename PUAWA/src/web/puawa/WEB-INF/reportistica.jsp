<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
	function do_change() {
		var formControlCollocazione = document
				.getElementById("FormControlCollocazione");
		var formControlTipiReport = document
				.getElementById("FormControlTipiReport");

		if (formControlCollocazione.options[formControlCollocazione.selectedIndex].text != "Seleziona"
				&& formControlTipiReport.options[formControlTipiReport.selectedIndex].text != "Seleziona"
				&& document.getElementById("data-da").value != "da"
				&& document.getElementById("data-da").value != ""
				&& document.getElementById("data-a").value != "a"
				&& document.getElementById("data-a").value != ""
				&& document.getElementById("data-da").value <= document
						.getElementById("data-a").value) {
			document.getElementById("generaReport").hidden = "";
		} else {
			document.getElementById("generaReport").hidden = "hidden";
		}

		if (document.getElementById("data-da").value > document
				.getElementById("data-a").value
				&& document.getElementById("data-da").value != "da"
				&& document.getElementById("data-da").value != ""
				&& document.getElementById("data-a").value != "a"
				&& document.getElementById("data-a").value != "") {
			document.getElementById("alertMessage").hidden = "";
		} else {
			document.getElementById("alertMessage").hidden = "hidden";
		}
	}
</script>

<c:import url="common/top.jsp" />

<div id="main_content" class="app-view app-view-home pb-12">
	<div class="app-container">
		<div id="alertMessage" name="alertMessage"
			class="app-row justify-content-md-center" hidden="hidden">
			<div class="col-12 col-md-9">
				<div class="alert alert-warning" role="alert">
					<!-- <p>Inserire un messaggio di alert nel caso in cui ci siano problemi</p> -->
					<p>La data finale dell'intervallo deve essere maggiore o uguale
						alla data iniziale</p>
				</div>
			</div>
		</div>
		<c:if test="${errori != null}">
			<div id="alertMessage" name="alertMessage"
				class="app-row justify-content-md-center">
				<div class="col-12 col-md-9">
					<div class="alert alert-warning" role="alert">
						<c:forEach items="${errori}" var="errore">
							<!-- <p>Inserire un messaggio di alert nel caso in cui ci siano problemi</p> -->
							<p>${errore.descrizione}</p>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
		<div class="app-row justify-content-md-center">
			<div class="col-12 col-md-9">
				<div class="card-box card-box-white">
					<div class="card-box-body">
						<form:form action="/puawa/genera"
							modelAttribute="reportisticaData" method="post"
							onsubmit="handleReport()">
							<h2 class="h5 mb-6">Reportistica</h2>
							<div class="row">
								<div class="col-12 col-md-6">
									<h3 class="h6">Collocazione</h3>
									<div class="form-group">
										<form:select id="FormControlCollocazione"
											path="codiceCollocazione"
											class="form-control col-12 col-md-10" onchange="do_change();">
											<form:option value="" selected="">Seleziona</form:option>
											<c:forEach
												items="${sessionScope.data.utente.viewListaCollocazioni}"
												var="collocazione">
												<form:option value="${collocazione.colCodice}">
													${collocazione.viewColCodice}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-12 col-md-6">
									<h3 class="h6">Data</h3>
									<div class="row">
										<div class="col-6">
											<label>Da</label>
											<div class="form-group">

												<div class="input-group date" data-provide="datepicker">
													<form:input title="da" type="date" class="form-control"
														id="data-da" path="dataDa" form:field="*{dataDa}"
														onchange="do_change();" />
												</div>
											</div>
										</div>
										<div class="col-6">
											<label>A</label>
											<div class="form-group">
												<div class="input-group date" data-provide="datepicker">
													<form:input type="date" class="form-control" id="data-a"
														path="dataA" form:field="*{dataA}" title="a"
														onchange="do_change();" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12 col-md-6">
									<h3 class="h6">Tipi di report</h3>
									<div class="form-group">
										<form:select name="report" path="codiceOptionReport"
											onchange="do_change();" class="form-control col-12 col-md-10"
											id="FormControlTipiReport" form:field="*{codiceOptionReport}">
											<form:option value="" selected="">Seleziona</form:option>
											<c:forEach items="${sessionScope.data.reportTipoDtoList}"
												var="report">
												<form:option value="${report.repTipoCodice}">
													${report.repTipoDescrizione}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<input id="generaReport" name="generaReport" hidden="hidden"
									class="btn btn-primary" type="submit" role="button"
									value="Genera Report" />
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>

<c:import url="common/footer.jsp" />
