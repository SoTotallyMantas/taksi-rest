package cucumber;

import io.cucumber.java.en.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitionsDriver {
    private  Long DriverID;
    private okhttp3.Response response;
    private okhttp3.Request request;
    private okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
    private static final String BASE_URL = "http://localhost:8090";
    private static final String path = "/drivers";
    @io.cucumber.java.en.When("I get all drivers")
    public void iGetAllDrivers() throws IOException {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path)
                .build();
        Call call = client.newCall(request);
        response = call.execute();

    }

    @Then("I should get {int} drivers")
    public void iShouldSeeAListOfDrivers(int arg0) throws IOException, ParseException {
        assert response.body() != null;
        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();

        JSONArray rootObject = (JSONArray) parser.parse(responseBody);

        assertThat(rootObject.size())
                .as("Response is not as expected")
                .isEqualTo(arg0);
    }

    @When("I get driver by id {int}")
    public void iGetDriverById(int arg0) {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "?driverID=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("I should get driver with id {int}")
    public void iShouldGetDriverWithId(int arg0) {

        String responseBody = null;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();

        }
        JSONParser parser = new JSONParser();
        JSONArray rootObject = null;
        try {
            rootObject = (JSONArray) parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        JSONObject driver = (JSONObject) rootObject.get(0);
        assertThat(driver.get("id"))
                .as("Response is not as expected")
                .isEqualTo(arg0);
    }

    @When("I get driver by name {string} {string}")
    public void iGetDriverByName(String arg0, String arg1) {

        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path+ "/findByName" + "?firstName=" + arg0 + "&lastName=" + arg1)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("I should get driver with name {string} {string}")
    public void iShouldGetDriverWithName(String arg0, String arg1) {
        String responseBody = null;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            {
                JSONParser parser = new JSONParser();
                JSONArray rootObject = null;
                try {
                    rootObject = (JSONArray) parser.parse(responseBody);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                JSONObject driver = (JSONObject) rootObject.get(0);
                assertThat(driver.get("firstName"))
                        .as("Response is not as expected")
                        .isEqualTo(arg0);
                assertThat(driver.get("lastName"))
                        .as("Response is not as expected")
                        .isEqualTo(arg1);
            }
        }
    }

    @When("I get driver by first name {string}")
    public void iGetDriverByFirstName(String arg0) {

        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "/findByName" + "?firstName=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("I should get driver with first name {string}")
    public void iShouldGetDriverWithFirstName(String arg0) {
        String responseBody = null;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONArray rootObject = null;
        try {
            rootObject = (JSONArray) parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject driver = (JSONObject) rootObject.get(0);
        assertThat(driver.get("firstName"))
                .as("Response is not as expected")
                .isEqualTo(arg0);
    }

    @When("I get driver by last name {string}")
    public void iGetDriverByLastName(String arg0) {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "/findByName" + "?lastName=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("I should get driver with last name {string}")
    public void iShouldGetDriverWithLastName(String arg0) {
        String responseBody = null;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONArray rootObject = null;
        try {
            rootObject = (JSONArray) parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject driver = (JSONObject) rootObject.get(0);
        assertThat(driver.get("lastName"))
                .as("Response is not as expected")
                .isEqualTo(arg0);

    }

    @When("I get driver by phone number {string}")
    public void iGetDriverByPhoneNumber(String arg0) {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "/findByPhoneNumber" + "?phoneNumber=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("I should get driver with phone number {string}")
    public void iShouldGetDriverWithPhoneNumber(String arg0) {
        String responseBody = null;
        try {
            assert response.body() != null;
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        JSONObject rootObject = null;
        try {
            rootObject = (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert rootObject != null;
        if (rootObject.containsKey("phoneNumber")) {
            String phoneNumber = (String) rootObject.get("phoneNumber");
            assertThat(phoneNumber)
                    .as("Response is not as expected")
                    .isEqualTo(arg0);
        } else {
            throw new RuntimeException("Response does not contain a 'phoneNumber' property");
        }
    }

    @When("I get driver by license plate {string}")
    public void iGetDriverByLicensePlate(String arg0) {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "/findByLicensePlate" + "?licensePlate=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("I should get driver with license plate {string}")
    public void iShouldGetDriverWithLicensePlate(String arg0) {
        String responseBody = null;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONArray rootObject = null;
        try {
            rootObject = (JSONArray) parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject driver = (JSONObject) rootObject.get(0);
        assertThat(driver.get("licensePlate"))
                .as("Response is not as expected")
                .isEqualTo(arg0);

    }

    @When("I delete driver with id {int}")
    public void iDeleteDriverWithId(int arg0) {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "?driverID=" + arg0)
                .delete()
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @When("I add new driver with first name {string} last name {string} phone number {string} license plate {string}")
    public void iAddNewDriverWithNameFirstNameLastNamePhoneNumberLicensePlate(String arg0, String arg1, String arg2, String arg3) {
        RequestBody emptyBody = RequestBody.create(new byte[0], null);
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "?phoneNumber=" + arg2 + "&firstName=" + arg0 + "&lastName=" + arg1 + "&licensePlate=" + arg3)
                .post(emptyBody)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("I should get driver with name {string} {string} first name {string} last name {string} phone number {string} license plate {string}")
    public void iShouldGetDriverWithNameFirstNameLastNamePhoneNumberLicensePlate(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        String responseBody = null;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        JSONObject rootObject = null;
        try {
            rootObject = (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertThat(rootObject.get("firstName"))
                .as("Response is not as expected")
                .isEqualTo(arg2);
        assertThat(rootObject.get("lastName"))
                .as("Response is not as expected")
                .isEqualTo(arg3);
        assertThat(rootObject.get("phoneNumber"))
                .as("Response is not as expected")
                .isEqualTo(arg4);
        assertThat(rootObject.get("licensePlate"))
                .as("Response is not as expected")
                .isEqualTo(arg5);
    }


    @And("I Should set driverID to new created driver")
    public void iShouldSetDriverIDToNewCreatedDriverWithName() {
        String responseBody = null;
        try {
            assert response.body() != null;
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }



        JSONParser parser = new JSONParser();
        Object parsedResponse = null;
        try {
            parsedResponse = parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (parsedResponse instanceof JSONArray rootObject) {
            if (rootObject.isEmpty()) {
                throw new AssertionError("Response array is empty");
            }
            JSONObject driver = (JSONObject) rootObject.get(0);
            Number idNumber = (Number) driver.get("id");
            DriverID = idNumber.longValue();
        } else if (parsedResponse instanceof JSONObject driver) {
            Number idNumber = (Number) driver.get("id");
            DriverID = idNumber.longValue();
        } else {
            throw new AssertionError("Unexpected response type: " + parsedResponse.getClass().getName());
        }
    }

    @And("I Search for driver with name {string} {string}")
    public void iSearchForDriverWithName(String arg0, String arg1) {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "/findByName" + "?firstName=" + arg0 + "&lastName=" + arg1)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();


        }


    }



    @When("I update driver with name {string} {string} phone number {string} license plate {string}")
    public void iUpdateDriverWithNamePhoneNumberLicensePlate(String arg0, String arg1, String arg2, String arg3) {
        RequestBody emptyBody = RequestBody.create(new byte[0], null);
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "?id=" + DriverID + "&phoneNumber=" + arg2 + "&firstName=" + arg0 + "&lastName=" + arg1 + "&licensePlate=" + arg3)
                .put(emptyBody)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("I should get first name {string} last name {string} phone number {string} license plate {string}")
    public void iShouldGetFirstNameLastNamePhoneNumberLicensePlate(String arg0, String arg1, String arg2, String arg3) {
        String responseBody = null;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        JSONArray rootObject = null;
        try {
            rootObject = (JSONArray) parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (rootObject.size() > 0) {
            JSONObject driverObject = (JSONObject) rootObject.get(0);
            assertThat(driverObject.get("firstName"))
                    .as("Response is not as expected")
                    .isEqualTo(arg0);
            assertThat(driverObject.get("lastName"))
                    .as("Response is not as expected")
                    .isEqualTo(arg1);
            assertThat(driverObject.get("phoneNumber"))
                    .as("Response is not as expected")
                    .isEqualTo(arg2);
            assertThat(driverObject.get("licensePlate"))
                    .as("Response is not as expected")
                    .isEqualTo(arg3);
        } else {
            throw new RuntimeException("Response does not contain any driver objects");
        }
    }

    @When("I delete driver with driverID")
    public void iDeleteDriverWithDriverID() {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "?id=" + DriverID)
                .delete()
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Then("I should get null")
    public void iShouldGetDriverWithNameNotFound() {

        String responseBody = null;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(responseBody)
                .as("Response is not as expected")
                .contains("[]");

    }
}
