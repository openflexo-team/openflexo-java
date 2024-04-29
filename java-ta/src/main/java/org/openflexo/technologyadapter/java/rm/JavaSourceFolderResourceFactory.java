/*
 * (c) Copyright 2013 Openflexo
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openflexo.technologyadapter.java.rm;

import java.io.IOException;
import java.util.logging.Logger;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.TechnologySpecificPamelaResourceFactory;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.java.JavaTechnologyAdapter;
import org.openflexo.technologyadapter.java.model.JavaPackageFactory;
import org.openflexo.technologyadapter.java.model.JavaSourceFolder;

/**
 * Implementation of ResourceFactory for {@link JavaSourceFolderResource}
 * 
 * @author sylvain
 *
 */
public class JavaSourceFolderResourceFactory extends
		TechnologySpecificPamelaResourceFactory<JavaSourceFolderResource, JavaSourceFolder, JavaTechnologyAdapter, JavaPackageFactory> {

	private static final Logger logger = Logger.getLogger(JavaSourceFolderResourceFactory.class.getPackage().getName());

	public JavaSourceFolderResourceFactory() throws ModelDefinitionException {
		super(JavaSourceFolderResource.class);
	}

	@Override
	public JavaSourceFolder makeEmptyResourceData(JavaSourceFolderResource resource) {
		logger.warning("Not implemented: makeEmptyResourceData()");
		return null;
	}

	@Override
	public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {

		if (!resourceCenter.isDirectory(serializationArtefact)) {
			return false;
		}
		return containsJavaFiles(serializationArtefact, resourceCenter);
	}

	private <I> boolean containsJavaFiles(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		for (I content : resourceCenter.getContents(serializationArtefact)) {
			if (resourceCenter.retrieveName(content).endsWith(JavaCompilationUnitResourceFactory.JAVA_FILE_EXTENSION)) {
				return true;
			}
		}
		for (I content : resourceCenter.getContents(serializationArtefact)) {
			if (resourceCenter.isDirectory(content)) {
				return containsJavaFiles(content, resourceCenter);
			}
		}
		return false;

	}

	@Override
	public <I> JavaSourceFolderResource registerResource(JavaSourceFolderResource resource, FlexoResourceCenter<I> resourceCenter) {
		super.registerResource(resource, resourceCenter);

		// Register the resource in the JavaSourceFolderRepository of supplied resource center
		registerResourceInResourceRepository(resource,
				getTechnologyAdapter(resourceCenter.getServiceManager()).getJavaSourceFolderRepository(resourceCenter));

		return resource;
	}

	@Override
	protected <I> JavaSourceFolderResource initResourceForRetrieving(I serializationArtefact, FlexoResourceCenter<I> resourceCenter)
			throws ModelDefinitionException, IOException {
		JavaSourceFolderResource returned = super.initResourceForRetrieving(serializationArtefact, resourceCenter);
		logger.warning("TODO: set URI for " + serializationArtefact);
		// returned.setURI(OWLOntology.findOntologyURI(returned.getIODelegate().getSerializationArtefactAsResource()));
		return returned;
	}

	@Override
	public JavaPackageFactory makeModelFactory(JavaSourceFolderResource resource,
			TechnologyContextManager<JavaTechnologyAdapter> technologyContextManager) throws ModelDefinitionException {
		return new JavaPackageFactory(resource, technologyContextManager.getServiceManager().getEditingContext());
	}

}
