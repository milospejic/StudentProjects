package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Line;
import geometry.Point;
import mvc.DrawingController;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LineDialog extends JDialog {

	/**
	 * 
	 */
	private final JPanel contentPanel = new JPanel();
	private JTextField txtX1;
	private JTextField txtY1;
	private JTextField txtX2;
	private JTextField txtY2;
	public boolean isOk;
	private Line line;
	private Color color;
	private DrawingController controller;
	private JButton btnColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LineDialog dialog = new LineDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public LineDialog() {
		setBounds(100, 100, 450, 300);
		setModal(true);
		setTitle("Line");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblX1 = new JLabel("X1: ");
			GridBagConstraints gbc_lblX1 = new GridBagConstraints();
			gbc_lblX1.anchor = GridBagConstraints.EAST;
			gbc_lblX1.insets = new Insets(0, 0, 5, 5);
			gbc_lblX1.gridx = 4;
			gbc_lblX1.gridy = 1;
			contentPanel.add(lblX1, gbc_lblX1);
		}
		{
			txtX1 = new JTextField();
			txtX1.setText("");
			GridBagConstraints gbc_txtX1 = new GridBagConstraints();
			gbc_txtX1.insets = new Insets(0, 0, 5, 0);
			gbc_txtX1.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtX1.gridx = 5;
			gbc_txtX1.gridy = 1;
			contentPanel.add(txtX1, gbc_txtX1);
			txtX1.setColumns(10);
		}
		{
			JLabel lblY1 = new JLabel("Y1: ");
			GridBagConstraints gbc_lblY1 = new GridBagConstraints();
			gbc_lblY1.anchor = GridBagConstraints.EAST;
			gbc_lblY1.insets = new Insets(0, 0, 5, 5);
			gbc_lblY1.gridx = 4;
			gbc_lblY1.gridy = 2;
			contentPanel.add(lblY1, gbc_lblY1);
		}
		{
			txtY1 = new JTextField();
			txtY1.setText("");
			GridBagConstraints gbc_txtY1 = new GridBagConstraints();
			gbc_txtY1.insets = new Insets(0, 0, 5, 0);
			gbc_txtY1.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtY1.gridx = 5;
			gbc_txtY1.gridy = 2;
			contentPanel.add(txtY1, gbc_txtY1);
			txtY1.setColumns(10);
		}
		{
			JLabel lblX2 = new JLabel("X2: ");
			GridBagConstraints gbc_lblX2 = new GridBagConstraints();
			gbc_lblX2.anchor = GridBagConstraints.EAST;
			gbc_lblX2.insets = new Insets(0, 0, 5, 5);
			gbc_lblX2.gridx = 4;
			gbc_lblX2.gridy = 3;
			contentPanel.add(lblX2, gbc_lblX2);
		}
		{
			txtX2 = new JTextField();
			txtX2.setText("");
			GridBagConstraints gbc_txtX2 = new GridBagConstraints();
			gbc_txtX2.insets = new Insets(0, 0, 5, 0);
			gbc_txtX2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtX2.gridx = 5;
			gbc_txtX2.gridy = 3;
			contentPanel.add(txtX2, gbc_txtX2);
			txtX2.setColumns(10);
		}
		{
			JLabel lblY2 = new JLabel("Y2: ");
			GridBagConstraints gbc_lblY2 = new GridBagConstraints();
			gbc_lblY2.anchor = GridBagConstraints.EAST;
			gbc_lblY2.insets = new Insets(0, 0, 5, 5);
			gbc_lblY2.gridx = 4;
			gbc_lblY2.gridy = 4;
			contentPanel.add(lblY2, gbc_lblY2);
		}
		{
			txtY2 = new JTextField();
			txtY2.setText("");
			GridBagConstraints gbc_txtY2 = new GridBagConstraints();
			gbc_txtY2.insets = new Insets(0, 0, 5, 0);
			gbc_txtY2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtY2.gridx = 5;
			gbc_txtY2.gridy = 4;
			contentPanel.add(txtY2, gbc_txtY2);
			txtY2.setColumns(10);
		}
		{
			btnColor = new JButton("Color");
			btnColor.setBackground(color);
			btnColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					color = JColorChooser.showDialog(null, "Choose a color", color);	
					btnColor.setBackground(color);

				}
			});
			GridBagConstraints gbc_btnColor = new GridBagConstraints();
			gbc_btnColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnColor.gridx = 4;
			gbc_btnColor.gridy = 5;
			contentPanel.add(btnColor, gbc_btnColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controller.lineOk();
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
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public void setLine(Line line) {
		txtX1.setText("" + line.getStartPoint().getX());
		txtY1.setText("" + line.getStartPoint().getY());
		txtX2.setText("" + line.getEndPoint().getX());
		txtY2.setText("" + line.getEndPoint().getY());
		color = line.getColor();
		btnColor.setBackground(color);
		this.line = line;
				
	}
	public Line getLine() {
		return line;
	}

	public JTextField getTxtX1() {
		return txtX1;
	}

	public JTextField getTxtY1() {
		return txtY1;
	}

	public JTextField getTxtX2() {
		return txtX2;
	}

	public JTextField getTxtY2() {
		return txtY2;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		btnColor.setBackground(color);
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JButton getBtnColor() {
		return btnColor;
	}
	
	
	
}
