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
					width: 110,
					textAlign: 'left',
					columnSpacing: 5,
					template: function(data) {
						
						return '<span class="font-weight-normal">' + data.customer.customerName + '</span>';
					}
				}, {
					field: 'mobileNo',
					title: 'Mobile No',
					width: 80,
					textAlign: 'center',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.customer.mobileNo + '</span>';
					}
				}, {
					field: 'alternateNo',
					title: 'Alternate No',
					width: 80,
					textAlign: 'center',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.customer.alternateNo + '</span>';
					}
				}, {
					field: 'pendingInvoiceAmount',
					title: 'Pending Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.pendingInvoiceAmount + '</span>';
					}
				}, {
					field: 'activeInvoiceAmount',
					title: 'Current Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.activeInvoiceAmount + '</span>';
					}
				}, {
					field: 'totalInvoiceAmount',
					title: 'Total Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.totalInvoiceAmount + '</span>';
					}
				}, {
					field: 'paymentAmount',
					title: 'Paid Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.paymentAmount + '</span>';
					}
				}, {
					field: 'balanceAmount',
					title: 'Balance Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-bold text-danger">' + data.balanceAmount + '</span>';
					}
				}, {
					field: 'rentedDate',
					title: 'Rent Date',
					width: 70,
					textAlign: 'center',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.rentedDate + '</span>';
					}
				}, {
					field: 'Actions',
					title: 'Actions',
					sortable: false,
					width: 200,
					overflow: 'visible',
					autoHide: false,
					template: function(data, row) {
						var payment = JSON.stringify(data.paymentSet);
						payment = payment.replaceAll("\"", "\'");
						
						return '\<a href="javascript:;" class="btn btn-sm btn-icon btn-light btn-color-muted btn-hover-success me-2 viewReceiptModal" data-id="'+ data.rentalId +'" data-toggle="modal" data-target="#viewInvoiceReceiptModal" title="Print Receipt">\
									<span class="svg-icon svg-icon-primary svg-icon-2x"><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/Devices/Printer.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
								    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
								        <rect x="0" y="0" width="24" height="24"/>\
								        <path d="M16,17 L16,21 C16,21.5522847 15.5522847,22 15,22 L9,22 C8.44771525,22 8,21.5522847 8,21 L8,17 L5,17 C3.8954305,17 3,16.1045695 3,15 L3,8 C3,6.8954305 3.8954305,6 5,6 L19,6 C20.1045695,6 21,6.8954305 21,8 L21,15 C21,16.1045695 20.1045695,17 19,17 L16,17 Z M17.5,11 C18.3284271,11 19,10.3284271 19,9.5 C19,8.67157288 18.3284271,8 17.5,8 C16.6715729,8 16,8.67157288 16,9.5 C16,10.3284271 16.6715729,11 17.5,11 Z M10,14 L10,20 L14,20 L14,14 L10,14 Z" fill="#000000"/>\
								        <rect fill="#000000" opacity="0.3" x="8" y="2" width="8" height="2" rx="1"/>\
								    </g>\
								</svg><!--end::Svg Icon--></span>\
							</a>\
							\<a href="javascript:;" class="btn btn-sm btn-icon btn-light btn-color-muted btn-hover-success me-2 viewInvoiceModal" data-id="'+ data.rentalId +'" data-toggle="modal" data-target="#viewInvoiceReceiptModal" title="View Invoice">\
								<span class="svg-icon svg-icon-primary svg-icon-2x"><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/Files/File.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
							    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
							        <polygon points="0 0 24 0 24 24 0 24"/>\
							        <path d="M5.85714286,2 L13.7364114,2 C14.0910962,2 14.4343066,2.12568431 14.7051108,2.35473959 L19.4686994,6.3839416 C19.8056532,6.66894833 20,7.08787823 20,7.52920201 L20,20.0833333 C20,21.8738751 19.9795521,22 18.1428571,22 L5.85714286,22 C4.02044787,22 4,21.8738751 4,20.0833333 L4,3.91666667 C4,2.12612489 4.02044787,2 5.85714286,2 Z" fill="#000000" fill-rule="nonzero" opacity="0.3"/>\
							        <rect fill="#000000" x="6" y="11" width="9" height="2" rx="1"/>\
							        <rect fill="#000000" x="6" y="15" width="5" height="2" rx="1"/>\
							    </g>\
							</svg><!--end::Svg Icon--></span>\
							</a>\
							\<a href="javascript:;" class="btn btn-sm btn-icon btn-light btn-color-muted btn-hover-success me-2 paymentHistoryModal" data-toggle="modal"  data-target="#paymentHistoryModal"  data-id="' + payment + '" title="View Payments">\
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
							<a href="javascript:;" class="btn btn-sm btn-default btn-text-primary btn-hover-success btn-icon mr-2 viewItemsModal" data-toggle="modal" data-target="#viewItemsModal" title="Edit Contact">\
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
	$("#paymentHistoryTable tr>td").remove();
	
	var object = $(this).data('id');

	if(object != undefined && object != "")
	{
		object = JSON.parse(object.replaceAll("\'", "\""));
		
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

jQuery(document).on("click", ".viewReceiptModal", function () {
	
	var rentalId = $(this).data('id');

	if(rentalId != undefined && rentalId != "")
	{
		 $.ajax({
             url: 'gaya/viewRentalReceipt/'+ rentalId,
             type: "POST",
             success: function (response) {
            	$('.modal-body').html(response);
            	$('.invoiceReceipt').html("View & Print Receipt");
            	
             },
             error: function (error) {
                 console.log(`Error ${error}`);
             }
		 });
	}
});

jQuery(document).on("click", ".viewInvoiceModal", function () {
	
	var rentalId = $(this).data('id');

	if(rentalId != undefined && rentalId != "")
	{
		 $.ajax({
             url: 'gaya/viewRentalInvoice/'+ rentalId,
             type: "POST",
             success: function (response) {
            	$('.modal-body').html(response);
            	$('.invoiceReceipt').html("View & Print Invoice");
            	
             },
             error: function (error) {
                 console.log(`Error ${error}`);
             }
		 });
	}
});

function printDiv(eltId, file_name) {
	var element =  document.getElementById(eltId);
	var opt = {
	  margin:       0.5,
	  filename:     file_name,
	  image:        { type: 'jpeg', quality: 0.98 },
	  html2canvas:  { scale:3 },
	  jsPDF:        { unit: 'in', format: 'letter', orientation: 'portrait' }
	};
	 
	// New Promise-based usage:
	html2pdf().from(element).set(opt).save();
	 
}