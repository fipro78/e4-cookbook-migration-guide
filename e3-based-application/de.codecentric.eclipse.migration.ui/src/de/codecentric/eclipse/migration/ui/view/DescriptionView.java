package de.codecentric.eclipse.migration.ui.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import de.codecentric.eclipse.migration.model.Person;
import de.codecentric.eclipse.migration.model.Person.Gender;
import de.codecentric.eclipse.migration.ui.view.overview.OverviewView;

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
