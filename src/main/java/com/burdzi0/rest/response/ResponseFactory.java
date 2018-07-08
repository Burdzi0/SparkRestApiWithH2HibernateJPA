package com.burdzi0.rest.response;

import com.google.gson.Gson;
import spark.Response;

public class ResponseFactory {

	private Gson gson = new Gson();

	public String standardResponse(Response response, Object data) {
		return gson.toJson(new StandardResponse(response.status(), data));
	}

	private class StandardResponse {

		private int status;
		private Object data;

		private StandardResponse(int status, Object data) {
			this.status = status;
			this.data = data;
		}
	}
}
