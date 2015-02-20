package de.codecentric.eclipse.migration.e4.ui.view.overview;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import de.codecentric.eclipse.migration.e4.model.Person;

public class FirstNameLabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
		Person element= (Person) cell.getElement();
		cell.setText(element.getFirstName());
		super.update(cell);
	}
}