@employee @deleteEmployee
Feature: Delete Employee
  Adding employee

  Background:
    Given Delete Employee system is started and it has three employees

  @main @events @deleteEvent
  Scenario Outline: Delete Employee
    When user provides valid employee information to delete a valid employee <employeeId>
    Then system deletes employee and provide an auto increment employee uuid
    And delete employee event will be recorded
    Examples:
      | employeeId |
      | 1          |
      | 2          |

  Scenario Outline: Delete non exiting Employee
    When user provides valid employee information to delete a non exiting employee <employeeId>
    Then delete system returns non exiting employee
    Examples:
      | employeeId |
      | -1         |
      | 0          |
