package org.fipro.eclipse.migration.ui.view.overview;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.fipro.eclipse.migration.model.Person;

public class FirstNameLabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
		Person element= (Person) cell.getElement();
		cell.setText(element.getFirstName());
		super.update(cell);
	}
}