<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="it.csi.solconfig.configuratoreweb.presentation.model.Nodo"%>
<!DOCTYPE html>
<c:choose>
	<c:when test="${albero.hasChildren}">




		<div class="row">

			<div class="col-12">

				<div class="toggle-content">



					<a
						id="row<c:out value='${albero.parent.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}'/>"
						class="toggle_handle toggle_plus"
						title="attiva/disattiva i dettagli"
						href="#row<c:out value='${albero.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}'/>"
						data-toggle="collapse" role="button" aria-expanded="false"
						aria-controls="row<c:out value='${albero.parent.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}'/>">
						${albero.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}-${albero.treeFunzionalitaDto.funzionalitaDto.descrizioneFunzione} <span
						class="sr-only">attiva/disattiva i dettagli </span><span>&nbsp;</span>
					</a>


					<div class="toggle_target_001 collapse toggle_div hide"
						id="row<c:out value='${albero.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}'/>"
						style="">




						<c:forEach var="node" items="${albero.figli}">

							<c:choose>
								<c:when test="${node.hasChildren}">





									<c:set var="albero" value="${node}" scope="request" />
									<jsp:include page="updateShowFunctionTree.jsp" />


								</c:when>
								<c:otherwise>
									
									<c:set var="checked" value="" scope="request"/>
									<c:forEach var="checkAttivo" items="${checkAttivi}">
										<c:if test="${checkAttivo eq node.treeFunzionalitaDto.funzionalitaDto.idFunzione}">
											<c:set var="checked" value="checked" scope="request"/>
										</c:if>
									</c:forEach>
							<div class="col-12 col-sm-6 col-md-6">
									<div class="form-check">
										<input class="form-check-input" type="checkbox"
											name="listaIdFunzioniSelezionata" value="${node.treeFunzionalitaDto.idTreeFunzione}"
											id="row<c:out value='${node.parent.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}'/>"
											${checked}/>
										<label class="form-check-label"
											for="row<c:out value='${node.parent.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}'/>">
											${node.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}-${node.treeFunzionalitaDto.funzionalitaDto.descrizioneFunzione} </label>
									</div>
							</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>



					</div>

				</div>
			</div>
		</div>


	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-12 col-sm-6 col-md-6">
			
			<c:set var="checked" value="" scope="request"/>
			<c:set var="nodeChecked" value="${node.treeFunzionalitaDto.idTreeFunzione}" scope="request"/>
									
				<c:forEach var="checkAttivo" items="${checkAttivi}">
					<c:if test="${checkAttivo eq node.treeFunzionalitaDto.funzionalitaDto.idFunzione}">
						<c:set var="checked" value="checked" scope="request"/>
					</c:if>
				</c:forEach>

				<div class="form-check">
					<input class="form-check-input" type="checkbox" name="listaIdFunzioniSelezionata"
						value ="${albero.treeFunzionalitaDto.idTreeFunzione}"
						id="row<c:out value='${albero.parent.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}'/>"
						${checked}/>
					<label class="form-check-label"
						for="row<c:out value='${albero.parent.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}'/>">
						${albero.treeFunzionalitaDto.funzionalitaDto.codiceFunzione}-${albero.treeFunzionalitaDto.funzionalitaDto.descrizioneFunzione} </label>
				</div>
			</div>
		</div>


	</c:otherwise>
</c:choose>