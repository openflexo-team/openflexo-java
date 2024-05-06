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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.TechnologySpecificPamelaResourceFactory;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.rm.InJarResourceImpl;
import org.openflexo.technologyadapter.java.JavaTechnologyAdapter;
import org.openflexo.technologyadapter.java.model.JavaPackage;
import org.openflexo.technologyadapter.java.model.JavaPackageFactory;

/**
 * Implementation of ResourceFactory for {@link JavaPackageResource}
 * 
 * @author sylvain
 *
 */
public class JavaPackageResourceFactory
		extends TechnologySpecificPamelaResourceFactory<JavaPackageResource, JavaPackage, JavaTechnologyAdapter, JavaPackageFactory> {

	private static final Logger logger = Logger.getLogger(JavaPackageResourceFactory.class.getPackage().getName());

	public JavaPackageResourceFactory() throws ModelDefinitionException {
		super(JavaPackageResource.class);
	}

	@Override
	public JavaPackage makeEmptyResourceData(JavaPackageResource resource) {
		logger.warning("Not implemented: makeEmptyResourceData()");
		return null;
	}

	@Override
	public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {

		if (!resourceCenter.isDirectory(serializationArtefact)) {
			return false;
		}
		return isPackage(serializationArtefact, resourceCenter);
	}

	private <I> boolean isJavaArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		return resourceCenter.retrieveName(serializationArtefact).endsWith(JavaCompilationUnitResourceFactory.JAVA_FILE_EXTENSION);
	}

	private <I> String getPackageName(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) throws IOException {
		if (serializationArtefact instanceof File) {
			FileReader fileReader = null;
			try {
				fileReader = new FileReader((File) serializationArtefact);
				return extractPackageName(fileReader);
			} finally {
				fileReader.close();
			}
		}
		else if (serializationArtefact instanceof InJarResourceImpl) {
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			try {
				inputStream = ((InJarResourceImpl) serializationArtefact).openInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				return extractPackageName(inputStreamReader);
			} finally {
				inputStreamReader.close();
				inputStream.close();
			}
		}
		System.out.println("What to do with " + serializationArtefact + " of " + serializationArtefact.getClass());
		return null;
	}

	private static String extractPackageName(Reader reader) throws IOException {
		BufferedReader br = new BufferedReader(reader);
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.startsWith("package ")) {
				return line.substring(8, line.lastIndexOf(';')).trim();
			}
		}
		return null;
	}

	private <I> boolean isPackage(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		return isPackage(serializationArtefact, resourceCenter, resourceCenter.retrieveName(serializationArtefact));
	}

	private <I> boolean isPackage(I serializationArtefact, FlexoResourceCenter<I> resourceCenter, String expectedPackageName) {

		for (I content : resourceCenter.getContents(serializationArtefact)) {
			if (isJavaArtefact(content, resourceCenter)) {
				String packageName = null;
				try {
					packageName = getPackageName(content, resourceCenter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// System.out.println("Fichier java: " + content + " package=" + packageName);
				if (expectedPackageName.equals(packageName)) {
					return true;
				}
				if (packageName == null) {
					return false;
				}
				if (packageName.endsWith(expectedPackageName)) {
					// System.out.println("expectedPackageName=" + expectedPackageName);
					// System.out.println("packageName=" + packageName);
					String expectedPathName = resourceCenter.relativePath(serializationArtefact);
					// System.out.println("expectedPathName=" + expectedPathName);
					expectedPathName = expectedPathName.replace(File.separator, ".");
					// System.out.println("expectedPathName=" + expectedPathName);
					if (packageName.equals(expectedPathName)) {
						// System.out.println("YES!!!");
						return true;
					}
				}
				return false;
			}
		}
		for (I content : resourceCenter.getContents(serializationArtefact)) {
			if (resourceCenter.isDirectory(content)) {
				return isPackage(content, resourceCenter, expectedPackageName + "." + resourceCenter.retrieveName(content));
			}
		}
		return false;

	}

	@Override
	public <I> JavaPackageResource registerResource(JavaPackageResource resource, FlexoResourceCenter<I> resourceCenter) {
		super.registerResource(resource, resourceCenter);

		// Register the resource in the JavaPackageRepository of supplied resource center
		registerResourceInResourceRepository(resource,
				getTechnologyAdapter(resourceCenter.getServiceManager()).getJavaSourceFolderRepository(resourceCenter));

		return resource;
	}

	@Override
	protected <I> JavaPackageResource initResourceForRetrieving(I serializationArtefact, FlexoResourceCenter<I> resourceCenter)
			throws ModelDefinitionException, IOException {
		JavaPackageResource returned = super.initResourceForRetrieving(serializationArtefact, resourceCenter);
		logger.warning("TODO: set URI for " + serializationArtefact);
		// returned.setURI(OWLOntology.findOntologyURI(returned.getIODelegate().getSerializationArtefactAsResource()));
		return returned;
	}

	@Override
	public JavaPackageFactory makeModelFactory(JavaPackageResource resource,
			TechnologyContextManager<JavaTechnologyAdapter> technologyContextManager) throws ModelDefinitionException {
		return new JavaPackageFactory(resource, technologyContextManager.getServiceManager().getEditingContext());
	}

}
