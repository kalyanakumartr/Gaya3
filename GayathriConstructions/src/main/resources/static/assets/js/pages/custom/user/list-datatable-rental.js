"use strict";
// Class definition
var datatable ;
var KTAppsRentalListDatatable = function() {
	// Private functions
	
	// basic demo
	var _demo = function() {
		datatable = $('#kt_datatable').KTDatatable({
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
					width: 100,
					textAlign: 'left',
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
					field: 'payableInvoiceAmount',
					title: 'Payable Invoices Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.payableInvoiceAmount + '</span>';
					}
				}, {
					field: 'activeInvoiceAmount',
					title: 'Current Invoice Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.activeInvoiceAmount + '</span>';
					}
				}, {
					field: 'totalInvoiceAmount',
					title: 'Overall Invoice Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.totalInvoiceAmount + '</span>';
					}
				}, {
					field: 'paymentAmount',
					title: 'So Far Paid Amount',
					width: 65,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-normal">' + data.paymentAmount + '</span>';
					}
				}, {
					field: 'balanceAmount',
					title: 'Balance Invoice Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-bold text-danger">' + data.balanceAmount + '</span>';
					}
				}, {
					field: 'rentalId',
					title: 'Pay',
					width: 35,
					textAlign: 'center',
					template: function(data, row) {
						var payment = '{"customerName":"'+ data.customer.customerName +'", "rentalId":"'+ data.rentalId +'", "balanceAmount":"'+ data.balanceAmount.replaceAll("&#x20B9; ", "")+'"}';
						payment = payment.replaceAll("\"", "\'");
						return '\<a href="javascript:;" class="btn btn-sm btn-icon btn-light btn-color-muted btn-hover-success me-2 paymentModal" data-id="'+ payment +'" data-toggle="modal" data-target="#paymentModal" title="Print Receipt">\
									<span class="svg-icon svg-icon-primary svg-icon-2x"><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/Shopping/Dollar.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
								    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
								        <rect x="0" y="0" width="24" height="24"/>\
								        <rect fill="#000000" opacity="0.3" x="11.5" y="2" width="2" height="4" rx="1"/>\
								        <rect fill="#000000" opacity="0.3" x="11.5" y="16" width="2" height="5" rx="1"/>\
								        <path d="M15.493,8.044 C15.2143319,7.68933156 14.8501689,7.40750104 14.4005,7.1985 C13.9508311,6.98949895 13.5170021,6.885 13.099,6.885 C12.8836656,6.885 12.6651678,6.90399981 12.4435,6.942 C12.2218322,6.98000019 12.0223342,7.05283279 11.845,7.1605 C11.6676658,7.2681672 11.5188339,7.40749914 11.3985,7.5785 C11.2781661,7.74950085 11.218,7.96799867 11.218,8.234 C11.218,8.46200114 11.2654995,8.65199924 11.3605,8.804 C11.4555005,8.95600076 11.5948324,9.08899943 11.7785,9.203 C11.9621676,9.31700057 12.1806654,9.42149952 12.434,9.5165 C12.6873346,9.61150047 12.9723317,9.70966616 13.289,9.811 C13.7450023,9.96300076 14.2199975,10.1308324 14.714,10.3145 C15.2080025,10.4981676 15.6576646,10.7419985 16.063,11.046 C16.4683354,11.3500015 16.8039987,11.7268311 17.07,12.1765 C17.3360013,12.6261689 17.469,13.1866633 17.469,13.858 C17.469,14.6306705 17.3265014,15.2988305 17.0415,15.8625 C16.7564986,16.4261695 16.3733357,16.8916648 15.892,17.259 C15.4106643,17.6263352 14.8596698,17.8986658 14.239,18.076 C13.6183302,18.2533342 12.97867,18.342 12.32,18.342 C11.3573285,18.342 10.4263378,18.1741683 9.527,17.8385 C8.62766217,17.5028317 7.88033631,17.0246698 7.285,16.404 L9.413,14.238 C9.74233498,14.6433354 10.176164,14.9821653 10.7145,15.2545 C11.252836,15.5268347 11.7879973,15.663 12.32,15.663 C12.5606679,15.663 12.7949989,15.6376669 13.023,15.587 C13.2510011,15.5363331 13.4504991,15.4540006 13.6215,15.34 C13.7925009,15.2259994 13.9286662,15.0740009 14.03,14.884 C14.1313338,14.693999 14.182,14.4660013 14.182,14.2 C14.182,13.9466654 14.1186673,13.7313342 13.992,13.554 C13.8653327,13.3766658 13.6848345,13.2151674 13.4505,13.0695 C13.2161655,12.9238326 12.9248351,12.7908339 12.5765,12.6705 C12.2281649,12.5501661 11.8323355,12.420334 11.389,12.281 C10.9583312,12.141666 10.5371687,11.9770009 10.1255,11.787 C9.71383127,11.596999 9.34650161,11.3531682 9.0235,11.0555 C8.70049838,10.7578318 8.44083431,10.3968355 8.2445,9.9725 C8.04816568,9.54816454 7.95,9.03200304 7.95,8.424 C7.95,7.67666293 8.10199848,7.03700266 8.406,6.505 C8.71000152,5.97299734 9.10899753,5.53600171 9.603,5.194 C10.0970025,4.85199829 10.6543302,4.60183412 11.275,4.4435 C11.8956698,4.28516587 12.5226635,4.206 13.156,4.206 C13.9160038,4.206 14.6918294,4.34533194 15.4835,4.624 C16.2751706,4.90266806 16.9686637,5.31433061 17.564,5.859 L15.493,8.044 Z" fill="#000000"/>\
								    </g>\
								</svg><!--end::Svg Icon--></span>\
							</a>\
	                    ';
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

// decimal format
$("#paymentAmountId").inputmask('decimal', {
    rightAlignNumerics: false
}); 

//decimal format
$("#discountAmountId").inputmask('decimal', {
    rightAlignNumerics: false
}); 

$('input[name=paymentAmount]').change(function() { 
	var bal = parseFloat($("#balanceAmountId").val());
	var pay = parseFloat($("#paymentAmountId").val());
	$("#paymentAmountId").val(pay.toFixed(2));
	
	if(bal == pay)
		$("#discountAmountId").val("0.00");
	$("#discount-info").text("Balance Amount is " + (bal-pay).toFixed(2) + ". You can also discount it.");
});

jQuery(document).on("click", ".paymentHistoryModal", function () {
	
	$("#paymentHistoryTable tr>td").remove();
	
	var object = $(this).data('id');

	if(object != undefined && object != "")
	{
		object = JSON.parse(object.replaceAll("\'", "\""));
		
	    $.each(object, function(index, jsonObject){     
	        if(Object.keys(jsonObject).length > 0){
	          var tableRow = '<tr class="font-weight-bold font-size-sm">';
	          var cellA = ["","","","",""];
	          
	          $.each(Object.keys(jsonObject), function(i, key){
	        	 if(i == 0)
	        		 cellA[0] = '<td class="pt-1 pb-4 text-center font-weight-normal font-size-lg text-uppercase">' + (index+1) + '</td>';
	        	 else
	        	 {
	        		 if(key == "paymentDate")
	        			 cellA[1] = '<td class="pt-1 pb-4 text-center font-weight-normal font-size-lg ">' + jsonObject[key] + '</td>';
	        		 else if(key == "invoiceNo")
	        			 cellA[2] = '<td class="pt-1 pb-4 text-left font-weight-normal font-size-lg text-uppercase">' + jsonObject[key] + cellA[2] ;
	        		 else if(key == "paymentAmount")
	        			 cellA[3] = '<td class="pt-1 pb-4 text-right font-weight-normal font-size-lg text-uppercase">' + jsonObject[key] + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
	        		 else if(key == "discountAmount")
	        			 cellA[4] = '<td class="pt-1 pb-4 text-right font-weight-normal font-size-lg text-uppercase">' + jsonObject[key] + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
	        		 else if(key == "status" && jsonObject[key] != "Pending")
	        		 {
	        			 var color = (jsonObject[key] == "Settled")? "success" : "primary";
        				 cellA[2] = cellA[2] + '<span class="svg-icon svg-icon-'+ color+' svg-icon-2x" ><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/Navigation/Check.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
        			 						<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
        			 						<polygon points="0 0 24 0 24 24 0 24"/>\
        			 						<path d="M6.26193932,17.6476484 C5.90425297,18.0684559 5.27315905,18.1196257 4.85235158,17.7619393 C4.43154411,17.404253 4.38037434,16.773159 4.73806068,16.3523516 L13.2380607,6.35235158 C13.6013618,5.92493855 14.2451015,5.87991302 14.6643638,6.25259068 L19.1643638,10.2525907 C19.5771466,10.6195087 19.6143273,11.2515811 19.2474093,11.6643638 C18.8804913,12.0771466 18.2484189,12.1143273 17.8356362,11.7474093 L14.0997854,8.42665306 L6.26193932,17.6476484 Z" fill="#000000" fill-rule="nonzero" transform="translate(11.999995, 12.000002) rotate(-180.000000) translate(-11.999995, -12.000002) "/>\
											</g>\
											</svg><!--end::Svg Icon--></span></td>';
	        		 }	 
	        	 
	        	 } 
	          });
	          
	          for (let i = 0; i < cellA.length; ++i) {
	        	  tableRow += cellA[i];
	          }
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
		 $('.modal-body-invoice-receipt').html("");
		 $.ajax({
             url: 'gaya/viewRentalReceipt/'+ rentalId,
             type: "POST",
             success: function (response) {
            	$('.modal-body-invoice-receipt').html(response);
            	$('.invoiceReceipt').html("View & Print Receipt");
            	$("#invoiceId").hide();
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
		displayInvoice(rentalId, true);
	}
});

jQuery(document).on("click", ".paymentModal", function () {

	var object = $(this).data('id');

	if(object != undefined && object != "")
	{
		object = JSON.parse(object.replaceAll("\'", "\""));
		$("#paymentTitle").html("Payment By " + object.customerName);
		$("#rentalId").val(object.rentalId );
		$("#paymentAmountId").val(object.balanceAmount );
		$("#balanceAmountId").val(object.balanceAmount);
		$("#discount-info").text("");
		
		if(parseFloat($("#paymentAmountId").val()) <= 0)
			$("#payment_submit_button").hide();
	}
});

$("#payment_submit_button").on("click", function (e) {
    e.preventDefault();

    if(parseFloat($("#paymentAmountId").val()) > 0)
    {
	    var data = {};
	    data["rentalId"] = $("#rentalId").val();
	    data["paymentAmount"] = $("#paymentAmountId").val();
	    data["discountAmount"] = $("#discountAmountId").val();
	
	    $.ajax({
	        url: 'gaya/payRentalAmount',
	        contentType: 'application/json',
	        type: "POST",
	        data: JSON.stringify(data),
	        success: function (response) {
	        	$('#paymentModal').modal('hide');
	        	if(response == "success")
	        	{
	        		Swal.fire("Payment Success", "Amount paid successfully.", "success");
	        	}
	        	else
	        	{
	        		Swal.fire("Payment Failed", "You payment has encountered error.", "error");
	        	}
	        	
	        	datatable.reload();
	        },
	        error: function (error) {
	        }
		 });
    }
});
function loadInvoice()
{
	var invoiceId = $("#invoiceId").val();
	displayInvoice(invoiceId, false);
}

function displayInvoice(rentalId, reset)
{
	 $.ajax({
        url: 'gaya/viewRentalInvoice/'+ rentalId,
        type: "POST",
        success: function (response) {
        	$('.modal-body-invoice-receipt').html(response);
        	if(reset)
        	{
	        	$('.invoiceReceipt').html("View & Print Invoice");
	           	$('#invoiceHistoryId').html($('#invoiceHistoryIdHide').html());
	        }
        	$('#invoiceHistoryIdHide').html("");
        },
        error: function (error) {
        	$('.modal-body-invoice-receipt').html("Not Able To Load Invoice");
        }
	 });
}


function generateInvoice(invoiceId) {

	if(invoiceId != undefined && invoiceId != "")
	{
		Swal.fire({
			title: "Confirm Invoice Closure",
			text: "Do you want to close Invoice !?",
	        icon: "warning",
	        showCancelButton: true,
			confirmButtonClass: "btn-danger",
			confirmButtonText: "Yes, Finalize",
	        cancelButtonText: "No, Just Continue!",
	        reverseButtons: true
	    }).then(function(result) {
	    	$.ajax({
	             url: 'gaya/generateInvoice/'+ ((result.value != undefined && result.value) ? 1 : 0) + '/'+ invoiceId,
	             type: "POST",
	             success: function (response) {
	            	if(response != null && response != undefined && response != "" )
	            	{
	            		$('#invoiceNoDiv').html(response);
	            		$('#createInvoiceBtn').attr("disabled", true);
	            		var currInv = $('#invoiceId option:selected').text();
	            		currInv = currInv.replaceAll("Current Invoice", response);
	            		$("#invoiceId").find("option:contains(\"Current Invoice\")").text(currInv);
	            	}
	             },
	             error: function (error) {
	                 console.log(`Error ${error}`);
	             }
			 });
	    });
	}
}

function printDiv(eltId, file_name) {
	var element =  document.getElementById(eltId);
	var opt = {
	  margin:       0.25,
	  filename:     file_name,
	  image:        { type: 'jpeg', quality: 0.98 },
	  html2canvas:  { scale:1, scrollY: -window.scrollY,  height: window.outerHeight + window.innerHeight - 500, windowHeight: window.outerHeight + window.innerHeight- 500},
	  jsPDF:        { unit: 'in', format: 'A4', orientation: 'portrait' }
	};
	 
	// New Promise-based usage:
	html2pdf().from(element).set(opt).save();
}