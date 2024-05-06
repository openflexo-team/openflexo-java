/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
 * 
 * This file is part of Xmlconnector, a component of the software infrastructure 
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

package org.openflexo.technologyadapter.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.technologyadapter.java.rm.JavaCompilationUnitResource;
import org.openflexo.technologyadapter.java.rm.JavaPackageResource;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

import spoon.reflect.declaration.CtCompilationUnit;

@RunWith(OrderedRunner.class)
public class TestResourceStructure extends AbstractJavaTestCase {

	protected static final Logger logger = Logger.getLogger(TestResourceStructure.class.getPackage().getName());

	/**
	 * Instanciate test ResourceCenter
	 * 
	 * @throws IOException
	 * @throws FlexoException
	 * @throws ResourceLoadingCancelledException
	 */
	@Test
	@TestOrder(1)
	public void testLoadTestResourceCenter() throws IOException, ResourceLoadingCancelledException, FlexoException {
		log("testLoadTestResourceCenter()");

		copyJavaSourceFiles("TestResourceCenter/JavaCode");
		copyJavaSourceFiles("SomeOtherFolders");

		instanciateTestServiceManager(JavaTechnologyAdapter.class);

		FlexoResourceCenter<?> resourceCenter = serviceManager.getResourceCenterService()
				.getFlexoResourceCenter("http://openflexo.org/java-test");
		logger.info("Initially working with " + resourceCenter);
		resourceCenter = makeNewDirectoryResourceCenterFromExistingResourceCenter(serviceManager, resourceCenter);
		logger.info("Now working with " + resourceCenter);
		assertNotNull(resourceCenter);

		javaTechnologyAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(JavaTechnologyAdapter.class);

		javaPackageRepository = javaTechnologyAdapter.getJavaSourceFolderRepository(resourceCenter);
		logger.info("javaPackageRepository=" + javaPackageRepository);
		logger.info("allResources=" + javaPackageRepository.getAllResources());

		for (JavaPackageResource javaPackageResource : javaPackageRepository.getAllResources()) {
			logger.info("> " + javaPackageResource + " in " + javaPackageResource.getIODelegate().getSerializationArtefact());
		}

		FlexoResource<?> rootSourceFolderResource = getResourceWithSerializationArtefactWithName(javaPackageRepository.getAllResources(),
				"TestResourceCenter");
		FlexoResource<?> javaCodeSourceFolderResource = getResourceWithSerializationArtefactWithName(
				javaPackageRepository.getAllResources(), "JavaCode");

		assertNotNull(rootSourceFolderResource);
		assertNotNull(javaCodeSourceFolderResource);

		assertNull(rootSourceFolderResource.getContainer());
		assertTrue(rootSourceFolderResource.getContents().contains(javaCodeSourceFolderResource));
		assertSame(javaCodeSourceFolderResource.getContainer(), rootSourceFolderResource);

		javaCompilationUnitRepository = javaTechnologyAdapter.getJavaCompilationUnitRepository(resourceCenter);

		logger.info("javaCompilationUnitRepository=" + javaCompilationUnitRepository);
		logger.info("allResources=" + javaCompilationUnitRepository.getAllResources());
		for (JavaCompilationUnitResource javaCompilationUnitResource : javaCompilationUnitRepository.getAllResources()) {
			logger.info(
					"> " + javaCompilationUnitResource + " in " + javaCompilationUnitResource.getIODelegate().getSerializationArtefact());
		}

		JavaCompilationUnitResource helloWorldClassResource = getResourceWithSerializationArtefactWithName(
				javaCompilationUnitRepository.getAllResources(), "HelloWorld.java");
		assertNotNull(helloWorldClassResource);
		logger.info("helloWorldClassResource=" + helloWorldClassResource);

		assertTrue(javaCodeSourceFolderResource.getContents().contains(helloWorldClassResource));
		assertSame(helloWorldClassResource.getContainer(), javaCodeSourceFolderResource);

		assertNotNull(helloWorldClassResource.loadResourceData());

		CtCompilationUnit cu = helloWorldClassResource.getCompilationUnit().getCompilationUnit();
		assertNotNull(cu);
	}

	@Test
	@TestOrder(2)
	public void testFoldersVsPackageResources() throws IOException, ResourceLoadingCancelledException, FlexoException {
		log("testFoldersVsPackageResources()");

		logger.info("javaPackageRepository=" + javaPackageRepository);
		logger.info("allResources=" + javaPackageRepository.getAllResources());

		for (JavaPackageResource javaPackageResource : javaPackageRepository.getAllResources()) {
			logger.info(">>> " + javaPackageResource.getImplementedInterface().getSimpleName() + " in "
					+ javaPackageResource.getIODelegate().getSerializationArtefact());
		}

		JavaPackageResource someJavaSourceFolderResource = getResourceWithSerializationArtefactWithName(
				javaPackageRepository.getAllResources(), "SomeJavaSource");
		assertNotNull(someJavaSourceFolderResource);

		RepositoryFolder<JavaPackageResource, ?> parentFolder = javaPackageRepository.getRepositoryFolder(someJavaSourceFolderResource);
		assertNotNull(parentFolder);
		assertEquals("SubFolder", parentFolder.getName());

		RepositoryFolder<JavaPackageResource, ?> parentParentFolder = parentFolder.getParentFolder();
		assertNotNull(parentParentFolder);
		assertEquals("SomeOtherFolders", parentParentFolder.getName());

		RepositoryFolder<JavaPackageResource, ?> rootFolder = parentParentFolder.getParentFolder();
		assertSame(rootFolder, javaPackageRepository.getRootFolder());

		JavaCompilationUnitResource fooClassResource = getResourceWithSerializationArtefactWithName(
				javaCompilationUnitRepository.getAllResources(), "Foo.java");
		assertNotNull(fooClassResource);
		logger.info("fooClassResource=" + fooClassResource);

		assertTrue(someJavaSourceFolderResource.getContents().contains(fooClassResource));
		assertSame(fooClassResource.getContainer(), someJavaSourceFolderResource);

		assertNotNull(fooClassResource.loadResourceData());

		CtCompilationUnit cu = fooClassResource.getCompilationUnit().getCompilationUnit();
		assertNotNull(cu);

	}

}
