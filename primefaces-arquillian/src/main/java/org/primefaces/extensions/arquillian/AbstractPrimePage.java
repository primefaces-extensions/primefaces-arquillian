/**
 * Copyright 2011-2019 PrimeFaces Extensions
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

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.openqa.selenium.WebDriver;

import java.net.URL;

public abstract class AbstractPrimePage {

    @Drone
    protected WebDriver webDriver;

    @ArquillianResource
    protected URL contextPath;

    public void goTo() {
        Graphene.goTo(this.getClass());
    }

    public boolean isAt() {
        return webDriver.getCurrentUrl().contains(getLocation());
    }

    public String getLocation() {
        return this.getClass().getAnnotation(Location.class).value();
    }
}
