import java.util.Random;

public class DiceImpl implements Dice{
    private int randResult;
    private Random rand = new Random();

    @Override
    public void ThrowDices() {
        randResult = rand.nextInt(6) + 1; 
    }

    public int getDicesResult(){
        return randResult;
    }

}
