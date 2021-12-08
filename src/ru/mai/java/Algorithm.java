package ru.mai.java;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Mikhail Lukoyanov
 */
public class Algorithm {
    //Регулярные выражения
    private static final String REGEX_ONE =
            "([a-z]*\s?[abcd])\s([a-z]+)\s([a-z]*\s?[abcd])\s([a-z]+)\s([a-z]*\s?[abcd])\s([a-z]+)\s([a-z]*\s?[abcd])";
    private static final String REGEX_TWO = "a=(\\D+)\sb=(\\D+)\sc=(\\D+)\sd=(\\D+)";
    private static final String REGEX_THREE = "not";

    //Входной поток
    private InputStream inPut;
    static Scanner in;

    private boolean a, b, c, d;

    boolean flagA = false;
    boolean flagB = false;

    public Algorithm(InputStream inPut) {
        this.inPut = inPut;
    }

    //Основной метод класса
    public void getAnswer() {
        in = new Scanner(inPut);
        Pattern patternOne = Pattern.compile(REGEX_ONE);
        Pattern patternTwo = Pattern.compile(REGEX_TWO);

        //Считываем все строчки из файла в список
        List<String> list = in.findAll(".+")
                .map(MatchResult::group)
                .collect(Collectors.toList());

        Matcher matcherOne = patternOne.matcher(list.get(0));
        Matcher matcherTwo = patternTwo.matcher(list.get(1));

        //Сравниваем со вторым регулярным выражением
        if (matcherTwo.find()) {
            //Присваиваем значения к переменным
            a = getParam(matcherTwo.group(1));
            b = getParam(matcherTwo.group(2));
            c = getParam(matcherTwo.group(3));
            d = getParam(matcherTwo.group(4));
        } else {
            System.out.println("Неверный формат данных во второй строчке");
        }

        //Сравниваем с первым регулярным выражением
        if(matcherOne.find()) {
            //Изменяем значения у переменных
            a = changeParam(a, matcherOne.group(1));
            b = changeParam(b, matcherOne.group(3));
            c = changeParam(c, matcherOne.group(5));
            d = changeParam(d, matcherOne.group(7));


            //Сравниваем стрчку для первого знака
            switch (matcherOne.group(2)) {
                case "or" : flagA = false;
                    break;
                case "and" : b = a & b;
                    flagA = true;
                    break;
                default :
                    System.out.println("Неверный формат данных в первой строчке");
                    break;
            }
            //Сравниваем стрчку для второго знака
            switch (matcherOne.group(4)) {
                case "or" : flagB = false;
                    break;
                case "and" : c = b & c;
                    flagB = true;
                    break;
                default :
                    System.out.println("Неверный формат данных в первой строчке");
                    break;
            }

            //Сравниваем стрчку для третьего знака
            switch (matcherOne.group(6)) {
                case "or" : d = c || d;
                    break;
                case "and" : d = c & d;
                    break;
                default :
                    System.out.println("Неверный формат данных в первой строчке");
                    break;
            }

            //Проверяем флаг б
            if (!flagB) {
                d = b || d;
            }

            //Проверяем флаг а
            if (!flagA) {
                d = a || d;
            }

            //Выводим ответ
            System.out.println(d);
        } else {

            System.out.println("Неверный формат данных в первой строчке");
        }

    }

    //Метод получения значений переменных
    private boolean getParam(String param) {
        boolean resultParam = false;
        //Сравниваем значение у переменной
        switch (param) {
            case "TRUE" : resultParam = true;
                break;
            case "FALSE" : resultParam = false;
                break;
        }
        return resultParam;
    }

    //Метод изменения значений переменных
    private boolean changeParam(boolean param, String flag) {
        Pattern pattern = Pattern.compile(REGEX_THREE);
        Matcher matcher = pattern.matcher(flag);
        if (matcher.find()) {
            param = !param;
        }
        return param;
    }

}
