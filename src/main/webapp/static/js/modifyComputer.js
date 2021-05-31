let inputName = $("#computerName")

inputName.on('input', function(evt) {
	checkEmptyName();
})




function checkEmptyName(evt) {
	if (inputName.val()) {
		inputName.after('<div>Name cannot be empty</div>').addClass('alert alert-danger');
	}
}