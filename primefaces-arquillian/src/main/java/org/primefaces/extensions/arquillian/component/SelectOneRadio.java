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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeExpectedConditions;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.primefaces.extensions.arquillian.component.model.SelectItem;

public abstract class SelectOneRadio extends AbstractComponent {

    @FindBy(css = ".ui-radiobutton")
    private List<WebElement> radiobuttons;

    public void select(int index) {
        WebElement radiobutton = radiobuttons.get(index);
        PrimeGraphene.waitGui().until(PrimeExpectedConditions.visibileAndAnimationComplete(radiobutton));

        WebElement input = radiobutton.findElement(By.tagName("input"));
        if (PrimeGraphene.isAjaxScript(input.getAttribute("onchange"))) {
            PrimeGraphene.guardAjaxSilently(radiobutton).click();
        }
        else {
            radiobutton.click();
        }
    }

    public void select(String text) {
        int indexToSelect = getLabels().indexOf(text);
        if (indexToSelect >= 0) {
            select(indexToSelect);
        }
    }

    public int getSelectedIndex() {
        for (SelectItem item : getItems()) {
            if (item.isSelected()) {
                return item.getIndex();
            }
        }

        return -1;
    }

    public List<String> getLabels() {
        return radiobuttons.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getLabel(int index) {
        return getItems().get(index).getLabel();
    }

    public int getItemsSize() {
        return radiobuttons.size();
    }

    public List<SelectItem> getItems() {
        ArrayList<SelectItem> items = new ArrayList<>();

        int idx = 0;
        for (WebElement radiobutton : radiobuttons) {
            idx++;

            WebElement input = radiobutton.findElement(By.tagName("input"));
            WebElement label = root.findElement(By.cssSelector("label[for='" + input.getAttribute("id") + "']"));
            WebElement box = radiobutton.findElement(By.className("ui-radiobutton-box"));

            SelectItem item = new SelectItem();
            item.setIndex(idx);
            item.setLabel(label.getText());
            item.setValue(input.getAttribute("value"));
            item.setSelected(PrimeGraphene.hasCssClass(box, "ui-state-active"));
            items.add(item);
        }

        return items;
    }
}
