/******************************************************************************
 * Copyright (c) 2002, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.gmf.runtime.common.ui.internal;

import org.eclipse.gmf.runtime.common.core.command.CMValidator;
import org.eclipse.gmf.runtime.common.core.l10n.AbstractResourceManager;
import org.eclipse.gmf.runtime.common.ui.internal.l10n.ResourceManager;
import org.eclipse.gmf.runtime.common.ui.plugin.XToolsUIPlugin;
import org.eclipse.gmf.runtime.common.ui.util.UIModificationValidator;

/**
 * The common UI plug-in.
 * 
 * @author khussey
 *  
 */
public class CommonUIPlugin
	extends XToolsUIPlugin {

	/**
	 * This plug-in's shared instance.
	 */
	private static CommonUIPlugin plugin;

	/**
	 * Creates a new plug-in runtime object.
	 */
	public CommonUIPlugin() {
		super();

		plugin = this;
	}

	/**
	 * Retrieves this plug-in's shared instance.
	 * 
	 * @return This plug-in's shared instance.
	 */
	public static CommonUIPlugin getDefault() {
		return plugin;
	}

	/**
	 * Retrieves the unique identifier of this plug-in.
	 * 
	 * @return A non-empty string which is unique within the plug-in registry.
	 */
	public static String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}

	/**
	 * Retrieves the resource manager for this plug-in.
	 * 
	 * @return The resource manager for this plug-in.
	 * 
	 * @see org.eclipse.gmf.runtime.common.core.plugin.XToolsPlugin#getResourceManager()
	 */
	public AbstractResourceManager getResourceManager() {
		return ResourceManager.getInstance();
	}
	
	/**
	 * Override to initialize the modification validator. 
	 */
	protected void doStartup() {
		CMValidator.setValidator(new UIModificationValidator());
	}

}