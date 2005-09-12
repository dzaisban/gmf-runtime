/******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.gmf.examples.runtime.diagram.logic.internal.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.HandleBounds;

import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapMode;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;

/**
 * code copied from real logic example in gef
 */
/*
 * @canBeSeenBy org.eclipse.gmf.examples.runtime.diagram.logic.*
 */
public class LogicFlowFigure
	extends NodeFigure
	implements HandleBounds
{
	public LogicFlowFigure() {
		setBorder(new LogicFlowBorder());
		setOpaque(true);
	}
 
	/**
	 * @see org.eclipse.gef.handles.HandleBounds#getHandleBounds()
	 */
	public Rectangle getHandleBounds() {
		return getBounds().getCropped(new Insets(2,0,2,0));
	}

	public Dimension getPreferredSize(int w, int h) {
		Dimension newPrefSize = super.getPreferredSize(MapMode.DPtoLP(w), MapMode.DPtoLP(h));
		Dimension defaultSize = new Dimension(MapMode.DPtoLP(100),MapMode.DPtoLP(100));
		newPrefSize.union(defaultSize);
		return newPrefSize;
	}

	/**
	 * @see org.eclipse.draw2d.Figure#paintFigure(Graphics)
	 */
	protected void paintFigure(Graphics graphics) {
		Rectangle rect = getBounds().getCopy();
		rect.crop(new Insets(2,0,2,0));
	}

	public String toString() {
		return "LogicFlowBoardFigure"; //$NON-NLS-1$
	}

	public void validate() {
		if(isValid()) return;
		super.validate();
	}
}
