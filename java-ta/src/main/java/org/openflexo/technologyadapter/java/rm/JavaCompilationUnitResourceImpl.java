/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Owlconnector, a component of the software infrastructure 
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

package org.openflexo.technologyadapter.java.rm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.PamelaResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.technologyadapter.java.model.JavaCompilationUnit;
import org.openflexo.technologyadapter.java.model.JavaModelFactory;
import org.openflexo.technologyadapter.java.model.JavaTechnologyContextManager;

import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;

/**
 * Represents the resource associated to a {@link OWLOntology}
 * 
 * @author sguerin
 * 
 */
public abstract class JavaCompilationUnitResourceImpl extends PamelaResourceImpl<JavaCompilationUnit, JavaModelFactory>
		implements JavaCompilationUnitResource {

	private static final Logger logger = Logger.getLogger(JavaCompilationUnitResourceImpl.class.getPackage().getName());

	@Override
	public JavaTechnologyContextManager getTechnologyContextManager() {
		return (JavaTechnologyContextManager) performSuperGetter(TECHNOLOGY_CONTEXT_MANAGER);
	}

	@Override
	public String getFullQualifiedClassName() {
		String javaName = getName();
		if (javaName.contains(JavaCompilationUnitResourceFactory.JAVA_FILE_EXTENSION)) {
			javaName = javaName.substring(0, javaName.length() - JavaCompilationUnitResourceFactory.JAVA_FILE_EXTENSION.length());
		}
		if (getContainer() instanceof JavaPackageResource) {
			return ((JavaPackageResource) getContainer()).getFullQualifiedPackageName() + "." + javaName;
		}
		return javaName;
	}

	@Override
	protected JavaCompilationUnit performLoad() throws IOException, Exception {

		if (getContainer() != null) {
			getContainer().getResourceData();
		}
		return getFactory().makeJavaCompilationUnit(getCtType().getPosition().getCompilationUnit());
	}

	private CtType<?> getCtType() {
		CtModel model = getTechnologyContextManager().getModelAnalysis();
		for (CtType<?> ctType : model.getAllTypes()) {
			if (ctType.getQualifiedName().equals(getFullQualifiedClassName())) {
				return ctType;
			}
		}
		return null;
	}

	@Override
	protected void performSave(boolean clearIsModified) throws SaveResourceException {
		// TODO Auto-generated method stub
		logger.warning("Not implemented: performSave() for JavaCompilationUnitResource");
	}

	@Override
	public Class<JavaCompilationUnit> getResourceDataClass() {
		return JavaCompilationUnit.class;
	}

	/**
	 * Return virtual model stored by this resource<br>
	 * Load the resource data when unloaded
	 */
	@Override
	public JavaCompilationUnit getCompilationUnit() {
		try {
			return getResourceData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ResourceLoadingCancelledException e) {
			e.printStackTrace();
		} catch (FlexoException e) {
			e.printStackTrace();
		}
		return null;
	}

}
