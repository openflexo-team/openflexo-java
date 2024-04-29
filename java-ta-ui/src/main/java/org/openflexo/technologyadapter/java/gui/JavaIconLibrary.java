/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
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

package org.openflexo.technologyadapter.java.gui;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.icon.ImageIconResource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.java.model.JavaCompilationUnit;
import org.openflexo.technologyadapter.java.model.JavaSourceFolder;

public class JavaIconLibrary {

	private static final Logger logger = Logger.getLogger(JavaIconLibrary.class.getPackage().getName());

	public static final ImageIconResource JAVA_TECHNOLOGY_BIG_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/Java32.png"));
	public static final ImageIconResource JAVA_TECHNOLOGY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/Java16.png"));

	public static final ImageIconResource COMPILATION_UNIT_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/jcu_obj.gif"));
	public static final ImageIconResource PACKAGE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/package_obj.gif"));

	public static ImageIcon iconForObject(Class<? extends TechnologyObject<?>> objectClass) {
		if (JavaCompilationUnit.class.isAssignableFrom(objectClass)) {
			return COMPILATION_UNIT_ICON;
		}
		if (JavaSourceFolder.class.isAssignableFrom(objectClass)) {
			return PACKAGE_ICON;
		}

		/*if (ExcelWorkbook.class.isAssignableFrom(objectClass)) {
			return EXCEL_TECHNOLOGY_ICON;
		}
		else if (ExcelCell.class.isAssignableFrom(objectClass)) {
			return EXCEL_CELL_ICON;
		}
		else if (ExcelSheet.class.isAssignableFrom(objectClass)) {
			return EXCEL_SHEET_ICON;
		}
		else if (ExcelRow.class.isAssignableFrom(objectClass)) {
			return EXCEL_ROW_ICON;
		}
		else if (ExcelColumn.class.isAssignableFrom(objectClass)) {
			return EXCEL_COLUMN_ICON;
		}
		else if (SEVirtualModelInstance.class.isAssignableFrom(objectClass)) {
			return IconFactory.getImageIcon(FMLRTIconLibrary.VIRTUAL_MODEL_INSTANCE_ICON, JavaIconLibrary.EXCEL_MARKER);
		}*/
		logger.warning("No icon for " + objectClass);
		return null;
	}

}
