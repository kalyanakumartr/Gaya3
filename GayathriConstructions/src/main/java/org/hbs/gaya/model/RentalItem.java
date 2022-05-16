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

import org.hbs.gaya.util.ConstUtil;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RentalItem implements Serializable
{

	private static final long		serialVersionUID	= 160903192897435425L;

	@Id
	@Column(name = "itemId")
	private String					itemId;

	@ManyToOne(targetEntity = Rental.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "rentalId")
	@JsonIgnore
	private Rental					rental;

	@ManyToOne(targetEntity = Inventory.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "inventoryId", nullable = false)
	@JsonIgnore
	private Inventory				inventory;

	@ManyToOne(targetEntity = RentalInvoice.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "invoiceId", nullable = false)
	@JsonIgnore
	private RentalInvoice			rentalInvoice;

	@Column(name = "quantity")
	@Builder.Default
	private Integer					quantity			= 0;

	@Column(name = "balanceQuantity")
	@Builder.Default
	private Integer					balanceQuantity		= 0;

	@Column(name = "price")
	@Builder.Default
	private Double					price				= 0.0;

	@Transient
	@Builder.Default
	private Double					rentalCost			= 0.0;

	@OneToMany(targetEntity = RentalItemHistory.class, fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JsonDeserialize(as = LinkedHashSet.class)
	@Builder.Default
	private Set<RentalItemHistory>	rentalItemHistorys	= new LinkedHashSet<>();

	@Transient
	public String getDataId()
	{
		return this.itemId + "," + this.balanceQuantity;
	}

	@Transient
	public Integer getTotalReturnQuantity()
	{
		int totalReturnQty = 0;
		for (RentalItemHistory itemHistory : this.rentalItemHistorys)
		{
			totalReturnQty += itemHistory.getQuantity();
		}

		return totalReturnQty;
	}
	
	@Transient
	public Double getTotalCost()
	{
		return this.quantity * this.price;
	}

	@Transient
	public String getTotalCost$()
	{
		return ConstUtil.getCurrency(this.getTotalCost());
	}

	@Transient
	public String getRentalCost$()
	{
		return ConstUtil.getCurrency(this.getTotalCost() * rentalInvoice.getNoOfDays$());
	}

	@Transient
	public String getPrice$()
	{
		return ConstUtil.getCurrency(this.price);
	}

	@Transient
	@JsonIgnore
	public RentalItem clone()
	{
		return RentalItem.builder()//
				.quantity(this.balanceQuantity)//
				.balanceQuantity(this.balanceQuantity) // specifically
				.inventory(this.inventory)//
				.price(this.price)//
				.rental(this.rental)//
				.rentalCost(this.rentalCost)//
				.rentalItemHistorys(new LinkedHashSet<>())//
				.build();
	}

}
