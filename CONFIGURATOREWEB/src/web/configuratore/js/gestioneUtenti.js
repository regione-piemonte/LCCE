$(() => {
    $('#insertUtenteSubmitBtn').click(() => {
        $('#formInserisciUtente input[name=\'cf\']').val($('#cf').val())
        $('#formInserisciUtente').submit()
    });

    $('#modificaUtenteSubmitBtn').click(() => {
		showLoading();
        $('#formModificaUtente input[name=\'cf\']').val($('#cf').val())
        $('#formModificaUtente').submit()
    });

    $('#pageLinkBox a').click(e => {
        e.preventDefault();
        showLoading();
        let numElementi = $(e.target).data('elementi');
        $('input[name="numeroElementi"]').val(numElementi);
        $('input[name="numeroPagina"]').val(1);
        $('#searchForm').submit();
    })

    $('#pageNavigation a[data-pagina]').click(e => {
        e.preventDefault();
        showLoading();
        let numPagina = $(e.target).data('pagina');
        $('input[name="numeroPagina"]').val(numPagina);
        $('#searchForm').submit();
    })
    
    $('#buttonRicerca').click(e => {
		showLoading();
	})
});