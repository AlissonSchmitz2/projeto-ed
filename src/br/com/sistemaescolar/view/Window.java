package br.com.sistemaescolar.view;

import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import br.com.sistemaescolar.model.Usuario;

public class Window extends JFrame {
	private static final long serialVersionUID = 3283754083146407662L;

	// Guardar aqui o usu·rio logado
	private Usuario usuarioLogado;

	private JMenu menuAlunos;
	private JMenu menuCursos;
	private JMenu menuDisciplinas;
	private JMenu menuFases;
	private JMenu menuCidades;
	private JMenu menuUsuarios;
	private JMenu menuOpcao;
	private JMenu menuProfessores;
	private JMenu menuGrade;

	private JDesktopPane desktop;
	
	private ListarAlunosWindow frameListarAlunos;
	private CadastrarAlunosWindow frameCadastrarAlunos;
	private CadastrarCidadesWindow frameCadastrarCidade;
	private ListarCidadesWindow frameListarCidades;
	private CadastrarUsuariosWindow frameCadastrarUsuario;
	private AlterarSenhaWindow frameAlterarSenha;
	private ListarUsuariosWindow frameListarUsuarios;
	private CadastrarCursosWindow frameCadastrarCursos;
	private CadastrarDisciplinasWindow frameCadastrarDisciplina;
	private CadastrarFasesWindow frameCadastrarFase;
	private CadastrarProfessoresWindow frameCadastrarProfessores;
	private ListarProfessoresWindow frameListarProfessores;
	private CadastrarGradeWindow frameCadastrarGrade;
	private ListarGradesWindow frameListarGrades;
	private ListarCursosWindow frameListarCursos;
	private ListarFasesWindow frameListarFases;
	private ListarDisciplinasWindow frameListarDisciplinas;
	
	private JLabel wallpaper;
	
	public Window(Usuario usuarioLogado) {
		super();

		this.usuarioLogado = usuarioLogado;

		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		desktop.setVisible(true);
		setContentPane(desktop);
		
		
		URL url = this.getClass().getResource("/br/com/sistemaescolar/icons/t.png");
		Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url);
		this.setIconImage(imagemTitulo);
		
	    ImageIcon logo = new ImageIcon(this.getClass().getResource("/br/com/sistemaescolar/icons/wallpaper.jpg"));
		wallpaper = new JLabel(logo);
		
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		wallpaper.setBounds(0, -50, screenSize.width, screenSize.height);
		getContentPane().add(wallpaper);
		
		inicializar();

