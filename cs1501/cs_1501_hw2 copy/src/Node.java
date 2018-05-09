
public class Node {
	public Word word;
	public Integer frequency;
	public Node next;
	
	public Node(Word word, Integer frequency, Node next){
		this.word = word;
		this.frequency = frequency;
		this.next = next;
	}
}
