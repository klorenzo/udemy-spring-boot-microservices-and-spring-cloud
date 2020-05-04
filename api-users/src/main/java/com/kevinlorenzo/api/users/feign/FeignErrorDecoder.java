package com.kevinlorenzo.api.users.feign;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
		case 400:
			break;
		case 404:
			break;
		default:
			return new Exception(response.reason());
		}
		return null;
	}

}
