package org.hbs.gaya.model.serializers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hbs.gaya.util.CommonValidator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DDMMMYYYYDeserializer extends JsonDeserializer<LocalDate>
{
	private DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

	@Override
	public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException
	{
		if (CommonValidator.isNotNullNotEmpty(jsonParser) && CommonValidator.isNotNullNotEmpty(jsonParser.getText()))

			return LocalDate.parse(jsonParser.getText(), dtFormat);
		return null;
	}
}