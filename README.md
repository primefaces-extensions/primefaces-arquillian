[![Maven](https://img.shields.io/maven-central/v/org.primefaces.extensions/primefaces-arquillian.svg)](http://repo1.maven.org/maven2/org/primefaces/extensions/primefaces-arquillian/)
[![Javadocs](http://javadoc.io/badge/org.primefaces.extensions/primefaces-arquillian.svg)](http://javadoc.io/doc/org.primefaces.extensions/primefaces-extensions)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Join the chat at https://gitter.im/primefaces-extensions/primefaces-extensions](https://badges.gitter.im/primefaces-extensions/primefaces-extensions.svg)](https://gitter.im/primefaces-extensions/primefaces-extensions?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/primefaces-extensions/primefaces-arquillian.svg?branch=master)](https://travis-ci.org/primefaces-extensions/primefaces-arquillian)

# primefaces-arquillian
PrimeFaces testing support based on Selenium, Arquillian, Arquillian Graphene and the concept of page ojects / fragements.

### Compatibility
Only tested on PrimeFaces 7.0+.

### Status
Currently only the following components are implemented (partially):

#### HTML
- Link

#### JSF / PrimeFaces
- AccordionPanel
- Calendar
- CommandButton
- CommandLink
- InputNumber
- InputSwitch
- InputText
- InputTextarea
- Messages
- Panel
- SelectOneButton
- SelectBooleanCheckbox
- SelectManyCheckbox
- SelectOneButton
- SelectOneMenu
- SelectOneRadio
- Slider

Contributions are very welcome ;)

### Usage

Example view: 
```java
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.AbstractPrimePage;
import org.primefaces.extensions.arquillian.component.InputText;
import org.primefaces.extensions.arquillian.component.SelectOneMenu;

@Location("index.xhtml")
public class IndexPage extends AbstractPrimePage {
    
    @FindBy(id = "form:manufacturer")
    private SelectOneMenu manufacturer;
    
    @FindBy(id = "form:car")
    private InputText car;

    public SelectOneMenu getManufacturer() {
        return manufacturer;
    }

    public InputText getCar() {
        return car;
    }
}
```

Example test:
```java
import org.primefaces.extensions.arquillian.AbstractPrimePageTest;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.junit.Assert;
import org.junit.Test;

public abstract class IndexPageTest extends AbstractPrimePageTest {

    // force new session after each test
    @After
    public void after() {
        webDriver.manage().deleteAllCookies();
    }

    @Test
    public void myFirstTest(@InitialPage IndexPage index) throws InterruptedException {
        assertDisplayed(index.getManufacturer());
        assertEnabled(index.getManufacturer());

        index.getManufacturer().select("BMW");
        Assert.assertTrue(index.getManufacturer().isSelected("BMW"));
    }
}
```

### Build & Run
- Build by source (mvn clean install)
- Run "primefaces-arquillian-example" project (mvn clean install)
