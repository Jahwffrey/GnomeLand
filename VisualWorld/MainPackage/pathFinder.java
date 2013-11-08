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
    ArrayList<node> finalPath = new ArrayList<node>();
    node startOfFinalPath;

    public pathFinder(int xx,int yy){
        x=xx;
        y=yy;
    }

    public void pathFind(int targetX,int targetY){
        boolean found = false;

        if(targetX==x&&targetY==y){
            return;
        }

        if(Math.abs(targetX-x)<=1&&Math.abs(targetY-y)<=1){
            x=targetX;
            y=targetY;
            return;
        }

        if(!isFree(targetX,targetY)){
            return;
        }

        openList.clear();
        closedList.clear();
        finalPath.clear();
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
            if(openList.size()>0){
                detectNeighborsOfNode(openList.get(0),newNodes);
                if(newNodes.size()>0){
                    for(int i = 0;i<newNodes.size();i++){
                        if(newNodes.get(i).dist==0){
                            closedList.add(0,newNodes.get(i));
                            found = true;
                        }
                    }
                }
                closedList.add(openList.get(0));
                openList.remove(0);
            }
            if(found){
                break;
            }
            mergeUnsortedWithSortedList(newNodes,openList);
            newNodes.clear();
        }
        if(found){
            startOfFinalPath = constructPath(closedList.get(0),start);
        }
        /*for(int i = 0;i<newNodes.size();i++){
            openList.add(newNodes.get(i));
        }**/
        //mergeUnsortedWithSortedList(newNodes,openList);
        //(openList.size()>0){

        //}
    }

    public node constructPath(node noda, node nodeStart){
        if(noda.prev!=nodeStart){
            node p = new node(noda.x,noda.y,constructPath(noda.prev,nodeStart),0,0,0);
            finalPath.add(p);
            return p;
        }
        else{
            node p = new node(noda.x,noda.y,nodeStart,0,0,0);
            finalPath.add(p);
            return p;
        }
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
        int costy = 1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if(isFree(aNode.x+xx,aNode.y+yy)){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,costy,goalX,goalY));
                }
            }
        }
        xx=0;
        yy=-1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if(isFree(aNode.x+xx,aNode.y+yy)){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,costy,goalX,goalY));
                }
            }
        }
        xx=1;
        yy=0;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if(isFree(aNode.x+xx,aNode.y+yy)){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,costy,goalX,goalY));
                }
            }
        }
        xx=0;
        yy=1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if(isFree(aNode.x+xx,aNode.y+yy)){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,costy,goalX,goalY));
                }
            }
        }

        costy = 1;
        xx=-1;
        yy=-1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if((isFree(aNode.x+xx,aNode.y+yy))&&(isFree(aNode.x,aNode.y+yy)||isFree(aNode.x+xx,aNode.y))){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,costy,goalX,goalY));
                }
            }
        }
        xx=1;
        yy=-1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if((isFree(aNode.x+xx,aNode.y+yy))&&(isFree(aNode.x,aNode.y+yy)||isFree(aNode.x+xx,aNode.y))){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,costy,goalX,goalY));
                }
            }
        }
        xx=-1;
        yy=1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if((isFree(aNode.x+xx,aNode.y+yy))&&(isFree(aNode.x,aNode.y+yy)||isFree(aNode.x+xx,aNode.y))){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,costy,goalX,goalY));
                }
            }
        }
        xx=1;
        yy=1;
        if(exists(aNode.x+xx,aNode.y+yy)){
            if((isFree(aNode.x+xx,aNode.y+yy))&&(isFree(aNode.x,aNode.y+yy)||isFree(aNode.x+xx,aNode.y))){
                if(openList.size()>0){
                    if(!thereExistsThisNodeInOpen(aNode.x+xx,aNode.y+yy)&&!thereExistsThisNodeInClosed(aNode.x+xx,aNode.y+yy))
                        theNodes.add(new node(aNode.x+xx,aNode.y+yy,aNode,costy,goalX,goalY));
                }
            }
        }
    }

    public void traversePath(){
        if(finalPath.size()>0&&startOfFinalPath.prev!=null){
            x = finalPath.get(0).x;
            y = finalPath.get(0).y;
            finalPath.remove(0);
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
