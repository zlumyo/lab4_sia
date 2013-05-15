
package lab4_sia;

/**
 * Контейнер типа "множество".
 * 
 * @author      Владимир
 * @param <T>   тип элементов множества 
 */
public class PSet<T extends Comparable> {
    
    /**
     * Узел двоичного дерева.
     * @author Владимир
     * @param <T>   тип данных хранимых в узле
     */
    private class Node<T extends Comparable> {
        public T data;              /** Данные хранящиеся в узле.*/
        public Node left, right;    /** Дочерние элементы узла.*/
        private byte difference;    /** Разность между высотами левого и правого поддеревьев.*/
        
        /**
         * Создаёт узел с данными.
         */
        public Node(T data) {
            this.data = data;
            this.difference = 0;
            left = null;
            right = null;
        }
    }
    
    private Node root; /** Корень двочиного дерева поиска.*/
    
    /**
     * Создаёт пустое множество.
     */
    public PSet() {
        root = null;
    }
    
    /**
     * Добавляет новый эелемент в множество.
     * @param item  добавляемый элемент
     * @return      true при успешном добавлении, иначе - false
     */
    public boolean add(T item) {
        return (traverseAdding(item, root) == null ? false : true);
    }
    
    /**
     * Рекурсивное добавление item в дерево с корнем node.
     * @param item  добавляемый элемент
     * @param node  корень дерева
     * @return      true при успешном добавлении, иначе - false
     */
    private Node traverseAdding(T item, Node node) {
        if (node == null) {         // если узел пустой, то создаём его
            return new Node(item);
        } else {
            if (node.data.compareTo(item) == 0) {     // иначе, если мы нашли такой же элемент,
                                                           // то заканчиваем на этом
                return null;
            } else if (node.data.compareTo(item) > 0) {  // если корень больше, чем item, то
                                                              // рекурсивно работаем с левой частью
                return node.left = traverseAdding(item, node.left);
                } else {                                      // если корень меньше, чем item, то
                                                              // рекурсивно работаем с правой частью
                return node.right = traverseAdding(item, node.right);
            }
        }
    }
    
    /**
     * Удаляет указанный элемент из множества.
     * @param item  удаляемый элемент
     * @return      true при успешном удалении, иначе - false
     */
    public boolean remove(T item) {
        return true;
    }
    
    /**
     * Проверяет наличие указанного элемента в множестве.
     * @param item  проверяемый элемент
     * @return      true при успешном нахождении, иначе - false
     */
    public boolean contains(T item) {
        return true;
    }
    
    /**
     * Возвращает мощность множества.
     * @return мощность множества
     */
    public int size() {
        return traverseCounting(root);
    }
    
    private int traverseCounting(Node node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + traverseCounting(node.left) + traverseCounting(node.right);
        }
    }
    
    /**
     * Возвращает внутреннее двоичное дерево в виде dot-инструкций.
     * @return dot-инструкции
     */
    public String getDotScript() {
        return "";
    }
}
