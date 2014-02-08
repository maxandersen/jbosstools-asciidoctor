/*******************************************************************************
 * Copyright (c) 2014 Jeremie Bresson and others.
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
 * Tests for asciidoc span elements.
 *
 * @author Jeremie Bresson
 */
public class AsciidocLanguageSpanElementsTest extends AsciidocLanguageTestBase {

	public void testStrong() {
		String html = parseToHtml("*foo bar*");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p><strong>foo bar</strong></p>\n"));
	}

	public void testEscapedStrong() {
		String html = parseToHtml("\\*foo bar*");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>*foo bar*</p>\n"));
	}

	public void testEmphasis() {
		String html = parseToHtml("_foo bar_");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p><em>foo bar</em></p>\n"));
	}

	public void testEscapedEmphasis() {
		String html = parseToHtml("\\_foo bar_");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>_foo bar_</p>\n"));
	}

	public void testCode() {
		String html = parseToHtml("+foo bar+");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p><code>foo bar</code></p>\n"));
	}

	public void testEscapedCode() {
		String html = parseToHtml("\\+foo bar+");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>+foo bar+</p>\n"));
	}


}
