package org.fipro.eclipse.migration.ui.view.overview;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.fipro.eclipse.migration.model.Person;
import org.fipro.eclipse.migration.service.PersonService;
import org.fipro.eclipse.migration.ui.editor.PersonEditor;
import org.fipro.eclipse.migration.ui.editor.PersonEditorInput;

public class OverviewView extends ViewPart {

	TableViewer viewer;
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout());
		
		IObservable list = new WritableList(PersonService.getPersons(10), Person.class);

		viewer = new TableViewer(parent, SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		ObservableListContentProvider cp = new ObservableListContentProvider();
		viewer.setContentProvider(cp);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText("Firstname");
		column.getColumn().setWidth(100);
		column.setLabelProvider(new FirstNameLabelProvider());
//		column.setEditingSupport(new FirstNameEditingSupport(viewer));
		
		column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText("Lastname");
		column.getColumn().setWidth(100);
		column.setLabelProvider(new LastNameLabelProvider());
//		column.setEditingSupport(new LastNameEditingSupport(viewer));

		column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText("Married");
		column.getColumn().setWidth(60);
		column.setLabelProvider(new MarriedLabelProvider());
//		column.setEditingSupport(new MarriedEditingSupport(viewer));
		
		column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText("Gender");
		column.getColumn().setWidth(80);
		column.setLabelProvider(new GenderLabelProvider());
//		column.setEditingSupport(new GenderEditingSupport(viewer));
		
		viewer.setInput(list);
		
		// set the viewer as selection provider
		getSite().setSelectionProvider(viewer);
		
		// hook double click for opening an editor
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = viewer.getSelection();
				if (selection != null && selection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) selection).getFirstElement();
					// if we had a selection lets open the editor
					if (obj != null) {
						Person person = (Person) obj;
						person.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								viewer.refresh();
							}
						});
						PersonEditorInput input = new PersonEditorInput(person);
				        try {
				        	getSite().getWorkbenchWindow().getActivePage().openEditor(input, PersonEditor.ID);

				        } catch (PartInitException e) {
				        	throw new RuntimeException(e);
				        }
					}
				}
			}
		});
	}

	@Override
	public void setFocus() {
		this.viewer.getControl().setFocus();
	}

}
