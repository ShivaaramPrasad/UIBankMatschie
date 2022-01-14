package steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.github.javafaker.Faker;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

public class CheckUIBankModule extends Common{
	public static String uiBankid;
	public static String uiBankUserid;
	public static  String number;
	public static  String firstName;  
    
	public static Map <String, String> allQueryParameter =new HashMap<String, String>();
	
	{
	allQueryParameter.put("filter[where][userId]", ""+uiBankUserid+"");
	}

    
	@Given("enable logs")
	public void setUp(){ 
		request = given().log().all();
		Locale locale = new Locale("en-ZA"); // It will generate India specific data.
		Faker faker = new Faker(locale);
		number = faker.number().digits(8);
		firstName = faker.name().firstName();
	}
	
	@When("login in to Ui Bank")
	public void loginUiBank(){

		response= request
				.body("{\r\n" + 
						"    \"username\": \"shivaaram\",\r\n" + 
						"    \"password\": \"Shivaa.125\"\r\n" + 
						"}")
				.post("users/login");
		response.then()
		.assertThat()
		.statusCode(200)
		.body(containsString("userId"),containsString("id"))
		.extract().response()
		.prettyPrint();	
		uiBankid = response.jsonPath().get("id");
		uiBankUserid = response.jsonPath().get("userId");

		System.out.println("Id: "+uiBankid);	
		System.out.println("User Id: "+uiBankUserid);	
		System.out.println("Status code: "+response.statusCode());

	}

	
	@And("create New Account")
	public void createNewAccount(){

		response= request.header("authorization",uiBankid)
				.body("{\r\n" + 
						"    \"friendlyName\": \""+firstName+"\",\r\n" + 
						"    \"type\": \"savings\",\r\n" + 
						"    \"userId\": \""+uiBankUserid+"\",\r\n" + 
						"    \"balance\": 100,\r\n" + 
						"    \"accountNumber\": "+number+"\r\n" + 
						"}")
				.post("accounts");
		response.then()
		.assertThat()
		.statusCode(200)
		.body(containsString("accountNumber"),containsString("balance"))
		.extract().response()
		.prettyPrint();

	}

	@And("get All Account")
	public void getAllAccounts(){
		response= request.header("authorization",uiBankid)
				.queryParams(allQueryParameter)
				.get("accounts");
		response
		.then()
		.assertThat()
		.statusCode(200)
		.body(containsString("type"),containsString("friendlyName"))
		.extract().response()
		.prettyPrint();		
		System.out.println("Status code: "+response.statusCode());
	}
	
}

