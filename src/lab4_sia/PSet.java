
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
    
    private Node node; /** Корень двочиного дерева поиска.*/
    
    /**
     * Создаёт пустое множество.
     */
    public PSet() {
        node = null;
    }
    
    /**
     * Добавляет новый эелемент в множество.
     * @param item  добавляемый элемент
     * @return      true при успешном добавлении, иначе - false
     */
    public boolean add(T item) {
        return traverseAdding(item, node);
    }
    
    /**
     * Рекурсивное добавление item в дерево с корнем node.
     * @param item  добавляемый элемент
     * @param node  корень дерева
     * @return      true при успешном добавлении, иначе - false
     */
    private boolean traverseAdding(T item, Node node) {
        if (node == null) {         // если узел пустой, то создаём его
            node = new Node(item);
            return true;
        } else {
            if (this.node.data.compareTo(item) == 0) {     // иначе, если мы нашли такой же элемент,
                                                           // то заканчиваем на этом
                return false;
            } else if (this.node.data.compareTo(item) > 0) {  // если корень больше, чем item, то
                                                              // рекурсивно работаем с левой частью
                return traverseAdding(item, node.left);
                } else {                                      // если корень меньше, чем item, то
                                                              // рекурсивно работаем с правой частью
                return traverseAdding(item, node.right);
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
        return 0;
    }
    
    /**
     * Возвращает внутреннее двоичное дерево в виде dot-инструкций.
     * @return dot-инструкции
     */
    public String getDotScript() {
        return "";
    }
}
