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

package org.eclipse.gmf.runtime.emf.core.internal.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gmf.runtime.emf.core.internal.util.MSLConstants;
import org.eclipse.gmf.runtime.emf.core.internal.util.MSLUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectUtil;

/**
 * This class changes the behavior of the default XMIHelper so that references
 * between projects are not deresolved.
 * 
 * @author rafikj
 */
public class MSLHelper
	extends LogicalHelper {

	/**
	 * Constructor.
	 */
	public MSLHelper(MSLResourceUnit resource) {
		super(resource);
	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.XMLHelper#deresolve(org.eclipse.emf.common.util.URI)
	 */
	public URI deresolve(URI uri) {

		// if this both target and container are within a platform resource and
		// projects
		// or plugins are different then do not deresolve.
		if (((MSLConstants.PLATFORM_SCHEME.equals(uri.scheme())) && (MSLConstants.PLATFORM_SCHEME
			.equals(resourceURI.scheme())))
			&& ((uri.segmentCount() > 2) && (resourceURI.segmentCount() > 2))
			&& ((!uri.segments()[0].equals(resourceURI.segments()[0])) || (!uri
				.segments()[1].equals(resourceURI.segments()[1]))))
			return uri;

		return super.deresolve(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl#getHREF(org.eclipse.emf.ecore.resource.Resource,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	protected URI getHREF(Resource otherResource, EObject obj) {
		URI result = super.getHREF(otherResource, obj);

		if (otherResource instanceof MSLResource) {
			String qName = EObjectUtil.getQName(obj, true);

			if (qName.length() > 0) {
				StringBuffer buffer = new StringBuffer(result.toString());

				buffer.append(MSLConstants.FRAGMENT_SEPARATOR);
				buffer.append(MSLUtil.encodeQName(qName));

				return URI.createURI(buffer.toString());
			}
		}

		return result;
	}
}