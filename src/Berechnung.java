public class Berechnung {
    static double m = 0.1;//mass
    static double t = 10; //thrust
    static double r = 0.3; //distance engine center of mass
    static double l = 0.5; //lenght of the rocket
    static double cax = 0; //current angle on X-Axis
    static double cay = 0; //current angle on Y-Axis
    static double dax = 5; //deflection Angle at the beginning on X-Axis
    static double day = 5; //deflection Angle at the beginning on Y-Axis
    static double sax = 0; //current angele of servos
    static double say = 0; //current angele of servos
    static double dt =0.005; //delta t = time between tow meassurements
    static double ct = 0; //time since the simulation begun in s
    static double at = 5;//time the simulation will run in seconds
    static double k;

    public static void main(String[] args){
        k =  (24*r)/(m*(l*l));
        cax = dax;
        cay = day;
        System.out.println("test");
        System.out.println("test02");
        while(ct<at){
            cax = k*t* Math.sin(sax)*(dt*dt)+cax;
            cay = k*t* Math.sin(say)*(dt*dt)+cay;
            System.out.println("X: "+cax+"Y: "+cay);
            ct = ct + dt;
        }
    }
}
