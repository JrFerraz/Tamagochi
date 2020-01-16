package tamagotchi;
import java.time.LocalDateTime;
import java.time.temporal.*;
public class tamagochi {
    String nombre;
    LocalDateTime fechaNacimiento, fechaMuerte;
    int tiempoVida;
    static final float PROBABILIDAD_APARICION_SUEÑO = 0.1f;
    static final float PROBABILIDAD_APARICION_ENFERMEDAD = 0.2f;
    static final int ESTRES_POR_SUEÑO = 25;
    static final int SALUD_POR_ENFERMEDAD = 25;
    static final int DIFICULTAD_GEN_HAMBRE = 2;
    static final int RIESGO_LEVE = 2;
    static final int RIESGO_MEDIO = 3;
    static final int RIESGO_GRAVE = 4;
    static final int RIESGO_MUY_GRAVE = 5;
    int limiteExp, experiencia, nivel;
    int energia, hambre, salud, estres, aburrimiento;
    int medicinas, calmantes;
    int numGalletas, numPizzas, numTartas;
    boolean sueño, enfermo, vivo, ataque;
    public tamagochi(String nom){
        this.nombre=nom;
        fechaNacimiento=LocalDateTime.now();
        hambre=0;
        estres=0;
        experiencia=0;
        nivel=1;
        tiempoVida=0;
        salud=100;
        limiteExp=50;
        medicinas=(int)((Math.random()*5)+1);
        calmantes=(int)((Math.random()*3)+2);
        numGalletas=(int)((Math.random()*3)+3);
        numPizzas=(int)((Math.random()*2)+2);
        numTartas=(int)((Math.random()*2)+1);
        energia=(int)((Math.random()*1)+1);
        sueño=false;
        ataque=false;
        enfermo=false;
        vivo=true;
    }
    public void morir(){
       if(aburrimiento==100 || estres==100 || hambre==100 || salud==0){
            fechaMuerte=LocalDateTime.now();
            tiempoVida = (int) ChronoUnit.SECONDS.between(fechaNacimiento, fechaMuerte);
            vivo=false;
        }
    }
    public void efectosTemporales(){
        if(sueño){
            estres=estres+(ESTRES_POR_SUEÑO*nivel);
            System.out.println("Debido al sueño "+nombre+" suma " +(ESTRES_POR_SUEÑO*nivel)+" puntos de estrés");
        }
        if(enfermo){
            salud=salud-(SALUD_POR_ENFERMEDAD*nivel);
            System.out.println("Debido la enfermedad, "+nombre+ " pierde "+ (SALUD_POR_ENFERMEDAD*nivel)+ " puntos de salud");
        }
    }
    public void generarSueño(){
        if(sueño == false && Math.random() < PROBABILIDAD_APARICION_SUEÑO) sueño=true;
    }
    public void generarHambre(){
        int generadorHambre =(int)((Math.random()*10)+10);
        generadorHambre=generadorHambre+(nivel*DIFICULTAD_GEN_HAMBRE);
        System.out.println();
        System.out.println();
        System.out.println("Este turno aumenta el hambre de " +nombre+" en "+generadorHambre);
        hambre=hambre+generadorHambre;
    }
    public void generarEnfermedad(){
        if(!enfermo && Math.random() < PROBABILIDAD_APARICION_ENFERMEDAD) enfermo=true;
    }
    public void comprobarNumMedicinasCalmantes(){
        if(medicinas < 0) medicinas = 0;
        if(calmantes < 0) calmantes = 0;
    }
    public void comprobarEnfermedadySueño (){
        if(sueño) System.out.println(nombre+" tiene sueño y debería dormir");
        if(enfermo)System.out.println(nombre+" ha enfermado y deberias darle una medicina");
    }
    public void comprobarHambre(){
        hambre = ajustarLimite(hambre);
        if(hambre<=10) System.out.println(nombre+" no tiene hambre");
        else if(hambre>10 && hambre<=30){
            System.out.println(nombre+" tiene algo de hambre, deberia comer un aperitivo, por ello pierde "+nivel +" puntos de salud");
            salud=salud-nivel;
        }else if(hambre<=50){
            System.out.println(nombre+" tiene bastante hambre, deberias darle algo mas contundente, por ello pierde "+ (nivel*RIESGO_LEVE) +" puntos de salud");
            salud=salud-(nivel*RIESGO_LEVE);
        }else if(hambre<=65){
            System.out.println("A "+nombre+ " empiezan a sonarle las tripas, por ello pierde "+(nivel*RIESGO_MEDIO)+" puntos de salud");
            salud=salud-(nivel*RIESGO_MEDIO);
        }else if (hambre<=80){
            System.out.println("Como siga así, " + nombre +" morira de hambre en breve, además el hambre le hace perder "+(nivel*RIESGO_GRAVE)+" puntos de salud");
            salud=salud-(nivel*RIESGO_GRAVE);
        }else if (hambre<=99){
            System.out.println(nombre+" te suplica que le des algo de comer y pierde "+(nivel*RIESGO_MUY_GRAVE)+" puntos de salud");
            salud=salud-(nivel*RIESGO_MUY_GRAVE);
        }
    }
    public void comprobarAburrimiento(){
        aburrimiento = ajustarLimite(aburrimiento);
        if(aburrimiento<=10) System.out.println(nombre+" esta feliz");
        else if(aburrimiento<=30){
            System.out.println(nombre+" se aburre un poco y recibe "+(nivel)+" puntos de estrés");
            estres=estres+(nivel);
        }else if(aburrimiento<=50){
            System.out.println(nombre+" empieza a aburrirse bastante y recibe "+(nivel*RIESGO_LEVE)+" puntos de estrés");
            estres=estres+(nivel*RIESGO_LEVE);
        }else if(aburrimiento<=75){
            System.out.println(nombre+" se empieza a aburrir mucho y recibe "+(nivel*RIESGO_MEDIO)+" puntos de estrés");
            estres=estres+(nivel*RIESGO_MEDIO);
        }else if(aburrimiento<=90){
            System.out.println(nombre+" se empieza a aburrir mucho y recibe "+(nivel*RIESGO_GRAVE)+" puntos de estrés");
            estres=estres+(nivel*RIESGO_GRAVE);
        }else if(aburrimiento<=99){
            System.out.println(nombre+" comienza a plantearse su existencia debido al aburrimiento y recibe "+(nivel*RIESGO_MUY_GRAVE)+" puntos de estrés");
            estres=estres+(nivel*RIESGO_MUY_GRAVE);
        }
    }
    private int ajustarLimite(int valor){
        if(valor>100) valor=100;
        if(valor<0) valor = 0;
        return valor;
    }
    public void comprobarSalud(){
        salud = ajustarLimite(salud);
        if(salud<=10) System.out.println(nombre+" esta muy bajo de salud, deberias darle medicina");
        else if(salud>10 && salud<=25) System.out.println(nombre+" esta regular de salud");
        else if(salud<=50) System.out.println(nombre+" esta bastante bien de salud");
        else if (salud<=75) System.out.println(nombre+" esta bien de salud");
        else System.out.println(nombre+" esta muy bien de salud");
    }
    public void comer(int selectorComida){
        int valorComida;
        switch (selectorComida) {
            case 1:
                if(numGalletas!=0){
                    valorComida=(int)((Math.random()*(15+nivel))+(20-nivel));
                    this.hambre=hambre-valorComida;
                    this.salud=salud+5;
                    this.energia=energia+1;
                    this.experiencia=experiencia+15;
                    System.out.println(nombre+" se ha comido una galleta y ha eliminado " + valorComida + " puntos de hambre");
                    System.out.println("Las galletas son buenas para la salud y le suman 5 puntos a la vida");
                    System.out.println("Obtiene 1 punto de energía");
                    System.out.println("Por ser la opcion de comida más arriesgada, " + nombre + " recibe 15 puntos de experiencia");
                    numGalletas--;
                }else System.out.println("No te quedan galletas");
                break;
            case 2:
                if(numPizzas!=0){
                    valorComida=(int)((Math.random()*(20+nivel))+(35-nivel));
                    this.hambre=hambre-valorComida;
                    this.salud=salud-5;
                    this.energia=energia+2;
                    this.experiencia=experiencia+10;
                    System.out.println(nombre+" se ha comido una pizza y ha eliminado " + valorComida + " puntos de hambre");
                    System.out.println("Las pizzas no son sanas y le pasan factura a "+ nombre+" restandole 5 puntos de vida");
                    System.out.println("Obtiene 2 puntos de energía");
                    System.out.println(nombre+" recibe 10 puntos de experiencia al comerse la pizza");
                    numPizzas--;
                }else System.out.println("No te quedan pizzas");
                break;
            case 3:
                if(numTartas!=0){
                    valorComida=(int)((Math.random()*(40+nivel))+(40-nivel));
                    this.hambre=hambre-valorComida;
                    this.aburrimiento=aburrimiento+10;
                    this.energia=energia+3;
                    this.experiencia=experiencia+5;
                    System.out.println(nombre+" se ha comido una tarta y ha eliminado " + valorComida + " puntos de hambre");
                    System.out.println("Al comerse la tarta se alimento bien, pero tanta cantidad de comida le ha ido aburriendo, +10 puntos de aburrimiento");
                    System.out.println("Obtiene 3 puntos de energía");
                    System.out.println("Por ser la opción más segura " + nombre+ " solo recibes 5 puntos de experiencia");
                    numTartas--;
                }else System.out.println("No te quedan tartas");
                break;
            default:
                System.out.println("Tu equivocación ha hecho que " + nombre+" no coma este turno");
                break;
        }
    }
    public void dormir(){
        if(sueño==true){
            this.experiencia=experiencia+30;
            System.out.println("Al irse a dormir con sueño, " + nombre + " recibe un plus de experiencia de 30 puntos");
        }else{
            this.experiencia=experiencia+1;
            System.out.println("Al dormir sin sueño "+nombre+" recibe algo de experiencia (1 punto)");
        }
        int reduccionEstres=(int)((Math.random()*45)+25);
        this.estres=estres-reduccionEstres;
        this.hambre=hambre+15;
        aburrimiento=aburrimiento+15;
        sueño=false;
        System.out.println(nombre+" se hechó una buena siesta y se aburrió un poco (+15 puntos de aburrimiento)");
    }
    public void usarCalmante(){
        if(calmantes!=0){
            int valorCalmante=(int)((Math.random()*25)+15);
            this.estres=estres-valorCalmante;
            calmantes--;
            aburrimiento=aburrimiento+10;
            if(estres<0) estres=0;
            if(estres>100) estres=100;
            System.out.println(nombre+" redujo el estrés en " + valorCalmante + " puntos, pero el calmante le aburre un poco y suma 10 puntos ");
            if(estres>=50){
                this.experiencia=experiencia+30;
                System.out.println(nombre + " decidió usar un calmante con un nivel de estrés considerable y por ello recibe 30 puntos de experiencia");
            }else{
                this.experiencia=experiencia+15;
                System.out.println(nombre + " ha usado un calmante con poco estrés por lo que recibe solo 10 puntos de experiencia");
            }
        }
        else System.out.println("No tienes calmantes");
    }
    public void curarse(){
        if(medicinas!=0){
            int valorCuracion=(int)((Math.random()*25)+(15+nivel));
            this.salud=salud+valorCuracion;
            medicinas--;
            aburrimiento=aburrimiento+10;
            estres=estres-5;
            if(salud<0) salud=0;
            if(salud>100) salud=100;
            System.out.println(nombre+" se curó y ahora tiene " + salud+ " puntos de vida, reduce algo de estrés (5 puntos). Además curarse es aburrido y suma 10 puntos de aburrimiento");
            if(enfermo==true){
                this.experiencia=experiencia+30;
                System.out.println("Al usar una medicina teniendo enfermedad, " + nombre + " recibe un plus de experiencia de 30 puntos");
                enfermo=false;
            }else{
                this.experiencia=experiencia+15;
                System.out.println("Al usar una medicina solamente para aumentar la salud, "+nombre+" recibe algo de experiencia (15 puntos)");
            }
        }else System.out.println("No tienes medicinas");
    }
    public void jugar(){
        if(energia>0){
            estres=estres-40;
            salud=salud-20;
            hambre=hambre+20;
            System.out.println("Al jugar reduces drasticamente el aburrimiento (60 puntos) y el estrés (40 puntos)");
            System.out.println("Sin embargo, el ser queda fatigado y pierde 20 puntos de salud, además le entra el hambre (+20 puntos)");
            energia--;
            if(aburrimiento>60){
            this.experiencia=experiencia+70;
            aburrimiento=aburrimiento-60;
            System.out.println(nombre+" realmente necesitaba jugar un rato y recibe muchisima experiencia (70 puntos EXP)");
            }else{
                aburrimiento=aburrimiento-60;
                this.experiencia=experiencia+40;
                System.out.println("A "+nombre + " le encanta jugar y pese a no estar aburrido, agradece entretenerse un rato (40 puntos EXP)");
            }
            }else System.out.println(nombre+" no puede jugar ya que no le queda energía");
    }
    public void comprobarEstadosEfectos(){
        salud = ajustarLimite(salud);
        hambre = ajustarLimite(hambre);
        aburrimiento = ajustarLimite(aburrimiento);
        comprobarAburrimiento();
        comprobarHambre();
        comprobarNumMedicinasCalmantes();
        efectosTemporales();
        estres = ajustarLimite(estres);
        comprobarSalud();
        comprobarEnfermedadySueño();
    }
    public void generadores (){
        generarHambre();
        generarEnfermedad();
        generarSueño();
    }
    public void verEstados (){
        morir();
        if(estres!=100) System.out.print("Nivel-Estrés: " + estres);
        if(aburrimiento!=100) System.out.print("    Nivel-Aburrimiento: " + aburrimiento);
        if(hambre!=100) System.out.print("    Nivel-Hambre: " + hambre);       
        if(salud!=0) System.out.print("    Nivel-Salud: " + salud);
        System.out.print("     Energía restante del ser " + energia);
        System.out.print("     Numero medicinas: "+medicinas);
        System.out.print("     Numero calmantes: "+calmantes);
        System.out.print("     Puntos de experiencia: "+experiencia);
        System.out.println();
        System.out.print("                                                      Ser de nivel: "+ nivel);
        System.out.print("     Necesitas "+(limiteExp-experiencia)+ " puntos de EXP para subir de nivel");
    }
    public void verComidas (){
        System.out.print("Numero-Galletas: " + numGalletas);
        System.out.print("    Numero-Pizzas: " + numPizzas);
        System.out.println("    Numero-Tartas: " + numTartas);
    }
    public void subirNivel (){
        int exceso=0;
        if(experiencia>limiteExp){
            exceso=experiencia-limiteExp;
            experiencia=limiteExp;
        }
        if(limiteExp==experiencia){
            nivel++;
            limiteExp=nivel*limiteExp;
            medicinas=medicinas+(nivel);
            numGalletas=numGalletas+((2*nivel)-1);
            numPizzas=numPizzas+2;
            numTartas++;
            calmantes++;
            experiencia=exceso;
            energia++;
            System.out.println();
            System.out.println("------>Has subido de nivel<------");
            System.out.println("Obtienes algunos premios");
            System.out.print("Galletas: " + ((2*nivel)-1));
            System.out.print("    Pizzas: "+ 2);
            System.out.print("    Una tarta");
            System.out.print("    Medicinas: "+nivel);
            System.out.print("    Calmantes: "+ 1);
            System.out.print("    Una carga de energia:");
            System.out.println();
        }
    }
    public boolean atacar(BotEnemigo miEnemigo){
        if(energia>0){
            int daño=(int)(Math.random()*(20*nivel)+(10*nivel));
            miEnemigo.salud=miEnemigo.salud-daño;
            energia--;
            ajustarLimite(miEnemigo.salud);
            System.out.println(nombre+" ataca a su enemigo inflingiendo "+daño+" puntos de daño a costa de usar una carga de energía");
            System.out.println();
            return ataque=true;
        }else{
            System.out.println("No tienes energía suficiente y haces cero puntos de daño");
            return ataque=false;
        }
    }
    public void noHacerNada(){
        this.aburrimiento=aburrimiento+30;
        this.estres=estres+20;
        this.salud=salud-20;
        this.hambre=hambre+10;
        this.experiencia=experiencia+100;
    }
    public boolean noAtacar(){
        return ataque=false;
    }
    public boolean persuadir(){
        boolean exito=false;
        int puntosHambre=100-hambre;
        int puntosAburrimiento=100-aburrimiento;
        int puntosEstres=100-estres;
        int puntosSalud=salud;
        int puntTotal = puntosHambre+puntosAburrimiento+puntosEstres+puntosSalud;
        int maxPuntos=400;
        double prcntExito=(puntTotal/maxPuntos);
        if(Math.random() < prcntExito){
            this.experiencia=experiencia+(nivel*30);
            System.out.println("Se valora mucho la paz y por ello recibes "+(nivel*30)+" puntos EXP al persuadir al enemigo");
            return exito=true;
        }
        return exito=false;
    }
}
