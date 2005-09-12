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

package org.eclipse.gmf.runtime.diagram.ui.internal.services.layout;

import org.eclipse.gmf.runtime.notation.Node;


/**
 * Interface that wraps the Node view in order to retrieve the size when the 
 * Node's extent (either width or height) have been autosized.
 * 
 * This interface can be used in the layout provider implementation class.
 * 
 * @author sshaw
 * @canBeSeenBy %level1
 */
public interface ILayoutNodeBase {

	/**
	 * getNode
	 * Accessor method to return the notation meta-model Node object.
	 * 
	 * @return Node
	 */
	public Node getNode();
}
