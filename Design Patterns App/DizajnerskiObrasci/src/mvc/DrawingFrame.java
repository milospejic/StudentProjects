package mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import geometry.Rectangle;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Insets;	
import java.awt.GridLayout;
import javax.swing.ScrollPaneConstants;

public class DrawingFrame extends JFrame{
	
	private DrawingController controller;
	private DrawingView view = new DrawingView();
	
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnCircle;
	
	private JButton btnDraw;
	private JButton btnModify;
	private JButton btnDelete;
	private JButton btnSelect;
	
	private final ButtonGroup buttonGroupShapes = new ButtonGroup();
	private final ButtonGroup buttonGroupOperations = new ButtonGroup();
	private JLabel lblMode;
	private JToggleButton tglbtnHexagon;
	private JButton btnUndo;
	private JButton btnRedo;
	private JPanel panelEast;
	private JPanel panelEast2;
	private JScrollPane scrollPane;
	private JList<String> list;
	private JButton btnSaveLog;
	private JButton btnLoadLog;
	private JButton btnNextStep;
	private JButton btnSaveDrawing;
	private JButton btnLoadDrawing;
	private JPanel panelWest;
	private JButton btnBorderColor;
	private JButton btnInnerColor;
	private JButton btnToFront;
	private JButton btnToBack;
	private JButton btnBringToFront;
	private JButton btnBringToBack;
	private JLabel lblColor;
	private JLabel lblPosition;
	
