package servertestapp.logic;

import servertestapp.config.Logger;

public class Factorization {

    int number;

    /**
     * Constructor
     *
     * @param number for decomposition
     */
    public Factorization(int number) {
        this.number = number;
    }

    /**
     * Number's factors decomposition
     *
     * @return decomposition string
     */
    public String factor() {
        StringBuilder sb = new StringBuilder();
        if (number == 0) {
            return "0";
        }
        if (number < 0) {
            sb.append("-");
            number = -number;
        }
        if (number == 1)
            return sb.append(1).toString();
        int factor = 2;
        Logger.log(String.format("Factorization of %d:", number));
        while (number > 1 && factor * factor <= number) {
            while (number % factor == 0) {
                sb.append(factor + " * ");
                number /= factor;
            }
            ++factor;
        }
        if (number > 1) {
            sb.append(number + " * ");
        }
        return transformResult(sb);
    }
    
    /**
     * Transform result to correct-form
     * @param sb - incorrect result
     * @return result string
     */
    private String transformResult(StringBuilder sb){
        if (sb.length() < 4)
            return sb.toString();
        return sb.toString().substring(0, sb.length() - 3);
    }

}
