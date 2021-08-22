package sample;

public class ModelTable {
    String fname;
    String lname;

    public ModelTable(String first, String last) {
        this.fname = first;
        this.lname = last;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return this.fname;
    }

    public String getLname() {
        return this.lname;
    }
}
