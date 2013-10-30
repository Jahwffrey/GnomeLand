package MainPackage;

/**
 * Created with IntelliJ IDEA.
 * User: Jack
 * Date: 10/27/13
 * Time: 3:12 PM
 */
public class menuItem {

    int count;
    int maxCount;
    String type;

    public menuItem(){
        count = 0;
        type = "nothing";
    }

    public menuItem(String ttype, int ccount){
        count = ccount;
        type = ttype;
        if(ttype == "nothing"){
            maxCount=0;
        }
        if(ttype == "dirt"||ttype == "stone"||ttype == "grass"){
            maxCount = 200;
        }
        else if(ttype == "metal"||ttype == "wood"){
            maxCount = 100;
        }
        else{
            maxCount = 25;
        }
    }

    public void setMax(){
        if(type == "dirt"||type == "stone"||type == "grass"){
            maxCount = 200;
        }
        else if(type == "metal"||type == "wood"){
            maxCount = 100;
        }
        else{
            maxCount = 25;
        }
    }

    public boolean isFull(){
        if(count==maxCount){
            return true;
        }
        else{
            return false;
        }
    }

    public void addAmt(int amt){
        count += amt;
        if(count>maxCount){
            count = maxCount;
        }
    }
}
