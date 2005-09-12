/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.gmf.runtime.diagram.ui.printing.render.util;

import org.eclipse.draw2d.Graphics;

import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.printing.internal.util.DiagramPrinter;
import org.eclipse.gmf.runtime.draw2d.ui.internal.graphics.MapModeGraphics;
import org.eclipse.gmf.runtime.draw2d.ui.render.internal.graphics.RenderedMapModeGraphics;
import org.eclipse.gmf.runtime.draw2d.ui.render.internal.graphics.RenderedPrinterGraphics;

/**
 * A specialized <code>DiagramPrinter</code> that supports rendering of
 * images.
 * 
 * @author cmahoney
 */
public class RenderedDiagramPrinter
	extends DiagramPrinter {

	/**
	 * Creates a new instance.
	 * @param preferencesHint
	 */
	public RenderedDiagramPrinter(PreferencesHint preferencesHint) {
		super(preferencesHint);
	}

	/**
	 * Creates a mapmode graphics that supports rendering of images.
	 */
	public MapModeGraphics createMapModeGraphics(Graphics theGraphics) {
		return new RenderedMapModeGraphics(new RenderedPrinterGraphics(
			theGraphics, printer, true));
	}
}