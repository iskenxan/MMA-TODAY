package space.samatov.mmatoday.model;

public class FighterStats {

    public static final String mCountriesUrl="http://ufc-data-api.ufc.com/api/v3/iphone/fighters/stats/born_country/";
    public static final String mBaseFighterStatsUrl="http://ufc-data-api.ufc.com/api/v3/iphone/fighters/stats/weight_class/";

    public static String[] mWeightClasses=new String[]{"wbw","hw","lhw","mw","ww","lw","bw","ftw","flw"};
    public static final String WEIGHT_KEY_ALL="all";
    public static final String WEIGHT_KEY_WBANTAM="wbw";
    public static final String WEIGHT_KEY_HEAVY="hw";
    public static final String WEIGHT_KEY_LHEAVY="lhw";
    public static final String WEIGHT_KEY_MIDDLE="mw";
    public static final String WEIGHT_KEY_WELTER="ww";
    public static final String WEIGHT_KEY_LIGHT="lw";
    public static final String WEIGHT_KEY_BANTAM="bw";
    public static final String WEIGHT_KEY_FEATHER="ftw";
    public static final String WEIGHT_KEY_FLY="flw";

    public static String[] mCountries=new String[]{"USA","JPN","BRA","ITA","CAN","NED","AUS","POL","PER","BLR","NZL","SWE","FRA","ARM","DEN",
            "ESA","TUR","BAH","KOR","CHN","CRO","NGR","SIN","GUM","RUS","HAI","GBR","IRL","CYP","MEX"};
    public static final String COUNTRY_KEY_USA="USA";
    public static final String COUNTRY_KEY_JAPAN="JPN";
    public static final String COUNTRY_KEY_BRAZIL="BRA";
    public static final String COUNTRY_KEY_ITALY="ITA";
    public static final String COUNTRY_KEY_CANADA="CAN";
    public static final String COUNTRY_KEY_NETHERLANDS="NED";
    public static final String COUNTRY_KEY_AUSTRIA="AUS";
    public static final String COUNTRY_KEY_POLAND="POL";
    public static final String COUNTRY_KEY_PERU="PER";
    public static final String COUNTRY_KEY_BELARUS="BLR";
    public static final String COUNTRY_KEY_NEW_ZELAND="NZL";
    public static final String COUNTRY_KEY_SWEDEN="SWE";
    public static final String COUNTRY_KEY_FRANCE="FRA";
    public static final String COUNTRY_KEY_ARMENIA="ARM";
    public static final String COUNTRY_KEY_DENMARK="DEN";
    public static final String COUNTRY_KEY_EL_SALVADOR="ESA";
    public static final String COUNTRY_KEY_TURKEY="TUR";
    public static final String COUNTRY_KEY_BAHAMAS="BAH";
    public static final String COUNTRY_KEY_KOREA="KOR";
    public static final String COUNTRY_KEY_CHINA="CHN";
    public static final String COUNTRY_KEY_CROATIA="CRO";
    public static final String COUNTRY_KEY_NIGERIA="NGR";
    public static final String COUNTRY_KEY_SINGAPORE="SIN";
    public static final String COUNTRY_KEY_GUAM="GUM";
    public static final String COUNTRY_KEY_RUSSIA="RUS";
    public static final String COUNTRY_KEY_HAITI="HAI";
    public static final String COUNTRY_KEY_UNITED_KINGDOM="GBR";
    public static final String COUNTRY_KEY_IRELAND="IRL";
    public static final String COUNTRY_KEY_CYPRUS="CYP";
    public static final String COUNTRY_KEY_MEXICO="MEX";

    public static final String SIG_STRIKES_KEY="SigStrikesLanded";
    public static final String SIG_STRIKES_DEF_KEY="SigStrikingDefense";
    public static final String SIG_STRIKES_ACCURACY_KEY="SigStrikesAccuracy";
    public static final String STRIKES_PER_MINUTE_KEY="SLpM";
    public static final String AVG_FIGHT_TIME_KEY="AvgFightTimeLong";
    public static final String TAKEDOWNS_KEY="TakedownsLanded";
    public static final String TAKEDOWNS_ACCURACY_KEY="TakedownAccuracy";
    public static final String TAKEDOWNS_DEFENCE_KEY="TakedownDefense";
    public static final String SUB_ATTEMPTS_KEY="SubmissionAttempts";

