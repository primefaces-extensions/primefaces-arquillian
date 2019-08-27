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
package org.primefaces.extensions.arquillian.component;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class InputSwitch extends AbstractInputComponent {

    @FindByParentPartialId("_input")
    private WebElement input;

    @Override
    public void click() {
        Graphene.waitGui().until(ExpectedConditions.elementToBeClickable(root));

        if (PrimeGraphene.isAjaxScript(root.getAttribute("onchange"))) {
            PrimeGraphene.guardAjaxSilently(root).click();
        }
        else {
            root.click();
        }
    }

    @Override
    protected WebElement getInput() {
        return input;
    }
}
