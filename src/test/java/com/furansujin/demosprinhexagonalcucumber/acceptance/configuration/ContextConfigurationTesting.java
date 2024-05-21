package com.furansujin.demosprinhexagonalcucumber.acceptance.configuration;


import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = {
        RepositoriesConfiguration.class,
        GatewaysConfiguration.class
})
public class ContextConfigurationTesting {

}