package data.mpo;

public class Sales {

    public Sales(String _name, int _price){
        name=_name;
        price =_price;
    }

    public String name;
    public int price;

    public int getPrice(){
        return price;
    }
}
