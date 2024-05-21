package com.furansujin.demosprinhexagonalcucumber;

import com.furansujin.demosprinhexagonalcucumber.acceptance.configuration.ContextConfigurationTesting;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {"pretty","summary",
                "html:target/cucumber-reports.html",
                "json:target/json_result.json"
        }
)
@RunWith(Cucumber.class)
public class AllAcceptanceTest {
}

