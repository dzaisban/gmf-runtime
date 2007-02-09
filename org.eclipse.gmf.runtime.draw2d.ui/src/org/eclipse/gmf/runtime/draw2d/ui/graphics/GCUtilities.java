/******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/
package org.eclipse.gmf.runtime.draw2d.ui.graphics;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

/**
 * Utility class for operations around using a <code>GC</code>
 * 
 * @author sshaw, crevells
 */
public class GCUtilities {

    static private boolean checkedAdvancedGraphicsSupport = false;

    static private boolean platformSupportsAdvancedGraphics = false;


    /**
     * Returns true is advanced graphics is supported in this environment. This
     * includes the OS supporting an advanced graphics library (usually an
     * externally installed library such as Cairo or GDI+)
     * 
     * @return true if advanced graphics is supported; false otherwise
     */
    public static boolean supportsAdvancedGraphics() {
        if (!checkedAdvancedGraphicsSupport) {
            Shell shell = new Shell();
            GC gc = new GC(shell);
            gc.setAdvanced(true);
            platformSupportsAdvancedGraphics = (gc.getAdvanced() == true);
            checkedAdvancedGraphicsSupport = true;
            gc.dispose();
            shell.dispose();
        }

        // Advanced graphics is disabled if the RTL orintation is ON
        // this is to avoid SWT bugzilla 99564, on the GC object, where if the Advanced
        // graphics Mask is on it does not consider mirroring
        return platformSupportsAdvancedGraphics
            && Window.getDefaultOrientation() != SWT.RIGHT_TO_LEFT;
    }
}
