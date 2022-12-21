<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="root">${pageContext.request.contextPath}</c:set>


<%@ page import="it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp" %>
<c:set var="UTENTE_NON_PRESENTE" value="<%=ConstantsWebApp.UTENTE_NON_PRESENTE%>"/>
<c:set var="PAGINAZIONE_20_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_20_ELEMENTI%>"/>
<c:set var="PAGINAZIONE_50_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_50_ELEMENTI%>"/>
<c:set var="PAGINAZIONE_100_ELEMENTI" value="<%=ConstantsWebApp.PAGINAZIONE_100_ELEMENTI%>"/>
<style>
    .ui-dialog {
        position: absolute !important;
        top: 50px !important;
        right: 100px;
        left: 0;
        z-index: 10040;
        overflow: auto;
        overflow-y: auto;
        height: 450px !important;
        width: 900px !important;
    }
    
    </style>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var ="checkElaborazione" value="${checkElaborazione}" scope="request"></c:set>
<c:import url="common/top.jsp"/>
<%@include file="common/controllofunzionalita.jsp" %>
<div id="main_content" class="app-view">
    <div class="app-container">
        <div class="app-row justify-content-md-center">
            <div class="col-12 col-lg-12">
                <div class="menu-interno d-lg-block" id="menu-interno">
                    <!-- finemenu dropdown responsive-->
                    <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                     <c:if test="${OPRicercaUtente eq 'true'}">
						<li class="nav-item"><a class="nav-link"
												href="${root}/utenti"> <span>Gestione utenti</span>
							</a></li>
						</c:if>
                        <c:if test="${OPRicercaRuolo eq 'true'}">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${root}/ruoli"> <span>Gestione ruoli</span>
                            </a></li>
                        </c:if>
                        <c:if test="${OPRicercaProfilo eq 'true'}">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${root}/profili"> <span>Gestione profili</span>
                            </a></li>
                        </c:if>
						<c:if test="${OPRicercaApplicazione eq 'true'}">
							<li class="nav-item"><a class="nav-link"
													href="${root}/applicazioni"> <span>Gestione applicazioni</span>
							</a></li>
						</c:if>
                        <c:if test="${OPRicercaFunzionalita eq 'true'}">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${root}/funzionalita"> <span>Gestione funzionalità</span>
                            </a></li>
                        </c:if>
                        <c:if test="${OPRicercaCredenzialiRUPAR eq 'true'}">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${root}/rupar"> <span>Credenziali Rupar</span>
                            </a></li>
                        </c:if>
                        <c:if test="${OPRicercaAbilitazioneMassiva eq 'true'}">
	                        <li class="nav-item"><a class="nav-link"
	                                                href="${root}/abilitazione-massiva"> <span>Abilitazione massiva</span>
	                        </a></li>
                        </c:if>     
                        <li class="nav-item"><a class="nav-link active"
                            href="${root}/disabilitazione-massiva"> <span>Disabilitazione massiva</span>
                        </a></li>                   
                    </ul>
                </div>
			</div>
			<div class="col-12 col-lg-8">
					<c:if test="${not empty errori}">
					<br><br>
		            <c:forEach items="${errori}" var="errore">
		                <c:choose>
		                    <c:when test="${errore.getCodice().equals(UTENTE_NON_PRESENTE)}">
		                        <div class="app-row justify-content-md-center">
		                            <div class="col-12 col-lg-8">
		                        
		
		                                <div class="row">
		                                    <div class="col-8">
		                                        <c:if test="${paginaUtenti.getElementiTotali() == 0}">
		                                            <p><strong>Nessun utente trovato</strong></p>
		                                        </c:if>
		                                    </div>
		                                </div>
		                                <div class="alert alert-warning mt-7" role="alert">
		                                    <p><strong>ATTENZIONE</strong></p>
		                                    <p>${errore.getDescrizione()}</p>
		                                </div>
		                            </div>
		                        </div>
		                    </c:when>
		                    <c:otherwise>
		                        <c:import url="common/alert.jsp" />
		                    </c:otherwise>
		                </c:choose>
		            </c:forEach>
		        </c:if>
                <h1 class="titolocard mt-7">Cerca utenti da disabilitare</h1>
                <div class="card-box card-box-white card-box-notifiche">
                    <div class="card-box-body row">
                        <div class="col-12">
                            <form:form id="searchForm" action="${root}/cercaUtentiDisabilitazioneMassiva" method="post"
                                       modelAttribute="abilitazione" class="form-inline">
                                
                                <%--<spring:bind path="selected">
                                    <c:if test="${status.error}">
                                        <div class="alert alert-danger">
                                            <c:forEach items="${status.errorMessages}" var="message">
                                                <p>${message}</p>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                </spring:bind>

								<form:hidden path="selected"/> --%>
								<input type="hidden" name="selected"/>
                                
                                <div class="form-group mx-2 bmd-form-group" style="width: 265px; overflow: hidden">
                                    <label for="azienda" class="bmd-label-floating">Azienda *</label> 
                                    <select class="form-control" name="azienda" id="azienda" path="azienda" required style="width: 265px">
										<option selected value="" disabled="disabled">Seleziona azienda</option>
										<c:forEach items="${aziende}" var="sede">
											<option value="${sede.id}" data-company="${sede.aziendaSanitaria}">
													${sede.codice} - ${sede.descrizione}
											</option>
										</c:forEach>
									</select>
                                </div>
                                
                                <div class="form-group mx-2 bmd-form-group col-7">
	                                <input type="hidden" name="collocazione"/>
	                                <input type="hidden" name="collocazioneLabel"/>
	
	            					<div class="d-none result-box w-100">
	            						<label class="bmd-label-static mx-3">Collocazione</label>
	                					<input type="text" name="collocazioneLabel" disabled="disabled" class="w-100"/>
	                					<div class="input-group-append">
	                    					<button type="button" id="revertColl" class="btn-close"></button>
	                					</div>
	            					</div>
	                                <div class="collocation-search-box w-100">
										<div class="form-group mx-2 bmd-form-group w-100">
											<label class="bmd-label-static">Cerca collocazione per codice</label> 
											<input type="text" class="w-100 form-control coll-code-input" placeholder="Inserire almeno tre caratteri" disabled="disabled">
										</div>
		
										<div class="form-group mx-2 bmd-form-group w-100">
											<label class="bmd-label-static">Cerca collocazione per descrizione</label> 
											<input type="text" class="w-100 form-control coll-desc-input" placeholder="Inserire almeno tre caratteri" disabled="disabled">
										</div>
									</div>
								</div>

							
								<div class="form-group mx-2 bmd-form-group" style="width: 265px; overflow: hidden">
                                    <div class="w-100">
                                        <div class="form-group mx-2 bmd-form-group w-100" style="padding-bottom: 30px">
                                           <label for="sol" class="bmd-label-floating">Servizio Online *</label> 
                                           <select class="w-100 form-control" name="sol" id="sol" path="sol" disabled="disabled" required>
                                               <option selected value="" disabled="disabled">Seleziona un SOL</option>
                                           </select>
                                       </div>
                                   </div>
	                             </div>  
	                                
	                             <div class="form-group mx-2 bmd-form-group col-7" style="overflow: hidden">
                                    <div class="w-100">
                                        <div class="form-group mx-2 bmd-form-group w-100" style="padding-bottom: 30px">
                                    <label for="ruolo" class="bmd-label-floating">Ruolo </label> 
                                    <select class="form-control" name="ruolo" id="ruolo" path="ruolo">
										<option selected value="">Seleziona un ruolo</option>
										<c:forEach items="${ruoli}" var="role">
											<option value="${role.id}">${role.descrizione}</option>
										</c:forEach>
									</select>
                                </div>
                            </div>
                        </div>

                                <form:hidden path="numeroPagina"/>
                                <form:hidden path="numeroElementi"/>
                                <form:hidden path="allSelected"/>
                                <input type="hidden" name="checkElaborazione" path="checkElaborazione" value="${checkElaborazione}"/>
                                <form:hidden name="disabilitazione" path="disabilitazione"/>
								<span class="col-md-12 form-group bmd-form-group"> <!-- needed to match padding for floating labels -->
                                  <button type="submit" class="btn btn-primary" id="btnRicercaMassiva">Cerca</button>
                                </span>
                            </form:form>
                        </div>
                    </div>

                </div>
            </div><!--fine app-row-->


        </div>
        <!--fine app-row-->
        <c:if test="${OPRVisualizzaStoricoMassiva eq 'true'}">
               <div class="row">
                <div class="app-row justify-content-md-center">
                    <div class="col-12 col-lg-8">
                <span class="col-md-12 form-group bmd-form-group text-left" > <!-- needed to match padding for floating labels -->
                    <button type="button" class="btn btn-primary" id="btnStorico" style="margin-top: 10px;">Visualizza Storico</button>
                  </span>
                </div>
                </div>
            </div>
            </c:if>

		<br><br>


        <c:if test="${paginaUtenti != null}">
            <div class="app-row justify-content-md-center">
                <div class="col-12 col-lg-8">
                  <div class="row">
                    <div class="col-8">
                    </div>
                    <div class="col-4">
                       <form action="${root}/annullaRicercaDisabilitazione" method="POST">
                            <input type="submit" class="btn btn-primary btn-outline-primary"
                                   role="button" value="Annulla la ricerca"/></form>
                    </div>
                  </div>
                </div>
              </div>
        </c:if>

        <c:if test="${paginaUtenti != null && paginaUtenti.getElementiTotali() != 0}">
            <div class="row info-pagination mt-9 justify-content-between">
                <div class="col-12">
                    <h2 class="title_prepagination episodi">
                    	${paginaUtenti.getElementiTotali()} Risultati trovati
                    	<button id="selectAll" class="btn btn-outline-primary ml-2">Seleziona tutti</button>
                    	<button id="deselectAll" class="btn btn-outline-danger ml-2" hidden="true">Deseleziona tutti</button>
                    </h2>
                    <!--  <p class="note">Seleziona un profilo per modificarlo o eliminarlo</p>  -->
                </div>

            </div>

            <div class="app-row justify-content-md-center">
                <div class="col-12">

                    <div class="div-overflow">
                        <table class="table table-hover table-sortable">
                            <thead>
                            <tr>
                            	<th scope="col"><input type="checkbox" id="checkPage"><span class="headerTxt">Tutta la pagina</span> </th>
                                <th scope="col"><span class="headerTxt">Cognome</span></th>
                                <th scope="col"><span class="headerTxt">Nome</span></th>
                                <th scope="col"><span class="headerTxt">Codice Fiscale</span></th>
                                <th scope="col"><span class="headerTxt">Collocazione</span></th>
                                <th scope="col"><span class="headerTxt">Ruolo</span></th>
                                <th scope="col"><span class="headerTxt">Profilo</span></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${paginaUtenti.getElementi()}" var="item" varStatus="loop">
                                <tr>
                                	<td><input type="checkbox" name="checkUtente" value="${item.id}" /></td>
                                    <td>${item.cognome}</td>
                                    <td>${item.nome}</td>
                                    <td>${item.codiceFiscale}</td>
                                    <td>${item.collocazione}</td>
                                    <td>${item.ruolo}</td>
                                    <td>${item.profilo}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>


                    <!--/div-->
                </div>

               
            </div>
            <c:set var="pagina" value="${paginaUtenti}" scope="request"></c:set>
            <c:set var="model" value="${abilitazione}" scope="request" />
            <c:import url="common/paginazioneDisabilitazioneMassiva.jsp" />
               </c:if>
               <form:form id="abilitazioneForm"
               action="${root}/disabilitazioneMassiva" method="post"
               modelAttribute="abilitazione" class="form-inline">
               <form:hidden name="disabilitazione" path="disabilitazione"/>
                    <form:hidden path="selected"/>
                    <form:hidden path="azienda"/>
                    <form:hidden path="collocazione"/>
                    <form:hidden path="collocazioneLabel"/>
                    <form:hidden path="allSelected"/>
                    <form:hidden path="ruolo"/>
                    <form:hidden path="sol"/>
               <div id="modal_dialog" title="Conferma">
                <div class='title'>
                 </div>
                <input type='submit' value='SI' id='btnYes' />
                <input type='button' value='NO' id='btnNo' />
            </div>
        </form:form>
        <div id="storico" title="Storico">
            <div class="row">
            <div class="app-row justify-content-md-center">
                <div class="col-12">
                    <button id="btnAggiorna" class="btn btn-primary">Aggiorna</button>
                    <div class="div-overflow">
                        <table class="table table-hover table-sortable">
                            <thead>
                            <tr>
                                <th scope="col"><span class="headerTxt">Stato Elaborazione</span></th>
                                <th scope="col"><span class="headerTxt">Data Inserimento</span></th>
                                <th scope="col"><span class="headerTxt">Avanzamento</span></th>
                                <th scope="col"><span class="headerTxt">Servizio Online</span></th>
                                <th scope="col"><span class="headerTxt">CSV Utenti Disabilitati</span></th>
                                <th scope="col"><span class="headerTxt">CSV Utenti Non Disabilitati</span></th>
                            </tr>
                            </thead>
                            <tbody id="tableStorico">
                            </tbody>
                        </table>
                    </div>


                    <!--/div-->
                </div>

               
            </div>
            </div>
            </div>
    </div>
   </div>

