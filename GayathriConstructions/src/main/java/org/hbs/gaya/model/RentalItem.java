package org.hbs.gaya.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RentalItem implements Serializable
{

	private static final long	serialVersionUID	= 160903192897435425L;

	@Id
	@Column(name = "itemId")
	private String				itemId;

	@ManyToOne(targetEntity = Rental.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "rentalId", nullable = false)
	@JsonIgnore
	private Rental				rental;

	@ManyToOne(targetEntity = Inventory.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "inventoryId", nullable = false)
	@JsonIgnore
	private Inventory			inventory;

	@Column(name = "quantity")
	private Integer				quantity			= 0;

	@Column(name = "balanceQuantity")
	private Integer				balanceQuantity		= 0;

	@Column(name = "price")
	private Double				price				= 0.0;

	@Column(name = "totalCost")
	private Double				totalCost			= 0.0;
	
	@OneToMany(targetEntity = RentalItemHistory.class, fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JsonDeserialize(as = LinkedHashSet.class)
	private Set<RentalItemHistory>		rentalItemHistorys = new LinkedHashSet<>();
	
	@Transient
	public Integer getTotalReturnQuantity()
	{
		int totalReturnQty = 0;
		for(RentalItemHistory itemHistory : rentalItemHistorys)
		{
			totalReturnQty += itemHistory.getQuantity();
		}
		
		return totalReturnQty;
	}

}
