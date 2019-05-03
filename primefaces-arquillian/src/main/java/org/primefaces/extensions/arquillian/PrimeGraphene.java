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
package org.primefaces.extensions.arquillian;

import org.jboss.arquillian.drone.api.annotation.Default;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.proxy.GrapheneProxyInstance;
import org.jboss.arquillian.graphene.proxy.Interceptor;
import org.jboss.arquillian.graphene.proxy.InvocationContext;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PrimeGraphene extends Graphene {

    private PrimeGraphene() {

    }

    public static void disableAnimations() {
        executeScript("if (window.$) { $(function() { $.fx.off = true; }); }");
    }

    public static void enableAnimations() {
        executeScript("if (window.$) { $(function() { $.fx.off = false; }); }");
    }

    public static void handleRequestGuardException(RequestGuardException e) {
        System.err.println("Expected " + e.getRequestExpected() + " request, " + e.getRequestDone() + " was done instead. Ignoring.");
    }

    public static WebDriver getWebDriver() {
        return GrapheneContext.getContextFor(Default.class).getWebDriver();
    }

    public static <T> T executeScript(String script) {
        JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
        return (T) executor.executeScript(script);
    }

    public static boolean hasCssClass(WebElement element, String cssClass) {
        String classes = element.getAttribute("class");
        if (classes == null || classes.trim().isEmpty()) {
            return false;
        }

        for (String currentClass : classes.split(" ")) {
            if (currentClass.equals(cssClass)) {
                return true;
            }
        }

        return false;
    }





    public static boolean isAjaxScript(String script) {
        if (script == null || script.trim().isEmpty()) {
            return false;
        }

        return script.contains("PrimeFaces.ab(") || script.contains("pf.ab(") || script.contains("mojarra.ab(") || script.contains("jsf.ajax.request");
    }

    public static boolean hasAjaxBehavior(WebElement element, String behavior) {
        if (!hasBehavior(element, behavior)) {
            return false;
        }

        String id = element.getAttribute("id");

        // TODO 6.3
        // Object result = executeScript("return " + getWidgetByIdScript(id) + ".getBehavior('" + behavior + "');");
        String result = executeScript("return " + getWidgetByIdScript(id) + ".cfg.behaviors['" + behavior + "'].toString();");
        return isAjaxScript(result);
    }

    public static boolean hasBehavior(WebElement element, String behavior) {
        if (!isWidget(element)) {
            return false;
        }

        String id = element.getAttribute("id");

        return executeScript("return " + getWidgetByIdScript(id) + ".hasBehavior('" + behavior + "');");
    }

    public static boolean isWidget(WebElement element) {
        String id = element.getAttribute("id");
        if (id == null || id.isEmpty()) {
            return false;
        }

        Object result = executeScript("return " + getWidgetByIdScript(id) + ";");
        return result != null;
    }

    public static String getWidgetByIdScript(String id) {
        return "PrimeFaces.getWidgetById('" + id + "')";
    }





    public static boolean isElementPresent(By by) {
        try {
            getWebDriver().findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isElementPresent(WebElement element) {
        try {
            element.isDisplayed(); // just any method to check if NoSuchElementException will be thrown
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isElementDisplayed(By by) {
        try {
            return getWebDriver().findElement(by).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isElementEnabled(By by) {
        try {
            return getWebDriver().findElement(by).isEnabled();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public static <T> T guardHttpSilently(T target) {
        GrapheneProxyInstance proxy = (GrapheneProxyInstance) Graphene.guardHttp(target);
        registerSilentInterceptor(proxy);
        return (T) proxy;
    }

    public static <T> T guardAjaxSilently(T target) {
        GrapheneProxyInstance proxy = (GrapheneProxyInstance) Graphene.guardAjax(target);
        registerSilentInterceptor(proxy);
        return (T) proxy;
    }

    private static void registerSilentInterceptor(GrapheneProxyInstance proxy) {
        proxy.registerInterceptor(new Interceptor() {
            @Override
            public Object intercept(InvocationContext context) throws Throwable {
                try {
                    return context.invoke();
                }
                catch (Throwable e) {
                    if (e instanceof RequestGuardException) {
                        PrimeGraphene.handleRequestGuardException((RequestGuardException) e);
                        return null;
                    }
                    throw e;
                }
            }

            @Override
            public int getPrecedence() {
                return 1;
            }
        });
    }
}
