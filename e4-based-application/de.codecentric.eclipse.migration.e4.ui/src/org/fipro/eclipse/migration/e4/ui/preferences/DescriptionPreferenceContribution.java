package org.fipro.eclipse.migration.e4.ui.preferences;

import org.fipro.eclipse.migration.e4.service.preferences.PreferenceNodeContribution;

public class DescriptionPreferenceContribution extends PreferenceNodeContribution {

	public DescriptionPreferenceContribution() {
		super("description", "Description", null, DescriptionPreferencePage.class, null, null);
	}
}
