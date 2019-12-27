package io.ilss.datastructure.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.ilss.datastructure.stack.CalculatorUtils.*;

/**
 * @author feng
 */
public class PolandNotation {
    public static void main(String[] args) {
        // 数和符号用空格隔开
        // String suffixExpress = "30 4 + 5 * 6 - ";
        // Stack stack = new LinkedStack();
        //
        // String[] strArr = suffixExpress.split(" ");
        // for (String item : strArr) {
        //     // 处理数字
        //     if (item.matches("\\d+")) {
        //         stack.push(Integer.parseInt(item));
        //         continue;
        //     }
        //     // 符号 就计算
        //     int num2 = stack.pop();
        //     int num1 = stack.pop();
        //     int result = calculator(num1, num2, item.charAt(0));
        //     stack.push(result);
        // }
        // // 164
        // System.out.println("expr: " + suffixExpress + ", result: " + stack.pop());


        String[] infixExprArr = transferToInfixExpr("1+20+3+(3+3)*4");
        String[] suffixExprArr = transferToSuffixExpr(infixExprArr);
        System.out.println(Arrays.toString(suffixExprArr));

    }

    // 表达式转换成中缀表达式
    public static String[] transferToInfixExpr(String expr) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < expr.length(); i++) {
            char temp = expr.charAt(i);
            // 处理为数字的情况
            if (isNum(temp)) {
                // 没到结尾
                StringBuilder builder = new StringBuilder(String.valueOf(temp));
                while (true) {
                    // 多位数处理
                    if (i != expr.length() - 1 && isNum(expr.charAt(i + 1))) {
                        i++;
                        builder.append(expr.charAt(i));
                    } else {
                        break;
                    }
                }
                result.add(builder.toString());
                continue;
            }
            // 处理字符的情况
            result.add(String.valueOf(temp));
        }
        return result.toArray(new String[0]);
    }

    public static String[] transferToSuffixExpr(String[] infixArr) {
        Stack operatorStack = new LinkedStack();
        List<String> result = new ArrayList<>();

        for (String item : infixArr) {
            // 数字
            if (item.matches("\\d+")) {
                result.add(item);
                continue;
            }
            // 左括号
            if ("(".equals(item)) {
                operatorStack.push(item.charAt(0));
                continue;
            }
            // 右括号
            if (")".equals(item)) {
                // 如果是右括号需要pop出符号，放入result 直到遇到左括号
                while (operatorStack.peek() != '(') {
                    result.add(String.valueOf((char) operatorStack.pop()));
                }
                // 然后把左括号 pop出来
                operatorStack.pop();
                continue;
            }
            // 其他符号
            while (!operatorStack.isEmpty() && priority(operatorStack.peek()) >= priority(item.charAt(0))) {
                result.add(String.valueOf((char) operatorStack.pop()));
            }
            operatorStack.push(item.charAt(0));
        }
        // 最后把所有符号栈的符号放入result
        while (!operatorStack.isEmpty()) {
            result.add(String.valueOf((char) operatorStack.pop()));
        }

        return result.toArray(new String[0]);
    }

}