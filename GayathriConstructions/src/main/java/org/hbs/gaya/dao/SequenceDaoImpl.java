package org.hbs.gaya.dao;

import java.security.InvalidKeyException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hbs.gaya.model.Sequence;
import org.hbs.gaya.util.CommonValidator;
import org.hbs.gaya.util.EBusinessKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceDaoImpl implements SequenceDao
{
	private static final long	serialVersionUID	= 1722249372045340263L;

	@Autowired
	PrimaryDao					primaryDao;

	private Sequence getGenerateSequenceKey(String keyName) throws InvalidKeyException
	{
		Sequence sequence = primaryDao.findBySequenceKey(keyName);
		if (CommonValidator.isNotNullNotEmpty(sequence) && sequence.getSequenceId() > 0L)
		{
			sequence.setSequenceId(sequence.getSequenceId() + 1);
			primaryDao.save(sequence);
			return sequence;
		}
		throw new InvalidKeyException("Sequence Id NOT received for " + keyName);
	}
	
	@Override
	public String create(String keyName)
	{
		return create(keyName, new LinkedHashMap<>());
	}
	
	@Override
	public String create(String keyName, Map<String, String> dataMap)
	{
		try
		{
			return getGenerateSequenceKey(keyName).generate(dataMap);
		}
		catch (InvalidKeyException e)
		{
			return EBusinessKey.EKey.auto();
		}
	}
}
