package org.hbs.gaya.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hbs.gaya.util.EnumInterface;

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
	
	public enum EInvoiceStatus implements EnumInterface
	{
		Settled, New, InProcess, Pending; 
	}

	@Id
	@Column(name = "invoiceId")
	private String				invoiceId;

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
	
	@Column(name = "invoiceAmount")
	private Double				invoiceAmount = 0.0;
	
	@Column(name = "invoiceStatus")
	@Enumerated(EnumType.STRING)
	private EInvoiceStatus				invoiceStatus;
	
	@Column(name = "paymentAmount")
	private Double				paymentAmount = 0.0;
	
	@Column(name = "paymentDate")
	private LocalDateTime				paymentDate;
}
