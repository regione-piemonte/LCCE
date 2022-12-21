<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:if test="${not empty errori}">
	<div id="alertMessage" name="alertMessage"
		class="app-row justify-content-md-center">
		<div class="col-12 col-md-9">
			<c:forEach items="${errori}" var="errore">
				<c:choose>
				<c:when test="${errore.tipoMessaggio == 'E'}">
					<div class="alert alert-danger" role="alert">
				</c:when>
				<c:when test="${errore.tipoMessaggio == 'S'}">
					<div class="alert alert-success" role="alert">
				</c:when>
				<c:otherwise>
					<div class="alert alert-warning" role="alert">
				</c:otherwise>
				</c:choose>
				<!-- <p>Inserire un messaggio di alert nel caso in cui ci siano problemi</p> -->
				<p>${errore.descrizione}</p>
				</div>
			</c:forEach>
		</div>
	</div>
	
</c:if>