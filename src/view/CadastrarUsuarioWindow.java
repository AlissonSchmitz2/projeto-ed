package view;

import model.Cidade;
import model.Usuario;
import observer.ObserverCidade;
import observer.ObserverUsuario;
import observer.SubjectUsuario;

import java.awt.event.ActionEvent;
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

import lib.ManipularArquivo;

public class CadastrarUsuarioWindow extends AbstractWindowFrame implements SubjectUsuario {
	private static final long serialVersionUID = 1L;

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
		super("Editar Cidade");
		this.usuario = usuario;
		criarComponentes();
		setarValores(usuario);
	}

	private void criarComponentes() {
		saida = new JLabel("Código: ");
		saida.setBounds(15, 10, 200, 25);
		getContentPane().add(saida);

		txfCodAluno = new JTextField();
		txfCodAluno.setBounds(15, 30, 200, 25);
		txfCodAluno.setToolTipText("Digite o código");
		getContentPane().add(txfCodAluno);

		saida = new JLabel("Senha:");
		saida.setBounds(15, 60, 200, 25);
		getContentPane().add(saida);

		txfSenha = new JPasswordField();
		txfSenha.setBounds(15, 80, 200, 25);
		txfSenha.setToolTipText("Digite uma senha");
		getContentPane().add(txfSenha);

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

		btnCadastra = new JButton(new AbstractAction("Cadastrar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

				usuario.setLogin(txfCodAluno.getText());
				String senhaUsuario = new String(txfSenha.getPassword());
				usuario.setSenha(senhaUsuario);
				usuario.setPerfil(txfPerfil.getSelectedItem().toString());

				boolean cadastrar = true;

				if (usuario.getId() != null) {
					notifyObservers(usuario);
					JOptionPane.showMessageDialog(null, "Usuario editado com sucesso!");
					cadastrar = false;
					setVisible(false);
				}

				if (cadastrar) {

					try {
						ManipularArquivo aM = new ManipularArquivo();
						aM.inserirDado(usuario);
						limparFormulario();
						JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnCadastra.setBounds(120, 170, 95, 25);
		getContentPane().add(btnCadastra);
	}

	public void limparFormulario() {
		txfCodAluno.setText("");
		txfSenha.setText("");
		txfPerfil.setSelectedIndex(0);

	}

	private void setarValores(Usuario usuario) {
		// TODO: setar valores iniciais para edição
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
