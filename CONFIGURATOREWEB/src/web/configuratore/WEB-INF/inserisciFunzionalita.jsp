<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root">${pageContext.request.contextPath}</c:set>

<c:import url="common/top.jsp" />
<%@include file="common/controllofunzionalita.jsp" %>

<div id="main_content" class="app-view">
    <div class="app-container">
      <div class="app-row justify-content-md-center">
        <div class="col-12 col-lg-8">
          <div class="link mt-9">
            <a href="${root}/funzionalita" class="link-prev"> Indietro</a>
          </div>
         
                  <form:form action="${root}/inserimentoNuovaFunzionalita" method="post" modelAttribute="funzionalita">
		  
          <div class="card-box card-box-white card-box-notifiche mt-2">
              <div class="card-box-body row">
                <div class="col-12">
                  <h1 class="titolocard">Inserisci nuova Funzionalità</h1>
                  <c:import url="common/alert.jsp"/>  
                  </div>

<!-- inizio campi -->

							<div class="row">
								<div class="col-12 col-md-12">

									<div class="form-group form-group-gestapp">
									<input type="hidden" name="id" value="${funzionalita.id}">
									
										<label for="codice" class="bmd-label-floating">Codice</label>
										<input type="text" class="form-control" id="codice"
											name="codice" maxlength="20" value="${funzionalita.codice}"
											path="codice" /> <span class="bmd-help">codice</span>
									</div>

									<div class="form-group bmd-form-group">
										<label for="Descrizione" class="bmd-label-floating">Descrizione</label>
										<input type="text" class="form-control" id="descrizione"
											name="descrizione" maxlength="150"
											value="${funzionalita.descrizione}" path="descrizione" /> <span
											class="bmd-help">Inserire una descrizione</span>
									</div>

									<div class="form-row">
									<div class="col-5">
										<div class="form-group mb-2 is-filled">
											<label for="exampleSelect1" class="bmd-label-floating">SOL</label>
											<form:select class="form-control" id="exampleSelect1" path="idApplicazione">
												<form:option value="">Seleziona...</form:option>
												<c:forEach items="${sessionScope.data.applicazioneDtoList}" var="applicazione">
													<form:option value="${applicazione.id}">${applicazione.descrizione}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
		
								</div>
								</div>
							</div>
							
							<div class="form-row">
								<div class="col-12">
									<div class="form-group is-filled">
										<label for="DatiAnagrafici" class="bmd-label-floating">Dati Anagrafici</label>
										<form:select class="form-control" id="datiAnagrafici" path="datiAnagrafici">
											<form:option value="">Seleziona...</form:option>
											<c:forEach items="${permessi}" var="permessi">
												<form:option value="${permessi.id}">${permessi.descrizione}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							
							<div class="form-row">
								<div class="col-12">
									<div class="form-group is-filled">
										<label for="DatiAnagrafici" class="bmd-label-floating">Dati Prescrittivi</label>
										<form:select class="form-control" id="datiPrescrittivi" path="datiPrescrittivi">
											<form:option value="">Seleziona...</form:option>
											<c:forEach items="${permessi}" var="permessi">
												<form:option value="${permessi.id}">${permessi.descrizione}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							
							<div class="form-row">
								<div class="col-12">
									<div class="form-group is-filled">
										<label for="DatiAnagrafici" class="bmd-label-floating">Dati Clinici</label>
										<form:select class="form-control" id="datiClinici" path="datiClinici">
											<form:option value="">Seleziona...</form:option>
											<c:forEach items="${permessi}" var="permessi">
												<form:option value="${permessi.id}">${permessi.descrizione}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							
							<div class="form-row">
								<div class="col-12">
									<div class="form-group is-filled">
										<label for="DatiAnagrafici" class="bmd-label-floating">Dati di Consenso (Privacy)</label>
										<form:select class="form-control" id="datiConsenso" path="datiConsenso">
											<form:option value="">Seleziona...</form:option>
											<c:forEach items="${permessi}" var="permessi">
												<form:option value="${permessi.id}">${permessi.descrizione}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							
							<div class="form-row">
								<div class="col-12">
									<div class="form-group is-filled">
										<label for="DatiAnagrafici" class="bmd-label-floating">Dati Amministrativi</label>
										<form:select class="form-control" id="datiAmministrativi" path="datiAmministrativi">
											<form:option value="">Seleziona...</form:option>
											<c:forEach items="${permessi}" var="permessi">
												<form:option value="${permessi.id}">${permessi.descrizione}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-12">
									<div class="switch mt-2">
										<label>
										<c:if test="${funzionalita.funzionalitaAttivo}">
											<c:set var="attivo" value="checked" scope="request"/>
										</c:if>
										 <input type="checkbox"
											name= "funzionalitaAttivo" id="funzionalitaAttivo" ${attivo}> Attiva
										</label>
									</div>
								</div>
							</div>

<!-- fine campi  -->

						</div>
            </div>
            <!-- fine card -->
            
            

            
                    <!-- tasti conferma -->
                    <div class="row prosegui mt-9">
                      <div class="col-md-6 col-sm-12 conferma">
                        <a class="btn btn-info btn-primary"
							href="${root}/inserimentoNuovaFunzionalita" value="annulla">Annulla</a>
                      </div>
                    
                      <div class="col-md-6 col-12 text-md-right mt-2 mt-md-0">

                          <input class="btn btn-primary" type="submit" value="Salva le modifiche">
                       <!--   <div class="alert alert-success" role="alert">
          					  <p>La configurazione &egrave; stata salvata correttamente</p> 
        		 		 </div>-->
                   		 </div>
                   		</div>
                   	

                   	</form:form>            
          
           </div>
         
        </div><!--fine app-row-->


      </div><!--fine app-row-->


    </div>
</div>



<c:import url="common/footer.jsp" /> 





