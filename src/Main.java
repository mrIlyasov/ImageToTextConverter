

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static ColorSchema schema = new ColorSchema();
    public static Converter converter = new Converter(0, 0, 0, schema);
    public static String[] menu = {
            "1. Запустить конвертер",
            "2. Задать максимальную ширину",
            "3. Задать максимальную высоту",
            "4. Задать максимальное соотношение сторон",
            "5. Задать цветовую схему",
            "6. Вернуть схему по умолчанию",
            "7. Выход"};

    public static void main(String[] args) throws Exception {

        while (true) {
            printMenu(menu);
            int input = scanner.nextInt();
            switch (input) {
                case 1: {
                    startConverter();
                    break;
                }
                case 2: {
                    System.out.println("Введите максимальную ширину");
                    int maxWidth = scanner.nextInt();
                    converter.setMaxWidth(maxWidth);
                    break;
                }
                case 3: {
                    System.out.println("Введите максимальную высоту");
                    int maxHeight = scanner.nextInt();
                    converter.setMaxHeight(maxHeight);
                    break;
                }
                case 4: {
                    System.out.println("Введите максимальное соотношение ширины к длине");
                    System.out.println("Введите ширину");
                    double width = scanner.nextInt();
                    System.out.println("Введите длину");
                    double height = scanner.nextInt();
                    double ratio = width / height;
                    converter.setMaxRatio(ratio);
                    break;
                }
                case 5: {
                    System.out.println("Введите цветовую схему(6 символов) в одну строку без пробелов \n " +
                            "символы следует вводить от самого темного к самому светлому");
                    String newChars = scanner.next();
                    if (newChars.length()>6 || newChars.length()<6){
                        System.out.println("Вы ввели неверное число символов");
                        System.out.println();
                    }
                    else {
                        schema.changeColors(newChars);
                        converter.setTextColorSchema(schema);

                    }
                    break;
                }
                case 6: {
                    schema.setDefaultChars();
                    converter.setTextColorSchema(schema);

                    break;
                }
                case 7: {
                    System.exit(1);
                    break;
                }
                default: {
                    System.out.println("Команда не распознана");
                    break;
                }
            }

        }
    }

    public static void printMenu(String[] menu) {
        for (int i = 0; i < menu.length; i++) {
            System.out.println(menu[i]);
        }
    }

    public static void startConverter() throws Exception {
        System.out.println("Введите URL адрес картинки");
        String url = scanner.next();
        converter.convert(url);
        if (converter.getRatio() > converter.getMaxRatio() && converter.getMaxRatio() > 0) {
            throw new BadImageSizeException(converter.getRatio(), converter.getMaxRatio());
        }
        System.out.close();
    }

}



