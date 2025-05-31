package vezbe11;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;


public class FrmIgraci extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultListModel<String> dlmIgraci = new DefaultListModel<String>();
	private JLabel lblMatic;
	private JLabel lblIvanovic;
	private JLabel lblKolarov;
	private JTextField textUnetiIgrac;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmIgraci frame = new FrmIgraci();
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
	public FrmIgraci() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBackground(Color.YELLOW);
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		GridBagLayout gbl_pnlCenter = new GridBagLayout();
		gbl_pnlCenter.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_pnlCenter.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_pnlCenter.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlCenter.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		pnlCenter.setLayout(gbl_pnlCenter);
		
		lblKolarov = new JLabel("Aleksandar Kolarov");
		GridBagConstraints gbc_lblKolarov = new GridBagConstraints();
		gbc_lblKolarov.insets = new Insets(0, 0, 5, 5);
		gbc_lblKolarov.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_lblKolarov.gridx = 0;
		gbc_lblKolarov.gridy = 0;
		pnlCenter.add(lblKolarov, gbc_lblKolarov);
		
		JToggleButton tglbtnKolarov = new JToggleButton("Kolarov");
		tglbtnKolarov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlmIgraci.addElement(lblKolarov.getText());
				if (tglbtnKolarov.isSelected()) {
					lblIvanovic.setForeground(Color.black);
					lblKolarov.setForeground(Color.blue);
					lblMatic.setForeground(Color.black);
				}
			}
		});
		GridBagConstraints gbc_tglbtnKolarov = new GridBagConstraints();
		gbc_tglbtnKolarov.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnKolarov.gridx = 1;
		gbc_tglbtnKolarov.gridy = 0;
		pnlCenter.add(tglbtnKolarov, gbc_tglbtnKolarov);
		buttonGroup.add(tglbtnKolarov);
		
		lblMatic = new JLabel("Nemanja Matic");
		GridBagConstraints gbc_lblMatic = new GridBagConstraints();
		gbc_lblMatic.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatic.gridx = 0;
		gbc_lblMatic.gridy = 1;
		pnlCenter.add(lblMatic, gbc_lblMatic);
		
		JToggleButton tglbtnMatic = new JToggleButton("Matic");
		tglbtnMatic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlmIgraci.addElement(lblMatic.getText());
				if (tglbtnMatic.isSelected()) {
					lblIvanovic.setForeground(Color.black);
					lblKolarov.setForeground(Color.black);
					lblMatic.setForeground(Color.blue);
				}
			}
		});
		GridBagConstraints gbc_tglbtnMatic = new GridBagConstraints();
		gbc_tglbtnMatic.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnMatic.gridx = 1;
		gbc_tglbtnMatic.gridy = 1;
		pnlCenter.add(tglbtnMatic, gbc_tglbtnMatic);
		buttonGroup.add(tglbtnMatic);
		
		lblIvanovic = new JLabel("Branislav Ivanovic");
		GridBagConstraints gbc_lblIvanovic = new GridBagConstraints();
		gbc_lblIvanovic.insets = new Insets(0, 0, 5, 5);
		gbc_lblIvanovic.gridx = 0;
		gbc_lblIvanovic.gridy = 2;
		pnlCenter.add(lblIvanovic, gbc_lblIvanovic);
		
		JToggleButton tglbtnIvanovic = new JToggleButton("Ivanovic");
		tglbtnIvanovic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlmIgraci.addElement(lblIvanovic.getText());

				if (tglbtnIvanovic.isSelected()) {
					lblIvanovic.setForeground(Color.blue);
					lblKolarov.setForeground(Color.black);
					lblMatic.setForeground(Color.black);
				}
			}
		});
		GridBagConstraints gbc_tglbtnIvanovic = new GridBagConstraints();
		gbc_tglbtnIvanovic.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnIvanovic.gridx = 1;
		gbc_tglbtnIvanovic.gridy = 2;
		pnlCenter.add(tglbtnIvanovic, gbc_tglbtnIvanovic);
		buttonGroup.add(tglbtnIvanovic);
		
		JCheckBox chckbxUnosIgraca = new JCheckBox("Unesi igraca");
		chckbxUnosIgraca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( chckbxUnosIgraca.isSelected()) {
					textUnetiIgrac.setEnabled(true);
				}else {
					textUnetiIgrac.setText("");
					textUnetiIgrac.setEnabled(false);
				}
			}
		});
		chckbxUnosIgraca.setBackground(Color.YELLOW);
		GridBagConstraints gbc_chckbxUnosIgraca = new GridBagConstraints();
		gbc_chckbxUnosIgraca.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxUnosIgraca.gridx = 0;
		gbc_chckbxUnosIgraca.gridy = 3;
		pnlCenter.add(chckbxUnosIgraca, gbc_chckbxUnosIgraca);
		
		textUnetiIgrac = new JTextField();
		textUnetiIgrac.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					dlmIgraci.addElement(textUnetiIgrac.getText());
					textUnetiIgrac.setText("");
				}
			}
		});
		textUnetiIgrac.setEnabled(false);
		GridBagConstraints gbc_textUnetiIgrac = new GridBagConstraints();
		gbc_textUnetiIgrac.insets = new Insets(0, 0, 5, 5);
		gbc_textUnetiIgrac.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUnetiIgrac.gridx = 1;
		gbc_textUnetiIgrac.gridy = 3;
		pnlCenter.add(textUnetiIgrac, gbc_textUnetiIgrac);
		textUnetiIgrac.setColumns(10);
		
		JLabel lblIgraci = new JLabel("Igraci:");
		GridBagConstraints gbc_lblIgraci = new GridBagConstraints();
		gbc_lblIgraci.anchor = GridBagConstraints.EAST;
		gbc_lblIgraci.insets = new Insets(0, 0, 5, 5);
		gbc_lblIgraci.gridx = 0;
		gbc_lblIgraci.gridy = 4;
		pnlCenter.add(lblIgraci, gbc_lblIgraci);
		
		JComboBox<String> cbxIgraci = new JComboBox<String>();
		cbxIgraci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlmIgraci.addElement(cbxIgraci.getSelectedItem().toString());
			}
		});
		cbxIgraci.setModel(new DefaultComboBoxModel<String>(new String[] {"Kolarov", "Matic", "Ivanovic", "Nikolic", "Markovic", "Peric"}));
		GridBagConstraints gbc_cbxIgraci = new GridBagConstraints();
		gbc_cbxIgraci.insets = new Insets(0, 0, 5, 5);
		gbc_cbxIgraci.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbxIgraci.gridx = 1;
		gbc_cbxIgraci.gridy = 4;
		pnlCenter.add(cbxIgraci, gbc_cbxIgraci);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 4;
		gbc_scrollPane.gridy = 5;
		pnlCenter.add(scrollPane, gbc_scrollPane);
		
		JList<String> lstIgraci = new JList<String>();
		scrollPane.setViewportView(lstIgraci);
		lstIgraci.setModel(dlmIgraci);
		
		JPanel pnlNorth = new JPanel();
		pnlNorth.setBackground(Color.RED);
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNaslov = new JLabel("Forma za unos igraca");
		lblNaslov.setForeground(Color.WHITE);
		pnlNorth.add(lblNaslov);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.setBackground(Color.RED);
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		
		JButton btnDodajIgraca = new JButton("Dodaj igraca");
		btnDodajIgraca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgIgraci dlgUnos = new DlgIgraci();
				dlgUnos.setVisible(true);
				
				if(dlgUnos.isOk) {
					dlmIgraci.addElement(dlgUnos.txtImeIgraca.getText()+" "+dlgUnos.txtPrezimeIgraca.getText());
					
				}
			}
		});
		pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlSouth.add(btnDodajIgraca);
		
		JButton btnModifikujIgraca = new JButton("Modifikuj igraca");
		btnModifikujIgraca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgIgraci dlgIzmena = new DlgIgraci();
				try {
				String[] split = dlmIgraci.getElementAt(lstIgraci.getSelectedIndex()).split(" ");
				dlgIzmena.txtImeIgraca.setText(split[0]);
				dlgIzmena.txtPrezimeIgraca.setText(split[1]);
				dlgIzmena.setVisible(true);
				
				if(dlgIzmena.isOk){
					dlmIgraci.removeElementAt(lstIgraci.getSelectedIndex());
					dlmIgraci.addElement(dlgIzmena.txtImeIgraca.getText()+" "+dlgIzmena.txtPrezimeIgraca.getText());
				}
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Morate selektovati igraca kojem je uneto i ime i prezime");
			}
			}
		});
		pnlSouth.add(btnModifikujIgraca);
	}

}
