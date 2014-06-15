package br.com.controleprocessos.view.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import br.com.controleprocessos.business.utils.DataUtil;
import br.com.controleprocessos.view.config.BigDecimalTypeAdapter;
import br.com.controleprocessos.view.config.HibernateProxyTypeAdapter;

import com.google.gson.GsonBuilder;

public class GsonUtils {

	public String messagesToJson(String... messages) {
		return toJson(Arrays.asList(messages));
	}

	public String messagesToJson(List<String> messages) {
		return toJson(messages);
	}

	public String toJson(Object o) {
		return toJson(o, DataUtil.JS_PATTERN);
	}

	public String toJson(Object o, String pattern) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		gsonBuilder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
		gsonBuilder.setDateFormat(pattern);

		return gsonBuilder.create().toJson(o);
	}

}
