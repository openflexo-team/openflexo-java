/**
 * 
 * Copyright (c) 2013-2015, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
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

package org.openflexo.technologyadapter.java;

import java.util.logging.Logger;

import org.openflexo.foundation.fml.annotations.DeclareModelSlots;
import org.openflexo.foundation.fml.annotations.DeclareResourceFactories;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterBindingFactory;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterInitializationException;
import org.openflexo.technologyadapter.java.model.JavaCompilationUnitRepository;
import org.openflexo.technologyadapter.java.model.JavaPackageRepository;
import org.openflexo.technologyadapter.java.model.JavaTechnologyContextManager;
import org.openflexo.technologyadapter.java.rm.JavaCompilationUnitResourceFactory;
import org.openflexo.technologyadapter.java.rm.JavaPackageResourceFactory;

/**
 * This class defines and implements a {@link TechnologyAdapter} dealing with Java source code
 * 
 * @author sylvain
 * 
 */
@FML(
		value = "Java technology adapter",
		description = "<html>This technology adapter provides model federation facilities to manage Java source code or Java byte code<br>"
				+ "This technology adapter is build on SPOON technology" + "</html>")
@DeclareModelSlots({ JavaPackageModelSlot.class })
@DeclareResourceFactories({ JavaPackageResourceFactory.class, JavaCompilationUnitResourceFactory.class })
public class JavaTechnologyAdapter extends TechnologyAdapter<JavaTechnologyAdapter> {

	private static final Logger logger = Logger.getLogger(JavaTechnologyAdapter.class.getPackage().getName());

	public JavaTechnologyAdapter() throws TechnologyAdapterInitializationException {
	}

	@Override
	public String getName() {
		return "Java technology adapter";
	}

	@Override
	protected String getLocalizationDirectory() {
		return "FlexoLocalization/JavaTechnologyAdapter";
	}

	@Override
	public void ensureAllRepositoriesAreCreated(FlexoResourceCenter<?> rc) {
		super.ensureAllRepositoriesAreCreated(rc);
		// getOWLOntologyRepository(rc);
	}

	/**
	 * Return the {@link FlexoOntologyTechnologyContextManager} for this technology shared by all {@link FlexoResourceCenter} declared in
	 * the scope of {@link FlexoResourceCenterService}
	 * 
	 * @return
	 */
	@Override
	public JavaTechnologyContextManager getTechnologyContextManager() {
		return (JavaTechnologyContextManager) super.getTechnologyContextManager();
	}

	public <I> JavaPackageRepository<I> getJavaSourceFolderRepository(FlexoResourceCenter<I> resourceCenter) {
		JavaPackageRepository<I> returned = resourceCenter.retrieveRepository(JavaPackageRepository.class, this);
		if (returned == null) {
			returned = JavaPackageRepository.instanciateNewRepository(this, resourceCenter);
			resourceCenter.registerRepository(returned, JavaPackageRepository.class, this);
		}
		return returned;
	}

	public <I> JavaCompilationUnitRepository<I> getJavaCompilationUnitRepository(FlexoResourceCenter<I> resourceCenter) {
		JavaCompilationUnitRepository<I> returned = resourceCenter.retrieveRepository(JavaCompilationUnitRepository.class, this);
		if (returned == null) {
			returned = JavaCompilationUnitRepository.instanciateNewRepository(this, resourceCenter);
			resourceCenter.registerRepository(returned, JavaCompilationUnitRepository.class, this);
		}
		return returned;
	}

	@Override
	protected void resourceCenterHasBeenInitialized(FlexoResourceCenter<?> rc) {
		super.resourceCenterHasBeenInitialized(rc);
	}

	@Override
	public <I> boolean isIgnorable(FlexoResourceCenter<I> resourceCenter, I contents) {
		return false;
	}

	@Override
	public JavaTechnologyContextManager createTechnologyContextManager(FlexoResourceCenterService resourceCenterService) {
		return new JavaTechnologyContextManager(this, resourceCenterService);
	}

	@Override
	public String getIdentifier() {
		return "JAVA";
	}

	public JavaPackageResourceFactory getJavaSourceFolderResourceFactory() {
		return getResourceFactory(JavaPackageResourceFactory.class);
	}

	public JavaCompilationUnitResourceFactory getJavaCompilationUnitResourceFactory() {
		return getResourceFactory(JavaCompilationUnitResourceFactory.class);
	}

	@Override
	public TechnologyAdapterBindingFactory getTechnologyAdapterBindingFactory() {
		return null;
	}

}
