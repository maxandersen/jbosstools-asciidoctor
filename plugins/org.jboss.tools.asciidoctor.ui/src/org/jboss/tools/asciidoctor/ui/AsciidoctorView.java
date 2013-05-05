package org.jboss.tools.asciidoctor.ui;

import java.util.Map;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Asciidoctor.Factory;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class AsciidoctorView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.jboss.tools.asciidoctor.ui.AsciidoctorView";

	private Browser browser;

	private IDocument currentDocument;

	Asciidoctor asciidoctor;

	private IDocumentListener docListener = new IDocumentListener() {

		@Override
		public void documentChanged(DocumentEvent event) {

			String content = event.getDocument().get();

			updateViewer(content);
		}

		@Override
		public void documentAboutToBeChanged(DocumentEvent arg0) {
			// TODO Auto-generated method stub

		}
	};

	private IPartListener2 partListener = new IPartListener2() {
		
		@Override
		public void partVisible(IWorkbenchPartReference arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void partOpened(IWorkbenchPartReference part) {

		}

		@Override
		public void partInputChanged(IWorkbenchPartReference arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void partHidden(IWorkbenchPartReference arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void partDeactivated(IWorkbenchPartReference arg0) {

		}

		@Override
		public void partClosed(IWorkbenchPartReference arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void partActivated(IWorkbenchPartReference part) {
			if(part instanceof IEditorReference) {
				IEditorReference ier = (IEditorReference) part;
				IEditorPart editor = ier.getEditor(false);
				if(editor instanceof ITextEditor) {
					setupEditor((ITextEditor) editor);
				}
			} else if (part instanceof ITextEditor) {
				setupEditor((ITextEditor) part);
			}
		}

		private void setupEditor(ITextEditor editor) {
			IDocument document = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());

			if (currentDocument != null) {
				currentDocument.removeDocumentListener(docListener);
				currentDocument = null;
			} else {
				document.addPrenotifiedDocumentListener(docListener);
			}
		}
	};

	public AsciidoctorView() {
	}

	public void createPartControl(Composite parent) {

		getSite().getWorkbenchWindow().getActivePage()
				.addPartListener(partListener);
		browser = new Browser(parent, SWT.None);
	}

	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getActivePage()
		.removePartListener(partListener);

		if(currentDocument!=null) {
			currentDocument.removeDocumentListener(docListener);
		}
		
		super.dispose();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		browser.setFocus();
	}

	void updateViewer(String asciidoc) {
		if (browser != null) {
			
			try {

				if (asciidoctor == null) {
					asciidoctor = Factory.create();
				}

				Map<String, Object> options = OptionsBuilder.options()
		                .compact(false)
		                .headerFooter(true)
		                .safe(SafeMode.UNSAFE)
		                .backend("html")
		                .asMap();
				
				String rendered = asciidoctor.render(asciidoc, options);
				browser.setText(rendered);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}