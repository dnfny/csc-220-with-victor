package prog08;
import java.util.*;
 
public class Tree <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {
 
  private class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    K key;
    V value;
    Node left, right;
    
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
  
  private Node root;
  private int size;
 
  public int size () { return size; }
 
  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find (K key, Node<K,V> root) {
    if(root == null)
                                return null;
                if(key.compareTo(root.key)==0)
                return root;
    else if(key.compareTo(root.key)<0) 
    {
                                return find(key, root.left);
    }
                               
    else
                                return find(key, root.right);
  
  }    
 
  public boolean containsKey (Object key) {
    return find((K) key, root) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key, root);
    if (node != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  /**
   * Add key,value pair to tree rooted at root.
   * Return root of modified tree.
   */
  private Node<K,V> add (K key, V value, Node<K,V> root) {
    Node<K,V> n= new Node(key, value);
                  // tree is empty?  return new Node with key and value
                  if(root == null)
                                return n;
                  //key < root key?  recursively add to left subtree 
                  //and replace current left subtree with result
                  //and return root
                  else if (key.compareTo(root.key)<0) 
                  {
                                  root.left = add(key, value, root.left);
                  }
                  //key > root key?  recursively add to right subtree
      //and replace current right subtree with result
      //and return root
                  else 
                  {
                                  root.right = add(key, value, root.right);
                  }
 
    return root;
  }
  
  int depth (Node root) {
    if (root == null)
      return -1;
    return 1 + Math.max(depth(root.left), depth(root.right));
  }
 
  public V put (K key, V value) {
                  Node<K,V> a= find(key,root);
                  if(a != null) 
                  {
                                return a.setValue(value);
                  }
                  root = add(key, value, root);
                  size ++;
                  return null;
    
  }      
  
  public V remove (Object keyAsObject) 
  {
    K key = (K)keyAsObject;
    Node node = remove(key, root);
    if(node == null)
                return null;
    else 
                return (V) node.value;
    
  }
 
  private Node<K,V> remove (K key, Node<K,V> root) {
    Node t = null;
    Node n = root;
    if(root== null)
                                return null;
    if(key.equals(root.key))
                                n = removeRoot(root);
    else if(key.compareTo(root.key)<0) 
    {
                  t = remove(key, root.left);
                  n.left = t;
    }
    else 
    {
                  t = remove(key, root.right);
                  n.right = t;
    }
    this.root = n;
    return n;
  }
 
  /**
   * Remove root of tree rooted at root.
   * Return root of BST of remaining nodes.
   */
  private Node removeRoot (Node root) {
                //left subtree is empty?  return right subtree
                if(root.left == null)  
                return root.right;
                //             right subtree is empty?  return left subtree
                else if(root.right == null)
                                return root.left;
                //Use moveMinToRoot to move the minimum in the right subtree
    //to the root of the right subtree.
                Node m = moveMinToRoot(root.right);
                m.left = root.left;
                this.root = m;
                return m;
  }
 
  /**
   * Move the minimum key (leftmost) node to the root.
   * Return the new root.
   */
  private Node moveMinToRoot (Node root) 
  {
                // left subtree is empty?  return root
                if(root.left == null)  
                                return root;
    //Move the minimum node to the root of the left subtree.
                Node s = root.left;
                Node min = s;
                if(s.left!=null)
                                min = moveMinToRoot(s);
                root.left = min.right;
                min.right = root;
                return min;
                               
  }
 
  public Set<Map.Entry<K, V>> entrySet () { return null; }
  
  public String toString () {
    return toString(root, 0);
  }
  
  private String toString (Node root, int indent) {
    if (root == null)
      return "";
    String ret = toString(root.right, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + root.key + " " + root.value + "\n";
    ret = ret + toString(root.left, indent + 2);
    return ret;
  }
 
  public static void main (String[] args) {
                  Tree<Character, Integer> tree = new Tree<Character, Integer>();
                    String s = "balanced";
                   
                    for (int i = 0; i < s.length(); i++) {
                      tree.put(s.charAt(i), i);
                      System.out.print(tree);
                      System.out.println("-------------");
                    }
 
                    for (int i = 0; i < s.length(); i++) {
                      tree.remove(s.charAt(i));
                      System.out.print(tree);
                      System.out.println("-------------");
  }
}
}
 