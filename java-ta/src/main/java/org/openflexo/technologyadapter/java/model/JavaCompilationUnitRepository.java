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

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterResourceRepository;
import org.openflexo.pamela.annotations.ImplementationClass;
import org.openflexo.pamela.annotations.ModelEntity;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.pamela.factory.PamelaModelFactory;
import org.openflexo.technologyadapter.java.JavaTechnologyAdapter;
import org.openflexo.technologyadapter.java.rm.JavaCompilationUnitResource;
import org.openflexo.technologyadapter.java.rm.JavaSourceFolderResource;

@ModelEntity
@ImplementationClass(JavaCompilationUnitRepository.JavaCompilationUnitRepositoryImpl.class)
public interface JavaCompilationUnitRepository<I>
		extends TechnologyAdapterResourceRepository<JavaCompilationUnitResource, JavaTechnologyAdapter, JavaCompilationUnit, I> {

	public static <I> JavaCompilationUnitRepository<I> instanciateNewRepository(JavaTechnologyAdapter technologyAdapter,
			FlexoResourceCenter<I> resourceCenter) {
		PamelaModelFactory factory;
		try {
			factory = new PamelaModelFactory(JavaCompilationUnitRepository.class);
			JavaCompilationUnitRepository<I> newRepository = factory.newInstance(JavaCompilationUnitRepository.class);
			newRepository.setTechnologyAdapter(technologyAdapter);
			newRepository.setResourceCenter(resourceCenter);
			newRepository.setBaseArtefact(resourceCenter.getBaseArtefact());
			newRepository.getRootFolder().setRepositoryContext("[JavaSources]");
			return newRepository;
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static abstract class JavaCompilationUnitRepositoryImpl<I>
			extends TechnologyAdapterResourceRepositoryImpl<JavaCompilationUnitResource, JavaTechnologyAdapter, JavaCompilationUnit, I>
			implements JavaCompilationUnitRepository<I> {

		@Override
		public void registerResource(JavaCompilationUnitResource resource, RepositoryFolder<JavaCompilationUnitResource, I> parentFolder) {
			super.registerResource(resource, parentFolder);
			System.out.println("Hop, on met " + resource + " dans " + parentFolder);

			JavaSourceFolderResource sourceFolderResource = getTechnologyAdapter().getJavaSourceFolderRepository(getResourceCenter())
					.getResourceForFolder(parentFolder.getSerializationArtefact());
			System.out.println("Le parent c'est " + sourceFolderResource);
			if (sourceFolderResource != null) {
				sourceFolderResource.addToContents(resource);
			}

		}

	}
}
