package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.AlunoDAO;
import dao.HistoricoPesoDAO;
import modelo.Aluno;
import modelo.RegistroPeso;
import GUI.AlunoGUI;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.Color;

public class HistoricoPesoGUI {
	
	private AlunoDAO alunoDAO;
    private HistoricoPesoDAO historicoPesoDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    private JFrame frame;
    private JTextField cpfField, dataField, pesoField;

    public HistoricoPesoGUI() {
    	alunoDAO = new AlunoDAO();
        historicoPesoDAO = new HistoricoPesoDAO();

        frame = new JFrame("Histórico de Peso");
        frame.setSize(600, 473);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(null);

        cpfField = new JTextField();
        cpfField.setBounds(0, 20, 252, 20);
        
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setBackground(new Color(255, 255, 255));
        textFieldPanel.setBounds(20, 20, 544, 47);
        textFieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        textFieldPanel.setLayout(null);

        JLabel label = new JLabel("CPF do Aluno:", SwingConstants.LEFT);
        label.setBounds(0, 0, 252, 20);
        textFieldPanel.add(label);
        textFieldPanel.add(cpfField);


        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((cpfField.getText().isEmpty()|| dataField.getText().isEmpty() || pesoField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
				} else {
					adicionarRegistro();
				}
			}
		});

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if ((cpfField.getText().isEmpty()|| dataField.getText().isEmpty() || pesoField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
				} else {
					atualizarRegistro();
				}
            }
        });

        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				excluirRegistro();
				
            }
        });

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarRegistros();
            }
        });
        
        JButton btnCalcular = new JButton("Calcular IMC");
        btnCalcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularIMC(); 
            }
        });
        
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                AlunoGUI alunoGUI = new AlunoGUI();
                alunoGUI.show(); 
            }
        });

       
        
        // Tabela para exibir os registros
        String[] columns = {"CPF do Aluno", "Data", "Peso"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 218, 544, 205);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBounds(20, 174, 544, 33);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(consultarButton);
        buttonPanel.add(btnCalcular);
        buttonPanel.add(deleteButton);
        

        // Adicionando o painel das caixas de texto, dos botões, e da tabela ao painel principal
        panel.add(textFieldPanel);
        panel.add(buttonPanel);
        
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose(); 
                AlunoGUI alunoGUI = new AlunoGUI();
                alunoGUI.show(); 
        	}
        });
        buttonPanel.add(btnVoltar);
        panel.add(scrollPane);
        

        frame.getContentPane().add(panel);
        
                JLabel label_2 = new JLabel("Peso:", SwingConstants.LEFT);
                label_2.setBounds(20, 122, 252, 20);
                panel.add(label_2);
                
                        JLabel lblData = new JLabel("Data:", SwingConstants.LEFT);
                        lblData.setBounds(20, 71, 252, 20);
                        panel.add(lblData);
                        dataField = new JTextField();
                        dataField.setBounds(20, 91, 252, 20);
                        panel.add(dataField);
                        pesoField = new JTextField();
                        pesoField.setBounds(20, 143, 252, 20);
                        panel.add(pesoField);
        frame.setVisible(true);

    }

    private void adicionarRegistro() {
        String cpf = cpfField.getText();
        String dataTexto = dataField.getText();
		LocalDate data = LocalDate.parse(dataTexto,
		DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double peso = Double.parseDouble(pesoField.getText());

        historicoPesoDAO.adicionar(new RegistroPeso(cpf, data, peso));
        JOptionPane.showMessageDialog(frame, "Registro de peso adicionado com sucesso!");
    }

    private void atualizarRegistro() {
    	String cpf = cpfField.getText();
    	String dataTexto = dataField.getText();
		LocalDate data = LocalDate.parse(dataTexto,
		DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double peso = Double.parseDouble(pesoField.getText());

        historicoPesoDAO.atualizar(new RegistroPeso(cpf, data, peso));
        JOptionPane.showMessageDialog(frame, "Registro de peso atualizado com sucesso!");
    }

    private void excluirRegistro() {
        String cpf = cpfField.getText();
        historicoPesoDAO.excluir(cpf);
        JOptionPane.showMessageDialog(frame, "Registro de peso excluído com sucesso!");
    }

    private void consultarRegistros() {
    	tableModel.setRowCount(0);

        List<RegistroPeso> registros = historicoPesoDAO.consultar();
        for (RegistroPeso registro : registros) {
            Object[] row = {registro.getCpfAluno(), registro.getData(), registro.getPeso()};
            tableModel.addRow(row);
        }
    }
    
    private void calcularIMC() {
    	
    	String nomeArquivo;
        String cpf = cpfField.getText();
        String dataTexto = dataField.getText();
		LocalDate data = LocalDate.parse(dataTexto,
		DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double peso = Double.parseDouble(pesoField.getText());
        
        // Obter as informações do aluno a partir do CPF
        Aluno aluno = alunoDAO.obterAlunoPorCPF(cpf); 

        RegistroPeso registroPeso = new RegistroPeso(cpf, data, peso);
        nomeArquivo = historicoPesoDAO.calcularIMC(registroPeso, aluno);
        
        try {
            File file = new File(nomeArquivo);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) { 
            e.printStackTrace(); // Tratar a exceção adequadamente
        }
    }
    
    
    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HistoricoPesoGUI();
            }
        });
    }
}