import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class TesteQualquer extends JFrame {
	private static final long serialVersionUID = 1L;

	public TesteQualquer() {
		super("Só Um Teste Qualquer");
		TesteComponente x = new TesteComponente(640, 480, 320, 240);
		JScrollPane scrollPane = new JScrollPane(x);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scrollPane.setPreferredSize(new Dimension(320, 240));
		getContentPane().add(scrollPane);
		//getContentPane().add(x);
		x.add(new JButton("xxx"), BorderLayout.SOUTH);
		pack();
		scrollPane.getViewport();
	}

	public static void main(String[] args) {
		TesteQualquer teste = new TesteQualquer();
		teste.setVisible(true);
		teste.repaint();
	}
}
