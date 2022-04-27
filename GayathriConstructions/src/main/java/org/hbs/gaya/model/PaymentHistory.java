package org.hbs.gaya.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paymenthistory")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PaymentHistory implements Serializable
{
	private static final long	serialVersionUID	= 8495956908004058913L;

	@Id
	@Column(name = "autoId")
	private Long				autoId;
	
	@ManyToOne(targetEntity = Rental.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "rentalId", nullable = false)
	@JsonIgnore
	private Rental			rental;

	@ManyToOne(targetEntity = RentalInvoice.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "invoiceId", nullable = false)
	@JsonIgnore
	private RentalInvoice		rentalInvoice;

	@Column(name = "paymentAmount")
	@JsonSerialize(using = TwoDecimalSerializer.class)
	private Double				paymentAmount		= 0.0;

	@Column(name = "paymentDate")
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
	private LocalDateTime		paymentDate;
	
}
