package org.hbs.gaya.model;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TwoDecimalSerializer extends JsonSerializer<Double>
{
	@Override
	public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
	{
		jgen.writeString("&#x20B9; " + new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
	}
}