package space.samatov.mmatoday.model;


public class ListGroupHeaders implements ListItem {
    public String mGroupName;

    public ListGroupHeaders(String groupName){
        mGroupName=groupName;
    }
    public ListGroupHeaders(){}

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
