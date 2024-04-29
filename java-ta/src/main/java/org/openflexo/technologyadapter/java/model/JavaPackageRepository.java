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

package org.openflexo.technologyadapter.java.model;

import java.util.HashMap;
import java.util.Map;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterResourceRepository;
import org.openflexo.pamela.annotations.ImplementationClass;
import org.openflexo.pamela.annotations.ModelEntity;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.pamela.factory.PamelaModelFactory;
import org.openflexo.technologyadapter.java.JavaTechnologyAdapter;
import org.openflexo.technologyadapter.java.rm.JavaPackageResource;

@ModelEntity
@ImplementationClass(JavaPackageRepository.JavaSourceFolderRepositoryImpl.class)
public interface JavaPackageRepository<I>
		extends TechnologyAdapterResourceRepository<JavaPackageResource, JavaTechnologyAdapter, JavaPackage, I> {

	public JavaPackageResource getResourceForFolder(I folder);

	public static <I> JavaPackageRepository<I> instanciateNewRepository(JavaTechnologyAdapter technologyAdapter,
			FlexoResourceCenter<I> resourceCenter) {
		PamelaModelFactory factory;
		try {
			factory = new PamelaModelFactory(JavaPackageRepository.class);
			JavaPackageRepository<I> newRepository = factory.newInstance(JavaPackageRepository.class);
			newRepository.setTechnologyAdapter(technologyAdapter);
			newRepository.setResourceCenter(resourceCenter);
			newRepository.setBaseArtefact(resourceCenter.getBaseArtefact());
			newRepository.getRootFolder().setRepositoryContext("[SourceFolders]");
			return newRepository;
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static abstract class JavaSourceFolderRepositoryImpl<I>
			extends TechnologyAdapterResourceRepositoryImpl<JavaPackageResource, JavaTechnologyAdapter, JavaPackage, I>
			implements JavaPackageRepository<I> {

		private Map<I, JavaPackageResource> resourcesStoredByFolder = new HashMap<>();

		@Override
		public void registerResource(JavaPackageResource resource, RepositoryFolder<JavaPackageResource, I> parentFolder) {
			super.registerResource(resource, parentFolder);
			System.out.println("Coucou " + resource + " dans " + parentFolder.getSerializationArtefact());
			resourcesStoredByFolder.put((I) resource.getIODelegate().getSerializationArtefact(), resource);
			JavaPackageResource parent = getResourceForFolder(parentFolder.getSerializationArtefact());
			if (parent != null) {
				parent.addToContents(resource);
			}
		}

		@Override
		public JavaPackageResource getResourceForFolder(I folder) {
			return resourcesStoredByFolder.get(folder);
		}

	}

}
