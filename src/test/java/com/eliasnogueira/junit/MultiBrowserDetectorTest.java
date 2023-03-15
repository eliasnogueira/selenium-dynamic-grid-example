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

import com.eliasnogueira.driver.BrowserFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class MultiBrowserDetectorTest extends BaseWeb {

    @ParameterizedTest
    @EnumSource(BrowserFactory.class)
    void detectBrowserTest(BrowserFactory browser) {
        beforeStartEachTest(browser.name().toLowerCase());

        var browserName = capitalize(driver.getCapabilities().getBrowserName());
        var browserVersion = driver.getCapabilities().getBrowserVersion().split("\\.");
        var platformNames = driver.getCapabilities().getPlatformName().getPartOfOsName();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("container")));

        assertSoftly(soft -> {
            soft.assertThat(driver.findElement(By.cssSelector("[data-browser-detect='browser']")).getText())
                    .containsAnyOf(normalizeBrowserName(browserName));

            soft.assertThat(driver.findElement(By.cssSelector("[data-browser-detect='version']")).getText())
                    .isEqualToIgnoringCase(browserVersion[0]);

            soft.assertThat(driver.findElement(By.cssSelector("[data-browser-detect='OS']")).getText().toLowerCase())
                    .containsAnyOf(platformNames);
        });
    }
}
