/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Freemodellingeditor, a component of the software infrastructure 
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

package org.openflexo.technologyadapter.java.controller.view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.openflexo.fml.controller.FMLTechnologyAdapterController;
import org.openflexo.fml.controller.widget.FIBCompilationUnitBrowser;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.fml.FMLTechnologyAdapter;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.selection.SelectionListener;
import org.openflexo.selection.SelectionManager;
import org.openflexo.technologyadapter.java.model.JavaCompilationUnit;
import org.openflexo.technologyadapter.java.rm.JavaCompilationUnitResource;
import org.openflexo.view.SelectionSynchronizedModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterControllerService;
import org.openflexo.view.controller.model.FlexoPerspective;

@SuppressWarnings("serial")
public class JavaCompilationUnitView extends JPanel
		implements SelectionSynchronizedModuleView<JavaCompilationUnit>, PropertyChangeListener {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(JavaCompilationUnitView.class.getPackage().getName());

	private final JavaCompilationUnitResource javaCompilationUnitResource;
	private final FlexoPerspective perspective;
	// private final FMLEditor editor;
	// private JTextArea editor;
	private final FlexoController flexoController;

	// private final JPanel bottomPanel;

	public JavaCompilationUnitView(JavaCompilationUnitResource javaCUResource, FlexoController flexoController,
			FlexoPerspective perspective) {
		super();
		setLayout(new BorderLayout());
		this.javaCompilationUnitResource = javaCUResource;
		this.perspective = perspective;
		this.flexoController = flexoController;

		String text;
		try {

			text = javaCUResource.getResourceData().getCompilationUnit().getOriginalSourceCode();
			RSyntaxTextArea textArea = createTextArea();
			textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
			textArea.setText(text);
			RTextScrollPane scrollPane = new RTextScrollPane(textArea, true);
			scrollPane.setIconRowHeaderEnabled(true);
			scrollPane.getGutter().setBookmarkingEnabled(true);
			ErrorStrip errorStrip = new ErrorStrip(textArea);
			add(scrollPane, BorderLayout.CENTER);
			add(errorStrip, BorderLayout.LINE_END);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ResourceLoadingCancelledException e) {
			e.printStackTrace();
		} catch (FlexoException e) {
			e.printStackTrace();
		}

		validate();

		getRepresentedObject().getPropertyChangeSupport().addPropertyChangeListener(getRepresentedObject().getDeletedProperty(), this);

		if (flexoController != null && flexoController.getSelectionManager() != null) {
			flexoController.getSelectionManager().addToSelectionListeners(this);
		}

	}

	/**
	 * Creates the text area for this application.
	 *
	 * @return The text area.
	 */
	private RSyntaxTextArea createTextArea() {
		RSyntaxTextArea textArea = new RSyntaxTextArea(25, 80);
		LanguageSupportFactory.get().register(textArea);
		textArea.setCaretPosition(0);
		// textArea.addHyperlinkListener(this);
		textArea.requestFocusInWindow();
		textArea.setMarkOccurrences(true);
		textArea.setCodeFoldingEnabled(true);
		textArea.setTabsEmulated(true);
		textArea.setTabSize(3);
		// textArea.setBackground(new java.awt.Color(224, 255, 224));
		// textArea.setUseSelectedTextColor(true);
		// textArea.setLineWrap(true);
		ToolTipManager.sharedInstance().registerComponent(textArea);
		return textArea;
	}

	public FlexoController getFlexoController() {
		return flexoController;
	}

	@Override
	public FlexoPerspective getPerspective() {
		return perspective;
	}

	@Override
	public void deleteModuleView() {
		System.out.println("deleteModuleView() in FMLCompilationUnitView");
		getRepresentedObject().getPropertyChangeSupport().removePropertyChangeListener(getRepresentedObject().getDeletedProperty(), this);
		if (getFlexoController() != null) {
			getFlexoController().removeModuleView(this);
			if (getFlexoController().getSelectionManager() != null) {
				getFlexoController().getSelectionManager().removeFromSelectionListeners(this);
			}
		}
		// getEditor().delete();
	}

	@Override
	public JavaCompilationUnit getRepresentedObject() {
		return javaCompilationUnitResource.getCompilationUnit();
	}

	@Override
	public boolean isAutoscrolled() {
		return true;
	}

	public FMLTechnologyAdapterController getDiagramTechnologyAdapterController(FlexoController controller) {
		TechnologyAdapterControllerService tacService = controller.getApplicationContext().getTechnologyAdapterControllerService();
		return tacService.getTechnologyAdapterController(FMLTechnologyAdapterController.class);
	}

	@Override
	public void show(final FlexoController controller, FlexoPerspective perspective) {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("Hop on recoit " + evt + " depuis " + evt.getSource());

		if (evt.getSource() == getRepresentedObject() && evt.getPropertyName().equals(getRepresentedObject().getDeletedProperty())) {
			System.out.println("On supprime la ModuleView");
			deleteModuleView();
		}
	}

	public FIBCompilationUnitBrowser getCompilationUnitBrowser() {
		FMLTechnologyAdapterController technologyAdapterController = (FMLTechnologyAdapterController) getFlexoController()
				.getTechnologyAdapterController(FMLTechnologyAdapter.class);
		return technologyAdapterController.getCompilationUnitBrowser();
	}

	/*public FIBView<?, ?> getFIBView(String componentName) {
		if (fibController != null) {
			return fibController.viewForComponent(componentName);
		}
		return null;
	}*/

	@Override
	public void willShow() {
		// getCompilationUnitBrowser().setCompilationUnit(getRepresentedObject().getDeclaringCompilationUnit());
		getPerspective().setBottomLeftView(getCompilationUnitBrowser());
		/*SwingUtilities.invokeLater(() -> {
			if (getFIBView("FlexoConceptBrowser") instanceof JFIBBrowserWidget) {
				JFIBBrowserWidget<FMLObject> browser = (JFIBBrowserWidget<FMLObject>) getFIBView("FlexoConceptBrowser");
				browser.performExpand(getRepresentedObject().getStructuralFacet());
				browser.performExpand(getRepresentedObject().getBehaviouralFacet());
				browser.performExpand(getRepresentedObject().getInnerConceptsFacet());
			}
		});*/

		// getPerspective().focusOnObject(getRepresentedObject());

	}

	@Override
	public void willHide() {
		// super.willHide();
		getPerspective().setBottomLeftView(null);
	}

	@Override
	public SelectionManager getSelectionManager() {
		if (getFlexoController() != null) {
			return getFlexoController().getSelectionManager();
		}
		return null;
	}

	@Override
	public Vector<FlexoObject> getSelection() {
		return getSelectionManager().getSelection();
	}

	@Override
	public void resetSelection() {
		getSelectionManager().resetSelection();
	}

	@Override
	public void addToSelected(FlexoObject object) {
		getSelectionManager().addToSelected(object);
	}

	@Override
	public void removeFromSelected(FlexoObject object) {
		getSelectionManager().removeFromSelected(object);
	}

	@Override
	public void addToSelected(Vector<? extends FlexoObject> objects) {
		getSelectionManager().addToSelected(objects);
	}

	@Override
	public void removeFromSelected(Vector<? extends FlexoObject> objects) {
		getSelectionManager().removeFromSelected(objects);
	}

	@Override
	public void setSelectedObjects(Vector<? extends FlexoObject> objects) {
		getSelectionManager().setSelectedObjects(objects);
	}

	@Override
	public FlexoObject getFocusedObject() {
		return getSelectionManager().getFocusedObject();
	}

	@Override
	public boolean mayRepresents(FlexoObject anObject) {
		return false;
	}

	@Override
	public List<SelectionListener> getSelectionListeners() {
		return Arrays.asList((SelectionListener) this);
	}

	@Override
	public void fireObjectSelected(FlexoObject object) {
		/*if (object instanceof FMLPrettyPrintable) {
			getEditor().clearHighlights();
			getEditor().highlightObject((FMLPrettyPrintable) object);
		}*/
	}

	@Override
	public void fireObjectDeselected(FlexoObject object) {
		/*if (object instanceof FMLPrettyPrintable) {
			getEditor().clearHighlights();
		}*/
	}

	@Override
	public void fireResetSelection() {
		// getEditor().clearHighlights();
	}

	@Override
	public void fireBeginMultipleSelection() {
		// TODO Auto-generated method stub
	}

	@Override
	public void fireEndMultipleSelection() {
		// TODO Auto-generated method stub
	}

}
