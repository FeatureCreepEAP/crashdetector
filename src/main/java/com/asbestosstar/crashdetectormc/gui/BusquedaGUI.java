package com.asbestosstar.crashdetectormc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetectormc.grepr.BusquedaArchivos;

public class BusquedaGUI extends JFrame {
    private JTextField dirField;
    private JTextField searchField;
    private JCheckBox regexCheck;
    private JCheckBox caseCheck;
    private JTextArea resultsArea;

    public BusquedaGUI() {
        setTitle("grepr/fgrepr");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CrashDetectorGUI.colorFondo);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dirField = new JTextField();
        JButton browseBtn = new JButton(MonitorDePID.idioma.seleccionarCarpeta());
        browseBtn.addActionListener(e -> seleccionarCarpeta());
        inputPanel.add(dirField);
        inputPanel.add(browseBtn);

        searchField = new JTextField();
        inputPanel.add(new JLabel(MonitorDePID.idioma.cadenaBusqueda()));
        inputPanel.add(searchField);

        regexCheck = new JCheckBox(MonitorDePID.idioma.usarRegex());
        caseCheck = new JCheckBox(MonitorDePID.idioma.ignorarMayusculas());
        inputPanel.add(regexCheck);
        inputPanel.add(caseCheck);

        JButton searchBtn = new JButton(MonitorDePID.idioma.buscar());
        searchBtn.addActionListener(e -> iniciarBusqueda());
        inputPanel.add(searchBtn);

        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void seleccionarCarpeta() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            dirField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void iniciarBusqueda() {
        String dirfield = dirField.getText().trim();
    	final String directorio;
        
        if (dirfield.replace(" ", "").equals("")) {
            directorio = System.getProperty("user.dir");
        }else {
        	directorio= dirfield;
        }
        String cadena = searchField.getText().trim();
        boolean usarRegex = regexCheck.isSelected();
        boolean ignorarMayus = caseCheck.isSelected();

        resultsArea.setText(MonitorDePID.idioma.busquedaEnProgreso() + "\n");

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() {
                return BusquedaArchivos.buscar(directorio, cadena, usarRegex, ignorarMayus);
            }

            @Override
            protected void done() {
                try {
                    List<String> resultados = get();
                    resultsArea.setText("");
                    if (resultados.isEmpty()) {
                        resultsArea.append(MonitorDePID.idioma.noSeEncontraronResultados());
                    } else {
                        for (String res : resultados) {
                            resultsArea.append(res + "\n");
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    resultsArea.append(MonitorDePID.idioma.errorBusqueda() + e.getMessage());
                }
            }
        }.execute();
    }
}