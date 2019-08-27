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

import org.jboss.arquillian.graphene.GrapheneElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.primefaces.extensions.arquillian.component.model.Msg;
import org.primefaces.extensions.arquillian.component.model.Severity;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;

public abstract class Messages extends AbstractComponent {

    @FindByJQuery("> div")
    private List<GrapheneElement> messagesSeverties;

    public List<Msg> getAllMessages() {
        List<Msg> result = new ArrayList<>();

        for (GrapheneElement messageSeverity : messagesSeverties) {

            Severity severity = Severity.toSeverity(messageSeverity.getAttribute("class"));

            for (GrapheneElement message : messageSeverity.findGrapheneElements(By.cssSelector("li"))) {
                Msg msg = new Msg();
                msg.setSeverity(severity);
                msg.setSummary(message.findElement(By.className("ui-messages-" + Severity.toName(severity) + "-summary")).getText());
                try {
                    msg.setDetail(message.findElement(By.className("ui-messages-" + Severity.toName(severity) + "-detail")).getText());
                }
                catch (NoSuchElementException e) {
                    // ignore, it's optional
                }

                result.add(msg);
            }
        }

        return result;
    }

    public Msg getMessage(int index) {
        List<Msg> allMessages = getAllMessages();

        if (allMessages.size() > index) {
            return allMessages.get(index);
        }

        return null;
    }

    public List<Msg> getMessagesBySeverity(Severity severity) {
        return getAllMessages().stream()
                .filter(message -> severity.equals(message.getSeverity()))
                .collect(Collectors.toList());
    }

    public List<String> getAllSummaries() {
        return getAllMessages().stream()
            .map(Msg::getSummary)
            .collect(Collectors.toList());
    }
}
