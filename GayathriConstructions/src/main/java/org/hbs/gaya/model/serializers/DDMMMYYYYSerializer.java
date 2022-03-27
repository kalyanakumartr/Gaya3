package org.hbs.gaya.model.serializers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DDMMMYYYYSerializer extends JsonSerializer<LocalDate>
{

	private DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException
	{
		if (value != null)
		{
			gen.writeString(value.format(dtFormat));
		}
	}
}