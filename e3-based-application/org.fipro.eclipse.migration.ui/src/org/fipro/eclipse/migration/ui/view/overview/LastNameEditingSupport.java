package org.fipro.eclipse.migration.ui.view.overview;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.fipro.eclipse.migration.model.Person;

public class LastNameEditingSupport extends EditingSupport {

	public LastNameEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor((Composite) getViewer().getControl());
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return ((Person) element).getLastName();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((Person) element).setLastName(String.valueOf(value));
	    getViewer().update(element, null);
	}
}
