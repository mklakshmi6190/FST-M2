import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.jfr.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GithubTest {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String sshKey = "token ";
    int id;
    String resourcePath = "/user/keys";

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization",sshKey) //.setAuth(oauth2(sshKey))
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType("application/json")
                .expectStatusCode(204)
                .expectBody("message",equalTo(id))
                .build();


    }

    @Test(priority = 1)
    public void postRequest(){

       /* Map<String,String> reqBody = new HashMap<>();
        reqBody.put("title","TestAPIKey");
        reqBody.put("key", "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCn4ACXpOqORgfprqohtA/CU6tm2YUIRpXmM5mX9//wZbOGA6Sgy6IXs0LC9Baszm4z9FaYCmhJEDOyqKMK64wGkL7AehNo8fdG6kd+JsPZPVjLd2e1xf00E4fopgU08xZWdDndRkQWPSYRC3bGazM91VcwrtsJ6a4g0ktOnX82gQ6grLZpoK+4cV5Hs9KQ02XqbBQ8PKu+gC9RJMnADAab+aY8azrCFCRPFsm9wZpR67SOZ1vFdV/gp9ojnnRr+7aF/l1qXRviPBkFzx/siKLtKi1WkR4SzM4W4qbSv3oVM59ZeD0r6VC+maHBWHWE1Kw9d/sUZjsiKZI4PYd65OpT");
        */
        String reqBody = "{\"title\": \"TestAPIKey\",\"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCn4ACXpOqORgfprqohtA/CU6tm2YUIRpXmM5mX9//wZbOGA6Sgy6IXs0LC9Baszm4z9FaYCmhJEDOyqKMK64wGkL7AehNo8fdG6kd+JsPZPVjLd2e1xf00E4fopgU08xZWdDndRkQWPSYRC3bGazM91VcwrtsJ6a4g0ktOnX82gQ6grLZpoK+4cV5Hs9KQ02XqbBQ8PKu+gC9RJMnADAab+aY8azrCFCRPFsm9wZpR67SOZ1vFdV/gp9ojnnRr+7aF/l1qXRviPBkFzx/siKLtKi1WkR4SzM4W4qbSv3oVM59ZeD0r6VC+maHBWHWE1Kw9d/sUZjsiKZI4PYd65OpT\"}";

        Response response =given().spec(requestSpec)
                .body(reqBody)
                .when().post(resourcePath);

        System.out.println(response.getBody().asPrettyString());
        //Extract and save it in the variable
        id=response.then().extract().path("id");
        System.out.println("ID Value in Post Request is: "+id);
        //Assertion
        response.then().statusCode(201);

    }

    @Test(priority = 2)
    public void getRequest(){
    Response response =given().spec(requestSpec)
            .pathParam("keyId",id)
            .when().get(resourcePath+"/{keyId}");
        System.out.println("ID Value in Get Request is: "+id);
        System.out.println("Get Request Response: " +response.getBody().asString());

        response.then().body("id",equalTo(id));
    }

    @Test(priority = 3)
    public void deleteRequest(){
    Response response = given().spec(requestSpec)
            .pathParam("keyId",id)
            .when().delete(resourcePath+"/{keyId}");
    System.out.println("Delete Response:" +response.getBody().asString());
    System.out.println("ID Value in Delete Request is: "+id);
    response.then().statusCode(204);

    }
}
