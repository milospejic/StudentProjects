package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JToggleButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrmTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultListModel<String> dlmBoje = new DefaultListModel<String>();

	private JLabel lblCrvena;
	private JLabel lblZuta;
	private JLabel lblPlava;
	private JTextField txtUnesiBoju;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmTest frame = new FrmTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		GridBagLayout gbl_pnlCenter = new GridBagLayout();
		gbl_pnlCenter.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_pnlCenter.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_pnlCenter.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlCenter.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		pnlCenter.setLayout(gbl_pnlCenter);

		JToggleButton tglbtnCrvena = new JToggleButton("Crvena boja");
		tglbtnCrvena.setPreferredSize(new Dimension(120,25));
		tglbtnCrvena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlmBoje.addElement(lblCrvena.getText());

				if(tglbtnCrvena.isSelected()) {
					lblCrvena.setForeground(Color.red);
					lblPlava.setForeground(Color.black);
					lblZuta.setForeground(Color.black);
				}
			}
		});
		
		JLabel lblIzaberiBoju = new JLabel("Izaberi boju:");
		GridBagConstraints gbc_lblIzaberiBoju = new GridBagConstraints();
		gbc_lblIzaberiBoju.anchor = GridBagConstraints.EAST;
		gbc_lblIzaberiBoju.insets = new Insets(0, 0, 5, 5);
		gbc_lblIzaberiBoju.gridx = 4;
		gbc_lblIzaberiBoju.gridy = 0;
		pnlCenter.add(lblIzaberiBoju, gbc_lblIzaberiBoju);
		
		JComboBox<String> cbxIzaberiBoju = new JComboBox<String>();
		cbxIzaberiBoju.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlmBoje.addElement(cbxIzaberiBoju.getSelectedItem().toString());
				
				switch(cbxIzaberiBoju.getSelectedItem().toString()) {
				case "Zelena":
					lblIzaberiBoju.setForeground(Color.green);
					break;
				case "Ljubicasta":
					lblIzaberiBoju.setForeground(Color.magenta);
					break;
				case "Narandzasta":
					lblIzaberiBoju.setForeground(Color.orange);
					break;
				}
			}
		});
		cbxIzaberiBoju.setModel(new DefaultComboBoxModel<String>(new String[] {"Zelena", "Narandzasta", "Ljubicasta"}));
		GridBagConstraints gbc_cbxIzaberiBoju = new GridBagConstraints();
		gbc_cbxIzaberiBoju.insets = new Insets(0, 0, 5, 0);
		gbc_cbxIzaberiBoju.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbxIzaberiBoju.gridx = 5;
		gbc_cbxIzaberiBoju.gridy = 0;
		pnlCenter.add(cbxIzaberiBoju, gbc_cbxIzaberiBoju);
		
		JLabel lblUnesiBoju = new JLabel("Unesi boju:");
		GridBagConstraints gbc_lblUnesiBoju = new GridBagConstraints();
		gbc_lblUnesiBoju.anchor = GridBagConstraints.EAST;
		gbc_lblUnesiBoju.insets = new Insets(0, 0, 5, 5);
		gbc_lblUnesiBoju.gridx = 4;
		gbc_lblUnesiBoju.gridy = 1;
		pnlCenter.add(lblUnesiBoju, gbc_lblUnesiBoju);
		
		txtUnesiBoju = new JTextField();
		txtUnesiBoju.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					dlmBoje.addElement(txtUnesiBoju.getText());
					txtUnesiBoju.setText("");
				}
			}
		});
		GridBagConstraints gbc_txtUnesiBoju = new GridBagConstraints();
		gbc_txtUnesiBoju.insets = new Insets(0, 0, 5, 0);
		gbc_txtUnesiBoju.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUnesiBoju.gridx = 5;
		gbc_txtUnesiBoju.gridy = 1;
		pnlCenter.add(txtUnesiBoju, gbc_txtUnesiBoju);
		txtUnesiBoju.setColumns(10);
		buttonGroup.add(tglbtnCrvena);

		GridBagConstraints gbc_tglbtnCrvena = new GridBagConstraints();
		gbc_tglbtnCrvena.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnCrvena.gridx = 0;
		gbc_tglbtnCrvena.gridy = 2;
		pnlCenter.add(tglbtnCrvena, gbc_tglbtnCrvena);

		lblCrvena = new JLabel("Crvena");
		GridBagConstraints gbc_lblCrvena = new GridBagConstraints();
		gbc_lblCrvena.anchor = GridBagConstraints.WEST;
		gbc_lblCrvena.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrvena.gridx = 1;
		gbc_lblCrvena.gridy = 2;
		pnlCenter.add(lblCrvena, gbc_lblCrvena);

		JToggleButton tglbtnPlava = new JToggleButton("Plava boja");
		tglbtnPlava.setPreferredSize(new Dimension(120,25));
		tglbtnPlava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlmBoje.addElement(lblPlava.getText());

				if(tglbtnPlava.isSelected()) {
					lblPlava.setForeground(Color.red);
					lblCrvena.setForeground(Color.black);
					lblZuta.setForeground(Color.black);
				}
			}
		});
		buttonGroup.add(tglbtnPlava);

		GridBagConstraints gbc_tglbtnPlava = new GridBagConstraints();
		gbc_tglbtnPlava.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnPlava.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnPlava.gridx = 0;
		gbc_tglbtnPlava.gridy = 3;
		pnlCenter.add(tglbtnPlava, gbc_tglbtnPlava);

		lblPlava = new JLabel("Plava");
		GridBagConstraints gbc_lblPlava = new GridBagConstraints();
		gbc_lblPlava.anchor = GridBagConstraints.WEST;
		gbc_lblPlava.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlava.gridx = 1;
		gbc_lblPlava.gridy = 3;
		pnlCenter.add(lblPlava, gbc_lblPlava);

		JToggleButton tglbtnZuta = new JToggleButton("Zuta boja");
		tglbtnZuta.setPreferredSize(new Dimension(120,25));
		tglbtnZuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlmBoje.addElement(lblZuta.getText());

				if(tglbtnZuta.isSelected()) {
					lblZuta.setForeground(Color.red);
					lblCrvena.setForeground(Color.black);
					lblPlava.setForeground(Color.black);
				}
			}
		});
		buttonGroup.add(tglbtnZuta);

		GridBagConstraints gbc_tglbtnZuta = new GridBagConstraints();
		gbc_tglbtnZuta.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnZuta.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnZuta.gridx = 0;
		gbc_tglbtnZuta.gridy = 4;
		pnlCenter.add(tglbtnZuta, gbc_tglbtnZuta);

		lblZuta = new JLabel("Zuta");
		GridBagConstraints gbc_lblZuta = new GridBagConstraints();
		gbc_lblZuta.insets = new Insets(0, 0, 5, 5);
		gbc_lblZuta.anchor = GridBagConstraints.WEST;
		gbc_lblZuta.gridx = 1;
		gbc_lblZuta.gridy = 4;
		pnlCenter.add(lblZuta, gbc_lblZuta);

		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 4;
		gbc_scrollPane.gridy = 5;
		pnlCenter.add(scrollPane, gbc_scrollPane);

		JList<String> lstBoje = new JList<String>();
		scrollPane.setViewportView(lstBoje);
		lstBoje.setModel(dlmBoje);

		
		JPanel pnlSouth = new JPanel();
		contentPane.add(pnlSouth, BorderLayout.SOUTH);

		JButton btnIspis = new JButton("Klikni me");
		btnIspis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Ovo je antistres dugme :)", "Poruka", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlSouth.add(btnIspis);
		
		JButton btnDodajBoju = new JButton("RGB");
		btnDodajBoju.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgTest dlgUnos = new DlgTest();
				dlgUnos.setVisible(true);
				
				if(dlgUnos.isOk) {
					dlmBoje.addElement(dlgUnos.txtCrvena.getText()+" "+dlgUnos.txtZelena.getText()+" "+dlgUnos.txtPlava.getText());
					pnlCenter.setBackground(new Color(Integer.parseInt(dlgUnos.txtCrvena.getText()),
							Integer.parseInt(dlgUnos.txtZelena.getText()),Integer.parseInt(dlgUnos.txtPlava.getText())));
				}
			}
		});
		pnlSouth.add(btnDodajBoju);
		
		JButton btnIzmenaBoje = new JButton("Izmena boje");
		btnIzmenaBoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DlgTest dlgIzmena = new DlgTest();
				try {
				String[] split = dlmBoje.getElementAt(lstBoje.getSelectedIndex()).split(" ");
				dlgIzmena.txtCrvena.setText(split[0]);
				dlgIzmena.txtZelena.setText(split[1]);
				dlgIzmena.txtPlava.setText(split[2]);
				dlgIzmena.setVisible(true);
				
				if(dlgIzmena.isOk){
					dlmBoje.removeElementAt(lstBoje.getSelectedIndex());
					dlmBoje.addElement(dlgIzmena.txtCrvena.getText() + " " + dlgIzmena.txtZelena.getText() + " "+ dlgIzmena.txtPlava.getText());
					pnlCenter.setBackground(new Color(Integer.parseInt(dlgIzmena.txtCrvena.getText()),Integer.parseInt(dlgIzmena.txtZelena.getText()),Integer.parseInt(dlgIzmena.txtPlava.getText())));	}
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Odaberite odgovarajucu boju!");
			}

		}
		});
		pnlSouth.add(btnIzmenaBoje);

		
		JPanel pnlNorth = new JPanel();
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNaslov = new JLabel("Zadatak 1");
		pnlNorth.add(lblNaslov);

		pnlNorth.setBackground(Color.green);

	}

}
