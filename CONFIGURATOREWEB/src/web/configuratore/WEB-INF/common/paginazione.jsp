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
								</c:forEach>
							</ul>
						</nav>
					</div>
				</div>

