package com.exigen.ipb.selenium.tests;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Custom test runner that is supposed to be used to run Product Factory
 * Selenium2 tests that verify behavior of alternate component view.
 * Whether test classes are run it depends if alternate view is enabled.
 *
 * @author gzukas
 * @since 3.9
 */
public class AlternateViewRunner extends BlockJUnit4ClassRunner {

    private static final String CONFIG_FILE =
    		System.getenv("EIS_CONFIG_ROOT") + "/product-ui-config.properties";

    private static final String ALTERNATE_FLAG = "allowedAlternateComponentsViewAccess";

    private static Log log = LogFactory.getLog(AlternateViewRunner.class);
    
    public AlternateViewRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        if (isAlternateViewEnabled() || true) {
            super.run(notifier);
        }
        else {
            notifier.fireTestIgnored(getDescription());
        }
    }

    private boolean isAlternateViewEnabled() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(CONFIG_FILE));
            return Boolean.valueOf(properties.getProperty(ALTERNATE_FLAG));
        }
        catch (Exception e) {
        	log.error("Alternate view must be enabled");
            return false;
        }
    }

}
