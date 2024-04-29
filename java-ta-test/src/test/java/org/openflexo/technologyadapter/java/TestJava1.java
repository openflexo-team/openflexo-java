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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.test.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.java.model.JavaCompilationUnitRepository;
import org.openflexo.technologyadapter.java.model.JavaPackageRepository;
import org.openflexo.technologyadapter.java.rm.JavaCompilationUnitResource;
import org.openflexo.technologyadapter.java.rm.JavaCompilationUnitResourceFactory;
import org.openflexo.technologyadapter.java.rm.JavaPackageResource;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;
import org.openflexo.toolbox.FileUtils;
import org.openflexo.toolbox.FileUtils.CopyStrategy;

@RunWith(OrderedRunner.class)
public class TestJava1 extends OpenflexoProjectAtRunTimeTestCase {

	protected static final Logger logger = Logger.getLogger(TestJava1.class.getPackage().getName());

	private static JavaTechnologyAdapter javaTechnologyAdapter;
	private static JavaPackageRepository<?> javaPackageRepository;
	private static JavaCompilationUnitRepository<?> javaCompilationUnitRepository;

	protected void copyJavaSourceFiles(String relativePath) throws IOException {
		Resource targetResource = ResourceLocator.locateResource(relativePath);
		System.out.println("targetResource=" + targetResource + " of " + targetResource.getClass());

		Resource sourceResource = ResourceLocator.locateSourceCodeResource(relativePath);
		System.out.println("sourceResource=" + sourceResource + " of " + sourceResource.getClass());

		if (targetResource instanceof FileResourceImpl && sourceResource instanceof FileResourceImpl) {
			File srcDir = ((FileResourceImpl) sourceResource).getFile();
			File dstDir = ((FileResourceImpl) targetResource).getFile();
			FileUtils.copyContentDirToDir(srcDir, dstDir, CopyStrategy.REPLACE, new FileFilter() {
				@Override
				public boolean accept(File path) {
					return path.getName().endsWith(JavaCompilationUnitResourceFactory.JAVA_FILE_EXTENSION);
				}
			});
		}
	}

	protected <R extends FlexoResource<?>> R getResourceWithSerializationArtefactWithName(Collection<R> resources, String resourceName) {
		for (R resource : resources) {
			if (resource.getIODelegate().getSerializationArtefactName().contains(resourceName)) {
				return resource;
			}
		}
		return null;
	}

	protected boolean containsResourceWithSerializationArtefactWithName(Collection<? extends FlexoResource<?>> resources,
			String resourceName) {
		return (getResourceWithSerializationArtefactWithName(resources, resourceName) != null);
	}

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

		/*Resource targetResource = ResourceLocator.locateResource("TestResourceCenter/JavaCode");
		System.out.println("targetResource=" + targetResource + " of " + targetResource.getClass());
		
		Resource sourceResource = ResourceLocator.locateSourceCodeResource("TestResourceCenter/JavaCode");
		System.out.println("sourceResource=" + sourceResource + " of " + sourceResource.getClass());
		
		if (targetResource instanceof FileResourceImpl && sourceResource instanceof FileResourceImpl) {
			File srcDir = ((FileResourceImpl) sourceResource).getFile();
			File dstDir = ((FileResourceImpl) targetResource).getFile();
			FileUtils.copyContentDirToDir(srcDir, dstDir, CopyStrategy.REPLACE, new FileFilter() {
				@Override
				public boolean accept(File path) {
					return path.getName().endsWith(JavaCompilationUnitResourceFactory.JAVA_FILE_EXTENSION);
				}
			});
		}*/

		copyJavaSourceFiles("TestResourceCenter/JavaCode");

		instanciateTestServiceManager(JavaTechnologyAdapter.class);

		FlexoResourceCenter<?> resourceCenter = serviceManager.getResourceCenterService()
				.getFlexoResourceCenter("http://openflexo.org/java-test");

		assertNotNull(resourceCenter);

		javaTechnologyAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(JavaTechnologyAdapter.class);

		javaPackageRepository = javaTechnologyAdapter.getJavaSourceFolderRepository(resourceCenter);
		System.out.println("javaPackageRepository=" + javaPackageRepository);
		System.out.println("allResources=" + javaPackageRepository.getAllResources());

		for (JavaPackageResource javaPackageResource : javaPackageRepository.getAllResources()) {
			System.out.println(
					"> " + javaPackageResource + " in " + javaPackageResource.getIODelegate().getSerializationArtefact());
		}

		FlexoResource<?> rootSourceFolderResource = getResourceWithSerializationArtefactWithName(
				javaPackageRepository.getAllResources(), "TestResourceCenter");
		FlexoResource<?> javaCodeSourceFolderResource = getResourceWithSerializationArtefactWithName(
				javaPackageRepository.getAllResources(), "JavaCode");

		assertNotNull(rootSourceFolderResource);
		assertNotNull(javaCodeSourceFolderResource);

		assertNull(rootSourceFolderResource.getContainer());
		assertTrue(rootSourceFolderResource.getContents().contains(javaCodeSourceFolderResource));
		assertSame(javaCodeSourceFolderResource.getContainer(), rootSourceFolderResource);

		javaCompilationUnitRepository = javaTechnologyAdapter.getJavaCompilationUnitRepository(resourceCenter);
		System.out.println("javaCompilationUnitRepository=" + javaCompilationUnitRepository);
		System.out.println("allResources=" + javaCompilationUnitRepository.getAllResources());
		for (JavaCompilationUnitResource javaCompilationUnitResource : javaCompilationUnitRepository.getAllResources()) {
			System.out.println(
					"> " + javaCompilationUnitResource + " in " + javaCompilationUnitResource.getIODelegate().getSerializationArtefact());
		}

		JavaCompilationUnitResource helloWorldClassResource = getResourceWithSerializationArtefactWithName(
				javaCompilationUnitRepository.getAllResources(), "HelloWorld.java");
		assertNotNull(helloWorldClassResource);
		logger.info("helloWorldClassResource=" + helloWorldClassResource);

		assertTrue(javaCodeSourceFolderResource.getContents().contains(helloWorldClassResource));
		assertSame(helloWorldClassResource.getContainer(), javaCodeSourceFolderResource);

		assertNotNull(helloWorldClassResource.loadResourceData());
	}

}
