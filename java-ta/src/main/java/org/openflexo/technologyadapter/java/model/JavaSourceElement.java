/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Excelconnector, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.technologyadapter.java.model;

import java.util.logging.Logger;

import org.openflexo.foundation.InnerResourceData;
import org.openflexo.pamela.annotations.Getter;
import org.openflexo.pamela.annotations.ModelEntity;
import org.openflexo.pamela.annotations.PropertyIdentifier;
import org.openflexo.pamela.annotations.Setter;
import org.openflexo.technologyadapter.java.JavaTechnologyAdapter;

import spoon.reflect.declaration.CtElement;

/**
 * An element contained in a {@link JavaCompilationUnit}
 * 
 * Wraps a {@link CtElement}
 * 
 * @author sylvain
 *
 */
@ModelEntity(isAbstract = true)
public interface JavaSourceElement<E extends CtElement> extends JavaSourceObject, InnerResourceData<JavaCompilationUnit> {

	@PropertyIdentifier(type = CtElement.class)
	public static final String ELEMENT_KEY = "element";

	/**
	 * Return {@link CtElement} wrapped by this {@link JavaSourceElement}
	 * 
	 * @return
	 */
	@Getter(value = ELEMENT_KEY, ignoreType = true)
	public E getElement();

	/**
	 * Sets {@link CtElement} wrapped by this {@link JavaSourceElement}
	 * 
	 * @param workbook
	 */
	@Setter(ELEMENT_KEY)
	public void setElement(E element);

	/**
	 * Default base implementation for {@link JavaSourceElement}
	 * 
	 * @author sylvain
	 *
	 */
	public static abstract class JavaSourceElementImpl<E extends CtElement> extends FlexoObjectImpl implements JavaSourceElement<E> {

		@SuppressWarnings("unused")
		private static final Logger logger = Logger.getLogger(JavaSourceElementImpl.class.getPackage().getName());

		@Override
		public JavaTechnologyAdapter getTechnologyAdapter() {
			if (getResourceData() != null && getResourceData().getResource() != null) {
				return getResourceData().getResource().getTechnologyAdapter();
			}
			return null;
		}

	}
}
