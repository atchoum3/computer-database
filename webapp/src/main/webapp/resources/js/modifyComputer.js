let inputDiscontinued = $("#discontinued")
let inputIntroduced = $("#introduced")
let submitBtn = $("#submitBtn")

// add listener
inputDiscontinued.change(function() {
	checkDiscontinuedIfNotIntroduced()
});

inputIntroduced.change(function() {
	checkDiscontinuedIfNotIntroduced()
});


function checkDiscontinuedIfNotIntroduced() {
	let errorTag = $("#errorDiscontinued")
	console.log(inputIntroduced.val())
	if (inputIntroduced.val() === "" &&  inputDiscontinued.val() !== "") {
		errorTag.html("The introduced date cannot be empty if the discontinued is it.")
		errorTag.addClass("alert alert-danger")
	} else {
		errorTag.removeClass()
		errorTag.html("")
	}
}

