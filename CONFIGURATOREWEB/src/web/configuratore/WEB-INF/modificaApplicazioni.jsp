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
            <a href="${root}/applicazioni" class="link-prev"> Indietro</a>
          </div>
         
                  <form:form action="${root}/modificaApplicazione" method="post" modelAttribute="applicazione">
		  
          <div class="card-box card-box-white card-box-notifiche mt-2">
              <div class="card-box-body row">
                <div class="col-12">
                  <h1 class="titolocard">Configura Applicazione</h1>
                  <jsp:include page="common/alert.jsp"/>
                  </div>

<!-- inizio campi -->

							<div class="row">
								<div class="col-12 col-md-12">

									<div class="form-group form-group-gestapp">
									<input type="hidden" name="id" value="${applicazione.id}">
									
										<label for="codice" class="bmd-label-floating">Codice</label>
										<input type="text" class="form-control" id="codice"
											name="codice" maxlength="20" value="${applicazione.codice}"
											path="codice"  readonly="readonly"/> <span class="bmd-help">codice</span>
									</div>

									<div class="form-group bmd-form-group">
										<label for="Descrizione" class="bmd-label-floating">Descrizione</label>
										<input type="text" class="form-control" id="descrizione"
											name="descrizione" maxlength="150"
											value="${applicazione.descrizione}" path="descrizione" /> <span
											class="bmd-help">Inserire una descrizione</span>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-12 col-md-12">
									<div class="form-group bmd-form-group">
										<label for="descrizioneWebapp" class="bmd-label-floating">Descrizione della webapp</label>
										<input type="text" class="form-control" id="descrizioneWebapp"
											name="descrizioneWebapp" maxlength="150"
											value="${applicazione.descrizioneWebapp}"
											path="descrizioneWebapp" /> <span class="bmd-help">Inserire
											la descrizione della Webapp</span>
									</div>
								</div>
							</div>							


							<div class="row">
								<div class="col-12">
									<div class="switch mt-2">
										<label>
										<c:set var="flagPIN" value="" scope="request"/>
										<c:if test="${applicazione.flagPIN}">
											<c:set var="flagPIN" value="checked" scope="request"/>
										</c:if>
										 <input type="checkbox"
											name= "flagPIN" id="flagPIN" ${flagPIN}> richiesto PIN
										</label>
									</div>
								</div>

							</div>
							
							<div class="row">
								<div class="col-12">
									<div class="switch mt-2">
										<label>
										<c:set var="flagBottone" value="" scope="request"/>
										<c:if test="${applicazione.flagBottone}">
											<c:set var="flagBottone" value="checked" scope="request"/>
										</c:if>
										 <input type="checkbox"
											name= "flagBottone" id="flagBottone" ${flagBottone}> flag Bottone
										</label>
									</div>
								</div>

							</div>
							


							<div class="row">
								<div class="col-12">
									<div class="switch mt-2">
										<label>
										<c:set var="flagOscurato" value="" scope="request"/>
										<c:if test="${applicazione.flagOscurato}">
											<c:set var="flagOscurato" value="checked" scope="request"/>
										</c:if>
										 <input type="checkbox"
											name= "flagOscurato" id="flagOscurato" ${flagOscurato}> Oscurato
										</label>
									</div>
								</div>

							</div>



							<div class="row">
								<div class="col-12 col-md-12">
									<div class="form-group bmd-form-group">
										<label for="urlverifyloginconditions"
											class="bmd-label-floating">URL servizio verifyloginconditions</label> <input
											type="text" class="form-control"
											id="urlverifyloginconditions" name="urlverifyloginconditions"
											maxlength="150"
											value="${applicazione.urlverifyloginconditions}"
											path="urlverifyloginconditions" /> <span class="bmd-help">Inserire
											la URL del servizio verifyloginconditions</span>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-12 col-md-12">
									<div class="form-group bmd-form-group">
										<label for="pathImmagine" class="bmd-label-floating">Path dell'immagine</label>
											<form:select path="pathImmagine" items="${icons}" cssClass="custom-select" /> <span
											class="bmd-help">Inserire il path dell'immagine</span>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-12 col-md-12">
									<div class="form-group bmd-form-group">
										<label for="redirectUrl" class="bmd-label-floating">URL della Applicazione</label>
										<input type="text" class="form-control" id="redirectUrl"
											name="redirectUrl" maxlength="150"
											value="${applicazione.redirectUrl}" path="redirectUrl" /> <span
											class="bmd-help">Inserire la URL della Applicazione</span>
									</div>
								</div>
							</div>



<!-- fine campi  -->

						</div>
              
              
            </div>
            <!-- fine card -->
            
                        <!-- COLLOCAZIONI -->
                        <div class="card-box card-box-white card-box-notifiche mt-2">
                            <div class="card-box-body row">
                                <div class="col-12">
                                    <h2 class="titolocard">Collocazione</h2>
                                    <spring:bind path="collocazioni">
                                        <c:if test="${status.error}">
                                            <div class="alert alert-danger">
                                                <c:forEach items="${status.errorMessages}" var="message">
                                                    <p>${message}</p>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </spring:bind>

                                    <form:hidden path="collocazioni"/>

                                    <div id="collocationContainer"></div>

                                    <div class="mt-6">
                                        <a class="btn btn-outline-aggiungi" role="button" id="addCollocationBtn">Aggiungi
                                            sede operativa</a>
                                    </div>
                                </div>
                                
                                <%--
                                <div class="col-12">
                                    <div class="row prosegui mt-9">
                                        <div class=" col-sm-12 indietro">
                                            <button class="btn btn-primary btn-outline-primary" id="collocationsUndoBtn"
                                                    type="button">
                                                Annulla
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                 --%>
                                
                            </div>
                        </div>
            

            
                    <!-- tasti conferma -->
                    <div class="row prosegui mt-9">
                      <div class="col-md-6 col-sm-12 conferma">
                            <c:url var="annullaModifica" value="/updateApplicazione">
                                <c:param name="id" value="${applicazione.id}" />
                            </c:url>
                      	  <a class="btn btn-info btn-primary"
								href="${annullaModifica}" value="annulla">Annulla</a>
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


<c:import url="collocazionePrototipo.jsp"/>

<c:import url="common/footer.jsp" /> 


<script src="<c:url value="/js/collocazioni.js" />" lang="javascript"></script>

<script>
    $(() => collocazioni.initDataEditPage());
</script>



