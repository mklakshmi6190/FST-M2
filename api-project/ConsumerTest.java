package liveproject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslRequestWithoutPath;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Headers
    Map<String, String> reqHeaders = new HashMap<>();
    //Resource Path
    String resourcePath = "/api/users";

    @Pact(consumer="UserConsumer",provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        //Set Headers
        reqHeaders.put("Content-Type","application/json");

        //Create Body
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id",123)
                .stringType("firstName","Maha")
                .stringType("lastName","lakshmi")
                .stringType("email","maha@gmail.com");
                //The values passed doesnt matter as it works only after integration, ie) going to live.
                //As its just a contract testing, those values are not going to be used anywhere

        //Create the contract
        //from provider perspective ie) when we get request matching the below criteria, then respond with
        //these are called fragments
        return builder.given("Request to create a user")
                .uponReceiving("Request to create a user")
                    .method("POST")
                    .path(resourcePath)
                    .headers(reqHeaders)
                    .body(reqResBody)
                .willRespondWith()
                    .status(201)
                    .body(reqResBody)
                .toPact();
    }
        @Test
        @PactTestFor(providerName = "UserProvider",port="8282")
        public void consumerTest(){
        //Set the baseURI
        String baseURI = "http://localhost:8282";

        //Set the Request Body
            Map<String,Object> reqBody = new HashMap<>();
            reqBody.put("id",123);
            reqBody.put("firstName","Maha");
            reqBody.put("lastName","Lakshmi");
            reqBody.put("email","maha@gmail.com");
            //The values we passed doesnt matter, we can even pass empty string, but as we defined map it needs two values

            //Generate Response
            Response response = given().headers(reqHeaders).body(reqBody)
                    .when().post(baseURI + resourcePath);

            //Print the response
            System.out.println(response.getBody().asPrettyString());

            //Assertions
            response.then().statusCode(201);

        }



}
//After executing, it will create pacts folder under target - a json file which defines everything a contract needs
