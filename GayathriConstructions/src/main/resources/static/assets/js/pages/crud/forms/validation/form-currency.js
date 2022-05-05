// Class definition
var KTFormControls = function () {
	// Private functions
	var __currencyForm = function () {
		FormValidation.formValidation(
			document.getElementById('paymentForm'),
			{
				fields: {
					digits: {
						validators: {
							notEmpty: {
								message: 'Digits is required'
							},
							digits: {
								message: 'The velue is not a valid digits'
							}
						}
					},
				},

				plugins: { //Learn more: https://formvalidation.io/guide/plugins
					trigger: new FormValidation.plugins.Trigger(),
					// Bootstrap Framework Integration
					bootstrap: new FormValidation.plugins.Bootstrap(),
					// Validate fields when clicking the Submit button
					submitButton: new FormValidation.plugins.SubmitButton(),
            		// Submit the form when all fields are valid
            		defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
				}
			}
		);
	}

	return {
		// public functions
		init: function() {
			__currencyForm();
		}
	};
}();

jQuery(document).ready(function() {
	KTFormControls.init();
});
