/**
 * 
 * Copyright (c) 2018, Openflexo
 * 
 * This file is part of OpenflexoTechnologyAdapter, a component of the software infrastructure 
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

package org.openflexo.ta.java.controller;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.editionaction.EditionAction;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.gina.utils.InspectorGroup;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.ta.java.gui.JavaIconLibrary;
import org.openflexo.ta.java.view.JavaTextView;
import org.openflexo.ta.java.JavaTechnologyAdapter;
import org.openflexo.ta.java.fml.JavaLineRole;
import org.openflexo.ta.java.fml.editionaction.AbstractSelectJavaLine;
import org.openflexo.ta.java.fml.editionaction.AddJavaLine;
import org.openflexo.ta.java.model.JavaLine;
import org.openflexo.ta.java.model.JavaObject;
import org.openflexo.ta.java.model.JavaText;
import org.openflexo.view.EmptyPanel;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class JavaAdapterController extends TechnologyAdapterController<JavaTechnologyAdapter> {

	static final Logger logger = Logger.getLogger(JavaAdapterController.class.getPackage().getName());

	private InspectorGroup javaInspectorGroup;

	@Override
	public Class<JavaTechnologyAdapter> getTechnologyAdapterClass() {
		return JavaTechnologyAdapter.class;
	}

	/**
	 * Initialize inspectors for supplied module using supplied {@link FlexoController}
	 * 
	 * @param controller
	 */
	@Override
	protected void initializeInspectors(FlexoController controller) {

		javaInspectorGroup = controller.loadInspectorGroup("Java", getTechnologyAdapter().getLocales(),
				getFMLTechnologyAdapterInspectorGroup());
	}

	/**
	 * Return inspector group for this technology
	 * 
	 * @return
	 */
	@Override
	public InspectorGroup getTechnologyAdapterInspectorGroup() {
		return javaInspectorGroup;
	}

	@Override
	protected void initializeActions(ControllerActionInitializer actionInitializer) {

		// You can initialize here actions specific to that technology
	}

	/**
	 * Return icon representing underlying technology, required size is 32x32
	 * 
	 * @return
	 */
	@Override
	public ImageIcon getTechnologyBigIcon() {
		return JavaIconLibrary.JAVA_TA_BIG_ICON;
	}

	/**
	 * Return icon representing underlying technology
	 * 
	 * @return
	 */
	@Override
	public ImageIcon getTechnologyIcon() {
		return JavaIconLibrary.JAVA_TA_ICON;
	}

	/**
	 * Return icon representing a model of underlying technology
	 * 
	 * @return
	 */
	@Override
	public ImageIcon getModelIcon() {
		return JavaIconLibrary.JAVA_TEXT_ICON;
	}

	/**
	 * Return icon representing a model of underlying technology
	 * 
	 * @return
	 */
	@Override
	public ImageIcon getMetaModelIcon() {
		return JavaIconLibrary.JAVA_TEXT_ICON;
	}

	/**
	 * Return icon representing supplied ontology object
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public ImageIcon getIconForTechnologyObject(Class<? extends TechnologyObject<?>> objectClass) {
		if (JavaObject.class.isAssignableFrom(objectClass)) {
			return JavaIconLibrary.iconForObject((Class<? extends JavaObject>) objectClass);
		}
		return null;
	}

	/**
	 * Return icon representing supplied pattern property
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public ImageIcon getIconForFlexoRole(Class<? extends FlexoRole<?>> patternRoleClass) {
		if (JavaLineRole.class.isAssignableFrom(patternRoleClass)) {
			return getIconForTechnologyObject(JavaLine.class);
		}
		return null;
	}

	/**
	 * Return icon representing supplied edition action
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public ImageIcon getIconForEditionAction(Class<? extends EditionAction> editionActionClass) {
		if (AddJavaLine.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(JavaLine.class), IconLibrary.DUPLICATE);
		}
		else if (AbstractSelectJavaLine.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(JavaLine.class), IconLibrary.IMPORT);
		}
		return super.getIconForEditionAction(editionActionClass);
	}

	@Override
	public boolean hasModuleViewForObject(TechnologyObject<JavaTechnologyAdapter> object, FlexoController controller) {
		return object instanceof JavaText;
	}

	@Override
	public String getWindowTitleforObject(TechnologyObject<JavaTechnologyAdapter> object, FlexoController controller) {
		if (object instanceof JavaText) {
			return ((JavaText) object).getResource().getName();
		}
		return object.toString();
	}

	@Override
	public ModuleView<?> createModuleViewForObject(TechnologyObject<JavaTechnologyAdapter> object, FlexoController controller,
			FlexoPerspective perspective) {
		if (object instanceof JavaText) {
			JavaTextView returned = new JavaTextView((JavaText) object, controller, perspective);
			return returned;
		}
		return new EmptyPanel<>(controller, perspective, object);
	}

}
