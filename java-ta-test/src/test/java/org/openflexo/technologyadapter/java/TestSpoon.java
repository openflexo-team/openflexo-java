package org.openflexo.technologyadapter.java;

import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;

public class TestSpoon {

	public static void main(String[] args) {
		SpoonAPI spoon = new Launcher();
		Resource resource = ResourceLocator.locateResource("TestResourceCenter/JavaCode");
		System.out.println("resource=" + resource + " of " + resource.getClass());
		Resource scResource = ResourceLocator.locateSourceCodeResource(resource);
		System.out.println("scResource=" + resource + " of " + scResource.getClass());
		if (resource instanceof FileResourceImpl) {
			// spoon.addInputResource(((FileResourceImpl) resource).getFile().getAbsolutePath());
			// spoon.addInputResource(
			// "/Users/sylvainguerin/GIT-2.99/openflexo-java/java-ta-test-rc/src/main/resources/TestResourceCenter/JavaCode");
			// spoon.addInputResource("/Users/sylvainguerin/GIT-2.99/openflexo-core/flexo-foundation/src/main/java");
			spoon.addInputResource("/Users/sylvainguerin/GIT-2.99/connie/connie-utils/src/main/java");
		}
		CtModel model = spoon.buildModel();
		for (CtPackage ctPackage : model.getAllPackages()) {
			System.out.println("Package: " + ctPackage);
			for (CtType<?> ctType : ctPackage.getTypes()) {
				System.out.println("> " + ctType.getQualifiedName());
				// System.out.println(
				// "fragment: " + ctType.getOriginalSourceFragment().getStart() + "-" + ctType.getOriginalSourceFragment().getEnd());
				/*System.out.println("Les methodes:");
				for (CtMethod<?> ctMethod : ctType.getMethods()) {
					System.out.println(">>> " + ctMethod.getShortRepresentation());
					// System.out.println("fragment: " + ctMethod.getOriginalSourceFragment().getStart() + "-"
					// + ctMethod.getOriginalSourceFragment().getEnd() + " : [" + ctMethod.getOriginalSourceFragment() + "]");
					// System.out.println("comment: " + ctMethod.getDocComment());
				
				}*/
			}
			/*for (CtElement ctElement : ctPackage.getElements(null)) {
				System.out.println("-----> " + ctElement + " of " + ctElement.getClass().getSimpleName());
			}*/
		}
	}
}
