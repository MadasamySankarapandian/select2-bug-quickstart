package org.sample;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Madasamy
 * @since x.x
 */
public class ClearFormVisitor implements IVisitor<FormComponent<?>, Void>, Serializable
{
    private final Set<FormComponent<?>> visited = new HashSet<FormComponent<?>>();

    public ClearFormVisitor()
    {
    }

    @Override
    public void component(FormComponent<?> formComponent, IVisit<Void> visit)
    {
        if (!visited.contains(formComponent) && formComponent instanceof FormComponent && !(formComponent instanceof Button)) {
            final FormComponent<?> formComponent1 = (FormComponent<?>) formComponent;
            boolean required = formComponent1.isRequired();
            if (required) {
                formComponent1.setRequired(false);
            }

            formComponent1.modelChanging();
            formComponent1.setModelValue(new String[]{});

            if (required) {
                formComponent1.setRequired(true);
            }
            visited.add(formComponent1);
        }
    }
}
