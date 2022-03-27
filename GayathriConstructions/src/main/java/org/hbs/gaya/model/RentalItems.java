package org.hbs.gaya.model;

import java.io.Serializable;

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
@Table(name = "rental_items")
@Getter
@Setter
@NoArgsConstructor
public class RentalItems implements Serializable
{

	private static final long serialVersionUID = 160903192897435425L;

	@Id
	@Column(name = "itemId")
	private String				itemId;

	@ManyToOne(targetEntity = Rental.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "rentalId", nullable = false)
	private Rental				rental;

	@Column(name = "quantity")
	private Integer				quantity = 0;

	@Column(name = "returnQuantity")
	private Integer				returnQuantity = 0;

	@Column(name = "brokenQuantity")
	private Integer				brokenQuantity = 0;
	
	@Column(name = "price")
	private Double				price = 0.0;
	
	@Column(name = "totalCost")
	private Double				totalCost = 0.0;

}
