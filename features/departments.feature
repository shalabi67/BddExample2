@departments
Feature: Add Department
  Adding members

  Background:
    Given departments system is started

  @createDepartment @main
  Scenario Outline: Create Department
    When user provides valid department name <departmentName>
    And system creates department and provide an auto increment department id
    #notice that system is not doing any validation on department name since it is not specified on requirements
    Examples:
      | departmentName |
      | hr             |
      | finance        |
      | it             |

  # this will not be implemented since it is not specified in the requirements.
  @createDepartment @alternative
  Scenario Outline: Create Department with an exiting department name
    When user provides department name which exists in the system <departmentName>
    And system returns an error department exists
    Examples:
      | departmentName |
      | hr             |
      | finance        |
      | it             |

# this will not be implemented since it is not specified in the requirements.
  @createDepartment @alternative
  Scenario Outline: Create Department with an invalid department name
    When user provides department name which is invalid <departmentName>
    And system returns an error invalid department name
    Examples:
      | departmentName |
      | 123            |
      | hr123          |
      | 123hr          |