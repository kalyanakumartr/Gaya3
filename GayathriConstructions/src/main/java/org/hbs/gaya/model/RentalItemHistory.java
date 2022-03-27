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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental_item_history")
@Getter
@Setter
@NoArgsConstructor
public class RentalItemHistory implements Serializable
{
	private static final long serialVersionUID = -5902355295555887191L;

	@Id
	@Column(name = "autoId")
	private Long				autoId;

	@ManyToOne(targetEntity = RentalItem.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "itemId", nullable = false)
	private RentalItem				item;

	@Column(name = "returnQuantity")
	private Integer				quantity = 0;

	@Column(name = "returnDate")
	private LocalDateTime				returnDate;

}
