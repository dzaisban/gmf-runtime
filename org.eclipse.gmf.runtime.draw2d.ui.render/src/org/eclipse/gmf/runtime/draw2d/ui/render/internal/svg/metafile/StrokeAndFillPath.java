/******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.gmf.runtime.draw2d.ui.render.internal.svg.metafile;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;

/**
 * @author dhabib
 * @canBeSeenBy org.eclipse.gmf.runtime.draw2d.ui.render.*
 */
public class StrokeAndFillPath implements IEmf2SvgConverter
{
	public void readEMFRecord( Record rec ) throws IOException
	{
	    // No data associated with this record.
	}

	public void render( Graphics2D g, DeviceContext context ) throws TranscoderException
	{
		context.getGdiPath().closeAll();
		
		GeneralPath	gp 			= context.getGdiPath().getPath();
		GdiBrush 	curBrush 	= context.getCurBrush();
		
		if( curBrush != null )
		{
			curBrush.fill( gp, g, context );
		}
		
		GdiPen pen = context.getCurPen();
		
		if( pen != null )
		{	
			pen.apply( g, context );
			g.draw( gp );
		}
	}
}
