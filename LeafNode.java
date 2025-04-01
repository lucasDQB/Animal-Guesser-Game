public class LeafNode implements Node {
    private String guess; //the animal represented by the Node that computer is trying to guess
    
    public LeafNode(String guess) {
        this.guess = guess;
    }
    
    public boolean finalAnswer() {
        return true;
    }
    
    public void setInfo(String info) {
        guess = info;
    }
    
    public String getInfo() {
        return guess;
    }
    
}
