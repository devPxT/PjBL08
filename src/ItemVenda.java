public class ItemVenda extends Produto {
    private String nomeItem;
    private String descricao;

    public ItemVenda(int idProduto, double preco, String nomeItem, String descricao) {
        this.nomeItem = nomeItem;
        this.descricao = descricao;
    }

    @Override
    public void imprimeDescricao() {
        System.out.println("Nome do item: " + nomeItem + ", Descrição: " + descricao);
    }
}
