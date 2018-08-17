package view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import javafx.event.ActionEvent;

public class AlunosWindow extends JDialog{

	private JTextField txfNome, txfCod, txfEmail, txfObs, txfEnder, txfNum, txfComplemen, 
	txfBairro;
	private JComboBox<String> cbxGenero, cbxCidade, cbxEstado, cbxPais;
	private JButton btnSalvar;
	private JLabel labes;
	private JFormattedTextField txfData, txfFone, txfCel, txfCep;
	
	//pessoal, tenho que modificar ainda essa classe.
	//Vou encaixa-la com as outras. Irei fazer no find ou na aula. 
	//Deixa comigo essa tela abraços :D
	
	
	AlunosWindow(){
		setSize(800,700);
		setTitle("Cadastro do Aluno");
		setLayout(null);
		setResizable(false);
		componentesCriar();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(201, 201, 248));
		
	}
	
	public void componentesCriar(){
		
		//Coluna da esquerda, campos e escritas
			labes = new JLabel("Código do Aluno:");
			labes.setBounds(20,20,250,25);
			getContentPane().add(labes);
		
		txfCod = new JTextField();
		txfCod.setBounds(20,50,200,25);
		getContentPane().add(txfCod);
		
			labes = new JLabel("Nome:");
			labes.setBounds(20,90,250,25);
			getContentPane().add(labes);
		
		txfNome = new JTextField();
		txfNome.setBounds(20,120,200,25);
		getContentPane().add(txfNome);
			
			labes = new JLabel("Sexo:");//
			labes.setBounds(20,160,250,25);
			getContentPane().add(labes);
		
		cbxGenero = new JComboBox<String>();
		cbxGenero.addItem("-Selecione-");
		cbxGenero.addItem("Masculino");
		cbxGenero.addItem("Feminino");
		cbxGenero.setBounds(20, 190, 200, 25);
		getContentPane().add(cbxGenero);

		labes = new JLabel("Data de Nascimento:");
		labes.setBounds(20,230,230,25);
			getContentPane().add(labes);
		
		try {
			MaskFormatter maskDat = new MaskFormatter("##/##/####");
			MaskFormatter maskFone = new MaskFormatter("(##) #####-####");
			MaskFormatter maskCep = new MaskFormatter("#####-###");
			
			
			txfData = new JFormattedTextField(maskDat);
			txfData.setBounds(20, 260, 100, 25);
			getContentPane().add(txfData);
			
				labes = new JLabel("Telefone:");//
				labes.setBounds(20,300,250,25);
				getContentPane().add(labes);
			
			txfFone = new JFormattedTextField(maskFone);
			txfFone.setBounds(20, 330, 100, 25);
			getContentPane().add(txfFone);
			
				labes = new JLabel("Celular:");//
				labes.setBounds(20,370,250,25);
				getContentPane().add(labes);
			
			txfCel = new JFormattedTextField(maskFone);
			txfCel.setBounds(20, 400, 100, 25);
			getContentPane().add(txfCel);
			
			labes = new JLabel("CEP:");
			labes.setBounds(450,160,250,25);
			getContentPane().add(labes);

		txfCep = new JFormattedTextField(maskCep);
		txfCep.setBounds(450,190,200,25);
		getContentPane().add(txfCep);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		labes = new JLabel("Email:");
		labes.setBounds(20,440,250,25);
		getContentPane().add(labes);
	
	txfEmail = new JTextField();
	txfEmail.setBounds(20,470,200,25);
	getContentPane().add(txfEmail);
	
		labes = new JLabel("Observação:");
		labes.setBounds(20,510,250,25);
		getContentPane().add(labes);

	txfObs = new JTextField();
	txfObs.setBounds(20,540,200,25);
	getContentPane().add(txfObs);
	
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(20, 600, 100, 25);
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	//new Teste().setVisible(true);
            	//Adaptar para a outra tela
            }

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
		
		//coluna da direita, cmpos e escrita
		
		labes = new JLabel("Endereço:");
		labes.setBounds(450,20,250,25);
		getContentPane().add(labes);
	
	txfEnder = new JTextField();
	txfEnder.setBounds(450,50,200,25);
	getContentPane().add(txfEnder);
	
		labes = new JLabel("Complemento:");
		labes.setBounds(450,90,250,25);
		getContentPane().add(labes);

	txfComplemen = new JTextField();
	txfComplemen.setBounds(450,120,200,25);
	getContentPane().add(txfComplemen);

		labes = new JLabel("Número:");
		labes.setBounds(450,230,250,25);
		getContentPane().add(labes);

	txfNum = new JTextField();
	txfNum.setBounds(450,260,200,25);
	getContentPane().add(txfNum);
	
		labes = new JLabel("Bairro:");
		labes.setBounds(450,300,250,25);
		getContentPane().add(labes);

	txfBairro = new JTextField();
	txfBairro.setBounds(450,330,200,25);
	getContentPane().add(txfBairro);
	
		labes = new JLabel("Cidade (Outra tela):");
		labes.setBounds(450,370,250,25);
		getContentPane().add(labes);

	cbxCidade = new JComboBox<String>();
	cbxCidade.addItem("-Selecione-");
	cbxCidade.addItem("Vem da outra tela");
	cbxCidade.setBounds(450, 400, 200, 25);
	getContentPane().add(cbxCidade);
	
		labes = new JLabel("Estado (Outra tela):");
		labes.setBounds(450,440,250,25);
		getContentPane().add(labes);

	cbxEstado = new JComboBox<String>();
	cbxEstado.addItem("-Selecione-");
	cbxEstado.addItem("Vem da outra tela");
	cbxEstado.setBounds(450, 470, 200, 25);
	getContentPane().add(cbxEstado);

		labes = new JLabel("País (Outra tela):");
		labes.setBounds(450,510,250,25);
		getContentPane().add(labes);

	cbxPais = new JComboBox<String>();
	cbxPais.addItem("-Selecione-");
	cbxPais.addItem("Vem da outra tela");
	cbxPais.setBounds(450, 540, 200, 25);
	getContentPane().add(cbxPais);
	
	

btnSalvar = new JButton("Salvar");
btnSalvar.setBounds(20, 600, 100, 25);
getContentPane().add(btnSalvar);
btnSalvar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
    	setVisible(false);
    	//new Teste().setVisible(true);
    	//new Teste().setVisible(true);
    	//Adaptar para a outra tela
    }

	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
});
		
}
	
	public static void main(String[] args) {

		new AlunosWindow();
	
		
	}

}
