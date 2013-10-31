package MainPackage;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jack
 * Date: 10/27/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class menu {
    ArrayList<menuItem> items = new ArrayList<menuItem>();
    int fullCount;
    int width;
    int height;
    menuItem queue;
    menuItem tempItem;
    menuItem trashSpot;
    int heldPosition;

    public menu(int wwidth, int hheight){
        width = wwidth;
        height = hheight;
        fullCount = wwidth*hheight;
        for(int i = 0;i<fullCount;i++){
            items.add(new menuItem());
        }
        queue = new menuItem("nothing",0);
        tempItem = new menuItem("nothing",0);
        trashSpot = new menuItem("nothing",0);
    }

    public int twoDToOneD(int x, int y){
        return x+y;
    }

    public void switchAnItemWithQueue(int space){
        tempItem.type = queue.type;
        tempItem.count = queue.count;
        tempItem.maxCount = queue.maxCount;

        queue.type = items.get(space).type;
        queue.count = items.get(space).count;
        queue.maxCount = items.get(space).maxCount;

        items.get(space).type=tempItem.type;
        items.get(space).count=tempItem.count;
        items.get(space).maxCount=tempItem.maxCount;
    }

    public void throwAwayQueue(){
        if(queue.type.equals("nothing")){
            tempItem.type = queue.type;
            tempItem.count = queue.count;
            tempItem.maxCount = queue.maxCount;

            queue.type = trashSpot.type;
            queue.count = trashSpot.count;
            queue.maxCount = trashSpot.maxCount;

            trashSpot.type = "nothing";
            trashSpot.count = 0;
            trashSpot.maxCount = 0;
        }
        else{
            trashSpot.type = queue.type;
            trashSpot.count = queue.count;
            trashSpot.maxCount = queue.maxCount;

            queue.type = "nothing";
            queue.count = 0;
            queue.maxCount = 0;
        }
    }

    public boolean canBeBuilt(String ttype){
        if(ttype.equals("dirt")||ttype.equals("stone")||ttype.equals("metal")){
            return true;
        }
        else{
            return false;
        }
    }

    public void accessSpace(int space,int buttonType){
        //LEFT CLICK:
        if(buttonType==1){
            /*if(Screen.mouseCommand == 6){
            //decide wich ones can be built:
                if(canBeBuilt(items.get(space).type)){
                    Screen.menuItemToBuild = space;
                    System.out.println("Gana build a "+items.get(space).type);
                }
            }**/
        }
        //RIGHT CLICK:
        if(buttonType==3){
            System.out.println(space);
            if(!queue.type.equals(items.get(space).type)){
                switchAnItemWithQueue(space);
            }
            else{
                if(items.get(space).count<items.get(space).maxCount){
                    for(int i = queue.count;i>0;i--){
                        if(items.get(space).count>=items.get(space).maxCount){
                            break;
                        }
                        items.get(space).count++;
                        queue.count--;
                    }
                    if(queue.count==0){
                        queue.type="nothing";
                        queue.maxCount=0;
                    }
                }
                else if(items.get(space).count==items.get(space).maxCount){
                    switchAnItemWithQueue(space);
                }
            }
        }
    }

    public int notFullCount(String ttype){
        int counter = 0;
        for (int i = 0;i<fullCount;i++){
            if(items.get(i).type.equals(ttype)&&(items.get(i).count<items.get(i).maxCount)){
                counter += 1;
            }
        }
        return counter;
    }
    public int typeCount(String ttype){
        int counter = 0;
        for (int i = 0;i<fullCount;i++){
            if(items.get(i).type.equals(ttype)){
                counter += items.get(i).count;
            }
        }
        return counter;
    }

    public void removeItemAmount(String ttype,int ccount){
        for (int i = 0;i<fullCount;i++){
            if(items.get(i).type.equals(ttype)){
                if(items.get(i).count>=ccount){
                    items.get(i).count-=ccount;
                    ccount=0;
                    if(items.get(i).count<=0){
                        items.get(i).count=0;
                        items.get(i).type="nothing";
                        items.get(i).maxCount=0;
                    }
                    return;
                }
                else if(items.get(i).count<ccount){
                    ccount-=items.get(i).count;
                    items.get(i).count=0;
                    items.get(i).type="nothing";
                    items.get(i).maxCount=0;
                }
            }
        }
    }

    public boolean isFull(){
        for (int i = 0;i<fullCount;i++){
            if(items.get(i).type.equals("nothing")){
               return false;
            }
        }
        return true;
    }

    public int findEmptySpace(){
        for (int i = 0;i<fullCount;i++){
            if(items.get(i).type.equals("nothing")){
                return i;
            }
        }
        return 1000;
    }

    public void addItem(String ttype, int ccount){
        int cccount = ccount;
        for (int i = 0;i<fullCount;i++){
            if(items.get(i).type.equals(ttype)&&(items.get(i).count<items.get(i).maxCount)){
                while(cccount>0 && items.get(i).count<items.get(i).maxCount){
                    items.get(i).count+=1;
                    cccount-=1;
                }
            }
        }
        if(cccount>0){
            if(!this.isFull()){
                int temp = this.findEmptySpace();
                items.get(temp).type = ttype;
                items.get(temp).setMax();
                items.get(temp).count = cccount;
            }
        }
    }
}
