$(() => {
	
    $('#btnRicercaMassiva').click(e => {
		if($('select[name="azienda"]').val() != null && $('select[name="azienda"]').val() != ''
			&& $('select[name="sol"]').val() != null && $('select[name="sol"]').val() != '') {
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
		
		
			$('select[name=sol]').prop('disabled', true);
			$('select[name=sol]').val('');
			let coll = $('input[name="collocazione"]').val() != '' && $('input[name="collocazione"]').val() != null ? 
				$('input[name="collocazione"]').val() : $('#azienda').val();
			
			showLoading();
			$.ajax({
	            url: '/configuratore/ajax/solSelezionabiliPerAbilitazione',
	            dataType: 'json',
	            data: {collocazione: coll
	                   
	            },
	            method: 'POST',
	            traditional: true,
	            success: data => {
			    	let solOptions = data.filter((i,e) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
			    	$('select[name=sol]').find('option').filter((i,e) => $(e).val() != '').remove();
			    	$('select[name=sol]').append(solOptions).focus(e => $(e.target).data('previous-value', $(e.target).val()));
	    			$('select[name=sol]').prop('disabled', false);
		        	hideLoading();
				}
        	});
		
		
		$('input[name="collocazione"]').val('');
        $('input[name="collocazioneLabel"]').val('');
		$('div.sede-search-box').show();
        $('div.collocation-search-box').show();
        $('div.result-box').removeClass('d-flex').addClass('d-none');
        $('input.coll-code-input').val('');
        $('input.coll-desc-input').val('');
        
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
			            url: '/configuratore/ajax/solSelezionabiliPerAbilitazione',
			            dataType: 'json',
			            data: {collocazione: coll
			                   
			            },
			            method: 'POST',
			            traditional: true,
			            success: data => {
					    	let solOptions = data.filter((i,e) => e.codice != 'SOLCONFIG').map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
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
        		
			}
        });
	})
	
	
	/**
	 * Alla selezione di un ruolo, se e' stato selezionata un'azienda o una collocazione', inizializzo e riempio la select dei SOL.
	 */
	$('#ruolo').change(e => {
		
	})

    $('#pageLinkBox a').click(e => {
        e.preventDefault();
        let numElementi = $(e.target).data('elementi');
        $('input[name="numeroElementi"]').val(numElementi);
        $('input[name="numeroPagina"]').val(1);
        $('#searchForm').submit();
    })

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
	
	$("#modal_dialog").dialog({
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
			data: {abilitazione: true
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
				"<td>"+ (e.download ? "-" : "<a href='/configuratore/downloadCsvAbilitati?id="+e.id+"&abilita=false'>Scarica</a>") +"</td>"+
				"<td>"+ (e.download ? "-" : "<a href='/configuratore/downloadCsvNonAbilitati?id="+e.id+"&abilita=false'>Scarica</a>") +"</td>");
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
			data: {abilitazione: true
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
				"<td>"+ (e.download ? "-" : "<a href='/configuratore/downloadCsvAbilitati?id="+e.id+"&abilita=false'>Scarica</a>") +"</td>"+
				"<td>"+ (e.download ? "-" : "<a href='/configuratore/downloadCsvNonAbilitati?id="+e.id+"&abilita=false'>Scarica</a>") +"</td>");
				$('#tableStorico').html('').append(storico);
				hideLoading();
				$("#storico").dialog("open");
			}
		});
	  });

	$("#btnInserimentoMassivo").on("click", function () {

		if($('input[name=checkElaborazione]').val()=='true'){

			bootbox.alert({
			title: '<b>' + 'Attenzione!' + '</b>',
			message: '<div role="alert" class="alert alert-heading ' + 'alert-warning' + '">' + 'Elaborazioni gi&agrave presenti.' + '</div>',
			size: 'large',
			centerVertical: true
		});
		

	}else{
		
		let message="Sei sicuro di voler procedere con la disabilitazione massiva?"; 
		$('.title').html(message);
		var dialog = $('#modal_dialog').dialog('open');
		
		$('#btnYes').click(e=>{
			e.preventDefault();
			$('#abilitazioneForm').submit();
			$("#dialog").dialog("close");
			$("#profili").empty().prop('disabled',true);
			dialog.dialog('close');
		});
	  
			$('#btnNo').click(function() {
			dialog.dialog('close');
			
			
		});

	}
	  });
  
	
	 

  
  });

 


  


	

