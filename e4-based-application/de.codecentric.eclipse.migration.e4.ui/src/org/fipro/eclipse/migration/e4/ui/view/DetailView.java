package org.fipro.eclipse.migration.e4.ui.view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.modeling.ISelectionListener;
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
import org.fipro.eclipse.migration.e4.model.Person;
import org.fipro.eclipse.migration.e4.model.Person.Gender;
import org.fipro.eclipse.migration.e4.ui.view.overview.OverviewView;

public class DetailView {

	@Inject
	ESelectionService selectionService;

	Person activePerson = new Person(-1);
	
	ISelectionListener selectionListener = new ISelectionListener() {
		
		@Override
		public void selectionChanged(MPart part, Object selection) {
			if (part.getObject() instanceof OverviewView) {
				if (selection != null
					&& selection instanceof Person) {
					Person p = (Person) selection;
					activePerson.setFirstName(p.getFirstName());
					activePerson.setLastName(p.getLastName());
					activePerson.setMarried(p.isMarried());
					activePerson.setGender(p.getGender());
				}
				else {
					activePerson.setFirstName(null);
					activePerson.setLastName(null);
					activePerson.setMarried(false);
					activePerson.setGender(null);
				}
			}
		}
	};
	
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
	    
		// add the selection listener
		selectionService.addSelectionListener(selectionListener);
	}

	@PreDestroy
	public void dispose() {
		selectionService.removeSelectionListener(selectionListener);
	}
}
