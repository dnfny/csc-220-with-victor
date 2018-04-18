package prog07;
import java.util.*;

public class DLLMap <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  protected static class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {

    K key;
    V value;
    Node<K,V> next;
    Node<K,V> previous;
    
    Node (K key, V value) {
      this.key = key;
      this.value = value;
    }
    
    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  protected Node<K, V> head, tail;
  protected int size;
  
  public boolean isEmpty () { return size == 0; }
  
  /** Find a node with a key starting from start.
      @param key The key we are seeking.
      @param start The node we should start from, null if we should
      start from head.
      @return The node with the key.  If there is no node with the
      key, the PREVIOUS node, or null if there is no previous node.
   */
  protected Node<K, V> find (K key, Node start) {
    // Start searching at start if it is not null or head if it is.
    for (Node<K, V> node = start == null ? head : (Node<K, V>) start;
   		node != null; node = node.next) {
    		
      // EXERCISE 5

      // Return node if it has the right key.
    		if(node.getKey().equals(key))
    		{
    			return node;
    		}
      // Return the PREVIOUS node if the key is not there.
    		if(node.getKey().compareTo(key)>0)
    		{
    			return node.previous;
    		}

    }
    
    return tail; 
  }
  
  public boolean containsKey (Object keyAsObject) {
    return containsKey((K) keyAsObject, null);
  }
  
  protected boolean containsKey (K key, Node start) {
    Node<K,V> node = find(key, start);
    return node != null && node.key.equals(key);
  }
  
  public V get (Object keyAsObject) {
    return get((K) keyAsObject, null);
  }
  
  protected V get (K key, Node start) {
    Node<K,V> node = find(key, start);

    // EXERCISE 6
    if(containsKey(key,start))
    {
    		return node.getValue();
    }

    return null;
  }
  
  public V remove (Object keyAsObject) {
    return remove((K) keyAsObject, null);
  }

	  protected V remove (K key, Node start) {
	    Node<K,V> node = find(key, start);

	    // EXERCISE 7
	    if( node == null || !key.equals(node.key))
	    return null;
	    
	    Node<K,V> previous = node.previous;
	    Node<K,V> next = node.next;
	    
	    if(previous != null)
	      previous.next = next;
	    else
	    head = next;
	    
	    if( next != null)
	        next.previous = previous;
	    else
	    tail = previous;
	    
	    /*
	    if(containsKey(key, start)){
	    Node<K,V> rtn = node;
	    if(node == tail){
	    tail = tail.previous;
	   
	    return rtn.value;
	    }
	    node = node.next;
	    return rtn.value;
	    }*/

	    size--;
	    return node.value; // WRONG
	  }      

	  public V put (K key, V value) {
	    return put(key, value, null);
	  }      
	  
	  protected V put (K key, V value, Node start) {
	    Node<K,V> node = find(key, start);

	    // EXERCISE 8
	    if(node != null && key.equals(node.key)){
	    V oldValue = node.value;
	    node.setValue(value);
	    return oldValue;
	    }
	    
	    
	 /*   Node<K,V> next;
	    if( node == null)
	    next = head;
	    else
	    next = node.next;*/
	    Node<K,V> next = node == null ? head : node.next;
	    Node<K,V> previous = node;
	    
	    
	    Node<K,V> addNode =  new Node<K,V>(key,value);
	    
	    addNode.previous = previous;
	    addNode.next = next;
	    
	    if(previous !=null)
	        previous.next = addNode;
	      else
	      head = addNode;
	    
	    if(next != null)
	       next.previous = addNode;
	    else
	    tail = addNode;
	    
	   
	    
	    
	    /*
	    if(node != null){
	    rtn = node.getValue();
	    Node<K,V> tool = new Node<K,V>(key, value);
	    node.previous.key = key;
	    node.previous.setValue(value); }
	    else{
	    Node<K,V> tool = new Node<K,V>(key, value);
	    //tool.key = key;
	    //tool.setValue(value);
	    }*/
	    
	    size++;
	    return null;
	  }           

  public String toString () {
    String s = "";
    for (Node<K,V> node = head; node != null; node = node.next)
      s += node.key + " ";
    s += "\n";
    if (head != null && !(head.value instanceof Node)) {
      for (Node<K,V> node = head; node != null; node = node.next)
        s += node.value + " ";
      s += "\n";
    }
    return s;
  }

  protected class Iter implements Iterator<Map.Entry<K, V>> {
    Node<K, V> next;
    
    Iter () {
      next = head;
    }
    
    public boolean hasNext () { return next != null; }
    
    public Map.Entry<K, V> next () {
      Map.Entry<K, V> ret = next;
      next = next.next;
      return ret;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return size; }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  protected void makeTestList () {
    for (int i = 2; i < 26; i += 2) {
      Node node = new Node("" + (char)('A' + i), i);
      if (size == 0)
        head = tail = node;
      else {
        tail.next = node;
        node.previous = tail;
        tail = node;
      }
      size++;
    }
  }

  public static void main (String[] args) {
    DLLMap<String, Integer> map = new DLLMap<String, Integer>();
    map.makeTestList();
    System.out.println(map);
    System.out.println("containsKey(A) = " + map.containsKey("A"));
    System.out.println("containsKey(C) = " + map.containsKey("C"));
    System.out.println("containsKey(L) = " + map.containsKey("L"));
    System.out.println("containsKey(M) = " + map.containsKey("M"));
    System.out.println("containsKey(Y) = " + map.containsKey("Y"));
    System.out.println("containsKey(Z) = " + map.containsKey("Z"));

    System.out.println("get(A) = " + map.get("A"));
    System.out.println("get(C) = " + map.get("C"));
    System.out.println("get(L) = " + map.get("L"));
    System.out.println("get(M) = " + map.get("M"));
    System.out.println("get(Y) = " + map.get("Y"));
    System.out.println("get(Z) = " + map.get("Z"));

    System.out.println("remove(A) = " + map.remove("A"));
    System.out.println("remove(C) = " + map.remove("C"));
    System.out.println("remove(L) = " + map.remove("L"));
    System.out.println("remove(M) = " + map.remove("M"));
    System.out.println("remove(Y) = " + map.remove("Y"));
    System.out.println("remove(Z) = " + map.remove("Z"));

    System.out.println(map);

    System.out.println("put(A,7) = " + map.put("A", 7));
    System.out.println("put(A,9) = " + map.put("A", 9));
    System.out.println("put(M,17) = " + map.put("M",17));
    System.out.println("put(M,19) = " + map.put("M",19));
    System.out.println("put(Z,3) = " + map.put("Z",3));
    System.out.println("put(Z,20) = " + map.put("Z",20));

    System.out.println(map);
  }
}
