package de.codecentric.eclipse.migration.e4.ui.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;

public class OpenDialogHandler {

	@Execute
	public void execute() {
		MessageDialog.openInformation(null, "Info", "Opened dialog via handler");
	}

}
