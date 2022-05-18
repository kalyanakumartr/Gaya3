package org.hbs.gaya.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hbs.gaya.dao.RentalDao;
import org.hbs.gaya.dao.RentalInvoiceDao;
import org.hbs.gaya.dao.RentalItemDao;
import org.hbs.gaya.dao.SequenceDao;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.util.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalBoImpl implements RentalBo
{
	private static final long	serialVersionUID	= 3807282873471737453L;

	@Autowired
	RentalDao					rentalDao;

	@Autowired
	SequenceDao					sequenceDao;

	@Autowired
	RentalInvoiceDao			rentalInvoiceDao;

	@Autowired
	RentalItemDao				rentalItemDao;

	@Override
	public List<Rental> searchRental(String search, boolean includeDraft)
	{
		if(includeDraft)
			return rentalDao.searchRentalWithDraft(search);
		else
			return rentalDao.searchRental(search);
	}

	@Override
	public void calculateDayRental()
	{
		RentalInvoice activeInvoice = null;
		LocalDate lastCalculatedDate = null;

		LocalDateTime currDate = LocalDate.now().atStartOfDay();

		for (Rental rental : rentalDao.getPendingRental(currDate))
		{
			activeInvoice = rental.getActiveInvoice();
			lastCalculatedDate = activeInvoice.getCalculatedDate().toLocalDate();

			while ( lastCalculatedDate.isBefore(currDate.toLocalDate()) )
			{
				activeInvoice.setInvoiceAmount(activeInvoice.getInvoiceAmount() + activeInvoice.getItemsTotalCost());
				lastCalculatedDate = lastCalculatedDate.plusDays(1);
			}

			activeInvoice.setCalculatedDate(LocalDateTime.now());
			rentalInvoiceDao.save(activeInvoice);
		}
	}

	@Override
	public String calculateDayRentalForSpecificRentalId(String rentalId) throws Exception
	{
		RentalInvoice activeInvoice = null;
		LocalDate lastCalculatedDate = null;

		Rental rental = rentalDao.getById(rentalId);
		activeInvoice = rental.getActiveInvoice();
		lastCalculatedDate = activeInvoice.getCalculatedDate().toLocalDate();

		if (lastCalculatedDate.isBefore(LocalDate.now()))
		{
			while ( lastCalculatedDate.isBefore(LocalDate.now()) )
			{
				activeInvoice.setInvoiceAmount(activeInvoice.getInvoiceAmount() + activeInvoice.getItemsTotalCost());
				lastCalculatedDate = lastCalculatedDate.plusDays(1);
			}

			activeInvoice.setCalculatedDate(LocalDateTime.now());
			rentalInvoiceDao.save(activeInvoice);
			return ConstUtil.SUCCESS;
		}
		else if (lastCalculatedDate.isAfter(LocalDate.now()))
			throw new Exception("Future Date");
		
		return ConstUtil.ERROR;
	}

	@Override
	public Rental getRentalById(String rentalId)
	{
		Optional<Rental> rentalOpt = rentalDao.findById(rentalId);

		if (rentalOpt.isPresent())
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