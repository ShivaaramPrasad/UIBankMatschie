package steps;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class Common {
	protected static Response response;
	protected static ValidatableResponse json;
	protected static RequestSpecification request;
}
