package com.UnDosTres.test.stepDefinitions;

import com.UnDosTres.test.pageObjects.HomePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

public class HomePageSteps {

    private HomePage homePage;

    public HomePageSteps(HomePage homePage){
        this.homePage = homePage;
    }

    @When("^I select operator as \"(.*?)\" from \"Operador\" field$")
    public void select_Operator_Field(String operator){
        Assert.assertTrue(homePage.setOperator(operator));
    }

    @And("^I provide number (.*?) under Numero de celular field$")
    public void enter_Mobile_Number_For_Recharge(String number){
        Assert.assertTrue(homePage.setMobileNumber(number));
    }

    @And("^I select (.*?) recharge under (.*?) for Monto de Recarga field$")
    public void select_Recharge_Type_And_Amount(String rechAmount, String rechargeType){
        Assert.assertTrue(homePage.setRechargeTypeAndAmount(rechargeType,rechAmount.replace("$","")));
    }

    @Then("^I click on siguiente button$")
    public void click_On_Siguiente_Button(){
        Assert.assertTrue(homePage.clickProceedToPayment()!=null);
    }
}
