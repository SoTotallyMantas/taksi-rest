Feature: Order Management
  Order Controller is responsible for managing Orders
  Scenario: Get all orders
    When I ask for all Orders
    Then i should get 9 Orders

  Scenario: Get Order by ID
    When I ask for Order by 2
    Then i should get Order by 2

#  Scenario: Create Order
#    When I create Order with address "Testasadress" driverID 1 and clientID 2 and dispatchID 3
#    Then I should get Order created
#
#    Scenario: Get Order by address
#    When I find Order by address "Testasadress"
#    Then i should get Order by address "Testasadress"
#
#   Scenario: Find by driver license plate
#    When I find Order by driver license plate "test"
#    Then i should get Order by driver license plate "test"
#
#  Scenario: Find by client name
#    When I find Order by client name "test2" "test2"
#    Then i should get Order by client name "test2" "test2"
#
#  Scenario: Find by dispatch name
#    When I find Order by dispatch name "test3" "test3"
#    Then i should get Order by dispatch name "test3" "test3"
#
#  Scenario: Find by driver name
#    When I find Order by driver name "test" "test"
#    Then i should get Order by driver name "test" "test"
#
#  Scenario: Find by client phone
#    When I find Order by client phone "test2"
#    Then I should get Order by client phone "test2"
#
#  Scenario: Find by dispatch phone
#    When I find Order by dispatch phone "test3"
#    Then I should get Order by dispatch phone "test3"
#
#  Scenario: Find by driver phone
#    When I find Order by driver phone "test"
#    Then I should get Order by driver phone "test"
#
#    Scenario: Delete Order
#      And I find Order by address "Testasadress"
#      And I assign response to orderID
#        When I delete Order with orderID
#        Then I should get Order deleted





