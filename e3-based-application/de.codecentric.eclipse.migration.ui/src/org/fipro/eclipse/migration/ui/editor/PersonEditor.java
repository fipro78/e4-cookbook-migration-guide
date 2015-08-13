package org.fipro.eclipse.migration.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.fipro.eclipse.migration.model.Person;
import org.fipro.eclipse.migration.model.Person.Gender;


public class PersonEditor extends EditorPart {

	public static final String ID = "org.fipro.eclipse.migration.ui.editor.personeditor";
	
	Person person;
	Person activePerson;
	
	boolean dirty = false;
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof PersonEditorInput)) {
			throw new RuntimeException("Wrong input");
		}
		
		this.person = ((PersonEditorInput) input).person;
		this.activePerson = new Person(this.person);
	    setSite(site);
	    setInput(input);
	    setPartName(input.getName());
	}
	
	@Override
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
		IObservableValue fnTarget = 
				WidgetProperties.text(SWT.Modify).observe(firstNameField);
		IObservableValue lnTarget = 
				WidgetProperties.text(SWT.Modify).observe(lastNameField);
		IObservableValue mTarget = 
				WidgetProperties.selection().observe(marriedButton);
		IObservableValue gTarget = 
				ViewersObservables.observeSingleSelection(genderCombo);
		
		IObservableValue fnModel= BeanProperties.
				value(Person.class,"firstName").observe(activePerson);
		IObservableValue lnModel= BeanProperties.
				value(Person.class,"lastName").observe(activePerson);
		IObservableValue mModel= BeanProperties.
				value(Person.class,"married").observe(activePerson);
		IObservableValue gModel= BeanProperties.
				value(Person.class,"gender").observe(activePerson);
		
		ctx.bindValue(fnTarget, fnModel); 
		ctx.bindValue(lnTarget, lnModel); 
		ctx.bindValue(mTarget, mModel);
		ctx.bindValue(gTarget, gModel);
		
		this.activePerson.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				dirty = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		});
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		this.person.setFirstName(this.activePerson.getFirstName());
		this.person.setLastName(this.activePerson.getLastName());
		this.person.setMarried(this.activePerson.isMarried());
		this.person.setGender(this.activePerson.getGender());
		
		// TODO update table
		
		this.dirty = false;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
	}

}
