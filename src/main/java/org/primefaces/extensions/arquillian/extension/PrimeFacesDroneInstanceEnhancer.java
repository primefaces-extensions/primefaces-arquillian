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
package org.primefaces.extensions.arquillian.extension;

import org.jboss.arquillian.drone.spi.DroneInstanceEnhancer;
import org.jboss.arquillian.drone.spi.InstanceOrCallableInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.lang.annotation.Annotation;

public class PrimeFacesDroneInstanceEnhancer implements DroneInstanceEnhancer<WebDriver> {
    
    protected WebDriverEventListener listener;

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public boolean canEnhance(InstanceOrCallableInstance instance, Class<?> droneType, Class<? extends Annotation> qualifier) {
        return droneType.isAssignableFrom(EventFiringWebDriver.class);
    }

    @Override
    public WebDriver enhance(WebDriver instance, Class<? extends Annotation> qualifier) {
        EventFiringWebDriver driver = new EventFiringWebDriver(instance);

        listener = new PrimeFacesWebDriverEventListener();
        driver.register(listener);

        return driver;
    }

    @Override
    public WebDriver deenhance(WebDriver enhancedInstance, Class<? extends Annotation> qualifier) {
        if (EventFiringWebDriver.class.isInstance(enhancedInstance)) {
            EventFiringWebDriver driver = (EventFiringWebDriver) enhancedInstance;

            driver.unregister(listener);
            listener = null;

            return driver.getWrappedDriver();
        }

        return enhancedInstance;
    }

}
