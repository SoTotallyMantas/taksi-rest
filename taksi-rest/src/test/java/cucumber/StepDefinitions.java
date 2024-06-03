package cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.*;


import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {
    private  Long orderID;
    private okhttp3.Response response;
    private okhttp3.Request request;
    private okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
    private static final String BASE_URL = "http://localhost:8090";
    private static final String path = "/orders";

    @When ("I ask for all Orders")
    public void i_ask_for_all_Orders() throws Exception {
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path)
                .build();
        Call call = client.newCall(request);
        response = call.execute();
    }


    @Then("i should get {int} Orders")
    public void iShouldGetOrders(int expectedOrderCount) throws IOException, ParseException {
        String responseBody = response.body().string();


        JSONParser parser = new JSONParser();
        Object parsedResponse = parser.parse(responseBody);

        if (parsedResponse instanceof JSONObject rootObject) {
            Object ordersObj = rootObject.get("orders");
            if (ordersObj instanceof JSONArray) {
                JSONArray ordersArray = (JSONArray) ordersObj;
                assertThat(ordersArray.size())
                        .as("Response is not as expected")
                        .isEqualTo(expectedOrderCount);
            } else {
                throw new IllegalArgumentException("'orders' key does not contain a JSONArray");
            }
        } else if (parsedResponse instanceof JSONArray rootArray) {
            assertThat(rootArray.size())
                    .as("Response is not as expected")
                    .isEqualTo(expectedOrderCount);
        } else {
            throw new IllegalArgumentException("Unexpected JSON structure");
        }
    }

    @When("I ask for Order by {long}")
    public void iAskForOrderByID(long arg0){
        request = new Request.Builder()
                .url(BASE_URL + path + "?id=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("i should get Order by {long}")
    public void iShouldGetOrderByID(long arg0){
        try {
            assertThat(response.code())
                    .as("Response is not as expected")
                    .isEqualTo(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @When("I create Order with address {string} driverID {long} and clientID {long} and dispatchID {long}")
    public void iCreateOrderWithAddressDriverIDAndClientIDAndDispatchID(String arg0, long arg1, long arg2, long arg3) {
        RequestBody emptyBody = RequestBody.create(new byte[0], null);
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "?clientID=" + arg2 + "&dispatchID=" + arg3 + "&driverID=" + arg1 + "&address=" + arg0)
                .post(emptyBody)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("I should get Order created")
    public void iShouldGetOrderCreated() {
        try {
            assertThat(response.code())
                    .as("Response is not as expected")
                    .isEqualTo(200);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @And("I find Order by address {string}")
    public void iFindOrderByAddress(String arg0) {
        request = new Request.Builder()
                .url(BASE_URL + path +"/findByAddress" + "?address=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("I assign response to orderID")
    public void iAssignResponseToOrderID() {
        try {
            String responseBody = response.body().string();
            JSONParser parser = new JSONParser();
            JSONArray rootObject = (JSONArray) parser.parse(responseBody);
            JSONObject object = (JSONObject) rootObject.get(0);
            Number number = (Number) object.get("id");
                orderID = number.longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Then("i should get Order by address {string}")
    public void iShouldGetOrderByAddress(String arg0) {
        String responseBody = null;
        try {
            assert response.body() != null;
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        try {
            Object parsedResponse = parser.parse(responseBody);
            if (parsedResponse instanceof JSONArray) {
                JSONArray rootArray = (JSONArray) parsedResponse;
                if (!rootArray.isEmpty()) {
                    JSONObject driverObject = (JSONObject) rootArray.get(0);
                    assertThat(driverObject.get("address"))
                            .as("Response is not as expected")
                            .isEqualTo(arg0);
                } else {
                    throw new RuntimeException("Response does not contain any Order objects");
                }
            } else if (parsedResponse instanceof JSONObject) {
                JSONObject rootObject = (JSONObject) parsedResponse;
                if (rootObject.containsKey("address")) {
                    assertThat(rootObject.get("address"))
                            .as("Response is not as expected")
                            .isEqualTo(arg0);
                } else {
                    throw new RuntimeException("Response does not contain an address field");
                }
            } else {
                throw new RuntimeException("Unexpected response format");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse response");
        }
    }


    @When("I update Order with address {string} driverID {int} and clientID {int} and dispatchID {int}")
    public void iUpdateOrderWithAddressDriverIDAndClientIDAndDispatchID(String arg0, int arg1, int arg2, int arg3) {
        RequestBody emptyBody = RequestBody.create(new byte[0], null);
        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "?orderID=" + orderID + "&clientID=" + arg2 + "&dispatchID=" + arg3 + "&driverID=" + arg1 + "&address=" + arg0)
                .put(emptyBody)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("I should get Order updated")
    public void iShouldGetOrderUpdated() {
        try {
            assertThat(response.code())
                    .as("Response is not as expected")
                    .isEqualTo(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("I delete Order with orderID")
    public void iDeleteOrderWithOrderID() {

        request = new okhttp3.Request.Builder()
                .url(BASE_URL + path + "?id=" + orderID)
                .delete()
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("I should get Order deleted")
    public void iShouldGetOrderDeleted() {
        try {
            assertThat(response.code())
                    .as("Response is not as expected")
                    .isEqualTo(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("I find Order by driver license plate {string}")
    public void iFindOrderByDriverLicensePlate(String arg0) {
        request = new Request.Builder()
                .url(BASE_URL + path +"/findByLicensePlate" + "?driverLicensePlate=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("i should get Order by driver license plate {string}")
    public void iShouldGetOrderByDriverLicensePlate(String arg0) {
        String responseBody = null;
        try {
            assert response.body() != null;
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        try {
            Object parsedResponse = parser.parse(responseBody);
            if (parsedResponse instanceof JSONArray) {
                JSONArray rootArray = (JSONArray) parsedResponse;
                if (!rootArray.isEmpty()) {
                    JSONObject driverObject = (JSONObject) rootArray.get(0);
                    assertThat(driverObject.get("driver").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                } else {
                    throw new RuntimeException("Response does not contain any Order objects");
                }
            } else if (parsedResponse instanceof JSONObject) {
                // Should find License plate of driver
                JSONObject rootObject = (JSONObject) parsedResponse;
                if (rootObject.containsKey("driver")) {
                    assertThat(rootObject.get("driver").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                } else {
                    throw new RuntimeException("Response does not contain an driver field");
                }
            } else {
                throw new RuntimeException("Unexpected response format");

            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse response");
        }

    }

    @When("I find Order by client name {string} {string}")
    public void iFindOrderByClientName(String arg0, String arg1) {
        request = new Request.Builder()
                .url(BASE_URL + path +"/findByName" + "?clientFirstName=" + arg0 + "&clientLastName=" + arg1)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("i should get Order by client name {string} {string}")
    public void iShouldGetOrderByClientName(String arg0 , String arg1) {
        String responseBody = null;
        try {
            assert response.body() != null;
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        try {
            Object parsedResponse = parser.parse(responseBody);
            if (parsedResponse instanceof JSONArray) {
                JSONArray rootArray = (JSONArray) parsedResponse;
                if (!rootArray.isEmpty()) {
                    JSONObject driverObject = (JSONObject) rootArray.get(0);
                    assertThat(driverObject.get("client").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                    assertThat(driverObject.get("client").toString())
                            .as("Response is not as expected")
                            .contains(arg1);
                } else {
                    throw new RuntimeException("Response does not contain any Order objects");
                }
            } else if (parsedResponse instanceof JSONObject) {

                JSONObject rootObject = (JSONObject) parsedResponse;
                if (rootObject.containsKey("client")) {
                    assertThat(rootObject.get("client").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                    assertThat(rootObject.get("client").toString())
                            .as("Response is not as expected")
                            .contains(arg1);
                } else {
                    throw new RuntimeException("Response does not contain an driver field");
                }
            } else {
                throw new RuntimeException("Unexpected response format");

            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse response");
        }


    }

    @When("I find Order by dispatch name {string} {string}")
    public void iFindOrderByDispatchName(String arg0, String arg1) {
        request = new Request.Builder()
                .url(BASE_URL + path +"/findByName" + "?dispatchFirstName=" + arg0 + "&dispatchLastName=" + arg1)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("i should get Order by dispatch name {string} {string}")
    public void iShouldGetOrderByDispatchName(String arg0, String arg1) {

        String responseBody = null;
        try {
            assert response.body() != null;
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();

        try {
            Object parsedResponse = parser.parse(responseBody);
            if (parsedResponse instanceof JSONArray) {
                JSONArray rootArray = (JSONArray) parsedResponse;
                if (!rootArray.isEmpty()) {
                    JSONObject driverObject = (JSONObject) rootArray.get(0);
                    assertThat(driverObject.get("dispatch").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                    assertThat(driverObject.get("dispatch").toString())
                            .as("Response is not as expected")
                            .contains(arg1);
                } else {
                    throw new RuntimeException("Response does not contain any Order objects");
                }
            } else if (parsedResponse instanceof JSONObject) {
                // Should find License plate of driver
                JSONObject rootObject = (JSONObject) parsedResponse;
                if (rootObject.containsKey("dispatch")) {
                    assertThat(rootObject.get("dispatch").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                    assertThat(rootObject.get("dispatch").toString())
                            .as("Response is not as expected")
                            .contains(arg1);
                } else {
                    throw new RuntimeException("Response does not contain an driver field");
                }
            } else {
                throw new RuntimeException("Unexpected response format");

            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse response");
        }

    }

    @When("I find Order by driver name {string} {string}")
    public void iFindOrderByDriverName(String arg0, String arg1) {
        request = new Request.Builder()
                .url(BASE_URL + path +"/findByName" + "?driverFirstName=" + arg0 + "&driverLastName=" + arg1)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("i should get Order by driver name {string} {string}")
    public void iShouldGetOrderByDriverName(String arg0,String arg1) {

            String responseBody = null;
            try {
                assert response.body() != null;
                responseBody = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONParser parser = new JSONParser();

            try {
                Object parsedResponse = parser.parse(responseBody);
                if (parsedResponse instanceof JSONArray) {
                    JSONArray rootArray = (JSONArray) parsedResponse;
                    if (!rootArray.isEmpty()) {
                        JSONObject driverObject = (JSONObject) rootArray.get(0);
                        assertThat(driverObject.get("driver").toString())
                                .as("Response is not as expected")
                                .contains(arg0);
                        assertThat(driverObject.get("driver").toString())
                                .as("Response is not as expected")
                                .contains(arg1);
                    } else {
                        throw new RuntimeException("Response does not contain any Order objects");
                    }
                } else if (parsedResponse instanceof JSONObject) {

                    JSONObject rootObject = (JSONObject) parsedResponse;
                    if (rootObject.containsKey("driver")) {
                        assertThat(rootObject.get("driver").toString())
                                .as("Response is not as expected")
                                .contains(arg0);
                        assertThat(rootObject.get("driver").toString())
                                .as("Response is not as expected")
                                .contains(arg1);
                    } else {
                        throw new RuntimeException("Response does not contain an driver field");
                    }
                } else {
                    throw new RuntimeException("Unexpected response format");

                }
            }
            catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to parse response");
            }
    }

    @When("I find Order by client phone {string}")
    public void iFindOrderByClientPhone(String arg0) {
        request = new Request.Builder()
                .url(BASE_URL + path +"/findByPhone" + "?clientPhoneNumber=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Then("I should get Order by client phone {string}")
    public void iShouldGetOrderByClientPhone(String arg0) {

            String responseBody = null;
            try {
                assert response.body() != null;
                responseBody = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONParser parser = new JSONParser();

            try {
                Object parsedResponse = parser.parse(responseBody);
                if (parsedResponse instanceof JSONArray) {
                    JSONArray rootArray = (JSONArray) parsedResponse;
                    if (!rootArray.isEmpty()) {
                        JSONObject driverObject = (JSONObject) rootArray.get(0);
                        assertThat(driverObject.get("client").toString())
                                .as("Response is not as expected")
                                .contains(arg0);
                    } else {
                        throw new RuntimeException("Response does not contain any Order objects");
                    }
                } else if (parsedResponse instanceof JSONObject) {

                    JSONObject rootObject = (JSONObject) parsedResponse;
                    if (rootObject.containsKey("client")) {
                        assertThat(rootObject.get("client").toString())
                                .as("Response is not as expected")
                                .contains(arg0);
                    } else {
                        throw new RuntimeException("Response does not contain an driver field");
                    }
                } else {
                    throw new RuntimeException("Unexpected response format");

                }
            }
            catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to parse response");
            }
    }

    @When("I find Order by dispatch phone {string}")
    public void iFindOrderByDispatchPhone(String arg0) {
        request = new Request.Builder()
                .url(BASE_URL + path +"/findByPhone" + "?dispatchPhoneNumber=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("I should get Order by dispatch phone {string}")
    public void iShouldGetOrderByDispatchPhone(String arg0) {
        String responseBody = null;
        try {
            assert response.body() != null;
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();

        try {
            Object parsedResponse = parser.parse(responseBody);
            if (parsedResponse instanceof JSONArray) {
                JSONArray rootArray = (JSONArray) parsedResponse;
                if (!rootArray.isEmpty()) {
                    JSONObject driverObject = (JSONObject) rootArray.get(0);
                    assertThat(driverObject.get("dispatch").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                } else {
                    throw new RuntimeException("Response does not contain any Order objects");
                }
            } else if (parsedResponse instanceof JSONObject) {
                // Should find License plate of driver
                JSONObject rootObject = (JSONObject) parsedResponse;
                if (rootObject.containsKey("dispatch")) {
                    assertThat(rootObject.get("dispatch").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                } else {
                    throw new RuntimeException("Response does not contain an driver field");
                }
            } else {
                throw new RuntimeException("Unexpected response format");

            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse response");
        }
    }

    @When("I find Order by driver phone {string}")
    public void iFindOrderByDriverPhone(String arg0) {
        request = new Request.Builder()
                .url(BASE_URL + path +"/findByPhone" + "?driverPhoneNumber=" + arg0)
                .build();
        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("I should get Order by driver phone {string}")
    public void iShouldGetOrderByDriverPhone(String arg0) {
        String responseBody = null;
        try {
            assert response.body() != null;
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();

        try {
            Object parsedResponse = parser.parse(responseBody);
            if (parsedResponse instanceof JSONArray) {
                JSONArray rootArray = (JSONArray) parsedResponse;
                if (!rootArray.isEmpty()) {
                    JSONObject driverObject = (JSONObject) rootArray.get(0);
                    assertThat(driverObject.get("driver").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                } else {
                    throw new RuntimeException("Response does not contain any Order objects");
                }
            } else if (parsedResponse instanceof JSONObject) {
                // Should find License plate of driver
                JSONObject rootObject = (JSONObject) parsedResponse;
                if (rootObject.containsKey("driver")) {
                    assertThat(rootObject.get("driver").toString())
                            .as("Response is not as expected")
                            .contains(arg0);
                } else {
                    throw new RuntimeException("Response does not contain an driver field");
                }
            } else {
                throw new RuntimeException("Unexpected response format");

            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse response");
        }
    }

}

