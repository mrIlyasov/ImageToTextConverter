


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    private TextColorSchema colorSchema;
    private BufferedImage image;


    public Converter(int maxWidth, int maxHeight, double maxRatio, TextColorSchema ColorSchema) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.colorSchema = new ColorSchema();
    }


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage image = ImageIO.read(new URL(url));
        this.image = image;
        Image scaledImage;
        WritableRaster raster = image.getRaster();
        double multiplier = getMultiplier(image);

        int scaledHeight;
        int scaledWidth;
        if (this.maxWidth > 0 || this.maxHeight > 0) {

            if (image.getWidth() > this.maxWidth || image.getHeight() > this.maxHeight) {

                scaledHeight = (int) ((float) image.getHeight() * multiplier);
                scaledWidth = (int) ((float) image.getWidth() * multiplier);
                scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, BufferedImage.SCALE_SMOOTH);
                BufferedImage bwImg = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.SCALE_DEFAULT);
                Graphics2D graphics = bwImg.createGraphics();
                graphics.drawImage(scaledImage, 0, 0, null);
                raster = bwImg.getRaster();

            } else scaledImage = image;

        } else {
            raster = image.getRaster();
        }

        StringBuilder sb = new StringBuilder();
        int[] colorsIn = new int[4];
        int[][] colors = new int[raster.getHeight()][raster.getWidth()];
        for (int i = 0; i < raster.getWidth(); i++) {
            for (int j = 0; j < raster.getHeight(); j++) {
                raster.getPixel(i, j, colorsIn);
                double red = colorsIn[0] * 0.299;
                double green = colorsIn[1] * 0.587;
                double blue = colorsIn[2] * 0.114;
                int greyColor = (int) (red + green + blue);
                colors[j][i] = greyColor;
            }
        }
        for (int p = 0; p < colors.length; p++) {
            for (int r = 0; r < colors[0].length; r++) {
                char symbol = colorSchema.convert(colors[p][r]);
                sb.append(symbol);
            }
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
        return sb.toString();
    }

    @Override
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        if (this.maxHeight == 0) {
            setMaxHeight(maxWidth);
        }

    }

    @Override
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        if (this.maxWidth == 0) {
            setMaxWidth(maxHeight);
        }
    }


    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }


    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.colorSchema = schema;
    }


    public double getMultiplier(BufferedImage img) {
        double multiplier = 1;
        double multiplierW = ((float) this.maxWidth / img.getWidth());
        double multiplierH = ((float) this.maxHeight / img.getHeight());
        if (this.maxWidth > 0 || this.maxHeight > 0) {
            if (img.getWidth() > maxWidth || img.getHeight() > maxHeight) {
                if (multiplierW < multiplierH) {
                    multiplier = multiplierW;
                } else {
                    multiplier = multiplierH;
                }
            }

        } else {
            multiplier = 1;
        }

        return multiplier;
    }

    public double getRatio() {
        double width = image.getWidth();
        double height = image.getHeight();
        double ratio = width / height;
        return ratio;
    }

    public double getMaxRatio() {
        return maxRatio;
    }


}
