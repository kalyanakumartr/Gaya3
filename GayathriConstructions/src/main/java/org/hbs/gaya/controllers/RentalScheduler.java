package org.hbs.gaya.controllers;

import java.io.Serializable;

import org.hbs.gaya.bo.RentalBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RentalScheduler implements Serializable
{
	@Autowired
	RentalBo rentalBo;
	
	private static final long serialVersionUID = -6450747979672312131L;
	private static final String	ASIA_KOLKATA			= "Asia/Kolkata";
	private static final String	MIDNIGHT_12				= "0 0 0 * * *";		// On_Every_Day_12AM
	private static final String ONE_MINUTE = "0 0/1 * * * *";
	
	@Scheduled(cron = MIDNIGHT_12, zone = ASIA_KOLKATA)
	public void calculateDayRental()
	{
		rentalBo.calculateDayRental();
	}
}
