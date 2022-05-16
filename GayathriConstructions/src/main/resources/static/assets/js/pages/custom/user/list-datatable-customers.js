"use strict";
// Class definition
var datatable ; 
var KTAppsCustomerListDatatable = function() {
	// Private functions
	
	// basic demo
	var _demo = function() {
		datatable = $('#kt_datatable').KTDatatable({
			// datasource definition
			data: {
				type: 'remote',
				source: {
					read: {
						url: '/gaya/searchRental/0',
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
					field: 'rentalStatus',
					title: 'Status',
					width: 60,
					textAlign: 'left',
					template: function(data) {
						return data.rentalStatus == 'None' ? '<span class="label label-lg label-warning label-inline mr-2">Draft</span>' : 
							'<span class="label label-lg label-info label-inline mr-2">' + data.rentalStatus + '</span>';
					}
				}, {
					field: 'customerName',
					title: 'Customer Name',
					width: 100,
					textAlign: 'left',
					template: function(data) {
						
						return '<span >' + data.customer.customerName + '</span>';
					}
				}, {
					field: 'mobileNo',
					title: 'Mobile No',
					width: 100,
					textAlign: 'center',
					template: function(data) {
						return '<span >' + data.customer.mobileNo + '</span>';
					}
				}, {
					field: 'alternateNo',
					title: 'Alternate No',
					width: 100,
					textAlign: 'center',
					template: function(data) {
						return '<span >' + data.customer.alternateNo + '</span>';
					}
				}, {
					field: 'address',
					title: 'Address',
					width: 220,
					textAlign: 'left',
					template: function(data) {
						return '<textarea class="form-control input-sm font-size-sm" cols="220" rows="3" readonly="readonly" style="resize: none;">' + data.customer.address + '</textarea>';
					}
				}, {
					field: 'viewRentals',
					title: 'Rentals',
					width: 220,
					textAlign: 'left',
					template: function(data) {
						
						var option = "";
						jQuery.each(data.rentalList, function(index, opt) {
							option += "<option value='" + opt.value + "'>" + opt.label + "</option>";
						});
												
						var select = "<select class='form-control input-sm font-size-sm rentalInfo' id='rentalInfo"+ data.customer.customerId + "' name='rentalInfo'>" + option + "</select>";
						return select;
					}
				}, {
					field: 'Actions',
					title: 'Add Items',
					sortable: false,
					width: 75,
					overflow: 'visible',
					textAlign: 'center',
					autoHide: false,
					template: function(data) { 
						
						if(data.rentalStatus == 'None')
						{
							var rentalData = JSON.stringify(data);
							rentalData = rentalData.replaceAll("\"", "\'");
							return '\
								<a href="javascript:;" class="btn btn-sm btn-light-primary btn-text-primary btn-hover-primary btn-icon me-2 addRentalMaterials" data-id="'+ rentalData +'" data-toggle="modal" data-target="#addRentalMaterials" title="Add Rental Items">\
								<span class="svg-icon svg-icon-2x">\
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
						}
						else
						{
							var customer = JSON.stringify(data.customer);
							customer = customer.replaceAll("\"", "\'");
							
							return '\
								<a href="javascript:;" class="btn btn-sm btn-light-primary btn-text-primary btn-hover-primary btn-icon me-2 viewItemsModal" data-id="rentalInfo'+ data.customer.customerId +'" data-toggle="modal" data-target="#viewItemsModal" title="Add Rental Items">\
								<span class="svg-icon svg-icon-primary svg-icon-2x"><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/General/Search.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
							    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
							        <rect x="0" y="0" width="24" height="24"/>\
							        <path d="M14.2928932,16.7071068 C13.9023689,16.3165825 13.9023689,15.6834175 14.2928932,15.2928932 C14.6834175,14.9023689 15.3165825,14.9023689 15.7071068,15.2928932 L19.7071068,19.2928932 C20.0976311,19.6834175 20.0976311,20.3165825 19.7071068,20.7071068 C19.3165825,21.0976311 18.6834175,21.0976311 18.2928932,20.7071068 L14.2928932,16.7071068 Z" fill="#000000" fill-rule="nonzero" opacity="0.3"/>\
							        <path d="M11,16 C13.7614237,16 16,13.7614237 16,11 C16,8.23857625 13.7614237,6 11,6 C8.23857625,6 6,8.23857625 6,11 C6,13.7614237 8.23857625,16 11,16 Z M11,18 C7.13400675,18 4,14.8659932 4,11 C4,7.13400675 7.13400675,4 11,4 C14.8659932,4 18,7.13400675 18,11 C18,14.8659932 14.8659932,18 11,18 Z" fill="#000000" fill-rule="nonzero"/>\
							    </g>\
							</svg><!--end::Svg Icon--></span>\
							</a>\
							<a href="javascript:;" class="btn btn-sm btn-light-primary btn-text-primary btn-hover-primary btn-icon me-2 addNewRentalWithCustomer" data-id="'+ customer +'" data-toggle="modal" data-target="#addNewRentalWithCustomer" title="Add Rental Items With Existing Customer">\
								<span class="svg-icon svg-icon-primary svg-icon-2x"><!--begin::Svg Icon | path:/var/www/preview.keenthemes.com/metronic/releases/2021-05-14-112058/theme/html/demo1/dist/../src/media/svg/icons/Navigation/Plus.svg--><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
							    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
							        <rect fill="#000000" x="4" y="11" width="16" height="2" rx="1"/>\
							        <rect fill="#000000" opacity="0.3" transform="translate(12.000000, 12.000000) rotate(-270.000000) translate(-12.000000, -12.000000) " x="4" y="11" width="16" height="2" rx="1"/>\
							    </g>\
							    </svg><!--end::Svg Icon--></span>\
							</a>';
						}
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

$(document.body).on('change', '#materialInfo', function() {
	var materialInfo = $("#materialInfo").val();
	
	if(materialInfo != undefined && materialInfo != null && materialInfo != "")
	{
		var material = materialInfo.split("|");
		
		$("#quantity").val(material[1]);
		$("#availableQuantity").val(material[1]);
		$("#price").val(parseFloat(material[2]).toFixed(2));
		$("#actualPrice").val(material[2]);
		updateTotalCost();
	}
});

$(document.body).on('change', '#quantity', function() {
	
	updateTotalCost();
});

$(document.body).on('change', '#price', function() {
	updateTotalCost();
	
});

$(document.body).on('change', '#advanceAmount', function() {
	
	var advanceAmount = $("#advanceAmount").val();
	if(advanceAmount == undefined || advanceAmount == null || advanceAmount == "" || parseFloat(advanceAmount) < 0)
		$("#advanceAmount").val("0.00");
	else
	{
		$("#advanceAmount").val(parseFloat($("#advanceAmount").val()).toFixed(2));
	}
});

function updateTotalCost()
{
	var qty = $("#quantity").val();
	var aQty = $("#availableQuantity").val();
	var price = $("#price").val();
	var aPrice = $("#actualPrice").val();
	
	if(qty == undefined || qty == null || qty == "" || parseInt(qty) < 0 )
		$("#quantity").val("0");
	
	if(aQty == undefined || aQty == null || aQty == "" || parseInt(aQty) < 0 )
	{
		$("#quantity").val("0");
		$("#price").val("0.00");
		alert("Material Quantity is not available.");
	}
	
	if(price == undefined || price == null || price == "" || parseFloat(price) < 0)
		$("#price").val("0.00");
	else
	{
		$("#price").val(parseFloat($("#price").val()).toFixed(2));
	}
	
	if(aPrice == undefined || aPrice == null || aPrice == "" || parseFloat(aPrice) < 0 )
	{
		$("#quantity").val("0");
		$("#price").val("0.00");
		alert("Material Price is not available.");
	}
	
	if(parseInt(qty) > parseInt(aQty))
		$("#quantity").val(aQty);
	
	$("#totalCost").val((parseInt($("#quantity").val()) * parseFloat($("#price").val())).toFixed(2));
}

$("#add_customer_submit_button").on("click", function (e) {
    e.preventDefault();
    
    var data = {};
    data["customerName"] = $("#customerName").val();
    data["mobileNo"] = $("#mobileNo").val();
    data["alternateNo"] = $("#alternateNo").val();
    data["address"] = $("#address").val();
    
    if($("#customerName").val() != "" && $("#mobileNo").val() != ""  && $("#alternateNo").val() != "" && $("#address").val() != "" )
    {
	    $.ajax({
	        url: '/gaya/addCustomer',
	        contentType: 'application/json',
	        type: "POST",
	        data: JSON.stringify(data),
	        success: function (response) {
	        	$('#addCustomerModal').modal('hide');
	        	if(response == "success")
	        	{
	        		Swal.fire("Customer Added", "Draft Rental Instance Created For The New Customer : " + $("#customerName").val() , "success");
	        	}
	        	else
	        	{
	        		Swal.fire("Customer Add Failed", "Customer adding has encountered error.", "error");
	        	}
	        	
	        	datatable.reload();
	        },
	        error: function (error) {
	        }
		 });
    }
    else
    {
    	Swal.fire("Details Missing", "Please enter all the fields value", "error");
    }
});

jQuery(document).on("click", ".viewItemsModal", function () {
	
	var rentalId = $("#" + $(this).data('id')).val();

	if(rentalId != undefined && rentalId != "")
	{
	    $.ajax({
	         url: 'gaya/viewRentalReceipt/'+ rentalId,
	         type: "POST",
	         success: function (response) {
	        	$('.modal-body-material-view-rental').html(response);
	         },
	         error: function (error) {
	             console.log(`Error ${error}`);
	         }
		 });
	}
});
jQuery(document).on("click", ".addNewRentalWithCustomer", function () {
	
	var customer = $(this).data('id');
	
	if(customer != undefined && customer != "")
	{
		Swal.fire({
			title: "Confirm New Rental",
			text: "Do you want to create new 'Rental' for this customer?",
	        icon: "warning",
	        showCancelButton: true,
			confirmButtonClass: "btn-info",
			cancelButtonClass: "btn-danger",
			confirmButtonText: "Create New Rental",
	        cancelButtonText: "Cancel"
	    }).then(function(result) {
	    	
	    	if(!result.isDismissed) 
	    	{
				customer = JSON.parse(customer.replaceAll("\'", "\""));
				$('.customerName').html(customer.customerName);
				$('.mobileNo').html(customer.mobileNo);
				
				var data = {};
			    data["customerId"] = customer.customerId;
		
			    $.ajax({
			        url: '/gaya/addRentals',
			        contentType: 'application/json',
			        type: "POST",
			        data: JSON.stringify(data),
			        success: function (response) {
			        	datatable.reload();
			        	$('.modal-body-material-rental').html(response);
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
			}
	    	else
	    	{
	    		$('.modal-body-material-rental').html("");
	    		$('#addNewRentalWithCustomer').modal('hide');
	    	}
	    });
	}
});

jQuery(document).on("click", ".addRentalMaterials", function () {
	
	var rental = $(this).data('id');

	if(rental != undefined && rental != "")
	{
		rental = JSON.parse(rental.replaceAll("\'", "\""));
		$('.customerName').html(rental.customer.customerName);
		$('.mobileNo').html(rental.customer.mobileNo);
		
	    $.ajax({
	         url: 'gaya/getRentalMaterials/'+ rental.rentalId,
	         type: "POST",
	         success: function (response) {
	        	$('.modal-body-material-rental').html(response);
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
	}
});

jQuery(document).ready(function() {
	KTAppsCustomerListDatatable.init();
});

jQuery(document).on("click", ".removeItems", function () {

	var itemId = $(this).data('id');
	var rentalId = $("#rentalId").val(); 
	if(rentalId != undefined && rentalId != "" && itemId != undefined && itemId != "")
	{
		$.ajax({
	        url: '/gaya/removeItemsFromRental/' + rentalId + "/" + itemId,
	        contentType: 'application/json',
	        type: "POST",
	        success: function (response) {
	        	$('.modal-body-material-rental').html(response);
	        },
	        error: function (error) {
	            console.log(`Error ${error}`);
	        }
		 });
	}
	
});


function finalizeRental()
{
	var data = {};
    data["rentalId"] = $("#rentalId").val();
    data["advanceAmount"] = $("#advanceAmount").val()=="" ? "0": $("#advanceAmount").val();
    
	$.ajax({
        url: '/gaya/finalizeRental',
        contentType: 'application/json',
        type: "POST",
        data: JSON.stringify(data),
        success: function (response) {

        	$('#addRentalMaterials').modal('hide');
        	
        	if(response == "success")
        	{
        		Swal.fire("Rental Finalized", "", "success");
        	}
        	else
        	{
        		Swal.fire("Rental Finalization Failed", "", "error");
        	}
        	
        	datatable.reload();
        },
        error: function (error) {
            console.log(`Error ${error}`);
        }
	 });
}

function addItemsToRental() {

	var materialInfo = $("#materialInfo").val();
	
	if(materialInfo != undefined && materialInfo != null && materialInfo != "")
	{
		var data = {};
	    data["materialId"] =  materialInfo.split("|")[0];
	    data["rentalId"] = $("#rentalId").val();
	    data["quantity"] = $("#quantity").val();
	    data["price"] = $("#price").val();
	    data["advanceAmount"] = $("#advanceAmount").val()=="" ? "0": $("#advanceAmount").val();
	    
	    $.ajax({
	        url: '/gaya/addRentals',
	        contentType: 'application/json',
	        type: "POST",
	        data: JSON.stringify(data),
	        success: function (response) {
	        	$('.modal-body-material-rental').html(response);
	        },
	        error: function (error) {
                console.log(`Error ${error}`);
            }
		 });
	}
}
