package ru.mai.java;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Mikhail Lukoyanov
 */
public class LabaFive {
    static final String INPUT_FILE = "t_five.txt";
    public static void main(String[] args) {
        //создаем входной и выходной потоки
        try (FileInputStream inPut = new FileInputStream(INPUT_FILE)) {
            //создаем объект класса
            Algorithm inParser = new Algorithm(inPut);
            //Вызываем метод класса
            inParser.getAnswer();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Потоки не создались");;
        }

    }
}
