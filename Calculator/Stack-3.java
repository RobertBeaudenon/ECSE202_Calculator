//Robert Beaudenon , 260725065
package ass4;

public class Stack {
	public listNode top;

	// Push
	public void push(String newStr) {
		listNode mn = new listNode(newStr);

		if (top != null) {
			top.next = mn;
			mn.previous = top;
		}
		top = mn;

	}

	public String top() {
		return top.keyVal;
	}

	// Pop
	public String pop() {
		if (top == null) {
			System.out.println("Error: empty stack");
			return null;
		}

		String result_str = top.keyVal;
		// we have one node in the stack
		if (top.previous == null) {
			top = null;
		} else {
			// we have more than one node in the stack
			listNode pre_top = top.previous;
			pre_top.next = null;
			top = pre_top;

		}
		return result_str;
	}

	public boolean isEmpty() {
		if (top == null) {
			return true;
		}
		return false;
	}
}

