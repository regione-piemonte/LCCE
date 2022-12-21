<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp" %>
<c:set var="PAGINAZIONE_20_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_20_ELEMENTI%>"/>
<c:set var="PAGINAZIONE_50_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_50_ELEMENTI%>"/>
<c:set var="PAGINAZIONE_100_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_100_ELEMENTI%>"/>

<div class="row">
	<div class="col-12" id="pageLinkBox">
		Seleziona il numero di elementi da visualizzare:
		<a class="page-link-view" href="#" data-elementi="${PAGINAZIONE_20_ELEMENTI}">20</a> |
		<a class="page-link-view" href="#" data-elementi="${PAGINAZIONE_50_ELEMENTI}">50</a> |
		<a class="page-link-view" href="#" data-elementi="${PAGINAZIONE_100_ELEMENTI}">100</a>
	</div>
</div>
<div class="row pagination mt-9">
	<div class="col-12" id="pageNavigation">
		<nav aria-label="Page navigation">
			<ul class="pagination justify-content-center">
				<c:forEach begin="1" end="${pagina.getPagineTotali()}" step="1" var="index">
					<c:choose>
						<c:when test="${pagina.getPagineTotali() > 25}">
							
							<c:if test="${index == 1}">
								<c:choose>
									<c:when test="${model.numeroPagina <= 1}">
										<li class="page-item disabled"><a class="page-link disabled" href="#">Precedente</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="#"
																 data-pagina="${model.numeroPagina-1}">Precedente</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:if>
							
							
							<c:choose>
								<c:when test="${index == model.numeroPagina}">
									<li class="page-item active">
										<a class="page-link" href="#" data-pagina="${index}">${index}</a>
									</li>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${index > 4 && index < pagina.getPagineTotali()-3}">
											<c:if test="${index == 5 || index == pagina.getPagineTotali()-4}">
												<li> <h3>&nbsp;...&nbsp;</h3> </li>
											</c:if>	
																							
										</c:when>
										<c:otherwise>
											<li class="page-item">
												<a class="page-link" href="#" data-pagina="${index}">${index}</a>
											</li>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							
							
							<c:if test="${index == pagina.getPagineTotali()}">
								<c:choose>
									<c:when test="${model.numeroPagina >= pagina.getPagineTotali()}">
										<li class="page-item disabled"><a class="page-link disabled" href="#">Successivo</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="#"
																 data-pagina="${model.numeroPagina+1}">Successivo</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:if>
							
						</c:when>
						<c:otherwise>
							<c:if test="${index == 1}">
								<c:choose>
									<c:when test="${model.numeroPagina <= 1}">
										<li class="page-item disabled"><a class="page-link disabled" href="#">Precedente</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="#"
																 data-pagina="${model.numeroPagina-1}">Precedente</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:choose>
								<c:when test="${index == model.numeroPagina}">
									<li class="page-item active"><a class="page-link" href="#"
																	data-pagina="${index}">${index}</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link" href="#"
															 data-pagina="${index}">${index}</a></li>
								</c:otherwise>
							</c:choose>
							<c:if test="${index == pagina.getPagineTotali()}">
								<c:choose>
									<c:when test="${model.numeroPagina >= pagina.getPagineTotali()}">
										<li class="page-item disabled"><a class="page-link disabled" href="#">Successivo</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="#"
																 data-pagina="${model.numeroPagina+1}">Successivo</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</nav>
	</div>
</div>
<c:if test="${pagina.getPagineTotali() > 25}">
	<div class="row">
		<div class="col-12 form-inline" style="justify-content: center">
			<div class="col-1">
				<input type="number" class="page-item" name="goToPage" min="1" max="${pagina.getPagineTotali()}"/>
			</div>
			<div class="col-2">
				<a id="go-to" href="#">Vai alla pagina</a>
			</div>
		</div>
	</div>
</c:if>
<div class="row">
	<span class="col-md-12 form-group bmd-form-group text-right"> <!-- needed to match padding for floating labels -->
		<button type="submit" class="btn btn-primary" id="btnInserimentoMassivo" disabled>Disabilita</button>
	</span>
</div>

