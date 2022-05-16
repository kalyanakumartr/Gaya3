package org.hbs.gaya.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hbs.gaya.util.EnumInterface;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
		Settled, Pending, Payable, None;
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
	@Builder.Default
	private Double			invoiceAmount	= 0.0;

	@Column(name = "invoiceStatus")
	@Enumerated(EnumType.STRING)
	private EInvoiceStatus	invoiceStatus;

	@Column(name = "paymentAmount")
	@JsonSerialize(using = TwoDecimalSerializer.class)
	@Builder.Default
	private Double			paymentAmount	= 0.0;

	@Column(name = "active")
	@Builder.Default
	private Boolean			active			= false;

	@Column(name = "calculatedDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime	calculatedDate;

	@OneToMany(targetEntity = RentalItem.class, fetch = FetchType.EAGER, mappedBy = "rentalInvoice", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.JOIN)
	@OrderBy("itemId")
	@JsonDeserialize(as = LinkedHashSet.class)
	@Builder.Default
	private Set<RentalItem>		itemSet		= new LinkedHashSet<>();
	
	@Transient
	@JsonIgnore
	public RentalInvoice getInvoiceClone()
	{
		return RentalInvoice.builder()
				.masterInvoice(this)
				.active(true)
				.calculatedDate(LocalDate.now().plusDays(1).atStartOfDay())
				.endDate(null)
				.startDate(LocalDate.now().plusDays(1).atStartOfDay())
				.invoiceNo(null)
				.invoiceStatus(EInvoiceStatus.Pending)
				.rental(this.rental)
				.invoiceDate(null)
				.invoiceAmount(0.0)
				.paymentAmount(0.0)
				.itemSet(new LinkedHashSet<>())
				.build();
	}
	
	@Transient
	public Double getItemsTotalCost()
	{
		double allItemsTotalCost = 0.0;
		
		for (RentalItem item : this.itemSet)
		{
			allItemsTotalCost += item.getTotalCost();
		}
		
		return allItemsTotalCost ;
	}
	
	@Transient
	public Double getInvoiceBalance()
	{
		return (this.invoiceAmount - this.paymentAmount);
	}
	
	@Transient
	public LocalDateTime getEndDate$()
	{
		return this.endDate == null ? LocalDateTime.now() : this.endDate;
	}
	@Transient
	public LocalDateTime getInvoiceDate$()
	{
		return this.invoiceDate == null ? LocalDateTime.now() : this.invoiceDate;
	}
	
	@Transient
	public Long getNoOfDays$()
	{
		return this.startDate.until(getEndDate$(), ChronoUnit.DAYS);
	}
}