    private long mFighterId;
    private String mFirst;
    private String mLast;
    private String mFrom;
    private String mFightsOutOf;
    private String mHeight;
    private String mAge;
    private String mWeight;
    private String mWeightClass;
    private String mAverageFightTime;
    private String mTakeDownsLanded;
    private String mTakeDownAccuracy;
    private String mTakeDownDefence;
    private String mSubmissionAttempts;
    private String mSigStrikesLanded;
    private String mSigStrikesAccuracy;
    private String mStikesLandedPerMinute;
    private String mSigStrikesDef;

    private String mWeighClassKey;
    private String mCountryBornKey;

    public String getmFirst() {
        return mFirst;
    }

    public void setmFirst(String mFirst) {
        this.mFirst = mFirst;
    }

    public String getmLast() {
        return mLast;
    }

    public void setmLast(String mLast) {
        this.mLast = mLast;
    }

    public String getmFrom() {
        return mFrom;
    }

    public void setmFrom(String mFrom) {
        this.mFrom = mFrom;
    }

    public String getmFightsOutOf() {
        return mFightsOutOf;
    }

    public void setmFightsOutOf(String mFightsOutOf) {
        this.mFightsOutOf = mFightsOutOf;
    }

    public String getmHeight() {
        return mHeight;
    }

    public void setmHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    public String getmAge() {
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    public String getmWeight() {
        return mWeight;
    }

    public void setmWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    public String getmWeightClass() {
        return mWeightClass;
    }

    public void setmWeightClass(String mWeightClass) {
        this.mWeightClass = mWeightClass;
    }

    public long getmFighterId() {
        return mFighterId;
    }

    public void setmFighterId(long mFighterId) {
        this.mFighterId = mFighterId;
    }

    public String getmAverageFightTime() {
        return mAverageFightTime;
    }

    public void setmAverageFightTime(String mAverageFightTime) {
        this.mAverageFightTime = mAverageFightTime;
    }

    public String getmTakeDownsLanded() {
        return mTakeDownsLanded;
    }

    public void setmTakeDownsLanded(String mTakeDownsLanded) {
        this.mTakeDownsLanded = mTakeDownsLanded;
    }

    public String getmTakeDownAccuracy() {
        return mTakeDownAccuracy;
    }

    public void setmTakeDownAccuracy(String mTakeDownAccuracy) {
        float value=Float.valueOf(mTakeDownAccuracy);
        int rounded=Math.round(value);
        this.mTakeDownAccuracy = rounded+"%";
    }

    public String getmTakeDownDefence() {
        return mTakeDownDefence;
    }

    public void setmTakeDownDefence(String mTakeDownDefence) {
        float value=Float.valueOf(mTakeDownDefence);
        int rounded=Math.round(value);
        this.mTakeDownDefence = rounded+"%";
    }

    public String getmSubmissionAttempts() {
        return mSubmissionAttempts;
    }

    public void setmSubmissionAttempts(String mSubmissionAttempts) {
        this.mSubmissionAttempts = mSubmissionAttempts;
    }

    public String getmSigStrikesLanded() {
        return mSigStrikesLanded;
    }

    public void setmSigStrikesLanded(String mSigStrikesLanded) {
        this.mSigStrikesLanded = mSigStrikesLanded;
    }

    public String getmSigStrikesAccuracy() {
        return mSigStrikesAccuracy;
    }

    public void setmSigStrikesAccuracy(String mSigStrikesAccuracy) {
        float value=Float.valueOf(mSigStrikesAccuracy);
        int rounded=Math.round(value);
        this.mSigStrikesAccuracy = rounded+"%";
    }

    public String getmStikesLandedPerMinute() {
        return mStikesLandedPerMinute;
    }

    public void setmStikesLandedPerMinute(String mStikesLandedPerMinute) {
        this.mStikesLandedPerMinute = mStikesLandedPerMinute;
    }

    public String getmSigStrikesDef() {
        return mSigStrikesDef;
    }

    public void setmSigStrikesDef(String mSigStrikesDef) {
        float value=Float.valueOf(mSigStrikesDef);
        int rounded=Math.round(value);
        this.mSigStrikesDef = rounded+"%";
    }

    public String getmWeighClassKey() {
        return mWeighClassKey;
    }

    public void setmWeighClassKey(String mWeighClassKey) {
        this.mWeighClassKey = mWeighClassKey;
    }

    public String getmCountryBornKey() {
        return mCountryBornKey;
    }

    public void setmCountryBornKey(String mCountryBornKey) {
        this.mCountryBornKey = mCountryBornKey;
    }
}
