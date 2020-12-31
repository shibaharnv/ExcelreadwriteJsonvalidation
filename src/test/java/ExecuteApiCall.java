import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class ExecuteApiCall {

        @Test
        public void executeApiCallTest()  {
            String response = RestAssured.given().log().all()
                    .queryParam("q", "London,UK")
                    .queryParam("appid", "b1b1 5 e88fa797225412429c1c50c122a1")
                    .when().get("https://samples.openweathermap.org/data/2.5/history/city").then()
                    .log().all().assertThat().statusCode(200).extract().response().asString();

            JsonPath js = new JsonPath(response);

             String cityId =js.getString("city_id");

            System.out.println("cityId " +cityId);
        }




}