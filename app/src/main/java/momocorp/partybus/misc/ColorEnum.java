package momocorp.partybus.misc;

import momocorp.partybus.R;

/**
 * Created by Pablo on 12/17/2016.
 */
public enum ColorEnum {
    RED400(R.string.red_400),
    PINK400(R.string.pink_400),
    PURPLE400(R.string.purple_400),
    INDIGO400(R.string.indigo_400),
    BLUE400(R.string.blue_400),

    LIGHTBLUE400(R.string.light_blue_400),
    CYAN400(R.string.cyan_400),
    TEAL400(R.string.teal_400),
    GREEN400(R.string.green_400),
    LIGHTGREEN400(R.string.light_green_400),
    LIME400(R.string.lime_400),
    YELLOW400(R.string.yellow_400),
    AMBER400(R.string.amber_400),
    ORANGE400(R.string.orange_400),
    DEEPORANGE400(R.string.deep_orange_400);


    private int backGroundColorResId;
    ColorEnum(int backgroundColorResId) {
    this.backGroundColorResId = backgroundColorResId;
    }

    public int getBackGroundColorResId(){
        return backGroundColorResId;
    }
}
