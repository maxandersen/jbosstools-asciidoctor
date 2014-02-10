/*******************************************************************************
 * Copyright (c) 2012 Stefan Seelmann and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stefan Seelmann - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.wikitext.asciidoc.tests;

import org.eclipse.mylyn.wikitext.tests.TestUtil;

/**
 * @author Stefan Seelmann
 */
public class AsciidocLanguageTest extends AsciidocLanguageTestBase {

	public void testFullExample() {

		StringBuilder text = new StringBuilder();
		text.append("Header 1\n");
		text.append("-------\n");
		text.append("\n");
		text.append("Lorem ipsum *dolor* sit amet, \n");
		text.append("\n");
		text.append("=== Header 2\n");
		text.append("\n");
		text.append("consetetur _adipisici_ elit.\n");

		String html = parseToHtml(text.toString());
		TestUtil.println("HTML: " + html);

		assertTrue(html.contains("Header 1</h2>"));
		assertTrue(html.contains("<p>Lorem ipsum"));
		assertTrue(html.contains("<strong>dolor"));
		assertTrue(html.contains("Header 2</h3>"));
		assertTrue(html.contains("<em>adipisici</em>"));
	}
}
