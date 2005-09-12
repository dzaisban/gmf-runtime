/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.gmf.runtime.emf.clipboard.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.gmf.runtime.emf.clipboard.core.internal.ClipboardPlugin;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.ClipboardSupportManager;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.DefaultClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.PasteOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.SerializationEMFResource;


/**
 * Utility for the creation of
 * {@link org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport} instances for
 * specific EMF metamodels, and for copy/paste operations to and from a String
 * form for use on the system clipboard.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ClipboardUtil {
	/**
	 * Hint to perform "weak" merges when resolving paste collisions by merging.
	 * Weak merges will merge the content of multiplicity-many features but
	 * will not replace existing values in scalar features.
	 * <p>
	 * This hint is specified on a per-object basis.
	 * </p>
	 * 
	 * @see #MERGE_HINT_STRONG
	 */
	public final static String MERGE_HINT_WEAK = "*merge=weak"; //$NON-NLS-1$

	/**
	 * Hint to perform "strong" merges when resolving paste collisions by merging.
	 * Strong merges will merge the content of multiplicity-many features and
	 * will also replace existing values in scalar features.  This is the
	 * default merge behaviour.
	 * <p>
	 * This hint is specified on a per-object basis.
	 * </p>
	 * 
	 * @see #MERGE_HINT_WEAK
	 */
	public final static String MERGE_HINT_STRONG = "*merge=strong"; //$NON-NLS-1$

	/**
	 * Hint to retain the original element's ID when pasting it.  Normally used
	 * only when implementing a "move" operation, rather than a "copy".
	 * <p>
	 * This hint is specified on a per-object basis.
	 * </p>
	 */
	public final static String RECYCLE_HINT_ID = "*recycle=id"; //$NON-NLS-1$

	/**
	 * Hint to ignore the recycle hint when pasting objects that were copied
	 * with the recycle hint.  This essentially forces IDs to be regenerated
	 * even when the copier asked for them to be reused.  This would be
	 * appropriate, for example, on the second and subsequent paste of elements
	 * that had been cut and already pasted once.
	 * <p>
	 * This hint applies to all objects:  use it as a key in the hint map with
	 * a boolean value to indicate whether it is enabled.
	 * </p>
	 */
	public final static String IGNORE_RECYCLE_HINT_ID = "*ignore_recycle=id"; //$NON-NLS-1$

	/**
	 * Hint to attempt to paste into the parent of target element if target
	 * element doesn't accept the copied element.
	 * <p>
	 * This hint is specified on a per-object basis.
	 * </p>
	 */
	public final static String PASTE_TO_TARGET_PARENT = "*paste=parent"; //$NON-NLS-1$

	/**
	 * Hint indicating that the elements to be pasted were harvested, rather
	 * than just copied.
	 * <p>
	 * This hint is specified on a per-object basis.
	 * </p>
	 */
	public final static String HARVESTED_ELEMENT = "*paste=harvest"; //$NON-NLS-1$


	private static final String PASTE_SELECTION_FROM_STRING = "pasteElementsFromString"; //$NON-NLS-1$

	private static final String COPY_SELECTION_TO_STRING = "copyElementsToString"; //$NON-NLS-1$

	
	/**
	 * Not instantiable by clients.
	 */
	private ClipboardUtil() {
		super();
	}
	
	/**
	 * Serializes elements to a string suitable for putting on the system
	 * clipboard.
	 * 
	 * @param eObjects a collection of {@link EObject}s to be serialized
	 * @param hints a mapping of hints (defined as constants on this class), or
	 *     <code>null</code> to provide no hints
	 * @param monitor a progress monitor to track progress, or
	 *     <code>null</code> if no progress feedback is required
	 * 
	 * @return the serial form of the <code>eObjects</code>
	 */
	public static String copyElementsToString(Collection eObjects, Map hints,
			IProgressMonitor monitor) {
		try {
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}
			
			monitor.beginTask(CopyOperation.COPY,
				CopyOperation.TOTAL_WORK);
			eObjects = ClipboardSupportUtil.getCopyElements(eObjects);
			if (eObjects.isEmpty() == false) {
				if (hints == null) {
					hints = Collections.EMPTY_MAP;
				}
				CopyOperation copyOperation = new CopyOperation(
					monitor,
					createClipboardSupport(((EObject) eObjects.toArray()[0]).eClass()),
					eObjects, hints);
				return copyOperation.copy();
			}
		} catch (Exception ex) {
			handleException(ex, COPY_SELECTION_TO_STRING);
		} finally {
			monitor.done();
		}
		return null;
	}

	/**
	 * Deerializes elements from a string (obtained from the system clipboard)
	 * and pastes them into the specified target element.
	 * 
	 * @param string the string containing the elements to be pasted
	 * @param targetElement the element into which the new elements are to be
	 *     pasted
	 * @param hints a mapping of hints (defined as constants on this class), or
	 *     <code>null</code> to provide no hints
	 * @param monitor a progress monitor to track progress, or
	 *     <code>null</code> if no progress feedback is required
	 * 
	 * @return the newly pasted {@link EObject}s
	 */
	public static Collection pasteElementsFromString(String string,
			EObject targetElement, final Map hints, IProgressMonitor monitor) {
		Set result = null;
		
		try {
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}
			
			monitor.beginTask(BasePasteOperation.PASTE,
				PasteOperation.TOTAL_WORK);
			IClipboardSupport helper = createClipboardSupport(targetElement.eClass());
			if (string.length() == 0) {
				return Collections.EMPTY_SET;
			}
			PasteOperation pasteProcess = new PasteOperation(
				monitor, helper, string, targetElement,
				SerializationEMFResource.LOAD_OPTIONS, hints);
			pasteProcess.paste();
			result = pasteProcess.getPastedElementSet();
			helper.performPostPasteProcessing(result);
		} catch (Exception ex) {
			handleException(ex, PASTE_SELECTION_FROM_STRING);
		} finally {
			monitor.done();
		}
		
		return result;
	}
	
	/**
	 * handles the exception, does tracing ...etc.
	 * 
	 * @param ex
	 *            the exception to hanlde
	 * @param methodname
	 *            the calling method
	 */
	private static void handleException(Exception ex, String methodname) {
		if (ex instanceof OperationCanceledException) {
			ClipboardPlugin.catching(ClipboardUtil.class,
				methodname, ex);
		} else {
			ClipboardPlugin.throwing(ClipboardUtil.class,
				methodname, ex);
			throw (ex instanceof RuntimeException) ? (RuntimeException) ex
				: new RuntimeException("Copy-Paste General Error", ex);//$NON-NLS-1$
		}
	}
	
	/**
	 * Obtains the clipboard copy/paste support utility, if any, for the
	 * specified <code>eClass</code>'s metamodel.  If the metamodel does not
	 * have a dedicated clipboard support implementation, then a default
	 * implementation is provided that implements semantics similar to the
	 * {@link org.eclipse.emf.ecore.util.EcoreUtil.Copier} class.
	 * 
	 * @param eClass a metaclass
	 * @return the <code>eClass</code>'s metamodel's clipboard support
	 *      utility, or a null implementation if none is registered for it
	 *      (not actually <code>null</code>)
	 */
	public static IClipboardSupport createClipboardSupport(EClass eClass) {
		IClipboardSupport result = DefaultClipboardSupport.getInstance();
		EPackage ePackage = eClass.getEPackage();
		IClipboardSupportFactory factory = ClipboardSupportManager.lookup(ePackage);
		
		if (factory != null) {
			result = factory.newClipboardSupport(ePackage);
		}
		
		return result;
	}

}
