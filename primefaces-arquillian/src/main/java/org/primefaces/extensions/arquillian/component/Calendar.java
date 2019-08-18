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
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.component.base.Script;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Calendar extends AbstractInputComponent {

    public LocalDateTime getValue() {
        Object date = PrimeGraphene.executeScript("return " + getWidgetByIdScript() + ".getDate()");

        if (date == null) {
            return null;
        }

        long timeZoneOffset = PrimeGraphene.executeScript("return " + getWidgetByIdScript() + ".getDate().getTimezoneOffset()");
        String utcTimeString = PrimeGraphene.executeScript("return " + getWidgetByIdScript() + ".getDate().toUTCString();");

        //Parse time string and subtract the timezone offset
        return LocalDateTime.parse(utcTimeString, DateTimeFormatter.RFC_1123_DATE_TIME).minusMinutes(timeZoneOffset);
    }

    public void setValue(LocalDateTime dateTime) {
        String jsDate = dateTime != null ? "new Date('" + dateTime.toString() + "')" : "null";
        PrimeGraphene.executeScript(getWidgetByIdScript() + ".setDate(" + jsDate + ");");

        Script fireDateSelectEventScript = () -> PrimeGraphene.executeScript(getWidgetByIdScript() + ".fireDateSelectEvent();");

        if (PrimeGraphene.hasAjaxBehavior(root, "dateSelect")) {
            try {
                Graphene.guardAjax(fireDateSelectEventScript).execute();
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            fireDateSelectEventScript.execute();
        }
    }
}
