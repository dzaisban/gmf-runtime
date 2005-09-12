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

 package org.eclipse.gmf.runtime.emf.core.internal.util;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

 /**
  * A type that requires a semantic hint for view creation. 
  * 
  * @author cmahoney
  */
 public interface IHintedType
 	extends IElementType {

 	/**
 	 * Gets the semantic hint required for view creation.
 	 * 
 	 * @return the semantic hint.
 	 */
 	String getSemanticHint();
 }
