/**
 * 
 * Copyright (c) 2013-2015, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
 * 
 * This file is part of Openflexo-technology-adapters-ui, a component of the software infrastructure 
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

package org.openflexo.technologyadapter.java.controller;

import javax.swing.ImageIcon;

import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.editionaction.EditionAction;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.gina.utils.InspectorGroup;
import org.openflexo.technologyadapter.java.JavaTechnologyAdapter;
import org.openflexo.technologyadapter.java.controller.view.JavaCompilationUnitView;
import org.openflexo.technologyadapter.java.gui.JavaIconLibrary;
import org.openflexo.technologyadapter.java.model.JavaCompilationUnit;
import org.openflexo.technologyadapter.java.model.JavaSourceElement;
import org.openflexo.technologyadapter.java.rm.JavaCompilationUnitResource;
import org.openflexo.technologyadapter.java.rm.JavaPackageResource;
import org.openflexo.view.EmptyPanel;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class JavaAdapterController extends TechnologyAdapterController<JavaTechnologyAdapter> {

	private InspectorGroup javaInspectorGroup;
	private JavaLanguageSupport javaLanguageSupport;

	public JavaAdapterController() {
		LanguageSupportFactory lsf = LanguageSupportFactory.get();
		javaLanguageSupport = (JavaLanguageSupport) lsf.getSupportFor(SyntaxConstants.SYNTAX_STYLE_JAVA);
	}

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

		javaInspectorGroup = controller.loadInspectorGroup("JAVA", getTechnologyAdapter().getLocales(),
				getFMLTechnologyAdapterInspectorGroup());
		// actionInitializer.getController().getModuleInspectorController().loadDirectory(ResourceLocator.locateResource("Inspectors/Excel"));
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
	}

	@Override
	public ImageIcon getTechnologyBigIcon() {
		return JavaIconLibrary.JAVA_TECHNOLOGY_BIG_ICON;
	}

	@Override
	public ImageIcon getTechnologyIcon() {
		return JavaIconLibrary.JAVA_TECHNOLOGY_ICON;
	}

	@Override
	public ImageIcon getModelIcon() {
		return JavaIconLibrary.JAVA_TECHNOLOGY_ICON;
	}

	@Override
	public ImageIcon getMetaModelIcon() {
		return JavaIconLibrary.JAVA_TECHNOLOGY_ICON;
	}

	@Override
	public ImageIcon getIconForTechnologyObject(Class<? extends TechnologyObject<?>> objectClass) {
		return JavaIconLibrary.iconForObject(objectClass);
	}

	@Override
	public ImageIcon getIconForFlexoRole(Class<? extends FlexoRole<?>> flexoRoleClass) {
		/*if (ExcelSheetRole.class.isAssignableFrom(flexoRoleClass)) {
			return getIconForTechnologyObject(ExcelSheet.class);
		}
		if (ExcelCellRole.class.isAssignableFrom(flexoRoleClass)) {
			return getIconForTechnologyObject(ExcelCell.class);
		}
		if (ExcelRowRole.class.isAssignableFrom(flexoRoleClass)) {
			return getIconForTechnologyObject(ExcelRow.class);
		}
		if (SEColumnRole.class.isAssignableFrom(flexoRoleClass)) {
			return getIconForTechnologyObject(ExcelColumn.class);
		}
		if (SEReferenceRole.class.isAssignableFrom(flexoRoleClass)) {
			return IconFactory.getImageIcon(FMLIconLibrary.FLEXO_CONCEPT_ICON, JavaIconLibrary.EXCEL_MARKER);
		}
		if (SEDataAreaRole.class.isAssignableFrom(flexoRoleClass)) {
			return IconFactory.getImageIcon(FMLIconLibrary.FLEXO_CONCEPT_ICON, JavaIconLibrary.EXCEL_MARKER);
		}*/
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
		/*if (CreateExcelResource.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(ExcelWorkbook.class), IconLibrary.DUPLICATE);
		}
		else if (GenerateExcelResource.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(ExcelWorkbook.class), IconLibrary.DUPLICATE);
		}
		else if (AddExcelSheet.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(ExcelSheet.class), IconLibrary.DUPLICATE);
		}
		else if (AddExcelCell.class.isAssignableFrom(editionActionClass)) {
			return JavaIconLibrary.ADD_EXCEL_CELL_ICON;
		}
		else if (AddExcelRow.class.isAssignableFrom(editionActionClass)) {
			return JavaIconLibrary.ADD_EXCEL_ROW_ICON;
		}
		else if (CellStyleAction.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(JavaIconLibrary.EXCEL_GRAPHICAL_ACTION_ICON, IconLibrary.DUPLICATE);
		}
		else if (MergeCells.class.isAssignableFrom(editionActionClass)) {
			return JavaIconLibrary.EXCEL_CELL_ICON;
		}
		else if (AbstractSelectExcelSheet.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(ExcelSheet.class), IconLibrary.IMPORT);
		}
		else if (AbstractSelectExcelRow.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(ExcelRow.class), IconLibrary.IMPORT);
		}
		else if (AbstractSelectExcelCell.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(ExcelCell.class), IconLibrary.IMPORT);
		}
		else if (CreateSEResource.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(FMLRTIconLibrary.VIRTUAL_MODEL_INSTANCE_ICON, JavaIconLibrary.EXCEL_MARKER);
		}
		else if (InsertSEObject.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(FMLRTIconLibrary.FLEXO_CONCEPT_INSTANCE_ICON, IconLibrary.NEW_MARKER);
		}
		else if (RemoveSEObject.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(FMLRTIconLibrary.FLEXO_CONCEPT_INSTANCE_ICON, IconLibrary.DELETE);
		}*/
		return super.getIconForEditionAction(editionActionClass);
	}

	@Override
	public boolean isRepresentableInModuleView(TechnologyObject<JavaTechnologyAdapter> object) {
		if (object instanceof JavaCompilationUnit) {
			return true;
		}
		if (object instanceof JavaSourceElement) {
			return true;
		}
		return false;
	}

	@Override
	public FlexoObject getRepresentableMasterObject(TechnologyObject<JavaTechnologyAdapter> object) {
		if (object instanceof JavaCompilationUnit) {
			return object;
		}
		if (object instanceof JavaSourceElement) {
			return ((JavaSourceElement<?>) object).getResourceData();
		}
		return null;
	}

	@Override
	public String getWindowTitleforObject(TechnologyObject<JavaTechnologyAdapter> object, FlexoController controller) {
		if (object instanceof JavaCompilationUnit) {
			return ((JavaCompilationUnit) object).getName();
		}
		if (object instanceof JavaSourceElement) {
			return ((JavaSourceElement<?>) object).getResourceData().getName();
		}
		return object.toString();
	}

	@Override
	public ModuleView<?> createModuleViewForMasterObject(TechnologyObject<JavaTechnologyAdapter> object, FlexoController controller,
			FlexoPerspective perspective) {
		System.out.println("Bon on cree une vue pour " + object);
		if (object instanceof JavaCompilationUnit) {
			JavaCompilationUnitResource resource = ((JavaCompilationUnit) object).getResource();
			return new JavaCompilationUnitView(resource, controller, perspective);
		}
		return new EmptyPanel<>(controller, perspective, object);
	}

	/**
	 * Implement a technology-specific policy for displaying resource folders
	 * 
	 * Return boolean indicating if supplied folder should be displayed in this technology, asserting this folder contains some resources of
	 * this technology
	 * 
	 * @param folder
	 * @return
	 */
	@Override
	public boolean shouldBeDisplayed(RepositoryFolder<?, ?> folder) {

		FlexoResource resource = ((RepositoryFolder) folder).getResourceRepository().getResource(folder.getSerializationArtefact());
		if (resource instanceof JavaPackageResource) {
			return false;
		}
		return true;
	}

	@Override
	public boolean shouldDisplayContents(FlexoResource<?> resource) {
		if (resource instanceof JavaPackageResource) {
			return true;
		}
		return super.shouldDisplayContents(resource);
	}

}
