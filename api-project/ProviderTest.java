package liveproject;
//we need a actual API Server

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("UserProvider")
@PactFolder("target/pacts")
//@PactBroker(host="")
//@PactURL(urls=) we are not using these as we have it already in the folder

public class ProviderTest {

@BeforeEach
    public void setUp(PactVerificationContext context) {
    HttpTestTarget target = new HttpTestTarget("localhost", 8585);
    context.setTarget(target);
}
//State is what going to trigger the test, not the test so we are going to use testtemplate -
// rather than 10 different test, we are using 10 different states
@TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void providerTest(PactVerificationContext context){
        context.verifyInteraction();
}
@State("Request to create a user")
    public void state1(){

}
}
