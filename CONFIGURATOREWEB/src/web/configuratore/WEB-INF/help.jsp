<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="common/top.jsp" />
<div id="main_content" class="app-view">
	<div class="app-container">
		<div class="app-row justify-content-md-center">
			<div class="col-12">

				<h1 class="titolocard mt-9">Serve aiuto?</h1>
				<div class="app-row mt-6">
					<div class="col-4">
						<!-- inizio box con immagine-->
						<div class="card-box card-box-help">
							<div class="card-box-body">
								<p class="card-box-image">
									<img class="" src="/ris/im/icone/faq.svg" alt="" /><!-- l'img cambierà a seconda dell'elemento -->
								</p>
								<p class="card-box-text">
								<a id="manuale" target="_blank" href="${manuale}">
									<strong>Leggi il manuale</strong>
								</a>
								</p>
								<p>Leggi le istruzioni all'uso del servizio</p>
							</div>
							<!-- Fine box con immagine-->
						</div>
					</div>

					<!--terzo box-->
					<div class="col-4">

						<!-- inizio box con immagine-->
						<div class="card-box card-box-help">
							<div class="card-box-body">
								<p class="card-box-image">
									<img class="" src="/ris/im/icone/scrivi.svg" alt="" />
									<!-- l'img cambierà a seconda dell'elemento -->
								</p>
								<p class="card-box-text">
									<strong>Scrivi</strong>
								</p>
								</a>
								<p>Per inviare richieste di assistenza � necessario compilare il seguente <strong><a href="https://assistenzapua.sistemapiemonte.it/#/">form</a></strong></p>
								<p class="p-bottom"> </p>
							</div>
							<!-- Fine box con immagine-->
						</div>
					</div>
				</div>


			</div><!--fine app-row-->
		</div><!--fine app-row-->




	</div><!--chiudo app container -->
</div>
<c:import url="common/footer.jsp" />