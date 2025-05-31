package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

public class DlgTest extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	protected JTextField txtCrvena;
	protected JTextField txtZelena;
	protected JTextField txtPlava;
	protected boolean isOk;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgTest dialog = new DlgTest();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgTest() {
		setBounds(100, 100, 450, 300);
		setTitle("RGB");
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCrvena = new JLabel("R:");
			GridBagConstraints gbc_lblCrvena = new GridBagConstraints();
			gbc_lblCrvena.anchor = GridBagConstraints.EAST;
			gbc_lblCrvena.insets = new Insets(0, 0, 5, 5);
			gbc_lblCrvena.gridx = 0;
			gbc_lblCrvena.gridy = 1;
			contentPanel.add(lblCrvena, gbc_lblCrvena);
		}
		{
			txtCrvena = new JTextField();
			GridBagConstraints gbc_txtCrvena = new GridBagConstraints();
			gbc_txtCrvena.insets = new Insets(0, 0, 5, 0);
			gbc_txtCrvena.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCrvena.gridx = 1;
			gbc_txtCrvena.gridy = 1;
			contentPanel.add(txtCrvena, gbc_txtCrvena);
			txtCrvena.setColumns(10);
		}
		{
			JLabel lblZelena = new JLabel("G:");
			GridBagConstraints gbc_lblZelena = new GridBagConstraints();
			gbc_lblZelena.anchor = GridBagConstraints.EAST;
			gbc_lblZelena.insets = new Insets(0, 0, 5, 5);
			gbc_lblZelena.gridx = 0;
			gbc_lblZelena.gridy = 2;
			contentPanel.add(lblZelena, gbc_lblZelena);
		}
		{
			txtZelena = new JTextField();
			GridBagConstraints gbc_txtZelena = new GridBagConstraints();
			gbc_txtZelena.insets = new Insets(0, 0, 5, 0);
			gbc_txtZelena.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtZelena.gridx = 1;
			gbc_txtZelena.gridy = 2;
			contentPanel.add(txtZelena, gbc_txtZelena);
			txtZelena.setColumns(10);
		}
		{
			JLabel lblPlava = new JLabel("B:");
			GridBagConstraints gbc_lblPlava = new GridBagConstraints();
			gbc_lblPlava.anchor = GridBagConstraints.EAST;
			gbc_lblPlava.insets = new Insets(0, 0, 0, 5);
			gbc_lblPlava.gridx = 0;
			gbc_lblPlava.gridy = 3;
			contentPanel.add(lblPlava, gbc_lblPlava);
		}
		{
			txtPlava = new JTextField();
			GridBagConstraints gbc_txtPlava = new GridBagConstraints();
			gbc_txtPlava.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPlava.gridx = 1;
			gbc_txtPlava.gridy = 3;
			contentPanel.add(txtPlava, gbc_txtPlava);
			txtPlava.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						isOk = true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						isOk = false;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
