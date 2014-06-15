package br.com.controleprocessos.view.config;

import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class BigDecimalTypeAdapter extends TypeAdapter<BigDecimal> {

	@Override
	public BigDecimal read(JsonReader in) throws IOException {
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public void write(JsonWriter out, BigDecimal value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}

		out.value(value.doubleValue());
	}
}
