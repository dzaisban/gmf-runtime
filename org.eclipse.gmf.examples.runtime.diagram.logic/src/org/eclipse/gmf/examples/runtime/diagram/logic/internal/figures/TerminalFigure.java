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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gmf.runtime.diagram.ui.figures.GateFigure;
import org.eclipse.gmf.runtime.diagram.ui.util.DrawConstant;

/**
 * @author qili
 *
 * To manage fixed connection anchors
 */
public class TerminalFigure extends GateFigure{
	
	protected FixedConnectionAnchor fixedAnchor;
	
	/**
	 * @author sshaw
	 *
	 * Override for GateLocator that will fix the location for the connection point based on 
	 * an initial position.  This locator will also scale the location of the connection if the
	 * parent figure changes.
	 */
	static public class FixedGateLocation extends GateLocator {
		 
		private Dimension initDim; 
		public void relocate(IFigure target) {
			Rectangle parentRect = getParentBorder();
			float xRatio = parentRect.width / (float)initDim.width;
			float yRatio = parentRect.height / (float)initDim.height;
			
			Rectangle targetRect = target.getBounds();
			Point ptLoc = this.getAbsoluteToBorder(getConstraintLocation());
			ptLoc = ptLoc.getTranslated(-parentRect.x, -parentRect.y);
			ptLoc.scale(xRatio, yRatio);
			ptLoc = ptLoc.getTranslated(parentRect.x, parentRect.y);
			
			target.setBounds(new Rectangle(ptLoc.x - targetRect.width / 2, ptLoc.y - targetRect.height / 2, targetRect.width, targetRect.height));
		}
		
		/**
		 * @param gate
		 * @param parentFigure
		 */
		public FixedGateLocation(GateFigure gate, IFigure parentFigure, Dimension initDim) {
			super(gate, parentFigure);
			this.initDim = initDim;
		}
	}
	
	/**
	 * @param preferredSide
	 */
	public TerminalFigure(DrawConstant preferredSide) {
		super(preferredSide);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure#getSourceConnectionAnchorAt(org.eclipse.draw2d.geometry.Point)
	 */
	public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
		if (p == null) {
			return getConnectionAnchor(szAnchor);
		}
		return fixedAnchor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure#getTargetConnectionAnchorAt(org.eclipse.draw2d.geometry.Point)
	 */
	public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
		if (p == null) {
			return getConnectionAnchor(szAnchor);
		}
		return fixedAnchor;
	}
}
