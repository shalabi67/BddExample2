@employee @getEmployee
Feature: Get Employee
  Get employee

  Background:
    Given Get Employee system is started and it has three employees 1, 2, 3

  @main
  Scenario Outline: Get Employee
    When user provides valid employee id to get a valid employee <employeeId>
    Then system gets employee
    Examples:
      | employeeId |
      | 1          |
      | 2          |

  @alternative
  Scenario Outline: Get non exiting Employee
    When user provides an employee id to get a non exiting employee <employeeId>
    Then get employee system returns non exiting employee
    Examples:
      | employeeId |
      | -1         |
      | 0          |
