import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Dados {
    private String USERS_FILE = "users.dat"; //ARQUIVO DE ARMAZENAMENTO DE TODOS OS USUARIOS
    private String ESTOQUE_FILE = "estoque.dat"; //ARQUIVO DE ARMAZENAMENTO DO ESTOQUE

    private List<Usuario> usuarios;
    private List<Produto> estoque;

    public Dados() {
        loadUsers();
        loadEstoque();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Produto> getEstoque() {
        return estoque;
    }

    @SuppressWarnings("unchecked")
    public void loadUsers() {
        File file = new File(USERS_FILE);
        if (file.length() == 0) {
            this.usuarios = new ArrayList<>();
            return; //arquivo vazio ent n tem nada pra carregar
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            this.usuarios = (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            //arquivo não existe, ent começa com uma lista vazia
            this.usuarios = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); //arquivo foi alterado
        }
    }

    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(this.usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(Usuario u) { //cadastro de usuario
        this.usuarios.add(u);
        saveUsers();
    }

    @SuppressWarnings("unchecked")
    public void loadEstoque() {
        File file = new File(ESTOQUE_FILE);
        if (file.length() == 0) {
            this.estoque = new ArrayList<>();
            return; //arquivo vazio ent n tem nada pra carregar
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ESTOQUE_FILE))) {
            this.estoque = (List<Produto>) ois.readObject();
        } catch (FileNotFoundException e) {
            //arquivo não existe, ent começa com uma lista vazia
            this.estoque = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); //arquivo foi alterado
        }
    }

    public void saveEstoque() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ESTOQUE_FILE))) {
            oos.writeObject(estoque);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProduto(Produto p, Vendedor vendedor) {
        estoque.add(p);
        vendedor.adicionarProduto(p);
        saveEstoque();
        saveUsers();
    }

    public int getProdutoID() {
        if (estoque.isEmpty()) {
            return 1; //lista vazio id = 1
        } else {
            int lastIndex = estoque.size() - 1;
            return estoque.get(lastIndex).getIdProduto() + 1; //retorna o último ID + 1
        }
    }

    public boolean removeProdutoPorId(int idProduto) {
        for (Produto produto : estoque) {
            if (produto.getIdProduto() == idProduto) {
                estoque.remove(produto);
                return true;
            }
        }
        return false; //produto não encontrado
    }


}
