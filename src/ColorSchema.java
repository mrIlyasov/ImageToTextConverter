
import java.util.Arrays;

public class ColorSchema implements TextColorSchema {
    private char[] chars;

    public ColorSchema() {
        this.chars = new char[6];
        this.chars[0] = '█';
        this.chars[1] = '▓';
        this.chars[2] = '▒';
        this.chars[3] = '░';
        this.chars[4] = ':';
        this.chars[5] = ' ';
    }


    public char[] getChars() {
        return this.chars;
    }


    public void setDefaultChars() {
        this.chars[0] = '█';
        this.chars[1] = '▓';
        this.chars[2] = '▒';
        this.chars[3] = '░';
        this.chars[4] = ':';
        this.chars[5] = ' ';
        System.out.println(Arrays.toString(this.chars));
    }


    public void changeColors(String newChars) {

        newChars.getChars(0, this.chars.length, this.chars, 0);
        System.out.println(Arrays.toString(this.chars));

    }

    @Override
    public char convert(int color) {
        if (color >= 235 && color <= 255) {
            return this.chars[5];
        }
        if (color >= 200 && color <= 235) {
            return this.chars[4];
        }
        if (color >= 150 && color < 200) {
            return this.chars[3];
        }
        if (color >= 100 && color < 150) {
            return this.chars[2];
        }
        if (color >= 50 && color < 100) {
            return this.chars[1];
        } else {
            return this.chars[0];
        }

    }


}
