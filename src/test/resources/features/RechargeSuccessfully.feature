@Regression
Feature: User should be able to recharge successfully

  Scenario: User do recharge through CC
    Given I navigate to the "HOME" page
    When I select operator as "Telcel" from "Operador" field
    And I provide number 8465433546 under Numero de celular field
    And I select 10$ recharge under Recarga for Monto de Recarga field
    Then I click on siguiente button
    Then I should be on Payment Screen where url containing /payment.php
    And I should see on Payment Page text Recarga Saldo (8465433546)
    Then I select payment option as Tarjeta
    And I select radio button "Usar nueva tarjeta"
    And I provide Card number 4111111111111111
    And I provide card expiry month as 11
    And I provide card expiry year as 25
    And I provide card cvv as 111
    And I provide email id "test@test.com" under correo electronico field
    When I click on Pagar con Tarjeta button
    Then I should see a pop up having text "Para pagar por favor Regístrate o Ingresa a tu cuenta"
    When I provide email id as "automationexcersise@test.com" and password as 123456 in pop up
    And I select check box "I'm not a robot" and click on "Acceso" button
    Then I should be able to see "¡Recarga Exitosa!"

