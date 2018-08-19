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

import java.net.URL;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The base of a Graphene workflow.
 */
@RunWith(Arquillian.class)
@RunAsClient
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
    //abstract avoids initialization here only
public abstract class AbstractPrimePageTest {

    @Drone
    protected WebDriver webDriver;

    @ArquillianResource
    protected URL contextPath;

    protected void assertPresent(WebElement element) {
        if (!PrimeGraphene.isElementPresent(element)) {
            Assert.fail("Element should be present!");
        }
    }

    protected void assertNotPresent(WebElement element) {
        if (PrimeGraphene.isElementPresent(element)) {
            Assert.fail("Element should not be present!");
        }
    }

    protected void assertDisplayed(WebElement element) {
        if (!PrimeGraphene.isElementDisplayed(element)) {
            Assert.fail("Element should be displayed!");
        }
    }

    protected void assertNotDisplayed(WebElement element) {
        if (PrimeGraphene.isElementDisplayed(element)) {
            Assert.fail("Element should not be displayed!");
        }
    }

    protected void assertEnabled(WebElement element) {
        if (!PrimeGraphene.isElementEnabled(element)) {
            Assert.fail("Element should be enabled!");
        }
    }

    protected void assertNotEnabled(WebElement element) {
        if (PrimeGraphene.isElementEnabled(element)) {
            Assert.fail("Element should not be enabled!");
        }
    }
}
