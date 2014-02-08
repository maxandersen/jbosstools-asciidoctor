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

package org.eclipse.mylyn.internal.wikitext.asciidoc.core.block;

import java.util.regex.Pattern;

import org.eclipse.mylyn.internal.wikitext.asciidoc.core.util.LookAheadReader;
import org.eclipse.mylyn.internal.wikitext.asciidoc.core.util.ReadAheadBlock;
import org.eclipse.mylyn.wikitext.core.parser.Attributes;
import org.eclipse.mylyn.wikitext.core.parser.markup.Block;

/**
 * Asciidoc underlined headings.
 *
 * @author Stefan Seelmann
 */
public class UnderlinedHeadingBlock extends Block implements ReadAheadBlock {

	private static final Pattern h2pattern = Pattern.compile("-+\\s*"); //$NON-NLS-1$
	private static final Pattern h3pattern = Pattern.compile("~+\\s*"); //$NON-NLS-1$
	private static final Pattern h4pattern = Pattern.compile("\\^+\\s*"); //$NON-NLS-1$
	private static final Pattern h5pattern = Pattern.compile("\\++\\s*"); //$NON-NLS-1$

	private int blockLineCount;

	private int level;

	public boolean canStart(String line, int lineOffset, LookAheadReader lookAheadReader) {
		blockLineCount = 0;
		level = 0;
		String nextLine = lookAheadReader.lookAhead();
		if (nextLine == null) {
			return false;
		} else if (h2pattern.matcher(nextLine).matches()) {
			level = 2;
			return true;
		} else if (h3pattern.matcher(nextLine).matches()) {
			level = 3;
			return true;
		} else if (h4pattern.matcher(nextLine).matches()) {
			level = 4;
			return true;
		} else if (h5pattern.matcher(nextLine).matches()) {
			level = 5;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canStart(String line, int lineOffset) {
		String message = "Read-ahead required, call canStart(String, int, LookAheadReader) instead."; //$NON-NLS-1$
		throw new UnsupportedOperationException(message);
	}

	@Override
	public int processLineContent(String line, int offset) {
		if (blockLineCount == 0) {
			builder.beginHeading(level, new Attributes());
			builder.characters(line);
		} else {
			builder.endHeading();
			setClosed(true);
		}

		blockLineCount++;
		return -1;
	}
}
