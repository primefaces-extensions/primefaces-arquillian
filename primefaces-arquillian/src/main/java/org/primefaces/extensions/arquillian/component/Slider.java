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
package org.primefaces.extensions.arquillian.component;

import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.component.base.Script;

public abstract class Slider extends AbstractInputComponent {

    public Number getValue() {
        return PrimeGraphene.executeScript("return " + getWidgetByIdScript() + ".getValue();");
    }

    public void setValue(Number value) {
        PrimeGraphene.executeScript(getWidgetByIdScript() + ".setValue(" + value + ");");
        PrimeGraphene.executeScript(getWidgetByIdScript() + ".onSlide(null, { value: " + value + " });");

        if (PrimeGraphene.hasAjaxBehavior(root, "slideEnd")) {
            PrimeGraphene.guardAjaxSilently((Script) () -> {
                PrimeGraphene.executeScript(getWidgetByIdScript() + ".onSlideEnd(null, { value: " + value + " });");
            }).execute();
        }
        else {
            PrimeGraphene.executeScript(getWidgetByIdScript() + ".onSlideEnd(null, { value: " + value + " });");
        }
    }


}
