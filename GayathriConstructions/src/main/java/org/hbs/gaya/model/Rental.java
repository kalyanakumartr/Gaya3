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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	private static final long serialVersionUID = 8952784246497174078L;

	@Id
	@Column(name = "rentalId")
	private String				rentalId;
	
	@Column(name = "address")
	private String				address;

	@Column(name = "advanceAmount")
	private Double				advanceAmount = 0.0;

	@Column(name = "alternateNo")
	private String				alternateNo;
	
	@Column(name = "mobileNo")
	private String				mobileNo;
	
	@Column(name = "personName")
	private String				personName;
	
	@Column(name = "rentalStatus")
	@Enumerated(EnumType.STRING)
	private ERentalStatus				rentalStatus;

	@Column(name = "rentedDate")
	private LocalDate				rentedDate;

	@Column(name = "createdDate")
	private LocalDateTime		createdDate;

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "createdBy", nullable = false)
	private Users				createdUser;

}
