package co.edu.unbosque.structure.tree;

public class FamilyTree {
	private Persona root; 

	public FamilyTree(Persona root) {
		this.root = root;
	}

	public void preOrder() {
		preOrderRec(root);
		System.out.println();
	}

	private void preOrderRec(Persona p) {
		if (p == null)
			return;
		System.out.print(p.nombre + " ");
		preOrderRec(p.madre);
		preOrderRec(p.padre);
	}

	public int depth() {
		return depthRec(root);
	}

	private int depthRec(Persona p) {
		if (p == null)
			return 0;
		int dm = depthRec(p.madre);
		int dp = depthRec(p.padre);
		return Math.max(dm, dp) + 1;
	}
}
