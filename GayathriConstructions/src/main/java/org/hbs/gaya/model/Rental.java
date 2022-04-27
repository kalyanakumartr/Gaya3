package org.hbs.gaya.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import org.hbs.gaya.model.RentalInvoice.EInvoiceStatus;
import org.hbs.gaya.util.EnumInterface;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Rental implements Serializable
{
	public enum ERentalStatus implements EnumInterface
	{
		Completed, None, Rented;
	}

	private static final long	serialVersionUID	= 8952784246497174078L;

	@Id
	@Column(name = "rentalId")
	private String				rentalId;

	@Column(name = "advanceAmount")
	private Double				advanceAmount		= 0.0;

	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "customerId", nullable = false)
	private Customer			customer;

	@Column(name = "rentalStatus")
	@Enumerated(EnumType.STRING)
	private ERentalStatus		rentalStatus;

	@Column(name = "rentedDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime		rentedDate;
	
	@OneToMany(targetEntity = PaymentHistory.class, fetch = FetchType.EAGER, mappedBy = "rental", cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JsonDeserialize(as = LinkedHashSet.class)
	@OrderBy("paymentDate Desc")
	private Set<PaymentHistory>		paymentSet		= new LinkedHashSet<>();

	@OneToMany(targetEntity = RentalItem.class, fetch = FetchType.EAGER, mappedBy = "rental", cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JsonDeserialize(as = LinkedHashSet.class)
	private Set<RentalItem>		rentalItemSet		= new LinkedHashSet<>();

	@OneToMany(targetEntity = RentalInvoice.class, fetch = FetchType.EAGER, mappedBy = "rental", cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JsonDeserialize(as = LinkedHashSet.class)
	private Set<RentalInvoice>	rentalInvoiceSet	= new LinkedHashSet<>();

	@Column(name = "createdDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime		createdDate;

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "createdBy", nullable = false)
	private Users				createdUser;

	@Transient
	@JsonSerialize(using = TwoDecimalSerializer.class)
	public Double getTotalInvoiceAmount()
	{
		return this.getPendingInvoiceAmount() + this.getActiveInvoiceAmount();
	}
	
	@Transient
	@JsonSerialize(using = TwoDecimalSerializer.class)
	public Double getBalanceAmount()
	{
		return this.getTotalInvoiceAmount() - this.getPaymentAmount();
	}


	@Transient
	@JsonSerialize(using = TwoDecimalSerializer.class)
	public Double getPendingInvoiceAmount()
	{
		Double pendingInvoiceAmount = 0.0;
		for (RentalInvoice pendingInvoice : this.getPendingInvoice())
		{
			pendingInvoiceAmount += pendingInvoice.getInvoiceAmount();
		}

		return pendingInvoiceAmount;
	}

	@Transient
	@JsonSerialize(using = TwoDecimalSerializer.class)
	public Double getActiveInvoiceAmount()
	{
		RentalInvoice rInvoice = this.getActiveInvoice();

		return rInvoice == null ? 0.0 : rInvoice.getInvoiceAmount();
	}

	@Transient
	public RentalInvoice getActiveInvoice()
	{
		Optional<RentalInvoice> inOpt = this.rentalInvoiceSet.stream().filter(p -> p.getActive()).findFirst();

		return inOpt.isPresent() ? inOpt.get() : null;
	}

	@Transient
	private Set<RentalInvoice> getPendingInvoice()
	{
		Set<RentalInvoice> pendingInvoiceSet = new LinkedHashSet<>();
		for (RentalInvoice rInvoice : this.rentalInvoiceSet)
		{
			if (!rInvoice.getActive().booleanValue() && rInvoice.getInvoiceStatus() == EInvoiceStatus.Pending)
				pendingInvoiceSet.add(rInvoice);
		}
		return pendingInvoiceSet;
	}

	@Transient
	@JsonSerialize(using = TwoDecimalSerializer.class)
	public Double getPaymentAmount()
	{
		Double paymentAmt = 0.0;
		for (RentalInvoice rInvoice : this.rentalInvoiceSet)
		{
			paymentAmt += rInvoice.getPaymentAmount();
		}
		return paymentAmt;
	}

}
