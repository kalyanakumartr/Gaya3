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
					width: 40,
					type: 'number',
					selector: false,
					textAlign: 'center',
					template: function(data, row) {
						return '<span class="font-weight-bolder">' + (row + 1) + '</span>';
					}
				}, {
					field: 'customerName',
					title: 'Customer Name',
					width: 120,
					textAlign: 'left',
					template: function(data) {
						
						return '<span class="font-weight-bolder">' + data.customer.customerName + '</span>';
					}
				}, {
					field: 'mobileNo',
					title: 'Mobile No',
					width: 90,
					textAlign: 'center',
					template: function(data) {
						return '<span class="font-weight-bolder">' + data.customer.mobileNo + '</span>';
					}
				}, {
					field: 'alternateNo',
					title: 'Alternate No',
					width: 90,
					textAlign: 'center',
					template: function(data) {
						return '<span class="font-weight-bolder">' + data.customer.alternateNo + '</span>';
					}
				}, {
					field: 'pendingInvoiceAmount',
					title: 'Pending Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-bolder">' + data.pendingInvoiceAmount + '</span>';
					}
				}, {
					field: 'activeInvoiceAmount',
					title: 'Current Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-bolder">' + data.activeInvoiceAmount + '</span>';
					}
				}, {
					field: 'totalInvoiceAmount',
					title: 'Total Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-bolder">' + data.totalInvoiceAmount + '</span>';
					}
				}, {
					field: 'paymentAmount',
					title: 'Paid Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-bolder">' + data.paymentAmount + '</span>';
					}
				}, {
					field: 'balanceAmount',
					title: 'Balance Amount',
					width: 70,
					textAlign: 'right',
					template: function(data) {
						return '<span class="font-weight-bolder text-danger">' + data.balanceAmount + '</span>';
					}
				}, {
					field: 'Actions',
					title: 'Actions',
					sortable: false,
					width: 150,
					overflow: 'visible',
					autoHide: false,
					template: function() {
						return '\<a href="javascript:;" class="btn btn-sm btn-icon btn-light btn-color-muted btn-hover-success me-2" data-bs-toggle="tab" title="View Invoice">\
								<span class="svg-icon svg-icon-2">\
									<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">\
										<path d="M21 7H3C2.4 7 2 6.6 2 6V4C2 3.4 2.4 3 3 3H21C21.6 3 22 3.4 22 4V6C22 6.6 21.6 7 21 7Z" fill="currentColor"></path>\
										<path opacity="0.3" d="M21 14H3C2.4 14 2 13.6 2 13V11C2 10.4 2.4 10 3 10H21C21.6 10 22 10.4 22 11V13C22 13.6 21.6 14 21 14ZM22 20V18C22 17.4 21.6 17 21 17H3C2.4 17 2 17.4 2 18V20C2 20.6 2.4 21 3 21H21C21.6 21 22 20.6 22 20Z" fill="currentColor"></path>\
									</svg>\
								</span>\
							</a>\
							<a href="javascript:;" class="btn btn-sm btn-icon btn-light btn-color-muted btn-hover-primary me-2"  title="Add/Edit Items" >\
								<span class="svg-icon svg-icon-2">\
									<svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" viewBox="0 0 24 24">\
										<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
											<rect x="5" y="5" width="5" height="5" rx="1" fill="currentColor"></rect>\
											<rect x="14" y="5" width="5" height="5" rx="1" fill="currentColor" opacity="0.3"></rect>\
											<rect x="5" y="14" width="5" height="5" rx="1" fill="currentColor" opacity="0.3"></rect>\
											<rect x="14" y="14" width="5" height="5" rx="1" fill="currentColor" opacity="0.3"></rect>\
										</g>\
									</svg>\
								</span>\
							</a>\
							<a href="javascript:;" class="btn btn-sm btn-default btn-text-primary btn-hover-light-success btn-icon mr-2" title="Edit Contact">\
		                        <span class="svg-icon svg-icon-md">\
									<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
										<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
											<rect x="0" y="0" width="24" height="24"/>\
											<path d="M12.2674799,18.2323597 L12.0084872,5.45852451 C12.0004303,5.06114792 12.1504154,4.6768183 12.4255037,4.38993949 L15.0030167,1.70195304 L17.5910752,4.40093695 C17.8599071,4.6812911 18.0095067,5.05499603 18.0083938,5.44341307 L17.9718262,18.2062508 C17.9694575,19.0329966 17.2985816,19.701953 16.4718324,19.701953 L13.7671717,19.701953 C12.9505952,19.701953 12.2840328,19.0487684 12.2674799,18.2323597 Z" fill="#000000" fill-rule="nonzero" transform="translate(14.701953, 10.701953) rotate(-135.000000) translate(-14.701953, -10.701953) "/>\
											<path d="M12.9,2 C13.4522847,2 13.9,2.44771525 13.9,3 C13.9,3.55228475 13.4522847,4 12.9,4 L6,4 C4.8954305,4 4,4.8954305 4,6 L4,18 C4,19.1045695 4.8954305,20 6,20 L18,20 C19.1045695,20 20,19.1045695 20,18 L20,13 C20,12.4477153 20.4477153,12 21,12 C21.5522847,12 22,12.4477153 22,13 L22,18 C22,20.209139 20.209139,22 18,22 L6,22 C3.790861,22 2,20.209139 2,18 L2,6 C2,3.790861 3.790861,2 6,2 L12.9,2 Z" fill="#000000" fill-rule="nonzero" opacity="0.3"/>\
										</g>\
									</svg>\
		                        </span>\
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
