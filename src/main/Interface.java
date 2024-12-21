package main;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Interface {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Interface::createAndShowGUI);
	}

	private static void createAndShowGUI() {
		ArrayList<String> valide = new ArrayList<>();
		ArrayList<String> noValide = new ArrayList<>();
		JFrame frame = new JFrame("Vérifier vos phrases");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(480, 350);
		frame.setLayout(new BorderLayout());

		// Main panel
		JPanel mainPanel = new JPanel();
		 mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	        mainPanel.setBackground(new Color(250, 250, 250)); // Soft white
	        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Title
		JLabel titleLabel = new JLabel("Vérifier vos phrases");
		 titleLabel.setFont(new Font("Poppins", Font.BOLD, 26));
	     titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	     titleLabel.setForeground(new Color(55, 71, 79)); // Soft dark gray
	     mainPanel.add(titleLabel);

		// Subtitle
		JLabel subtitleLabel = new JLabel("Entrez votre phrase");
		subtitleLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(new Color(120, 144, 156)); // Muted gray
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(subtitleLabel);


		// Custom rounded text area
		JTextArea textArea = new JTextArea(4, 50) {
			@Override
			protected void paintComponent(Graphics g) {
				if (!isOpaque()) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					 // Paint shadow
                    g2.setColor(new Color(0, 0, 0, 30)); // Transparent black
                    g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 30, 30);
                    
					// Paint background
					g2.setColor(getBackground());
					g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

					// Create border
					g2.setColor(new Color(200, 200, 200));
					g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
				}
				super.paintComponent(g);
			}
		};
		textArea.setFont(new Font("Poppins", Font.PLAIN, 14));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
		textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Custom rounded scroll pane
		JScrollPane scrollPane = new JScrollPane(textArea) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
			}
		};
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setMaximumSize(new Dimension(600, 200));
		scrollPane.setPreferredSize(new Dimension(600, 50));

		// Center the scroll pane
		JPanel textAreaPanel = new JPanel();
		scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setMaximumSize(new Dimension(400, 100));
        scrollPane.setPreferredSize(new Dimension(400, 100));
        mainPanel.add(scrollPane);

		// Round Button
		JButton selectButton = new JButton("Vérification") {
			@Override
			protected void paintComponent(Graphics g) {
				if (getModel().isArmed()) {
					g.setColor(new Color(76, 175, 80)); // Pressed color
				} else {
					g.setColor(getBackground());
				}
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
				super.paintComponent(g);
			}

			@Override
			protected void paintBorder(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(getForeground());
				g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
			}

			@Override
			public boolean contains(int x, int y) {
				if (shape == null || !shape.getBounds().equals(getBounds())) {
					shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
				}
				return shape.contains(x, y);
			}

			private Shape shape;
		};

		selectButton.setFont(new Font("Poppins", Font.PLAIN, 16));
		selectButton.setBackground(new Color(76, 175, 80));
		selectButton.setForeground(Color.WHITE);
		selectButton.setFocusPainted(false);
		selectButton.setContentAreaFilled(false);
		selectButton.setBorderPainted(false);
		selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectButton.setPreferredSize(new Dimension(150, 50));
		selectButton.setMaximumSize(new Dimension(150, 50));
		selectButton.setMinimumSize(new Dimension(150, 50));

		// Button hover effect
		selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				selectButton.setBackground(new Color(0, 204, 0));
				selectButton.repaint();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
				selectButton.setBackground(new Color(76, 175, 80));
				selectButton.repaint();
			}
		});

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(selectButton);

		// Result label
		JLabel resultLabel = new JLabel();
		resultLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
		resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		resultLabel.setForeground(new Color(55, 71, 79));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(resultLabel);

		selectButton.addActionListener(e -> {
		    String input = textArea.getText();
		    if (input.isEmpty()) {
		        resultLabel.setText("Faites entrer votre phrase!");
		        JOptionPane.showMessageDialog(frame, "Faites entrer votre phrase!", "Erreur",
		                JOptionPane.WARNING_MESSAGE);
		    } else {
		        String[] choices = input.split("-");
		        StringBuilder resultBuilder = new StringBuilder();

		        for (String test : choices) {
		            TokenManager tm = new TokenManager(test);
		            Parseur parseur = new Parseur(tm);

		            try {
		                parseur.parse();
		                System.out.println("Entrée : \"" + test + "\" => valide");
		                valide.add(test);
		                resultBuilder.append(test.trim()).append(" ✅\n");
		            } catch (RuntimeException ex) {
		                System.out.println("Entrée : \"" + test + "\" => invalide (" + ex.getMessage() + ")");
		                noValide.add(test);
		                resultBuilder.append(test.trim()).append(" ❌\n");
		            }
		        }

		        // Affichage des résultats dans une boîte de dialogue.
		        JTextArea resultArea = new JTextArea(resultBuilder.toString());
		        resultArea.setFont(new Font("Poppins", Font.PLAIN, 14));
		        resultArea.setEditable(false);
		        resultArea.setLineWrap(true);
		        resultArea.setWrapStyleWord(true);

		        JScrollPane resultScrollPane = new JScrollPane(resultArea);
		        resultScrollPane.setPreferredSize(new Dimension(100, 50));

		        int resultOk = JOptionPane.showConfirmDialog(frame, resultScrollPane, "Résultats",
		                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		        // Nettoyer les listes après affichage
		        if (resultOk == JOptionPane.OK_OPTION || resultOk == JOptionPane.CANCEL_OPTION
		                || resultOk == JOptionPane.CLOSED_OPTION) {
		            valide.clear();
		            noValide.clear();
		            System.out.println("Les listes ont été réinitialisées.");
		        }
		    }
		});


		frame.add(mainPanel, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}