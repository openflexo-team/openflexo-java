/**
 * 
 * Copyright (c) 2013-2014, Openflexo
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

package org.openflexo.technologyadapter.java.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterResource;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.technologyadapter.java.JavaTechnologyAdapter;
import org.openflexo.technologyadapter.java.rm.JavaPackageResource;
import org.openflexo.toolbox.FileUtils;

import spoon.IncrementalLauncher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;

/**
 * TODO
 * 
 * @author sylvain
 * 
 */
public class JavaTechnologyContextManager extends TechnologyContextManager<JavaTechnologyAdapter> {

	private static final Logger logger = Logger.getLogger(JavaTechnologyContextManager.class.getPackage().getName());

	private Set<File> directories = new HashSet<>();

	private SpoonAPI spoon;
	private boolean analysisUptodate = false;

	public JavaTechnologyContextManager(JavaTechnologyAdapter adapter, FlexoResourceCenterService resourceCenterService) {
		super(adapter, resourceCenterService);
	}

	@Override
	public void registerResource(TechnologyAdapterResource<?, JavaTechnologyAdapter> resource) {
		super.registerResource(resource);
		// System.out.println("-----> registerResource " + resource);
		if (resource instanceof JavaPackageResource) {
			if (resource.getIODelegate().getSerializationArtefact() instanceof File) {
				appendSourceDirectory((File) resource.getIODelegate().getSerializationArtefact());
				/*System.out.println("On rajoute: " + ((File) resource.getIODelegate().getSerializationArtefact()).getAbsolutePath());
				spoon.addInputResource(((File) resource.getIODelegate().getSerializationArtefact()).getAbsolutePath());
				spoon.buildModel();
				spoon.*/
			}
		}
	}

	private void appendSourceDirectory(File aDirectory) {
		List<File> uselessDirectories = new ArrayList<>();
		for (File dir : directories) {
			if (FileUtils.isFileContainedIn(aDirectory, dir)) {
				// No need to add it
				return;
			}
			if (FileUtils.isFileContainedIn(dir, aDirectory)) {
				uselessDirectories.add(dir);
			}
		}
		if (uselessDirectories.size() > 0) {
			directories.removeAll(uselessDirectories);
		}
		directories.add(aDirectory);
		analysisUptodate = false;
	}

	public CtModel getModelAnalysis() {
		if (analysisUptodate) {
			return spoon.getModel();
		}
		logger.info("Building Spoon IncrementalLauncher...");
		for (File file : directories) {
			logger.info("...use " + file.getAbsolutePath());
		}
		final File cache = new File("<path_to_cache>");
		spoon = new IncrementalLauncher(directories, Collections.emptySet(), cache);
		CtModel model = spoon.buildModel();
		analysisUptodate = true;

		return model;
	}
}
