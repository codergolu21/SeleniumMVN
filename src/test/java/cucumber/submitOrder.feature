Feature: Purchasing a product from E-commerce website

  Background:
    Given Land to login page through URL

    @Regression
  Scenario Outline:

    Given login through <email> and <password>
    When click on <productName> and add to cart
    And Checkout <productName> and submit order
    Then "THANKYOU FOR THE ORDER." is displayed on Confirmation page

    Examples:
      | email                    | password    | productName |
      | gouri.pusapati@gmail.com | Gouri@31748 | ADIDAS ORIGINAL |

