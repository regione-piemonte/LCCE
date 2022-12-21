$(() => {
    $('#pageLinkBox a').click(e => {
        showLoading();
        e.preventDefault();
        let numElementi = $(e.target).data('elementi');
        $('input[name="numeroElementi"]').val(numElementi);
        $('input[name="numeroPagina"]').val(1);
        $('input[name="allSelected"]').val(false);
        $('input[name="selected"]').val([]);
        $('#searchForm').submit();
         })

    $('#pageNavigation a[data-pagina]').click(e => {
        showLoading();
        e.preventDefault();
        let numPagina = $(e.target).data('pagina');
        $('input[name="numeroPagina"]').val(numPagina);
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
        $('#searchForm').submit();
    })

	$('#go-to').click(e => {
		showLoading();
        e.preventDefault();
        let numPagina = $('input[name="goToPage"]').val();
        if(!isNaN(numPagina)) {
	        $('input[name="numeroPagina"]').val(numPagina);
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
	        $('#searchForm').submit();
		} else {		
			hideLoading();
		}
	})
});