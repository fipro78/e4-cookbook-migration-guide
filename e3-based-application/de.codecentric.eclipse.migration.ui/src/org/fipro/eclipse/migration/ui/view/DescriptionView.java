package org.fipro.eclipse.migration.ui.view;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.fipro.eclipse.migration.model.Person;
import org.fipro.eclipse.migration.model.Person.Gender;
import org.fipro.eclipse.migration.ui.Activator;
import org.fipro.eclipse.migration.ui.view.overview.OverviewView;

public class DescriptionView extends ViewPart {

	Text description;
	
	ISelectionListener selectionListener = new ISelectionListener() {
		
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (part instanceof OverviewView 
					&& selection instanceof IStructuredSelection) {
				
				if (!selection.isEmpty()) {
					Object selected = ((IStructuredSelection) selection).getFirstElement();
					Person p = (Person) selected;
					description.setText(p.getFirstName() + " " + p.getLastName() 
							+ " is a " + (p.isMarried() ? "married " : "single ")
							+ (Gender.MALE.equals(p.getGender()) ? "man" : "woman"));
				}
				else {
					description.setText("");
				}
			}
		}
	};
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		description = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		
		// read preferences to set the initial text color
		String color = Platform.getPreferencesService().
				  getString("org.fipro.eclipse.migration.ui", "description_color", "black", null);
		
		Color toUse = "blue".equals(color) ? 
				Display.getDefault().getSystemColor(SWT.COLOR_BLUE) : Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		
		description.setForeground(toUse);

		// register a listener on the PreferencesStore to react on changes
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(
				new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getProperty() == "description_color") {
							Color toUse = "blue".equals(event.getNewValue()) ? 
									Display.getDefault().getSystemColor(SWT.COLOR_BLUE) : Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
							
							if (description != null && !description.isDisposed()) {
								description.setForeground(toUse);
							}
						}
					}
				});
		
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void dispose() {
		// on disposal remove the selection listener
		ISelectionService s = getSite().getWorkbenchWindow().getSelectionService();
		s.removeSelectionListener(selectionListener);
		
		super.dispose();
	}
}
