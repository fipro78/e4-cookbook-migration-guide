package de.codecentric.eclipse.migration.e4.ui.view.overview;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.codecentric.eclipse.migration.e4.model.Person;

public class MarriedLabelProvider extends StyledCellLabelProvider {
	
	private final Image uncheckedImg;
	private final Image checkedImg;

	public MarriedLabelProvider() {
		LocalResourceManager resourceMgr = new LocalResourceManager(JFaceResources.getResources());
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		URL checked = FileLocator.find(bundle, new Path("icons/checked.gif"), null);
		URL unchecked = FileLocator.find(bundle, new Path("icons/unchecked.gif"), null);
		this.checkedImg = resourceMgr.createImage(ImageDescriptor.createFromURL(checked));
		this.uncheckedImg = resourceMgr.createImage(ImageDescriptor.createFromURL(unchecked));
	}

	@Override
	public void update(ViewerCell cell) {
		if (((Person)cell.getElement()).isMarried()) {
			cell.setImage(checkedImg);
		}
		else {
			cell.setImage(uncheckedImg);
		}
	}
}