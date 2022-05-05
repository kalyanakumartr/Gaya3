package org.hbs.gaya.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PaymentHistory implements Serializable
{
	private static final long	serialVersionUID	= 8495956908004058913L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "autoId")
	private Long				autoId;

	@ManyToOne(targetEntity = Rental.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "rentalId", nullable = false)
	@JsonIgnore
	private Rental				rental;

	@ManyToOne(targetEntity = RentalInvoice.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "invoiceId", nullable = false)
	@JsonIgnore
	private RentalInvoice		rentalInvoice;

	@Column(name = "paymentAmount")
	@JsonSerialize(using = TwoDecimalSerializer.class)
	@Builder.Default	
	private Double				paymentAmount		= 0.0;
	
	@Column(name = "discountAmount")
	@JsonSerialize(using = TwoDecimalSerializer.class)
	@Builder.Default
	private Double				discountAmount		= 0.0;

	@Column(name = "paymentDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime		paymentDate;

	@Transient
	@JsonProperty("invoiceNo")
	public String getInvoiceNo()
	{
		return ((rentalInvoice.getInvoiceNo() != null && rentalInvoice.getInvoiceDate() != null) ? rentalInvoice.getInvoiceNo() : "Current Invoice") ;
	}

	@Transient
	@JsonProperty("status")
	public String getStatus()
	{
		return rentalInvoice.getInvoiceStatus().name();
	}
}
