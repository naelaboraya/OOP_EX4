package GraphPack;

import java.awt.*;

public class EdgeDataImpl implements EdgeData {
    private int src;
    private int dst;
    private double Weight;
    private String info;
    Color color=Color.white;
    public EdgeDataImpl(int src, int dst , double weight){
        this.src=src;
        this.dst=dst;
        this.Weight=weight;
    }

    @Override
    public int getSrc() {
       return src;
    }

    @Override
    public int getDest() {
        return dst;
    }

    @Override
    public double getWeight() {
        return Weight;
    }

    public void setSrc(int s){
        this.src=s;
}

    public void setWeight(Double w){
        this.Weight=w;
}

    public void setDst(int dst)
{
        this.dst=dst;
}

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
    this.info=s;
    }

    @Override
    public int getTag() {
      int x=  this.color.getRGB();
      return x;

    }
    @Override
    public void setTag(int t) {
        this.color=new Color(t);
    }
}
