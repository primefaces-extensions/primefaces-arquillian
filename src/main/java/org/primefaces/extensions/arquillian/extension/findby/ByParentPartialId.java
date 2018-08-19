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
package org.primefaces.extensions.arquillian.extension.findby;

import org.jboss.arquillian.core.spi.Validate;
import org.jboss.arquillian.drone.api.annotation.Default;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.proxy.GrapheneProxyInstance;
import org.jboss.arquillian.graphene.spi.findby.LocationStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.lang.annotation.Annotation;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ByParentPartialId extends By {
    
    private final String partialId;
    private final boolean searchFromRoot;

    public ByParentPartialId(String partialId, boolean searchFromRoot) {
        super();
        Validate.notNull(partialId, "Cannot find elements when partialId is null!");
        this.partialId = partialId;
        this.searchFromRoot = searchFromRoot;
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        GrapheneContext grapheneContext = getGrapheneContext(searchContext);

        List<WebElement> elements;
        try {
            if (searchContext instanceof WebElement) {
                WebElement parent = (WebElement) searchContext;

                String parentId = parent.getAttribute("id");
                if (parentId == null || StringUtils.isBlank(parentId)) {
                    throw new WebDriverException("Id of parent element is null or empty!");
                }

                By by = By.id(parentId + partialId);
                if (searchFromRoot) {
                    elements = grapheneContext.getWebDriver(Default.class).findElements(by);
                }
                else {
                    elements = parent.findElements(by);
                }
            }
            else {
                throw new WebDriverException(
                        "Cannot determine the SearchContext you are passing to the findBy/s method! It is not instance of WebDriver nor WebElement! It is: "
                        + searchContext);
            }
        }
        catch (Exception ex) {
            throw new WebDriverException("Can not locate element using partialId " + partialId
                    + " Check out whether it is correct!", ex);
        }
        return elements;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        List<WebElement> elements = findElements(context);
        if (elements == null || elements.isEmpty()) {
            throw new NoSuchElementException("Cannot locate element using: " + partialId);
        }
        return elements.get(0);
    }

    @Override
    public String toString() {
        return "ByParentPartialId(value = \"" + partialId + "\", searchFromRoot = \"" + searchFromRoot + "\")";
    }

    private GrapheneContext getGrapheneContext(SearchContext searchContext) {
        if (searchContext instanceof GrapheneProxyInstance) {
            return ((GrapheneProxyInstance) searchContext).getGrapheneContext();
        }
        else {
            return GrapheneContext.lastContext();
        }
    }

    public static class ParentPartialIdLocationStrategy implements LocationStrategy {
        
        @Override
        public ByParentPartialId fromAnnotation(Annotation annotation) {
            FindByParentPartialId findBy = (FindByParentPartialId) annotation;
            return new ByParentPartialId(findBy.value(), findBy.searchFromRoot());
        }
    }
}
