package org.hbs.gaya.model.serializers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hbs.gaya.util.CommonValidator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DDMMMYYYYLDTDeserializer extends JsonDeserializer<LocalDateTime>
{
	private DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException
	{
		if (jsonParser !=null && CommonValidator.isNotNullNotEmpty(jsonParser.getText()))
			return LocalDateTime.parse(jsonParser.getText(), dtFormat);
		return null;
	}
}