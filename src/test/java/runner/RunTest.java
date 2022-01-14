package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features/UIBank.feature",
				 glue = {"steps","hooks"},
				 monochrome = true,publish=true)

public class RunTest extends AbstractTestNGCucumberTests{

}

