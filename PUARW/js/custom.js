jQuery(document).ready(function($) {

  $('#myModal').on('shown.bs.modal', function () {
    $('#myInput').trigger('focus')
  })


}); //ready


$('#myTab a').on('click', function (e) {
  e.preventDefault();
  if($(this).hasClass('show') && $(this).hasClass('active')) {
	$(this).removeClass('active');
	$(this).removeClass('show');
	//console.log($(this).attr('href'));
	$($(this).attr('href')).removeClass('active');
	$($(this).attr('href')).removeClass('show');
	return false;
  };
})