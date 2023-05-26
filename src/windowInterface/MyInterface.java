package windowInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import backend.Simulator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MyInterface extends JFrame {

	private static final long serialVersionUID = -6840815447618468846L;
	private JPanel contentPane;
	private JLabel stepLabel;
	private JLabel borderLabel;
	private JLabel borderLabelBorders;
	private JLabel speedLabel;
	private JPanelDraw panelDraw;
	private Simulator mySimu = null;
	private JSlider randSlider;
	private JSlider speedSlider;
	private String gameType = "Game of life";
	private boolean behavior = true;
	private boolean looping = true;

	/**
	 * Create the frame.
	 */
	public MyInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panelTop = new JPanel();
		contentPane.add(panelTop, BorderLayout.NORTH);

		JPanel panelRight = new JPanel();
		panelRight.setLayout(new GridLayout(10, 1));
		contentPane.add(panelRight, BorderLayout.EAST);

		JButton btnGo = new JButton("Start/Pause");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicButtonGo();
			}
		});
		panelTop.add(btnGo);

//		JButton btnPause = new JButton("Toggle Pause");
//		btnPause.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				clicButtonPause();
//			}
//		});
//		panelTop.add(btnPause);

		stepLabel = new JLabel("Step : X");
		panelTop.add(stepLabel);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicButtonStop();
			}
		});
		panelTop.add(btnStop);

		speedLabel = new JLabel("speed slider : ");
		panelTop.add(speedLabel);

		speedSlider = new JSlider();
		speedSlider.setValue(3);
		speedSlider.setMinimum(0);
		speedSlider.setMaximum(10);
		speedSlider.setOrientation(SwingConstants.HORIZONTAL);
		speedSlider.setPreferredSize(new Dimension(100, 30));
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				changeSpeed();
			}
		});
		panelTop.add(speedSlider);

//		JButton btnSpeed = new JButton("Set Speed");
//		btnSpeed.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				clicButtonSpeed();
//			}
//		});
//		panelTop.add(btnSpeed);

		JButton btnLoad = new JButton("Load File");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicLoadFileButton();
			}
		});
		panelRight.add(btnLoad);

		JButton btnSave = new JButton("Save To File");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicSaveToFileButton();
			}
		});
		panelRight.add(btnSave);

		JButton btnRandGen = new JButton("Random");
		btnRandGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateRandomBoard();
			}
		});
		panelRight.add(btnRandGen);

		JLabel randLabel = new JLabel("random density slider :");
		panelRight.add(randLabel);

		randSlider = new JSlider();
		randSlider.setValue(50);
		randSlider.setMinimum(0);
		randSlider.setMaximum(100);
		randSlider.setPreferredSize(new Dimension(30, 200));
		panelRight.add(randSlider);

		JButton btnBorder = new JButton("Toggle Border");
		btnBorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicButtonBorder();
			}
		});
		panelRight.add(btnBorder);

		borderLabel = new JLabel("border : loop");
		panelRight.add(borderLabel);
		
		JButton btnGridBehavior = new JButton("Grid behavior");
		btnGridBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicButtonBehavior();
			}
		});
		panelRight.add(btnGridBehavior);
		
		borderLabelBorders = new JLabel("border : expanding");
		panelRight.add(borderLabelBorders);

		panelDraw = new JPanelDraw(this);
		contentPane.add(panelDraw, BorderLayout.CENTER);
		
		
		String[] games = new String[] {"Game of life", "High Life", "Labyrinthes", "Exploding with chaos", "replicating paterns", "day and night"};
		 
		// create a combo box with the fixed array:
		JComboBox<String> comboGames = new JComboBox<String>(games);
		comboGames.setSelectedIndex(0);
		
		comboGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
		        String selectedGame = (String) comboGames.getSelectedItem();
		        mySimu.setTypeOfGame(selectedGame);
		        gameType = selectedGame;
			}
			
		});
		panelRight.add(comboGames);
		
		
		
		
	}

	public void setStepBanner(String s) {
		stepLabel.setText(s);
	}

	public void setBorderBanner(String s) {
		borderLabel.setText(s);
	}

	public JPanelDraw getPanelDessin() {
		return panelDraw;
	}
	
	public String getGameType() {
		return gameType;
	}
	
	public boolean getLooping() {
		return looping;
	}
	
	public boolean getBehavior() {
		return behavior;
	}

	/**
	 * Create a new simulator if none exists
	 * set it in the panelDraw
	 */
	public void instantiateSimu() {
		if (mySimu == null) {
			mySimu = new Simulator(this);
			panelDraw.setSimu(mySimu);
		}
	}
	
	///////////HERE IS THE BUTTONS ACTIONS///////////

	public void clicButtonGo() {
		this.instantiateSimu();
		if (!mySimu.isAlive()) {
			mySimu.start();
		} else {
			mySimu.togglePause();
		}
	}

	public void clicButtonStop() {
		if (mySimu != null) {
			panelDraw.setSimu(null);
			mySimu.stopSimu();
			mySimu = null;
			this.eraseLabels();
			panelDraw.repaint();
		}
	}
	
	public void clicButtonBehavior() {
		if (mySimu != null) {
			mySimu.toggleBehavior();
			borderLabelBorders.setText("border : " + (mySimu.isExpanding() ? "expanding" : "fixed"));
		} else {
			behavior = !behavior;
			borderLabelBorders.setText("border : " + (behavior ? "expanding" : "fixed"));
		}
	}

