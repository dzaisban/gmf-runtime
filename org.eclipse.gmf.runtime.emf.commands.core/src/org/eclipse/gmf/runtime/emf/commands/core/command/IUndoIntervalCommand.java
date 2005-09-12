/******************************************************************************
 * Copyright (c) 2002, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.gmf.runtime.emf.commands.core.command;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.core.edit.MUndoInterval;

/**
 * A command which has a corresponding undo interval.
 * @author Michael Yee
 */
public interface IUndoIntervalCommand extends ICommand {

	/**
	 * Retrieves the command's undo interval.
	 * @return the command's undo interval.
	 */
	public MUndoInterval getUndoInterval();
}