		//Full screen
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void inicializar() {
		String dataLogin = getDateTime();
		this.setTitle("Sistema Escolar v2.0.0-betha     " + "Usu·rio Logado: " + usuarioLogado.getLogin() + " ("
				+ usuarioLogado.getPerfil() + ")" + " - ⁄ltimo Login: " + dataLogin);
		this.setJMenuBar(getWindowMenuBar());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(0, 0, 796, 713));
		this.setFocusableWindowState(true);
		getContentPane().setBackground(new Color(247, 247, 247));
		
	}

	/*
	 * MENU DE NAVEGA«√O
	 */
	private JMenuBar getWindowMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getMenuCursos());
		menuBar.add(getMenuFases());
		menuBar.add(getMenuDisciplinas());
		menuBar.add(getMenuProfessores());
		menuBar.add(getMenuGrade());
		menuBar.add(getMenuAlunos());	
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
				// Garante que a grid se encaixe na tela depois que a tela √© criada
				frameListarAlunos.redimensionarGrid(frameListarAlunos.getGridContent());
			}
		});

		return menuItem;
	}

	// Menu Professores
	private JMenu getMenuProfessores() {
		menuProfessores = new JMenu();
		menuProfessores.setText("Professores");
		menuProfessores.setFont(getDefaultFont());

		menuProfessores.add(getMenuItemCadastrarProfessores());
		menuProfessores.add(getMenuItemListarProfessores());

		return menuProfessores;
	}

	private JMenuItem getMenuItemCadastrarProfessores() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		protegerMenuItemBaseadoPerfilUsuario(menuItem);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frameCadastrarProfessores = new CadastrarProfessoresWindow();
				abrirFrame(frameCadastrarProfessores);

			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemListarProfessores() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameListarProfessores = new ListarProfessoresWindow(desktop, usuarioLogado);
				abrirFrame(frameListarProfessores);
				// Garante que a grid se encaixe na tela depois que a tela √© criada
				frameListarProfessores.redimensionarGrid(frameListarProfessores.getGridContent());
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
				frameListarCursos = new ListarCursosWindow(desktop, usuarioLogado);
				abrirFrame(frameListarCursos);
				// Garante que a grid se encaixe na tela depois que a tela È criada
				frameListarCursos.redimensionarGrid(frameListarCursos.getGridContent());
			}
		});
		return menuItem;
	}

	// Menu Fases
	private JMenu getMenuFases() {
		menuFases = new JMenu();
		menuFases.setText("Fases");
		menuFases.setFont(getDefaultFont());

		menuFases.add(getMenuItemCadastrarFases());
		menuFases.add(getMenuItemListarFases());
		return menuFases;
	}

	private JMenuItem getMenuItemCadastrarFases() {
		JMenuItem menuItem = new JMenuItem();

		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		protegerMenuItemBaseadoPerfilUsuario(menuItem);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameCadastrarFase = new CadastrarFasesWindow();
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
				frameListarFases = new ListarFasesWindow(desktop, usuarioLogado);
				abrirFrame(frameListarFases);
				// Garante que a grid se encaixe na tela depois que a tela È criada
				frameListarFases.redimensionarGrid(frameListarFases.getGridContent());
			}
		});

		return menuItem;
	}

	// Menu Disciplinas
	private JMenu getMenuDisciplinas() {
		menuDisciplinas = new JMenu();
		menuDisciplinas.setText("Disciplinas");
		menuDisciplinas.setFont(getDefaultFont());

		menuDisciplinas.add(getMenuItemCadastrarDisciplinas());
		menuDisciplinas.add(getMenuItemListarDisciplinas());
		return menuDisciplinas;
	}

	private JMenuItem getMenuItemCadastrarDisciplinas() {
		JMenuItem menuItem = new JMenuItem();

		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		protegerMenuItemBaseadoPerfilUsuario(menuItem);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameCadastrarDisciplina = new CadastrarDisciplinasWindow();
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
				frameListarDisciplinas = new ListarDisciplinasWindow(desktop, usuarioLogado);
				abrirFrame(frameListarDisciplinas);
				// Garante que a grid se encaixe na tela depois que a tela È criada
				frameListarDisciplinas.redimensionarGrid(frameListarDisciplinas.getGridContent());
			
			}
		});

		return menuItem;
	}
	
	//Menu Grade
	
		private JMenu getMenuGrade() {
			menuGrade = new JMenu();
			menuGrade.setText("Grade");
			menuGrade.setFont(getDefaultFont());
			
			menuGrade.add(getMenuItemCadastrarGrade());
			menuGrade.add(getMenuItemListarGrade());
			return menuGrade;
		}
		
		private JMenuItem getMenuItemCadastrarGrade() {
			JMenuItem menuItem = new JMenuItem();
			
			menuItem.setText("Cadastrar");
			menuItem.setFont(getDefaultFont());
			
			protegerMenuItemBaseadoPerfilUsuario(menuItem);
			
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frameCadastrarGrade = new CadastrarGradeWindow();
					abrirFrame(frameCadastrarGrade);
				}
			});
			
			return menuItem;
		}
		
		private JMenuItem getMenuItemListarGrade() {
			JMenuItem menuItem = new JMenuItem();

			menuItem.setText("Listar");
			menuItem.setFont(getDefaultFont());

			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frameListarGrades = new ListarGradesWindow(desktop, usuarioLogado);
					abrirFrame(frameListarGrades);
					// Garante que a grid se encaixe na tela depois que a tela È criada
					frameListarGrades.redimensionarGrid(frameListarGrades.getGridContent());
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
				frameCadastrarCidade = new CadastrarCidadesWindow();
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
				// Garante que a grid se encaixe na tela depois que a tela √© criada
				frameListarCidades.redimensionarGrid(frameListarCidades.getGridContent());
			}
		});

		return menuItem;
	}

	// Menu Usu√°rios
	private JMenu getMenuUsuarios() {
		menuUsuarios = new JMenu();
		menuUsuarios.setText("Usu·rios");
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
				frameCadastrarUsuario = new CadastrarUsuariosWindow();
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
				// Garante que a grid se encaixe na tela depois que a tela √© criada
				frameListarUsuarios.redimensionarGrid(frameListarUsuarios.getGridContent());
			}
		});

		return menuItem;
	}

	// Menu opÁıes

	private JMenu getMenuOpcao() {
		menuOpcao = new JMenu();
		menuOpcao.setText("Utilit·rios");
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
		// Se usu√°rio for diferente de administrador, desabilita menu item
		if (!usuarioLogado.possuiPerfilAdministrador()) {
			menuItem.setEnabled(false);
		}
	}

	private void abrirFrame(AbstractWindowFrame frame) {
		boolean frameJaExiste = false;

		// Percorre todos os frames adicionados
		for (JInternalFrame addedFrame : desktop.getAllFrames()) {
			// Se o frame a ser adicionado j√° estiver
			if (addedFrame.getTitle().equals(frame.getTitle())) {
				// Se for uma tela com grid, remove a existente para for√ßar a atualiza√ß√£o da
				// lista
				if (addedFrame instanceof AbstractGridWindow) {
					desktop.remove(addedFrame);

					// Do contr√°rio, apenas atribui o frame ao j√° existente
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
