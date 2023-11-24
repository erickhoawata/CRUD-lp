package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.AlunoDAO;
import modelo.Aluno;
import GUI.HistoricoPesoGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.border.BevelBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class AlunoGUI {
    private AlunoDAO alunoDAO;

    private JFrame frame;
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField alturaField;
    private JTextField pesoField;
    private JTextField cpfField;
    private JPanel tablePanel;
    private JTable table;
    
    public AlunoGUI() {
        alunoDAO = new AlunoDAO();
        
        frame = new JFrame("Cadastro Aluno");
        frame.getContentPane().setFont(new Font("Arial", Font.BOLD, 12));
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addButton = new JButton("Cadastrar");
        addButton.setBounds(101, 7, 90, 21);
        addButton.setBackground(new Color(192, 192, 192));
        addButton.setFont(new Font("Arial", Font.BOLD, 10));
        JButton viewAllButton = new JButton("Consultar");
        viewAllButton.setBounds(190, 7, 90, 21);
        viewAllButton.setBackground(new Color(192, 192, 192));
        viewAllButton.setFont(new Font("Arial", Font.BOLD, 10));
        JButton deleteButton = new JButton("Deletar");
        deleteButton.setBounds(278, 7, 84, 21);
        deleteButton.setBackground(new Color(192, 192, 192));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 10));
        JButton limpaTela = new JButton("Limpar");
        limpaTela.setBounds(357, 7, 90, 21);
        limpaTela.setBackground(new Color(192, 192, 192));
        limpaTela.setFont(new Font("Arial", Font.BOLD, 10));
        JButton historicoPeso = new JButton("Historico de Peso");
        historicoPeso.setBounds(446, 7, 128, 21);
        historicoPeso.setBackground(new Color(192, 192, 192));
        historicoPeso.setFont(new Font("Arial", Font.BOLD, 10));
        
        addButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
				if ((nomeField.getText().isEmpty() || cpfField.getText().isEmpty()
						|| dataNascimentoField.getText().isEmpty() || pesoField.getText().isEmpty()
						|| alturaField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
				} else {

					String nome = nomeField.getText();
					String dataNascimentoTexto = dataNascimentoField.getText();
					LocalDate dataNascimento = LocalDate.parse(dataNascimentoTexto,
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					double peso = Double.parseDouble(pesoField.getText());
					double altura = Double.parseDouble(alturaField.getText());
					String cpf = cpfField.getText();

					Aluno aluno = new Aluno(nome, cpf, dataNascimento, peso, altura);

					alunoDAO.adicionar(aluno);
					JOptionPane.showMessageDialog(null, "Aluno " + nomeField.getText() + " inserido com sucesso! ");
				}
			}
        });

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Aluno> alunos = alunoDAO.consultar();
                displayAlunos(alunos);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
					String cpf = cpfField.getText();
					alunoDAO.excluir(cpf);
					clearFields();
					displayMessage("Aluno excluído com sucesso!");
				}
			
		});
        
        limpaTela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	clearFields();
            }
        });
        
        historicoPeso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	HistoricoPesoGUI historicoPesoGUI = new HistoricoPesoGUI();
            	historicoPesoGUI.show(); 
            	frame.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 326, 584, 35);
        buttonPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        buttonPanel.setLayout(null);
        JButton updateButton = new JButton("Atualizar");
        updateButton.setBounds(10, 7, 95, 21);
        updateButton.setBackground(new Color(192, 192, 192));
        updateButton.setFont(new Font("Arial", Font.BOLD, 10));
        buttonPanel.add(updateButton);
        
        updateButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
				if ((nomeField.getText().isEmpty() || cpfField.getText().isEmpty()
						|| dataNascimentoField.getText().isEmpty() || pesoField.getText().isEmpty()
						|| alturaField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
				} else {

					String nome = nomeField.getText();
					String dataNascimentoTexto = dataNascimentoField.getText();
					LocalDate dataNascimento = LocalDate.parse(dataNascimentoTexto,
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					double peso = Double.parseDouble(pesoField.getText());
					double altura = Double.parseDouble(alturaField.getText());
					String cpf = cpfField.getText();

					Aluno aluno = new Aluno(nome, cpf, dataNascimento, peso, altura);

					alunoDAO.atualizar(aluno);

					JOptionPane.showMessageDialog(null, "Aluno " + nomeField.getText() + " alterado com sucesso! ");
				}
			}
		});
        
        frame.getContentPane().setLayout(null);
        buttonPanel.add(addButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(limpaTela);
        buttonPanel.add(historicoPeso);
        
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        tablePanel = new JPanel(new BorderLayout());
        frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        frame.setVisible(true);
        
        frame.getContentPane().add(buttonPanel);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 584, 1);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("Nome :");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 12));
        lblNewLabel_2.setBounds(78, 14, 46, 14);
        frame.getContentPane().add(lblNewLabel_2);
        
        nomeField = new JTextField();
        nomeField.setFont(new Font("Arial", Font.BOLD, 10));
        nomeField.setBounds(210, 12, 273, 20);
        frame.getContentPane().add(nomeField);
        nomeField.setColumns(10);
        
        JLabel lblDataNascimento = new JLabel("Data nascimento :");
        lblDataNascimento.setFont(new Font("Arial", Font.BOLD, 12));
        lblDataNascimento.setBounds(78, 42, 122, 14);
        frame.getContentPane().add(lblDataNascimento);
        
        dataNascimentoField = new JTextField();
        dataNascimentoField.setFont(new Font("Arial", Font.BOLD, 10));
        dataNascimentoField.setColumns(10);
        dataNascimentoField.setBounds(210, 40, 273, 20);
        frame.getContentPane().add(dataNascimentoField);
        
        JLabel lblAltura = new JLabel("Altura :");
        lblAltura.setFont(new Font("Arial", Font.BOLD, 12));
        lblAltura.setBounds(78, 70, 92, 14);
        frame.getContentPane().add(lblAltura);
        
        alturaField = new JTextField();
        alturaField.setFont(new Font("Arial", Font.BOLD, 10));
        alturaField.setColumns(10);
        alturaField.setBounds(210, 71, 273, 20);
        frame.getContentPane().add(alturaField);
        
        JLabel lblPeso = new JLabel("Peso :");
        lblPeso.setFont(new Font("Arial", Font.BOLD, 12));
        lblPeso.setBounds(78, 98, 78, 14);
        frame.getContentPane().add(lblPeso);
        
        pesoField = new JTextField();
        pesoField.setFont(new Font("Arial", Font.BOLD, 10));
        pesoField.setColumns(10);
        pesoField.setBounds(210, 96, 273, 20);
        frame.getContentPane().add(pesoField);
        
        JLabel lblCpf = new JLabel("CPF :");
        lblCpf.setFont(new Font("Arial", Font.BOLD, 12));
        lblCpf.setBounds(78, 126, 34, 14);
        frame.getContentPane().add(lblCpf);
        
        cpfField = new JTextField();
        cpfField.setFont(new Font("Arial", Font.BOLD, 10));
        cpfField.setColumns(10);
        cpfField.setBounds(210, 124, 273, 20);
        frame.getContentPane().add(cpfField);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(78, 158, 405, 157);
        frame.getContentPane().add(scrollPane);
        
        table = new JTable();
        scrollPane.setColumnHeaderView(table);
        
    }
    

    private Aluno criarAlunoFromFields() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoField.getText());
        double peso = Double.parseDouble(pesoField.getText());
        double altura = Double.parseDouble(alturaField.getText());

        return new Aluno(nome, cpf, dataNascimento, peso, altura);
    }
    
    private void displayAlunos(List<Aluno> alunos) {
        String[] columns = {"Nome", "CPF", "Data de Nascimento", "Peso", "Altura"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Aluno aluno : alunos) {
            Object[] row = {
                    aluno.getNome(),
                    aluno.getCpf(),
                    aluno.getDataNascimento(),
                    aluno.getPeso(),
                    aluno.getAltura()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.removeAll();
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }


    private void clearFields() {
        nomeField.setText("");
        cpfField.setText("");
        dataNascimentoField.setText("");
        pesoField.setText("");
        alturaField.setText("");
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlunoGUI();
            }
        });
    }
}