public class TextJustifier {

    public static String[] justifyText(String[] words, int maxWidth){

        String[] str = new String[words.length];

        for (int i = 0; i < words.length; i++)
        {
            int countWords = 0;
            int curLen = 0;

            int curI = i;
            while(curLen < maxWidth)
            {
                if(curLen + words[i].length() > maxWidth) {
                    break;
                }

                curLen += words[i].length();
                countWords++;
                i++;
            }

            int difference = maxWidth - curLen;
            int countSep = difference / 2;

            for(int j = 0 ; j < countWords; j++){
                str[curI] = words[j];
                curI++;
            }
            str[str.length - 1] = "\n";
        }
        return str;
    }

    public static void main(String[] args) {
        String[] str = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog."};
       String[] newStr = justifyText( str, 11);

        for (String s : newStr) {
            System.out.print(s);
        }

    }

}