package org.hbs.gaya.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hbs.gaya.dao.RentalDao;
import org.hbs.gaya.dao.RentalInvoiceDao;
import org.hbs.gaya.dao.RentalItemDao;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.model.RentalItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalBoImpl implements RentalBo
{
	private static final long	serialVersionUID	= 3807282873471737453L;

	@Autowired
	RentalDao					rentalDao;

	@Autowired
	RentalInvoiceDao			rentalInvoiceDao;

	@Autowired
	RentalItemDao				rentalItemDao;

	@Override
	public List<Rental> searchRental(String search)
	{
		return rentalDao.searchRental(search);
	}

	@Override
	public List<RentalItem> searchRentalItem(String rentalId)
	{
		return rentalItemDao.searchRentalItem(rentalId);
	}

	@Override
	public void calculateDayRental()
	{
		RentalInvoice activeInvoice = null;
		LocalDate lastCalculatedDate = null;

		for (Rental rental : rentalDao.getPendingRental(LocalDate.now().atStartOfDay()))
		{
			activeInvoice = rental.getActiveInvoice();
			lastCalculatedDate = activeInvoice.getCalculatedDate().toLocalDate();

			while ( lastCalculatedDate.isBefore(LocalDate.now()) )
			{
				for (RentalItem item : rental.getRentalItemSet())
				{
					activeInvoice.setInvoiceAmount(activeInvoice.getInvoiceAmount() + item.getTotalCost());
				}
				lastCalculatedDate = lastCalculatedDate.plusDays(1);
			}
			activeInvoice.setCalculatedDate(LocalDateTime.now());
			rentalInvoiceDao.save(activeInvoice);

		}

	}
	@Override
	public String getLastRentalId() {
		return rentalDao.getLastRentalId();
	}
	@Override
	public Rental getRentalById(String rentalId)
	{
		Optional<Rental> rentalOpt = rentalDao.findById(rentalId);
		
		if(rentalOpt.isPresent())
			return rentalOpt.get();
		return null;
	}

	@Override
	public Rental getRentalByInvoiceId(String rentalOrInvoiceId)
	{
		return rentalInvoiceDao.getRentalByInvoiceId(rentalOrInvoiceId);
	}

	@Override
	public Rental saveOrUpdate(Rental rental)
	{
		return rentalDao.save(rental);
	}
}