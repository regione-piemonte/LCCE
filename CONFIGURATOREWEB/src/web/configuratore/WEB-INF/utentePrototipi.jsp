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

    <!-- COLLOCAZIONE -->
    <div data-prototype="collocation">
        <div class="collocation-group">
            <input type="hidden" name="collocazioni"/>

            <div class="d-none bmd-form-group result-box">
                <input type="text" name="collocazioneLabel" disabled="disabled" class="w-100"/>
                <button type="button" class="btn-close"></button>
            </div>

            <div class="form-group input-group mb-2 is-filled bmd-form-group sede-search-box">
                <label class="bmd-label-floating">Sede operativa</label>
                <select class="custom-select" name="sedeOperativa">
                    <option value="">-</option>
                    <c:forEach items="${sediOperative}" var="sede">
                    <c:if test="${fn:length(sediOperative) == 1}">
                    	  <option value="${sede.id}" data-company="${sede.aziendaSanitaria}" selected="selected">
                                ${sede.codice} - ${sede.descrizione}
                        </option>
                    </c:if>   
                    <c:if test="${fn:length(sediOperative) != 1}">
                        <option value="${sede.id}" data-company="${sede.aziendaSanitaria}">
                                ${sede.codice} - ${sede.descrizione}
                        </option>
                      </c:if>   
                    </c:forEach>
                </select>

                <div class="input-group-append">
                    <button type="button" class="btn-close"></button>
                </div>
            </div>

            <div class="collocation-search-box collapse">
                <a class="btn btn-primary btn-outline-primary collocation-select-company-button" role="button">
                    Seleziona Azienda Sanitaria
                    <div class="ripple-container"></div>
                </a>
                <div class="form-group input-group bmd-form-group">
                    <label class="bmd-label-static">Cerca il dettaglio della sede per codice</label>
                    <input type="text" class="form-control coll-code-input">
                    <span class="bmd-help">Inserire almeno tre caratteri</span>
                </div>

                <div class="form-group bmd-form-group">
                    <label class="bmd-label-static">Cerca il dettaglio della sede per descrizione</label>
                    <input type="text" class="form-control coll-desc-input">
                    <span class="bmd-help">Inserire almeno tre caratteri</span>
                </div>
            </div>
        </div>
    </div>

    <!-- SOL -->
    <div data-prototype="sol">
        <div class="card-box card-box-white card-box-notifiche mt-2 sol-group">
            <div class="card-box-body row">
                <div class="col-12">
                    <h2 class="titolocard">Seleziona SOL per questo utente</h2>
                </div>
                <div class="col-12">
                    <div class="form-group input-group mb-2 is-filled bmd-form-group sede-search-box">
                        <label class="bmd-label-floating">Sede operativa</label>
                        <select class="custom-select coll-sol-selector" name="sedeOperativaSol">
                            <option value="">-</option>
                        </select>
                    </div>
                    <div id="rolSolContainer"></div>
                    <div>
                        <div class="col-12 mt-9">
                            <button class="btn btn-outline-danger delete-sol" type="button">Elimina configurazione</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- SOL DEFAULT -->
    <div data-prototype="sol-default">
        <div class="card accordion-box sol-group">
            <div class="card-header">
                <h3 class="mb-0">
                    <button class="btn btn-link text-left collapsed" data-toggle="collapse" aria-expanded="false"
                            type="button"></button>
                </h3>
            </div>

            <div class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <div class="titolocard">
                        <a class="btn btn-link btn-settings" href="#">
                            <span class="fa fa-pencil icon-btn-settings"></span>
                        </a>
                    </div>
                    <div class="row">
                        <!-- START CONTENT -->
                        <div class="col-12 col-md-6">
                            <div style="display: none !important;">
                                <select class="custom-select sol-selector">
                                    <option value="">-</option>
                                </select>
                            </div>
                        </div>

                        <div class="sol-collocation-container col-12"></div>
                        
                        <!-- END CONTENT -->
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- SOL DEFAULT - COLLOCAZIONI -->
    <div data-prototype="sol-collocation">
        <div class="sol-collocation-group">
            <div class="form-group form-group-gestutenti bmd-form-group">
            	<label class="bmd-label-floating">Collocazione</label>
                <input type="text" class="form-control" disabled="disabled" name="collocationName"/>
            </div>

            <div class="sol-role-container"></div>

			<div class="col-12 mt-9">
				<button class="btn btn-outline-danger delete-sol" type="button">
                	Elimina configurazione
            	</button>
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

     <!--SOL- SCELTA SOL -->
     <div data-prototype="sol-selezione">
        <div class="sol-scelta-group">
            <div class="form-group form-group-gestutenti bmd-form-group">
                <label class="bmd-label-floating">Seleziona SOL per questo utente</label>
                <select class="custom-select sol-selector">
                    <option value="">-</option>
                </select>
            </div>
            <div class="sol-profile-container"></div>
        </div>
        <div id="profileButton" style="display: none">
            <a class="btn btn-outline-aggiungi add-sol-profile" role="button" >Aggiungi profilo</a>
            <a class="btn btn-primary btn-outline-primary remove-sol-profiles" >Annulla</a>
        </div>
    </div>



    <!-- SOL - PROFILI -->
    <div data-prototype="sol-profile">
        <div class="profile-group">
            <input type="hidden" name="oldValue" disabled/>
            <input type="hidden" name="profiliSol"/>
            <div class="row" style="align-items: baseline;">
                <div class="col-8">
                    <div class="form-group mb-2 input-group bmd-form-group is-filled">
                        <label class="bmd-label-floating">Profilo</label>
                        <select class="custom-select profile-selector">
                            <option value="">-</option>
                        </select>
                    </div>
                </div>

                <div class="col-4">
                    <a type="button" class="link-primary profile-viewer-button" href="#">
                        Funzionalit&agrave; del profilo
                    </a>
                </div>
            </div>
             <div class="row" name="profiloTitolato">
                <div class="col">
            		<!-- Simple link -->
            		profilo titolato alla funzionalit&agrave;
          		</div>
            </div>
            <div class="row" style="align-items: baseline;">
                <div class="col-8">
                    <div class="form-group input-group date bmd-form-group is-focused"
                         data-provide="datepicker">
                        <label class="bmd-label-floating">Data fine validit&agrave;</label>
                        <input type="text" class="form-control profile-validity" name="dataFineRitiroReferti">

                        <div class="input-group-append">
                            <button type="button" class="btn-calendar"></button>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="col">
                        nel caso di data fine illimitata impostare 31/12/2099
                    </div>
                </div>
            </div>
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