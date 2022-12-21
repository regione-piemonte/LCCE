$(() => {
	
    $('#btnRicercaMassiva').click(e => {
		if($('select[name="azienda"]').val() != null && $('select[name="azienda"]').val() != ''
			&& $('select[name="ruolo"]').val() != null && $('select[name="ruolo"]').val() != '') {
			showLoading();
			$('input[name="selected"]').val([]);
			$('input[name="allSelected"]').val(false);
			$('input[name="numeroPagina"]').val(1);
		}
	})	
	
	/**
	 * Controllo tasto di annullamento collocazione inserita
	 */
	$('#revertColl').click(e => {
		e.preventDefault();
		$('input[name="collocazione"]').val('');
        $('input[name="collocazioneLabel"]').val('');
		$('div.sede-search-box').show();
        $('div.collocation-search-box').show();
        $('div.result-box').removeClass('d-flex').addClass('d-none');
        $('input.coll-code-input').val('');
        $('input.coll-desc-input').val('');
	})
	
	/**
	 * Alla selezione di un'azienda, se e'' stato selezionato un ruolo, inizializzo e riempio la select dei SOL'.
	 * Inoltre inizializzo le ricerche delle collocazioni per codice e descrizione con autocompletamento.
	 */
	 $('#azienda').change(e => {
		e.preventDefault();
		
		if($('#ruolo').val() != '' && $('#ruolo').val() != null) {
			$('select[name=sol]').prop('disabled', true);
			$('select[name=sol]').val('');
			let coll = $('input[name="collocazione"]').val() != '' && $('input[name="collocazione"]').val() != null ? 
				$('input[name="collocazione"]').val() : $('#azienda').val();
			let role = $('#ruolo').val();
			showLoading();
			$.ajax({
	            url: '/configuratore/ajax/solSelezionabili',
	            dataType: 'json',
	            data: {collocazione: coll,
	                   ruolo: role
	            },
	            method: 'POST',
	            traditional: true,
	            success: data => {
			    	let solOptions = data.filter((e,i) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
			    	$('select[name=sol]').find('option').filter((i,e) => $(e).val() != '').remove();
			    	$('select[name=sol]').append(solOptions).focus(e => $(e.target).data('previous-value', $(e.target).val()));
	    			$('select[name=sol]').prop('disabled', false);
		        	hideLoading();
				}
        	});
		}
		
		$('input[name="collocazione"]').val('');
        $('input[name="collocazioneLabel"]').val('');
		$('div.sede-search-box').show();
        $('div.collocation-search-box').show();
        $('div.result-box').removeClass('d-flex').addClass('d-none');
        $('input.coll-code-input').val('');
        $('input.coll-desc-input').val('');
        // set calendario
        $.datepicker.regional['it'] = {
            closeText: 'Chiudi',
            currentText: 'Oggi',
            monthNames: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'],
            monthNamesShort: ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Giu', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'],
            dayNames: ['Domenica', 'Luned&#236', 'Marted&#236', 'Mercoled&#236', 'Gioved&#236', 'Venerd&#236', 'Sabato'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Me', 'Gio', 'Ve', 'Sa'],
            dateFormat: 'dd/mm/yy',
            firstDay: 1
        };

        $.datepicker.setDefaults({changeMonth: true, changeYear: true});
        $.datepicker.setDefaults($.datepicker.regional['it']);
        
		$('input.coll-code-input').prop('disabled', false).autocomplete({
            source: (request, response) => {
                $.ajax({
                    url: '/configuratore/ajax/collocazione',
                    dataType: 'json',
                    data: {
                        code: $(e.target).find('option:selected').text().trim(),
                        codeTerm: request.term
                    },
                    success: data => {
                        let resp = {};
                        data.forEach(function (item, i) {
                            resp[i] = {};
                            resp[i].label = item.codice + ' - ' + item.descrizione;
                            resp[i].value = item.codice;
                            resp[i].id = item.id;
                            resp[i].code = item.codice;
                            resp[i].desc = item.descrizione;
                        });
                        response(resp);
                    }
                });
            },
            minLength: 3,
            select: (event, ui) =>  {
				let label = ui.item.code + ' - ' + ui.item.desc;

        		$('input[name="collocazione"]').val(ui.item.id);
        		$('input[name="collocazioneLabel"]').val(label);

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
			            url: '/configuratore/ajax/solSelezionabili',
			            dataType: 'json',
			            data: {collocazione: coll,
			                   ruolo: role
			            },
			            method: 'POST',
			            traditional: true,
			            success: data => {
					    	let solOptions = data.filter((e,i) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
					    	$('select[name=sol]').find('option').filter((i,e) => $(e).val() != '').remove();
					    	$('select[name=sol]').append(solOptions).focus(e => $(e.target).data('previous-value', $(e.target).val()));
			    			$('select[name=sol]').prop('disabled', false);
		        			hideLoading();
						}
		        	});
				}
			}
        });

        $('input.coll-desc-input').prop('disabled', false).autocomplete({
            source: (request, response) => {
                $.ajax({
                    url: '/configuratore/ajax/collocazione',
                    dataType: 'json',
                    data: {
                        code: $(e.target).find('option:selected').text().trim(),
                        descTerm: request.term
                    },
                    success: data => {
                        let resp = {};
                        data.forEach(function (item, i) {
                            resp[i] = {};
                            resp[i].label = item.codice + ' - ' + item.descrizione;
                            resp[i].value = item.descrizione;
                            resp[i].id = item.id;
                            resp[i].code = item.codice;
                            resp[i].desc = item.descrizione;
                        });
                        response(resp);
                    }
                });
            },
            minLength: 3,
            select: (event, ui) =>  {
				let label = ui.item.code + ' - ' + ui.item.desc;

        		$('input[name="collocazione"]').val(ui.item.id);
        		$('input[name="collocazioneLabel"]').val(label);

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
			            url: '/configuratore/ajax/solSelezionabili',
			            dataType: 'json',
			            data: {collocazione: coll,
			                   ruolo: role
			            },
			            method: 'POST',
			            traditional: true,
			            success: data => {
					    	let solOptions = data.filter((e,i) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
					    	$('select[name=sol]').find('option').filter((i,e) => $(e).val() != '').remove();
					    	$('select[name=sol]').append(solOptions).focus(e => $(e.target).data('previous-value', $(e.target).val()));
			    			$('select[name=sol]').prop('disabled', false);
			    			hideLoading();
						}
		        	});
				}
			}
        });
	})
	
	
	/**
	 * Alla selezione di un ruolo, se e' stato selezionata un'azienda o una collocazione', inizializzo e riempio la select dei SOL.
	 */
	$('#ruolo').change(e => {
		e.preventDefault();
		if($('#azienda').val() != '' && $('#azienda').val() != null) {
			$('select[name=sol]').prop('disabled', true);
			$('select[name=sol]').val('');
			let coll = $('input[name="collocazione"]').val() != '' && $('input[name="collocazione"]').val() != null ? 
				$('input[name="collocazione"]').val() : $('#azienda').val();
			let role = $('#ruolo').val();
			showLoading();
			$.ajax({
	            url: '/configuratore/ajax/solSelezionabili',
	            dataType: 'json',
	            data: {collocazione: coll,
	                   ruolo: role
	            },
	            method: 'POST',
	            traditional: true,
	            success: data => {
			    	let solOptions = data.filter((e,i) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
			    	$('select[name=sol]').find('option').filter((i,e) => $(e).val() != '').remove();
	    			$('select[name=sol]').append(solOptions).focus(e => $(e.target).data('previous-value', $(e.target).val()));
	    			$('select[name=sol]').prop('disabled', false);
			    	hideLoading();
				}
	    	});
	    }
	})

    $('#pageLinkBox a').click(e => {
        e.preventDefault();
        let numElementi = $(e.target).data('elementi');
        $('input[name="numeroElementi"]').val(numElementi);
        $('input[name="numeroPagina"]').val(1);
	    $('#searchForm').submit();
    });

    $('#pageNavigation a[data-pagina]').click(e => {
        e.preventDefault();
        let numPagina = $(e.target).data('pagina');
        $('input[name="numeroPagina"]').val(numPagina);
        $('#searchForm').submit();
    })
     
	$('#selectAll').click(e => {
		e.preventDefault();
		$('input[name="allSelected"]').val(true);
		$(e.target).prop('hidden', true);
		$('#deselectAll').prop('hidden', false);
		$('input[name="checkUtente"]').prop('checked', true);

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
		
	})
	$('#deselectAll').click(e => {
		e.preventDefault();
		$('input[name="allSelected"]').val(false);
		$(e.target).prop('hidden', true);
		$('#selectAll').prop('hidden', false);
		$('input[name="checkUtente"]').prop('checked', false);
		$('input[name="selected"]').val([]);

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
		
	})
	
	if($('input[name="allSelected"]').val() === 'true') {
		$('#selectAll').prop('hidden', true);
		$('#deselectAll').prop('hidden', false);
		$('input[name="checkUtente"]').prop('checked', true);
	}
});

