Feature: ErrorValidation Test

    @ErrorValidation
    Scenario Outline:
      Given Land to login page through URL
      When  login through <username> and <password>
      Then  "Incorrect email or password." is displayed

      Examples:
        | username                 | password   |  |  |
        | gouri.pusapati@gmail.net | Gouri@3174 |  |  |


