package org.sample;

import com.vaynberg.wicket.select2.Response;
import com.vaynberg.wicket.select2.Select2Choice;
import com.vaynberg.wicket.select2.TextChoiceProvider;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomePage extends WebPage
{
    private static final long serialVersionUID = 1L;
    private User user = new User();
    private UserChoiceProvider userChoiceProvider = new UserChoiceProvider();
    private Form<User> userForm;

    public HomePage(final PageParameters parameters)
    {
        super(parameters);
        add(createForm());
    }

    private Form<User> createForm()
    {
        userForm = new Form<User>("form", new CompoundPropertyModel<User>(user));
        userForm.add(createNameField());
        userForm.add(createCountryField());
        userForm.add(createClearLink("clearLink"));
        userForm.setOutputMarkupId(true);
        userForm.setOutputMarkupPlaceholderTag(true);
        return userForm;
    }

    private TextField<String> createNameField()
    {
        return new TextField<String>("name");
    }

    private Select2Choice<Country> createCountryField()
    {
        Select2Choice<Country> roleChoiceField = new Select2Choice<Country>("country",
                new PropertyModel<Country>(user, "country"), userChoiceProvider);
        roleChoiceField.getSettings().setMinimumInputLength(1);
        roleChoiceField.getSettings().setAllowClear(true);
        roleChoiceField.setOutputMarkupId(true);
        return roleChoiceField;
    }

    protected AjaxSubmitLink createClearLink(String id)
    {
        AjaxSubmitLink clearLink = new AjaxSubmitLink(id, userForm)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                HomePage.this.onClear(target, form);
            }
        };
        clearLink.add(createResetLabel());
        return clearLink;
    }

    protected void onClear(AjaxRequestTarget target, Form<?> form)
    {
        form.visitFormComponents(new ClearFormVisitor());
        target.add(form);
    }

    private Label createResetLabel()
    {
        return new Label("clearLabel", "Clear");
    }

    private class UserChoiceProvider extends TextChoiceProvider<Country>
    {
        @Override
        protected String getDisplayText(Country choice)
        {
            return choice.getCountryName();
        }

        @Override
        protected Object getId(Country choice)
        {
            return choice.getCountryName();
        }

        @Override
        public void query(String term, int page, Response<Country> choices)
        {
            choices.addAll(getMatchedChoices(term, page, 12));
            choices.setHasMore(choices.size() == 12);
        }

        private Collection<? extends Country> getMatchedChoices(String term, int page, int maxSize)
        {
            List<Country> matchedChoices = new ArrayList<Country>();
            final int offset = page * maxSize;
            int matched = 0;
            for (Country choice : getAllChoices()) {
                if (matchedChoices.size() == maxSize) {
                    break;
                }
                if (choice.getCountryName().toUpperCase().contains(term.toUpperCase())) {
                    matched++;
                    if (matched > offset) {
                        matchedChoices.add(choice);
                    }
                }
            }
            return matchedChoices;
        }

        private List<Country> getAllChoices()
        {
            List<Country> countries = new ArrayList<Country>();
            countries.add(new Country("USA"));
            countries.add(new Country("UK"));
            countries.add(new Country("Canada"));
            countries.add(new Country("Astria"));
            return countries;
        }

        @Override
        public Collection<Country> toChoices(Collection<String> ids)
        {
            List<Country> selectedChoices = new ArrayList<Country>();
            for (String selectedId : ids) {
                selectedChoices.add(getSelectedChoice(selectedId));
            }
            return selectedChoices;
        }

        private Country getSelectedChoice(String selectedId)
        {
            for (Country country : getAllChoices()) {
                if (selectedId.equalsIgnoreCase(country.getCountryName())) {
                    return country;
                }
            }
            return new Country();
        }
    }
}
