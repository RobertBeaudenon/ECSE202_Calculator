//Robert Beaudenon , 260725065
package ass4;



public class listNode {

	listNode previous;
	listNode next;
	String keyVal;


	public listNode(String val) {  //constructor 
		this.keyVal= val;
		this.next = null;
		this.previous= null;
	}
}