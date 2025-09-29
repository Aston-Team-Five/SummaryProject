package uimenu;
import data.FillArray;
import java.util.*;

import static java.lang.System.*;

public class UIInterface {
    FillArray fillArray = new FillArray();
    public void printMainMenu() {
        int itemMainMenu = 0;
        do {
            // Главное меню
            Scanner scannerItemMainMenu = new Scanner(in);
            out.println("Меню");
            out.println("1) Заполнить массив");
            out.println("2) Сортировать массив");
            out.println("3) Поиск по массиву");
            out.println("4) Выход");
            out.print("Выберите действие: ");

            // При вводе нужного значения запустится нужная функция
            // иначе выведется, что такого в меню нет
            try {
                itemMainMenu = scannerItemMainMenu.nextInt();
                switch (itemMainMenu) {
                    case 1: printFillArrayMenu(); break;
                    case 2: printSortedArrayMenu(); break;
                    case 3: printSearchInArrayMenu(); break;
                    case 4: exit(0);
                    default: out.println("Данного значения нет в меню!");
                }
            }
            // Исключение при вводе не числа
            catch (InputMismatchException ex) {
                out.println("Неккоретно введенно значение в меню!");
            }
            // Из меню можно выйти только если выбрать 4) Выход
        } while (true);
    }

    public void printFillArrayMenu() {
        // Меню заполнение массива
        do {
            out.println("Выберите способ заполнение массива: ");
            out.println("1) Из файла");
            out.println("2) Рандомные значения");
            out.println("3) Ввести значние самостоятельно");
            Scanner scannerItemFillArrayMenu = new Scanner(in);
            out.print("Выберите действие: ");
            // При вводе нужного значения запустится нужная функция
            // иначе выведется, что такого в меню нет
            try {
                int itemFillArrayMenu = scannerItemFillArrayMenu.nextInt();


                switch (itemFillArrayMenu) {
                    case 1: fillArray.FillArrayInFile(); break;
                    case 2: fillArray.FillArrayRandom(); break;
                    case 3: fillArray.FillArrayScanner(); break;
                    default: out.println("Данного значения нет в меню!");
                }
                // Исключение при вводе не числа
            }  catch (InputMismatchException ex) {
                out.println("Неккоретно введенно значение в меню!");
            }
        } while (fillArray.getPersonList() == null);
    }

    public void printSortedArrayMenu() {
        /*TODO: Меню сортировки массива*/
    }

    public void printSearchInArrayMenu(){
        /*TODO: Меню поиска по массиву*/
    }

}