//	public void clicButtonPause() {
//		if(mySimu != null) {
//			mySimu.togglePause();
//		}
//	}

	public void clicButtonBorder() {
		if (mySimu != null) {
			mySimu.toggleLoopingBorder();
			borderLabel.setText("border : " + (mySimu.isLoopingBorder() ? "loop" : "closed"));
		} else {
			looping = !looping;
			borderLabel.setText("border : " + (looping ? "loop" : "closed"));
		}
	}

	public void generateRandomBoard() {
		this.instantiateSimu();
		float chanceOfLife = ((float) randSlider.getValue()) / ((float) randSlider.getMaximum());
		mySimu.generateRandom(chanceOfLife);
		panelDraw.repaint();
	}

	public void changeSpeed() {
		if (mySimu != null) {
			int delay = (int) Math.pow(2, 10 - speedSlider.getValue());
			mySimu.setLoopDelay(delay);
		} else {
			speedSlider.setValue(3);
		}
	}

	public void clicLoadFileButton() {
		Simulator loadedSim = new Simulator(this);
		String fileName = SelectFile();
		if (fileName.length() > 0) {
			try {
				BufferedReader fileContent = new BufferedReader(new FileReader(fileName));
				String line = fileContent.readLine();
				int y = 0;
				while (line != null) {
					loadedSim.populateLine(y, line);
					y++;
					line = fileContent.readLine();
				}
				fileContent.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (mySimu != null) {
				mySimu.stopSimu();
				this.eraseLabels();
			}
			mySimu = loadedSim;
			panelDraw.setSimu(mySimu);
			this.repaint();
		}
	}

	public void clicSaveToFileButton() {
		String fileName = SelectFile();
		if (fileName.length() > 0) {
			String[] content = mySimu.getFileRepresentation();
			writeFile(fileName, content);
		}
	}
	

	public String SelectFile() {
		String s;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose a file");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(true);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			s = chooser.getSelectedFile().toString();
		} else {
			System.out.println("No Selection ");
			s = "";
		}
		return s;
	}

	public void writeFile(String fileName, String[] content) {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(fileName);
			for (String row : content) {
				csvWriter.append(row);
				csvWriter.append("\n");
			}
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(int stepCount) {
		this.setStepBanner("Step : " + stepCount);
		this.repaint();
	}

	public void eraseLabels() {
		this.setStepBanner("Step : X");
		this.setBorderBanner("border : loop");
		speedSlider.setValue(3);
	}

}
