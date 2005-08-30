/*
 *+------------------------------------------------------------------------+
 *| Licensed Materials - Property of IBM                                   |
 *| (C) Copyright IBM Corp. 2002, 2003.  All Rights Reserved.              |
 *|                                                                        |
 *| US Government Users Restricted Rights - Use, duplication or disclosure |
 *| restricted by GSA ADP Schedule Contract with IBM Corp.                 |
 *+------------------------------------------------------------------------+
 */
package org.eclipse.gmf.runtime.notation.providers.internal.l10n;

import java.util.MissingResourceException;

import org.eclipse.core.runtime.Plugin;

import org.eclipse.gmf.runtime.common.core.l10n.AbstractResourceManager;
import org.eclipse.gmf.runtime.notation.providers.internal.util.NotationMSLPlugin;

/**
 * A singleton resource manager object that manages string, image, font and
 * cursor types of resources for this plug-in.
 * 
 * @author rafikj
 */
public final class ResourceManager
	extends AbstractResourceManager {

	/**
	 * Singleton instance for the resource manager.
	 */
	private static AbstractResourceManager resourceManager = new ResourceManager();

	/**
	 * Constructs a new resource manager.
	 */
	private ResourceManager() {
		super();
	}

	/**
	 * Retrieves the singleton instance of this resource manager.
	 * 
	 * @return The singleton resource manager.
	 */
	public static AbstractResourceManager getInstance() {
		return resourceManager;
	}

	/**
	 * Retrieves a localized string for the specified key.
	 * 
	 * @return A localized string value, or a key if the bundle does not contain
	 *         this entry.
	 * @param key
	 *            The resource bundle key.
	 */
	public static String getI18NString(String key) {
		return getInstance().getString(key);
	}

	/**
	 * Initializes this resource manager's resources.
	 * 
	 * @see org.eclipse.gmf.runtime.common.core.l10n.AbstractResourceManager#initializeResources()
	 */
	protected void initializeResources() {
		initializeMessageResources();
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.l10n.AbstractResourceManager#getString(java.lang.String,
	 *      java.lang.String)
	 */
	public String getString(String key, String defaultValue) {

		try {
			return getMessagesBundle().getString(key);
		} catch (MissingResourceException mre) {
			return defaultValue;
		}
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.l10n.AbstractResourceManager#getPlugin()
	 */
	protected Plugin getPlugin() {
		return NotationMSLPlugin.getDefault();
	}
}