@employee @createEmployee
Feature: Add Employee
  Adding employee

  Background:
    Given Employee system is started and it has three department 1, 2, and 3

  @main @events @addEvent
  Scenario Outline: Create Employee
    When user provides valid employee information to create a valid employee <firstName>, <lastName>, <email>, <birthday>, and <department>
    Then system creates employee and provide an auto increment employee uuid
    And add employee event will be recorded
    #notice that requirement did not specify this uuid should be of type UUID so I wil assume it is Long for simplicity.
    Examples:
      | firstName | lastName | email            | birthday    | department |
      | nana      | smith    | smith@go.com     | 1990-10-20  | 1          |
      | lana      | dora     | lana.dora@aa.de  | 2001-10-30  | 1          |
      | kamal     | dora     | kamal.dora@aa.de | 2001-11-30  | 3          |


  #TODO: add more test cases to email validation
  @alternative
  Scenario Outline: Create Employee with an invalid email
    When user provides invalid email with employee information to create an employee <firstName>, <lastName>, <email>, <birthday>, and <department>
    Then system returns invalid email information
    Examples:
      | firstName | lastName | email            | birthday    | department |
      | hana      | smith    | hana.go.com      | 1990-10-20  | 1          |
      | lana      | dora     | lana             | 2001-10-30  | 1          |
      | kamal     | dora     | kamal.dora@aa    | 2001-11-30  | 3          |
      | kamal     | dora     | 123              | 2001-11-30  | 2          |
      #| kamal     | smith    | -smith@foo.com   | 2001-11-30  | 2          |  TODO: is this valid email
      #| kamal     | smith    | .smith@foo.com   | 2001-11-30  | 1          |  TODO: is this valid email
      | kamal     | smith    | smith@foo_com    | 2001-11-30  | 2          |
      | kamal     | smith    | smith@foo@com.de | 2001-11-30  | 3          |
      | kamal     | smith    | smith@.com       | 2001-11-30  | 2          |



  @alternative
  Scenario Outline: Create Employee with an exiting email
    When user provides exiting email with employee information to create an employee <firstName>, <lastName>, <email>, <birthday>, and <department>
    Then system returns exiting email information
    Examples:
      | firstName | lastName | email                  | birthday    | department |
      | nana      | smith    | smithExisit@go.com     | 1990-10-20  | 1          |
      | lana      | dora     | lana.Exist@aa.de       | 2001-10-30  | 2          |
      | kamal     | dora     | kamal.Exist@aa.de      | 2001-11-30  | 3          |

  #TODO: add more test cases
  @alternative
  Scenario Outline: Create Employee with invalid birthday
    When user provides invalid birthday with employee information to create an employee <firstName>, <lastName>, <email>, <birthday>, and <department>
    Then system returns invalid birthday
    Examples:
      | firstName | lastName | email            | birthday    | department |
      | nana      | smith    | smith@go.com     | 1990/10/20  | 1          |
      | lana      | dora     | lana.dora@aa.de  | 10-12-1990  | 1          |
      | kamal     | dora     | kamal.dora@aa.de | 2001-11-31  | 3          |
