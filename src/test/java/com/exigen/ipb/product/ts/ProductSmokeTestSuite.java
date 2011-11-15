package com.exigen.ipb.product.ts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.exigen.ipb.product.ProductConfigurationFitnesseSmokeWebTests;
import com.exigen.ipb.product.RulesOverrideFitnesseSmokeWebTests;
import com.exigen.ipb.product.runtime.RulesOverrideRuntimeFitnesseSmokeWebTests;


@Suite.SuiteClasses({ 
        ProductConfigurationFitnesseSmokeWebTests.class,
		RulesOverrideFitnesseSmokeWebTests.class,
		RulesOverrideRuntimeFitnesseSmokeWebTests.class
})

@RunWith(Suite.class)
public class ProductSmokeTestSuite {
}