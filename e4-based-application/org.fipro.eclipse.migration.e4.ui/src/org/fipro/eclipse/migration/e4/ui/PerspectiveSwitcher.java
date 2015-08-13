package org.fipro.eclipse.migration.e4.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.ElementContainer;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;

public class PerspectiveSwitcher {
	
    @Inject
    EModelService modelService;
	
    @Inject
    EPartService partService;

    // FIXME potential bug, toolcontrol rendering is not executed via contribution
    
    @PostConstruct
    public void createGui(Composite parent, MApplication app, MWindow window) {
    	
		List<MPerspectiveStack> psList = modelService.findElements(window, null,
				MPerspectiveStack.class, null);
		if (psList.size() > 0) {
			System.out.println(psList.get(0).getChildren().size());
		}

        List<MPerspective> perspectives = 
            modelService.findElements(app, null, MPerspective.class, null);
		
        for (final MPerspective perspective : perspectives) {
            Button button = new Button(parent, SWT.PUSH);
            button.setText(perspective.getLabel());
            button.addSelectionListener(new SelectionAdapter() {
				
                @Override
                public void widgetSelected(SelectionEvent e) {
                    partService.switchPerspective(perspective);
                }
            });
        }
    }

	@Inject
	@Optional
	private void subscribeTopicChildAdded(@UIEventTopic(ElementContainer.TOPIC_CHILDREN) Event event) {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MPerspectiveStack) {
			System.out.println("perspective added");
			if (UIEvents.isADD(event)) {
			}
		}
	}

}