	public DrawingFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				controller.mouseClicked(e);
				
			}
		});
		
		getContentPane().add(view, BorderLayout.CENTER);
		
		
		
		JPanel pnlSouth = new JPanel();
		getContentPane().add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tglbtnPoint = new JToggleButton("Point");
		pnlSouth.add(tglbtnPoint);
		buttonGroupShapes.add(tglbtnPoint);
		tglbtnPoint.setEnabled(false);
		
		tglbtnLine = new JToggleButton("Line");
		pnlSouth.add(tglbtnLine);
		buttonGroupShapes.add(tglbtnLine);
		tglbtnLine.setEnabled(false);
		
		tglbtnCircle = new JToggleButton("Circle");
		pnlSouth.add(tglbtnCircle);
		buttonGroupShapes.add(tglbtnCircle);
		tglbtnCircle.setEnabled(false);
		
		tglbtnRectangle = new JToggleButton("Rectangle");
		pnlSouth.add(tglbtnRectangle);
		buttonGroupShapes.add(tglbtnRectangle);
		tglbtnRectangle.setEnabled(false);
		
		tglbtnDonut = new JToggleButton("Donut");
		pnlSouth.add(tglbtnDonut);
		buttonGroupShapes.add(tglbtnDonut);
		tglbtnDonut.setEnabled(false);
		
		tglbtnHexagon = new JToggleButton("Hexagon");
		pnlSouth.add(tglbtnHexagon);
		buttonGroupShapes.add(tglbtnHexagon);
		tglbtnHexagon.setEnabled(false);
		
		JPanel pnlNorth = new JPanel();
		getContentPane().add(pnlNorth, BorderLayout.NORTH);
		
		lblMode = new JLabel("Neutral");
		pnlNorth.add(lblMode);
		
		btnSelect = new JButton("Select");
		pnlNorth.add(btnSelect);
		buttonGroupOperations.add(btnSelect);
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clickOnSelect();
			}
		});
		btnSelect.setEnabled(false);
		
		btnDraw = new JButton("Draw");
		pnlNorth.add(btnDraw);
		buttonGroupOperations.add(btnDraw);
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				controller.clickOnDraw();
			}
		});
		
		btnModify = new JButton("Modify");
		pnlNorth.add(btnModify);
		buttonGroupOperations.add(btnModify);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modify();
				
			}
		});
		btnModify.setEnabled(false);
		
		btnDelete = new JButton("Delete");
		pnlNorth.add(btnDelete);
		buttonGroupOperations.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
			}
		});
		btnDelete.setEnabled(false);
		
		btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clickOnUndo();
			}
		});
		pnlNorth.add(btnUndo);
		btnUndo.setEnabled(false);
		
		btnRedo = new JButton("Redo");
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clickOnRedo();
			}
		});
		pnlNorth.add(btnRedo);
		btnRedo.setEnabled(false);
		
		panelEast = new JPanel();
		getContentPane().add(panelEast, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{120,120};
		gbl_panel.rowHeights = new int[]{130, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelEast.setLayout(gbl_panel);
		
		panelEast2 = new JPanel();
		GridBagConstraints gbc_panelEast2 = new GridBagConstraints();
		gbc_panelEast2.gridwidth = 2;
		gbc_panelEast2.insets = new Insets(0, 0, 5, 0);
		gbc_panelEast2.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelEast2.gridx = 0;
		gbc_panelEast2.gridy = 0;
		panelEast.add(panelEast2, gbc_panelEast2);
		GridBagLayout gbl_panelEast2 = new GridBagLayout();
		gbl_panelEast2.columnWidths = new int[]{0, 0};
		gbl_panelEast2.rowHeights = new int[]{0, 0};
		gbl_panelEast2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelEast2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelEast2.setLayout(gbl_panelEast2);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelEast2.add(scrollPane, gbc_scrollPane);
		//panel_1.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(260, 150));
		list = new JList<String>();
		scrollPane.setViewportView(list);
		
		
		
		btnSaveLog = new JButton("Save Log");
		btnSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		GridBagConstraints gbc_btnSaveLog = new GridBagConstraints();
		gbc_btnSaveLog.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveLog.gridx = 0;
		gbc_btnSaveLog.gridy = 1;
		panelEast.add(btnSaveLog, gbc_btnSaveLog);
		
		btnSaveDrawing = new JButton("Save Drawing");
		btnSaveDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		GridBagConstraints gbc_btnSaveDrawing = new GridBagConstraints();
		gbc_btnSaveDrawing.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveDrawing.gridx = 1;
		gbc_btnSaveDrawing.gridy = 1;
		panelEast.add(btnSaveDrawing, gbc_btnSaveDrawing);
		
		btnLoadLog = new JButton("Load Log");
		btnLoadLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadLog();
			}
		});
		GridBagConstraints gbc_btnLoadLog = new GridBagConstraints();
		gbc_btnLoadLog.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadLog.gridx = 0;
		gbc_btnLoadLog.gridy = 2;
		panelEast.add(btnLoadLog, gbc_btnLoadLog);
		
		btnLoadDrawing = new JButton("Load Drawing");
		btnLoadDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadDrawing();
			}
		});
		GridBagConstraints gbc_btnLoadDrawing = new GridBagConstraints();
		gbc_btnLoadDrawing.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadDrawing.gridx = 1;
		gbc_btnLoadDrawing.gridy = 2;
		panelEast.add(btnLoadDrawing, gbc_btnLoadDrawing);
		
		btnNextStep = new JButton("Next Step");
		btnNextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.nextStep();
			}
		});
		GridBagConstraints gbc_btnNextStep = new GridBagConstraints();
		gbc_btnNextStep.insets = new Insets(0, 0, 0, 5);
		gbc_btnNextStep.gridx = 0;
		gbc_btnNextStep.gridy = 3;
		panelEast.add(btnNextStep, gbc_btnNextStep);
		btnNextStep.setEnabled(false);
		
		panelWest = new JPanel();
		getContentPane().add(panelWest, BorderLayout.WEST);
		GridBagLayout gbl_panelWest = new GridBagLayout();
		gbl_panelWest.columnWidths = new int[]{0, 0};
		gbl_panelWest.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelWest.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelWest.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelWest.setLayout(gbl_panelWest);
		
		lblColor = new JLabel("Color");
		GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.insets = new Insets(0, 0, 5, 0);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 0;
		panelWest.add(lblColor, gbc_lblColor);
		
		btnBorderColor = new JButton("Border Color");
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseBorderColor();
			}
		});
		GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
		gbc_btnBorderColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnBorderColor.gridx = 0;
		gbc_btnBorderColor.gridy = 1;
		panelWest.add(btnBorderColor, gbc_btnBorderColor);
		
		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseInnerColor();
			}
		});
		GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
		gbc_btnInnerColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnInnerColor.gridx = 0;
		gbc_btnInnerColor.gridy = 2;
		panelWest.add(btnInnerColor, gbc_btnInnerColor);
		
		lblPosition = new JLabel("Position");
		GridBagConstraints gbc_lblPosition = new GridBagConstraints();
		gbc_lblPosition.insets = new Insets(0, 0, 5, 0);
		gbc_lblPosition.gridx = 0;
		gbc_lblPosition.gridy = 3;
		panelWest.add(lblPosition, gbc_lblPosition);
		
		btnToFront = new JButton("To Front");
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clickOnToFront();
			}
		});
		GridBagConstraints gbc_btnToFront = new GridBagConstraints();
		gbc_btnToFront.insets = new Insets(0, 0, 5, 0);
		gbc_btnToFront.gridx = 0;
		gbc_btnToFront.gridy = 4;
		panelWest.add(btnToFront, gbc_btnToFront);
		btnToFront.setEnabled(false);
		
		btnToBack = new JButton("To Back");
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clickOnToBack();
			}
		});
		GridBagConstraints gbc_btnToBack = new GridBagConstraints();
		gbc_btnToBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnToBack.gridx = 0;
		gbc_btnToBack.gridy = 5;
		panelWest.add(btnToBack, gbc_btnToBack);
		btnToBack.setEnabled(false);
		
		btnBringToFront = new JButton("Bring To Front");
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clickOnBringToFront();
			}
		});
		GridBagConstraints gbc_btnBringToFront = new GridBagConstraints();
		gbc_btnBringToFront.insets = new Insets(0, 0, 5, 0);
		gbc_btnBringToFront.gridx = 0;
		gbc_btnBringToFront.gridy = 6;
		panelWest.add(btnBringToFront, gbc_btnBringToFront);
		btnBringToFront.setEnabled(false);
		
		btnBringToBack = new JButton("Bring To Back");
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clickOnBringToBack();
			}
		});
		GridBagConstraints gbc_btnBringToBack = new GridBagConstraints();
		gbc_btnBringToBack.gridx = 0;
		gbc_btnBringToBack.gridy = 7;
		panelWest.add(btnBringToBack, gbc_btnBringToBack);
		btnBringToBack.setEnabled(false);
		
		
	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	
	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public JButton getBtnDraw() {
		return btnDraw;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnSelect() {
		return btnSelect;
	}

	public JLabel getLblMode() {
		return lblMode;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JList<String> getList() {
		return list;
	}

	public JButton getBtnSaveLog() {
		return btnSaveLog;
	}

	public JButton getBtnLoadLog() {
		return btnLoadLog;
	}

	public JButton getBtnSaveDrawing() {
		return btnSaveDrawing;
	}

	public JButton getBtnLoadDrawing() {
		return btnLoadDrawing;
	}

	public JButton getBtnNextStep() {
		return btnNextStep;
	}

	public JPanel getPanelEast() {
		return panelEast;
	}

	public JPanel getPanel_1() {
		return panelEast2;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JPanel getPanel_2() {
		return panelWest;
	}

	public JButton getBtnBorderColor() {
		return btnBorderColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}


	
}

