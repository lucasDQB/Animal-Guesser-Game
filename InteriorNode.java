public class InteriorNode implements Node{
    public Node yes; //if the answer is yes, this is the child Node
    public Node no; //if the answer is no, this is the child Node
    private String question; //question the Node represents
    
    public InteriorNode(String question, Node yes, Node no) {
        this.question = question;
        this.yes = yes;
        this.no = no;
    }
    
    public boolean finalAnswer() {
        return false;
    }
    
    public void setInfo(String info) {
        question = info;
    }
    
    public String getInfo() {
        return question;
    }
    
}
