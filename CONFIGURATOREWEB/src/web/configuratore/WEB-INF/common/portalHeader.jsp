<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- portal header -->
<div class="sp_header">
	<div id="header" class="header">
		<div id="sp-navbar" class="sp_navbar sp_views1">
			<div class="sp_container">
				<div class="sp_row">
					<div class="navbar-header">

						<a class="navbar-brand" href="<c:url value='/logout'/>"> <span
							class="d-brand-dsk">Punto Unico di Accesso</span> <span
							class="d-brand-mo"><abbr
								title="PUA">PUA</abbr></span>
						</a>
					</div>
					<!--fine navbar-header -->
					<div id="utilities">

                            <a class="user" href="#" role="button">
                                <!--i class="icon-help"></i-->
                                <img class="ico_svg" src="/ris/im/icone/ico_user.svg" alt="">
                                <span class="user"><c:out
										value="${sessionScope.data.utente.nome}" /> <c:out
										value="${sessionScope.data.utente.cognome}" /></span>
                            </a>

					</div>
					<!--fine menu utilities-->
				</div>
				<!-- fine utilities-->
			</div>
			<!-- fine sp_row-->
		</div>
		<div id="sp-navbar-2" class="sp_navbar sp_views2">
			<div class="sp_container">
				<div class="sp_row">
					<div class="navbar-header">

						<a class="navbar-brand" href="<c:url value='/utenti'/>">
							<span class="d-brand-dsk">Configuratore servizi sanitari digitali</span>

						</a>
					</div>
					<!--fine navbar-header -->
					<div class="utilities">


						<a class="help" href="<c:url value='/help'/>" role="button">
							<!--i class="icon-help"></i-->
							<img class="ico_svg" src="/ris/im/icone/ico_help.svg" alt="ti serve aiuto?">
							<span class="txt sr-only">Aiuto</span>
						</a>



						<!---/dropdown show-->

					</div>
				</div>
				<!-- fine utilities-->
			</div>
			<!-- fine sp_row-->
		</div>
	</div>
	<!--sp_navbar-->
	<!--- serve per l'offcanvas menu-->
	<div class="bsnav-mobile">
		<div class="bsnav-mobile-overlay"></div>

		<div class="navbar">
			<div class="navbar-header-menu"></div>
		</div>
	</div>
	<!--- FINE serve per l'offcanvas menu-->


</div>
<!-- end portal header -->