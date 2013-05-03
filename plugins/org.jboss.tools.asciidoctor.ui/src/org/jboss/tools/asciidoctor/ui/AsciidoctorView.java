package org.jboss.tools.asciidoctor.ui;

import java.util.Collections;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Asciidoctor.Factory;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class AsciidoctorView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.jboss.tools.asciidoctor.ui.AsciidoctorView";

	private Browser browser;

	final private ISelectionListener selectionlistener;

	Asciidoctor asciidoctor;

	public AsciidoctorView() {

		selectionlistener = new ISelectionListener() {

			@Override
			public void selectionChanged(IWorkbenchPart arg0, ISelection arg1) {
				if (browser != null) {
					try {

						if (asciidoctor == null) {
							asciidoctor = Factory.create();
						}

						String string = "*This* is it at " + arg1.toString()
								+ " from _" + arg0.getTitle() + "_";
						String rendered = asciidoctor.render(string,
								Collections.EMPTY_MAP);
						System.out.println(rendered);
						browser.setText(rendered);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(selectionlistener);
		browser = new Browser(parent, SWT.None);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AsciidoctorView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(browser);
		browser.setMenu(menu);
		// getSite().registerContextMenu(menuMgr, this);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		// manager.add(action1);
		// manager.add(new Separator());
		// manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		// manager.add(action1);
		// manager.add(action2);
		// Other plug-ins can contribute there actions here
		// manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		// manager.add(action1);
		// manager.add(action2);
	}

	private void makeActions() {

	}

	private void hookDoubleClickAction() {
		/*
		 * viewer.addDoubleClickListener(new IDoubleClickListener() { public
		 * void doubleClick(DoubleClickEvent event) { doubleClickAction.run(); }
		 * });
		 */
	}

	@Override
	public void dispose() {
		ISelectionService s = getSite().getWorkbenchWindow()
				.getSelectionService();
		s.removeSelectionListener(selectionlistener);
		super.dispose();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		browser.setFocus();
	}
}