<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="common/top.jsp" />

<div id="main_content" class="app-view app-view-home pb-12">
	<div class="app-container">
		<div class="app-row justify-content-md-center">
			<div class="col-12 col-md-9">
				<div class="alert alert-warning" role="alert">
					<p>ATTENZIONE
						La tua sessione di lavoro e' scaduta. Probabilmente l'applicativo e' rimasto inattivo per 1 ora.

						Verrai ridiretto alla homepage dell'applicativo senza necessita' di compiere una nuova autenticazione.
						I dati della precedente sessione di lavoro verranno persi.
						Puo' succedere che ti venga richiesto di scegliere il ruolo (se l'utente e' multiruolo).</p>
				</div>
				<h3>
					<a href="@urlPua@" title="Sessione Scaduta">Continua</a>
				</h3>
			</div>
		</div>
	</div>
</div>

<c:import url="common/footer.jsp" />