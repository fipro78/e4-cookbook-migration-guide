package de.codecentric.eclipse.migration.e4.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DescriptionPreferencePage extends PreferencePage {

	// Names for preferences
	private static final String DESCRIPTION_COLOR = "description_color";

	// The checkboxes
	private Button checkBoxBlack;
	private Button checkBoxBlue;

	public DescriptionPreferencePage() {
		super("Description");
		setDescription("The preferences page for the Description view");
	}

	/**
	 * Creates the controls for this page
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		// Get the preference store
		IPreferenceStore preferenceStore = getPreferenceStore();
		
		String color = preferenceStore.getString(DESCRIPTION_COLOR);
		boolean isBlack = (color != null && !color.isEmpty()) ? "black".equals(color) : true;

		// Create the checkboxes
		checkBoxBlack = new Button(composite, SWT.RADIO);
		checkBoxBlack.setText("Text Color Black");
		checkBoxBlack.setSelection(isBlack);

		checkBoxBlue = new Button(composite, SWT.RADIO);
		checkBoxBlue.setText("Text Color Blue");
		checkBoxBlue.setSelection(!isBlack);

		checkBoxBlack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkBoxBlack.setSelection(true);
				checkBoxBlue.setSelection(false);
			}
		});
		
		checkBoxBlue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkBoxBlack.setSelection(false);
				checkBoxBlue.setSelection(true);
			}
		});
		
		return composite;
	}

	/**
	 * Called when user clicks Restore Defaults
	 */
	@Override
	protected void performDefaults() {
		// Get the preference store
		IPreferenceStore preferenceStore = getPreferenceStore();

		String color = preferenceStore.getString(DESCRIPTION_COLOR);
		boolean isBlack = (color != null && !color.isEmpty()) ? "black".equals(color) : true;

		// Reset the fields to the defaults
		checkBoxBlack.setSelection(isBlack);
		checkBoxBlue.setSelection(!isBlack);
	}

	/**
	 * Called when user clicks Apply or OK
	 * 
	 * @return boolean
	 */
	@Override
	public boolean performOk() {
		// Get the preference store
		IPreferenceStore preferenceStore = getPreferenceStore();

		// Set the values from the fields
		if (checkBoxBlack != null && checkBoxBlack.getSelection()) {
			preferenceStore.setValue(DESCRIPTION_COLOR, "black");
		}
		else if (checkBoxBlue != null && checkBoxBlue.getSelection()) {
			preferenceStore.setValue(DESCRIPTION_COLOR, "blue");
		}

		// Return true to allow dialog to close
		return true;
	}

}
