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

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.pamela.annotations.Getter;
import org.openflexo.pamela.annotations.ImplementationClass;
import org.openflexo.pamela.annotations.ModelEntity;
import org.openflexo.pamela.annotations.PropertyIdentifier;
import org.openflexo.pamela.annotations.Setter;
import org.openflexo.pamela.annotations.XMLElement;
import org.openflexo.technologyadapter.java.rm.JavaCompilationUnitResource;

import spoon.reflect.declaration.CtCompilationUnit;

/**
 * Represents a Java compilation unit
 * 
 * @author sylvain
 * 
 */
@ModelEntity
@ImplementationClass(value = JavaCompilationUnit.JavaCompilationUnitImpl.class)
@XMLElement
public interface JavaCompilationUnit extends JavaSourceObject, ResourceData<JavaCompilationUnit> {

	@PropertyIdentifier(type = CtCompilationUnit.class)
	public static final String COMPILATION_UNIT_KEY = "compilationUnit";

	@Override
	public JavaCompilationUnitResource getResource();

	/**
	 * Return {@link CtCompilationUnit} wrapped by this {@link JavaSourceElement}
	 * 
	 * @return
	 */
	@Getter(value = COMPILATION_UNIT_KEY, ignoreType = true)
	public CtCompilationUnit getCompilationUnit();

	/**
	 * Sets {@link CtCompilationUnit} wrapped by this {@link JavaSourceElement}
	 * 
	 * @param workbook
	 */
	@Setter(COMPILATION_UNIT_KEY)
	public void setCompilationUnit(CtCompilationUnit cu);

	/**
	 * Default base implementation for {@link JavaCompilationUnit}
	 * 
	 * @author sylvain
	 *
	 */
	public static abstract class JavaCompilationUnitImpl extends JavaSourceObjectImpl implements JavaCompilationUnit {

		@SuppressWarnings("unused")
		private static final Logger logger = Logger.getLogger(JavaCompilationUnitImpl.class.getPackage().getName());

	}
}
