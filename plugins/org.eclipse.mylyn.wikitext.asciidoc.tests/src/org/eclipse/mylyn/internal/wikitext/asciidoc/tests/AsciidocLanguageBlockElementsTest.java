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

	public void testEqStyleHeaderNotH6() {
		String html = parseToHtml("====== This is not h6");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>====== This is not h6</p>"));
	}

	public void testEqStyleHeaderNoTitleWith7eq() {
		String html = parseToHtml("======== This is not a title (7)");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>======== This is not a title (7)</p>"));
	}

	public void testEqStyleHeaderNoTitleWith10eq() {
		String html = parseToHtml("=========== This is not a title (10)");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>=========== This is not a title (10)</p>"));
	}

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

	public void testClosedEqStyleHeaderLevel4WithSpaces() {
		String html = parseToHtml("===== This is H5 with spaces    =====");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>This is H5 with spaces</h5>")
				.matcher(html)
				.find());
	}

	public void testClosedEqStyleHeaderNotH6() {
		String html = parseToHtml("====== This is also not h6 ======");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>====== This is also not h6 ======</p>"));
	}

	public void testClosedEqStyleHeaderNoTitleWith7eq() {
		String html = parseToHtml("======= This is also not a title (7) =======");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>======= This is also not a title (7) =======</p>"));
	}

	public void testClosedEqStyleHeaderNoTitleWith12eq() {
		String html = parseToHtml("============ This is also not a title (12) ============");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<p>============ This is also not a title (12) ============</p>"));
	}

	public void testClosedEqStyleHeaderWithMoreClosingEq() {
		String html = parseToHtml("== This is an H2 again ==================");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>This is an H2 again ==================</h2>")
				.matcher(html)
				.find());
	}

	public void testClosedEqStyleHeaderWithMoreClosingEqAndSpaces() {
		String html = parseToHtml("== This is an H2 with spaces     ====");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>This is an H2 with spaces     ====</h2>")
				.matcher(html)
				.find());
	}

	public void testClosedAtxStyleHeaderWithLessCosingEq() {
		String html = parseToHtml("===== This is an H5 again ==");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>This is an H5 again ==</h5>")
				.matcher(html)
				.find());
	}

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

	public void testUnderlinedLevel1LineMinusOneChar() {
		String html = parseToHtml("Lorem Ipsum\n----------"); //title 11 chars, line 10 chars
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>Lorem Ipsum</h2>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel2LineMinusOneChar() {
		String html = parseToHtml("Lorem Ipsum Dolor\n~~~~~~~~~~~~~~~~"); //title 17 chars, line 16 chars
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h3[^>]*>Lorem Ipsum Dolor</h3>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel3LineMinusOneChar() {
		String html = parseToHtml("LoremIpsumDolor\n^^^^^^^^^^^^^^"); //title 15 chars, line 14 chars
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h4[^>]*>LoremIpsumDolor</h4>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel4LineMinusOneChar() {
		String html = parseToHtml("Lorem-Ipsum\n++++++++++"); //title 11 chars, line 10 chars
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>Lorem-Ipsum</h5>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel1LinePlusOneChar() {
		String html = parseToHtml("Lorem Ipsum\n------------"); //title 11 chars, line 12 chars
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>Lorem Ipsum</h2>")
				.matcher(html)
				.find());
	}

	public void testNotUnderlinedLevel1LineMinusTwoChars() {
		String html = parseToHtml("Lorem Ipsum\n---------"); //title 11 chars, line 9 chars
		TestUtil.println("HTML: " + html);
		assertFalse(Pattern.compile(
				"<h2[^>]*>Lorem Ipsum</h2>")
				.matcher(html)
				.find());
	}

	public void testNotUnderlinedLevel2LineMinusTwoChars() {
		String html = parseToHtml("Lorem Ipsum Dolor\n~~~~~~~~~~~~~~~"); //title 17 chars, line 15 chars
		TestUtil.println("HTML: " + html);
		assertFalse(Pattern.compile(
				"<h3[^>]*>Lorem Ipsum Dolor</h3>")
				.matcher(html)
				.find());
	}

	public void testNotUnderlinedLevel3LineMinusTwoChars() {
		String html = parseToHtml("LoremIpsumDolor\n^^^^^^^^^^^^^"); //title 15 chars, line 13 chars
		TestUtil.println("HTML: " + html);
		assertFalse(Pattern.compile(
				"<h4[^>]*>LoremIpsumDolor</h4>")
				.matcher(html)
				.find());
	}

	public void testNotUnderlinedLevel4LineMinusTwoChars() {
		String html = parseToHtml("Lorem-Ipsum\n+++++++++"); //title 11 chars, line 9 chars
		TestUtil.println("HTML: " + html);
		assertFalse(Pattern.compile(
				"<h5[^>]*>Lorem-Ipsum</h5>")
				.matcher(html)
				.find());
	}

	public void testNotUnderlinedLevel1LinePlusTwoChars() {
		String html = parseToHtml("Lorem Ipsum\n-------------"); //title 11 chars, line 13 chars
		TestUtil.println("HTML: " + html);
		assertFalse(Pattern.compile(
				"<h2[^>]*>Lorem Ipsum</h2>")
				.matcher(html)
				.find());
	}

	public void testNotUnderlinedLevel2LinePlusTwoChars() {
		String html = parseToHtml("Lorem Ipsum Dolor\n~~~~~~~~~~~~~~~~~~~"); //title 17 chars, line 18 chars
		TestUtil.println("HTML: " + html);
		assertFalse(Pattern.compile(
				"<h3[^>]*>Lorem Ipsum Dolor</h3>")
				.matcher(html)
				.find());
	}

	public void testNotUnderlinedLevel3LinePlusTwoChars() {
		String html = parseToHtml("LoremIpsumDolor\n^^^^^^^^^^^^^^^^^"); //title 15 chars, line 16 chars
		TestUtil.println("HTML: " + html);
		assertFalse(Pattern.compile(
				"<h4[^>]*>LoremIpsumDolor</h4>")
				.matcher(html)
				.find());
	}

	public void testNotUnderlinedLevel4LinePlusTwoChars() {
		String html = parseToHtml("Lorem-Ipsum\n+++++++++++++"); //title 11 chars, line 12 chars
		TestUtil.println("HTML: " + html);
		assertFalse(Pattern.compile(
				"<h5[^>]*>Lorem-Ipsum</h5>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel1TitleTrailingSpaces() {
		String html = parseToHtml("Title test underlined H2     \n------------------------");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>Title test underlined H2</h2>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel2TitleTrailingSpaces() {
		String html = parseToHtml("Title test underlined H3\t\n~~~~~~~~~~~~~~~~~~~~~~~~");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h3[^>]*>Title test underlined H3</h3>")
				.matcher(html)
				.find());
		}

	public void testUnderlinedLevel3TitleTrailingSpaces() {
		String html = parseToHtml("Title test underlined H4   \t\n^^^^^^^^^^^^^^^^^^^^^^^^");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h4[^>]*>Title test underlined H4</h4>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel4TitleTrailingSpaces() {
		String html = parseToHtml("Title test underlined H5\t  \n++++++++++++++++++++++++");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>Title test underlined H5</h5>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel1LineWithTrailingSpaces() {
		String html = parseToHtml("Title test underlined H2\n------------------------     ");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h2[^>]*>Title test underlined H2</h2>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel2LineWithTrailingSpaces() {
		String html = parseToHtml("Title test underlined H3\n~~~~~~~~~~~~~~~~~~~~~~~~\t");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h3[^>]*>Title test underlined H3</h3>")
				.matcher(html)
				.find());
		}

	public void testUnderlinedLevel3LineWithTrailingSpaces() {
		String html = parseToHtml("Title test underlined H4\n^^^^^^^^^^^^^^^^^^^^^^^^\t\t");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h4[^>]*>Title test underlined H4</h4>")
				.matcher(html)
				.find());
	}

	public void testUnderlinedLevel4LineWithTrailingSpaces() {
		String html = parseToHtml("Title test underlined H5\n++++++++++++++++++++++++\t  ");
		TestUtil.println("HTML: " + html);
		assertTrue(Pattern.compile(
				"<h5[^>]*>Title test underlined H5</h5>")
				.matcher(html)
				.find());
	}

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

	public void testPreBlockMultiLineIndentedByFourSpacesNoContinueAfterEmptyLine() {
		String html = parseToHtml("    aaa\n    bbb\n    ccc\n        \n    after empty line");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<pre>aaa\nbbb\nccc</pre>"));
		assertTrue(html.contains("<pre>after empty line</pre>"));
	}

	public void testPreBlockMultiLineIndentedByFourSpacesNoContinueAfterTabLine() {
		String html = parseToHtml("    aaa\n    bbb\n    ccc\n    \t\t\n    after empty line");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<pre>aaa\nbbb\nccc</pre>"));
		assertTrue(html.contains("<pre>after empty line</pre>"));
	}

	public void testPreBlockMultiLineIndentedByOneTabNoContinueAfterEmptyLine() {
		String html = parseToHtml("\taaa\n\tbbb\n\tccc\n\t\n\tafter empty line");
		TestUtil.println("HTML: " + html);
		assertTrue(html.contains("<pre>aaa\nbbb\nccc</pre>"));
		assertTrue(html.contains("<pre>after empty line</pre>"));
	}

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
