public class TextJustifier {

    public static String[] justifyText(String[] words, int maxWidth) {
        String[] result = new String[words.length];
        int ind = 0;
        int i = 0;

        while (i < words.length) {
            int countWords = 0;
            int curLen = 0;
            int curI = i;


            while (curI < words.length && curLen + words[curI].length() + countWords <= maxWidth) {
                curLen += words[curI].length();
                countWords++;
                curI++;
            }

            StringBuilder newStr = new StringBuilder();
            int spacesToAdd = maxWidth - curLen;


            if (curI == words.length || countWords == 1) {
                for (int j = i; j < curI; j++) {
                    newStr.append(words[j]);
                    if (j < curI - 1) {
                        newStr.append(" ");
                    }
                }

                while (newStr.length() < maxWidth) {
                    newStr.append(" ");
                }
            } else {

                int spacesBetween = spacesToAdd / (countWords - 1);
                int extraSpaces = spacesToAdd % (countWords - 1);

                for (int j = i; j < curI - 1; j++) {
                    newStr.append(words[j]);
                    for (int k = 0; k < spacesBetween + (j - i < extraSpaces ? 1 : 0); k++) {
                        newStr.append(" ");
                    }
                }
                newStr.append(words[curI - 1]);
            }

            result[ind++] = newStr.toString();
            i = curI;
        }


        String[] finalResult = new String[ind];
        System.arraycopy(result, 0, finalResult, 0, ind);

        return finalResult;
    }

    public static void main(String[] args) {
        String[] str = {"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer."};
        String[] newStr = justifyText(str, 20);

        for (String s : newStr) {
            System.out.println(s);
        }
    }
}
