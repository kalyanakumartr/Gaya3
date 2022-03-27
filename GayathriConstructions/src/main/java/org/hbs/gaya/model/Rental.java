package org.hbs.gaya.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hbs.gaya.util.EnumInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental")
@Getter
@Setter
@NoArgsConstructor
public class Rental extends CreatedModifiedDateStatus implements Serializable
{
	public enum ERentalStatus implements EnumInterface
	{
		None, Rented, Completed; 
	}
	

	private static final long serialVersionUID = 8952784246497174078L;

	@Id
	@Column(name = "rentalId")
	private String				rentalId;

	@Column(name = "personName")
	private String				personName;

	@Column(name = "mobileNo")
	private String				mobileNo;

	@Column(name = "address")
	private String				address;
	
	@Column(name = "alternateNo")
	private String				alternateNo;

	@Column(name = "advanceAmount")
	private Double				advanceAmount = 0.0;
	
	@Transient
	private Double				overallAmount = 0.0;
	
	@Transient
	private Double				monthlyinvoiceAmount = 0.0;
	
	@Transient
	private Double				paidinvoiceAmount = 0.0;

	@Transient
	private Double				balanceinvoiceAmount = 0.0;

	@Column(name = "rentedDate")
	private LocalDate				rentedDate;
	
	@Column(name = "rentalStatus")
	@Enumerated(EnumType.STRING)
	private ERentalStatus				rentalStatus;
	
	


}
