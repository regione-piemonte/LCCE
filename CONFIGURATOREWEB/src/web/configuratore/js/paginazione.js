$(() => {
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
});