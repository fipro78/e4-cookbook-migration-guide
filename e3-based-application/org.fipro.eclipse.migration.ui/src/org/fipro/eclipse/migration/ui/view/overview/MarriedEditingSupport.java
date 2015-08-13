package org.fipro.eclipse.migration.ui.view.overview;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.fipro.eclipse.migration.model.Person;

public class MarriedEditingSupport extends EditingSupport {

	public MarriedEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new CheckboxCellEditor((Composite) getViewer().getControl(), SWT.CHECK);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return ((Person) element).isMarried();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((Person) element).setMarried((Boolean)value);
	    getViewer().update(element, null);
	}

}
