"use strict";
// Class definition

var KTAppsRentalListDatatable = function() {
	// Private functions
	
	// basic demo
	var _demo = function() {
		var datatable = $('#kt_datatable').KTDatatable({
			// datasource definition
			data: {
				type: 'remote',
				source: {
					read: {
						url: '/gaya/searchRental',
					},
				},
				pageSize: 10, // display 20 records per page
				serverPaging: true,
				serverFiltering: true,
				serverSorting: true,
			},

			// layout definition
			layout: {
				scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
				footer: false, // display/hide footer
			},

			// column sorting
			sortable: true,

			pagination: true,

			search: {
				input: $('#kt_subheader_search_form'),
				delay: 1000,
				key: 'generalSearch'
			},

			// columns definition
			columns: [
				{
					field: '',
					title: 'Sno',
					sortable: 'asc',
					width: 30,
					type: 'number',
					selector: false,
					textAlign: 'center',
					template: function(data, row) {
						return '<input type="hidden"  id="payment'+row+'" value="'+ JSON.stringify(data.paymentSet) +'"> <span class="font-weight-sm">' + (row + 1) + '</span>';
					}
				}, {
					field: 'customerName',
					title: 'Customer Name',
					width: 120,
					textAlign: 'left',
					template: function(data) {
						
						return '<span class="">' + data.customer.customerName + '</span>';
					}
				}, {
					field: 'mobileNo',
					title: 'Mobile No',
					width: 80,
					textAlign: 'center',
					template: function(data) {
						return '<span class="">' + data.customer.mobileNo + '</span>';
					}
				}, {
					field: 'alternateNo',
					title: 'Alternate No',
					width: 80,
					textAlign: 'center',
					template: function(data) {
						return '<span class="">' + data.customer.alternateNo + '</span>';
					}
				}, {
					field: 'pendingInvoiceAmount',
					title: 'Pending Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="">' + data.pendingInvoiceAmount + '</span>';
					}
				}, {
					field: 'activeInvoiceAmount',
					title: 'Current Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="">' + data.activeInvoiceAmount + '</span>';
					}
				}, {
					field: 'totalInvoiceAmount',
					title: 'Total Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="">' + data.totalInvoiceAmount + '</span>';
					}
				}, {
					field: 'paymentAmount',
					title: 'Paid Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="">' + data.paymentAmount + '</span>';
					}
				}, {
					field: 'balanceAmount',
					title: 'Balance Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="label label-lg label-light-danger label-inline">' + data.balanceAmount + '</span>';
					}
				}, {
					field: 'rentedDate',
					title: 'Rent Date',
					width: 70,
					textAlign: 'center',
					template: function(data) {
						return '<span class="">' + data.rentedDate + '</span>';
					}
				}, {
					field: 'rentalStatus',
					title: 'Status',
					width: 60,
					textAlign: 'center',
					template: function(data) {
						return '<span class="label label-lg label-light-success label-inline text-success">' + data.rentalStatus + '</span>';
					}
				}, {
					field: 'Actions',
					title: 'Actions',
					sortable: false,
					width: 150,
					overflow: 'visible',
					autoHide: false,
					template: function(data, row) {
						var payment = JSON.stringify(data.paymentSet);
						payment = payment.replaceAll("\"", "\'");
						
						return '\<a href="javascript:;" class="btn btn-sm btn-icon btn-light btn-color-muted btn-hover-success me-2 paymentHistoryModal" data-toggle="modal"  data-target="#paymentHistoryModal"  data-id="' + payment + '" title="View Payments">\
									<span class="svg-icon svg-icon-primary svg-icon-2x"><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/Communication/Dial-numbers.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
								    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
								        <rect x="0" y="0" width="24" height="24"/>\
								        <rect fill="#000000" opacity="0.3" x="4" y="4" width="4" height="4" rx="2"/>\
								        <rect fill="#000000" x="4" y="10" width="4" height="4" rx="2"/>\
								        <rect fill="#000000" x="10" y="4" width="4" height="4" rx="2"/>\
								        <rect fill="#000000" x="10" y="10" width="4" height="4" rx="2"/>\
								        <rect fill="#000000" x="16" y="4" width="4" height="4" rx="2"/>\
								        <rect fill="#000000" x="16" y="10" width="4" height="4" rx="2"/>\
								        <rect fill="#000000" x="4" y="16" width="4" height="4" rx="2"/>\
								        <rect fill="#000000" x="10" y="16" width="4" height="4" rx="2"/>\
								        <rect fill="#000000" x="16" y="16" width="4" height="4" rx="2"/>\
								    </g>\
								</svg><!--end::Svg Icon--></span>\
							</a>\
							\<a href="javascript:;" class="btn btn-sm btn-icon btn-light btn-color-muted btn-hover-success me-2" data-toggle="modal" data-target="#viewInvoiceModal" title="View Invoice">\
								<span class="svg-icon svg-icon-primary svg-icon-2x"><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/Files/Import.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
							    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
							        <rect x="0" y="0" width="24" height="24"/>\
							        <rect fill="#000000" opacity="0.3" transform="translate(12.000000, 7.000000) rotate(-180.000000) translate(-12.000000, -7.000000) " x="11" y="1" width="2" height="12" rx="1"/>\
							        <path d="M17,8 C16.4477153,8 16,7.55228475 16,7 C16,6.44771525 16.4477153,6 17,6 L18,6 C20.209139,6 22,7.790861 22,10 L22,18 C22,20.209139 20.209139,22 18,22 L6,22 C3.790861,22 2,20.209139 2,18 L2,9.99305689 C2,7.7839179 3.790861,5.99305689 6,5.99305689 L7.00000482,5.99305689 C7.55228957,5.99305689 8.00000482,6.44077214 8.00000482,6.99305689 C8.00000482,7.54534164 7.55228957,7.99305689 7.00000482,7.99305689 L6,7.99305689 C4.8954305,7.99305689 4,8.88848739 4,9.99305689 L4,18 C4,19.1045695 4.8954305,20 6,20 L18,20 C19.1045695,20 20,19.1045695 20,18 L20,10 C20,8.8954305 19.1045695,8 18,8 L17,8 Z" fill="#000000" fill-rule="nonzero" opacity="0.3"/>\
							        <path d="M14.2928932,10.2928932 C14.6834175,9.90236893 15.3165825,9.90236893 15.7071068,10.2928932 C16.0976311,10.6834175 16.0976311,11.3165825 15.7071068,11.7071068 L12.7071068,14.7071068 C12.3165825,15.0976311 11.6834175,15.0976311 11.2928932,14.7071068 L8.29289322,11.7071068 C7.90236893,11.3165825 7.90236893,10.6834175 8.29289322,10.2928932 C8.68341751,9.90236893 9.31658249,9.90236893 9.70710678,10.2928932 L12,12.5857864 L14.2928932,10.2928932 Z" fill="#000000" fill-rule="nonzero"/>\
							    </g>\
							</svg><!--end::Svg Icon--></span>\
							</a>\
							<a href="javascript:;" class="btn btn-sm btn-default btn-text-primary btn-hover-success btn-icon mr-2" data-toggle="modal" data-target="#viewItemsModal" title="Edit Contact">\
								<span class="svg-icon svg-icon-primary svg-icon-2x"><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/Design/Edit.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
							    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
							        <rect x="0" y="0" width="24" height="24"/>\
							        <path d="M8,17.9148182 L8,5.96685884 C8,5.56391781 8.16211443,5.17792052 8.44982609,4.89581508 L10.965708,2.42895648 C11.5426798,1.86322723 12.4640974,1.85620921 13.0496196,2.41308426 L15.5337377,4.77566479 C15.8314604,5.0588212 16,5.45170806 16,5.86258077 L16,17.9148182 C16,18.7432453 15.3284271,19.4148182 14.5,19.4148182 L9.5,19.4148182 C8.67157288,19.4148182 8,18.7432453 8,17.9148182 Z" fill="#000000" fill-rule="nonzero" transform="translate(12.000000, 10.707409) rotate(-135.000000) translate(-12.000000, -10.707409) "/>\
							        <rect fill="#000000" opacity="0.3" x="5" y="20" width="15" height="2" rx="1"/>\
							    </g>\
								</svg><!--end::Svg Icon--></span>\
		                    </a>\
	                        <a href="javascript:;" class="btn btn-sm btn-default btn-text-danger btn-hover-danger btn-icon" title="Delete">\
								<span class="svg-icon svg-icon-md">\
									<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
										<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
											<rect x="0" y="0" width="24" height="24"/>\
											<path d="M6,8 L6,20.5 C6,21.3284271 6.67157288,22 7.5,22 L16.5,22 C17.3284271,22 18,21.3284271 18,20.5 L18,8 L6,8 Z" fill="#000000" fill-rule="nonzero"/>\
											<path d="M14,4.5 L14,4 C14,3.44771525 13.5522847,3 13,3 L11,3 C10.4477153,3 10,3.44771525 10,4 L10,4.5 L5.5,4.5 C5.22385763,4.5 5,4.72385763 5,5 L5,5.5 C5,5.77614237 5.22385763,6 5.5,6 L18.5,6 C18.7761424,6 19,5.77614237 19,5.5 L19,5 C19,4.72385763 18.7761424,4.5 18.5,4.5 L14,4.5 Z" fill="#000000" opacity="0.3"/>\
										</g>\
									</svg>\
								</span>\
	                        </a>\
	                    ';
					},
				}],
		});

		$('#kt_datatable_search_status').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'Status');
		});

		$('#kt_datatable_search_type').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'Type');
		});

		$('#kt_datatable_search_status, #kt_datatable_search_type').selectpicker();
	};

	return {
		// public functions
		init: function() {
			_demo();
		},
	};
}();

jQuery(document).ready(function() {
	KTAppsRentalListDatatable.init();
});

jQuery(document).on("click", ".paymentHistoryModal", function () {
	var object = $(this).data('id');

	if(object != undefined && object != "")
	{
		object = JSON.parse(object.replaceAll("\'", "\""));
		$("#paymentHistoryTable tr>td").remove();
	    $.each(object, function(index, jsonObject){     
	        if(Object.keys(jsonObject).length > 0){
	          var tableRow = '<tr>';
	          $.each(Object.keys(jsonObject), function(i, key){
	        	 if(i == 0)
	        		 tableRow += '<td>' + (index+1) + '</td>';
	        	 else
	        		 tableRow += '<td>' + jsonObject[key] + '</td>';
	        	 	 
	          });
	          tableRow += "</tr>";
	          $('#paymentHistoryTable tbody').append(tableRow);
	        }
    	});
	}
	
});