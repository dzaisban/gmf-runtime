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

package org.eclipse.gmf.runtime.diagram.ui.services.layout;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.util.Assert;

import org.eclipse.gmf.runtime.common.core.service.IProvider;


/**
 * @author sshaw
 *
 * The LayoutNodesOperation is an operation used to layout all of the members of a map
 * of Nodes. 
 */
public class LayoutNodesOperation implements ILayoutNodesOperation {
	
	private List layoutNodes = null;
	private boolean offsetFromBoundingBox;
	private final IAdaptable layoutHint;
    
    /**
     *  Constructs a new layout operation with the specified parameters
     * @param layoutNodes   nodes to layout
     * @param offsetFromBoundingBox  the offset
     * @param layoutHint the layout hint
     */
    public LayoutNodesOperation(
        List layoutNodes, boolean offsetFromBoundingBox,
		IAdaptable layoutHint) {            
            super();
            Assert.isNotNull(layoutNodes);	            
            this.layoutNodes = layoutNodes; 
            this.offsetFromBoundingBox = offsetFromBoundingBox;
            this.layoutHint = layoutHint;
    }
    
	/* (non-Javadoc)
	 * @see com.ibm.xtools.presentation.services.layout.ILayoutNodesOperation#getNodesToSizesMap()
	 */
	public List getLayoutNodes() {
		return layoutNodes;
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.xtools.presentation.services.layout.ILayoutNodesOperation#getXOffset()
	 */
	public boolean shouldOffsetFromBoundingBox() {
		return offsetFromBoundingBox;
	}
	
    /**
     * Retrieves the value of the <code>layoutHint</code> instance variable.
     * 
     * @return IAdaptable layoutHint
     */
    public final IAdaptable getLayoutHint() {
        return this.layoutHint;
    } 
	
    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.common.core.service.IOperation#execute(org.eclipse.gmf.runtime.common.core.service.IProvider)
     */
    public Object execute(IProvider provider) {
        Assert.isNotNull(provider);

        return ((ILayoutNodesProvider) provider).layoutNodes(
        	getLayoutNodes(), shouldOffsetFromBoundingBox(), getLayoutHint());
    }
}
