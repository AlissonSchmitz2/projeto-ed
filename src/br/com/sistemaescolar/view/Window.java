package br.com.sistemaescolar.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;

import br.com.sistemaescolar.model.Usuario;

public class Window extends JFrame {
	private static final long serialVersionUID = 3283754083146407662L;

	// Guardar aqui o usuário logado
	private Usuario usuarioLogado;

	private JMenu menuAlunos;
	private JMenu menuCursos;
	private JMenu menuDisciplina;
	private JMenu menuFase;
	private JMenu menuCidades;
	private JMenu menuUsuarios;
	private JMenu menuOpcao;

	private JDesktopPane desktop;

	private ListarAlunosWindow frameListarAlunos;
	private CadastrarAlunosWindow frameCadastrarAlunos;
	private CadastrarCidadeWindow frameCadastrarCidade;
	private ListarCidadesWindow frameListarCidades;
	private CadastrarUsuarioWindow frameCadastrarUsuario;
	private AlterarSenhaWindow frameAlterarSenha;
	private ListarUsuariosWindow frameListarUsuarios;
	private CadastrarCursosWindow frameCadastrarCursos;
	private CadastrarDisciplinaWindow frameCadastrarDisciplina;
	private CadastrarFaseWindow frameCadastrarFase;

	public Window(Usuario usuarioLogado) {
		super();

		this.usuarioLogado = usuarioLogado;

		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		desktop.setVisible(true);
		setContentPane(desktop);
		
		URL url = this.getClass().getResource("/br/com/sistemaescolar/icons/b.png");
		Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url);
		this.setIconImage(imagemTitulo);

		inicializar();

		// Full screen
		setExtendedState(Frame.MAXIMIZED_BOTH);
		
		
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void inicializar() {
		String dataLogin = getDateTime();
		this.setTitle("Sistema Escolar v0.0.0-1      " + "Usuário Logado: " + usuarioLogado.getLogin() + " ("
				+ usuarioLogado.getPerfil() + ")" + " - Ultimo Login: " + dataLogin);
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
		menuBar.add(getMenuCursos());
		menuBar.add(getMenuFases());
		menuBar.add(getMenuDisciplinas());
		menuBar.add(getMenuCidades());
		menuBar.add(getMenuUsuarios());
		menuBar.add(getMenuOpcao());
		return menuBar;
	}

