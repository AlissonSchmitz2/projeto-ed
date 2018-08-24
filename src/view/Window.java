package view;

import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.Usuario;

public class Window extends JFrame {
	private static final long serialVersionUID = 3283754083146407662L;
	
	//Guardar aqui o usuário logado
	private Usuario usuarioLogado;
	
	private JMenu menuAlunos;
	private JMenu menuCidades;
	private JMenu menuUsuarios;
	private JMenuItem menuLogin;

	private JDesktopPane desktop;
	
	public Window(Usuario usuarioLogado) {
		super();
		
		this.usuarioLogado = usuarioLogado;

		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	    setContentPane(desktop);
		
		inicializar();
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
		
		//TODO: habilitar somente após logado
		//desabilitarMenuItems(menuAlunos);
			
		return menuAlunos;
	}
	
	private JMenuItem getMenuItemCadastrarAluno() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());
		
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//System.out.println("TODO: Cadastro de Aluno");
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
		
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.out.println("TODO: Listar Alunos");
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
		
		//TODO: desabilitarMenuItems(menuCidades);
			
		return menuCidades;
	}
	private JMenuItem getMenuItemCadastrarCidade() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());
		
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
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
		
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.out.println("TODO: Listar Cidades");
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
		
		//TODO: desabilitarMenuItems(menuUsuarios);
			
		return menuUsuarios;
	}

	private JMenuItem getMenuItemCadastrarUsuario() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());
		
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				CadastrarUsuarioWindow frame = new CadastrarUsuarioWindow();
				abrirFrame(frame);
			}
		});
			
		return menuItem;
	}

	private JMenuItem getMenuItemListarUsuarios() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());
		
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ListarUsuariosWindow frame = new ListarUsuariosWindow();
				abrirFrame(frame);
			}
		});
		
		return menuItem;
	}
	
	
	/*
	 * HELPERS
	 */
	private void abrirFrame(WindowFrame frame) {
		frame.setVisible(true);
	    desktop.add(frame);
	    try {
	    	frame.setMaximum(true);
	        frame.setSelected(true);
	    } catch (PropertyVetoException e) {}
	}
	
	private void habilitarMenuItems(JMenu menuPrincipal) {
		for (Component menuItem : menuPrincipal.getMenuComponents()) {
			menuItem.setEnabled(false);
		}
	}
	
	
	private void desabilitarMenuItems(JMenu menuPrincipal) {
		for (Component menuItem : menuPrincipal.getMenuComponents()) {
			menuItem.setEnabled(false);
		}
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
	
	//public static void main(String[] args) {
		//Window tela = new Window();
		//tela.setVisible(true);
	//}
}
