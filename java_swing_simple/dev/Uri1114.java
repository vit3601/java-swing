

import javax.swing.JOptionPane;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Uri1114 {

  private static final String FILE_NAME = "pessoas.ser";

  public static void main(String[] args) {

    Set<Pessoa> pessoasRepository = carregarPessoas();

    int code = JOptionPane.showConfirmDialog(null, "Você tem conta?", "Bem-vindo!", JOptionPane.YES_NO_OPTION);

    if (code == JOptionPane.YES_OPTION) {
      JOptionPane.showMessageDialog(null, "Entre em sua conta:", "Contas", JOptionPane.DEFAULT_OPTION);

      Pessoa p1 = account(pessoasRepository);
    

      if (pessoasRepository.contains(p1)) {
        mostrarMensagemSucesso(p1);
      } else {
        criarContaEMostrarMensagemSucesso(pessoasRepository);
      }
    } else {
      criarContaEMostrarMensagemSucesso(pessoasRepository);
    }

    mostrarTodasAsPessoas(pessoasRepository);
    salvarPessoas(pessoasRepository);

    int codeDelete = JOptionPane.showConfirmDialog(null, "Deletar todas contas?", "Deletar!",
        JOptionPane.YES_NO_OPTION);

    if (codeDelete == JOptionPane.YES_OPTION) {

      deletarPessoasRepositoryComMenssagem(pessoasRepository);
      mostrarTodasAsPessoas(pessoasRepository);
      JOptionPane.showMessageDialog(null, "Até Logo! ", "Sucesso!", JOptionPane.CLOSED_OPTION);

    } else {

      JOptionPane.showMessageDialog(null, "Até Logo! ", "Sucesso!", JOptionPane.CLOSED_OPTION);
    }

  }

  private static void deletarPessoasRepositoryComMenssagem(Set<Pessoa> pessoasRepository) {

    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
      pessoasRepository.clear();
      oos.writeObject(pessoasRepository);
    } catch (IOException e) {
      e.printStackTrace();
    }

    JOptionPane.showMessageDialog(null, "A lista foi deletada", "Sucesso", JOptionPane.PLAIN_MESSAGE);

  }

  private static void criarContaEMostrarMensagemSucesso(Set<Pessoa> pessoasRepository) {
    JOptionPane.showMessageDialog(null, "Crie uma conta:", "Crie sua conta",
        JOptionPane.PLAIN_MESSAGE);

    Pessoa p2 = account(pessoasRepository);
    pessoasRepository.add(p2);

    mostrarMensagemSucesso(p2);
  }

  private static void mostrarMensagemSucesso(Pessoa pessoa) {
    JOptionPane.showMessageDialog(null, "Bem-vindo " + pessoa.getUsername() + "!", "Sucesso!",
        JOptionPane.CLOSED_OPTION);
  }

  private static void mostrarTodasAsPessoas(Set<Pessoa> pessoasRepository) {
    StringBuilder table = new StringBuilder(
        "<html><body><table border='1'><tr><th>Username</th><th>Password</th></tr>");

    for (Pessoa pessoa : pessoasRepository) {
      table.append("<tr><td>").append(pessoa.getUsername()).append("</td><td>").append(pessoa.getPassword())
          .append("</td></tr>");
    }

    table.append("</table></body></html>");
    JOptionPane.showMessageDialog(null, table.toString(), "Todas as Pessoas Cadastradas", JOptionPane.PLAIN_MESSAGE);
  }

  private static void salvarPessoas(Set<Pessoa> pessoasRepository) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
      oos.writeObject(pessoasRepository);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  private static Set<Pessoa> carregarPessoas() {
    Set<Pessoa> pessoas = new HashSet<>();

    File file = new File(FILE_NAME);
    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
        pessoas = (Set<Pessoa>) ois.readObject();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    return pessoas;
  }


  public static Pessoa account(Set<Pessoa> list) {
    Pessoa pessoa = new Pessoa();

    pessoa.setUsername(JOptionPane.showInputDialog("Digite seu username:"));
    while (pessoa.getUsername().isBlank() || usernameExists(pessoa.getUsername(), list)) {
        if (pessoa.getUsername().isBlank()) {
            pessoa.setUsername(JOptionPane.showInputDialog("O username não pode ser nulo. Tente novamente:"));
        } else {
            pessoa.setUsername(JOptionPane.showInputDialog("O username '" + pessoa.getUsername() + "' já existe. Tente outro:"));
        }
    }

    pessoa.setPassword(JOptionPane.showInputDialog("Digite sua senha:"));
    while (pessoa.getPassword().isBlank()) {
        pessoa.setPassword(JOptionPane.showInputDialog("A senha não pode ser nula. Tente novamente:"));
    }

    return pessoa;
}

private static boolean usernameExists(String username, Set<Pessoa> list) {
    for (Pessoa p : list) {
        if (p.getUsername().equals(username)) {
            return true;
        }
    }
    return false;
}


}

class Pessoa implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;
  private String password;

  public Pessoa() {
  }

  public Pessoa(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Pessoa other = (Pessoa) obj;
    return username != null ? username.equals(other.username) : other.username == null;
  }
}
