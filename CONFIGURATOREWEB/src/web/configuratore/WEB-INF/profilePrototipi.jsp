<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
    prefix="fn" %>

<div style="display: none !important" id="prototypes">
    <!-- RUOLO -->
    <div data-prototype="role-ro">
        <div class="form-group input-group mb-2 is-filled bmd-form-group role-group">
            <label class="bmd-label-floating">Ruolo</label>
            <select class="custom-select role-selector" name="ruoli">
                <c:if test="${fn:length(ruoliNonConfiguratore) != 1}">
                    <option value="">-</option>
                </c:if>
                <c:forEach items="${ruoliNonConfiguratore}" var="role">
                	<c:if test="${fn:length(ruoliNonConfiguratore) == 1}">
                    	<option value="${role.id}" selected="selected">${role.descrizione}</option>
                    </c:if>	
                    <c:if test="${fn:length(ruoliNonConfiguratore) != 1}">
                    	<option value="${role.id}" >${role.descrizione}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
    </div>
    
      

    <div data-prototype="role">
        <div class="form-group input-group mb-2 is-filled bmd-form-group role-group">
            <label class="bmd-label-floating">Ruolo</label>
            <select class="custom-select role-selector" name="ruoli">
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
    
    <div data-prototype="role-ro-filtered">
        <div class="form-group input-group mb-2 is-filled bmd-form-group role-group">
            <label class="bmd-label-floating">Ruolo</label>
            <select class="custom-select role-selector" name="ruoli">
                <c:if test="${fn:length(ruoliNonConfiguratoreSelezionabili) != 1}">
                    <option value="">-</option>
                </c:if>
                <c:forEach items="${ruoliNonConfiguratoreSelezionabili}" var="role">
                    <c:if test="${fn:length(ruoliNonConfiguratoreSelezionabili) == 1}">
                        <option value="${role.id}" selected="selected">${role.descrizione}</option>
                    </c:if>
                    <c:if test="${fn:length(ruoliNonConfiguratoreSelezionabili) != 1}">
                        <option value="${role.id}">${role.descrizione}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
    </div>

    <div data-prototype="role-filtered">
        <div class="form-group input-group mb-2 is-filled bmd-form-group role-group">
            <label class="bmd-label-floating">Ruolo</label>
            <select class="custom-select role-selector" name="ruoli">
                <c:if test="${fn:length(ruoliSelezionabili) != 1}">
                    <option value="">-</option>
                </c:if>
                <c:forEach items="${ruoliSelezionabili}" var="role">
                    <c:if test="${fn:length(ruoliSelezionabili) == 1}">
                        <option value="${role.id}" selected="selected">${role.descrizione}</option>
                    </c:if>
                    <c:if test="${fn:length(ruoliSelezionabili) != 1}">
                        <option value="${role.id}">${role.descrizione}</option>
                    </c:if>
                </c:forEach>
            </select>

            <div class="input-group-append">
                <button type="button" class="btn-close"></button>
            </div>
        </div>
    </div>

    

	<!-- SOL DEFAULT - RUOLI -->
    <div data-prototype="sol-roles-default">
        <div class="sol-role-group">
            <div class="form-group form-group-gestutenti bmd-form-group">
            	<label class="bmd-label-floating">Ruolo</label>
                <input type="text" class="form-control" disabled="disabled" name="roleName"/>
            </div>

            <div class="sol-profile-container"></div>

            <!-- <div>
                <a class="btn btn-outline-aggiungi add-sol-profile" role="button">Aggiungi profilo</a>
                <a class="btn btn-primary btn-outline-primary remove-sol-profiles">Annulla</a>
            </div>-->
        </div>
    </div>
	

   <!-- SOL - RUOLI -->
    <div data-prototype="sol-ruoli">
        <div class="sol-ruoli-group">
            <div class="form-group form-group-gestutenti bmd-form-group">
                <label class="bmd-label-floating">Ruolo</label>
                <select class="custom-select ruolo-sol-selector" name="ruoloSol">
                    <option value="">-</option>
                </select>
            </div>
            <div id="sceltaSolContainer"></div>
           </div>
    </div>
</div>

    