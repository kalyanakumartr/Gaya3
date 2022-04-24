package org.hbs.gaya.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
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

}
