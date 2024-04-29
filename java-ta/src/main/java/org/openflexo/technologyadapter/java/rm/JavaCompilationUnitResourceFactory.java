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
import org.openflexo.technologyadapter.java.model.JavaCompilationUnit;
import org.openflexo.technologyadapter.java.model.JavaModelFactory;

/**
 * Implementation of ResourceFactory for {@link JavaPackageResource}
 * 
 * @author sylvain
 *
 */
public class JavaCompilationUnitResourceFactory extends
		TechnologySpecificPamelaResourceFactory<JavaCompilationUnitResource, JavaCompilationUnit, JavaTechnologyAdapter, JavaModelFactory> {

	private static final Logger logger = Logger.getLogger(JavaCompilationUnitResourceFactory.class.getPackage().getName());

	public static String JAVA_FILE_EXTENSION = ".java";

	public JavaCompilationUnitResourceFactory() throws ModelDefinitionException {
		super(JavaCompilationUnitResource.class);
	}

	@Override
	public JavaCompilationUnit makeEmptyResourceData(JavaCompilationUnitResource resource) {

		logger.warning("Not implemented: makeEmptyResourceData()");
		return null;
	}

	@Override
	public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		return resourceCenter.retrieveName(serializationArtefact).endsWith(JAVA_FILE_EXTENSION);
	}

	@Override
	public <I> JavaCompilationUnitResource registerResource(JavaCompilationUnitResource resource, FlexoResourceCenter<I> resourceCenter) {
		super.registerResource(resource, resourceCenter);
		// Register the resource in the JavaPackageRepository of supplied resource center
		registerResourceInResourceRepository(resource,
				getTechnologyAdapter(resourceCenter.getServiceManager()).getJavaCompilationUnitRepository(resourceCenter));

		return resource;
	}

	@Override
	protected <I> JavaCompilationUnitResource initResourceForRetrieving(I serializationArtefact, FlexoResourceCenter<I> resourceCenter)
			throws ModelDefinitionException, IOException {
		JavaCompilationUnitResource returned = super.initResourceForRetrieving(serializationArtefact, resourceCenter);
		logger.warning("TODO: set URI for " + serializationArtefact);
		// returned.setURI(OWLOntology.findOntologyURI(returned.getIODelegate().getSerializationArtefactAsResource()));
		return returned;
	}

	@Override
	public JavaModelFactory makeModelFactory(JavaCompilationUnitResource resource,
			TechnologyContextManager<JavaTechnologyAdapter> technologyContextManager) throws ModelDefinitionException {
		return new JavaModelFactory(resource, technologyContextManager.getServiceManager().getEditingContext());
	}

}
