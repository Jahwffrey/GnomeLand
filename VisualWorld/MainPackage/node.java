package MainPackage;

/**
 * Created with IntelliJ IDEA.
 * User: Jack
 * Date: 11/6/13
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class node {
    int x;
    int y;
    node prev;
    int moveCost;
    int dist;
    int totalCost;

    public node(int xx, int yy, node pprev,int mmoveCost,int goalX,int goalY){
        x=xx;
        y=yy;
        prev=pprev;
        moveCost=mmoveCost;
        dist=Math.abs(x-goalX)+Math.abs(y-goalY);
        totalCost = moveCost + dist;
    }
}
