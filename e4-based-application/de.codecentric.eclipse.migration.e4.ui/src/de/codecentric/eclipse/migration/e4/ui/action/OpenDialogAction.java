package de.codecentric.eclipse.migration.e4.ui.action;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;

public class OpenDialogAction {

	@Execute
	public void execute() {
		MessageDialog.openInformation(null, "Info", "Opened dialog via action");
	}

}