<c:import url="common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/disabilitazioneMassiva.js" lang="javascript"></script>
<script src="${pageContext.request.contextPath}/js/sortTable.js" lang="javascript"></script>
<script src="${pageContext.request.contextPath}/js/paginazioneDisabilitazioneMassiva.js" lang="javascript"></script>

<script type="text/javascript">
	$(() => {	
		//let abilitazione = '${abilitazione}';
		let azienda = '${abilitazione.azienda}';
		let ruolo = '${abilitazione.ruolo}';
		let collocazione = '${abilitazione.collocazione}';
		let collocazioneLabel = '${abilitazione.collocazioneLabel}';
		let sol = '${abilitazione.sol}';
        let selected='${abilitazione.selected}';

        $('input[name="checkElaborazione"]').val('${checkElaborazione}');
		
		if(azienda != null && azienda != '') {
            $('#azienda').val(azienda).change();
            setTimeout(()=>{
                showLoading();
                $('#azienda').attr('readonly',true);
                $('#ruolo').attr('readonly',true);
                $('select[name=sol]').attr('readonly', true);
                if($('input[name="collocazioneLabel"]').val() != '' && $('input[name="collocazioneLabel"]').val() != null){
                    $('.input-group-append').attr('hidden',true);
                }else{
                    $('input.coll-code-input').attr('readonly', true);
                    $('input.coll-desc-input').attr('readonly', true)
    
            }
            if(sol != null && sol != '') {
			            $('#sol').val(sol)
		                }
            $('#btnRicercaMassiva').prop('disabled', true);  
            hideLoading();          
            },"1500");
		}
		if(ruolo != null && ruolo != '') {
			$('#ruolo').val(ruolo).change();
		}

        
		
		if(collocazione != null && collocazione != '') {
			$('input[name="collocazione"]').val(collocazione);
    		$('input[name="collocazioneLabel"]').val(collocazioneLabel);

    		$('div.sede-search-box').hide();
    		$('div.collocation-search-box').hide();
    		$('div.result-box').removeClass('d-none').addClass('d-flex');
    		
    		if($('#ruolo').val() != '' && $('#ruolo').val() != null) {
				$('select[name=sol]').prop('disabled', true);
				$('select[name=sol]').val('');
				let coll = $('input[name="collocazione"]').val() != '' && $('input[name="collocazione"]').val() != null ? 
					$('input[name="collocazione"]').val() : $('#azienda').val();
				let role = $('#ruolo').val();
				showLoading();
				$.ajax({
		            url: '${root}/ajax/solSelezionabili',
		            dataType: 'json',
		            data: {collocazione: coll,
		                   ruolo: role
		            },
		            method: 'POST',
		            traditional: true,
		            success: data => {
				    	let solOptions = data.filter((i,e) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
				    	$('select[name=sol]').find('option').filter((i,e) => $(e).val() != '').remove();
				    	$('select[name=sol]').append(solOptions).focus(e => $(e.target).data('previous-value', $(e.target).val()));
		    			$('select[name=sol]').prop('disabled', false);
                        if(sol != null && sol != '') {
			            $('#sol').val(sol)
		                }
	        			hideLoading();
					}
	        	});
			}
		}
		
		$('input[name="selected"]').val(selected);
        $('input[name="disabilitazione"]').val(true);
        
        let paginaAttuale = $('li.page-item.active a.page-link').data('pagina');
        let checked = selected.replaceAll('[', '').replaceAll(']', '').split(", ")
        					  .filter( elem => elem.startsWith(paginaAttuale+'|'))
        					  .map( elem => elem.split('|')[1]);
        checked.forEach(c => $('input[name="checkUtente"][value='+c+']').prop('checked', true));
        
        $('#checkPage').change(function() {
        var ischecked= $(this).is(':checked');
        $('input[name="checkUtente"]').prop('checked', ischecked);
        let paginaAttuale = $('li.page-item.active a.page-link').data('pagina');
        let selected = $('input[name="checkUtente"]:checked')
        .map((i,e) => paginaAttuale + '|'+($(e).val())).toArray();
        let valoriSelezionati=$('input[name="selected"]').val();
        if(valoriSelezionati == '' || valoriSelezionati == '[]')
			valoriSelezionati = [];
		else
        	valoriSelezionati = valoriSelezionati.replaceAll('[', '').replaceAll(']', '').split(", ");

        valoriSelezionati = valoriSelezionati.filter( elem => !elem.startsWith(paginaAttuale+'|'));

        valoriSelezionati = valoriSelezionati.concat(selected);
        $('input[name="selected"]').val(valoriSelezionati);
		        var check=$('input[name="checkUtente"]').is(":checked");
                if($('input[name="selected"]').val()!='[]' && $('input[name="selected"]').val()!=''){
                $("#btnInserimentoMassivo").prop("disabled", false);
                }else{
                    if(check){
		        $("#btnInserimentoMassivo").prop("disabled", false);
	            }else{
		        $("#btnInserimentoMassivo").prop("disabled", true);
                }
                }

          
            
        }); 
        
        if($('input[name="selected"]').val()!='[]'){
                $("#btnInserimentoMassivo").prop("disabled", false);
            }
        
        $("[name='checkUtente']").click(function(){
        let paginaAttuale = $('li.page-item.active a.page-link').data('pagina');
        let selected = $('input[name="checkUtente"]:checked')
        .map((i,e) => paginaAttuale + '|'+($(e).val())).toArray();
        let valoriSelezionati=$('input[name="selected"]').val();
        if(valoriSelezionati == '' || valoriSelezionati == '[]')
			valoriSelezionati = [];
		else
        	valoriSelezionati = valoriSelezionati.replaceAll('[', '').replaceAll(']', '').split(", ");

        valoriSelezionati = valoriSelezionati.filter( elem => !elem.startsWith(paginaAttuale+'|'));

        valoriSelezionati = valoriSelezionati.concat(selected);
        $('input[name="selected"]').val(valoriSelezionati);
		        var check=$('input[name="checkUtente"]').is(":checked");
                if($('input[name="selected"]').val()!='[]' && $('input[name="selected"]').val()!=''){
                $("#btnInserimentoMassivo").prop("disabled", false);
                }else{
                    if(check){
		        $("#btnInserimentoMassivo").prop("disabled", false);
	            }else{
		        $("#btnInserimentoMassivo").prop("disabled", true);
                }
                }
	            
	});

	})
</script>