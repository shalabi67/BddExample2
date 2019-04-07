@employee @UpdateEmployee
Feature: Update Employee
  Adding employee

  Background:
    Given Employee system is started and it has three employees 1, 2, and 3

  @main @events @updateEvent
  Scenario Outline: Update Employee
    When user provides valid employee information to update a valid employee <employeeId> <firstName>, <lastName>, <email>, <birthday>, and <department>
    Then system updates employee
    And update employee event will be recorded
    #old data came from employeeAdd feature
    Examples:
      | employeeId | firstName      | lastName    | email              | birthday    | department |
      | 1          | nana-update    | smith       | smith@go.com       | 1990-10-20  | 1          |
      | 2          | lana           | dora-update | lana.dora@aa.de    | 2001-10-30  | 1          |
      | 2          | lana           | dora        | lana.update@aa.de  | 2001-10-30  | 1          |
      | 3          | kamal          | dora        | kamal.dora@aa.de   | 2019-11-30  | 3          |
      | 3          | kamal          | dora        | kamal.dora@aa.de   | 2001-11-30  | 1          |
      | 3          | update-first   | update-last | update@aa.de       | 2018-11-30  | 2          |


  @alternative
  Scenario Outline: Update Employee with an invalid email
    When user provides invalid email with employee information to update an employee <employeeId> <firstName>, <lastName>, <email>, <birthday>, and <department>
    Then employee update system returns invalid email information
    Examples:
      | employeeId | firstName | lastName | email            | birthday    | department |
      | 1          | hana      | smith    | hana.go.com      | 1990-10-20  | 1          |
      | 2          | lana      | dora     | lana             | 2001-10-30  | 1          |
      | 3          | kamal     | dora     | kamal.dora@aa    | 2001-11-30  | 3          |
      | 3          | kamal     | dora     | 123              | 2001-11-30  | 2          |
      | 3          | kamal     | smith    | smith@foo_com    | 2001-11-30  | 2          |
      | 3          | kamal     | smith    | smith@foo@com.de | 2001-11-30  | 3          |
      | 3          | kamal     | smith    | smith@.com       | 2001-11-30  | 2          |



  @alternative
  Scenario Outline: Create Employee with an exiting email
    When user provides exiting email with employee information to update an employee <employeeId> <firstName>, <lastName>, <email>, <birthday>, and <department>
    Then employee update system returns exiting email information
    Examples:
      | employeeId | firstName | lastName | email                  | birthday    | department |
      | 1          | nana      | smith    | smithExisit@go.com     | 1990-10-20  | 1          |
      | 2          | lana      | dora     | lana.Exist@aa.de       | 2001-10-30  | 2          |
      | 3          | kamal     | dora     | kamal.Exist@aa.de      | 2001-11-30  | 3          |

  #TODO: add more test cases
  @alternative
  Scenario Outline: update Employee with invalid birthday
    When user provides invalid birthday with employee information to update an employee <employeeId> <firstName>, <lastName>, <email>, <birthday>, and <department>
    Then employee update system returns invalid birthday
    Examples:
      | employeeId | firstName | lastName | email            | birthday    | department |
      | 1          | nana      | smith    | smith@go.com     | 1990/10/20  | 1          |
      | 2          | lana      | dora     | lana.dora@aa.de  | 10-12-1990  | 1          |
      | 3          | kamal     | dora     | kamal.dora@aa.de | 2001-11-31  | 3          |
