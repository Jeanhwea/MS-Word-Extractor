package cn.edu.buaa.sei.ltp;

import java.util.HashMap;

public class LtpConstant {

    /**
     * 分词标注集
     */
    private static final HashMap<String, String> ws = allocWS();
    /**
     * 词性标注集
     */
    private static final HashMap<String, String> pos = allocPOS();
    /**
     * 命名实体识别标注集
     */
    private static final HashMap<String, String> ner = allocNER();
    /**
     * 依存句法关系
     */
    private static final HashMap<String, String> dp = allocDP();
    /**
     * 语义角色类型
     */
    private static final HashMap<String, String> srl = allocSRL();


    public LtpConstant() {
    }

    /**
     * 分词标注集
     *
     * @param key 输入的查找键
     * @return
     */
    public static String getWS(String key) {
        return ws.get(key);
    }

    /**
     * 词性标注集
     *
     * @param key 输入的查找键
     */
    public static String getPOS(String key) {
        return pos.get(key);
    }

    /**
     * 命名实体识别标注集
     *
     * @param key 输入的查找键
     */
    public static String getNER(String key) {
        return ner.get(key);
    }

    /**
     * 依存句法关系
     *
     * @param key 输入的查找键
     */
    public static String getDP(String key) {
        return dp.get(key);
    }

    /**
     * 语义角色类型集
     *
     * @param key 输入的查找键
     */
    public static String getSRL(String key) {
        return srl.get(key);
    }

    /**
     * @return 分词标注集
     */
    private static HashMap<String, String> allocWS() {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("B", "词首");
        ret.put("I", "词中");
        ret.put("E", "词尾");
        ret.put("S", "单字成词");
        return ret;
    }

    /**
     * LTP 使用的是863词性标注集
     *
     * @return 词性标注集
     */
    private static HashMap<String, String> allocPOS() {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("a", "adjective");
        ret.put("b", "other noun-modifier");
        ret.put("c", "conjunction");
        ret.put("d", "adverb");
        ret.put("e", "exclamation");
        ret.put("g", "morpheme");
        ret.put("h", "prefix");
        ret.put("i", "idiom");
        ret.put("j", "abbreviation");
        ret.put("k", "suffix");
        ret.put("m", "number");
        ret.put("n", "general noun");
        ret.put("nd", "direction noun");
        ret.put("nh", "person name");
        ret.put("ni", "organization name");
        ret.put("nl", "location noun");
        ret.put("ns", "geographical name");
        ret.put("nt", "temporal noun");
        ret.put("nz", "other proper noun");
        ret.put("o", "onomatopoeia");
        ret.put("p", "preposition");
        ret.put("q", "quantity");
        ret.put("r", "pronoun");
        ret.put("u", "auxiliary");
        ret.put("v", "verb");
        ret.put("wp", "punctuation");
        ret.put("ws", "foreign words");
        ret.put("x", "non-lexeme");
        return ret;
    }

    /**
     * NE识别模块的标注结果采用O-S-B-I-E标注形式
     *
     * @return 命名实体识别标注集
     */
    private static HashMap<String, String> allocNER() {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("O", "这个词不是NE");
        ret.put("S", "这个词单独构成一个NE");
        ret.put("B", "这个词为一个NE的开始");
        ret.put("I", "这个词为一个NE的中间");
        ret.put("Nh", "人名");
        ret.put("Ni", "机构名");
        ret.put("Ns", "地名");
        ret.put("E", "这个词位一个NE的结尾");
        return ret;
    }

    /**
     * @return 依存句法关系
     */
    private static HashMap<String, String> allocDP() {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("SBV", "主谓关系");
        ret.put("VOB", "动宾关系");
        ret.put("IOB", "间宾关系");
        ret.put("FOB", "前置宾语");
        ret.put("DBL", "兼语");
        ret.put("ATT", "定中关系");
        ret.put("ADV", "状中结构");
        ret.put("CMP", "动补结构");
        ret.put("COO", "并列关系");
        ret.put("POB", "介宾关系");
        ret.put("LAD", "左附加关系");
        ret.put("RAD", "右附加关系");
        ret.put("IS", "独立结构");
        ret.put("HED", "核心关系");
        return ret;
    }

    /**
     * @return 语义角色类型
     */
    private static HashMap<String, String> allocSRL() {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("ADV", "附加的，默认标记");
        ret.put("BNE", "受益人");
        ret.put("CND", "条件");
        ret.put("DIR", "方向");
        ret.put("DGR", "程度");
        ret.put("EXT", "扩展");
        ret.put("FRQ", "频率");
        ret.put("LOC", "地点");
        ret.put("MNR", "方式");
        ret.put("PRP", "目的或原因");
        ret.put("TMP", "时间");
        ret.put("TPC", "主题");
        ret.put("CRD", "并列参数");
        ret.put("PRD", "谓语动词");
        ret.put("PSR", "持有者");
        ret.put("PSE", "被持有");
        return ret;
    }
}
