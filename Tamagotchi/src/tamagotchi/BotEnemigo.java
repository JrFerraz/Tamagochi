package tamagotchi;
public class BotEnemigo {
    String nombre;
    int nivel;
    int energia, hambre, salud, estres, aburrimiento;
    int medicinas, calmantes;
    int tarta;
    int turnosSinAtacar;
    boolean vivo, flag;
     
    public BotEnemigo(tamagochi nuevoSer){
        turnosSinAtacar=0;
        nivel=nuevoSer.nivel+(int)((Math.random()*3));
        energia=(int)((Math.random()*6)+1);
        hambre=(int)((Math.random()*39)+1);
        salud=(int)((Math.random()*49)+51);
        estres=(int)((Math.random()*39)+1);
        aburrimiento=(int)((Math.random()*39)+1);
        medicinas=(int)((Math.random()*2)+1);
        calmantes=(int)((Math.random()*3)+1);
        tarta=(int)((Math.random()*3)+1);
        vivo=true;
    }
    public void morir(){
       if(aburrimiento==100 || estres==100 || hambre==100 || salud==0){
           vivo=false;
           if(aburrimiento==100) System.out.println("Has matado al enemigo de aburrimiento");
           if(estres==100) System.out.println("El enemigo murió de estrés");
           if(hambre==100) System.out.println("El enemigo murió de hambre");
           if(salud==0) System.out.println("Mataste al enemigo");
       }
    }
    public void atacar(tamagochi nuevoSer){
        if(energia>0){
            int ataque =(int)(Math.random()*(20*nivel));
            energia--;
            this.estres=estres+15;
            this.aburrimiento=aburrimiento-10;
            System.out.println("El enemigo se pone nervioso en combate y genera "+15+" puntos de estrés, aunque al pegar no se aburre y resta 10 puntos al aburrimiento");
            System.out.println(nuevoSer.nombre+" recibe un impacto de "+ataque+" puntos de daño, el enemigo pierde 1 de energía por el esfuerzo");
            nuevoSer.salud=nuevoSer.salud-ataque;
            ajustarLimite(nuevoSer.salud);
        }else System.out.println("Como no tenia energía su golpe ha sido inutil");
    }
    public void finSer(tamagochi nuevoSer){
        nuevoSer.calmantes=nuevoSer.calmantes+calmantes;
        nuevoSer.medicinas=nuevoSer.medicinas+medicinas;
        nuevoSer.numTartas=nuevoSer.numTartas+tarta;
        nuevoSer.experiencia=nuevoSer.experiencia+(nivel*20);
        System.out.println("Al acabar con el otro ser obtienes sus cosas...");
        System.out.print("Calmantes: " + calmantes);
        System.out.print("    Medicinas: " + medicinas);
        System.out.print("    Tartas: " + tarta);
        System.out.print("    Puntos EXP: "+ (nivel*2l));
    }
    public void comer(){
        if(tarta!=0){
                int valorComida=(int)((Math.random()*(40+nivel))+(45-nivel));
                this.hambre=hambre-valorComida;
                this.aburrimiento=aburrimiento+10;
                this.energia=energia+1;
                tarta--;
                System.out.println("El enemigo comió, eliminando " +valorComida+" puntos de hambre");
        }else System.out.println("Al enemigo no le queda comida");
    }
    public void curarse(){
        if(medicinas!=0){
            int valorCuracion=(int)((Math.random()*25)+(15+nivel));
            this.salud=salud+valorCuracion;
            medicinas--;
            estres=estres-5-nivel;
            System.out.println("El enemigo se cura, recuperando " +valorCuracion+" puntos de salud, reduciendo también ligeramente el estrés");
        }else System.out.println("Al enemigo no le quedan medicinas");
    }
    public void usarCalmante(){
        if(calmantes!=0){
            int valorCalmante=(int)((Math.random()*25)+15);
            this.estres=estres-valorCalmante;
            calmantes--;
            aburrimiento=aburrimiento-10;
            System.out.println("El enemigo usa un calmante, reduciendo el estres por valor de "+valorCalmante+" y reduce el aburrimiento en (10 puntos)");
        }else System.out.println("Al enemigo no le quedan calmantes");
    }
    private int ajustarLimite(int valor){
        if(valor>100) valor=100;
        if(valor<0) valor = 0;
        return valor;
    }
    public boolean regalarItem(boolean persuasion, tamagochi nuevoSer){
        if(medicinas>0 && Math.random() < 0.5){
                nuevoSer.medicinas=nuevoSer.medicinas+1;
                medicinas--;
                System.out.println("El ser te ha regalado una medicina antes de irse");
            }else if(calmantes>0 && Math.random() < 0.8){
                nuevoSer.calmantes=nuevoSer.calmantes+1;
                calmantes--;
                System.out.println("El ser te ha regalado un calmante antes de irse");
            }else if(tarta>0 && Math.random() < 1){
                nuevoSer.numTartas=nuevoSer.numTartas+1;
                tarta--;
                System.out.println("El ser te ha regalado una tarta antes de irse");
            }else{
                System.out.println("El ser no te ha dado nada, aunque almenos te ha dejado empaz");
            }
            return flag=false;
    }
    public void mantenerLimites(){
        salud = ajustarLimite(salud);
        hambre = ajustarLimite(hambre);
        energia = ajustarLimite(energia);
        estres = ajustarLimite(estres);
        aburrimiento = ajustarLimite(aburrimiento);
        medicinas = ajustarLimite(medicinas);
        calmantes = ajustarLimite(calmantes);
        tarta = ajustarLimite(tarta);
    }
    public void aburrimiento(boolean ataque){
        if(!ataque){
            turnosSinAtacar++;
            this.aburrimiento=aburrimiento+(turnosSinAtacar*10);
            System.out.println("Como no le atacas el enemigo se está aburriendo y suma " +(turnosSinAtacar*10)+" puntos de aburrimiento");
        }else turnosSinAtacar=0;
    }
    public boolean caracteristicasEnemigo(tamagochi nuevoSer){
        boolean existo=false;
        morir();
        if(vivo){
            System.out.println();
            System.out.println("Estadisticas del enemigo...");
            if(estres!=100) System.out.print("Nivel-Estrés: " + estres);
            if(aburrimiento!=100) System.out.print("    Nivel-Aburrimiento: " + aburrimiento);
            if(hambre!=100) System.out.print("    Nivel-Hambre: " + hambre);       
            if(salud!=0) System.out.print("    Nivel-Salud: " + salud);
            System.out.print("     Energía restante del ser " + energia);
            System.out.print("     Numero medicinas: "+medicinas);
            System.out.print("     Numero calmantes: "+calmantes);
            System.out.print("     Ser de nivel: "+ nivel);
            return existo=true;
        }else{
            finSer(nuevoSer);
            return existo=false;
        }
    }
    public void accionesPosibles(tamagochi nuevoSer){
        int valorHambre=(int)((Math.random()*15)+10);
        System.out.println();
        System.out.println("El tiempo también pasa para el enemigo y por ello suma "+valorHambre+" puntos de hambre");
        this.hambre=hambre+valorHambre;
        if(salud<=30 && Math.random() < 0.5){
            System.out.println("Ha decidido curarse");
            curarse();
        }else if(hambre>=70 && Math.random() < 0.8){
            System.out.println("Ha decidido comer");
            comer();
        }else if(estres>=70 && Math.random() < 0.6){
            System.out.println("Ha decidido usar calmante");
            usarCalmante();
        }else if(aburrimiento>=70 && Math.random() < 0.6){
            System.out.println("Ha decidido usar calmante");
            usarCalmante();
        }else{
            System.out.println("Te ha atacado");
            atacar(nuevoSer);
        }
    }
}