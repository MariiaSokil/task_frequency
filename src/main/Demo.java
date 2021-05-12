package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Demo {
    private static final String KEY = "LOGIC";

    public static void main(String[] args) {
        String input = "I love to work in global logic!";
        input = input.replaceAll("[^a-zA-Z0-9]", " ");

        String withoutSpaces = input.replaceAll("[^a-zA-Z0-9]", "");
        int efficientTotalLength = withoutSpaces.length();

        String[] words = input.toLowerCase().split(" ");

        int frequency = 0;
        String statementInLowerCase = withoutSpaces.toLowerCase();
        String key = KEY.toLowerCase();
        for (char s : statementInLowerCase.toCharArray()) {
            if (key.indexOf(s) >= 0) {
                frequency++;
            }
        }

        List<ResultBean> resultBeanList = new ArrayList<>();
        char[] keyChars = key.toCharArray();
        for (String w : words) {
            int index = 0;
            StringBuilder sb = new StringBuilder();
            for (char k : keyChars) {
                if (w.indexOf(k) >= 0) {
                    if (sb.length() == 0) {
                        sb.append(k);
                    } else {
                        sb.append(",").append(k);
                    }

                    int count = (int) w.codePoints().filter(ch -> ch == k).count();
                    index = index + count;
                }
            }

            ResultBean bean = new ResultBean(index, w.length(), sb.toString(), frequency);
            resultBeanList.add(bean);
        }

        resultBeanList.sort(new MyResultComparator());

        resultBeanList.forEach(System.out::println);
        System.out.printf("TOTAL Frequency: %.2f (%d/%d)%n", (float) frequency / efficientTotalLength, frequency, efficientTotalLength);
    }

    static class ResultBean {
        private final int freq;
        private final int length;
        private final String letters;
        private final int commonFreq;

        ResultBean(int freq, int length, String letters, int commonFreq) {
            this.freq = freq;
            this.length = length;
            this.letters = letters;
            this.commonFreq = commonFreq;
        }

        public int getFreq() {
            return freq;
        }

        public int getLength() {
            return length;
        }

        @Override
        public String toString() {
            return String.format("{(%s), %d} = %.2f (%d/%d)", letters, length, (float) freq / commonFreq, freq, commonFreq);
        }
    }

    static class MyResultComparator implements Comparator<ResultBean> {

        @Override
        public int compare(ResultBean o1, ResultBean o2) {
            return Comparator.comparing(ResultBean::getFreq)
                    .thenComparing(ResultBean::getLength)
                    .compare(o1, o2);
        }
    }
}
