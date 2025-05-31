package vezbe11;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgIgraci extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	protected JTextField txtImeIgraca;
	protected JTextField txtPrezimeIgraca;
	protected boolean isOk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgIgraci dialog = new DlgIgraci();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgIgraci() {
		setBounds(100, 100, 450, 300);
		setTitle("Unesite igraca");
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.RED);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblImeIgraca = new JLabel("Ime igraca:");
			GridBagConstraints gbc_lblImeIgraca = new GridBagConstraints();
			gbc_lblImeIgraca.insets = new Insets(0, 0, 5, 5);
			gbc_lblImeIgraca.anchor = GridBagConstraints.EAST;
			gbc_lblImeIgraca.gridx = 0;
			gbc_lblImeIgraca.gridy = 1;
			contentPanel.add(lblImeIgraca, gbc_lblImeIgraca);
		}
		{
			txtImeIgraca = new JTextField();
			GridBagConstraints gbc_txtImeIgraca = new GridBagConstraints();
			gbc_txtImeIgraca.insets = new Insets(0, 0, 5, 5);
			gbc_txtImeIgraca.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtImeIgraca.gridx = 1;
			gbc_txtImeIgraca.gridy = 1;
			contentPanel.add(txtImeIgraca, gbc_txtImeIgraca);
			txtImeIgraca.setColumns(10);
		}
		{
			JLabel lblPrezimeIgraca = new JLabel("Prezime igraca:");
			GridBagConstraints gbc_lblPrezimeIgraca = new GridBagConstraints();
			gbc_lblPrezimeIgraca.anchor = GridBagConstraints.EAST;
			gbc_lblPrezimeIgraca.insets = new Insets(0, 0, 5, 5);
			gbc_lblPrezimeIgraca.gridx = 2;
			gbc_lblPrezimeIgraca.gridy = 1;
			contentPanel.add(lblPrezimeIgraca, gbc_lblPrezimeIgraca);
		}
		{
			txtPrezimeIgraca = new JTextField();
			GridBagConstraints gbc_txtPrezimeIgraca = new GridBagConstraints();
			gbc_txtPrezimeIgraca.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPrezimeIgraca.anchor = GridBagConstraints.NORTH;
			gbc_txtPrezimeIgraca.insets = new Insets(0, 0, 5, 5);
			gbc_txtPrezimeIgraca.gridx = 3;
			gbc_txtPrezimeIgraca.gridy = 1;
			contentPanel.add(txtPrezimeIgraca, gbc_txtPrezimeIgraca);
			txtPrezimeIgraca.setColumns(10);
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
						isOk=false;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
