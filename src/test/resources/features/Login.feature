@login
Feature: Stack overflow - Login

  Background:
    Given I open the stack overflow log in page

  Scenario: Validate successful login
    When I enter a unique email eugenee.rabota@gmail.com
    And I enter a unique password Qwerty1234$
    And I click on log in button
    Then It should be presented the icon home

  Scenario Outline: Validate Unsuccessful login
    When I enter a email <email>
    And I enter a password <password>
    And I click on log in button
    Then It should be presented with the unsuccessful login message <logInvalidationMassage>

    Examples:
      | email                     | password    | logInvalidationMassage            |
      | dsadsad@gmail.com         | Qwerty1234$ | No user found with matching email |
      | eugenee.rabota1@gmail.com | 1234        | No user found with matching email |
