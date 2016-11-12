/**
 *  A simple generic binary tree class to demonstrate the basic principles
 *  of implementing a tree data structure. 
 */

import java.util.Stack;
import java.util.Iterator;


public class BinaryTree<E extends Comparable<E>> implements Tree<E>
{
 
  private TreeNode<E> root;

 
  private static class TreeNode<T extends Comparable<T>>
  {
    /**
     *  Data object reference.
     */
    public T val;

    /**
     *  Left and right child nodes.
     */
    public TreeNode<T> left,right;

    
    public TreeNode(T val, TreeNode<T> left, TreeNode<T> right)
    {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    /**
     * Insert an object into the tree.
     *
     * @param obj object to insert into tree.
     */
    public void insert(T obj)
    {
      if (val.compareTo(obj) < 0)
      {
        if (right != null)
        {
          right.insert(obj) ;
        }
        else
        {
          right = new TreeNode<T>(obj,null,null) ;
        }
      }
      else
      {
        if (left != null)
        {
          left.insert(obj) ;
        }
        else
        {
          left = new TreeNode<T>(obj,null,null) ;
        }
      }
    }

    /**
     * Find an object in the tree. Objects are compared using the compareTo method, so
     * must conform to type Comparable.
     * Two objects are equal if they represent the same value.
     *
     * @param obj Object representing value to find in tree.
     * @return  reference to matching node or null.
     */
    public TreeNode<T> find(T obj)
    {
      int temp = val.compareTo(obj) ;
      if (temp == 0)
      {
        return this ;
      }
      if (temp < 0)
      {
        return (right == null) ? null : right.find(obj) ;
      }
      return (left == null) ? null : left.find(obj) ;
    }

    private TreeNode<T> remove(T obj, TreeNode<T> t)
    {
      if (t == null)
      {
        return t;
      }
      if (obj.compareTo(t.val) < 0)
      {
        t.left = remove(obj,t.left);
      }
      else
      if (obj.compareTo(t.val) > 0 )
      {
        t.right = remove(obj, t.right);
      }
      else
      if (t.left != null && t.right != null)
      {
        t.val = findMin(t.right).val;
        t.right = remove(t.val,t.right);
      }
      else
      {
        t = (t.left != null) ? t.left : t.right;
      }
      return t;
    }

    /**
     * Helper method to find the left most leaf node in a sub-tree.
     *
     * @param t TreeNode to be examined.
     * @return reference to left most leaf node or null.
     */
    private TreeNode<T> findMin(TreeNode<T> t)
    {
      if(t == null)
      {
        return null;
      }
      else
      if(t.left == null)
      {
        return t;
      }
      return findMin(t.left);
    }
  }

  /**
   * Construct an empty tree.
   */
  public BinaryTree()
  {
    root = null ;
  }

  public void add(E obj)
  {
    if (root == null)
    {
      root = new TreeNode<E>(obj,null,null) ;
    }
    else
    {
      root.insert(obj) ;
    }
  }

  /**
   * Determine whether the tree contains an object with the same value as the
   * argument.
   *
   * @param obj reference to Comparable object whose value will be searched for.
   * @return true if the value is found.
   */
  public boolean contains(E obj)
  {
    if (root == null)
    {
      return false ;
    }
    else
    {
      return (root.find(obj) != null) ;
    }
  }

  public void remove(E obj)
  {
    if (root != null)
    {
      root = root.remove(obj,root) ;
    }
  }

  private class PreOrderIterator implements Iterator<E>
  {
    private Stack<TreeNode<E>> nodes = new Stack<TreeNode<E>>() ;

    /**
     * Construct a new iterator for the current tree object.
     */
    public PreOrderIterator()
    {
      pushLeft(root) ;
    }

    public E next()
    {
      if (nodes.isEmpty())
      {
        return null ;
      }
      TreeNode<E> node = nodes.pop() ;
      pushLeft(node.right) ;
      return node.val ;
    }

    /**
     * Determine if there is another object in the iteration sequence.
     *
     * @return true if another object is available in the sequence.
     */
    public boolean hasNext()
    {
      return !nodes.isEmpty() ;
    }

 
    public void remove()
    {
      throw new UnsupportedOperationException();
    }

    /**
     * Helper method used to push node objects onto the stack to keep
     * track of the iteration.
     */
    private void pushLeft(TreeNode<E> node)
    {
      while (node != null)
      {
        nodes.push(node) ;
        node = node.left ;
      }
    }
  }

  /**
   * Return a new tree iterator object.
   *
   * @return new iterator object.
   */
  public Iterator<E> iterator()
  {
    return new PreOrderIterator() ;
  }
}
