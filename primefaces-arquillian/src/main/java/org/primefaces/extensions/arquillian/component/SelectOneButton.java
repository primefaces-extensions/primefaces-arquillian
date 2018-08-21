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

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectOneButton extends AbstractInputComponent {

    @FindBy(css = ".ui-button")
    private List<WebElement> options;

    @FindBy(css = ".ui-button.ui-state-active")
    private WebElement activeOption;

    public List<String> getOptionLabels() {
        List<String> result = new ArrayList<>();
        options.forEach((element) -> result.add(element.getText()));

        return result;
    }

    public String getSelectedLabel() {
        return activeOption.getText();
    }

    public boolean isSelected(String label) {
        return getSelectedLabel().equalsIgnoreCase(label);
    }

    public void selectNext() {
        int activeIndex = options.indexOf(activeOption);
        int nextIndex = activeIndex + 1;

        if (nextIndex >= options.size()) {
            nextIndex = 0;
        }

        click(options.get(nextIndex));
    }

    public void select(String label) {
        if (!isSelected(label)) {
            for (WebElement element : options) {
                if (element.getText().equalsIgnoreCase(label)) {
                    click(element);
                }
            }
        }
    }

    public void deselect(String label) {
        if (isSelected(label)) {
            for (WebElement element : options) {
                if (element.getText().equalsIgnoreCase(label)) {
                    click(element);
                }
            }
        }
    }

    protected void click(WebElement element) {
        if (PrimeGraphene.hasAjaxBehavior(root, "change") || PrimeGraphene.hasAjaxBehavior(root, "onchange")) {
            try {
                Graphene.guardAjax(element).click();
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            element.click();
        }
    }
}
