import model.program.HalfMan;

public class HalfmanApp {


    public static void main(String[] args) throws Exception {
        HalfMan h = new HalfMan();
        while (true) {
            h.listen();
        }
    }
}
