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

import java.util.regex.Pattern;

import org.eclipse.mylyn.wikitext.tests.TestUtil;

/**
 * Tests for asciidoc block elements.
 *
 * @author Stefan Seelmann
 */
public class AsciidocLanguageBlockElementsTest extends AsciidocLanguageTestBase {

	public void testParagraphWithOneLine() {
		String html = parseToHtml("a paragraph");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>a paragraph</p>"));
	}

	public void testParagraphWithMulitpleLines() {
		String html = parseToHtml("a paragraph\nwith multiple\nlines");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>a paragraph\nwith multiple\nlines</p>"));
	}

	public void testParagraphsSeparatedBySingleBlankLine() {
		String html = parseToHtml("a paragraph\n\nanother paragraph\n\n");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>a paragraph</p>"));
		assertTrue(html.contains("<p>another paragraph</p>"));
	}

	public void testParagraphsSeparatedByMulitpleBlankLines() {
		String html = parseToHtml("a paragraph\n\n\nanother paragraph\n\n\n");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>a paragraph</p>"));
		assertTrue(html.contains("<p>another paragraph</p>"));
	}

	public void testParagraphsSeparatedByMulitpleBlankLinesWithSpacesAndTabs() {
		String html = parseToHtml("a paragraph\n \n\t\nanother paragraph");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>a paragraph</p>"));
		assertTrue(html.contains("<p>another paragraph</p>"));
	}

	/* FIXME: this is the real line breack in paragraph syntax.
	public void testLineBreakInParagraph() {
		String html = parseToHtml("line  1 +\nline  2 +\nline  3");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<p>line  1<br/?>\nline  2<br/?>\nline  3</p>")
				.matcher(html)
				.find());
	}
	*/

	/*
	 * Headers.
	 */
	public void testEqStyleHeaderLevel1() {
		String html = parseToHtml("== This is an H2");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>This is an H2</h2>")
				.matcher(html)
				.find());
	}

	public void testEqStyleHeaderLevel2() {
		String html = parseToHtml("=== This is an H3");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h3[^>]*>This is an H3</h3>")
				.matcher(html)
				.find());
	}

	public void testEqStyleHeaderLevel3() {
		String html = parseToHtml("==== This is an H4");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h4[^>]*>This is an H4</h4>")
				.matcher(html)
				.find());
	}

	public void testEqStyleHeaderLevel4() {
		String html = parseToHtml("===== This is an H5");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>This is an H5</h5>")
				.matcher(html)
				.find());
	}

	/* FIXME:
	public void testEqStyleHeaderNotH6() {
		String html = parseToHtml("====== This is not h6");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>====== This is not h6</p>"));
	}
	*/

	/*
	 * Optionally, you may "close" equals-style headers.
	 */
	public void testClosedEqStyleHeaderLevel1() {
		String html = parseToHtml("== This is also H2 ==");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>This is also H2</h2>")
				.matcher(html)
				.find());
	}

	public void testClosedEqStyleHeaderLevel2() {
		String html = parseToHtml("=== This is also H3 ===");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h3[^>]*>This is also H3</h3>")
				.matcher(html)
				.find());
	}

	public void testClosedEqStyleHeaderLevel3() {
		String html = parseToHtml("==== This is also H4 ====");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h4[^>]*>This is also H4</h4>")
				.matcher(html)
				.find());
	}

	public void testClosedEqStyleHeaderLevel4() {
		String html = parseToHtml("===== This is also H5 =====");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>This is also H5</h5>")
				.matcher(html)
				.find());
	}

	/* FIXME:
	public void testClosedEqStyleHeaderNotH6() {
		String html = parseToHtml("====== This is also not h6 ======");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>====== This is also not h6 ======</p>"));
	}
	 */

	/* FIXME:
	public void testClosedEqStyleHeaderWithMoreClosingHashes() {
		String html = parseToHtml("== This is an H2 again ==================");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>This is an H2 again ==================</h2>")
				.matcher(html)
				.find());
	}
	*/

	/* FIXME:
	public void testClosedAtxStyleHeaderWithLessCosingHashes() {
		String html = parseToHtml("===== This is an H5 again ==");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>This is an H5 again ==</h5>")
				.matcher(html)
				.find());
	}
	*/


	/*
	 * "underlined" headers
	 */
	public void testUnderlinedLevel1() {
		String html = parseToHtml("This is an underlined H2\n------------------------");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>This is an underlined H2</h2>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel2() {
		String html = parseToHtml("This is an underlined H3\n~~~~~~~~~~~~~~~~~~~~~~~~");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h3[^>]*>This is an underlined H3</h3>")
				.matcher(html)
				.find());
		}

	public void testUnderlinedLevel3() {
		String html = parseToHtml("This is an underlined H4\n^^^^^^^^^^^^^^^^^^^^^^^^");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h4[^>]*>This is an underlined H4</h4>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel4() {
		String html = parseToHtml("This is an underlined H5\n++++++++++++++++++++++++");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>This is an underlined H5</h5>")
				.matcher(html)
				.find());
	}

	//TODO: the "underline" line need to match exactly the length of the title.

	public void testPreBlockIndentedByFourSpaces() {
		String html = parseToHtml("    This is a pre block.");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<pre>This is a pre block.</pre>"));
	}

	public void testPreBlockIndentedByOneTab() {
		String html = parseToHtml("\tThis is a pre block.");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<pre>This is a pre block.</pre>"));
	}

	/*
	 * One level of indentation - 4 spaces or 1 tab - is removed from each line of the pre block.
	 */
	public void testPreBlockMultiLineIndentedByFourSpaces() {
		String html = parseToHtml("    aaa\n        bbb\n            ccc");
		TestUtil.println("HTML: " + html);
		String expectedHtml = "<pre>aaa\n    bbb\n        ccc</pre>";
		assertTrue(html.contains(expectedHtml));
	}

	public void testPreBlockMultiLineIndentedByOneTab() {
		String html = parseToHtml("\taaa\n\t\tbbb\n\t\t\tccc");
		TestUtil.println("HTML: " + html);
		String expectedHtml = "<pre>aaa\n\tbbb\n\t\tccc</pre>";
		assertTrue(html.contains(expectedHtml));
	}

	//TODO: no "continue after empty line" in pre block.

	/**
	 * Within a pre block, ampersands (&) and angle brackets (< and >) are automatically converted into HTML entities.
	 */
	public void testSpecialCharactersAreConvertedInCodeBlock() {
		String html = parseToHtml("    <div class=\"footer\">\n    &copy; 2004 Foo Bar\n    </div>");
		TestUtil.println("HTML: " + html);
		String expectedHtml = "<pre>&lt;div class=\"footer\"&gt;\n&amp;copy; 2004 Foo Bar\n&lt;/div&gt;</pre>";
		assertTrue(html.contains(expectedHtml));
	}

	/**
	 * Regular asciidoc syntax is not processed within code blocks.
	 */
	public void testNoProcessingInCodeBlock() {
		String html = parseToHtml("    === Header 3\n    Lorem *ipsum*");
		TestUtil.println("HTML: " + html);
		String expectedHtml = "<pre>=== Header 3\nLorem *ipsum*</pre>";
		assertTrue(html.contains(expectedHtml));
	}
}
