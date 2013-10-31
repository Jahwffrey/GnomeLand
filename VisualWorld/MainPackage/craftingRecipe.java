package MainPackage;
import java.awt.*;
/**
 * Created with IntelliJ IDEA.
 * User: Jack
 * Date: 10/28/13
 * Time: 7:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class craftingRecipe {
    String type1 = null;
    int count1;
    String type2 = null;
    int count2;
    String type3 = null;
    int count3;
    String result;
    int resultCount;

    Image img;

    public craftingRecipe(String t,int c,String res, int resC,Image im){
        type1 = t;
        count1 = c;
        result = res;
        resultCount = resC;
        img = im;
    }

    public craftingRecipe(String t,int c,String t2,int c2,String res, int resC,Image im){
        type1 = t;
        count1 = c;
        type2 = t2;
        count2 = c2;
        result = res;
        resultCount = resC;
        img = im;
    }

    public craftingRecipe(String t,int c,String t2,int c2,String t3,int c3,String res, int resC,Image im){
        type1 = t;
        count1 = c;
        type2 = t2;
        count2 = c2;
        type3 = t3;
        count3 = c3;
        result = res;
        resultCount = resC;
        img = im;
    }

    public boolean canBeMade(){
        if(type2==null&&type3==null){
            if(Screen.inventory.typeCount(type1)>=count1){
                return true;
            }
        }
        else if(type3==null){
            if(Screen.inventory.typeCount(type1)>=count1&&Screen.inventory.typeCount(type2)>=count2){
                return true;
            }
        }
        else{
            if(Screen.inventory.typeCount(type1)>=count1&&Screen.inventory.typeCount(type2)>=count2&&Screen.inventory.typeCount(type3)>=count3){
                return true;
            }
        }
        return false;
    }

    public void craft(){
        if((Screen.inventory.queue.type.equals("nothing"))||(Screen.inventory.queue.type.equals(result)&&Screen.inventory.queue.count<Screen.inventory.queue.maxCount)){
            if(type1!=null){
                Screen.inventory.removeItemAmount(type1,count1);
            }
            if(type2!=null){
                Screen.inventory.removeItemAmount(type2,count2);
            }
            if(type3!=null){
                Screen.inventory.removeItemAmount(type3,count3);
            }
            if(Screen.inventory.queue.type.equals("nothing")){
                Screen.inventory.queue.type=result;
                Screen.inventory.queue.count = resultCount;
                Screen.inventory.queue.setMax();
            }
            else if(Screen.inventory.queue.type.equals(result)&&Screen.inventory.queue.count<Screen.inventory.queue.maxCount){
                Screen.inventory.queue.count += resultCount;
            }
        }
    }
}
