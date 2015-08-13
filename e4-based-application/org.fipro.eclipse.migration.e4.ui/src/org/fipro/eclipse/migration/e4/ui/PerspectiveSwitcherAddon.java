package org.fipro.eclipse.migration.e4.ui;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;

public class PerspectiveSwitcherAddon {

	static final String PERSPECTIVE_ONE = "org.fipro.toolbar.perspective.one";
	static final String PERSPECTIVE_TWO = "org.fipro.toolbar.perspective.two";
	
	static final String FIRST_TOOLBAR = "org.eclipse.ui.main.toolbar";
	static final String SECOND_TOOLBAR = "org.fipro.e4.toolbar.toolbar.0";
	
	@Inject
	EModelService modelService;

	@Inject
	@Optional
	public void subscribeTopicSelectedElement(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event,
			MApplication application) {
		
		Object newValue = event.getProperty(EventTags.NEW_VALUE);
		if (newValue instanceof MPerspective) {
			boolean isPerspectiveOne = ((MPerspective) newValue).getElementId().equals(PERSPECTIVE_ONE);
			
			MTrimBar top = (MTrimBar) modelService.find("org.eclipse.ui.trimbar.top", application.getChildren().get(0));
			
			for (MTrimElement item : top.getChildren()) {
				if ((isPerspectiveOne && item.getElementId().equals(SECOND_TOOLBAR))
						|| !isPerspectiveOne && item.getElementId().equals(FIRST_TOOLBAR)) {
					item.setVisible(false);
				}
				else if ((!isPerspectiveOne && item.getElementId().equals(SECOND_TOOLBAR))
						|| isPerspectiveOne && item.getElementId().equals(FIRST_TOOLBAR)) {
					item.setVisible(true);
				}
			}
		}
	}
}
