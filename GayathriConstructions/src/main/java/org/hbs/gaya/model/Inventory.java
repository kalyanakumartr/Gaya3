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
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
public class Inventory extends CreatedModifiedDateStatus implements Serializable
{

	private static final long	serialVersionUID	= -8743496249588408243L;

	@Id
	@Column(name = "inventoryId")
	private String				inventoryId;

	@ManyToOne(targetEntity = Material.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "materialId", nullable = false)
	private Material				material;

	@Column(name = "materialCost")
	private Double				materialCost;

	@Column(name = "rentalItemCost")
	private Double				rentalItemCost;

	@Column(name = "availableQuantity")
	private Integer				availableQuantity;

	@Column(name = "rentedQuantity")
	private Integer				rentedQuantity;
	
	@Column(name = "brokenQuantity")
	private Integer				brokenQuantity;
	
	@Column(name = "quantity")
	private Integer				quantity;
}
