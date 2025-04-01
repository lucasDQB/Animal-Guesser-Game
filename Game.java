import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game { 
//problem when it is a yes/no child and the answer for the new questions is no/yes

    private static final String FILE_NAME = "TwentyQuestionsData.txt";
    private static Scanner scanner;  // Global Scanner for reading input
    private static PrintWriter writer; //Global PrinterWriter for writing output

    public static void main(String[] args) {
        Node root;  // Pointer to the root of the question tree.


        // Initialize Scanner to read from the file.
        try {
            scanner = new Scanner(new File(FILE_NAME));  
            root = readTree();  // Read the question tree.
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No data file found.");
            System.out.println("Starting from scratch.");
            root = new LeafNode("a dog");
        }

        // Re-initialize Scanner to read from standard input
        scanner = new Scanner(System.in);
        System.out.println("Welcome to the game of Twenty Questions!");

        while (true) {
            System.out.println("\nThink of any item in the category animal!");
            System.out.println("OK, let's begin.\n");
            play(root);  // Use the global Scanner for user input
        
            System.out.println("Do you want to play again? (yes/no) ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("yes"))
                break;
        }//ERROR AFTER THIS

        // Write the updated tree back to the file
        try {
            writer = new PrintWriter(FILE_NAME);
            writeTree(root);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error while saving data to file " + FILE_NAME + ": " + e);
        }

        System.out.println("\nBye!");
    }

    private static Node readTree() {
	if (!scanner.hasNextLine()) return null;
        String line = scanner.nextLine().trim();
        int firstSpaceIndex = line.indexOf(' ');
        if (firstSpaceIndex == -1) return null;

	//finalAnswer will hold true or false, first token in each line in the file
	//this represents if the Node represents a final answer (true) or not (false)
        boolean finalAnswer = Boolean.parseBoolean(line.substring(0, firstSpaceIndex));
        String info = line.substring(firstSpaceIndex + 1).trim();
        //TO DO: finish writing method to recursively read file into tree
        
        if(finalAnswer){
            return new LeafNode(info);
        }else{
            return new InteriorNode(info, readTree(), readTree());
        }

    }

    private static void writeTree(Node tree) {
        if (tree == null) return; //already here in original version
        if(tree.finalAnswer()){
            writer.write("true " + tree.getInfo() + "\n");
        }
        else{
            writer.write("false " + tree.getInfo() + "\n");
            writeTree(((InteriorNode) tree).yes);
            writeTree(((InteriorNode) tree).no);
        }
        
        //TO DO: write method recursively to write tree to file in preorder traversal (root, yes child, no child)

    }

    private static void play(Node root) {
	//TO DO: 
        //use equalsIgnoreCase to accept case insensitive user input
        if(root==null) return;
        InteriorNode parent = (InteriorNode)(root); //pointer to parent
        String userAnswer = ""; //stores user's yes/no answers
        while(true){
            if(!root.finalAnswer()){ //if it's an interior node
                System.out.println(root.getInfo() + ""); 
                userAnswer = scanner.nextLine(); 
                if(userAnswer.trim().equalsIgnoreCase("yes")){ 
                    if(((InteriorNode)root).yes.finalAnswer()){ 
                        parent = (InteriorNode)(root); //update parent
                    }
                    root = ((InteriorNode)root).yes; //update root to yes child and go back to while loop
                }
                else if(userAnswer.trim().equalsIgnoreCase("no")){
                    if(((InteriorNode)root).no.finalAnswer()){
                        parent = (InteriorNode)(root);
                    }
                    root = ((InteriorNode)root).no;
                }
            }
            else{ //if its leafnode
                System.out.println("Are you thinking of: " + root.getInfo());
                if(scanner.nextLine().trim().equalsIgnoreCase("yes")){
                    System.out.println("I guessed correct!");
                    break;
                }
                else{
                    System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO\nNOOOOOOOOOOOOOOOOOOOOOOOOOOOO\n\n\n\n\nNOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO\n\nWhat were you thinking of?");
                    String animal = scanner.nextLine();
                    System.out.println("Please help me learn! What question could I have asked to choose between " + root.getInfo() + " and " + animal + "?");
                    String question = scanner.nextLine();
                    System.out.println("For " + animal + ", what is the answser to the question \"" + question + "\" Yes or no?");
                    String answer = scanner.nextLine();
                    if(answer.trim().equalsIgnoreCase("yes")){
                        Node yes = new LeafNode(animal);
                        Node no = new LeafNode(root.getInfo());
                        if(userAnswer.trim().equalsIgnoreCase("yes")){
                            parent.yes = new InteriorNode(question, yes, no);
                        }
                        else if(userAnswer.trim().equalsIgnoreCase("no")){
                            parent.no = new InteriorNode(question, yes, no);
                        }
                    }
                    else if(answer.trim().equalsIgnoreCase("no")){
                        Node no = new LeafNode(animal);
                        Node yes = new LeafNode(root.getInfo());
                        if(userAnswer.trim().equalsIgnoreCase("yes")){
                            parent.yes = new InteriorNode(question, yes, no);
                        }
                        else if(userAnswer.trim().equalsIgnoreCase("no")){
                            parent.no = new InteriorNode(question, yes, no);
                        }
                    }
                    else{
                        System.out.println("What the heck that wasn't an option");
                    }
                }
                break;
            }
        }
    }
}
