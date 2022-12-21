<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
    prefix="fn" %>

<div style="display: none !important" id="prototypes">

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
<%-- 
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
                 --%>
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