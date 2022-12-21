<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
    prefix="fn" %>

<div style="display: none !important" id="prototypes">
    <!-- RUOLO -->
    <div data-prototype="role">
        <div class="form-group role-group row">
        <input class="ruoliSelH" type="hidden" name="ruoliSel" value="-"/>
        <div class=" input-group mb-2 is-filled bmd-form-group roleSel-group" style="flex: 0 0 50%">
            <label class="bmd-label-floating">Ruolo</label>
            <select class="custom-select role-selector" name="ruoliSelect">
                <c:if test="${fn:length(ruoli) != 1}">
                    <option value="">-</option>
                </c:if>
                <c:forEach items="${ruoli}" var="role">
	                <c:if test="${fn:length(ruoli) == 1}">
						<option value="${role.id}" selected="selected">${role.descrizione}</option>
	                </c:if>	
					<c:if test="${fn:length(ruoli) != 1}">
                    	<option value="${role.id}">${role.descrizione}</option>
                    </c:if>
                </c:forEach>
            </select>

            <div class="input-group-append">
                <!--  button type="button" class="btn-close"></button -->
            </div>
        </div>
        
        <div class=" input-group mb-2 is-filled bmd-form-group collTipo-group" style="flex: 0 0 50%">
            <label class="bmd-label-floating">Tipo Collocazione</label>
            <select class="custom-select collTipo-selector" name="collTipoSelect">
                <c:if test="${fn:length(ruoli) != 1}">
                    <option value="">-</option>
                </c:if>
                <c:forEach items="${collocazioniTipo}" var="coll">
	                <c:if test="${fn:length(collocazioniTipo) == 1}">
						<option value="${coll.idColTipo}" selected="selected">${coll.colTipoDescrizione}</option>
	                </c:if>	
					<c:if test="${fn:length(collocazioniTipo) != 1}">
                    	<option value="${coll.idColTipo}">${coll.colTipoDescrizione}</option>
                    </c:if>
                </c:forEach>
            </select>

            <div class="input-group-append">
                <button type="button" class="btn-close"></button>
            </div>
        </div>
        

        </div>

    </div>
    
    <!-- ruoli compatibili -->
    <div data-prototype="ruoliCompatibili">
        <div class="form-group input-group mb-2 is-filled bmd-form-group role-group">
            <label class="bmd-label-floating">Ruolo</label>
            <select class="custom-select ruoliComp-selector" name="ruoliCompatibili">
                <c:if test="${fn:length(ruoli) != 1}">
                    <option value="">-</option>
                </c:if>
                <c:forEach items="${ruoli}" var="role">
	                <c:if test="${fn:length(ruoli) == 1}">
						<option value="${role.id}" selected="selected">${role.descrizione}</option>
	                </c:if>	
					<c:if test="${fn:length(ruoli) != 1}">
                    	<option value="${role.id}">${role.descrizione}</option>
                    </c:if>
                </c:forEach>
            </select>

            <div class="input-group-append">
                <button type="button" class="btn-close"></button>
            </div>
        </div>
        


    </div> 


<!-- Modal funzionalita' SOL -->
<div class="modal fade" id="funzionalitaModal" tabindex="-1" role="dialog"
     aria-labelledby="funzionalita1Title" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title"></h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
            </div>
        </div>
    </div>
</div>

</div>