package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Usuario;
import br.com.sistemaescolar.observer.ObserverUsuario;
import br.com.sistemaescolar.observer.SubjectUsuario;

public class CadastrarUsuarioWindow extends AbstractWindowFrame implements SubjectUsuario {
	private static final long serialVersionUID = 1L;

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastraUsuario();
			}
		}
	};

	ManipularArquivo aM = new ManipularArquivo();

	private ArrayList<ObserverUsuario> observers = new ArrayList<ObserverUsuario>();
	private JPasswordField txfSenha;
	private JTextField txfCodAluno;
	private JComboBox<String> txfPerfil;
	private JButton btnCadastra;
	private JButton btnLimpar;
	private JLabel saida;

	private Usuario usuario;

	public CadastrarUsuarioWindow() {
		super("Cadastrar Usuário");
		this.usuario = new Usuario();
		criarComponentes();
	}

	public CadastrarUsuarioWindow(Usuario usuario) {
		super("Editar Usuario");
		this.usuario = usuario;
		criarComponentes();
		setarValores(usuario);
	}

	private void criarComponentes() {
		saida = new JLabel("Login: ");
		saida.setBounds(15, 10, 200, 25);
		getContentPane().add(saida);

		txfCodAluno = new JTextField();
		txfCodAluno.setBounds(15, 30, 200, 25);
		txfCodAluno.setToolTipText("Digite o código");
		getContentPane().add(txfCodAluno);
		txfCodAluno.addKeyListener(acao);

		saida = new JLabel("Senha:");
		saida.setBounds(15, 60, 200, 25);
		getContentPane().add(saida);

		txfSenha = new JPasswordField();
		txfSenha.setBounds(15, 80, 200, 25);
		txfSenha.setToolTipText("Digite uma senha");
		getContentPane().add(txfSenha);
		txfSenha.addKeyListener(acao);

		saida = new JLabel("Perfil:");
		saida.setBounds(15, 110, 200, 25);
		getContentPane().add(saida);

		txfPerfil = new JComboBox<String>();
		txfPerfil.addItem("-Selecione-");
		txfPerfil.addItem(Usuario.ADMINISTRADOR);
		txfPerfil.addItem(Usuario.CONVIDADO);
		txfPerfil.setBounds(15, 130, 200, 25);
		txfPerfil.setToolTipText("Informe o perfil");
		getContentPane().add(txfPerfil);
		txfPerfil.addKeyListener(acao);

		btnLimpar = new JButton(new AbstractAction("Limpar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if (usuario.getId() != null) {
					setarValores(usuario);
				} else {
					limparFormulario();
				}
			}
		});

		btnLimpar.setBounds(15, 170, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar os campos");
		getContentPane().add(btnLimpar);

		btnCadastra = new JButton(new AbstractAction("Salvar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				cadastraUsuario();
			}
		});

		btnCadastra.addKeyListener(acao);
		btnCadastra.setBounds(120, 170, 95, 25);
		getContentPane().add(btnCadastra);
	}

	public boolean validarCamposObrigatorios() {
		if ("-Selecione-".equals(txfPerfil.getSelectedItem()) || txfCodAluno.getText().isEmpty() || (new String(txfSenha.getPassword())).isEmpty()) {
			return true;
		}

		return false;
	}

	public void cadastraUsuario() {

		if (validarCamposObrigatorios()) {
			JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		Usuario verificaUsuario = aM.pegarUsuarioPorLogin(txfCodAluno.getText());
		if (verificaUsuario != null && verificaUsuario.getId() != usuario.getId()) {
			JOptionPane.showMessageDialog(rootPane, "Já existe um usuário com login informado, por gentiliza digite um novo login", "",JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		usuario.setLogin(txfCodAluno.getText());
		String senhaUsuario = new String(txfSenha.getPassword());
		usuario.setSenha(senhaUsuario);
		usuario.setPerfil(txfPerfil.getSelectedItem().toString());

		if (usuario.getId() == null) {
			ManipularArquivo aM = new ManipularArquivo();
			aM.inserirDado(usuario);
			limparFormulario();
			JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!");
		}

		if (usuario.getId() != null) {
			aM.editarDado(usuario);
			notifyObservers(usuario);
			JOptionPane.showMessageDialog(null, "Usuario editado com sucesso!");
			setVisible(false);
		}
	}

	public void limparFormulario() {
		txfCodAluno.setText("");
		txfSenha.setText("");
		txfPerfil.setSelectedIndex(0);

		usuario = new Usuario();
	}

	private void setarValores(Usuario usuario) {
		txfPerfil.setSelectedItem(usuario.getPerfil());
		txfSenha.setText(usuario.getSenha());
		txfCodAluno.setText(usuario.getLogin());
	}

	@Override
	public void addObserver(ObserverUsuario o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(ObserverUsuario o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Usuario usuario) {
		Iterator it = observers.iterator();
		while (it.hasNext()) {
			ObserverUsuario observer = (ObserverUsuario) it.next();
			observer.update(usuario);
		}

	}
}
