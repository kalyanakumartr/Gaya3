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
import javax.persistence.Transient;

import org.hbs.gaya.util.EnumInterface;
import org.hbs.gaya.util.EBusinessKey.EKey;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental_invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Builder
public class RentalInvoice implements Serializable
{

	private static final long serialVersionUID = 160903192897435425L;

	public enum EInvoiceStatus implements EnumInterface
	{
		Settled, New, InProcess, Pending, Payable;
	}

	@Id
	@Column(name = "invoiceId")
	private String			invoiceId;

	@Column(name = "invoiceNo")
	private String			invoiceNo;

	@ManyToOne(targetEntity = RentalInvoice.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "masterInvoiceId", nullable = false)
	@JsonIgnore
	private RentalInvoice	masterInvoice;

	@ManyToOne(targetEntity = Rental.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "rentalId", nullable = false)
	@JsonIgnore
	private Rental			rental;

	@Column(name = "invoiceDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime		invoiceDate;

	@Column(name = "startDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime		startDate;

	@Column(name = "endDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime		endDate;

	@Column(name = "invoiceAmount")
	@JsonSerialize(using = TwoDecimalSerializer.class)
	private Double			invoiceAmount	= 0.0;

	@Column(name = "invoiceStatus")
	@Enumerated(EnumType.STRING)
	private EInvoiceStatus	invoiceStatus;

	@Column(name = "paymentAmount")
	@JsonSerialize(using = TwoDecimalSerializer.class)
	private Double			paymentAmount	= 0.0;

	@Column(name = "active")
	private Boolean			active			= false;

	@Column(name = "calculatedDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime	calculatedDate;

	@Transient
	@JsonIgnore
	public RentalInvoice getInvoiceClone()
	{
		return RentalInvoice.builder()
				.masterInvoice(this)
				.active(true)
				.calculatedDate(null)
				.endDate(null)
				.startDate(LocalDate.now().plusDays(1).atStartOfDay())
				.invoiceId(EKey.timeline("IN"))
				.invoiceNo(null)
				.invoiceStatus(EInvoiceStatus.Pending)
				.rental(this.rental)
				.invoiceDate(null)
				.invoiceAmount(0.0)
				.paymentAmount(0.0)
				.build();
	}

}
