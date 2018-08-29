package view;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import model.Usuario;

public class Window extends JFrame {
	private static final long serialVersionUID = 3283754083146407662L;
	
	//Guardar aqui o usuário logado
	private Usuario usuarioLogado;
	
	private JMenu menuAlunos;
	private JMenu menuCidades;
	private JMenu menuUsuarios;

	private JDesktopPane desktop;
	
	public Window(Usuario usuarioLogado) {
		super();
		
		this.usuarioLogado = usuarioLogado;

		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		desktop.setVisible(true);
	    setContentPane(desktop);
		
		inicializar();
		
		//Full screen
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	private void inicializar() {
		this.setTitle("Sistema Escolar v0.0.0-1");
		this.setJMenuBar(getWindowMenuBar());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(0, 0, 796, 713));
		this.setFocusableWindowState(true);
	}
	
	/*
	 * MENU DE NAVEGAÇÃO
	 */
	private JMenuBar getWindowMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getMenuAlunos());
		menuBar.add(getMenuCidades());
		menuBar.add(getMenuUsuarios());
		
		return menuBar;
	}
	
	//Menu Alunos
	private JMenu getMenuAlunos() {
		menuAlunos = new JMenu();
		menuAlunos.setText("Alunos");
		menuAlunos.setFont(getDefaultFont());
		
		menuAlunos.add(getMenuItemCadastrarAluno());
		menuAlunos.add(getMenuItemListarAlunos());

		return menuAlunos;
	}
	
	private JMenuItem getMenuItemCadastrarAluno() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());
		
		protegerMenuItemBaseadoPerfilUsuario(menuItem);
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastrarAlunosWindow frame = new CadastrarAlunosWindow();
				abrirFrame(frame);
			}
		});

		return menuItem;
	}
	
	private JMenuItem getMenuItemListarAlunos() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarAlunosWindow frame = new ListarAlunosWindow(desktop, usuarioLogado);
				abrirFrame(frame);
				//Garante que a grid se encaixe na tela depois que a tela é criada
				frame.redimensionarGrid(frame.getGridContent());
			}
		});
		
		return menuItem;
	}
	
	
	//Menu Cidades
	private JMenu getMenuCidades() {
		menuCidades = new JMenu();
		menuCidades.setText("Cidades");
		menuCidades.setFont(getDefaultFont());
		
		menuCidades.add(getMenuItemCadastrarCidade());
		menuCidades.add(getMenuItemListarCidades());
			
		return menuCidades;
	}
	private JMenuItem getMenuItemCadastrarCidade() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());
		
		protegerMenuItemBaseadoPerfilUsuario(menuItem);
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastrarCidadeWindow frame = new CadastrarCidadeWindow();
				abrirFrame(frame);
			}
		});
			
		return menuItem;
	}

	private JMenuItem getMenuItemListarCidades() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarCidadesWindow frame = new ListarCidadesWindow(desktop, usuarioLogado);
				abrirFrame(frame);
				//Garante que a grid se encaixe na tela depois que a tela é criada
				frame.redimensionarGrid(frame.getGridContent());
			}
		});
		
		return menuItem;
	}
	
	
	//Menu Usuários
	private JMenu getMenuUsuarios() {
		menuUsuarios = new JMenu();
		menuUsuarios.setText("Usuários");
		menuUsuarios.setFont(getDefaultFont());
		
		menuUsuarios.add(getMenuItemCadastrarUsuario());
		menuUsuarios.add(getMenuItemListarUsuarios());
		menuUsuarios.add(getMenuItemAlterarSenhaUsuario());
			
		return menuUsuarios;
	}

	private JMenuItem getMenuItemCadastrarUsuario() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());
		
		protegerMenuItemBaseadoPerfilUsuario(menuItem);
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastrarUsuarioWindow frame = new CadastrarUsuarioWindow();
				abrirFrame(frame);
			}
		});
			
		return menuItem;
	}

	private JMenuItem getMenuItemAlterarSenhaUsuario() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Alterar Minha Senha");
		menuItem.setFont(getDefaultFont());
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: abrir a página para alterar a senha
				AlterarSenhaWindow frame = new AlterarSenhaWindow(usuarioLogado);
				abrirFrame(frame);
			}
		});
		
		return menuItem;
	}
	
	private JMenuItem getMenuItemListarUsuarios() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarUsuariosWindow frame = new ListarUsuariosWindow(desktop, usuarioLogado);
				abrirFrame(frame);
				//Garante que a grid se encaixe na tela depois que a tela é criada
				frame.redimensionarGrid(frame.getGridContent());
			}
		});
		
		return menuItem;
	}
	
	
	/*
	 * HELPERS
	 */
	private void protegerMenuItemBaseadoPerfilUsuario(JMenuItem menuItem) {
		//Se usuário for diferente de administrador, desabilita menu item
		if (!usuarioLogado.possuiPerfilAdministrador()) {
			menuItem.setEnabled(false);
		}
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
	    desktop.add(frame);
	    
	    try {
	    	frame.setMaximum(true);
	        frame.setSelected(true);
	    } catch (PropertyVetoException e) {}
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
}
