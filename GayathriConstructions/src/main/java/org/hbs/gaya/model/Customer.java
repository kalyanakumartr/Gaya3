package org.hbs.gaya.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Customer implements Serializable
{
	private static final long	serialVersionUID	= 8952784246497174078L;

	@Id
	@Column(name = "customerId")
	private String				customerId;

	@Column(name = "address")
	private String				address;

	@Column(name = "alternateNo")
	private String				alternateNo;

	@Column(name = "mobileNo")
	private String				mobileNo;

	@Column(name = "customerName")
	private String				customerName;
	
	@OneToMany(targetEntity = Rental.class, fetch = FetchType.EAGER, mappedBy = "customer")
	@Fetch(FetchMode.JOIN)
	@OrderBy("rentedDate Desc")
	@Builder.Default
	@JsonIgnore
	private Set<Rental>	rentalSet	= new LinkedHashSet<>();


}
