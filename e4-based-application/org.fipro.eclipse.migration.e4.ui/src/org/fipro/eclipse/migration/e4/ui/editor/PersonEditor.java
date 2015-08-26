package org.fipro.eclipse.migration.e4.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.fipro.eclipse.migration.e4.model.Person;
import org.fipro.eclipse.migration.e4.model.Person.Gender;

public class PersonEditor {

	@Inject
	private MDirtyable dirtyable;

	public static final String ID = "org.fipro.eclipse.migration.ui.editor.personeditor";
	
	public static final String CONTRIBUTION_URI = "bundleclass://org.fipro.eclipse.migration.e4.ui/org.fipro.eclipse.migration.e4.ui.editor.PersonEditor";

	public static final String PERSON_INPUT_DATA = "personInputData";

	Person person;
	Person activePerson;

	boolean dirty = false;

	@Inject
	public void init(MPart part) throws PartInitException {
		Map<String, Object> transientData = part.getTransientData();
		
		// note that we are using transient data here, because the editor is not persisted anyway.
		// In order to persist the editor between sessions using part.getPersistedState(); is necessary
		if (!(transientData.get(PERSON_INPUT_DATA) instanceof Person)) {
			throw new RuntimeException("You forgot to pass the actual person as transient data input");
		}

		this.person = (Person) part.getTransientData().get(PERSON_INPUT_DATA);
		this.activePerson = new Person(this.person);
	}

	@PostConstruct
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Label firstNameLabel = new Label(parent, SWT.NONE);
		firstNameLabel.setText("Firstname:");
		GridDataFactory.defaultsFor(firstNameLabel).applyTo(firstNameLabel);

		Text firstNameField = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(firstNameField);

		Label lastNameLabel = new Label(parent, SWT.NONE);
		lastNameLabel.setText("Lastname:");
		GridDataFactory.defaultsFor(lastNameLabel).applyTo(lastNameLabel);

		Text lastNameField = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(lastNameField);

		Label marriedLabel = new Label(parent, SWT.NONE);
		marriedLabel.setText("Married:");
		GridDataFactory.defaultsFor(marriedLabel).applyTo(marriedLabel);

		Button marriedButton = new Button(parent, SWT.CHECK);

		Label genderLabel = new Label(parent, SWT.NONE);
		genderLabel.setText("Gender:");
		GridDataFactory.defaultsFor(marriedLabel).applyTo(genderLabel);

		ComboViewer genderCombo = new ComboViewer(parent);
		genderCombo.setContentProvider(ArrayContentProvider.getInstance());
		genderCombo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return super.getText(element);
			}
		});
		genderCombo.setInput(Gender.values());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(genderCombo.getControl());

		// add bindings
		DataBindingContext ctx = new DataBindingContext();
		IObservableValue fnTarget = WidgetProperties.text(SWT.Modify).observe(firstNameField);
		IObservableValue lnTarget = WidgetProperties.text(SWT.Modify).observe(lastNameField);
		IObservableValue mTarget = WidgetProperties.selection().observe(marriedButton);
		IObservableValue gTarget = ViewersObservables.observeSingleSelection(genderCombo);

		IObservableValue fnModel = BeanProperties.value(Person.class, "firstName").observe(activePerson);
		IObservableValue lnModel = BeanProperties.value(Person.class, "lastName").observe(activePerson);
		IObservableValue mModel = BeanProperties.value(Person.class, "married").observe(activePerson);
		IObservableValue gModel = BeanProperties.value(Person.class, "gender").observe(activePerson);

		ctx.bindValue(fnTarget, fnModel);
		ctx.bindValue(lnTarget, lnModel);
		ctx.bindValue(mTarget, mModel);
		ctx.bindValue(gTarget, gModel);

		this.activePerson.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				dirtyable.setDirty(true);
			}
		});
	}

	@Persist
	public void save() {
		this.person.setFirstName(this.activePerson.getFirstName());
		this.person.setLastName(this.activePerson.getLastName());
		this.person.setMarried(this.activePerson.isMarried());
		this.person.setGender(this.activePerson.getGender());

		dirtyable.setDirty(false);
	}
}
