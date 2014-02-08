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
 * Tests for asciidoc overview and miscellaneous.
 *
 * @author Stefan Seelmann
 */
public class AsciidocLanguageMiscellaneousTest extends AsciidocLanguageTestBase {

	public void testEmptyLine() {
		String html = parseToHtml("    ");
		TestUtil.println("HTML: {" + html + "}");
		assertTrue(html.matches("\\s*"));
	}

	public void testPreserveHtmlEntities() {
		String html = parseToHtml("&copy; &amp;");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>&copy; &amp;</p>"));
	}

	public void testAmpersandIsEscaped() {
		String html = parseToHtml("AT&T, a & b");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>AT&amp;T, a &amp; b</p>"));
	}

	public void testAngleBracketsAreEscaped() {
		//lower than:
		String html = parseToHtml("4 < 5");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>4 &lt; 5</p>"));

		//greater than:
		html = parseToHtml("6 > 5");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>6 &gt; 5</p>"));
	}

	public void testBackslashBackslash() {
		//this is not an escaped backslash
		String html = parseToHtml("\\\\");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\\\</p>"));
	}

	public void testBackslashBacktick() {
		//this is not an escaped backtick
		String html = parseToHtml("\\`");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\`</p>"));
	}

	/* FIXME
	public void testBackslashAsterisk() {
		//this is not an escaped asterisk
		String html = parseToHtml("\\*");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\*</p>"));
	}
	*/

	/* FIXME
	public void testBackslashUnderscore() {
		//this is not an escaped underscore
		String html = parseToHtml("\\_");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\_</p>"));
	}
	 */

	public void testBackslashOpeningCurlyBrace() {
		//this is not an escaped opening curly brace
		String html = parseToHtml("\\{");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\{</p>"));
	}

	public void testBackslashClosingCurlyBrace() {
		//this is not an escaped closing curly brace
		String html = parseToHtml("\\}");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\}</p>"));
	}

	public void testBackslashOpeningSquareBracket() {
		//this is not an escaped opening square bracket
		String html = parseToHtml("\\[");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\[</p>"));
	}

	public void testBackslashClosingSquareBracket() {
		//this is not an escaped closing square bracket
		String html = parseToHtml("\\]");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\]</p>"));
	}

	public void testBackslashOpeningParenthesis() {
		//this is not an escaped opening parenthesis
		String html = parseToHtml("\\(");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\(</p>"));
	}

	public void testBackslashClosingParenthesis() {
		//this is not an escaped closing parenthesis
		String html = parseToHtml("\\)");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\)</p>"));
	}

	public void testBackslashHashMark() {
		//this is not an escaped hash mark
		String html = parseToHtml("\\#");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\#</p>"));
	}

	/* FIXME
	public void testBackslashPlusSign() {
		//this is not an escaped plus sign
		String html = parseToHtml("\\+");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\+</p>"));
	}
	*/

	public void testBackslashMinusSign() {
		//this is not an escaped minus sign
		String html = parseToHtml("\\-");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\-</p>"));
	}

	public void testBackslashDot() {
		//this is not an escaped dot
		String html = parseToHtml("\\.");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\.</p>"));
	}

	public void testBackslashExclamationMark() {
		//this is not an escaped exclamation mark
		String html = parseToHtml("\\!");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>\\!</p>"));
	}
}