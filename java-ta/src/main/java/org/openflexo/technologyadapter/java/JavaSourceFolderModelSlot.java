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

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.DeclareFlexoRoles;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.ModelSlotInstance;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.pamela.PamelaMetaModelLibrary;
import org.openflexo.pamela.annotations.ImplementationClass;
import org.openflexo.pamela.annotations.ModelEntity;
import org.openflexo.pamela.annotations.XMLElement;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.java.fml.JavaClassRole;
import org.openflexo.technologyadapter.java.model.JavaSourceFolder;

/**
 * TODO
 * 
 * @author sylvain
 * 
 */
@DeclareFlexoRoles({ JavaClassRole.class })
@ModelEntity
@ImplementationClass(JavaSourceFolderModelSlot.JavaSourceFolderModelSlotImpl.class)
@XMLElement
@FML("JavaSourceFolderModelSlot")
/*@FML(
		value = "TypedDiagram",
		description = "<html>This ModelSlot represents access to a Diagram conform to a DiagramSpecification<br>"
				+ "Such diagram refers to a diagram metamodel which is composed of a example diagram and a collection of palettes both encoding template shapes and connectors"
				+ "</html>",
		examples = { @UsageExample(
				example = "Diagram myDiagram with DIAGRAM::TypedDiagram(diagramSpecification = myDiagramSpecification,"
						+ "paletteElementBindings = {\n"
						+ "            FMLDiagramPaletteElementBinding:(elementId=\"MyPaletteElement\",dropAction=\"MyConcept.drop()\",overridingGR=RED_SHAPE2),\n"
						+ "            FMLDiagramPaletteElementBinding:(elementId=\"MyPaletteElement2\",dropAction=\"MyConcept.drop()\")\n"
						+ "});",
				description = "Declares a model slot called 'myDiagram' with resulting type 'Diagram', realized through the 'TypedDiagram' model slot, conform to 'myDiagramSpecification' and specified palette element bindings") },
		references = { @SeeAlso(FreeDiagramModelSlot.class), @SeeAlso(CreateDiagram.class) })*/
public interface JavaSourceFolderModelSlot extends ModelSlot<JavaSourceFolder> {

	@Override
	public JavaTechnologyAdapter getModelSlotTechnologyAdapter();

	public static abstract class JavaSourceFolderModelSlotImpl extends ModelSlotImpl<JavaSourceFolder>
			implements JavaSourceFolderModelSlot {

		private static final Logger logger = Logger.getLogger(JavaSourceFolderModelSlot.class.getPackage().getName());

		private static org.openflexo.pamela.factory.PamelaModelFactory MF;

		static {
			try {
				MF = new org.openflexo.pamela.factory.PamelaModelFactory(PamelaMetaModelLibrary.retrieveMetaModel(JavaClassRole.class));
			} catch (ModelDefinitionException e) {
				e.printStackTrace();
			}
		}

		public static org.openflexo.pamela.factory.PamelaModelFactory getModelFactory() {
			return MF;
		}

		@Override
		public Class<JavaTechnologyAdapter> getTechnologyAdapterClass() {
			return JavaTechnologyAdapter.class;
		}

		@Override
		public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
			if (JavaClassRole.class.isAssignableFrom(patternRoleClass)) {
				return "class";
			}
			return null;
		}

		@Override
		public Type getType() {
			return JavaSourceFolder.class;
		}

		@Override
		public String getTypeDescription() {
			return "Java source folder";
		};

		@Override
		public JavaTechnologyAdapter getModelSlotTechnologyAdapter() {
			return (JavaTechnologyAdapter) super.getModelSlotTechnologyAdapter();
		}

		@Override
		public String getModelSlotDescription() {
			return "Java source folder";
		}

		@Override
		public String getURIForObject(JavaSourceFolder resourceData, Object o) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object retrieveObjectWithURI(JavaSourceFolder resourceData, String objectURI) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ModelSlotInstance<?, JavaSourceFolder> makeActorReference(JavaSourceFolder object, FlexoConceptInstance fci) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