$(function() {
	let selectableSols;
	$("#dialog").dialog({
	   autoOpen: false,
	   modal: true
	 });

	 $("#storico").dialog({
		autoOpen: false,
		modal: true
	  });

	  $('#btnStorico').click(e=>{
		e.preventDefault();
		showLoading();
		$.ajax({
			url: '/configuratore/ajax/storico',
			dataType: 'json',
			data: {abilitazione: false
				   },
			method: 'POST',
			traditional: true,
			success: data => {

							
				let storico = data.map(e =>
				"<tr> <input type='hidden' name='idBatch' value="+e.id+" />"+
				"<td>"+e.stato+"</td>"+
				"<td>"+e.dataInserimento+"</td>"+
				"<td>"+e.progresso+"</td>"+
				"<td>"+e.sol+"</td>"+
				"<td>"+e.profilo+"</td>"+
				"<td>"+ (e.download ? "-" : "<a href='/configuratore/downloadCsvAbilitati?id="+e.id+"&abilita=true'>Scarica</a>") +"</td>"+
				"<td>"+ (e.download ? "-" : "<a href='/configuratore/downloadCsvNonAbilitati?id="+e.id+"&abilita=true'>Scarica</a>") +"</td>");
				$('#tableStorico').html('').append(storico);
				hideLoading();
				$("#storico").dialog("open");
			}
		});
	  });

	  $('#btnAggiorna').click(e=>{
		e.preventDefault();
		$("#storico").dialog("close");
		showLoading();
		$.ajax({
			url: '/configuratore/ajax/storico',
			dataType: 'json',
			data: {abilitazione: false
				   },
			method: 'POST',
			traditional: true,
			success: data => {

							
				let storico = data.map(e =>
				"<tr> <input type='hidden' name='idBatch' value="+e.id+" />"+
				"<td>"+e.stato+"</td>"+
				"<td>"+e.dataInserimento+"</td>"+
				"<td>"+e.progresso+"</td>"+
				"<td>"+e.sol+"</td>"+
				"<td>"+e.profilo+"</td>"+
				"<td>"+ (e.download ? "-" : "<a href='/configuratore/downloadCsvAbilitati?id="+e.id+"&abilita=true'>Scarica</a>") +"</td>"+
				"<td>"+ (e.download ? "-" : "<a href='/configuratore/downloadCsvNonAbilitati?id="+e.id+"&abilita=true'>Scarica</a>") +"</td>");
				$('#tableStorico').html('').append(storico);
				hideLoading();
				$("#storico").dialog("open");
			}
		});
	  });
  
	$("#btnInserimentoMassivo").on("click", function(e) {

		if($('input[name=checkElaborazione]').val()=='true'){

			bootbox.alert({
				title: '<b>' + 'Attenzione!' + '</b>',
				message: '<div role="alert" class="alert alert-heading ' + 'alert-warning' + '">' + 'Elaborazioni gi&agrave presenti.' + '</div>',
				size: 'large',
				centerVertical: true
			});

		}else{
			e.preventDefault();
			showLoading();
			
			let coll = $('input[name="collocazione"]').val() != '' && $('input[name="collocazione"]').val() != null ? 
					$('input[name="collocazione"]').val() : $('#azienda').val();
			let role = $('#ruolo').val();
			
			$.ajax({
	            url: '/configuratore/ajax/solSelezionabili',
	            dataType: 'json',
	            data: {
					collocazione: coll,
	                ruolo: role
	            },
	            method: 'POST',
	            traditional: true,
	            success: data => {
					let solOptions = data.filter((e,i) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
					$('select[name=solSelezionabili]').find('option').filter((i,e) => $(e).val() != '').remove();
					$('select[name=solSelezionabili]').append(solOptions).focus(e => $(e.target).data('previous-value', $(e.target).val()));
					$('select[name=solSelezionabili]').prop('disabled', false);
					selectableSols=data;
					hideLoading();
					$("#dialog").dialog("open");
				}
	        });
			
			/*let coll = $('#azienda').val();
		 	$.ajax({
				url: '/configuratore/ajax/solSelezionabiliPerAbilitazione',
				dataType: 'json',
				data: {collocazione: coll
					   },
				method: 'POST',
				traditional: true,
				success: data => {
					let solOptions = data.filter((i,e) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
					$('select[name=solSelezionabili]').find('option').filter((i,e) => $(e).val() != '').remove();
					$('select[name=solSelezionabili]').append(solOptions).focus(e => $(e.target).data('previous-value', $(e.target).val()));
					$('select[name=solSelezionabili]').prop('disabled', false);
					selectableSols=data;
					hideLoading();
					$("#dialog").dialog("open");
				}
			});*/
	
		}
		
	});

	$("#solSelezionabili").change(e=>{
		let solId = parseInt($("#solSelezionabili").val());
		let sol = selectableSols.filter(e => e.id === solId)[0];
		if (sol==undefined){
			$("#profili").empty().append("<option value='' disabled='disabled' selected>" + "Seleziona un profilo*" + "</option>");
		}else{
			let profilesOptions = sol.profili.map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>");
			$("#profili").empty().append("<option value='' disabled='disabled' selected>" + "Seleziona un profilo*" + "</option>").append(profilesOptions).prop('disabled',false);

		}
	}

	);

	$("#modal_dialog").dialog({
		autoOpen: false,
		modal: true
	});
	$("#btnAbilitaMassivo").on("click", function () {
			
			

			let message="Sei sicuro di voler procedere con l'abilitazione massiva?"; 
			$('.title').html(message);
			var dialog = $('#modal_dialog').dialog('open');
		  
			$('#btnYes').click(e=>{
				e.preventDefault();
				$('#abilitazioneForm').submit();
				dialog.dialog('close');
				$("#dialog").dialog("close");
				$("#profili").empty().prop('disabled',true);
	
			});
			$('#btnNo').click(function() {
				dialog.dialog('close');
			});
			
	        let showMsg = false;
			$('#solSelezionabili').each(function () {
	            let solSelezionabili = $(this).val();
	            
		        if(solSelezionabili == null || solSelezionabili == '' || solSelezionabili.trim().length === 0) {
					showMsg = true;
				}
	        });
	        if(showMsg) {
	            showAlert("Il SOL &egrave; obbligatorio", 'Campo obbligatorio', 'error');
	            dialog.dialog('close');
			} else {
			
				$('#profili').each(function () {
		            let profili = $(this).val();
		            
			        if(profili == null || profili == '' || profili.trim().length === 0) {
						showMsg = true;
					}
		        });
		        if(showMsg) {
		            showAlert("Il profilo &egrave; obbligatorio", 'Campo obbligatorio', 'error');
		            dialog.dialog('close');
				} else {
				
					$('input[name=dataFineValidita]').each(function () {
			            let dataFineValidita = $(this).val();
			            
				        if(dataFineValidita == null || dataFineValidita == '' || dataFineValidita.trim().length === 0) {
							showMsg = true;
						}
			        });
			        if(showMsg) {
			            showAlert("La data fine validit&agrave; &egrave; obbligatoria", 'Campo obbligatorio', 'error');
			            dialog.dialog('close');
					}
				}
			}

		
		
	  });
  
  


  
  });
  
  /**
	 * Mostra un alert, come modale bootstrap.
	 *
	 * @param msg il messaggio da visualizzare.
	 * @param title il titolo del modal. Se null, il titolo viene dedotto dal tipo del modale.
	 * @param type il tipo del modale. Puo' essere: info - warning - error
	 */
	function showAlert(msg, title, type) {
		let cssClass;
		if (type === 'warning') {
			cssClass = 'alert-warning';
			title = !title ? 'Attenzione!' : title;
		} else if (type === 'error') {
			cssClass = 'alert-danger';
			title = !title ? 'Errore!' : title;
		} else {
			cssClass = 'alert-info';
			title = !title ? 'Informazione' : title;
		}

		bootbox.alert({
			title: '<b>' + title + '</b>',
			message: '<div role="alert" class="alert alert-heading ' + cssClass + '">' + msg + '</div>',
			size: 'large',
			centerVertical: true
		});
	}




  


	

