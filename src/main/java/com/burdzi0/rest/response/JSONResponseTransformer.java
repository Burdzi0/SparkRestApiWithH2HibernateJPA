package com.burdzi0.rest.response;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JSONResponseTransformer implements ResponseTransformer {

	private Gson json = new Gson();

	@Override
	public String render(Object model) throws Exception {
		return json.toJson(model);
	}
}
