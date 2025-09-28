import java.util.*;

public class AbstractPartIterator<AbstractProduct> implements Iterator<AbstractProduct> {

    private Stack<Iterator<AbstractProduct>> stack = new Stack<Iterator<AbstractProduct>>();
    private HashMap<AbstractBaseIngredient, Double> baseIngredientsAPM;
    private HashMap<AbstractPart, Double> partsAPM;

    public AbstractPartIterator(AbstractPart start) {
        Iterator<AbstractProduct> iterator = (Iterator<AbstractProduct>) start.getChildNodes().keySet().iterator();
        stack.push(iterator);
    }
    public AbstractProduct next() {
        if(hasNext()) {
            Iterator<AbstractProduct> iterator = stack.peek();
            AbstractProduct product = iterator.next();
            if(product instanceof AbstractPart pro) {
                if(!pro.getChildNodes().isEmpty()) {
                    stack.push((Iterator<AbstractProduct>) pro.getChildNodes().keySet().iterator());
                }
            }
            return product;
        } else {
            throw new NoSuchElementException();
        }
    }
    public boolean hasNext() {
        if(stack.isEmpty()) {
            return false;
        } else  {
            Iterator<AbstractProduct> iterator = stack.peek();
            if(!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }
}
