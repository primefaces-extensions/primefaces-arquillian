/**
 * Copyright 2011-2018 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.extensions.arquillian;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * A collection of expected conditions for Selenium which are specific for
 * PrimeFaces.
 */
@SuppressWarnings("PMD.ClassNamingConventions")
    //PMD want to enforce the utility class to be name ...Utils/Helper, but
    //this name is better
public final class PrimeExpectedConditions {

    private PrimeExpectedConditions() {
    }

    public static ExpectedCondition<Boolean> jQueryNotActive() {
        return driver -> (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0;");
    }

    public static ExpectedCondition<Boolean> visibileAndAnimationComplete(WebElement element) {
        return ExpectedConditions.and(
                jQueryNotActive(),
                ExpectedConditions.visibilityOf(element));
    }

    public static ExpectedCondition<Boolean> invisibleAndAnimationComplete(WebElement element) {
        return ExpectedConditions.and(
                jQueryNotActive(),
                ExpectedConditions.invisibilityOf(element));
    }
}
