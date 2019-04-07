@events @employee @createEmployee
Feature: Get Employee Event
  Get all recorded events for an employee sorted by date.

  Background:
    Given Events system is started

  # notice at this stage no paging is supported
  @main
  Scenario Outline: Get Employee Events
    When user provided an employee id <employeeId>
    Then system returns all eventes realted to that employee sorted by event creation date.
    Examples:
      | employeeId |
      | 1          |
      | 2          |
      | 3          |


  @alternative
  Scenario Outline: Get Employee Events for non exiting employee
    When user requested to get events for a non exiting employee <employeeId>
    Then system returns an error employee not found.
    Examples:
      | employeeId |
      | -1         |
      | 0          |
      | 200000     |
