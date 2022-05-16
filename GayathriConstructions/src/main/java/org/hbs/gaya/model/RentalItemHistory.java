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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental_item_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RentalItemHistory implements Serializable
{
	private static final long serialVersionUID = -5902355295555887191L;

	@Id
	@Column(name = "autoId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				autoId;

	@ManyToOne(targetEntity = RentalItem.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "itemId", nullable = false)
	@JsonIgnore
	private RentalItem				item;

	@Column(name = "returnQuantity")
	@Builder.Default
	private Integer				quantity = 0;

	@Column(name = "returnDate")
	private LocalDateTime				returnDate;

}
