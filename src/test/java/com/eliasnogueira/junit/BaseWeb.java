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

package com.eliasnogueira.junit;

import com.eliasnogueira.driver.DriverManager;
import com.eliasnogueira.driver.TargetFactory;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.eliasnogueira.config.ConfigurationManager.getInstance;

public abstract class BaseWeb {

    protected RemoteWebDriver driver;
    protected WebDriverWait wait;

    @AfterEach
    void quitBrowser() {
        driver.quit();
    }

    protected void beforeStartEachTest(String browser) {
        driver = new TargetFactory().createInstance(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(getInstance().timeout()));
        DriverManager.setDriver(driver);

        DriverManager.getDriver().get(getInstance().url());
    }

    protected String[] normalizeBrowserName(String browserName) {
        return browserName.split("(?<=[a-z])(?=[A-Z])");
    }
}
