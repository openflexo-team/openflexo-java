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
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.FlexoResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.technologyadapter.java.model.JavaSourceFolder;

/**
 * Represents the resource associated to a {@link OWLOntology}
 * 
 * @author sguerin
 * 
 */
public abstract class JavaSourceFolderResourceImpl extends FlexoResourceImpl<JavaSourceFolder> implements JavaSourceFolderResource {

	private static final Logger logger = Logger.getLogger(JavaSourceFolderResourceImpl.class.getPackage().getName());

	/**
	 * Load the &quot;real&quot; load resource data of this resource.
	 * 
	 * @param progress
	 *            a progress monitor in case the resource data is not immediately available.
	 * @return the resource data.
	 * @throws ResourceLoadingCancelledException
	 * @throws ResourceDependencyLoopException
	 * @throws FileNotFoundException
	 * @throws FlexoException
	 */
	@Override
	public JavaSourceFolder loadResourceData() throws ResourceLoadingCancelledException, FileNotFoundException, FlexoException {
		// JavaSourceFolder returned = new OWLOntology(getURI(), getIODelegate().getSerializationArtefactAsResource(), getOntologyLibrary(),
		// getTechnologyAdapter());
		// returned.setResource(this);
		// resourceData = returned;
		return null;
	}

	/**
	 * Save the &quot;real&quot; resource data of this resource.
	 * 
	 * @throws SaveResourceException
	 */
	@Override
	public void save() throws SaveResourceException {
		// TODO
	}

	@Override
	public Class<JavaSourceFolder> getResourceDataClass() {
		return JavaSourceFolder.class;
	}

}
