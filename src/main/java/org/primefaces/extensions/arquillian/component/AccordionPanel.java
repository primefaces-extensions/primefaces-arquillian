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
 * Component wrapper for the PrimeFaces {@code p:accordionPanel}.
 */
public abstract class AccordionPanel extends AbstractComponent
{
    @FindBy(css = ".ui-accordion-header")
    private List<GrapheneElement> headers;

    /**
     * Toggle the tab denoted by the specified index.
     * 
     * @param index the index of the tab to expand
     */
    public void toggleTab(int index)
    {
        if (PrimeGraphene.hasAjaxBehavior(root, "tabChange"))
        {
            try
            {
                Graphene.guardAjax(headers.get(index)).click();
            }
            catch (RequestGuardException e)
            {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else
        {
            headers.get(index).click();
        }
    }

    /**
     * Provides the header of an {@link AccordionPanel} tab at the specified index.
     * 
     * @param index the index
     * @return the header of the {@link AccordionPanel} tab
     */
    public String getTabHeader(int index)
    {
        return headers.get(index).getText();
    }

    /**
     * Provides the headers of the {@link AccordionPanel} tabs in their order.
     * 
     * @return a copy of the headers in order
     */
    public List<String> getTabHeaders()
    {
        return headers.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }
}
