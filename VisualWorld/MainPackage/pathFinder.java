package MainPackage;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jack
 * Date: 11/6/13
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class pathFinder {

    int x;
    int y;
    int goalX;
    int goalY;
    ArrayList<node> openList = new ArrayList<node>();
    ArrayList<node> closedList = new ArrayList<node>();

    public pathFinder(int xx,int yy){
        x=xx;
        y=yy;
    }

    public void pathFind(int targetX,int targetY){
        boolean found = false;
        openList.clear();
        closedList.clear();
        goalX=targetX;
        goalY=targetY;
        node start = new node(x,y,null,0,goalX,goalY);
        closedList.add(0, start);
        ArrayList<node> newNodes = new ArrayList<node>();
        openList.add(new node(0,0,null,0,0,0));
        detectNeighborsOfNode(start,newNodes);
        openList.clear();
        mergeUnsortedWithSortedList(newNodes,openList);
        newNodes.clear();

        for(int ii = 0;ii<4000;ii++){
            int closest = 0;
            for(int i = 0;i < openList.size();i++){
                if(openList.get(i).totalCost<openList.get(closest).totalCost){
                    closest=i;
                }
            }
            if(openList.size()>0){
                detectNeighborsOfNode(openList.get(closest),newNodes);
                if(newNodes.size()>0){
                    for(int i = 0;i<newNodes.size();i++){
                        if(newNodes.get(i).totalCost==1){
                            closedList.add(newNodes.get(i));
                            found = true;
                        }
                    }
                }
                closedList.add(openList.get(closest));
                openList.remove(closest);
            }
            mergeUnsortedWithSortedList(newNodes,openList);
            newNodes.clear();
            if(found){
                break;
            }
        }
        /*for(int i = 0;i<newNodes.size();i++){
            openList.add(newNodes.get(i));
        }**/
        //mergeUnsortedWithSortedList(newNodes,openList);
        //(openList.size()>0){

        //}
    }

    public void mergeUnsortedWithSortedList(ArrayList<node> unsorted,ArrayList<node> sorted){
        if(unsorted.size()>0){
            while(unsorted.size()>0){
                for(int i = 0;i<=sorted.size();i++){
                    if(i==sorted.size()){
                        sorted.add(unsorted.get(0));
                        unsorted.remove(0);
                        break;
                    }
                    else if(sorted.get(i).totalCost>unsorted.get(0).totalCost){
                        sorted.add(i,unsorted.get(0));
                        unsorted.remove(0);
                        break;
                    }
                }
            }
        }
    }

    void detectNeighborsOfNode(node aNode,ArrayList<node> theNodes){
        int xx = -1;
        int yy = 0;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if(isFree(aNode.x+xx,aNode.y+yy)){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,1,goalX,goalY));
                }
            }
        }
        xx=0;
        yy=-1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if(isFree(aNode.x+xx,aNode.y+yy)){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,1,goalX,goalY));
                }
            }
        }
        xx=1;
        yy=0;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if(isFree(aNode.x+xx,aNode.y+yy)){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,1,goalX,goalY));
                }
            }
        }
        xx=0;
        yy=1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if(isFree(aNode.x+xx,aNode.y+yy)){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,1,goalX,goalY));
                }
            }
        }
        xx=-1;
        yy=-1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if((isFree(aNode.x+xx,aNode.y+yy))&&(isFree(aNode.x,aNode.y+yy)||isFree(aNode.x+xx,aNode.y))){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,1,goalX,goalY));
                }
            }
        }
        xx=1;
        yy=-1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if((isFree(aNode.x+xx,aNode.y+yy))&&(isFree(aNode.x,aNode.y+yy)||isFree(aNode.x+xx,aNode.y))){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,1,goalX,goalY));
                }
            }
        }
        xx=-1;
        yy=1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if((isFree(aNode.x+xx,aNode.y+yy))&&(isFree(aNode.x,aNode.y+yy)||isFree(aNode.x+xx,aNode.y))){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,1,goalX,goalY));
                }
            }
        }
        xx=1;
        yy=1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if((isFree(aNode.x+xx,aNode.y+yy))&&(isFree(aNode.x,aNode.y+yy)||isFree(aNode.x+xx,aNode.y))){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,1,goalX,goalY));
                }
            }
        }
    }

    public boolean thereExistsThisNodeInOpen(int xx, int yy){
        for(int i = 0;i<openList.size();i++){
            if(openList.get(i).x==xx){
                if(openList.get(i).y==yy)
                    return true;
            }
        }
        return false;
    }
    public boolean thereExistsThisNodeInClosed(int xx, int yy){
        for(int i = 0;i<closedList.size();i++){
            if(closedList.get(i).x==xx){
                if(closedList.get(i).y==yy)
                    return true;
            }
        }
        return false;
    }

    public boolean exists(int gridX,int gridY){
        if(gridX>0&&gridX<Screen.land[1].length&&gridY>0&&gridY<Screen.land.length){
            return true;
        }
        else return false;
    }

    public boolean isFree(int xx,int yy){
        switch(Screen.land[yy][xx]){
            //all the solids:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
                return false;
        }
        return true;
    }
}
