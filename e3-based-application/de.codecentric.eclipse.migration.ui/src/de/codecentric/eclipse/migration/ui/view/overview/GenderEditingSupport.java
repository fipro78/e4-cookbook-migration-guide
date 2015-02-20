package de.codecentric.eclipse.migration.ui.view.overview;

import java.util.Arrays;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import de.codecentric.eclipse.migration.model.Person;
import de.codecentric.eclipse.migration.model.Person.Gender;

public class GenderEditingSupport extends EditingSupport {

	private ComboBoxViewerCellEditor cellEditor;
	
	public GenderEditingSupport(ColumnViewer viewer) {
		super(viewer);
		cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl());
		cellEditor.setLabelProvider(new LabelProvider());
        cellEditor.setContentProvider(new ArrayContentProvider());
        cellEditor.setInput(Arrays.asList(Gender.FEMALE, Gender.MALE));
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return ((Person) element).getGender();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((Person) element).setGender((Gender)value);
	    getViewer().update(element, null);
	}

}
