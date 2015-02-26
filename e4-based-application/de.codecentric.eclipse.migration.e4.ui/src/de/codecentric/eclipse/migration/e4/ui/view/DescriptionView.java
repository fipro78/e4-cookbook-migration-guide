package de.codecentric.eclipse.migration.e4.ui.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import de.codecentric.eclipse.migration.e4.model.Person;
import de.codecentric.eclipse.migration.e4.model.Person.Gender;

public class DescriptionView {

	Text description;
	
	@PostConstruct
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		description = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
	}

	@Inject
	void updateDescription(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Person person) {
		if (description != null && !description.isDisposed()) {
			if (person != null) {
				description.setText(person.getFirstName() + " " + person.getLastName() 
						+ " is a " + (person.isMarried() ? "married " : "single ")
						+ (Gender.MALE.equals(person.getGender()) ? "man" : "woman"));
			}
			else {
				description.setText("");
			}
		}
	}
}
