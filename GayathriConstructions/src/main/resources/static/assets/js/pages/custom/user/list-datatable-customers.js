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
						return '<span class="font-weight-sm">' + (row + 1) + '</span>';
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
					field: 'address',
					title: 'Address',
					width: 80,
					textAlign: 'center',
					template: function(data) {
						return '<span class="">' + data.customer.address + '</span>';
					}					
				}, {
					field: 'Actions',
					title: 'Actions',
					sortable: false,
					width: 150,
					overflow: 'visible',
					autoHide: false,
					template: function(data) { 
						return '\
							<a href="javascript:;" class="btn btn-sm btn-default btn-text-primary btn-hover-primary btn-icon me-2" data-id =\''+JSON.stringify(data)+'\' data-toggle="modal" data-target="#addRentalMaterials" title="Add Rental Items">\
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
							</a>';
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

jQuery('#addRentalMaterials').on('show.bs.modal', function(e) {

    //get data-id attribute of the clicked element
    var rentalId='RNT1000';
    var data = jQuery(e.relatedTarget).data('id');
    $('.mobileNo').html(data.customer.mobileNo);
    $('.customerName').html(data.customer.customerName);
     $('#customerId').val(data.customer.customerId);
     $.ajax({
             url: 'gaya/getRentalMaterials/'+rentalId,
             type: "POST",
             success: function (response) {
            	$('.modal-body-material-rent').html(response);
            	
             },
             error: function (error) {
                 console.log(`Error ${error}`);
             }
		 });
    $("#addRentalMaterialTable tr>td").remove();
	
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
	          $('#addRentalMaterialTable tbody').append(tableRow);
	        }
    	});
	}
});
jQuery(document).ready(function() {
	KTAppsRentalListDatatable.init();
});

function loadMaterialInfo()
{
	var invoiceId = $("#invoiceId").val();
	displayInvoice(invoiceId, false);
}
function add(){
	var rentalId = $("#rentalId").val();
	var material = $("#material").val();
	var rentPerUnit = $("#rentPerUnit").val();
	var noOfUnits = $("#noOfUnits").val();
	var total = $("#total").val();
	var customerId = $("#customerId").val();

	
	 $.ajax({
             url: 'gaya/addRentals?customerId='+customerId+'&rentalId='+rentalId+'&material='+material+"&rentPerUnit="+rentPerUnit+"&noOfUnits="+noOfUnits+"&total="+total,
             type: "POST",
             success: function (response) {
            	$('.modal-body-material-rent').html(response);
            	
             },
             error: function (error) {
                 console.log(`Error ${error}`);
             }
		 });
}
function calculateTotal(){
		var rentPerUnit = $("#rentPerUnit").val();
		var noOfUnits = $("#noOfUnits").val();
		$("#total").val(rentPerUnit*noOfUnits);
}