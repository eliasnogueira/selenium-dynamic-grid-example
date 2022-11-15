/*
 * MIT License
 *
 * Copyright (c) 2022 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.eliasnogueira.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.logging.Logger;

import static com.eliasnogueira.config.ConfigurationManager.configuration;
import static java.lang.String.*;

public class TargetFactory {

    private static final Logger logger = Logger.getLogger(TargetFactory.class.getName());

    public RemoteWebDriver createInstance(String browser) {
        logger.info(format("Creating a [%s] browser instance", browser));
        return createRemoteInstance(BrowserFactory.valueOf(browser.toUpperCase()).getOptions());
    }

    private RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            String gridURL = format("http://%s:%s", configuration().gridUrl(), configuration().gridPort());
            logger.info(format("Tests will run at: %s for %s", gridURL, capability));

            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), capability);
        } catch (java.net.MalformedURLException e) {
            logger.severe("Grid URL is invalid or Grid is not available");
            logger.severe(format("Browser: %s", capability.getBrowserName()));
            logger.severe(e.toString());
        } catch (IllegalArgumentException e) {
            logger.severe(format("Browser %s is not valid or recognized", capability.getBrowserName()));
            logger.severe(e.toString());
        }

        return remoteWebDriver;
    }
}
