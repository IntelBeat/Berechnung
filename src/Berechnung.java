public class Berechnung {
    static double m = 0.4;//mass
    static double t = 100; //thrust
    static double r = 0.3; //distance engine center of mass
    static double l = 0.5; //length of the rocket
    static double dax = 25; //deflection Angle at the beginning on X-Axis
    static double day = 5; //deflection Angle at the beginning on Y-Axis
    static double dt =0.05; //delta t = time between tow measurements
    static double ct = 0; //time since the simulation begun in s
    static double at = 5;//time the simulation will run in seconds
    static double k;
    static double P = 8;
    static double I = 0.01;
    static double D = 100;

    public static void main(String[] args){
        double sax = 0; //current angele of servos
        double say = 0; //current angele of servos
        double cax; //current angle on X-Axis
        double cay; //current angle on Y-Axis
        double pax = 0; // previous X_Axis angle
        k =  (24*r)/(m*(l*l));
        cax = dax;
        cay = day;
        while(ct<at){
            double eax = cax;
            sax = P*eax + D* ((eax-pax)/dt);
            System.out.println("Servo "+sax);
            cax = k*t* Math.sin(sax)*(dt*dt)+cax;
            cay = k*t* Math.sin(say)*(dt*dt)+cay;
            System.out.println(cax);
            ct = ct + dt;
            pax = cax;
        }
    }
}
