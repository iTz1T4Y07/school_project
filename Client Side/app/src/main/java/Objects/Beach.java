package Objects;

public class Beach {

    private String name;
    private int id;

    public Beach (String name, int id){
        this.name = name;
        this.id = id;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }
}
