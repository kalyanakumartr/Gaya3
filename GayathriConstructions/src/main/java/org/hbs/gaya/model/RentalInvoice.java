package org.hbs.gaya.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental_invoice")
@Getter
@Setter
@NoArgsConstructor
public class RentalInvoice implements Serializable
{

	private static final long serialVersionUID = 160903192897435425L;

	@Id
	@Column(name = "invoiceId")
	private String				itemId;

	@ManyToOne(targetEntity = Rental.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "masterInvoiceId", nullable = false)
	private RentalInvoice				masterInvoice;
	
	@ManyToOne(targetEntity = Rental.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "rentalId", nullable = false)
	private Rental				rental;

	@Column(name = "invoiceDate")
	private LocalDate				invoiceDate;

	@Column(name = "startDate")
	private LocalDate				startDate;

	@Column(name = "endDate")
	private LocalDate				endDate;
	
	@Column(name = "paymentDate")
	private LocalDateTime				paymentDate;
	
	@Column(name = "rentalAmount")
	private Double				rentalAmount = 0.0;

}
