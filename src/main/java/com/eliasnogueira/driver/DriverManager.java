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

import org.openqa.selenium.WebDriver;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final Logger LOGGER = Logger.getLogger(DriverManager.class.getName());

    private DriverManager() {}

    public static WebDriver getDriver() {
        return Optional.ofNullable(DRIVER.get())
                .orElseThrow(() -> new IllegalStateException("WebDriver has not been set."));
    }

    public static void setDriver(WebDriver driver) {
        if (driver == null) {
            LOGGER.log(Level.SEVERE, "WebDriver is null while trying to set the driver in DriverManager");
            throw new IllegalArgumentException("WebDriver cannot be null.");
        } else {
            DRIVER.set(driver);
        }
    }

    public static void quit() {
        Optional.ofNullable(DRIVER.get()).ifPresent(webDriver -> {
            webDriver.quit();
            DRIVER.remove();
        });
    }
}
