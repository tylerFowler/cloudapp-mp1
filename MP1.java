import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Override;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.StringTokenizer;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@\\[\\]\\(\\)\\{}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];

        try (BufferedReader br = new BufferedReader(
            new FileReader(this.inputFileName))
        ) {
            String line = null;
            HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
            List<Integer> indexes = Arrays.asList(getIndexes());

            ArrayList<String> file = new ArrayList<String>();

            while ((line = br.readLine()) != null) {
                file.add(line);
            }

            for (int idx : Arrays.asList(getIndexes())) {
                StringTokenizer st = new StringTokenizer(file.get(idx), delimiters);

                while (st.hasMoreTokens()) {
                    String cleanWord = st.nextToken().trim().toLowerCase();

                    // ignore blacklisted "filler" words
                    if (Arrays.asList(stopWordsArray).contains(cleanWord)) continue;

                    // if not in the list, then add it, otherwise just bump the word count
                    if (wordMap.containsKey(cleanWord))
                        wordMap.put(cleanWord, wordMap.get(cleanWord) + 1);
                    else
                        wordMap.put(cleanWord, 0);
                }
            }

            // TreeMap sorts by Key so we need a custom comparator that has the context of the source map
            FreqComp comp = new FreqComp(wordMap);
            TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(comp);
            sortedMap.putAll(wordMap);

            int i = 0;
            System.out.println("Found " + sortedMap.size() + " keys");
            for (Map.Entry entry : sortedMap.descendingMap().entrySet()) {
                if (i >= ret.length) break;

                System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
                ret[i] = (String)entry.getKey();
                i++;
            }
        }
        return ret;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}


class FreqComp implements Comparator<String> {
    Map<String, Integer> sourceMap;

    FreqComp(Map<String, Integer> sourceMap) {
        this.sourceMap = sourceMap;
    }

    @Override
    public int compare(String _this, String _that) {
        // if we have the same values then fall back to comparing by key
        int _thisValue = sourceMap.get(_this);
        int _thatValue = sourceMap.get(_that);

        if (_thisValue == _thatValue) return _this.compareTo(_that) * -1;
        return (_thisValue >= _thatValue) ? 1 : -1;
    }
}
