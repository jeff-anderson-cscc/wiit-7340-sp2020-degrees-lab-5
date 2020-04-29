# WIIT-7340 Lab Five
## Server-side Degrees Restaurant at Columbus State website  

## Getting started:

Using the [create from template](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/creating-a-repository-from-a-template) option, create a repository from this template.

## Tasks

__TIP:__ When getting unit tests working, fix them one by one in order. Avoid moving on to the next test until all the ones above are working.

- [ ] Get all test in `S01_WebContentControllerTests` passing by correctly implementing methods in `WebContentController`.  Completing this should get the [menu page](http://localhost:3000/menu) working correctly.
- [ ] Get all test in `S02_MessageSourcePropertyTests` passing by adding internationalization support using the instruction below. Once this is done, the [about page](http://localhost:3000/about) to display correctly in English and Spanish.
- [ ] Get all test in `S03_WebMenuCategoryControllerTests` passing by correctly implementing methods in `WebMenuCategoryController`. Once thes tests are passing, the [Menu Categories](http://localhost:3000/categories) pages should work correctly.
- [ ] Get all test in `S04_WebMenuItemTests` passing by correctly implementing methods in `WebMenuItemController`.Once thes tests are passing, the [Menu Items](http://localhost:3000/items) pages should work correctly.
- [ ] Add multilingual validation constraint messages using the instructions below. Test this by attempting to submit blank new menu items and menu categories. The field errors should show English or Spanish, depending on your browser settings.

## Adding support for Internationalization

- [ ] under __src > main > resources > i18n__ add a new file called `messages.properties` with the following contents:

```
view.about.message=At Degrees restaurant, you’ll find familiar favorites with a gourmet twist. We invite you to join us for lunch and dinner at Columbus State’s campus in the heart of the Discovery District. Here, students in our award-winning culinary arts program work with industry professionals to deliver carefully crafted food and outstanding hospitality.
view.about.title=About
view.about.parking=Parking is available on the street and in the lot directly behind Mitchell Hall
view.about.hours=CURRENTLY CLOSED
view.about.phone-label=Phone
view.about.location-label=Location
view.about.hours-label=Hours
```
  
- [ ] under __src > main > resources > i18n__ add a new file called `messages_es.properties` with the following contents:

```
view.about.message=En el restaurante Degrees, encontrará favoritos familiares con un toque gourmet. Te invitamos a unirte a nosotros para el almuerzo y la cena en el campus de Columbus State en el corazón del Discovery District. Aquí, los estudiantes de nuestro galardonado programa de artes culinarias trabajan con profesionales de la industria para ofrecer alimentos cuidadosamente elaborados y una hospitalidad excepcional.
view.about.title=Acerca de
view.about.parking=El estacionamiento está disponible en la calle y en el lote directamente detrás de Mitchell Hall
view.about.hours=ACTUALMENTE CERRADO
view.about.phone-label=Teléfono
view.about.location-label=Ubicación
view.about.hours-label=Horas
```

- [ ] Add the following code to `edu.cscc.degrees.DegreesApplication`:

```java
@Bean
public ResourceBundleMessageSource messageSource () {

    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasename("i18n/messages");
    source.setDefaultEncoding("UTF-8");
    return source;
}
```

- [ ] Verify the test in `S02_MessageSourcePropertyTests` runs successfully.
- [ ] Visit the [About]((http://localhost:3000/about) page and make sure you see the English content from the first property file.
- [ ] Change your preferred language to Spanish. Refresh the page and make sure you see the content in Spanish.

## Adding support for multilingual validation constraint messages

- [ ] under __src > main > resources > i18n__ add a new file called `messages.properties` with the following contents:

```
#https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html
javax.validation.constraints.min.Size.message=Must be at least {min} characters
javax.validation.constraints.NotNull.message=Must not be null
javax.validation.constraints.Size.message=Must be between {min} and {max} characters
```
  
- [ ] under __src > main > resources > i18n__ add a new file called `ValidationMessages.properties` with the following contents:

```
javax.validation.constraints.min.Size.message = Debe tener al menos {min} caracteres
javax.validation.constraints.NotNull.message=No debe ser nulo
javax.validation.constraints.Size.message=Debe tener entre {min} y {max} caracteres
```

- [ ] Add the following code to `edu.cscc.degrees.DegreesApplication`:

```java
@Bean
public LocalValidatorFactoryBean getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasename("i18n/ValidationMessages");
    source.setDefaultEncoding("UTF-8");

    bean.setValidationMessageSource(source);
    return bean;
}
```

- [ ] Verify the test in `S02_MessageSourcePropertyTests` runs successfully.
- [ ] Visit the [About]((http://localhost:3000/about) page and make sure you see the English content from the first property file.
- [ ] Change your preferred language to Spanish. Refresh the page and make sure you see the content in Spanish.
