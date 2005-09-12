/******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.gmf.examples.runtime.diagram.geoshapes.internal.util;

import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.util.IDEEditorFileCreator;


/**
 * @author qili
 *
 * Class that generates diagram files.
 */
public class DiagramFileCreator extends IDEEditorFileCreator{
	
	private static DiagramFileCreator INSTANCE = new DiagramFileCreator();

	/**
	 * Method getInstance.
	 * This class is a singleton that can only be accessed through this static method.
	 * @return VizDiagramFileCreator The singleton instance
	 */
	static public DiagramFileCreator getInstance() {
		return INSTANCE;
	}
	
	/**
	 * @see com.ibm.xtools.uml.ui.diagram.internal.util.AbstractUMLDiagramFileCreator#getExtension()
	 */
	public String getExtension() {
		return ".geo"; //$NON-NLS-1$
	}

}
