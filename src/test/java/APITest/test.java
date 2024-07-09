package APITest;

import io.restassured.RestAssured;
import io.restassured.response.Response; 
import org.testng.Assert; 
import org.testng.annotations.Test; 
import io.qameta.allure.Description; 
import io.qameta.allure.Step; 
import io.qameta.allure.Feature;

import static io.restassured.RestAssured.given;

@Feature("API Test")
public class test {

    @Test
    @Description("Add new device")
    public void addNewDevice() {
        RestAssured.baseURI = "https://api.restful-api.dev/objects";

//        String requestBody = "{\n" +
//        			"\"name\": \"Apple Max Pro 1TB\",\n" +
//        			"\"data\": {\n" +
//        			"\"year\": 2023,\n" +
//        			"\"price\": 7999.99,\n" +
//        			"\"CPU model\": \"Apple ARM A7\",\n" +
//                	"\"Hard disk size\": \"1 TB\"\n" +
//                	"}\n" + "}";

        Response response = sendPostRequest("{\n" +
    										"\"name\": \"Apple Max Pro 1TB\",\n" +
    										"\"data\": {\n" +
    										"\"year\": 2023,\n" +
    										"\"price\": 7999.99,\n" +
    										"\"CPU model\": \"Apple ARM A7\",\n" +
    										"\"Hard disk size\": \"1 TB\"\n" +
    										"}\n" +
    										"}");

        String id = response.jsonPath().getString("id");
        String name = response.jsonPath().getString("name");
        String createdAt = response.jsonPath().getString("createdAt");
        int year = response.jsonPath().getInt("data.year");
        double price = response.jsonPath().getDouble("data.price");
        String cpuModel = response.jsonPath().getString("data['CPU model']");
        String hardDiskSize = response.jsonPath().getString("data['Hard disk size']");

        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertEquals(name, "Apple Max Pro 1TB", "Name should be Apple Max Pro 1TB");
        Assert.assertNotNull(createdAt, "CreatedAt should not be null");
        Assert.assertEquals(year, 2023, "Year should be 2023");
        Assert.assertEquals(price, 7999.99, "Price should be 7999.99");
        Assert.assertEquals(cpuModel, "Apple ARM A7", "CPU model should be Apple ARM A7");
        Assert.assertEquals(hardDiskSize, "1 TB", "Hard disk size should be 1TB");

	    System.out.println(response.getBody().asString());

    }

    @Step("POST request: add new device")
    private Response sendPostRequest(String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

}
