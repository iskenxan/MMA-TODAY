package space.samatov.mmatoday.model;


import space.samatov.mmatoday.model.interfaces.ListItem;

public class LetterGroupHeader implements ListItem {
    public String mGroupName;

    public LetterGroupHeader(String groupName){
        mGroupName=groupName;
    }

    public String getmGroupName() {
        return mGroupName;
    }

    public void setmGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    @Override
    public boolean isGroupHeader() {
        return true;
    }
}
