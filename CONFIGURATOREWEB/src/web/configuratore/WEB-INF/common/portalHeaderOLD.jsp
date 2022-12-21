<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- portal header -->
<div class="sp_header">
	<div id="header" class="header">
		<div id="sp-navbar" class="sp_navbar sp_views1">
			<div class="sp_container">
				<div class="sp_row">
					<div class="navbar-header">

						<a class="navbar-brand-portal" href="<c:url value='change'/>">
							<span class="sr-only"><abbr title="SistemaPiemonte">SP</abbr></span>
						</a> <a class="navbar-brand" href="<c:url value='change'/>"> <span
							class="d-brand-dsk">Punto Unico di Accesso</span> <span
							class="d-brand-mo"><abbr
								title="PUA">PUA</abbr></span>
						</a>
					</div>
					<!--fine navbar-header -->
					<div id="utilities">

						<%--<a class="contact" href="#" role="button" data-toggle="modal"
							data-target="#modalContatti"> <!--i class="icon-contact"></i-->
							<img class="ico_svg"
							src="@prefissoRisorseStatiche@im/icone/sp/ico_contatti.svg"
							alt="" /> <span class="txt">Contatti</span>
						</a> <a class="help" href="#" role="button"> <!--i class="icon-help"></i-->
							<img class="ico_svg"
							src="@prefissoRisorseStatiche@im/icone/sp/ico_help.svg" alt="" />
							<span class="txt">Aiuto</span>
						</a>--%>

                            <a class="user" href="#" role="button">
                                <!--i class="icon-help"></i-->
                                <img class="ico_svg" src="im/icone/ico_user.svg" alt="">
                                <span class="user"><c:out
										value="${sessionScope.data.utente.nome}" /> <c:out
										value="${sessionScope.data.utente.cognome}" /></span>
                            </a>

						<%--<div class="dropdown user-dropdown show">

							<a class="user dropdown-toggle" href="#" role="button"
								id="dropdownMenuLink" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false"> <!--i class="icon-user"></i-->
								<img class="ico_svg"
								src="@prefissoRisorseStatiche@im/icone/sp/ico_user.svg" alt="" />
								<span class="user"><c:out
										value="${sessionScope.data.utente.nome}" /> <c:out
										value="${sessionScope.data.utente.cognome}" /></span>
							</a>
							<div class="dropdown-menu dropdown-menu-right"
								aria-labelledby="dropdownMenuLink">

								<span class="titolo-dropdown user"><c:out
										value="${sessionScope.data.utente.nome}" /> <c:out
										value="${sessionScope.data.utente.cognome}" /></span>
								<div class="user-line dropdown-divider"></div>

								<div class="menuUtente">
									<div class="row">
										<div class="col">
											<div class="content-dropdown">
												<div class="dropdown-item dropdown-items-item">
													<div class="dropdown-item-sx">
														<h4 class="text-uppercase h6">${sessionScope.data.utente.collocazione.colDescAzienda}</h4>
														<!--nome asl-->
														<c:if
															test="${sessionScope.data.codiceRuoloSelezionato != null}">
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('MMG')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_generale.svg"
																	alt="">
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('FAR')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_farmacista.svg"
																	alt="">
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('MEDOSP')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_dirigente_sanitario.svg"
																	alt="">
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('MEDRSA')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_rsa.svg"
																	alt="">
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('PLS')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_pediatra.svg"
																	alt="">
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('MEDRP')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_patologia.svg"
																	alt="">
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('OPI')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_operatore_info.svg"
																	alt="" />
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('OAM')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_generica.svg"
																	alt="" />
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('DAM')}">
																<img class=""
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_generica.svg"
																	alt="" />
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('DRS')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_dirigente_sanitario.svg"
																	alt="" />
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('MRP')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_patologia.svg"
																	alt="" />
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('RSA')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_medico_rsa.svg"
																	alt="" />
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('INF')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_personale_infermieristico.svg"
																	alt="" />
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('AAS')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_personale_specializzazione.svg"
																	alt="" />
															</c:if>
															<c:if
																test="${sessionScope.data.codiceRuoloSelezionato.equals('PSS')}">
																<img class="ico_svg"
																	src="@prefissoRisorseStatiche@im/icone/ruoli/ico_professionista_sociale.svg"
																	alt="" />
															</c:if>
															<c:out
																value="${sessionScope.data.utente.ruolo.descrizione}" />
														</c:if>
													</div>
													&lt;%&ndash; Per ora non richiesto &ndash;%&gt;
													&lt;%&ndash;<div class="dropdown-item-dx">
														<a href="<c:url value='/change'/>">cambia ruolo</a>
													</div>&ndash;%&gt;
												</div>
												<div class="dropdown-divider"></div>
												<div class="dropdown-item">
													<p>${sessionScope.data.utente.collocazione.viewNome}</p>
												</div>
												<div class="dropdown-item">
													<p>${sessionScope.data.utente.collocazione.viewIndirizzo}</p>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="dropdown-divider"></div>
								<div class="footer-dropdown">
									<a class="dropdown-item" href="<c:url value='/logout'/>"> <img
										class="ico_svg"
										src="@prefissoRisorseStatiche@im/icone/sp/ico_esci.svg" alt="" />
										esci
									</a>
								</div>

							</div>
							<!---/dropdown-menu-->

						</div>--%>
						<!---/dropdown show-->

					</div>
					<!--fine menu utilities-->
					<button class="navbar-toggler toggler-elastic">
						<span class="navbar-toggler-icon"></span>
					</button>
				</div>
				<!-- fine utilities-->
			</div>
			<!-- fine sp_row-->
		</div>
		<div id="sp-navbar-2" class="sp_navbar sp_views2">
			<div class="sp_container">
				<div class="sp_row">
					<div class="navbar-header">

						<a class="navbar-brand" href="#">
							<span class="d-brand-dsk">Configuratore servizi sanitari digitali</span>

						</a>
					</div>
					<!--fine navbar-header -->
					<div class="utilities">


						<a class="help" href="<c:url value='/help'/>" role="button">
							<!--i class="icon-help"></i-->
							<img class="ico_svg" src="im/icone/ico_help.svg" alt="ti serve aiuto?">
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