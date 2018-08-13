package org.primefaces.extensions.arquillian.component;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.AbstractPrimePageFragment;
import org.primefaces.extensions.arquillian.PrimeGraphene;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AccordionPanel extends AbstractPrimePageFragment
{
    @FindBy(css = ".ui-accordion-header")
    private List<GrapheneElement> accordionHeaders;

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

    public String getTabHeader(int index)
    {
        return accordionHeaders.get(index).getText();
    }

    public List<String> getTabHeaders()
    {
        return accordionHeaders.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }
}
