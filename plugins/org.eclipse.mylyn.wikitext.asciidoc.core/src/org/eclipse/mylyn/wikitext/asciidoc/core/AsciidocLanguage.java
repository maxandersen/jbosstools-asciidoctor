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

package org.eclipse.mylyn.wikitext.asciidoc.core;

import java.util.List;

import org.eclipse.mylyn.internal.wikitext.asciidoc.core.block.HeadingBlock;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.block.InlineHtmlBlock;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.block.ParagraphBlock;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.block.PreformattedBlock;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.block.UnderlinedHeadingBlock;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.phrase.BackslashEscapePhraseModifier;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.phrase.SimplePhraseModifier;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.token.PreserverHtmlEntityToken;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.util.ReadAheadDispatcher;
import org.eclipse.mylyn.wikitext.core.parser.DocumentBuilder.SpanType;
import org.eclipse.mylyn.wikitext.core.parser.markup.AbstractMarkupLanguage;
import org.eclipse.mylyn.wikitext.core.parser.markup.Block;
import org.eclipse.mylyn.wikitext.core.parser.markup.phrase.HtmlEndTagPhraseModifier;
import org.eclipse.mylyn.wikitext.core.parser.markup.phrase.HtmlStartTagPhraseModifier;
import org.eclipse.mylyn.wikitext.core.parser.markup.token.PatternLineBreakReplacementToken;

/**
 * A markup language implementing Asciidoc syntax. http://daringfireball.net/projects/asciidoc/syntax
 *
 * @author Stefan Seelmann
 * @since 1.8
 */
public class AsciidocLanguage extends AbstractMarkupLanguage {

	public AsciidocLanguage() {
		setName("Asciidoc"); //$NON-NLS-1$
	}

	@Override
	protected void addStandardTokens(PatternBasedSyntax tokenSyntax) {
		// HTML entities are preserved
		tokenSyntax.add(new PreserverHtmlEntityToken());
		// two or more spaces at end of line force a line break
		tokenSyntax.add(new PatternLineBreakReplacementToken("( {2,})$")); //$NON-NLS-1$
	}

	@Override
	protected void addStandardPhraseModifiers(PatternBasedSyntax phraseModifierSyntax) {
		// inline HTML
		phraseModifierSyntax.add(new HtmlEndTagPhraseModifier());
		phraseModifierSyntax.add(new HtmlStartTagPhraseModifier());
		// backslash escaped span elements
		phraseModifierSyntax.add(new BackslashEscapePhraseModifier("+")); //$NON-NLS-1$
		phraseModifierSyntax.add(new BackslashEscapePhraseModifier("*")); //$NON-NLS-1$
		phraseModifierSyntax.add(new BackslashEscapePhraseModifier("_")); //$NON-NLS-1$
		// emphasis span elements
		phraseModifierSyntax.add(new SimplePhraseModifier("+", SpanType.CODE)); //$NON-NLS-1$
		phraseModifierSyntax.add(new SimplePhraseModifier("*", SpanType.STRONG)); //$NON-NLS-1$
		phraseModifierSyntax.add(new SimplePhraseModifier("_", SpanType.EMPHASIS)); //$NON-NLS-1$
	}

	@Override
	protected void addStandardBlocks(List<Block> blocks, List<Block> paragraphBreakingBlocks) {
		blocks.add(new PreformattedBlock());
		blocks.add(new HeadingBlock());
		blocks.add(new InlineHtmlBlock());
	}

	@Override
	protected Block createParagraphBlock() {
		ParagraphBlock paragraphBlock = new ParagraphBlock();
		UnderlinedHeadingBlock headingBlock = new UnderlinedHeadingBlock();
		ReadAheadDispatcher readAheadBlock = new ReadAheadDispatcher(headingBlock, paragraphBlock);
		return readAheadBlock;
	}

}