	// Menu Alunos
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
				frameCadastrarAlunos = new CadastrarAlunosWindow();
				abrirFrame(frameCadastrarAlunos);

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
				frameListarAlunos = new ListarAlunosWindow(desktop, usuarioLogado);
				abrirFrame(frameListarAlunos);
				// Garante que a grid se encaixe na tela depois que a tela é criada
				frameListarAlunos.redimensionarGrid(frameListarAlunos.getGridContent());
			}
		});

		return menuItem;
	}

	// Menu Cursos
	private JMenu getMenuCursos() {
		menuCursos = new JMenu();
		menuCursos.setText("Cursos");
		menuCursos.setFont(getDefaultFont());

		menuCursos.add(getMenuItemCadastrarCursos());
		menuCursos.add(getMenuItemListarCursos());
		return menuCursos;
	}

	private JMenuItem getMenuItemCadastrarCursos() {
		JMenuItem menuItem = new JMenuItem();

		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		protegerMenuItemBaseadoPerfilUsuario(menuItem);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameCadastrarCursos = new CadastrarCursosWindow();
				abrirFrame(frameCadastrarCursos);
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemListarCursos() {
		JMenuItem menuItem = new JMenuItem();

		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO:Abrir frame para listar cursos
			}
		});
		return menuItem;
	}

	// Menu Fases
	private JMenu getMenuFases() {
		menuFase = new JMenu();
		menuFase.setText("Fase");
		menuFase.setFont(getDefaultFont());

		menuFase.add(getMenuItemCadastrarFases());
		menuFase.add(getMenuItemListarFases());
		return menuFase;
	}

	private JMenuItem getMenuItemCadastrarFases() {
		JMenuItem menuItem = new JMenuItem();

		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		protegerMenuItemBaseadoPerfilUsuario(menuItem);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameCadastrarFase = new CadastrarFaseWindow();
				abrirFrame(frameCadastrarFase);
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemListarFases() {
		JMenuItem menuItem = new JMenuItem();

		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO:Abrir frame para listar fases
			}
		});

		return menuItem;
	}

	// Menu Disciplinas
	private JMenu getMenuDisciplinas() {
		menuDisciplina = new JMenu();
		menuDisciplina.setText("Disciplina");
		menuDisciplina.setFont(getDefaultFont());

		menuDisciplina.add(getMenuItemCadastrarDisciplinas());
		menuDisciplina.add(getMenuItemListarDisciplinas());
		return menuDisciplina;
	}

	private JMenuItem getMenuItemCadastrarDisciplinas() {
		JMenuItem menuItem = new JMenuItem();

		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		protegerMenuItemBaseadoPerfilUsuario(menuItem);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameCadastrarDisciplina = new CadastrarDisciplinaWindow();
				abrirFrame(frameCadastrarDisciplina);
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemListarDisciplinas() {
		JMenuItem menuItem = new JMenuItem();

		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO:Abrir frame para listar disciplinas
			}
		});

		return menuItem;
	}

	// Menu Cidades
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
				frameCadastrarCidade = new CadastrarCidadeWindow();
				abrirFrame(frameCadastrarCidade);
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
				frameListarCidades = new ListarCidadesWindow(desktop, usuarioLogado);
				abrirFrame(frameListarCidades);
				// Garante que a grid se encaixe na tela depois que a tela é criada
				frameListarCidades.redimensionarGrid(frameListarCidades.getGridContent());
			}
		});

		return menuItem;
	}

	// Menu Usuários
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
				frameCadastrarUsuario = new CadastrarUsuarioWindow();
				abrirFrame(frameCadastrarUsuario);
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemAlterarSenhaUsuario() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Alterar senha");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameAlterarSenha = new AlterarSenhaWindow(usuarioLogado);
				abrirFrame(frameAlterarSenha);
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
				frameListarUsuarios = new ListarUsuariosWindow(desktop, usuarioLogado);
				abrirFrame(frameListarUsuarios);
				// Garante que a grid se encaixe na tela depois que a tela é criada
				frameListarUsuarios.redimensionarGrid(frameListarUsuarios.getGridContent());
			}
		});

		return menuItem;
	}

	// Menu opções

	private JMenu getMenuOpcao() {
		menuOpcao = new JMenu();
		menuOpcao.setText("Utilitários");
		menuOpcao.setFont(getDefaultFont());

		menuOpcao.add(getMenuItemImportar());
		menuOpcao.add(getMenuItemSobre());
		menuOpcao.add(getMenuItemSair());

		return menuOpcao;
	}
	
	private JMenuItem getMenuItemImportar() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Importador");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImportarWindow frameImportar = new ImportarWindow();
				abrirFrame(frameImportar);
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemSobre() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Sobre");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Sistema desenvolvido por:\n\nAlisson Schmitz\n"
								+ "Edvaldo da Rosa\nGiovane Santiago\nVinnicius Mazzuchetti\nVictor Cechinel\n"
								+ "Wilian Hendler");
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemSair() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Sair");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginWindow().setVisible(true);
				setVisible(false);
			}
		});

		return menuItem;
	}

	/*
	 * HELPERS
	 */
	private void protegerMenuItemBaseadoPerfilUsuario(JMenuItem menuItem) {
		// Se usuário for diferente de administrador, desabilita menu item
		if (!usuarioLogado.possuiPerfilAdministrador()) {
			menuItem.setEnabled(false);
		}
	}

	private void abrirFrame(AbstractWindowFrame frame) {
		boolean frameJaExiste = false;

		// Percorre todos os frames adicionados
		for (JInternalFrame addedFrame : desktop.getAllFrames()) {
			// Se o frame a ser adicionado já estiver
			if (addedFrame.getTitle().equals(frame.getTitle())) {
				// Se for uma tela com grid, remove a existente para forçar a atualização da
				// lista
				if (addedFrame instanceof AbstractGridWindow) {
					desktop.remove(addedFrame);

					// Do contrário, apenas atribui o frame ao já existente
				} else {
					frame = (AbstractWindowFrame) addedFrame;
					frameJaExiste = true;
				}

				break;
			}
		}

		try {
			if (!frameJaExiste) {
				desktop.add(frame);
			}

			frame.setSelected(true);
			frame.setMaximum(true);
			frame.setVisible(true);
		} catch (PropertyVetoException e) {
			JOptionPane.showMessageDialog(rootPane, "Houve um erro ao abrir a janela", "", JOptionPane.ERROR_MESSAGE,
					null);
		}
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
}
