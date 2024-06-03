Feature: Driver Management

  Scenario: Get All Drivers
    When I get all drivers
    Then I should get 13 drivers

Scenario: Get Driver By Id
    When I get driver by id 1
    Then I should get driver with id 1

  Scenario: Get Driver By Name
    When I get driver by name "test" "test"
    Then I should get driver with name "test" "test"

    Scenario: Get Driver By FirstName
    When I get driver by first name "test"
    Then I should get driver with first name "test"

      Scenario: Get Driver By LastName
    When I get driver by last name "test"
    Then I should get driver with last name "test"

        Scenario: Get Driver by PhoneNumber
    When I get driver by phone number "test"
    Then I should get driver with phone number "test"

 Scenario: Get Driver by License plate
    When I get driver by license plate "test"
    Then I should get driver with license plate "test"

  Scenario: Add new Driver
    When I add new driver with first name "test22" last name "test22" phone number "test22" license plate "test22"
    Then I should get driver with name "test22" "test22" first name "test22" last name "test22" phone number "test22" license plate "test22"
  Scenario: Update Driver
    And  I get driver by name "test22" "test22"
    And I Should set driverID to new created driver
    When I update driver with name "test333" "test333" phone number "test333" license plate "test333"
    Then I should get driver with name "test333" "test333" first name "test333" last name "test333" phone number "test333" license plate "test333"

  Scenario: Delete Driver
    And  I get driver by name "test333" "test333"
    And  I Should set driverID to new created driver
    When I delete driver with driverID
    And I get driver by name "test333" "test333"
    Then I should get null




