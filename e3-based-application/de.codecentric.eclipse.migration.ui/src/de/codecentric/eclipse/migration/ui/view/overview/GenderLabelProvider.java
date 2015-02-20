package de.codecentric.eclipse.migration.ui.view.overview;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import de.codecentric.eclipse.migration.model.Person;

public class GenderLabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
		Person element= (Person) cell.getElement();
		cell.setText(element.getGender().toString());
		super.update(cell);
	}
}
