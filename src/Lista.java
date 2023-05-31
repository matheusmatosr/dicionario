public class Lista {
    private No cabec;
    private int tamanho;

    public Lista() {
        cabec = null;
        tamanho = 0;
    }

    public void inserePosicao(No n, int pos) {
        No auxi = cabec;
        int c = 0;
        if (pos > this.tamanho) {
            while (auxi.getProx != null) {
                auxi = auxi.getProx;
            }
            auxi.setProx(n);
            tamanho++;
        } else {
            while (c < pos) {
                auxi = auxi.getProx();
                c++;
            }
            n.setProx(auxi.getProx());
            auxi.setProx(n);
            this.tamanho++;
        }
    }

    public void excluiPosicao(No n, int posi) {
        No auxi = cabec;
        int i = 0;
        if (posi == this.tamanho) {
            while (i < posi) {
                auxi = auxi.getProx();
                i++;
            }
            auxi.setProx(auxi.getProx().getProx());
        } else {
            System.out.println("insere");
        }
    }
}
