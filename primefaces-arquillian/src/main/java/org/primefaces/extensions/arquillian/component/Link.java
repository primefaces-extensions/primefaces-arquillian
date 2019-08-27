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

import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;

public abstract class Link extends AbstractComponent
{
    @Override
    public void click() {
        String href = getHref();
        String target = getTarget();

        if ("_blank".equals(target) || (href != null && href.startsWith("#"))) {
            getRoot().click();
        }
        else {
            PrimeGraphene.guardHttpSilently(getRoot()).click();
        }
    }

    public String getHref() {
        return getRoot().getAttribute("href");
    }

    public String getTarget() {
        return getRoot().getAttribute("target");
    }
}