/*
 * modelbuilderMk2
 */
package unlekker.mb2.geo;

import java.util.ArrayList;
import java.util.Iterator;

import unlekker.mb2.util.UMB;


/**
 * WORK IN PROGRESS - NOT FUNCTIONAL
 * @author marius
 *
 */
public class UEdgeList extends UMB implements Iterable<UEdge> {
  public UGeo parent;
  ArrayList<UEdge> edges;
  
  public UEdgeList() {
    edges=new ArrayList<UEdge>();
  }
  
  public UEdgeList(UGeo model) {
    this();
    if(model!=null) parent=model;    
    edges=new ArrayList<UEdge>();
    for(UFace ff:model.getF()) {
      UVertex vv[]=ff.getV();
      add(vv[0],vv[1]).add(ff);
      add(vv[1],vv[2]).add(ff);
      add(vv[2],vv[0]).add(ff);
    }
    
  }

  public UEdge add(UVertex v1,UVertex v2) {
    UEdge e=null;
    if(!contains(v1, v2)) {
      if(parent!=null) e=new UEdge(parent,v1,v2);
      else e=new UEdge(v1,v2);
      
      edges.add(e);
    }

    if(e==null) e=get(v1,v2);
    
    return e;
  }
  
  public int size() {
    return edges.size();
  }
  
  public Iterator<UEdge> iterator() {
    // TODO Auto-generated method stub
    return edges.iterator();
  }
  
  public boolean contains(UVertex v1,UVertex v2) {
    int id1=-1,id2=-1;
    
    if(parent!=null) {
      id1=parent.getVID(v1);
      id2=parent.getVID(v2);      
    }
    
    if(id1<0 || id2<0) {
      for(UEdge e:edges) if(e.equals(v1,v2)) return true;
    }
    else {
      for(UEdge e:edges) if(e.equals(id1,id2)) return true;
    }
    
    return false;
  }

  public UEdge get(UVertex v1,UVertex v2) {
    UEdge e=null;
    int id=indexOf(v1, v2);
    if(id>-1) e=edges.get(id);
    return e;
  }

  public int indexOf(UVertex v1,UVertex v2) {
    int id1=-1,id2=-1;
    
    if(parent!=null) {
      id1=parent.getVID(v1);
      id2=parent.getVID(v2);      
    }

    int cnt=0;
    if(id1<0 || id2<0) {
      for(UEdge e:edges) {
        if(e.equals(v1,v2)) return cnt;
        cnt++;
      }
    }
    else {
      cnt=0;
      for(UEdge e:edges) {
        if(e.equals(id1,id2)) return cnt;
        cnt++;
      }
    }
    
    return -1;
  }
}
