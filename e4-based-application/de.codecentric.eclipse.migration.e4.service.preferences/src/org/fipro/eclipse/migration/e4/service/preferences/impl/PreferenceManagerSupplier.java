package org.fipro.eclipse.migration.e4.service.preferences.impl;

import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.core.di.suppliers.IRequestor;
import org.eclipse.jface.preference.PreferenceManager;
import org.fipro.eclipse.migration.e4.service.preferences.ContributedPreferenceNode;
import org.fipro.eclipse.migration.e4.service.preferences.PreferenceNodeContribution;

public class PreferenceManagerSupplier extends ExtendedObjectSupplier {

	PreferenceManager mgr;
	
	@Override
	public Object get(IObjectDescriptor descriptor, IRequestor requestor, boolean track, boolean group) {
		return getManager();
	}

	protected PreferenceManager getManager() {
		if (this.mgr == null) {
			this.mgr = new PreferenceManager();
		}
		return mgr;
	}
	
	public synchronized void addPreferenceNode(PreferenceNodeContribution node) {
		for (ContributedPreferenceNode contribNode : node.getPreferenceNodes()) {
			if (contribNode.getPath() == null) {
				getManager().addToRoot(contribNode);
			}
			else {
				getManager().addTo(contribNode.getPath(), contribNode);
			}
		}
	}
	
	public synchronized void removePreferenceNode(PreferenceNodeContribution node) {
		for (ContributedPreferenceNode contribNode : node.getPreferenceNodes()) {
			getManager().remove(contribNode);
		}
	}
}
