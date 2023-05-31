public class No {
    private String dado;
    private No prox;

    public No(String d, No p) {
        this.dado = d;
        this.prox = p;
    }

    public String getDado() {
        return this.dado;
    }

    public No getProx() {
        return this.prox;
    }

    public void setDado(String s) {
        this.dado = s;
    }

    public void setProx(No n) {
        this.prox = n;
    }
}
