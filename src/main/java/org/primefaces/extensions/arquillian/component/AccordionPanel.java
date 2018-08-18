package org.primefaces.extensions.arquillian.component;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeGraphene;

import java.util.List;
import java.util.stream.Collectors;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;

/**
 * Graphene extensions for a PrimeFaces {@code p:accordionPanel}.
 */
public abstract class AccordionPanel extends AbstractComponent
{
    @FindBy(css = ".ui-accordion-header")
    private List<GrapheneElement> accordionHeaders;

    /**
     * Changes the currently expanded tab to the tab denoted by the specified
     * index.
     * @param index the index of the tab to change to
     */
    public void changeTab(int index)
    {
        if (PrimeGraphene.hasAjaxBehavior(root, "tabChange"))
        {
            try
            {
                Graphene.guardAjax(accordionHeaders.get(index)).click();
            }
            catch (RequestGuardException e)
            {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else
        {
            accordionHeaders.get(index).click();
        }
    }

    /**
     * Provides the header of an accordion panel tab at the specified index.
     * @param index the index
     * @return the header of the accordion panel tab
     */
    public String getTabHeader(int index)
    {
        return accordionHeaders.get(index).getText();
    }

    /**
     * Provides the headers of the accordion panel tabs in their order.
     * @return a copy of the headers in order
     */
    public List<String> getTabHeaders()
    {
        return accordionHeaders.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }
}
