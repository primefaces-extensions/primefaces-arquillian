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

import java.io.Serializable;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.WebElement;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.Script;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class InputNumber extends InputText {

    @FindByParentPartialId(value = "_input")
    private WebElement input;

    @FindByParentPartialId(value = "_hinput")
    private WebElement hiddenInput;

    @Override
    protected WebElement getInput() {
        return input;
    }

    protected WebElement getHiddenInput() {
        return hiddenInput;
    }

    @Override
    public void setValue(Serializable value) {
        String script = getWidgetByIdScript() + ".setValue(" + value.toString() + ");";

        if (isOnchangeAjaxified()) {
            try {
                Graphene.guardAjax((Script) () -> {
                    PrimeGraphene.executeScript(script);
                }).execute();
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            PrimeGraphene.executeScript(script);
        }
    }
}
