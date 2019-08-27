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
package org.primefaces.extensions.arquillian.extension.findby;

import org.jboss.arquillian.graphene.spi.findby.ImplementsLocationStrategy;
import org.openqa.selenium.support.FindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Alternative {@link FindBy} annotation, which should only be used in page fragments.
 * It searches by id, starting from the parent element, based on the id of the parent concatenated with {@link #value()}.
 *
 * Sometimes, for example with <code>appendTo="..."</code>, the child element is moved to somewhere else in the DOM.
 * In this case you have to set {@link #searchFromRoot()} to <code>true</code>.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ImplementsLocationStrategy(ByParentPartialId.ParentPartialIdLocationStrategy.class)
public @interface FindByParentPartialId {

    String value();

    boolean searchFromRoot() default false;
}
