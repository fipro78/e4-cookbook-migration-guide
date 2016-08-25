package org.fipro.eclipse.migration.e4.ui.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.fipro.eclipse.migration.e4.model.Person;
import org.fipro.eclipse.migration.e4.model.Person.Gender;

@SuppressWarnings("restriction")
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
	
	@Inject
	@Optional
	void setTextColor(
			@Preference(nodePath="org.fipro.eclipse.migration.e4.ui", value="description_color") String color) {

		Color toUse = "blue".equals(color) ? 
				Display.getDefault().getSystemColor(SWT.COLOR_BLUE) : Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		
		if (description != null && !description.isDisposed()) {
			description.setForeground(toUse);
		}
	}
}
